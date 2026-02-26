package stepDef;

import com.pages.FooterPage;
import com.pages.LoginPage;
import com.pages.MouseAction;
import com.qa.factory.DriverFactory;
import com.utilities.UIActionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import webevents.PageBase;

public class MouseActionStepDef extends PageBase {

    private MouseAction mouseAction = new MouseAction(DriverFactory.getDriver());

    @Then("Hover mouse Add-ons")
    public void user_is_on_login_page() {
          mouseAction.mouse_hover();
    }

}
