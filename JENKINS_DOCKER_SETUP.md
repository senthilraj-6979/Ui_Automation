# Docker Setup for Jenkins with Ui_Automation

## Quick Docker Setup

### Option 1: Official Jenkins Docker Image

Create a `docker-compose.yml` in your project root:

```yaml
version: '3.8'

services:
  jenkins:
    image: jenkins/jenkins:lts
    container_name: jenkins_automation
    ports:
      - "8080:8080"
      - "50000:50000"
    environment:
      JAVA_OPTS: "-Xmx2048m"
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    networks:
      - automation_network

  # Optional: Chrome for Selenium
  chrome:
    image: selenium/node-chrome:latest
    container_name: selenium_chrome
    ports:
      - "4444:4444"
      - "7900:7900"
    environment:
      SE_ENABLE_PASS_THROUGH: "true"
      SE_VNC_NO_GNOME: "true"
    networks:
      - automation_network
    depends_on:
      - hub

  # Optional: Selenium Hub
  hub:
    image: selenium/hub:latest
    container_name: selenium_hub
    ports:
      - "4442:4442"
      - "4443:4443"
      - "4444:4444"
    networks:
      - automation_network

volumes:
  jenkins_home:

networks:
  automation_network:
    driver: bridge
```

### Quick Start

```bash
# Start Jenkins with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f jenkins

# Access Jenkins
# Open: http://localhost:8080

# Get initial admin password
docker exec jenkins_automation cat /var/jenkins_home/secrets/initialAdminPassword

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

---

## Option 2: Dockerfile (Custom)

Create a `Dockerfile` in project root:

```dockerfile
FROM jenkins/jenkins:lts

USER root

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Chrome for Selenium
RUN apt-get update && \
    apt-get install -y chromium-browser && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy Jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

USER jenkins

EXPOSE 8080 50000
```

Create `plugins.txt`:

```
pipeline-model-definition:latest
git:latest
junit:latest
htmlpublisher:latest
email-ext:latest
allure-jenkins-plugin:latest
log-parser:latest
```

Build and run:

```bash
docker build -t jenkins-automation .
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins jenkins-automation
```

---

## Option 3: Complete Setup with Pipeline

Create `.github/workflows/setup-jenkins.yml`:

```yaml
name: Setup Jenkins Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  setup-jenkins:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up Docker
      uses: docker/setup-buildx-action@v2
    
    - name: Start Jenkins
      run: |
        docker-compose up -d
        sleep 30
    
    - name: Get Jenkins URL
      run: |
        echo "Jenkins URL: http://localhost:8080"
        docker exec jenkins_automation cat /var/jenkins_home/secrets/initialAdminPassword
    
    - name: Verify Jenkins
      run: |
        docker exec jenkins_automation curl -s http://localhost:8080 || true
```

---

## Jenkins Configuration as Code (JCasC)

Create `jenkins.yaml` in your project:

```yaml
jenkins:
  systemMessage: "Welcome to UI Automation Jenkins"
  
  securityRealm:
    local: {}
  
  authorizationStrategy:
    roleBased:
      roles:
        global:
          - name: "admin"
            permissions:
              - "hudson.model.Hudson.Administer"
          - name: "dev"
            permissions:
              - "hudson.model.Hudson.Read"
              - "hudson.model.Item.Build"

  remotingSecurity:
    enabled: true

  crumbIssuer:
    standard: {}

credentials:
  system:
    domainCredentials:
      - credentials:
          - basic:
              scope: GLOBAL
              id: "github_credentials"
              username: "${GIT_USERNAME}"
              password: "${GIT_PASSWORD}"
              description: "GitHub credentials"

unclassified:
  location:
    url: http://localhost:8080/
  
  mailer:
    smtpHost: "smtp.gmail.com"
    smtpPort: 587
    useSslSmtpPort: false
    useTls: true
    username: "your-email@gmail.com"
    password: "${EMAIL_PASSWORD}"
    replyToAddress: "your-email@gmail.com"

tools:
  maven:
    installations:
      - name: "Maven3"
        home: "/usr/share/maven"
  jdk:
    installations:
      - name: "JDK8"
        home: "/usr/lib/jvm/java-8-openjdk-amd64"

jobs:
  - script: >
      pipelineJob('UI_Automation_Pipeline') {
        description('Selenium UI Automation Tests')
        definition {
          cpsScm {
            scm {
              git {
                remote {
                  url('https://github.com/your-repo/Ui_Automation.git')
                  credentials('github_credentials')
                }
                branch('*/main')
              }
            }
            scriptPath('Jenkinsfile')
          }
        }
        triggers {
          githubPush()
        }
      }
```

Use with Docker:

```dockerfile
# Add to Dockerfile
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/jenkins.yaml
COPY jenkins.yaml /var/jenkins_home/jenkins.yaml
```

---

## Kubernetes Deployment

Create `kubernetes/jenkins-deployment.yaml`:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: jenkins-config
data:
  casc.yaml: |
    jenkins:
      systemMessage: "Jenkins on Kubernetes"

---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jenkins
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins
  template:
    metadata:
      labels:
        app: jenkins
    spec:
      containers:
      - name: jenkins
        image: jenkins/jenkins:lts
        ports:
        - containerPort: 8080
        - containerPort: 50000
        env:
        - name: JAVA_OPTS
          value: "-Xmx1500m"
        volumeMounts:
        - name: jenkins-storage
          mountPath: /var/jenkins_home
        - name: config-volume
          mountPath: /var/jenkins_home/casc
      volumes:
      - name: jenkins-storage
        persistentVolumeClaim:
          claimName: jenkins-pvc
      - name: config-volume
        configMap:
          name: jenkins-config

---
apiVersion: v1
kind: Service
metadata:
  name: jenkins
spec:
  type: LoadBalancer
  ports:
  - port: 80
    targetPort: 8080
    name: web
  - port: 50000
    targetPort: 50000
    name: agent
  selector:
    app: jenkins

---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
```

Deploy to Kubernetes:

```bash
kubectl apply -f kubernetes/jenkins-deployment.yaml
kubectl port-forward svc/jenkins 8080:80
```

---

## Docker Compose with Multiple Services

```yaml
version: '3.8'

services:
  jenkins:
    build: .
    container_name: jenkins
    ports:
      - "8080:8080"
      - "50000:50000"
    environment:
      JAVA_OPTS: "-Xmx2048m"
      JENKINS_OPTS: "--argumentsRealm.passwd.jenkins=admin --argumentsRealm.roles.jenkins=admin"
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    networks:
      - test_network
    depends_on:
      - selenium-hub

  selenium-hub:
    image: selenium/hub:latest
    container_name: selenium_hub
    ports:
      - "4444:4444"
    environment:
      GRID_MAX_SESSION: 10
    networks:
      - test_network

  chrome:
    image: selenium/node-chrome:latest
    container_name: selenium_chrome_1
    environment:
      SE_EVENT_BUS_HOST: selenium-hub
      SE_EVENT_BUS_PUBLISH_PORT: 4442
      SE_EVENT_BUS_SUBSCRIBE_PORT: 4443
      SE_NODE_MAX_SESSIONS: 5
    networks:
      - test_network
    depends_on:
      - selenium-hub

  firefox:
    image: selenium/node-firefox:latest
    container_name: selenium_firefox_1
    environment:
      SE_EVENT_BUS_HOST: selenium-hub
      SE_EVENT_BUS_PUBLISH_PORT: 4442
      SE_EVENT_BUS_SUBSCRIBE_PORT: 4443
      SE_NODE_MAX_SESSIONS: 5
    networks:
      - test_network
    depends_on:
      - selenium-hub

  db:
    image: mysql:8.0
    container_name: jenkins_db
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: test_db
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - test_network

volumes:
  jenkins_home:
  mysql_data:

networks:
  test_network:
    driver: bridge
```

Start entire stack:

```bash
docker-compose up -d
docker-compose down
docker-compose logs -f
```

---

## Useful Docker Commands

```bash
# View logs
docker logs -f jenkins_automation

# Execute command in container
docker exec -it jenkins_automation bash

# Copy file from container
docker cp jenkins_automation:/var/jenkins_home/config.xml ./

# Check Jenkins admin password
docker exec jenkins_automation cat /var/jenkins_home/secrets/initialAdminPassword

# Restart Jenkins
docker restart jenkins_automation

# Remove and clean up
docker rm -f jenkins_automation
docker volume rm jenkins_home
```

---

## Troubleshooting

### Jenkins won't start
```bash
docker logs jenkins_automation
# Check JAVA_OPTS memory settings
```

### Permission denied errors
```bash
# Fix Docker socket permissions
sudo usermod -aG docker $USER
newgrp docker
```

### Port already in use
```bash
# Change port in docker-compose.yml
ports:
  - "8081:8080"  # Use 8081 instead
```

### Selenium connection issues
```bash
# Check if Selenium Hub is running
docker exec -it selenium_hub curl localhost:4444
```

---

## Production Considerations

1. **Persistent Storage**: Use Docker volumes for Jenkins home
2. **Backup**: Regularly backup Jenkins configuration
3. **Resource Limits**: Set CPU and memory limits in docker-compose
4. **Security**: Use secrets management, not hardcoded passwords
5. **Monitoring**: Use Prometheus + Grafana
6. **Logging**: Centralize logs with ELK stack
7. **High Availability**: Use Jenkins with load balancer
8. **Git Integration**: Configure webhook for automated builds

---

