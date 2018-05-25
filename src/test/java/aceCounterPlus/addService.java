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
	public static void valCheck(int pTagNum, int btnNum, String val) {
		String msgCheck = $("p", pTagNum).text();
		if(val.equals("website_sub")) {
			checkMsg = "������Ʈ �̸��� �Է����ּ���.";
		} else if (val.equals("domain_input_sub")) {
			checkMsg = "��ϵ� �������� �����ϴ�.";
		} else if (val.equals("domain_input_sm")) {
			checkMsg = "�������� �Է��ϼ���.";
		} else if (val.equals("domain_form_sm")) {
			checkMsg = "������ ������ �ùٸ��� �ʽ��ϴ�.";
		} else if (val.equals("website_form_sub")) {
			checkMsg = "������Ʈ �з��� �������ּ���.";
		}
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + "-btn validation check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + "-btn validation check Fail !! *** ");
			close();
		}
	}
	@Test(priority = 0)
	public void serviceAdd() throws InterruptedException {
		open(baseUrl);
		//$("#uid").setValue(id + number);
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
		$(By.linkText("�����߰�")).click();
		String p4 = $("p", 4).text();
		Thread.sleep(1000);
		if(p4.equals("* ǥ�ô� �ʼ� �Է�")) {
			System.out.println(" *** addService page access Success !! *** ");
		} else {
			System.out.println(" *** addService page access Fail !! *** ");
			close();
		}
		$("#btn_submit").click();
		valCheck(10, 5, "website_sub");
		String msgCheck = $("p", 10).text();
		$(By.name("svcNm")).setValue(id + number);
		$("#btn_submit").click();
		valCheck(11, 6, "domain_input_sub");
		$(".ace-btn-add-domain").click();
		valCheck(12, 7, "domain_input_sm");
		$(".gui-input", 1).setValue(id + number);
		$(".ace-btn-add-domain").click();
		valCheck(13, 8, "domain_form_sm");
		$(".gui-input", 1).setValue(id + number + ".com");
		$(".ace-btn-add-domain").click();
		$("#btn_submit").click();
		valCheck(14, 9, "website_form_sub");
	    $(By.name("largeCategoryCd")).click();
	    $(By.xpath("//option[@value='22']")).click();
		$("#btn_submit").click();
		valCheck(15, 10, "website_form_sub");
	    $(By.name("middleCategoryCd")).click();
	    $(By.xpath("//option[@value='188']")).click();
		$("#btn_submit").click();
		Thread.sleep(2000);
		msgCheck = $("h2").text();
		if(msgCheck.equals("���� �߰��� �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** addServiceComplete page access Success !! *** ");
			$(".btn-dark").click();
		} else {
			System.out.println(" *** addServiceComplete page access Fail !! *** ");
			close();
		}
		$(By.linkText("���ո���Ʈ ����")).click();
		Thread.sleep(1000);
		if(p4.equals("* ǥ�ô� �ʼ� �Է�")) {
			System.out.println(" *** integratedReport page access Success !! *** ");
		} else {
			System.out.println(" *** integratedReport page access Fail !! *** ");
			close();
		}
		$("#report_nm").setValue("���ո���Ʈ �׽�Ʈ : " + number1);
		
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}