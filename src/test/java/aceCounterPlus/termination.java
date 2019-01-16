package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

/*import com.codeborne.selenide.testng.ScreenShooter;*/

public class termination {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, A, B, C, domain, checkMsg, pageLoadCheck, dateCheck;

	Date number_date = new Date();
	SimpleDateFormat number_format = new SimpleDateFormat("yyyy-MM-dd");
	String date = number_format.format(number_date);

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf";
		pw1 = "qordlf";
		A = "!@34";
		B = "1@#4";
		C = "12#$";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*
			 * ChromeOptions options = new ChromeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			// cap = DesiredCapabilities.firefox();
			// RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*
			 * EdgeOptions options = new EdgeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*
			 * InternetExplorerOptions options = new InternetExplorerOptions(); driver = new
			 * RemoteWebDriver(new URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // 보안설정 변경
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}
	@Test(priority = 0)
	public void testAccount_terminationApply() {
		open("https://new-admin.acecounter.com/admin/login");
		sleep(1500);
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(1500);
		open("https://new-admin.acecounter.com/admin/regCustomer/userList");
		sleep(1000);
	    $("#__BVID__4_").click(); //상태 : 트라이얼(정상) 선택
	    sleep(500);
	    $("#__BVID__4_").selectOptionByValue("t1");
	    //$("option[value=t1]").click();
	    //$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='정보변경'])[1]/following::option[32]")).click();
	    sleep(500);
	    $("#__BVID__5_").click();
	    sleep(500);
	    $("#__BVID__5_").selectOptionByValue("u.email_addr");
	    //$("option[value=u.email_addr]").click();
	    //$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='정보변경'])[1]/following::option[51]")).click();
	    sleep(1500);
	    $(".input-sm", 5).setValue("apzz0928@nate.com");		
		$(".btn-dark").click();
		sleep(3000);
		for(int i=0, x=100;i<=3;i++) {
			$("a", x).click();
			sleep(1000);
			switchTo().window(1);
			open("https://new-admin.acecounter.com/manage/serviceInfo/leaveService");
			sleep(1000);
			$("#pwd").setValue("qordlf!@34");
			$("#btn-ok").click();
			$("#btnIng").waitUntil(visible, 10000);
			sleep(800);
			$("#checkAll").click();
			$("#btnIng").click();
			sleep(1000);
			$("#btn-modal-alert-yes").click();
			sleep(500);
			$("label", 5).click();
			$("label", 9).click();
			$(".form-control", 3).setValue(date + " 테스트계정 삭제");
			$("#btnApply").scrollIntoView(false);
			$("#btnApply").click();
			sleep(1000);
			$(".btn-info", 3).click();
			sleep(800);
			$(".btn-primary").click();
			switchTo().window(0);
			x = x+10;
		}
  	}
  	@Test(priority = 1)
	public void testAccount_termination() {
		open("https://new-admin.acecounter.com/admin/login");
		sleep(1500);
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(1500);
		open("https://new-admin.acecounter.com/admin/comApply/termination");
		$("#__BVID__2_").click();
		sleep(500);
		$("#__BVID__2_").selectOptionByValue("10");
		//$("option[value=10]").click();
	    //$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='정보변경'])[1]/following::option[19]")).click();
	    sleep(500);
		$("#__BVID__4_").click();
		sleep(500);
		$("#__BVID__4_").selectOptionByValue("ia.applicant_email_addr");
		//$("option[value=ia.applicant_email_addr]").click();
		sleep(500);
	    $(".input-sm", 4).setValue("apzz0928@nate.com");
		$(".btn-dark").click();
		sleep(3000);
		String[] terminationNumber = {"", "", "", ""};
		for(int i=0, x=0;i<=3;i++) {
			terminationNumber[i] = $("td", x).text();
			x = x+17;
		}
		for(int i=0;i<=terminationNumber.length-1;i++) {
			System.out.println(terminationNumber[i]);
		}
		js("window.open('');");
		sleep(1000);
		switchTo().window(1);
		open("https://new-admin.acecounter.com/admin/comApply/termination/form/" + terminationNumber[0]);
		for(int i=1;i<=4;i++) {
			/*sleep(500); //담당자 로그인 사용자 자동선택 업데이트로 인해 주석처리
		    $("#__BVID__2_").click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='담당자'])[1]/following::option[47]")).click();*/
		    sleep(500);
		    $("#__BVID__3_").click();
		    sleep(500);
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='처리상태'])[1]/following::option[3]")).click();
		    sleep(500);
		    $(".btn-primary").click();
		    sleep(500);
		    $("#btn-modal-alert-yes").click();
		    sleep(1000);
		    if(i < 4) {
				open("https://new-admin.acecounter.com/admin/comApply/termination/form/" + terminationNumber[i]);
			    sleep(1000);	
		    }
		}
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}