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
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, number1, domain;

	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("MMddhhmmss");
    String number = number_format.format(number_date);
    
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
		number1 = "00";

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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // 보안설정 변경
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}

	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
	
	public static void alertCheck(String check1, String check2, int i) {
		String alertCheck = "";
		if(check1.equals("입력")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals(check2 + " 입력해주세요.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " missing check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " missing check Fail *** ");
				close();
			}
		} else if (check1.equals("유효성")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals("한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " validation check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " validation check Fail *** ");
				close();
			}
		}
	}
	
	@Test(priority = 0)
	public void login() {
		open(baseUrl);
		$(".go_signup").waitUntil(visible, 8000);
		String signUp = $(".go_signup").text();
		if(signUp.contentEquals("회원가입")) {
			System.out.println(" SignUp Button check Success!! ");
			$(".go_signup").click();		
		} else {
			System.out.println(" SignUp Button check Fail !! ");
		}
		$("input", 1).click();
		js("$('#using').click();"); // 체크박스 체크가 안먹어서 js로
		js("$('#info').click();");
		$("#stepOneCompleted").scrollIntoView(true);
		$("#userid").setValue(id + number);
		$("#recheck").click();
		$("#userpw").setValue(pw);
		$("#repeatpw").setValue(pw);
		$("#usernm").setValue(id + number);
		$("#useremail").setValue("apzz0928@nate.com");
		js("$('#news').click();"); // 체크박스 체크가 안먹어서 js로
		$("#mp2").setValue("9743");
		$("#mp3").setValue("0928");
		$("#stepOneCompleted").click();
		String stepOne = $(".request").text(); 
		if(stepOne.equals("※ 서비스 선택이 어려우신가요? 딱 맞는 서비스를 추천해 드립니다.추천 받기")) {
			System.out.println(" *** SignUp Step.1 Success !! *** ");			
		} else {
			System.out.println(" *** SignUp Step.1 Fail !! *** ");
			close();
		}
		$(By.name("input-domain")).setValue(domain + number + ".com");
		$(".ace-btn-add-domain").waitUntil(visible, 1000);
		$(By.name("largeCategoryCd")).selectOptionByValue("22");
		$(By.name("middleCategoryCd")).selectOptionByValue("188");
		$(".ace-btn-add-domain").click(); // 페이지 이동 속도때문에 도메인 체크를 나중에
		$("#stepTwoCompleted").click();
		String stepTwo = $("h3").text();
		if(stepTwo.equals("회원가입(무료체험) 신청이 완료되었습니다.")) {
			System.out.println(" *** SignUp Step.2 Success !! *** ");
			System.out.println("ID is : " + id + number);
		} else {
			System.out.println(" *** SignUp Step.1 Fail !! *** ");
			close();
		}
		$(".btn_join").click();
		$(".dropdown-toggle", 3).click();
		$(".btn-logout").click();
	}
	
	@Test(priority = 1)
	public void changePassword() {
		open(baseUrl);
		$("#uid").setValue(id + number);
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
		$(By.linkText("회원정보")).click();
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		System.out.println(" *** Password change Page access Success !! *** ");
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		String mbn = $(".mbn").text();
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
			$("#okButton").click();
			System.out.println(" *** change Password Success !! *** ");
		} else {
			System.out.println(" *** change Password Fail !! *** ");
			close();
		}
		$("#prePwd").setValue(pw1);
		$("#changePwd").setValue(pw);
		$("#changePwdConfirm").setValue(pw);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
			$("#okButton").click();
			System.out.println(" *** change Password Success !! *** ");
		} else {
			System.out.println(" *** change Password Fail !! *** ");
			close();
		}
		$(".dropdown-toggle", 3).click();
		$(".btn-logout").click();
	}
	@Test(priority = 2)
	public void addMarketing_add() {
		open(baseUrl);
		$("#uid").setValue(id + number);
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
		$(".go_stat", 1).click();
		String pageLoadingCheck = $(".ace-svc-name").text();
		if(pageLoadingCheck.equals(domain + number + ".com")) {
			System.out.println(" *** statsLiveDashboard Page Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page Fail !! *** ");
			close();
		}
		$("#redirectConfBtn").click();
		pageLoadingCheck = $(".notokr-bold").text();
		if(pageLoadingCheck.equals("마케팅 유입 설정")) {
			System.out.println(" *** appmarketing page Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page Fail !! *** ");
			close();
		}
		$("#inflowAddBtn").click();
		js("$('#channelDcd2').click();");
		$("#btnReg").click();
		alertCheck("입력", "캠페인명을", 4);
		$("#campaign_nm").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("유효성", "캠페인명", 5);
		$("#campaign_nm").setValue(domain + number + number1 + ".com");
		$("#btnReg").click();
		alertCheck("입력", "소재를", 6);
		$("#campaign_material_value0").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("유효성", "소재", 7);
		$("#campaign_material_value0").setValue(domain + number + ".com");
		$("#btnReg").click();
		//alertCheck("입력", "연결 URL을 ", 8); alert 문구가 하세요. 라서 공통화 못시킴 문구 통일 필요
		String msgCheck = $("p", 8).text();
		$("p", 8).click();
		$("p", 8).click();
		if(msgCheck.equals("연결 URL을 입력하세요.")) {
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
		if(msgCheck.equals("올바른 URL을 입력하세요.")) {
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
		if(msgCheck.equals("등록에 성공했습니다.")) {
			$(".btn-sm", 11).click();
			System.out.println(" *** Add Marketing Inflow settings Success !! *** ");
		} else {
			System.out.println(" *** Add Marketing Inflow settings Fail !! *** ");
			close();
		}
	}
	@Test(priority = 3)
	public void addMarketing_del() {
		String pageLoadingCheck = $(".notokr-bold").text();
		if(pageLoadingCheck.equals("마케팅 유입 설정")) {
			System.out.println(" *** appmarketing page Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page Fail !! *** ");
			close();
		}
		$("#deleteViewBtn").click();
		$("#deleteBtn").click();
		String msgCheck = $("p", 3).text();
		$("p", 3).click();
		$("p", 3).click();
		System.out.println(msgCheck);
		if(msgCheck.equals("삭제할 마케팅 유입 설정을 선택하세요.")) {
			$(".btn-sm", 4).click();
			System.out.println(" *** marketing delete validation check Sueecss !! *** ");
		} else {
			System.out.println(" *** marketing delete validation check Fail !! *** ");
			close();
		}
		$("#checkAllCamp").click();
		$("#deleteBtn").click();
		msgCheck = $("p", 4).text();
		$("p", 4).click();
		$("p", 4).click();
		if(msgCheck.equals("선택한 마케팅 유입 설정을 삭제하시겠습니까?\n" + 
				"마케팅 유입설정 변수에 대해 수집/분석이 중지되며,\n" + 
				"삭제 후 복구가 불가능합니다.")) {
			$(".btn-sm", 6).click();
			System.out.println(" *** marketing delete check Sueecss !! *** ");
		} else {
			System.out.println(" *** marketing delete check Fail !! *** ");
			//close();
		}
		System.out.println(" *** !! *** ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}