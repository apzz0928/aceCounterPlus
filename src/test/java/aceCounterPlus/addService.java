package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class addService {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain;

	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("MMddhhmmss");
    String number = number_format.format(number_date);
    
	Date number_date1 = new Date();
    String number1 = number_format.format(number_date1);
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf!@34";
		pw1 = "qordlf12#$";
		domain = "apzz";
		//number = "0027";
		//number1 = "00";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = true;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			// driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			cap = DesiredCapabilities.firefox();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
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

	@SuppressWarnings("unused")
	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}

	@Test(priority = 0)
	public void passwordChange() throws InterruptedException {
		open(baseUrl);
		//$("#uid").setValue(id + number);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail !! *** ");
			close();
		}
		$(".go_setting").click();
		$(By.linkText("서비스추가")).click();
		String p4 = $("p", 4).text();
		Thread.sleep(1000);
		if(p4.equals("* 표시는 필수 입력")) {
			System.out.println(" *** addService page access Success !! *** ");
		} else {
			System.out.println(" *** addService page access Fail !! *** ");
			close();
		}
		$("#btn_submit").click();
		String msgCheck = $("p", 10).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("웹사이트 이름을 입력해주세요.")) {
			System.out.println(" *** submit button webSiteName validation check Success !! *** ");
			$(".btn-sm", 5).click();
		} else {
			System.out.println(" *** submit button webSiteName validation check Fail !! *** ");
			close();
		}
		$(By.name("svcNm")).setValue(id + number);
		$("#btn_submit").click();
		msgCheck = $("p", 11).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("등록된 도메인이 없습니다.")) {
			System.out.println(" *** submit button domain input validation check Success !! *** ");
			$(".btn-sm", 6).click();
		} else {
			System.out.println(" *** submit button domain input validation check Fail !! *** ");
			close();
		}
		$(".ace-btn-add-domain").click();
		msgCheck = $("p", 12).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("도메인을 입력하세요.")) {
			System.out.println(" *** add domain button domain input validation check Success !! *** ");
			$(".btn-sm", 7).click();
		} else {
			System.out.println(" *** add domain button domain input validation check Fail !! *** ");
			close();
		}
		$(".gui-input", 1).setValue(id + number);
		$(".ace-btn-add-domain").click();
		msgCheck = $("p", 13).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("도메인 형식이 올바르지 않습니다.")) {
			System.out.println(" *** add domain button domain form validation check Success !! *** ");
			$(".btn-sm", 8).click();
		} else {
			System.out.println(" *** add domain button domain form validation check Fail !! *** ");
			close();
		}
		$(".gui-input", 1).setValue(id + number + ".com");
		$(".ace-btn-add-domain").click();
		$("#btn_submit").click();
		msgCheck = $("p", 14).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("웹사이트 분류를 선택해주세요.")) {
			System.out.println(" *** submit button website sort validation check Success !! *** ");
			$(".btn-sm", 9).click();
		} else {
			System.out.println(" *** submit button website sort validation check Fail !! *** ");
			close();
		}
	    $(By.name("largeCategoryCd")).click();
	    $(By.xpath("//option[@value='22']")).click();
		$("#btn_submit").click();
		msgCheck = $("p", 15).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("웹사이트 분류를 선택해주세요.")) {
			System.out.println(" *** submit button website sort validation check Success !! *** ");
			$(".btn-sm", 10).click();
		} else {
			System.out.println(" *** submit button website sort validation check Fail !! *** ");
			close();
		}
	    $(By.name("middleCategoryCd")).click();
	    $(By.xpath("//option[@value='188']")).click();
		$("#btn_submit").click();
		Thread.sleep(2000);
		msgCheck = $("h2").text();
		if(msgCheck.equals("서비스 추가가 완료되었습니다.")) {
			System.out.println(" *** addServiceComplete page access Success !! *** ");
			$(".btn-dark").click();
		} else {
			System.out.println(" *** addServiceComplete page access Fail !! *** ");
			close();
		}
		
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}