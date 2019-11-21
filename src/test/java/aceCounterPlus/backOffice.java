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

public class backOffice {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://new.acecounter.com/admin/login";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
		id = "apzz0928";
		pw = "qordlf!@34";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = true;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
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
		$(".gui-input", 0).setValue(id);
		$(".gui-input", 1).setValue(pw);
		$(".btn-primary").click();
	}

	@Test(priority = 1)
	public void comApply() {
		for (int i = 0; i <= 2; i++) {
			$(".accordion-toggle", 0).hover();
			$(".sub_m", i).click();
			elementText("not", "normal", "td", "데이터가 없습니다.");
			$(By.name("keyword")).setValue("9999999999");
			$(".ladda-button").click();
		}
		System.out.println("-- comApply Test End --");
	}

	@Test(priority = 2)
	public void comPayment() {
		for (int i = 3; i <= 5; i++) {
			$(".accordion-toggle", 1).hover();
			$(".sub_m", i).click();
			elementText("not", "normal", "td", "데이터가 없습니다.");
			$(By.name("keyword")).setValue("9999999999");
			$(".ladda-button").click();
		}
		System.out.println("-- comPayment Test End --");
	}

	@Test(priority = 3)
	public void regCustomer() {
		for (int i = 6; i <= 12; i++) {
			$(".accordion-toggle", 2).hover();
			if (i == 6) { // 이용자만 데이터 로딩 대기 필요
				// elementText("not", "normal", "td", "데이터가 없습니다.");
			}
			$(".sub_m", i).click();
			if (i == 10 || i == 12) {
				// 제휴문의는 오류때문에 검색 x, 피드백은 검색 없음
			} else {
				$(By.name("keyword")).setValue("9999999999");
				$(".ladda-button").click();
			}
			if (i == 12) {
				$(".label-rounded-wide", 2).click();
			}
		}
		System.out.println("-- regCustomer Test End --");
	}

	@Test(priority = 4)
	public void monitoring() {
		for (int i = 13; i <= 19; i++) {
			if (i == 16) {
				$(".accordion-toggle", 3).hover();
				$(".sub_m", i).click();
				$(".gui-input", 0).setValue("2017-12-20");
				$(".ladda-button").click();
			} else if (i == 17) {
				// 조회된 내역이 없습니다.
			} else {
				$(".accordion-toggle", 3).hover();
				$(".sub_m", i).click();
				if (i == 13 || i == 19) {
					elementText("not", "normal", "td", "데이터가 없습니다.");
				} else {
					// 데이터 없어서 로딩 기다릴 필요 X
				}
				$(By.name("keyword")).setValue("9999999999");
				$(".ladda-button").click();
			}
		}
		System.out.println("-- Monitoring Test End --");
	}

	@Test(priority = 5)
	public void contents() {
		for (int i = 20; i <= 24; i++) {
			$(".accordion-toggle", 4).hover();
			$(".sub_m", i).click();
			$(By.name("keyword")).setValue("9999999999");
			if (i == 20) {
				$("#search-btn").click();
			} else {
				$(".ladda-button").click();
			}
			if (i >= 23) {
				elementText("have", "normal", "td", "데이터가 없습니다.");
			}
		}
		System.out.println("-- Contents Test End --");
	}

	@Test(priority = 6)
	public void setting() {
		$(".accordion-toggle", 5).hover();
		$(".sub_m", 25).click();
		for (int x = 0; x <= 5; x++) {
			$(".del", 1).click();
			System.out.println(x + " 번째 메뉴 삭제");
		}
		$(".save").click();
		$(".modal-content").waitUntil(visible, 3000);
		$("#btn-modal-alert-yes").click();
		$(".btn-default", 1).click();
		$(".form-group", 0).dragAndDropTo($(".favorite-menu"));
		$(".form-group", 1).dragAndDropTo($(".favorite-menu"));
		$(".form-group", 2).dragAndDropTo($(".favorite-menu"));
		$(".form-group", 3).dragAndDropTo($(".favorite-menu"));
		$(".form-group", 7).dragAndDropTo($(".favorite-menu"));
		$(".form-group", 0).dragAndDropTo($(".favorite-menu"));
		$(".save").click();
		$(".modal-content").waitUntil(visible, 3000);
		$("#btn-modal-alert-yes").click();
		$(".btn-default", 1).click();
		System.out.println("-- Setting Menu Test End --");
		$(".save").waitUntil(visible, 1000);
		$(".accordion-toggle", 5).hover();
		$(".sub_m", 26).click();
		$(By.name("userLoginPwd")).setValue(pw);
		$(".btn-sm").click();
		$(".modal-content").waitUntil(visible, 3000);
		$("#btn-modal-alert-yes").click();
		$(".btn-default", 1).click();
		System.out.println("-- Setting User Test End --");
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