package aceCounterPlus;

import java.io.IOException;
import java.net.HttpURLConnection;
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

import com.codeborne.selenide.testng.ScreenShooter;

public class temporarily {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;

	// 신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
	SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
	SimpleDateFormat number_format1 = new SimpleDateFormat("yyyy-MM-dd");
	String date = number_format.format(number_date);
	String date1 = number_format1.format(number_date);

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
			/*
			 * ChromeOptions options = new ChromeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			// cap = DesiredCapabilities.firefox();
			// RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*
			 * EdgeOptions options = new EdgeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*
			 * InternetExplorerOptions options = new InternetExplorerOptions(); driver = new
			 * RemoteWebDriver(new URL(urlToRemoteWD), options);
			 */
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

	public static void valCheck(int pTagNum, int btnNum, String val) {
		$(".modal-backdrop").waitUntil(visible, 10000);
		String msgCheck = $("p", pTagNum).text();
		switch (val) {
		case "scriptList_email_null":
			checkMsg = "수신 이메일을 입력해 주세요.";
			break;
		case "scriptList_email_validation":
			checkMsg = "이메일 주소가 올바르지 않습니다.";
			break;
		case "scriptList_email_send":
			checkMsg = "설치안내 메일이 발송되었습니다.";
			break;
		case "installApply_null":
			checkMsg = "신청할 서비스를 선택해 주세요.";
			break;
		case "installApply_name_null":
			checkMsg = "이름을 입력해 주세요.";
			break;
		case "installApply_email_null":
			checkMsg = "이메일을 입력해 주세요.";
			break;
		case "installApply_email_validation":
			checkMsg = "이메일 형식이 올바르지 않습니다.";
			break;
		case "installApply_FTP_null":
			checkMsg = "FTP정보를 입력해 주세요.";
			break;
		case "installApply_port_null":
			checkMsg = "포트번호를 입력해 주세요.";
			break;
		case "installApply_id_null":
			checkMsg = "아이디를 입력해 주세요.";
			break;
		case "installApply_pw_null":
			checkMsg = "비밀번호를 입력해 주세요.";
			break;
		case "installApply_agree_null":
			checkMsg = "개인정보 수집 및 이용에 대한 안내를 동의해 주세요.";
			break;
		case "memberInfo_checkpage_pw_null":
			checkMsg = "비밀번호를 입력하세요.";
			break;
		case "memberInfo_oldPw_null":
			checkMsg = "현재 비밀번호를 입력하세요.";
			break;
		case "memberInfo_newPw_null":
			checkMsg = "새 비밀번호를 입력하세요.";
			break;
		case "memberInfo_newPwCk_null":
			checkMsg = "새 비밀번호 확인이 필요합니다.";
			break;
		case "memberInfo_newPw_validation":
			checkMsg = "새 비밀번호가 일치하지 않습니다.";
			break;
		case "memberInfo_pw_confirm":
			checkMsg = "비밀번호를 변경 하시겠습니까?";
			break;
		case "memberInfo_pw_alert":
			checkMsg = "비밀번호 변경이 완료되었습니다.";
			break;
		case "memberInfo_pw1_confirm":
			checkMsg = "비밀번호를 변경 하시겠습니까?";
			break;
		case "memberInfo_pw1_alert":
			checkMsg = "비밀번호 변경이 완료되었습니다.";
			break;
		case "memberInfo_name_null":
			checkMsg = "담당자 이름을 입력하세요.";
			break;
		case "memberInfo_email_null":
			checkMsg = "이메일 주소를 입력하여 주세요.";
			break;
		case "memberInfo_email_validation":
			checkMsg = "이메일 주소가 올바르지 않습니다.";
			break;
		case "memberInfo_change_ok":
			checkMsg = "회원정보가 수정되었습니다.";
			break;
		case "memberInfo_change1_ok":
			checkMsg = "회원정보가 수정되었습니다.";
			break;
		case "myCoupon_1_null":
			checkMsg = "쿠폰번호를 입력해주세요.";
			break;
		case "myCoupon_2_null":
			checkMsg = "쿠폰번호를 입력해주세요.";
			break;
		case "myCoupon_3_null":
			checkMsg = "쿠폰번호를 입력해주세요.";
			break;
		case "myCoupon_4_null":
			checkMsg = "쿠폰번호를 입력해주세요.";
			break;
		case "myCoupon_5_null":
			checkMsg = "쿠폰번호가 맞지 않습니다.\n" + "다시 확인해 주세요.";
			break;
		case "editService_modify_check":
			checkMsg = "변경된 정보가 없습니다.";
			break;
		case "addService_siteName_null":
			checkMsg = "웹사이트 이름을 입력해주세요.";
			break;
		case "addService_domain_null":
			checkMsg = "등록된 도메인이 없습니다.";
			break;
		case "addService_domain_validation":
			checkMsg = "도메인 형식이 올바르지 않습니다.";
			break;
		case "addService_domain_duplication":
			checkMsg = "이미 추가된 도메인입니다.";
			break;
		case "addService_siteGroup1_null":
			checkMsg = "웹사이트 분류를 선택해주세요.";
			break;
		case "addService_siteGroup2_null":
			checkMsg = "웹사이트 분류를 선택해주세요.";
			break;
		case "addService_coupon_null":
			checkMsg = "쿠폰번호가 맞지 않습니다.\n" + "다시 확인해 주세요.";
			break;
		case "addIntegralReport_name_null":
			checkMsg = "통합리포트 이름을 입력해 주세요.";
			break;
		case "addIntegralReport_service_null":
			checkMsg = "2개 이상의 서비스를 선택해 주세요.";
			break;
		case "editService_siteName_null":
			checkMsg = "웹사이트 이름을 입력해주세요.";
			break;
		case "editService_domain_null":
			checkMsg = "등록된 도메인이 없습니다.";
			break;
		case "editService_domain_validation":
			checkMsg = "도메인 형식이 올바르지 않습니다.";
			break;
		case "editService_edit_alert":
			checkMsg = "수정이 완료되었습니다.";
			break;
		case "editService_restore_alert":
			checkMsg = "수정이 완료되었습니다.";
			break;
		case "summaryReport_sendEmail_null":
			checkMsg = "수신 이메일을 추가해 주세요.";
			break;
		case "summaryReport_sendEmail_check":
			checkMsg = "올바른 이메일을 입력하세요.";
			break;
		case "summaryReport_sendEmail_send":
			checkMsg = "요약리포트가 발송되었습니다.";
			break;
		case "summaryReport_reserveEmail_null":
			checkMsg = "수신 이메일을 추가해 주세요.";
			break;
		case "summaryReport_reserveEmail_check":
			checkMsg = "올바른 이메일을 입력하세요.";
			break;
		case "summaryReport_reserveEmail_send":
			checkMsg = "설정하신 내용이 저장되었습니다.\n" + "설정내용은 익일부터 반영됩니다.";
			break;
		case "subManager_name_null":
			checkMsg = "담당자 이름을 입력해 주세요.";
			break;
		case "subManager_email_null":
			checkMsg = "담당자 이메일을 입력해 주세요.";
			break;
		case "subManager_email_check":
			checkMsg = "이메일 형식이 올바르지 않습니다.";
			break;
		case "subManager_service_null":
			checkMsg = "서비스를 추가해 주세요.";
			break;
		case "31":
			checkMsg = "";
			break;
		case "41":
			checkMsg = "";
			break;
		case "7":
			checkMsg = "";
			break;
		case "8":
			checkMsg = "";
			break;
		case "69":
			checkMsg = "";
			break;
		case "1":
			checkMsg = "";
			break;
		case "0":
			checkMsg = "";
			break;
		case "2":
			checkMsg = "";
			break;
		case "4":
			checkMsg = "";
			break;
		case "5":
			checkMsg = "";
			break;
		case "669":
			checkMsg = "";
			break;
		case "37":
			checkMsg = "";
			break;
		case "318":
			checkMsg = "";
			break;
		case "419":
			checkMsg = "";
			break;
		case "10":
			checkMsg = "";
			break;
		case "11":
			checkMsg = "";
			break;
		case "12":
			checkMsg = "";
			break;

		}
		Thread.onSpinWait();
		if (msgCheck.equals(checkMsg)) {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val
					+ " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
			if (!val.split("_")[0].equals("scriptList")) {
				$(".modal-backdrop").waitUntil(hidden, 10000);
			} else {

			}
		} else if (msgCheck.isEmpty()) {
			System.out.println(" *** ☆★☆★☆★ pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val
					+ " - msgCheck is Empty ... ☆★☆★☆★ *** ");
			close();
		} else {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val
					+ " - check Fail ... !@#$%^&*() *** ");
			System.out.println(msgCheck);
			System.out.println("-------------------------------");
			System.out.println(checkMsg);
			close();
		}
	}

	// 입력된 URL 정상 여부 확인
	public static boolean brokenLinkCheck(String urlName, String urlLink) {
		try {
			huc = (HttpURLConnection) (new URL(urlLink).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			if (respCode >= 400) {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " *****");
				close();
			} else {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " *****");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
		}
	}

	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}

	@Test(priority = 0)
	public void serviceManage_main() {
		System.out.println(" ! ----- serviceManage_main Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if (loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_setting").click();
		$(".notokr-bold", 0).waitUntil(visible, 1000);
		String pageLoadCheck = $(".notokr-bold", 0).text();
		if (pageLoadCheck.equals("서비스 관리")) {
			System.out.println(" *** serviceManage page load Success !! *** ");
		} else {
			System.out.println(" *** serviceManage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- serviceManage_main End ----- ! ");
	}

	@Test(priority = 1)
	public void scriptList() {
		System.out.println(" ! ----- scriptList Start ----- ! ");
		$(By.linkText("분석스크립트")).click();
		$("#items").waitUntil(visible, 10000);
		$(".br-dark").click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		String pageLoadCheck = $(".modal-title").text();
		if (pageLoadCheck.equals("분석스크립트 메일발송")) {
			System.out.println(" *** scriptList layerPopup load Success !! *** ");
		} else {
			System.out.println(" *** scriptList layerPopup load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-dark", 1).click();
		valCheck(61, 3, "scriptList_email_null");
		$("#mail_to").setValue(date);
		$(".btn-dark", 1).click();
		valCheck(62, 4, "scriptList_email_validation");
		$("#mail_to").setValue("apzz0928@nhnent.com");
		$(".btn-dark", 1).click();
		$(".btn-dark", 1).waitUntil(hidden, 10000);
		valCheck(63, 5, "scriptList_email_send");
		sleep(15000);
		js("window.open('https://nhnent.dooray.com/mail/folders/2241107403578885832');");
		switchTo().window(1);
		pageLoadCheck = $("label", 0).text();
		if (pageLoadCheck.equals("Username:")) {
			System.out.println(" *** scriptList what's up login page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList what's up login page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#username").setValue("apzz0928");
		$("#password").setValue(pw);
		$(".btn_red").click();
		$(".subject", 0).waitUntil(visible, 10000);
		sleep(5000);
		$(".subject", 0).click();
		pageLoadCheck = $(".subject", 50).text();
		if (pageLoadCheck.substring(54, 66).equals("ap0426104716")) {
			System.out.println(" *** scriptList install mail check Success !! *** ");
		} else {
			System.out.println(" *** scriptList install mail check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".d-toolbar-white-btn", 2).click();
		sleep(1500);
		$(".navi-item", 6).click();
		sleep(1500);
		$(".list-box-title").waitUntil(visible, 10000);
		sleep(1500);
		$(".subject", 0).click();
		sleep(1500);
		$(".d-toolbar-white-btn", 2).click();
		sleep(1500);
		$(".action-btn").waitUntil(visible, 10000);
		sleep(1500);
		$(".action-btn").click();
		sleep(1500);
		$(".navi-item-content", 12).click();
		sleep(1500);
		System.out.println(" *** Testmail delete !! *** ");
		switchTo().window(0);
		pageLoadCheck = $("#scriptList").text();
		if (pageLoadCheck.equals("분석스크립트")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121333").waitUntil(visible, 10000);
		$("#scroll_target_121333").click();
		$(".text-danger", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-danger", 0).text();
		if (pageLoadCheck.equals("데이터 수집/분석중지")) {
			System.out.println(" *** scriptList detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121333").click();
		// $(".text-danger", 0).waitUntil(hidden, 10000);
		System.out.println(" ! ----- scriptList End ----- ! ");
	}

	@Test(priority = 2)
	public void installApply() {
		System.out.println(" ! ----- installApply Start ----- ! ");
		$(By.linkText("분석스크립트 설치신청")).click();
		String pageLoadCheck = $(".panel-title", 0).text();
		if (pageLoadCheck.equals("1서비스 선택")) {
			System.out.println(" *** installApply page load Success !! *** ");
		} else {
			System.out.println(" *** installApply page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_next_step1").scrollTo();
		$("#btn_next_step1").click();
		valCheck(20, 4, "installApply_null");
		$("#checkbox_svc_46").click();
		$("#btn_next_step1").click();
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(22, 5, "installApply_name_null");
		$("#applicant_nm").scrollTo();
		$("#applicant_nm").setValue("name");
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(23, 6, "installApply_email_null");
		$("#applicant_email").scrollTo();
		$("#applicant_email").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(24, 7, "installApply_email_validation");
		$("#applicant_email").scrollTo();
		$("#applicant_email").setValue(date + "@net.com");
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(25, 8, "installApply_FTP_null");
		$("#host_46").scrollTo();
		$("#host_46").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(26, 9, "installApply_port_null");
		$("#port_46").scrollTo();
		$("#port_46").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(27, 10, "installApply_id_null");
		$("#id_46").scrollTo();
		$("#id_46").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(28, 11, "installApply_pw_null");
		$("#pwd_46").scrollTo();
		$("#pwd_46").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(29, 12, "installApply_agree_null");
		System.out.println(" ! ----- installApply End ----- ! ");
	}

	@Test(priority = 3)
	public void memberInfo() {
		System.out.println(" ! ----- memberInfo Start ----- ! ");
		$(By.linkText("회원정보")).click();
		$("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("비밀번호 재확인")) {
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
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
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
		if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
			System.out.println(" *** Restoration Password Success !! *** ");
			$("#okButton").click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** Restoration Password Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_name").setValue("변경이름");
		$("#s_company").setValue("변경회사명");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='011']")).click();
	    $("#s_hp2").setValue("0928");
	    $("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@naver.com");
	    $(".btn-lg", 1).click();
	    String modalBody = $(".modal-body", 1).text();
		$(".modal-backdrop").waitUntil(visible, 10000);
		sleep(500);
	    if(modalBody.equals("회원정보가 수정되었습니다.")) {
			$(".btn-sm", 5).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
			System.out.println(" *** change memberInfo Success !! *** ");
		} else {
			System.out.println(" *** change memberInfo Fail ... !@#$%^&*() *** ");
			close();
		}
	    sleep(1000);
	    $("#s_name").waitUntil(visible, 10000);
		sleep(500);
	    $("#s_name").setValue("원래이름");
		$("#s_company").setValue("원래회사명");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928@gmail.com");
	    $(".btn-lg", 1).click();
	    $(".modal-dialog").waitUntil(visible, 10000);
		if(modalBody.equals("회원정보가 수정되었습니다.")) {
			$(".btn-sm", 4).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
			System.out.println(" *** Restoration memberInfo Success !! *** ");
		} else {
			System.out.println(" *** Restoration memberInfo Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- memberInfo End ----- ! ");
	}

	//@Test(priority = 4)
	public void myCoupon() {
		System.out.println(" ! ----- myCoupon Start ----- ! ");
		open("https://new.acecounter.com/manage/myCoupon");
		sleep(2000);
		//$("#btn-save").waitUntil(visible, 10000);
		String pageLoadCheck = $("#btn-save").text();
		if (pageLoadCheck.equals("쿠폰등록")) {
			System.out.println(" *** myCoupon page load Success !! *** ");
		} else {
			System.out.println(" *** myCoupon page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		valCheck(4, 4, "myCoupon_1_null");
		$(".coupon-input", 0).setValue("1234");
		$("#btn-save").click();
		valCheck(5, 5, "myCoupon_2_null");
		$(".coupon-input", 0).click();
		$(".coupon-input", 0).setValue("1234");
		$(".coupon-input", 1).setValue("123456");
		$("#btn-save").click();
		valCheck(6, 6, "myCoupon_3_null");
		$(".coupon-input", 0).click();
		$(".coupon-input", 0).setValue("1234");
		$(".coupon-input", 1).setValue("123456");
		$(".coupon-input", 2).setValue("12345");
		$("#btn-save").click();
		valCheck(7, 7, "myCoupon_4_null");
		$(".coupon-input", 0).click();
		$(".coupon-input", 0).setValue("1234");
		$(".coupon-input", 1).setValue("123456");
		$(".coupon-input", 2).setValue("12345");
		$(".coupon-input", 3).setValue("1234");
		$("#btn-save").click();
		valCheck(8, 8, "myCoupon_5_null");
		System.out.println(" ! ----- myCoupon End ----- ! ");
	}

	@Test(priority = 5)
	public void addService() {
		System.out.println(" ! ----- addService Start ----- ! ");
		$(By.linkText("서비스추가")).click();
		$("#btn_submit").waitUntil(visible, 10000);
		String pageLoadCheck = $("#btn_submit").text();
		if (pageLoadCheck.equals("등록하기")) {
			System.out.println(" *** addService page load Success !! *** ");
		} else {
			System.out.println(" *** addService page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_submit").click();
		valCheck(10, 5, "addService_siteName_null");
		$(".input-sm", 0).setValue(date);
		$("#btn_submit").click();
		valCheck(11, 6, "addService_domain_null");
		$(".gui-input", 1).setValue(date);
		$(".ace-btn-add-domain").click();
		valCheck(12, 7, "addService_domain_validation");
		$(".gui-input", 1).setValue(date + ".com");
		$(".ace-btn-add-domain").click();
		$(".cross").waitUntil(visible, 10000);
		$(".gui-input", 1).setValue(date + ".com");
		$(".ace-btn-add-domain").click();
		valCheck(13, 8, "addService_domain_duplication");
		$("#btn_submit").click();
		valCheck(14, 9, "addService_siteGroup1_null");
		$(".br-dark", 1).click();
		$("h3", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 3).text();
		if (pageLoadCheck.equals("분류전체보기")) {
			System.out.println(" *** addService siteGroup layer load Success !! *** ");
		} else {
			System.out.println(" *** addService siteGroup layer load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".close", 0).click();
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$(By.name("largeCategoryCd")).click();
		$(By.xpath("//option[@value='22']")).click();
		$("#btn_submit").click();
		valCheck(15, 10, "addService_siteGroup2_null");
		$(By.name("middleCategoryCd")).click();
		$(By.xpath("//option[@value='188']")).click();
		$("label", 12).click();
		$("#btn_submit").click();
		valCheck(16, 11, "addService_coupon_null");
		System.out.println(" ! ----- addService End ----- ! ");
	}

	@Test(priority = 6)
	public void addView() {
		System.out.println(" ! ----- addView Start ----- ! ");
		$(By.linkText("뷰필터 추가")).click();
		$(".div_not_paid").waitUntil(visible, 10000);
		String pageLoadCheck = $(".div_not_paid").text();
		if (pageLoadCheck.equals("뷰필터는 유료 서비스 전환 후에 추가 하실 수 있습니다.")) {
			System.out.println(" *** addView page load Success !! *** ");
		} else {
			System.out.println(" *** addView page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- addView End ----- ! ");
	}

	@Test(priority = 7)
	public void addIntegralReport() {
		System.out.println(" ! ----- addIntegralReport Start ----- ! ");
		$(By.linkText("통합리포트 생성")).click();
		$(".nano-content", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $(".nano-content", 2).text();
		if (pageLoadCheck.equals("이용중인 서비스가 없습니다.")) {
			System.out.println(" *** addIntegralReport page load Success !! *** ");
		} else {
			System.out.println(" *** addIntegralReport page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).scrollTo();
		$(".btn-info", 0).click();
		valCheck(5, 5, "addIntegralReport_name_null");
		$("#report_nm").scrollTo();
		$("#report_nm").setValue(date);
		$(".btn-info", 0).scrollTo();
		$(".btn-info", 0).click();
		valCheck(6, 6, "addIntegralReport_service_null");
		System.out.println(" ! ----- addIntegralReport End ----- ! ");
	}

	@Test(priority = 8)
	public void editService() {
		System.out.println(" ! ----- editService Start ----- ! ");
		$(By.linkText("정보수정")).click();
		$("#svc_nm_title_1").waitUntil(visible, 10000);
		String pageLoadCheck = $(".btn-info", 0).text();
		if (pageLoadCheck.equals("서비스 추가")) {
			System.out.println(" *** editService page load Success !! *** ");
		} else {
			System.out.println(" *** editService page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#svc_nm_title_1").click();
		$(".btn-info", 2).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		valCheck(120, 27, "editService_modify_check");
		$(".input-sm", 1).setValue("");
		$(".cross", 2).click();
		$(".btn-info", 2).click();
		valCheck(121, 28, "editService_siteName_null");
		$(".input-sm", 1).setValue(date);
		$(".btn-info", 2).click();
		valCheck(122, 29, "editService_domain_null");
		$(".gui-input", 5).setValue(date);
		$(".br-dark", 1).click();
		valCheck(123, 30, "editService_domain_validation");
		$(".gui-input", 5).setValue(date + ".com");
		$(".br-dark", 1).click();
		$(".cross", 2).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		valCheck(124, 31, "editService_edit_alert");
		$(".btn-info", 2).waitUntil(hidden, 10000);
		$(".btn-info", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("#svc_nm_title_1").text();
		System.out.println("date is " + date);
		System.out.println("svc_nm_title_1 is " + pageLoadCheck);
		System.out.println("svc_nm_title_1 (substring 21, 33) is " + pageLoadCheck.substring(21, 33));
		if (pageLoadCheck.substring(21, 33).equals(date)) {
			System.out.println(" *** editService edit Success !! *** ");
		} else {
			System.out.println(" *** editService edit Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".cross", 2).click();
		$(".gui-input", 5).setValue("ap0420121150.com");
		$(".br-dark", 1).click();
		$(".cross", 2).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		valCheck(120, 27, "editService_restore_alert");
		$(".btn-info", 2).waitUntil(hidden, 10000);
		$(".btn-info", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("#svc_nm_title_1").text();
		System.out.println("date is " + date);
		System.out.println("svc_nm_title_1 is " + pageLoadCheck);
		System.out.println("svc_nm_title_1 (substring 21, 33) is " + pageLoadCheck.substring(21, 33));
		if (pageLoadCheck.substring(21, 33).equals("ap0420121150")) {
			System.out.println(" *** editService restore Success !! *** ");
		} else {
			System.out.println(" *** editService restore Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- editService End ----- ! ");
	}

	@Test(priority = 9)
	public void summaryReport() {
		System.out.println(" ! ----- summaryReport Start ----- ! ");
		$(By.linkText("발송메일 설정")).click();
		$("#btn-sendMail").waitUntil(visible, 10000);
		$(".cross", 0).click();
		$(".sendEmail").waitUntil(hidden, 10000);
		$("#btn-sendMail").click();
		valCheck(6, 5, "summaryReport_sendEmail_null");
		$(".gui-input", 0).setValue("apzz0928@");
		$("#btn-sendEmail").click();
		valCheck(7, 6, "summaryReport_sendEmail_check");
		$(".gui-input", 0).setValue("apzz0928@nhnent.com");
		$("#btn-sendEmail").click();
		$(".sendEmail").waitUntil(visible, 10000);
		$("#btn-sendMail").click();
		$(".modal-center", 5).waitUntil(visible, 10000);
		valCheck(8, 7, "summaryReport_sendEmail_send");
		sleep(15000);
		switchTo().window(1);
		String subject = $(".subject", 0).text();
		System.out.println("sub0 is : " + subject);
		subject = $(".subject", 1).text();
		System.out.println("sub1 is : " + subject);
		$(".subject", 0).click();
		String pageLoadCheck = $("h1").text();
		if (pageLoadCheck.equals("주간요약 리포트입니다.")) {
			System.out.println(" *** summaryReport sendMail Check Success !! *** ");
		} else {
			System.out.println(" *** summaryReport sendMail Check Fail ... !@#$%^&*() *** ");
			close();
		}
		switchTo().window(0);
		$("#btn-save").scrollTo();
		$(".cross", 1).click();
		$(".reserveEmail").waitUntil(hidden, 10000);
		$("#btn-save").click();
		valCheck(6, 5, "summaryReport_reserveEmail_null");
		$(".gui-input", 3).setValue("apzz0928@");
		$("#btn-reserveEmail").click();
		valCheck(7, 6, "summaryReport_reserveEmail_check");
		$(".gui-input", 3).setValue("apzz0928@nhnent.net");
		$("#btn-reserveEmail").click();
		$(".reserveEmail").waitUntil(visible, 10000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck(8, 7, "summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 10000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck(6, 5, "summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 10000);
		System.out.println(" ! ----- summaryReport End ----- ! ");
	}

	//@Test(priority = 10)
	public void subManager() {
		System.out.println(" ! ----- summaryReport Start ----- ! ");
		$(By.linkText("부관리자")).click();
		$("#btn_mail").waitUntil(visible, 10000);
		$("#btn_mail").click();
		valCheck(5, 7, "subManager_name_null");
		$("#submanager_nm").setValue("최영권");
		$("#btn_mail").click();
		valCheck(6, 8, "subManager_email_null");
		$("#submanager_email").setValue("apzz0928@");
		$("#btn_mail").click();
		valCheck(7, 9, "subManager_email_check");
		$("#submanager_email").setValue("apzz0928@nhnent.net");
		$("#btn_mail").click();
		valCheck(8, 10, "subManager_service_null");

		System.out.println(" ! ----- summaryReport End ----- ! ");
	}

	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}