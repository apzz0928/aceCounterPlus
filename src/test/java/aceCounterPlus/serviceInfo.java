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

public class serviceInfo {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;

	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // 보안설정 변경
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
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
		if(val.equals("website_sub")) {
			checkMsg = "웹사이트 이름을 입력해주세요.";
		} else if (val.equals("domain_input_sub")) {
			checkMsg = "등록된 도메인이 없습니다.";
		} else if (val.equals("domain_input_sm")) {
			checkMsg = "도메인을 입력하세요.";
		} else if (val.equals("domain_form_sm")) {
			checkMsg = "도메인 형식이 올바르지 않습니다.";
		} else if (val.equals("email_input_sendbtn")) {
			checkMsg = "올바른 이메일을 입력하세요.";
		} else if (val.equals("email_input_sub")) {
			checkMsg = "수신 이메일을 추가해 주세요.";
		} else if (val.equals("email_send_sub")) {
			checkMsg = "요약리포트가 발송되었습니다.";
		} else if (val.equals("email_h1_check")) {
			checkMsg = "주간요약 리포트입니다.";
		}
		$(".modal-backdrop").waitUntil(visible, 15000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + "-btn validation check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + "-btn validation check Fail !! *** ");
			close();
		}
	}

	@Test(priority = 0)
	public void modifyInfo() throws InterruptedException {
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail !! *** ");
			close();
		}
		$(".go_setting").click();
		$(By.linkText("정보수정")).click();
		String btninfo = $(".btn-info", 0).text();
		if(btninfo.equals("서비스 추가")) {
			System.out.println(" *** modify Info page access Success !! *** ");
		} else {
			System.out.println(" *** modify Info page access Fail !! *** ");
			close();
		}
		$(".collapsed", 0).click();
		$(".ace-btn-edit").click();
		String msgCheck = $("p", 120).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("변경된 정보가 없습니다.")) {
			System.out.println(" *** don't InfoModify check Success !! *** ");
			$(".btn-sm", 27).click();
		} else {
			System.out.println(" *** don't InfoModify check Fail !! *** ");
			close();
		}
		$(".gui-input", 0).setValue("");
		$(".ace-btn-remove", 0).click();
		$(".ace-btn-remove", 0).click();
		$(".ace-btn-edit").click();
		validationCheck(121, 28, "website_sub");
		$(".gui-input", 0).setValue(number);
		$(".ace-btn-edit").click();
		validationCheck(122, 29, "domain_input_sub");
		$(".ace-btn-add-domain", 0).click();
		validationCheck(123, 30, "domain_input_sm");
		$(".gui-input", 1).setValue(id+number);
		$(".ace-btn-add-domain", 0).click();
		validationCheck(124, 31, "domain_form_sm");
		$(".gui-input", 1).setValue(id+number + ".com");
		$(".ace-btn-add-domain", 0).click();
		$(".gui-input", 1).setValue("www." + id+number + ".com");
		$(".ace-btn-add-domain", 0).click();
		$(".ace-btn-edit").click();
		msgCheck = $("p", 125).text();
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals("수정이 완료되었습니다.")) {
			System.out.println(" *** modify info check Success !! *** ");
			$(".btn-sm", 32).click();
		} else {
			System.out.println(" *** modify info check Fail !! *** ");
			close();
		}
	}
	@Test(priority = 1)
	public void sendmailSetting() {
		$(By.linkText("발송메일 설정")).click();
		String mt10 = $(".mt10").text();
		if(mt10.equals("* 표시는 필수 입력")) {
			System.out.println(" *** sendmailSetting page access Success !! *** ");
		} else {
			System.out.println(" *** sendmailSetting page access Fail !! *** ");
			close();
		}
		$(".btn-del-sendEmail").click();
		$("#btn-sendEmail").click();
		validationCheck(6, 5,"email_input_sendbtn");
		$("#btn-sendMail").click();
		validationCheck(7, 6,"email_input_sub");
		$(".gui-input").setValue("apzz0928@nhnent");
		$("#btn-sendEmail").click();
		validationCheck(8, 7, "email_input_sendbtn");
		$(".gui-input").setValue("apzz0928@nhnent.com");
		$("#btn-sendEmail").click();
		$("#btn-sendMail").click();
		validationCheck(9, 8, "email_send_sub");
		open("https://nhnent.dooray.com/mail/new");
		
		$(".one-line-block").click();
		String mailCheck = $("h1").text();
		if(mailCheck.equals("주간요약 리포트입니다.")) {
			System.out.println(" *** mail Title check Success !! *** ");
		} else {
			System.out.println(" *** mail Title check Fail !! *** ");
			close();
		}
		back();
		
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}