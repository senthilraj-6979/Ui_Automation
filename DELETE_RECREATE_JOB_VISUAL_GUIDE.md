# 🎯 DELETE & RECREATE JOB - VISUAL GUIDE

## The Problem

Jenkins is loading OLD declarative pipeline metadata, ignoring your new scripted Jenkinsfile.

## The Solution

**DELETE the job completely, then CREATE it fresh.**

---

## STEP 1: Delete Old Job

### Visual Steps:

```
Jenkins Dashboard
    ↓
Click: UI_Automation_Pipeline (the job name)
    ↓
Left sidebar menu
    ↓
Click: "Delete Project" or "Delete Pipeline"
    ↓
Confirmation dialog appears
    ↓
Click: "Yes" or "OK"
    ↓
Job is GONE ✓
```

**What you'll see:**
- Job disappears from dashboard
- All builds gone
- All metadata cleared

---

## STEP 2: Create New Job

### 2a. New Item
```
Jenkins Dashboard
    ↓
Click: "New Item" (top left)
    ↓
Enter name: UI_Automation_Pipeline
    ↓
Select: Pipeline (click the radio button)
    ↓
Click: OK
```

### 2b. Configure Pipeline
```
Configuration page loads
    ↓
Scroll down to "Pipeline" section
    ↓
Find "Definition" dropdown
    ↓
Select: "Pipeline script from SCM"
```

### 2c. SCM Configuration
```
SCM dropdown appears
    ↓
Select: Git
    ↓
Repository URL field appears
    ↓
Enter: https://github.com/senthilraj-6979/Ui_Automation
```

### 2d. Branch Configuration
```
Find "Branches to build" section
    ↓
Branch Specifier field
    ↓
Enter: */Ui_Automation
```

### 2e. Script Path
```
Find "Script Path" field
    ↓
Should already say: Jenkinsfile
    ↓
If not, enter: Jenkinsfile
```

### 2f. Save
```
Scroll to bottom
    ↓
Click: Save (blue button)
    ↓
Job page loads ✓
```

---

## STEP 3: Build Now

```
Job page (after saving)
    ↓
Left sidebar menu
    ↓
Click: "Build Now"
    ↓
Build #1 appears in Build History
    ↓
Click: #1
    ↓
Click: "Console Output"
    ↓
Watch the build execute
```

---

## What You'll See in Console Output

```
Started by user ...
Running as SYSTEM
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /Users/senthilraj/.jenkins/workspace/UI_Automation_Pipeline
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Checkout)
[Pipeline] echo
Checking out code...
[Pipeline] checkout
Cloning the remote Git repository
Cloning repository https://github.com/senthilraj-6979/Ui_Automation
...
[Pipeline] } // stage
[Pipeline] stage
[Pipeline] { (Build)
[Pipeline] echo
Building project...
...
[Pipeline] stage
[Pipeline] { (Test)
[Pipeline] echo
Running tests...
...
[Pipeline] stage
[Pipeline] { (Reports)
[Pipeline] echo
Publishing reports...
[Pipeline] publishHTML
...
[Pipeline] stage
[Pipeline] { (Archive)
[Pipeline] echo
Archiving artifacts...
...
[Pipeline] echo
Build completed successfully
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```

**NO ERRORS! ✅**

---

## Configuration Summary

```
Job Name: UI_Automation_Pipeline
Type: Pipeline
Definition: Pipeline script from SCM
SCM: Git
Repository URL: https://github.com/senthilraj-6979/Ui_Automation
Branch: */Ui_Automation
Script Path: Jenkinsfile
```

---

## This WILL Work Because:

```
OLD JOB:
✗ Had declarative pipeline metadata
✗ Had post block cached
✗ Ignored new Jenkinsfile
✗ junit context error

NEW JOB:
✓ Fresh metadata
✓ Loads from GitHub
✓ Detects scripted pipeline
✓ No post block
✓ SUCCESS!
```

---

## Time Required:

- Delete job: 30 seconds
- Create job: 2 minutes
- Build job: 5-10 minutes

**Total: ~12 minutes to complete success**

---

**DO IT NOW!**

The junit error will be GONE! 🚀

