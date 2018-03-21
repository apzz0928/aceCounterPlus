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

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class signUp {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, number, domain;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "nhnace";
		pw = "qordlf!@34";
		pw1 = "qordlf12#$";
		number = "0007";
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

	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
	public static void alertCheck(String check1, String check2, int i) throws InterruptedException {
		String alertCheck = "";
		if(check1.equals("�Է�")) {
			alertCheck = $("p", i).text();
			Thread.sleep(1000);
			if(alertCheck.equals(check2 + " �Է����ּ���.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " ���Է� üũ ���� *** ");
			} else {
				System.out.println(" *** " + check2 + " ���Է� üũ ���� *** ");
				close();
			}
		} else if (check1.equals("��ȿ��")) {
			alertCheck = $("p", i).text();
			Thread.sleep(1000);
			if(alertCheck.equals("�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " ��ȿ�� üũ ���� *** ");
			} else {
				System.out.println(" *** " + check2 + " ��ȿ�� üũ ���� *** ");
				close();
			}
		}
	}
	
	//@Test(priority = 0)
	public void login() {
		open(baseUrl);
		String signUp = $(".go_signup").text();
		if(signUp.contentEquals("ȸ������")) {
			System.out.println(" ȸ������ ��ư Ȯ�� �Ϸ�! ");
			$(".go_signup").click();		
		} else {
			System.out.println(" ȸ������ ��ư�� ã�� �� �����ϴ�! ");
		}
		$("input", 1).click();
		js("$('#using').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
		js("$('#info').click();");
		$("#stepOneCompleted").scrollIntoView(true);
		$("#userid").setValue(id + number);
		$("#recheck").click();
		$("#userpw").setValue(pw);
		$("#repeatpw").setValue(pw);
		$("#usernm").setValue(id + number);
		$("#useremail").setValue("apzz0928@nate.com");
		js("$('#news').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
		$("#mp2").setValue("9743");
		$("#mp3").setValue("0928");
		$("#stepOneCompleted").click();
		String stepOne = $(".request").text(); 
		if(stepOne.equals("�� ���� ������ �����Ű���? �� �´� ���񽺸� ��õ�� �帳�ϴ�.��õ �ޱ�")) {
			System.out.println(" *** ȸ������ 1�ܰ� ������� �� ȸ������ �Է� ���� *** ");			
		} else {
			System.out.println(" *** ȸ������ 1�ܰ� ���� *** ");
			close();
		}
		$(By.name("input-domain")).setValue(domain + number + ".com");
		$(".ace-btn-add-domain").waitUntil(visible, 1000);
		$(By.name("largeCategoryCd")).selectOptionByValue("22");
		$(By.name("middleCategoryCd")).selectOptionByValue("188");
		$(".ace-btn-add-domain").click(); // ������ �̵� �ӵ������� ������ üũ�� ���߿�
		$("#stepTwoCompleted").click();
		String stepTwo = $("h3").text();
		if(stepTwo.equals("ȸ������(����ü��) ��û�� �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** ȸ������ 2�ܰ� ���� ���� �Է�, ȸ������ 3�ܰ� ���� ��û �Ϸ� *** ");			
		} else {
			System.out.println(" *** ȸ������ 2�ܰ� ���� *** ");
			close();
		}
		$(".btn_join").click();
		$(".dropdown-toggle", 3).click();
		$(".btn-logout").click();
	}
	
	//@Test(priority = 1)
	public void changePassword() {
		open(baseUrl);
		$("#uid").setValue(id + number);
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** �α��� ���� *** ");
		} else {
			System.out.println(" *** �α׾ƿ� ��ư�� ã�� �� �����ϴ�. �α��� ���θ� Ȯ�����ּ��� *** ");
			close();
		}
		$(".go_setting").click();
		$(By.linkText("ȸ������")).click();
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		System.out.println(" *** ��й�ȣ ���� ������ ���� ���� *** ");
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		String mbn = $(".mbn").text();
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
			$("#okButton").click();
			System.out.println(" *** ��й�ȣ ���� ���� *** ");
		} else {
			System.out.println(" *** ��й�ȣ ���� ���� *** ");
			close();
		}
		$("#prePwd").setValue(pw1);
		$("#changePwd").setValue(pw);
		$("#changePwdConfirm").setValue(pw);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
			$("#okButton").click();
			System.out.println(" *** ��й�ȣ ���� ���� *** ");
		} else {
			System.out.println(" *** ��й�ȣ ���� ���� *** ");
			close();
		}
		$(".dropdown-toggle", 3).click();
		$(".btn-logout").click();
	}
	@Test(priority = 2)
	public void addMarketing() throws InterruptedException {
		open(baseUrl);
		$("#uid").setValue(id + number);
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** �α��� ���� *** ");
		} else {
			System.out.println(" *** �α׾ƿ� ��ư�� ã�� �� �����ϴ�. �α��� ���θ� Ȯ�����ּ��� *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadingCheck = $(".ace-svc-name").text();
		if(pageLoadingCheck.equals(domain + number + ".com")) {
			System.out.println(" *** ��躸�� ������ ���� ���� *** ");
		} else {
			System.out.println(" *** ��躸�� ������ ���� ���� *** ");
			close();
		}
		$("#redirectConfBtn").click();
		pageLoadingCheck = $(".notokr-bold").text();
		if(pageLoadingCheck.equals("������ ���� ����")) {
			System.out.println(" *** ������ ���� ���� ������ ���� ���� *** ");
		} else {
			System.out.println(" *** ������ ���� ���� ������ ���� ���� *** ");
			close();
		}
		$("#inflowAddBtn").click();
		js("$('#channelDcd2').click();");
		$("#btnReg").click();
		alertCheck("�Է�", "ķ���θ���", 4);
		$("#campaign_nm").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "ķ���θ�", 5);
		$("#campaign_nm").setValue(domain + number + ".com");
		$("#btnReg").click();
		alertCheck("�Է�", "���縦", 6);
		$("#campaign_material_value0").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "����", 7);
		$("#campaign_material_value0").setValue(domain + number + ".com");
		$("#btnReg").click();
		//alertCheck("�Է�", "���� URL�� ", 8); alert ������ �ϼ���. �� ����ȭ ����Ŵ ���� ���� �ʿ�
		String msgCheck = $("p", 8).text();
		if(msgCheck.equals("���� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 9).click();
			System.out.println(" *** ���� URL ���Է� üũ ���� *** ");
		} else {
			System.out.println(" *** ���� URL ���Է� üũ ���� *** ");
			close();
		}
		$("#original_url0").setValue("a");
		$("#btnReg").click();
		msgCheck = $("p", 9).text();
		if(msgCheck.equals("�ùٸ� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 10).click();
			System.out.println(" *** ���� URL ��ȿ�� üũ ���� *** ");
		} else {
			System.out.println(" *** ���� URL ��ȿ�� üũ ���� *** ");
			close();
		}
		$("#original_url0").setValue(domain + number + ".com");
		$("#btnReg").click();
		msgCheck = $("p", 10).text();
		if(msgCheck.equals("��Ͽ� �����߽��ϴ�.")) {
			$(".btn-sm", 11).click();
			System.out.println(" *** ������ ���� ���� �߰� ���� *** ");
		} else {
			System.out.println(" *** ������ ���� ���� �߰� ���� *** ");
			close();
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}