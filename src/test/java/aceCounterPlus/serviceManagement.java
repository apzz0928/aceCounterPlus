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

/*import com.codeborne.selenide.testng.ScreenShooter;*/

public class serviceManagement {
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
		B = "@#14";
		C = "12#$";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

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
		case "memberInfo_new_password_notconsensus":
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
		case "addIntegralReport_00":
			checkMsg = "";
		case "addIntegralReport_01":
			checkMsg = "";
		case "addIntegralReport_02":
			checkMsg = "";
		case "addIntegralReport_03":
			checkMsg = "";
		case "addIntegralReport_04":
			checkMsg = "";
		case "addIntegralReport_05":
			checkMsg = "";
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
			checkMsg = "해지사유를 선택해주세요.\n(하나 이상의 해지 사유 선택)";
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
	    //$$("p").last().click();
	    sleep(800);
		String msgCheck = $$("p").last().text().trim();
	    //Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	        	System.out.println(" *** " + val +  " - confirm check Success !! *** ");
				$$(".btn-info").last().click();
				if(!val.equals("memberInfo_change_password_confirm")) {
				    $(".modal-backdrop").waitUntil(hidden, 10000); //비번변경 confirm 에러나서 sleep으로 대체 	
				}
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

	@Test(priority = 0)
	public void serviceManage_main() {
		System.out.println(" ! ----- serviceManage_main Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 15000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("로그아웃")) {
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

	@Test(priority = 1)
	public void scriptList() {
		System.out.println(" ! ----- scriptList Start ----- ! ");
		$(By.linkText("분석스크립트")).waitUntil(visible, 10000);
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
		$("#mail_to").setValue("apzz0928888@daum.net");
		$(".btn-dark", 1).click();
		$(".btn-dark", 1).waitUntil(hidden, 10000);
		valCheck("scriptList_email_send");
		js("window.open('https://logins.daum.net/accounts/signinform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		//다음메일 탭으로 포커스 변경
		switchTo().window(1);
		$("#id").setValue("apzz0928888");
		$("#inputPwd").setValue(pw + A);
		$("#loginBtn").click();
		$(".link_check").waitUntil(visible, 15000);
		sleep(3000);
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
		if (pageLoadCheck.equals("script(www.ap0420121150.com).zip")) {
			System.out.println(" *** scriptList send mail fileName check Success !! *** ");
		} else {
			System.out.println(" *** scriptList send mail fileName check Fail ... !@#$%^&*() *** ");
			close();
		}
		//받은메일함으로 이동 후 메일 삭제
		$(".ui-droppable", 0).click();
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_del").click();
		$(".info_none").waitUntil(visible, 10000);
		$(".ui-droppable", 4).click();
        System.out.println(" *** move to basket *** ");
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_permanent").click();
		$(".btn_box").waitUntil(visible, 10000);
		$(".btn_g.check_type2").click();
        System.out.println(" *** delete mail *** ");
		$(".btn_box").waitUntil(hidden, 10000);
		$(".ui-droppable", 0).click();
        System.out.println(" *** move to inbox *** ");
		switchTo().window(0);
		pageLoadCheck = $("#scriptList").text().trim();
		if (pageLoadCheck.equals("분석스크립트")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_146588").waitUntil(visible, 15000);
		$("#scroll_target_146588").click();
		$(".text-danger", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-danger", 0).text().trim();
		if (pageLoadCheck.equals("데이터 수집/분석중지")) {
			System.out.println(" *** scriptList detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- scriptList End ----- ! ");
	}

	@Test(priority = 11)
	public void installApply() {
		System.out.println(" ! ----- installApply Start ----- ! ");
		$(By.linkText("분석스크립트 설치신청")).waitUntil(visible, 10000);
		$(By.linkText("분석스크립트 설치신청")).click();
		pageLoadCheck = $(".panel-title", 0).text().trim();
		if (pageLoadCheck.equals("1서비스 선택")) {
			System.out.println(" *** installApply page load Success !! *** ");
		} else {
			System.out.println(" *** installApply page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_next_step1").click();
		valCheck("installApply_null");
		sleep(500);
		$("#checkbox_svc_0").click();
		$("#btn_next_step1").click();
		$("#btn_submit").waitUntil(visible, 15000);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_name_null");
		$("#applicant_nm").scrollIntoView(false);
		$("#applicant_nm").setValue("name");
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_email_null");
		$("#applicant_email").scrollIntoView(false);
		$("#applicant_email").setValue(date);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_email_validation");
		$("#applicant_email").scrollIntoView(false);
		$("#applicant_email").setValue(date + "@net.com");
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_FTP_null");
		$("#host_0").scrollIntoView(false);
		$("#host_0").setValue(date);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_port_null");
		$("#port_0").scrollIntoView(false);
		$("#port_0").setValue(date);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_id_null");
		$("#id_0").scrollIntoView(false);
		$("#id_0").setValue(date);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_pw_null");
		$("#pwd_0").scrollIntoView(false);
		$("#pwd_0").setValue(date);
		$("#btn_submit").scrollIntoView(false);
		$("#btn_submit").click();
		valCheck("installApply_agree_null");
		System.out.println(" ! ----- installApply End ----- ! ");
	}

	@Test(priority = 21)
	public void memberInfo() {
		System.out.println(" ! ----- memberInfo Start ----- ! ");
		$(By.linkText("회원정보")).waitUntil(visible, 10000);
		$(By.linkText("회원정보")).click();
		$("h3", 2).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("비밀번호 재확인")) {
			System.out.println(" *** memberInfo PW reCheck page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo PW reCheck load Fail ... !@#$%^&*() *** ");	
			close();
		}
		$("#btn-ok").click();
		valCheck("memberInfo_password_null");
		$("#pwd").setValue(pw + A);
		$("#btn-ok").click();
		$(".text-muted", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-muted", 0).text().trim();
		if(pageLoadCheck.equals("apzz0928888")) {
			System.out.println(" *** memberInfo change Page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo change Page load Fail ... !@#$%^&*() *** ");		
			close();
		}
		$("#modifyProc").click();
		valCheck("memberInfo_now_password_null");
		$("#prePwd").setValue(pw + A);
		$("#modifyProc").click();
		valCheck("memberInfo_new_password_null");
		$("#changePwd").setValue(pw + B);
		$("#modifyProc").click();
		valCheck("memberInfo_new_password_check");
		$("#changePwdConfirm").waitUntil(visible, 10000);
		$("#changePwdConfirm").setValue(pw + C);
		$("#modifyProc").click();
		valCheck("memberInfo_new_password_notconsensus");
		$("#prePwd").setValue("");
		$("#changePwd").setValue("");
		$("#changePwdConfirm").setValue("");
		for(int i=1;i<=3;i++) { //이전 사용 비밀번호로 변경 불가해서 3번바꿈
			if(i==1) {
				pw = pw + A;
				pw1 = pw1 + B;
			} else if(i==2) {
				pw = pw + B;
				pw1 = pw1 + C;
			} else if(i==3) {
				pw = pw + C;
				pw1 = pw1 + A;
			}
			$("#prePwd").waitUntil(visible, 10000);
			$("#prePwd").setValue(pw);
			sleep(500);
			$("#changePwd").setValue(pw1);
			sleep(500);
			$("#changePwdConfirm").setValue(pw1);
			sleep(500);
			$("#modifyProc").click();
			sleep(1000);
			valCheck("memberInfo_change_password_confirm");
			sleep(1500);
			valCheck("memberInfo_change_password_check");
			pw = "qordlf";
			pw1 = "qordlf";
		}
		$("#s_name").setValue("변경이름");
		$("#s_company").setValue("변경회사명");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='011']")).click();
	    $("#s_hp2").setValue("0928");
	    $("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@naver.com");
	    $(".btn-lg", 1).click();
	    valCheck("memberInfo_change_alert");
	    sleep(1000);
	    $("#s_name").waitUntil(visible, 15000);
		sleep(500);
	    $("#s_name").setValue("원래이름");
		$("#s_company").setValue("원래회사명");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928888@daum.net");
	    $(".btn-lg", 1).click();
	    valCheck("memberInfo_change_alert");
		System.out.println(" ! ----- memberInfo End ----- ! ");
	}

	//@Test(priority = 31)
	public void myCoupon() {
		System.out.println(" ! ----- myCoupon Start ----- ! ");
		$(By.linkText("쿠폰관리")).waitUntil(visible, 10000);
		$(By.linkText("쿠폰관리")).click();
		$(".coupon-input", 0).waitUntil(visible, 15000);
		pageLoadCheck = $("#btn-save").text().trim();
		if (pageLoadCheck.equals("쿠폰등록")) {
			System.out.println(" *** myCoupon page load Success !! *** ");
		} else {
			System.out.println(" *** myCoupon page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		for(int i=1;i<=4;i++) {
		    valCheck("myCoupon_" + i + "_null");
	        $(".coupon-input", 0).click();
	        $(".coupon-input", 0).setValue("1234");
	        if (i >= 2) {
		        $(".coupon-input", 1).setValue("123456");
		        if (i >= 3) {
			        $(".coupon-input", 2).setValue("12345");
			        if (i >= 4) {
				        $(".coupon-input", 3).setValue("1234");
				    }
			    }
		    }
		    $("#btn-save").click();
		}
		valCheck("myCoupon_number_check");
		System.out.println(" ! ----- myCoupon End ----- ! ");
	}

	@Test(priority = 41)
	public void addService() {
		System.out.println(" ! ----- addService Start ----- ! ");
		$(By.linkText("서비스추가")).waitUntil(visible, 10000);
		$(By.linkText("서비스추가")).scrollIntoView(false);
		$(By.linkText("서비스추가")).click();
		open("https://new.acecounter.com/manage/serviceInfo/addService");
		$("#btn_submit").waitUntil(visible, 15000);
		pageLoadCheck = $("#btn_submit").text().trim();
		if (pageLoadCheck.equals("등록하기")) {
			System.out.println(" *** addService page load Success !! *** ");
		} else {
			System.out.println(" *** addService page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_submit").click();
		valCheck("addService_siteName_null");
		$(".input-sm", 0).setValue(date);
		$("#btn_submit").click();
		valCheck("addService_domain_null");
		$(".gui-input", 1).setValue(date);
		$(".ace-btn-add-domain").click();
		valCheck("addService_domain_validation");
		$(".gui-input", 1).setValue(date + ".com");
		$(".ace-btn-add-domain").click();
		$(".cross").waitUntil(visible, 15000);
		$(".gui-input", 1).setValue(date + ".com");
		$(".ace-btn-add-domain").click();
		valCheck("addService_domain_duplication");
		$("#btn_submit").click();
		valCheck("addService_siteGroup1_null");
		$(".br-dark", 1).click();
		$("h3", 3).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 3).text().trim();
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
		valCheck("addService_siteGroup2_null");
		$(By.name("middleCategoryCd")).click();
		$(By.xpath("//option[@value='188']")).click();
		$("label", 12).click();
		$("#btn_submit").click();
		valCheck("addService_coupon_null");
		System.out.println(" ! ----- addService End ----- ! ");
	}

	//@Test(priority = 51)
	public void addView() {
		System.out.println(" ! ----- addView Start ----- ! ");
		$(By.linkText("뷰필터 추가")).click();
		$(".div_not_paid").waitUntil(visible, 15000);
		pageLoadCheck = $(".div_not_paid").text().trim();
		if (pageLoadCheck.equals("뷰필터는 종속된 서비스에 분석 이력이 있는 경우만 신청 가능 합니다.")) {
			System.out.println(" *** addView page load Success !! *** ");
		} else {
			System.out.println(" *** addView page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- addView End ----- ! ");
	}

	//@Test(priority = 61)
	public void addIntegralReport() {
		System.out.println(" ! ----- addIntegralReport Start ----- ! ");
		$(By.linkText("통합리포트 생성")).waitUntil(visible, 10000);
		$(By.linkText("통합리포트 생성")).scrollIntoView(false);
		$(By.linkText("통합리포트 생성")).click();
		$(".nano-content", 2).waitUntil(visible, 15000);
		pageLoadCheck = $(".nano-content", 2).text().trim();
		if (pageLoadCheck.equals("이용중인 서비스가 없습니다.")) {
			System.out.println(" *** addIntegralReport page load Success !! *** ");
		} else {
			System.out.println(" *** addIntegralReport page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).scrollIntoView(false);
		$(".btn-info", 0).click();
		valCheck("addIntegralReport_name_null");
		$("#report_nm").scrollIntoView(false);
		$("#report_nm").setValue(date);
		$(".btn-info", 0).scrollIntoView(false);
		$(".btn-info", 0).click();
		valCheck("addIntegralReport_service_null");
		System.out.println(" ! ----- addIntegralReport End ----- ! ");
	}

	@Test(priority = 71)
	public void editService() {
		System.out.println(" ! ----- editService Start ----- ! ");
		$(By.linkText("정보수정")).waitUntil(visible, 10000);
		$(By.linkText("정보수정")).click();
		$("#svc_nm_title_1").waitUntil(visible, 15000);
		pageLoadCheck = $(".btn-info", 0).text().trim();
		if (pageLoadCheck.equals("서비스 추가")) {
			System.out.println(" *** editService page load Success !! *** ");
		} else {
			System.out.println(" *** editService page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#svc_nm_title_0").click();
		$(".btn-info", 1).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck("editService_modify_check");
		$(".input-sm", 0).setValue("");
		$(".cross", 0).click();
		$(".btn-info", 1).click();
		valCheck("editService_siteName_null"); 
		$(".input-sm", 0).setValue(date);
		$(".btn-info", 1).click();
		valCheck("editService_domain_null");
		$(".gui-input", 1).setValue(date);
		$(".br-dark", 0).click();
		valCheck("editService_domain_validation");
		$(".gui-input", 1).setValue(date + ".com");
		$(".br-dark", 0).click();
		$(".cross", 0).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck("editService_edit_alert");
		sleep(1000);
		//$(".btn-info", 1).waitUntil(hidden, 10000);
		$(".btn-info", 1).waitUntil(visible, 15000);
		pageLoadCheck = $("#svc_nm_title_0").text().trim();
		if (pageLoadCheck.substring(20, 32).equals(date)) {
			System.out.println(" *** editService edit Success !! *** ");
		} else {
			System.out.println(" *** editService edit Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(1000);
		$(".cross", 0).click();
		$(".gui-input", 1).setValue("ap0420121150.com");
		$(".br-dark", 0).click();
		$(".cross", 0).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck("editService_restore_alert");
		$(".btn-info", 1).waitUntil(visible, 15000);
		sleep(1000);
		pageLoadCheck = $("#svc_nm_title_0").text().trim();
		if (pageLoadCheck.substring(20, 32).equals("ap0420121150")) {
			System.out.println(" *** editService restore Success !! *** ");
		} else {
			System.out.println(" *** editService restore Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- editService End ----- ! ");
	}

	@Test(priority = 81)
	public void summaryReport() {
		System.out.println(" ! ----- summaryReport Start ----- ! ");
		$(By.linkText("발송메일 설정")).waitUntil(visible, 10000);
		$(By.linkText("발송메일 설정")).click();
		$("#btn-sendMail").waitUntil(visible, 15000);
		$(".cross", 0).click();
		$(".sendEmail").waitUntil(hidden, 10000);
		$("#btn-sendMail").click();
		valCheck("summaryReport_sendEmail_null");
		$(".gui-input", 0).setValue("apzz0928888@");
		$("#btn-sendEmail").click();
		valCheck("summaryReport_sendEmail_check");
		$(".gui-input", 0).setValue("apzz0928888@daum.net");
		$("#btn-sendEmail").click();
		$(".sendEmail").waitUntil(visible, 15000);
		$("#btn-sendMail").click();
		$(".modal-center", 5).waitUntil(visible, 15000);
		valCheck("summaryReport_sendEmail_send");
		switchTo().window(1);
		sleep(3000);
		refresh();
		pageLoadCheck = $(".tit_subject", 0).text().trim();
		if (pageLoadCheck.substring(15, 22).equals("주간요약리포트")) {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		sleep(3000);
		$("h2", 5).waitUntil(visible, 15000);
		pageLoadCheck = $("h2", 5).text().trim();
		if (pageLoadCheck.substring(11, 15).equals("주간요약")) {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		//받은메일함으로 이동 후 메일 삭제
		$(".ui-droppable", 0).click();
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_del").click();
		$(".info_none").waitUntil(visible, 10000);
		$(".ui-droppable", 4).click();
        System.out.println(" *** move to basket *** ");
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_permanent").click();
		$(".btn_box").waitUntil(visible, 10000);
		$(".btn_g.check_type2").click();
        System.out.println(" *** delete mail *** ");
		$(".btn_box").waitUntil(hidden, 10000);
		$(".ui-droppable", 0).click();
        System.out.println(" *** move to inbox *** ");
		switchTo().window(0);
		$("#btn-save").scrollIntoView(false);
		$(".cross", 1).click();
		$(".reserveEmail").waitUntil(hidden, 10000);
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_null");
		$(".gui-input", 3).setValue("apzz0928888@");
		$("#btn-reserveEmail").click();
		valCheck("summaryReport_reserveEmail_check");
		$(".gui-input", 3).setValue("apzz0928888@daum.net");
		$("#btn-reserveEmail").click();
		$(".reserveEmail").waitUntil(visible, 10000);
		$("label", 18).waitUntil(visible, 10000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_send");
		refresh();
		$("#btn-save").waitUntil(visible, 10000);
		$("label", 18).waitUntil(visible, 10000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 15000);
		System.out.println(" ! ----- summaryReport End ----- ! ");
	}
	
	@Test(priority = 82)
	public void notifyReport() {
		System.out.println(" ! ----- notifyReport Start ----- ! ");
		/*$(By.linkText("알림메일 발송")).waitUntil(visible, 10000);
		$(By.linkText("알림메일 발송")).scrollIntoView(false);
		$(By.linkText("알림메일 발송")).click();*/
		$("li", 60).waitUntil(visible, 10000);
		$("li", 60).scrollIntoView(false);
		$("li", 60).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 1).text().trim();
		if (pageLoadCheck.equals("알림메일 발송")) {
			System.out.println(" *** notifyReport page load check Success !! *** ");
		} else {
			System.out.println(" *** notifyReport page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".switch-info").click();
		$("#reserveEmail_0").waitUntil(visible, 10000);
		$("#btn-save").click();
		valCheck("notifyReport_save_check");
		refresh();
		for(int i=0;i<=5;i++) {
			if(i<=2) {
				$("label[for=inc_" + (i+1) + "_chk]").click();
			} else {
				$("label[for=dec_" + (i-2) + "_chk]").click();
			}
			if(i<=2) {
				$(".set-value", i).setValue("1000");
			} else {
				$(".set-value", i).setValue("100");
			}
			$(".set-compare", i).selectOptionByValue("20");
		}
		$("#btn-save").click();
		valCheck("notifyReport_save_check");
		refresh();
		for(int i=0;i<=5;i++) {
			if(i<=2) {
				$("label[for=inc_" + (i+1) + "_chk]").click();
			} else {
				$("label[for=dec_" + (i-2) + "_chk]").click();
			}
			$(".set-compare", i).selectOptionByValue("10");
			if(i==5) {
				$(".set-value", i).setValue("1");
			} else {
				$(".set-value", i).setValue("");
			}
		}
		$("#btn-save").click();
		valCheck("notifyReport_save_check");
		refresh();
		$(".switch-info").click();
		$("#reserveEmail_0").waitUntil(hidden, 10000);
		$("#btn-save").click();
		valCheck("notifyReport_save_check");
		System.out.println(" ! ----- notifyReport End ----- ! ");
	}

	@Test(priority = 91)
	public void add_subManager() {
		System.out.println(" ! ----- add_subManager Start ----- ! ");
		sleep(1000);
		$(By.linkText("부관리자")).waitUntil(visible, 10000);
		$(By.linkText("부관리자")).click();
		$("#btn_mail").waitUntil(visible, 10000);
		$("#btn_mail").click();
		valCheck("subManager_name_null");
		$("#submanager_nm").setValue("부관리자테스트");
		$("#btn_mail").click();
		valCheck("subManager_email_null");
		$("#submanager_email").setValue("apzz0928888@");
		$("#btn_mail").click();
		valCheck("subManager_email_check");
		$("#submanager_email").setValue("apzz0928888@daum.net");
		$("#btn_mail").click();
		valCheck("subManager_service_null");
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='*'])[3]/following::button[1]")).click();
	    $(By.id("treeDemo_2_check")).click();
	    $(By.id("select_auth")).click();
	    $(By.id("select_auth")).selectOption("통계보기/설정하기");
	    $("#btn_add_svc").click();
		$("#btn_mail").click();
		valCheck("subManager_email_send");
		switchTo().window(1);
		sleep(3500);
		refresh();
		pageLoadCheck = $(".tit_subject", 0).text().trim();
		if(pageLoadCheck.substring(22).equals("부관리자 안내 메일입니다.")) {
			System.out.println(" *** subManager guide mail title Check Success !! *** ");
		} else {
			System.out.println(" *** subManager guide mail title Check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		sleep(3000);
		pageLoadCheck = $("h2", 5).text().trim();
		if (pageLoadCheck.substring(11).equals("부관리자 회원가입 안내")) {
			System.out.println(" *** subManager signin mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** subManager signin mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.linkText("부관리자 회원가입 바로가기")).scrollIntoView(false);
		$(By.linkText("부관리자 회원가입 바로가기")).click();
		switchTo().window(2);
		$("label", 0).waitUntil(visible, 15000);
		$("label", 0).click();
		$("#userid").setValue(id + date2);
		$("#recheck").click();
		$("#userpw").setValue(pw + A);
		$("#repeatpw").setValue(pw + A);
		$(".btn_join").click();
		$(".btn_pop_submit").click();
		switchTo().window(1);
		//받은메일함으로 이동 후 메일 삭제
		$(".ui-droppable", 0).click();
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_del").click();
		$(".info_none").waitUntil(visible, 10000);
		$(".ui-droppable", 4).click();
        System.out.println(" *** move to basket *** ");
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_permanent").click();
		$(".btn_box").waitUntil(visible, 10000);
		$(".btn_g.check_type2").click();
        System.out.println(" *** delete mail *** ");
		$(".btn_box").waitUntil(hidden, 10000);
		$(".ui-droppable", 0).click();
        System.out.println(" *** move to inbox *** ");
		switchTo().window(0);
		switchTo().window(0);
		refresh();
		pageLoadCheck = $(".text-dark", 3).text().trim();
		if(pageLoadCheck.substring(0, 4).equals("가입완료")) {
			System.out.println(" *** subManager_signUp Check Success !! *** ");
		} else {
			System.out.println(" *** subManager_signUp Check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".indicator").click();
		$(".btn-info", 1).waitUntil(visible, 15000);
	    $(By.id("select_added_auth_11000")).click();
	    $(By.id("select_added_auth_11000")).selectOption("통계보기"); //부관리자 권한 변경
	    $(".btn-info", 1).click();
	    $("#btn-modal-alert-yes").waitUntil(visible, 15000);
	    $("#btn-modal-alert-yes").click();
		valCheck("submanager_authority_modify_check");
		System.out.println(" ! ----- add_subManager End ----- ! ");
	}
	
	@Test(priority = 92)
	public void subManager_modifyInfoAndDel() {
		System.out.println(" ! ----- subManager_modifyInfoAndDel Start ----- ! ");
		open("https://new.acecounter.com/auth/logout");
		$("#uid").waitUntil(visible, 15000);
		$("#uid").setValue(id + date2);
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		$(".go_setting").waitUntil(visible, 10000);
		$(".go_setting").click();
		$("#prePwd").setValue(pw + A);
		$("#changePwd").setValue(pw + B);
		$("#changePwdConfirm").setValue(pw + B);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").waitUntil(visible, 15000);
		$("#btn-modal-alert-yes").click();
		$("p", 4).waitUntil(visible, 15000);
		valCheck("subManager_pw_alert");
		open("https://new.acecounter.com/auth/logout");
		$("#uid").waitUntil(visible, 15000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		$(".btn_logout").waitUntil(visible, 15000);
		open("https://new.acecounter.com/manage/serviceInfo/submanager");
		$(".br-dark", 2).waitUntil(visible, 15000);
		$(".br-dark", 2).click();
		valCheck("subManager_delete_confirm");
		valCheck("subManager_delete_check");
		System.out.println(" ! ----- subManager_modifyInfoAndDel End ----- ! ");
	}
	
	@Test(priority = 101)
	public void leaveService() {
		System.out.println(" ! ----- leaveService Start ----- ! ");
		/*$(By.linkText("서비스 해지")).waitUntil(visible, 10000);
		$(By.linkText("서비스 해지")).scrollIntoView(false);
		$(By.linkText("서비스 해지")).click();*/
		open("https://new.acecounter.com/manage/serviceInfo/leaveService");
		sleep(500);
		$(".m10", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".m10", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("회원님의")) {
			System.out.println(" *** leaveService pw check page load check Success !! *** ");
		} else {
			System.out.println(" *** leaveService pw check page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(1000);
		$("#pwd").setValue(pw + A);
		$("#btn-ok").click();
		$("#btnIng").waitUntil(visible, 10000);
		$("#btnIng").click();
		valCheck("leaveService_service_null");
		$(".ckBoxSvc", 0).click();
		$("#btnIng").click();
		$(".control-label", 0).waitUntil(visible, 10000);
		String leaveService_check = "";
		String leaveService_value = "";
		for(int i=0, x=0;i<=5;i++){
		    if(i == 0){
		        leaveService_check = "leaveService_name_null";
		    } else if (i == 1) {
		        leaveService_check = "leaveService_number_null";
		        leaveService_value = "이름";
		    } else if (i == 2) {
		        leaveService_value = "1234";
		        x = ++x;
		    } else if (i == 3) {
		        leaveService_check = "leaveService_email_null";
		        leaveService_value = "5678";
		        x = ++x;
		    } else if (i == 4) {
		        leaveService_check = "leaveService_email_check";
		        leaveService_value = "mail.com";
		        x = 5;
		    } else if (i == 5) {
		        leaveService_check = "leaveService_casue_null";
		        leaveService_value = "test@mail.com";
		    }
		    if(i > 0){
		    	$(".gui-input", x).scrollIntoView(false);
		        $(".gui-input", x).setValue(leaveService_value);
		    }
		    $("#btnApply").click();
			//System.out.println("입력창 번호 : " + x + " // 입력값 : " + leaveService_value + " // 체크값 : " + leaveService_check);
		    valCheck(leaveService_check);
		}
		$("label", 5).scrollIntoView(false);
		$("label", 5).click();
		String inputCheck = "";
		String userInfoCheck = "";
		for(int i=0;i<=30;i++) {
			if($(".gui-input", 0).text() != "원래이름") {
				sleep(100);
			} else {
				break;
			}
		}
		for(int i=0;i<=5;i++) {
			switch(i) {
			case 0:
				userInfoCheck = "원래이름";
				break;
			case 1:
				userInfoCheck = "";
				break;
			case 3:
				userInfoCheck = "9743";
				break;
			case 4:
				userInfoCheck = "0928";
				break;
			case 5:
				userInfoCheck = "apzz0928888@daum.net";
				break;
			}
			inputCheck = $(".gui-input", i).getValue().trim();
			if(inputCheck.equals(userInfoCheck)) {
				System.out.println(" *** leaveService input value(" + i +") check Success !! *** ");
			} else {
				System.out.println(" *** leaveService input value(" + i +") check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- leaveService End ----- ! ");
	}
	@Test(priority = 111)
	public void extendCharge() {
		System.out.println(" ! ----- extendCharge Start ----- ! ");
		$(By.linkText("연장요금")).waitUntil(visible, 10000);
		$(By.linkText("연장요금")).scrollIntoView(false);
		$(By.linkText("연장요금")).click();
		$("#btn_step1_next").waitUntil(visible, 10000);
		pageLoadCheck = $("#headingStep1.panel-heading.active").text().trim();
		String[] pLC = pageLoadCheck.split("수정하기");
		if(pLC[0].equals("1서비스선택")) {
			System.out.println(" *** extendCharge step1 page load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step1 page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_step1_next").click();
		valCheck("extendCharge_service_null");
		$("#checkbox_step1_0").click();
		$("#btn_step1_next").click();
		/*$("#btnSkipScript").waitUntil(visible, 10000);
		$("#btnSkipScript").click();*/
		$("#headingStep2.panel-heading.active").waitUntil(visible, 10000);
		pageLoadCheck = $("#headingStep2.panel-heading.active").text().trim();
		pLC = pageLoadCheck.split(" 확인 및 계약기간 선택");
		if(pLC[0].equals("2요금")) {
			System.out.println(" *** extendCharge step2 page load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step2 page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.id("select_chargelist_0")).click();
		$(By.id("select_chargelist_0")).selectOption("100,001 ~ 200,000");
	    sleep(100);
		pageLoadCheck = $(".text-danger", 0).text().trim();
		$(By.id("select_chargelist_0")).click();
		$(By.id("select_chargelist_0")).selectOption("1 ~ 100,000");
		if(pageLoadCheck.equals("44,000")) { //월 서비스 요금 확인
			System.out.println(" *** extendCharge month service charge check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge month service charge check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-dark", 0).waitUntil(visible, 10000);
		$(".br-dark", 0).click();
		$(".btn-info", 10).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-center", 18).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("적용")) { //쿠폰보기 팝업 확인
			System.out.println(" *** extendCharge coupon popup load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge coupon popup load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 10).click();
		$(".btn-info", 10).waitUntil(hidden, 10000);
		$("#btn_consult").click();
		$("#btn_consult_submit").waitUntil(visible, 10000);
		$("#btn_consult_submit").scrollIntoView(false);
		String extendCharge_check = "";
		String extendCharge_value = "";
		for(int i=0, x=0;i<=4;i++){
			if(i == 0){
				extendCharge_check = "extendCharge_name_null";
			} else if (i == 1) {
				extendCharge_check = "extendCharge_number_null";
				extendCharge_value = "이름";
			} else if (i == 2) {
				extendCharge_value = "1234";
				x = 1;
			} else if (i == 3) {
				extendCharge_check = "extendCharge_email_null";
				extendCharge_value = "5678";
				x = 2;
			} else if (i == 4) {
				extendCharge_check = "extendCharge_email_check";
				extendCharge_value = "mail.com";
				x = 5;
			}
			if(i > 0){
				$(".gui-input", x).setValue(extendCharge_value);
			}
			//System.out.println("입력창 번호 : " + x + " // 입력값 : " + extendCharge_value + " // 체크값 : " + extendCharge_check);
			$("#btn_consult_submit").click();
			valCheck(extendCharge_check);
		}
		$("label", 10).click();
		String inputCheck = "";
		String userInfoCheck = "";
		for(int i=0;i<=30;i++) {
			if($(".gui-input", 0).text() != "원래이름") {
				sleep(100);
			} else {
				break;
			}
		}
		for(int i=0;i<=5;i++) {
			switch(i) {
			case 0:
				userInfoCheck = "원래이름";
				break;
			case 1:
				userInfoCheck = "";
				break;
			case 3:
				userInfoCheck = "9743";
				break;
			case 4:
				userInfoCheck = "0928";
				break;
			case 5:
				userInfoCheck = "apzz0928888@daum.net";
				break;
			}
			inputCheck = $(".gui-input", i).getValue().trim();
			if(inputCheck.equals(userInfoCheck)) {
				System.out.println(" *** extendCharge input value(" + i +") check Success !! *** ");
			} else {
				System.out.println(" *** extendCharge input value(" + i +") check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		/*$("#btn_consult").waitUntil(visible, 15000);
		$("#btn_consult").click();
		$("#btn_consult_submit").waitUntil(hidden, 10000);*/
		$("#btn_step2_next").click();
		$("#headingStep3.panel-heading.active").waitUntil(visible, 10000);
		pageLoadCheck = $("#headingStep3.panel-heading.active").text().trim();
		pLC = pageLoadCheck.split(" 방법 선택 및 요금 결제");
		if(pLC[0].equals("3결제")) {
			System.out.println(" *** extendCharge step3 page load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step3 page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#credit-card").waitUntil(visible, 15000);
		$("#credit-card").scrollIntoView(false);
		pageLoadCheck = $("#credit-card").text().trim();
		pLC = pageLoadCheck.split("\n결제하실 금액\n");
		/*for(int i=0;i<=pLC.length-1;i++) {
			System.out.println("pLC[" + i + "] =  " + pLC[i]);
		}*/
		if(pLC[0].equals("신용카드") && pLC[1].substring(0, 7).equals("66,000원")) {
			System.out.println(" *** extendCharge step3 credit-card payment check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step3 credit-card payment check Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(500);
		$("label", 15).click();
		//$("label", 17).waitUntil(visible, 15000);
		sleep(1500);
		$("#virtual-account").waitUntil(visible, 15000);
		$("#virtual-account").scrollIntoView(false);
		pageLoadCheck = $("#virtual-account").text().trim();
		pLC = pageLoadCheck.split(" 발급 신청\n결제하실 금액\n");
		if(pLC[0].equals("가상계좌") && pLC[1].substring(0, 7).equals("66,000원")) {
			System.out.println(" *** extendCharge step3 virtual-account payment check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step3 virtual-account payment check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("label", 16).click();
		sleep(1000);
		$("#payco").waitUntil(visible, 15000);
		$("#payco").scrollIntoView(false);
		pageLoadCheck = $("#payco").text().trim();
		pLC = pageLoadCheck.split(" 간편결제\n결제하실 금액\n");
		if(pLC[0].equals("페이코") && pLC[1].substring(0, 8).equals("66,000 원")) {
			System.out.println(" *** extendCharge step3 payco payment check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** extendCharge step3 payco payment check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- extendCharge End ----- ! ");
	}
	@Test(priority = 121)
	public void additionalCharge() {
		System.out.println(" ! ----- additionalCharge Start ----- ! ");
		$(By.linkText("추가요금")).waitUntil(visible, 10000);
		$(By.linkText("추가요금")).click();
		$("#btn-next-step").waitUntil(visible, 10000);
		pageLoadCheck = $("td").text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("추가요금")) {
			System.out.println(" *** additionalCharge page load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** additionalCharge page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-next-step").click();
		valCheck("additionalCharge_service_null");
		System.out.println(" ! ----- additionalCharge End ----- ! ");
	}
	@Test(priority = 131)
	public void paymentHistory() {
		System.out.println(" ! ----- paymentHistory Start ----- ! ");
		$(By.linkText("결제내역조회")).waitUntil(visible, 10000);
		$(By.linkText("결제내역조회")).click();
		$("#topbar").waitUntil(visible, 10000);
		pageLoadCheck = $("#topbar").text().trim();
		if(pageLoadCheck.equals("결제내역조회")) {
			System.out.println(" *** paymentHistory page load check Success !! *** ");
		} else {
			System.out.println(" *** paymentHistory page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $(".opened", 0).text().trim();
		String[] pLC = pageLoadCheck.split("\\(");
		if(pLC[1].substring(0, 13).equals("test00011.org")) {
			System.out.println(" *** paymentHistory 1st site url check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** paymentHistory 1st site url check Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $(".opened", 1).text().trim();
		pLC = pageLoadCheck.split("\\(");
		if(pLC[1].substring(0, 16).equals("ap0420121150.com")) {
			System.out.println(" *** paymentHistory 2nd site url check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** paymentHistory 2nd site url check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- paymentHistory End ----- ! ");
	}
	@Test(priority = 141)
	public void paymentBill() {
		System.out.println(" ! ----- paymentBill Start ----- ! ");
		$(By.linkText("계산서")).waitUntil(visible, 10000);
		$(By.linkText("계산서")).click();
		$("#topbar").waitUntil(visible, 10000);
		pageLoadCheck = $("#topbar").text().trim();
		if(pageLoadCheck.equals("계산서")) {
			System.out.println(" *** paymentBill page load check Success !! *** ");
		} else {
			System.out.println(" *** paymentBill page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".w200").click();
		$("#btn-submit").waitUntil(visible, 10000);
		$("#btn-submit").scrollIntoView(false);
		for(int i=0;i<=7;i++) {
			$(".gui-input", i).setValue("");
		}
		String paymentBill_check = "";
		String paymentBill_value = "";
		for(int i=0;i<=2;i++) { //사업자등록번호 유효성체크
			for(int x=0;x<=1;x++) {
				if(x==0) {
					paymentBill_check = "paymentBill_number_null";
					paymentBill_value = "문자";
				} else {
					paymentBill_check = "paymentBill_number_only";
					paymentBill_value = Integer.toString(i) + Integer.toString(i);				
				}
				$("#btn-submit").click();
				valCheck(paymentBill_check);
				$(".gui-input", i).setValue(paymentBill_value);
			}
			paymentBill_check = "";
			paymentBill_value = "";
		}
		String typeA = "paymentBill_char_only";
		String typeB = "paymentBill_numberOrchar_only";
		for(int i=3;i<=8;i++) { //사업자정보 입력 유효성체크 부분 작성해야함
			for(int x=0;x<=1;x++) {
				if(i==3 && x==0) {
					paymentBill_check = "paymentBill_company_null";
					paymentBill_value = "ㅇ";
				} else if (i==3 && x==1){
					paymentBill_check = typeB;
					paymentBill_value = "회사명";
				} else if (i==4 && x==0) {
					paymentBill_check = "paymentBill_ceoname_null";
					paymentBill_value = "ㅇ";
				} else if (i==4 && x==1) {
					paymentBill_check = typeA;
					paymentBill_value = "대표자명";
				} else if (i==5 && x==0) {
					paymentBill_check = "paymentBill_address_null";
					paymentBill_value = "ㅇ";
				} else if (i==5 && x==1) {
					paymentBill_check = typeB;
					paymentBill_value = "회사주소";
				} else if (i==6 && x==0) {
					paymentBill_check = "paymentBill_businessType_null";
					paymentBill_value = "ㅇ";
				} else if (i==6 && x==1) {
					paymentBill_check = typeB;
					paymentBill_value = "업태";
				} else if (i==7 && x==0) {
					paymentBill_check = "paymentBill_businessTypeSection_null";
					paymentBill_value = "ㅇ";
				} else if (i==7 && x==1) {
					paymentBill_check = typeB;
					paymentBill_value = "종목";
				} else if (i==8) {
					paymentBill_check = "paymentBill_check";
					break;
				}
				$("#btn-submit").click();
				valCheck(paymentBill_check);
				if(i<=7){
					$(".gui-input", i).setValue(paymentBill_value);
				}
			}
		}
		$("#btn-submit").click();
		valCheck("paymentBill_check");
		$("#btn-submit").waitUntil(hidden, 10000);
		System.out.println(" ! ----- paymentBill End ----- ! ");
	}
	@Test(priority = 151)
	public void myNoticeList() {
		System.out.println(" ! ----- myNoticeList Start ----- ! ");
		$(By.linkText("서비스 공지사항")).waitUntil(visible, 10000);
		$(By.linkText("서비스 공지사항")).click();
		$("#btn-search").waitUntil(visible, 10000);
		pageLoadCheck = $(".notokr-bold").text().trim();
		if(pageLoadCheck.equals("서비스 공지사항")) {
			System.out.println(" *** myNoticeList page load check Success !! *** ");
		} else {
			System.out.println(" *** myNoticeList page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td").text().trim();
		if(pageLoadCheck.equals("데이터가 없습니다.")) {
			System.out.println(" *** myNoticeList search check Success !! *** ");
		} else {
			System.out.println(" *** myNoticeList search check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue("");
		$("#btn-search").click();
		$(".post-title", 0).click();
		$("#list").waitUntil(visible, 10000);
		pageLoadCheck = $("#list").text().trim();
		if(pageLoadCheck.equals("목록")) {
			System.out.println(" *** myNoticeList detailView page load check Success !! *** ");
		} else {
			System.out.println(" *** myNoticeList detailView page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#list").click();
		$("#btn-search").waitUntil(visible, 10000);
		$(By.linkText("개선안내")).waitUntil(visible, 10000);
		$(By.linkText("개선안내")).click();
		$("#btn-search").waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 1).text().trim();
		if(pageLoadCheck.equals("개선안내")) {
			System.out.println(" *** myImproveList page load check Success !! *** ");
		} else {
			System.out.println(" *** myImproveList page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td").text().trim();
		if(pageLoadCheck.equals("데이터가 없습니다.")) {
			System.out.println(" *** myImproveList search check Success !! *** ");
		} else {
			System.out.println(" *** myImproveList search check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue("");
		$("#btn-search").click();
		$(".post-title", 0).click();
		$("#list").waitUntil(visible, 10000);
		pageLoadCheck = $("#list").text().trim();
		if(pageLoadCheck.equals("목록")) {
			System.out.println(" *** myImproveList detailView page load check Success !! *** ");
		} else {
			System.out.println(" *** myImproveList detailView page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#list").click();
		$("#btn-search").waitUntil(visible, 10000);
		System.out.println(" ! ----- myNoticeList End ----- ! ");
	}
	@Test(priority = 161)
	public void myAllimList() {
		System.out.println(" ! ----- myAllimList Start ----- ! ");
		$(By.linkText("서비스 알림내역")).waitUntil(visible, 10000);
		$(By.linkText("서비스 알림내역")).click();
		pageLoadCheck = $(".notokr-bold").text().trim();
		if(pageLoadCheck.equals("서비스 알림내역")) {
			System.out.println(" *** myAllimList page load check Success !! *** ");
		} else {
			System.out.println(" *** myAllimList page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td").text().trim();
		if(pageLoadCheck.equals("데이터가 없습니다.")) {
			System.out.println(" *** myAllimList search check Success !! *** ");
		} else {
			System.out.println(" *** myAllimList search check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue("");
		$("#btn-search").click();
		$(".text-left", 0).click();
		$(".mh20", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".mh20", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("고객님께서")) {
			System.out.println(" *** myAllimList detailView check Success !! *** ");
		} else {
			System.out.println(" *** myAllimList detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.linkText("모니터링내역")).waitUntil(visible, 10000);
		$(By.linkText("모니터링내역")).click();
		pageLoadCheck = $(".active", 1).text().trim();
		if(pageLoadCheck.equals("모니터링내역")) {
			System.out.println(" *** myMonitoringList page load check Success !! *** ");
		} else {
			System.out.println(" *** myMonitoringList page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 0).text().trim();
		if(pageLoadCheck.equals("데이터가 없습니다.")) {
			System.out.println(" *** myMonitoringList search check Success !! *** ");
		} else {
			System.out.println(" *** myMonitoringList search check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("s_key")).setValue("");
		$("#btn-search").click();
		System.out.println(" ! ----- myAllimList End ----- ! ");
		open("http://apzz092888.blogspot.com"); //3일 이상 수집데이터 없으면 알람 내용 변경되서 추가함
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}