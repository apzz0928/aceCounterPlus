package aceCounterPlus; //ȸ������ ����

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

public class memberInfo {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain;

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
		/*ScreenShooter.captureSuccessfulTests = true;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			//cap = DesiredCapabilities.firefox();
			//RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*EdgeOptions options = new EdgeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
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
	
  	public static void sleep(long millis) {
  		try {
  			Thread.sleep(millis);
  		} catch (InterruptedException ex) {
  		}
  	}
  	
	@Test(priority = 0)
	public void memberInfoChange() {
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String pageLoadCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(pageLoadCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_setting").click();
		$(".notokr-bold").waitUntil(visible, 10000);
		$(By.linkText("ȸ������")).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("��й�ȣ ��Ȯ��")) {
			System.out.println(" *** memberInfo Recongirming page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo Recongirming page load Fail ... !@#$%^&*() *** ");			
		}
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		$("h3", 2).waitUntil(visible, 10000);
		System.out.println(" *** Password change Page access Success !! *** ");
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		$("#btn-modal-alert-yes").click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		String mbn = $(".mbn").text();
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** Change Password Success !! *** ");
			$("#okButton").click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** Change Password Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#prePwd").setValue(pw1);
		$("#changePwd").setValue(pw);
		$("#changePwdConfirm").setValue(pw);
		$("#modifyProc").click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		$("#btn-modal-alert-yes").click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** Restoration Password Success !! *** ");
			$("#okButton").click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** Restoration Password Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='011']")).click();
	    $("#s_hp2").setValue("0928");
	    $("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@naver.com");
	    $(".btn-lg", 1).click();
	    sleep(1000);
	    String modalBody = $(".modal-body", 1).text();
		$(".modal-backdrop").waitUntil(visible, 10000);
		if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
			$(".btn-sm", 5).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
			System.out.println(" *** change memberInfo Success !! *** ");
		} else {
			System.out.println(" *** change memberInfo Fail ... !@#$%^&*() *** ");
			close();
		}
	    sleep(2000);
	    $("#s_name").waitUntil(visible, 10000);
	    $("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928@gmail.com");
	    $(".btn-lg", 1).click();
	    $(".modal-dialog").waitUntil(visible, 10000);
		if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
			$(".btn-sm", 4).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
			System.out.println(" *** Restoration memberInfo Success !! *** ");
		} else {
			System.out.println(" *** Restoration memberInfo Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".nav-tabs").waitUntil(visible, 10000);
		$(".dropdown-toggle", 3).click();
		$(".btn-logout", 0).click();
		System.out.println(" *** Logout Success !! *** ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}