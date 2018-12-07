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

public class getInflow {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, A, B, C, domain, checkMsg, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;

	// 신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
	SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
	SimpleDateFormat number_format1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat number_format2 = new SimpleDateFormat("MMddhhmmss");
	String date = number_format.format(number_date);
	String date1 = number_format1.format(number_date);
	String date2 = number_format2.format(number_date);

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf";
		pw1 = "qordlf";
		A = "!@34";
		B = "1@#4";
		C = "12#$";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = false;

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

	public static void valCheck(String val) {
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
		case "memberInfo_change_alert":
			checkMsg = "회원정보가 수정되었습니다.";
			break;
		case "memberInfo_password_null":
			checkMsg = "비밀번호를 입력하세요.";
			break;
		case "memberInfo_now_password_null":
			checkMsg = "현재 비밀번호를 입력하세요.";
			break;
		case "memberInfo_new_password_null":
			checkMsg = "새 비밀번호를 입력하세요.";
			break;
		case "memberInfo_new_password_check":
			checkMsg = "새 비밀번호 확인이 필요합니다.";
			break;
		case "memberInfo_new_password_fail":
			checkMsg = "새 비밀번호가 일치하지 않습니다.";
			break;
		case "memberInfo_change_password_confirm":
			checkMsg = "비밀번호를 변경 하시겠습니까?";
			break;
		case "memberInfo_change_password_check":
			checkMsg = "비밀번호 변경이 완료되었습니다.";
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
		case "myCoupon_number_check":
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
		case "notifyReport_save_confirm!":
			checkMsg = "선택하신 알림 내용이 없습니다.\n" + "트래픽 알림을 중지 하시겠습니까?";
			break;
		case "notifyReport_save_check":
			checkMsg = "설정하신 내용이 저장되었습니다.\n" + "설정내용은 익일부터 반영됩니다.";
			break;
		case "notifyReport_modify_confirm":
			checkMsg = "선택하신 알림 내용이 없습니다.\n" + "트래픽 알림을 중지 하시겠습니까?";
			break;
		case "editS4ervice_restore_alert":
			checkMsg = "";
			break;
		case "editSer5vice_restore_alert":
			checkMsg = "";
			break;
		case "editSer6vice_restore_alert":
			checkMsg = "";
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
		case "subManager_email_send":
			checkMsg = "이메일이 발송되었습니다.\n" + "\n" + "발송된 이메일의 회원가입 링크 유효기간은 총 7일로\n" + "7일 이내 미가입 시 유효기간이 종료됩니다.";
			break;
		case "submanager_authority_modify_check":
			checkMsg = "수정이 완료되었습니다.";
			break;
		case "subManager_pw_alert":
			checkMsg = "비밀번호 변경이 완료되었습니다.";
			break;
		case "subManager_delete_confirm":
			checkMsg = "부관리자 권한을 삭제하시겠습니까?";
			break;
		case "subManager_delete_check":
			checkMsg = "삭제가 완료되었습니다.";
			break;
		case "leaveService_service_null":
			checkMsg = "해지하실 서비스를 선택하여 주세요.";
			break;
		case "leaveService_name_null":
			checkMsg = "이름을 입력해주세요.";
			break;
		case "leaveService_number_null":
			checkMsg = "연락처와 휴대전화번호 중\n" + "하나는 반드시 입력해 주세요";
			break;
		case "leaveService_email_null":
			checkMsg = "이메일을 입력해주세요.";
			break;
		case "leaveService_email_check":
			checkMsg = "이메일 주소가 올바르지 않습니다.";
			break;
		case "leaveService_casue_null":
			checkMsg = "해지사유를 선택해주세요.";
			break;
		case "extendCharge_service_null":
			checkMsg = "서비스를 선택해 주세요.";
			break;
		case "extendCharge_name_null":
			checkMsg = "이름을 입력해 주세요.";
			break;
		case "extendCharge_number_null":
			checkMsg = "연락처와 휴대전화번호 중\n" + "하나는 반드시 입력해 주세요.";
			break;
		case "extendCharge_email_null":
			checkMsg = "이메일을 입력해주세요.";
			break;
		case "extendCharge_email_check":
			checkMsg = "이메일 주소가 올바르지 않습니다.";
			break;
		case "additionalCharge_service_null":
			checkMsg = "서비스를 선택해주세요.";
			break;
		case "paymentBill_number_null":
			checkMsg = "사업자등록번호를 입력해 주세요.";
			break;
		case "paymentBill_number_only":
			checkMsg = "숫자만 입력할 수 있습니다.";
			break;
		case "paymentBill_char_only":
			checkMsg = "문자만 입력할 수 있습니다.";
			break;
		case "paymentBill_numberOrchar_only":
			checkMsg = "숫자 또는 문자만 입력할 수 있습니다.";
			break;
		case "paymentBill_company_null":
			checkMsg = "회사명을 입력해 주세요.";
			break;
		case "paymentBill_ceoname_null":
			checkMsg = "대표자명을 입력해 주세요.";
			break;
		case "paymentBill_address_null":
			checkMsg = "회사주소를 입력해 주세요.";
			break;
		case "paymentBill_businessType_null":
			checkMsg = "업태를 입력해 주세요.";
			break;
		case "paymentBill_businessTypeSection_null":
			checkMsg = "종목을 입력해 주세요.";
			break;
		case "paymentBill_check":
			checkMsg = "저장되었습니다.";
			break;
		}
		$(".modal-backdrop").waitUntil(visible, 10000);
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	        	System.out.println(" *** " + val +  " - confirm check Success !! *** ");
				$$(".btn-info").last().click();
			    $(".modal-backdrop").waitUntil(hidden, 10000);
	        } else { //confirm 아니면 .btn-sm클릭
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            //$(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert 로딩이 늦거나 노출되지 않았을때 체크하기위해 빈값 체크
	        System.out.println(" *** ☆★☆★☆★ val : " + val + " // pTag text is : " + msgCheck +  " // - msgCheck is Empty ... ☆★☆★☆★ *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** // val : " + val + " // pTag text is : " + msgCheck +  " // - check Fail ... !@#$%^&*() *** ");
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
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() *** ");
				close();
			} else {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Success !! *** ");
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
			ex.printStackTrace();
		}
	}

	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}

	//@Test(priority = 0)
	public void serviceManage_main() {
		System.out.println(" ! ----- serviceManage_main Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 15000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text().trim();
		$(".btn_logout").getValue();
		if (loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_setting").click();
		$(".notokr-bold", 0).waitUntil(visible, 1000);
		pageLoadCheck = $(".notokr-bold", 0).text().trim();
		if (pageLoadCheck.equals("서비스 관리")) {
			System.out.println(" *** serviceManage page load Success !! *** ");
		} else {
			System.out.println(" *** serviceManage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- serviceManage_main End ----- ! ");
	}

	//@Test(priority = 1)
	public void scriptList() {
		System.out.println(" ! ----- scriptList Start ----- ! ");
		$(By.linkText("분석스크립트")).click();
		$("#items").waitUntil(visible, 15000);
		$(".br-dark").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		pageLoadCheck = $(".modal-title").text().trim();
		if (pageLoadCheck.equals("분석스크립트 메일발송")) {
			System.out.println(" *** scriptList layerPopup load Success !! *** ");
		} else {
			System.out.println(" *** scriptList layerPopup load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-dark", 1).click();
		valCheck("scriptList_email_null");
		$("#mail_to").setValue(date);
		$(".btn-dark", 1).click();
		valCheck("scriptList_email_validation");
		$("#mail_to").setValue("apzz092888@daum.net");
		$(".btn-dark", 1).click();
		$(".btn-dark", 1).waitUntil(hidden, 10000);
		valCheck("scriptList_email_send");
		js("window.open('https://logins.daum.net/accounts/loginform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		//다음메일 탭으로 포커스 변경
		switchTo().window(1);
		$("#id").setValue("apzz092888");
		$("#inputPwd").setValue(pw + A);
		$("#loginBtn").click();
		$(".link_check").waitUntil(visible, 15000);
		sleep(1000);
		refresh();
		pageLoadCheck = $("h1").text().trim();
		if (pageLoadCheck.equals("Daum\n" + "메일")) {
			System.out.println(" *** scriptList daum mail list page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList daum mail list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).waitUntil(visible, 15000);
		$(".tit_subject", 0).click();
		$(".txt_filename").waitUntil(visible, 15000);
		pageLoadCheck = $(".txt_filename").text().trim();
		if (pageLoadCheck.equals("script(ap0420121150.com).zip")) {
			System.out.println(" *** scriptList send mail fileName check Success !! *** ");
		} else {
			System.out.println(" *** scriptList send mail fileName check Fail ... !@#$%^&*() *** ");
			close();
		}
		//메일삭제
		$(By.linkText("받은메일함")).click();
		$(".select_all").waitUntil(visible, 15000);
		$(".select_all").click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("휴지통")).click();
		$(".select_all").waitUntil(visible, 15000);
		$(".select_all").click();
		$(".wrap_bold > .btn_permanent").click();
		sleep(2000);
		$(".check_type2").click();
		$(By.linkText("받은메일함")).click();
		switchTo().window(0);
		pageLoadCheck = $("#scriptList").text().trim();
		if (pageLoadCheck.equals("분석스크립트")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121331").waitUntil(visible, 15000);
		$("#scroll_target_121331").click();
		$(".text-danger", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-danger", 0).text().trim();
		if (pageLoadCheck.equals("데이터 수집/분석중지")) {
			System.out.println(" *** scriptList detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121331").click();
		// $(".text-danger", 0).waitUntil(hidden, 10000);
		System.out.println(" ! ----- scriptList End ----- ! ");
	}

	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}