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

public class changeMemberInfo {
	@SuppressWarnings("unused")
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
	
	public static void alertCheck(String check1, String check2, int i) {
		String alertCheck = "";
		if(check1.equals("�Է�")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals(check2 + " �Է����ּ���.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " missing check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " missing check Fail *** ");
				close();
			}
		} else if (check1.equals("��ȿ��")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals("�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " validation check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " validation check Fail *** ");
				close();
			}
		}
	}
	@Test(priority = 0)
	public void memberInfoChange() throws InterruptedException {
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
		$(By.linkText("ȸ������")).click();
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		System.out.println(" *** MemberInfo change Page access Success !! *** ");
		$("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='011']")).click();
	    $("#s_hp2").setValue("0928");
	    $("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@naver.com");
	    $(".btn-lg", 1).click();
	    String modalBody = $(".modal-body").text();
	    $(".modal-dialog").waitUntil(visible, 3000);
		if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
			$(".btn-sm", 4).click();
			System.out.println(" *** change memberInfo Success !! *** ");
		} else {
			System.out.println(" *** change memberInfo Fail !! *** ");
			close();
		}
		$(".nav-tabs").waitUntil(visible, 3000);
		$("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928@gmail.com");
	    $(".btn-lg", 1).click();
	    $(".modal-dialog").waitUntil(visible, 3000);
		if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
			$(".btn-sm", 4).click();
			System.out.println(" *** Restoration memberInfo Success !! *** ");
		} else {
			System.out.println(" *** Restoration memberInfo Fail !! *** ");
			close();
		}
		$(".nav-tabs").waitUntil(visible, 3000);
		$(".dropdown-toggle", 3).click();
		$(".btn-logout", 0).click();
		System.out.println(" *** Logout Success !! *** ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}