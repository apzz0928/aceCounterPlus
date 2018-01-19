package aceCounterPlus;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
//import org.openqa.selenium.JavascriptExecutor;

//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class linkCheck {      
 	   private static String baseUrl, hubUrl;
       private static String TestBrowser;
       private WebDriver driver;
       private int invalidLinksCount;
       
       @Parameters("browser")
       @BeforeClass
       public void beforeTest(String browser) throws MalformedURLException {
 	       baseUrl = "http://new.acecounter.com/admin/login";
    	   hubUrl = "http://10.77.249.69:5555/wd/hub";
 	       
    	   String urlToRemoteWD = hubUrl;
    	   DesiredCapabilities cap;
    	   ScreenShooter.captureSuccessfulTests = false;
    	   
    	   if(browser.equals("chrome")){
    		   TestBrowser = "chrome";
    		   cap = DesiredCapabilities.chrome();
    		   RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
    		   WebDriverRunner.setWebDriver(driver);
    		   driver.manage().window().setSize(new Dimension(1650, 900));
     		} else if(browser.equals("firefox")) {
     			TestBrowser = "firefox";
     			cap = DesiredCapabilities.firefox();
     			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
     			WebDriverRunner.setWebDriver(driver);
     			driver.manage().window().setSize(new Dimension(1650, 900));
     		} else if(browser.equals("edge")) {
     			TestBrowser = "edge";
     			cap = DesiredCapabilities.edge();
     			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
     			WebDriverRunner.setWebDriver(driver);
     			driver.manage().window().setSize(new Dimension(1650, 900));
     		} else if(browser.equals("internetExplorer")) {
     			TestBrowser = "internetExplorer";
     			cap = DesiredCapabilities.internetExplorer();
     			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); //IE 실행을 위한 보안 관련 설정 임시 변경
     			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); //ie text 입력 속도 향상을 위한 부분
     			cap.setCapability("requireWindowFocus", true); //ie text 입력 속도 향상을 위한 부분
     			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); //ie 캐시 삭제를 위한 부분
     			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
     			WebDriverRunner.setWebDriver(driver);
     			driver.manage().window().setSize(new Dimension(1650, 900));
     		} 
       }
       @Test(priority = 0)
       public void login() throws InterruptedException {
 	       open(baseUrl);
 	       $(".gui-input", 0).setValue("apzz0928");
 	       $(".gui-input", 1).setValue("qordlf!@34");
 	       $(".btn-primary").click();
 	       Thread.sleep(5000);
 	   }
       @Test(priority = 1)
       public void linkCheck() {
           String url = "";
           HttpURLConnection huc = null;
           int respCode = 200;
           
    	   List<WebElement> links = driver.findElements(By.tagName("a"));
           Iterator<WebElement> it = links.iterator();
           while(it.hasNext()){
               url = it.next().getAttribute("href");
               System.out.println(url);
               if(url == null || url.isEmpty()){
                   System.out.println("URL is either not configured for anchor tag or it is empty");
                   continue;
               }
               if(!url.startsWith(baseUrl)){
                   System.out.println("URL belongs to another domain, skipping it.");
                   continue;
               }
               try {
                   huc = (HttpURLConnection)(new URL(url).openConnection());
                   huc.setRequestMethod("HEAD");
                   huc.connect();
                   respCode = huc.getResponseCode();
                   if(respCode >= 400){
                       System.out.println(url+" is a broken link");
                   }
                   else{
                       System.out.println(url+" is a valid link");
                   }
               } catch (MalformedURLException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               } catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }
       }
       //@Test(priority = 1)
       public void egloosTest() {
 	       
       }
       @AfterClass
       public void afterTest() {
    	  closeWebDriver();
       }    

}   