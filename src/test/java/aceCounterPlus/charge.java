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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class charge {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;

	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
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
			//driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
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

	@SuppressWarnings("unused")
	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
	public static void validationCheck(int pTagNum, int btnNum, String val) {
		String msgCheck = $("p", pTagNum).text();
		switch(val) {
		case "service_select_next":
			checkMsg = "���񽺸� ������ �ּ���.";
			break;
		case "businessLicense_input_submit":
			checkMsg = "����ڵ�Ϲ�ȣ�� �Է��� �ּ���.";
			break;
		case "businessLicense_input_number_submit":
			checkMsg = "���ڸ� �Է��� �� �ֽ��ϴ�.";
			break;
		}
		$(".modal-backdrop").waitUntil(visible, 15000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + "-btn validation check Success !! *** ");
			$(".btn-sm", btnNum).click();
			if(val.equals("saveCheck")) {
				System.out.println(" *** summary report mail send Success !! *** ");
			}
		} else {
			System.out.println(" *** " + val + "-btn validation check Fail !! *** ");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			close();
		}
	}

	@Test(priority = 0)
	public void extensionCharge() {
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail !! *** ");
			close();
		}
		$(".go_setting").click();
		$(By.linkText("������")).click();
		$(".nav-tabs").waitUntil(visible, 5000);
		String panel = $(".panel-title", 0).text();
		if(panel.equals("1���񽺼���")) {
			System.out.println(" *** extension charge page access Success !! *** ");
		} else {
			System.out.println(" *** extension charge page access Fail !! *** ");
			close();
		}
		js("window.scrollTo(0,2000)");
		$("#btn_step1_next").click();
		validationCheck(23, 4, "service_select_next");
		js("window.scrollTo(0,0)");
		$("#checkbox_step1_0").click();
		js("window.scrollTo(0,2000)");
		$("#btn_step1_next").click();
		$(".modal-backdrop").waitUntil(visible, 3000);
		$(".btn-dark", 0).click();
		$("#btn_step2_next").click();
		//����â�߸� �������̵尡 �ȸԾ �ּ�ó����
		/*$(".btn-payment").click();
		$("#blockOverlayID").waitUntil(visible, 3000);
		$("#cancel_kcp_payifr").click();
		$("#blockOverlayID").waitUntil(disabled, 3000);*/
		System.out.println(" *** extension charge page Test Success !! *** ");
	}
	@Test(priority = 1)
	public void addCharge() throws InterruptedException {
		Thread.sleep(2000);
		//$(By.linkText("�߰����")).click();
		open("https://new.acecounter.com//manage/charge/additionalCharge");
		$(".nav-tabs").waitUntil(visible, 5000);
		$("#btn-next-step").click();
		validationCheck(15, 4, "service_select_next");
		System.out.println(" *** add charge page Test Success !! *** ");
	}
	@Test(priority = 2)
	public void inquiryCharge() {
		$(By.linkText("����������ȸ")).click();
		$(".nav-tabs").waitUntil(visible, 5000);
		$(".opened", 0).click();
		System.out.println(" *** inquiry charge page Test Success !! *** ");		
	}
	@Test(priority = 3)
	public void bill() {
		$(By.linkText("��꼭")).click();
		$(".nav-tabs").waitUntil(visible, 5000);
		$("#btn-business-info").click();
		$(".mt20").waitUntil(visible, 3000);
		$("#btn-submit").click();
		validationCheck(7, 3, "businessLicense_input_submit");
		$(By.name("businessRegistNoFirstPosNo")).setValue("������");
		$("#btn-submit").click();
		validationCheck(8, 4, "businessLicense_input_number_submit");
		
		$(By.name("businessRegistNoFirstPosNo")).setValue("999");
		$(By.name("businessRegistNoMidPosNo")).setValue("99");
		$(By.name("businessRegistNoEndPosNo")).setValue("99999");
		System.out.println(" *** inquiry charge page Test Success !! *** ");		
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}