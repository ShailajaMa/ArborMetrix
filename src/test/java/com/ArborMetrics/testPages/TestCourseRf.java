package com.ArborMetrics.testPages;

import com.ArborMetrics.dataProvider.ExcelData;
import com.ArborMetrics.pages.CourseRf;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static com.ArborMetrics.base.BasePage.*;

public class TestCourseRf {
    /**
     * Initializing the browser and getting the url by using  properties file
     * @throws IOException
     */
    @BeforeMethod
    public void openLink () throws IOException {
        initDriver(getProperties("browser"), getProperties("url"));
        log.info("initialized browser and launched url");

    }

    @Test(description = "Validating url", priority = 1)
    public void testOnUrlValidation() throws IOException {
        log.info("validating url test");
        CourseRf courseRf = new CourseRf();
        try {
            Assert.assertTrue(courseRf.isFormAvailable(), "form has not loaded");
        }catch(Exception e){
           e.printStackTrace();
        }
     log.debug("test has exexuted successfully");
    }

    //CourseRegistrationForm_TestCase_1
    @Test ( description = "verify if the input fields are clickable and able to enter data"/*dataProviderClass = ExcelData.class, dataProvider = "NameFieldsData"*/, priority = 2)
    public void testOnNameFieldsData(){
        CourseRf courseRf = new CourseRf();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(courseRf.sendTextToNameFields("Sam","patrick","samuel","sam@gmail.com"));
        softAssert.assertAll();
    }

    //CourseRegistrationForm_TestCase_2
    @Test(description = "verify if the email id field is throwing error if we enter negative data", priority = 5)
    public void testOnNegativeTextToEmailId() throws InterruptedException {
        CourseRf courseRf = new CourseRf();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(courseRf.negativeTextToEmailId(), "Negative data is accepting in the email field");
        softAssert.assertAll();

    }


    //CourseRegistrationForm_TestCase_4
    @Test(description = "Verify if we are able to select course", priority = 3)
    public void testOnIsSelectedMath() {
        CourseRf courseRf = new CourseRf();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(courseRf.isSelectedMath(), "Math 101", "Math 101 is not selected");
        softAssert.assertAll();
    }

    /**
     * This test is entering all the data end to end and asserting if the page is redirecting to form
     * @throws InterruptedException
     */
    @Test(enabled = true)
    public void testOnSubmit() throws InterruptedException {
        Thread.sleep(3000);
        CourseRf courseRf = new CourseRf();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(courseRf.thankYouMessage("Sam", "patrick", "reddy","sam@gmail.com","Math 101"),"Form is not redirecting");
    }

    /**
     * this is to quit the browser
     */
    @AfterMethod
    public void closeBrowser(){
        closeDriver();
    }


}
