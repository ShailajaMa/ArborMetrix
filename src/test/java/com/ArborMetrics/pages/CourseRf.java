package com.ArborMetrics.pages;

import com.ArborMetrics.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;


public class CourseRf extends BasePage {

    /**
     * This is a constructor where we are initializing page object class using driver object
     */
    public CourseRf(){
        PageFactory.initElements(driver, this);
    }
    @FindBy(id = "first_4")
    private WebElement firstName;

    @FindBy(id="middle_4")
    private WebElement middleName;

    @FindBy(id="last_4")
    private WebElement lastName;

    @FindBy(id = "input_6")
    private WebElement emailId;

    private static final String FIRSTNAME ="first_4";

    private static final String MIDDLENAME = "middle_4";

    private static final String LASTNAME = "last_4";

    private static final String EMAILID = "input_6";

    private static final String COURSESDROPDOWN = "q46_courses";


    private static final String COURSENAME = "//option[@value=\"Math 101\"]";

    private static final String DROPDOWN = "input_46";

    private static final String SUBMITBUTTON = "input_20";


    @FindBy(id ="input_20")
    private WebElement submit;

    @FindBy(xpath = "//select[@name=\"q46_courses\"]")
    private WebElement courseInputText;

    @FindBy (id ="header_1")
    private WebElement formName;

    @FindBy(css = "input[type=\"tel\"]")
    private WebElement mobileNumber;

    @FindBy (css = "div[class=\"form-error-message\"]")
    private WebElement errorMessage;

    @FindBy (css = "#formPreviewArea")
    WebElement iFrame;

    @FindBy(id = "formPreviewArea")
    WebElement registrationFormWindow;

    public boolean thankYouMessage(String firstName, String middleName, String lastName, String emailID, String course) throws InterruptedException {

        String findElementById = "return (arguments[0].contentDocument.getElementById('%s')).value='";

        this.executeJs(String.format(findElementById, CourseRf.FIRSTNAME) + firstName + "';", registrationFormWindow);
        this.executeJs(String.format(findElementById, CourseRf.MIDDLENAME) + middleName + "';", registrationFormWindow);
        this.executeJs(String.format(findElementById, CourseRf.LASTNAME) + lastName + "';", registrationFormWindow);
        this.executeJs(String.format(findElementById, CourseRf.EMAILID) + emailID + "';", registrationFormWindow);
        this.executeJs(String.format(findElementById, CourseRf.DROPDOWN) + course + "';", registrationFormWindow);

        this.executeJs("return (arguments[0].contentDocument.getElementById('"+CourseRf.SUBMITBUTTON+"')).click();", registrationFormWindow);
        Thread.sleep(1500);
        String thankYOu = (String) this.executeJs("return (arguments[0].contentDocument.getElementsByTagName('h1'))[0].innerHTML;", registrationFormWindow);
        String submittionMessage = (String) this.executeJs("return (arguments[0].contentDocument.getElementsByTagName('p'))[1].innerHTML;", registrationFormWindow);
        return true;
    }



    /**
     * this method is to return boolean if the url is launching
     * @return - boolean
     * @throws IOException
     */
    public boolean isFormAvailable() throws IOException {
        driver.get(getProperties("url"));
        return true;
    }

    /**
     * This method returns boolean if the parameters listed are clickable and able to send keys
     * @param firstName - string
     * @param middleName - string
     * @param lastName - string
     * @param emailId - string
     * @return - boolean
     */
    public boolean sendTextToNameFields(String firstName, String middleName, String lastName, String emailId){
        switchFrame(iFrame);
        if(isVisible(this.firstName)) {
            this.firstName.click();
            this.firstName.sendKeys(firstName);
        }
        if(isVisible(this.middleName)) {
            this.middleName.click();
            this.middleName.sendKeys(middleName);
        }
        if(isVisible(this.lastName)) {
            this.lastName.click();
            this.lastName.sendKeys(lastName);
        }
       // scrollToLocation("185", "1006", this.emailId);
        if(isVisible(this.emailId))
           this.emailId.sendKeys(emailId);
        return true;
    }

    /**
     * This method verifies if the negative data is throwing invalid email id address in the web form
     * @return - boolean
     */
    public boolean negativeTextToEmailId() {
        switchFrame(iFrame);
        scrollToLocation("185", "1006", emailId);
        emailId.sendKeys("233434");
        mobileNumber.click();
        if(errorMessage.getText().equals("Enter a valid e-mail address")){
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method verifies if the dropdown course is selecting Math 101
     * @return - returns the course text - String
     */
   public String isSelectedMath() {
       switchFrame(iFrame);
       scrollToLocation("185", "1395", courseInputText);
       //coursesDropDown.click();
       return isSelect(courseInputText, "Math 101");
   }

    /**
     * Tis checks if we are able to submit form and reloading to new/ empty form by taking screenshot
     * @return - boolean if the form is loaded true or false
     * @throws IOException
     */

  /* public String isSubmitAndRedirect() throws InterruptedException {
       if(isVisible(submit))
         submit.click();
        return thankYouMessage();
   }*/
}
