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

public class marketingInflowSetting {
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
	public void addMarketing_add() {
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
		$(".go_stat", 1).click();
		String secondbtn = $("#secondbtn").text();
		System.out.println(secondbtn);
		if(secondbtn.equals("�ʴ���")) {
			System.out.println(" *** statsLiveDashboard Page Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page Fail !! *** ");
			close();
		}
		$("#redirectConfBtn").click();
		String pageLoadingCheck = $(".notokr-bold").text();
		if(pageLoadingCheck.equals("������ ���� ����")) {
			System.out.println(" *** appmarketing page Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page Fail !! *** ");
			close();
		}
		$("#inflowAddBtn").click();
		js("$('#channelDcd2').click();");
		$("#btnReg").click();
		alertCheck("�Է�", "ķ���θ���", 4);
		$("#campaign_nm").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "ķ���θ�", 5);
		$("#campaign_nm").setValue(domain + number + number1 + ".com");
		$("#btnReg").click();
		alertCheck("�Է�", "���縦", 6);
		$("#campaign_material_value0").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "����", 7);
		$("#campaign_material_value0").setValue(domain + number + ".com");
		$("#btnReg").click();
		//alertCheck("�Է�", "���� URL�� ", 8); alert ������ �ϼ���. �� ����ȭ ����Ŵ ���� ���� �ʿ�
		String msgCheck = $("p", 8).text();
		$("p", 8).click();
		$("p", 8).click();
		if(msgCheck.equals("���� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 9).click();
			System.out.println(" *** link URL Missing Check Success !! *** ");
		} else {
			System.out.println(" *** link URL Missing Check Fail !! *** ");
			close();
		}
		$("#original_url0").setValue("a");
		$("#btnReg").click();
		msgCheck = $("p", 9).text();
		$("p", 9).click();
		$("p", 9).click();
		if(msgCheck.equals("�ùٸ� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 10).click();
			System.out.println(" *** link URL validation check Success !! *** ");
		} else {
			System.out.println(" *** link URL validation check Fail !! *** ");
			close();
		}
		$("#original_url0").setValue(domain + number + ".com");
		$("#btnReg").click();
		msgCheck = $("p", 10).text();
		$("p", 10).click();
		$("p", 10).click();
		if(msgCheck.equals("��Ͽ� �����߽��ϴ�.")) {
			$(".btn-sm", 11).click();
			System.out.println(" *** Add Marketing Inflow settings Success !! *** ");
		} else {
			System.out.println(" *** Add Marketing Inflow settings Fail !! *** ");
			close();
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}