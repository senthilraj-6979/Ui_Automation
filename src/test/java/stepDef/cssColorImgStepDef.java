package stepDef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import webevents.PageBase;

public class cssColorImgStepDef extends PageBase {
    WebElement image;

    @When("Click and verify the image size")
    public void click_and_verify_the_image_size() {
        image = getDriver().findElement(By.xpath("//div[@class='position-relative']"));
        int imgHeight = image.getSize().height;
        int imgWidth = image.getSize().width;
        System.out.println("image height---"+imgHeight);
        System.out.println("img Width---"+imgWidth);
        Assert.assertTrue(imgHeight>10);
        Assert.assertTrue(imgWidth>10);

    }
    @Then("Verify the image color")
    public void verify_the_image_color() {
       String color = image.getCssValue("color");
       String bgColor = image.getCssValue("background-color");
       System.out.println("Color------"+color);
       System.out.println("bgColor------"+bgColor);
    }

    @Then("Verify the image location")
    public void verify_the_image_position() {
        Point location = image.getLocation();
        System.out.println("X position---"+location.x+" Y position---"+location.y);
    }

    @Then("Verify the bgcolor matches {string}")
    public void verify_the_bgcolor_matches(String bgColorCode) {
        WebElement bgColorImg = getDriver().findElement(By.xpath("//h5[contains(text(),' Development')]//ancestor" +
                "::div[@class='bg-primary bg-opacity-10 rounded-3 text-center p-3 position-relative stretched-link']"));
        String bgColorImg1 = bgColorImg.getCssValue("background-color");
        Assert.assertEquals(bgColorImg1,bgColorCode);
    }
}
