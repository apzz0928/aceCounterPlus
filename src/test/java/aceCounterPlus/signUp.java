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
	public static void alertCheck(String check1, String check2, int i) throws InterruptedException {
		String alertCheck = "";
		if(check1.equals("입력")) {
			alertCheck = $("p", i).text();
			Thread.sleep(1000);
			if(alertCheck.equals(check2 + " 입력해주세요.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " 미입력 체크 성공 *** ");
			} else {
				System.out.println(" *** " + check2 + " 미입력 체크 실패 *** ");
				close();
			}
		} else if (check1.equals("유효성")) {
			alertCheck = $("p", i).text();
			Thread.sleep(1000);
			if(alertCheck.equals("한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " 유효성 체크 성공 *** ");
			} else {
				System.out.println(" *** " + check2 + " 유효성 체크 실패 *** ");
				close();
			}
		}
	}
	
	//@Test(priority = 0)
	public void login() {
		open(baseUrl);
		String signUp = $(".go_signup").text();
		if(signUp.contentEquals("회원가입")) {
			System.out.println(" 회원가입 버튼 확인 완료! ");
			$(".go_signup").click();		
		} else {
			System.out.println(" 회원가입 버튼을 찾을 수 없습니다! ");
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
			System.out.println(" *** 회원가입 1단계 약관동의 및 회원정보 입력 성공 *** ");			
		} else {
			System.out.println(" *** 회원가입 1단계 실패 *** ");
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
			System.out.println(" *** 회원가입 2단계 서비스 정보 입력, 회원가입 3단계 서비스 신청 완료 *** ");			
		} else {
			System.out.println(" *** 회원가입 2단계 실패 *** ");
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
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** 로그인 성공 *** ");
		} else {
			System.out.println(" *** 로그아웃 버튼을 찾을 수 없습니다. 로그인 여부를 확인해주세요 *** ");
			close();
		}
		$(".go_setting").click();
		$(By.linkText("회원정보")).click();
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		System.out.println(" *** 비밀번호 수정 페이지 접근 성공 *** ");
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		String mbn = $(".mbn").text();
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
			$("#okButton").click();
			System.out.println(" *** 비밀번호 변경 성공 *** ");
		} else {
			System.out.println(" *** 비밀번호 변경 실패 *** ");
			close();
		}
		$("#prePwd").setValue(pw1);
		$("#changePwd").setValue(pw);
		$("#changePwdConfirm").setValue(pw);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
			$("#okButton").click();
			System.out.println(" *** 비밀번호 원복 성공 *** ");
		} else {
			System.out.println(" *** 비밀번호 원복 실패 *** ");
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
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** 로그인 성공 *** ");
		} else {
			System.out.println(" *** 로그아웃 버튼을 찾을 수 없습니다. 로그인 여부를 확인해주세요 *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadingCheck = $(".ace-svc-name").text();
		if(pageLoadingCheck.equals(domain + number + ".com")) {
			System.out.println(" *** 통계보기 페이지 접근 성공 *** ");
		} else {
			System.out.println(" *** 통계보기 페이지 접근 실패 *** ");
			close();
		}
		$("#redirectConfBtn").click();
		pageLoadingCheck = $(".notokr-bold").text();
		if(pageLoadingCheck.equals("마케팅 유입 설정")) {
			System.out.println(" *** 마케팅 유입 설정 페이지 접근 성공 *** ");
		} else {
			System.out.println(" *** 마케팅 유입 설정 페이지 접근 실패 *** ");
			close();
		}
		$("#inflowAddBtn").click();
		js("$('#channelDcd2').click();");
		$("#btnReg").click();
		alertCheck("입력", "캠페인명을", 4);
		$("#campaign_nm").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("유효성", "캠페인명", 5);
		$("#campaign_nm").setValue(domain + number + ".com");
		$("#btnReg").click();
		alertCheck("입력", "소재를", 6);
		$("#campaign_material_value0").setValue(domain + number + ".com@");
		$("#btnReg").click();
		alertCheck("유효성", "소재", 7);
		$("#campaign_material_value0").setValue(domain + number + ".com");
		$("#btnReg").click();
		//alertCheck("입력", "연결 URL을 ", 8); alert 문구가 하세요. 라서 공통화 못시킴 문구 통일 필요
		String msgCheck = $("p", 8).text();
		if(msgCheck.equals("연결 URL을 입력하세요.")) {
			$(".btn-sm", 9).click();
			System.out.println(" *** 연결 URL 미입력 체크 성공 *** ");
		} else {
			System.out.println(" *** 연결 URL 미입력 체크 실패 *** ");
			close();
		}
		$("#original_url0").setValue("a");
		$("#btnReg").click();
		msgCheck = $("p", 9).text();
		if(msgCheck.equals("올바른 URL을 입력하세요.")) {
			$(".btn-sm", 10).click();
			System.out.println(" *** 연결 URL 유효성 체크 성공 *** ");
		} else {
			System.out.println(" *** 연결 URL 유효성 체크 실패 *** ");
			close();
		}
		$("#original_url0").setValue(domain + number + ".com");
		$("#btnReg").click();
		msgCheck = $("p", 10).text();
		if(msgCheck.equals("등록에 성공했습니다.")) {
			$(".btn-sm", 11).click();
			System.out.println(" *** 마케팅 유입 설정 추가 성공 *** ");
		} else {
			System.out.println(" *** 마케팅 유입 설정 추가 실패 *** ");
			close();
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}