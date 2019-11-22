package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;

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

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class frontPage {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, number, domain;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
		//hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "apzz0928888";
		pw = "qordlf!@34";
		number = "0002";
		domain = "apzz";

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

	@Test(priority = 0)
	public void login() {
		open(baseUrl);
		$("#uid").setValue(id);
		$("#upw").setValue(pw);
		$(".btn_login").click();
	}

	@Test(priority = 1)
	public void product() {
		$(".menu1").hover();
		$(".go_product_intro").click();
		$("#Map_beta").scrollTo();
		$("#Map").scrollTo();
		$(".customer-support").scrollTo();
		$("img", 40).click();
		System.out.println(" ** -- * Product Intro * -- **");
		$(".gnb").waitUntil(visible, 3000);
		$(".go_product_info").click();
		$(By.linkText("Live 대시보드")).click();
		$(By.linkText("마케팅-요약")).click();
		$(By.linkText("마케팅-상세")).click();
		$(By.linkText("사용자-유지율")).click();
		$(By.linkText("전환-멀티채널전환-경로")).click();
		$(By.linkText("콘텐츠-페이지-인기페이지")).click();
		$(By.linkText("콘텐츠-경로-시나리오")).click();
		$(By.linkText("커머스-잠재고객-전체")).click();
		$(By.linkText("커머스-잠재고객-상세")).click();

	}

	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}

	public static void elementText(String find, String selector, String tag, String element) {
		String tagName = "";
		switch (selector) {
		case "normal":
			tagName = tag;
			break;
		case "id":
			tagName = "#" + tag;
			break;
		case "class":
			tagName = "." + tag;
			break;
		}
		if (find.equals("have")) {
			$(tagName).shouldHave(text(element));
		} else {
			$(tagName).shouldNotHave(text(element));
		}
	}
}