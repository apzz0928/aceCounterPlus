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

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class signUp {
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

	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
	@Test(priority = 0)
	public void go_signup() {
		open(baseUrl);
		$(".go_signup").waitUntil(visible, 8000);
		String signUp = $(".go_signup").text();
		if(signUp.contentEquals("ȸ������")) {
			System.out.println(" SignUp Button check Success!! ");
			$(".go_signup").click();		
		} else {
			System.out.println(" SignUp Button check Fail !! ");
		}
		$("input", 1).click();
		js("$('#using').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
		js("$('#info').click();");
		$("#stepOneCompleted").scrollIntoView(true);
		$("#userid").setValue(id + number);
		$("#recheck").click();
		$("#userpw").setValue(pw);
		$("#repeatpw").setValue(pw);
		$("#usernm").setValue("ȸ�������׽�Ʈ");
		$("#useremail").setValue("apzz0928@nate.com");
		js("$('#news').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
		$("#mp2").setValue("0000");
		$("#mp3").setValue("0000");
		$("#stepOneCompleted").click();
		String stepOne = $(".request").text(); 
		if(stepOne.equals("�� ���� ������ �����Ű���? �� �´� ���񽺸� ��õ�� �帳�ϴ�.��õ �ޱ�")) {
			System.out.println(" *** SignUp Step.1 Success !! *** ");			
		} else {
			System.out.println(" *** SignUp Step.1 Fail !! *** ");
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
			System.out.println(" *** SignUp Step.2 Success !! *** ");
			System.out.println("ID is : " + id + number);
		} else {
			System.out.println(" *** SignUp Step.2 Fail !! *** ");
			close();
		}
		$(".btn_join").click();
		$(".dropdown-toggle", 3).click();
		$(".btn-logout").click();
		System.out.println(" *** SignUp *** Success !! *** ");
		$("#uid").setValue(id + number);
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
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}