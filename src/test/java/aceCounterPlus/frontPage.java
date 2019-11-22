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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
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
		$(By.linkText("Live ��ú���")).click();
		$(By.linkText("������-���")).click();
		$(By.linkText("������-��")).click();
		$(By.linkText("�����-������")).click();
		$(By.linkText("��ȯ-��Ƽä����ȯ-���")).click();
		$(By.linkText("������-������-�α�������")).click();
		$(By.linkText("������-���-�ó�����")).click();
		$(By.linkText("Ŀ�ӽ�-�����-��ü")).click();
		$(By.linkText("Ŀ�ӽ�-�����-��")).click();

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