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

public class temporarily_2 {
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
		case "subManager_email_send":
			checkMsg = "이메일이 발송되었습니다.\n" + "\n" + "발송된 이메일의 회원가입 링크 유효기간은 총 7일로\n" + "7일 이내 미가입 시 유효기간이 종료됩니다.";
			break;
		case "submanager_authority_modify_check":
			checkMsg = "수정이 완료되었습니다.";
			break;
		case "subManager_pw_alert":
			checkMsg = "비밀번호 변경이 완료되었습니다.";
			break;
		case "subManager_delete":
			checkMsg = "삭제가 완료되었습니다.";
			break;
		}
		$(".modal-backdrop").waitUntil(visible, 10000);
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	        	//confirm이 없어서 제거
	        } else { //confirm 아니면 .btn-sm클릭
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            //$(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert 로딩이 늦거나 노출되지 않았을때 체크하기위해 빈값 체크
	        System.out.println(" *** ☆★☆★☆★ " + val +  " - msgCheck is Empty ... ☆★☆★☆★ *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** " + val +  " - check Fail ... !@#$%^&*() *** ");
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
		pageLoadCheck = $(".notokr-bold", 0).text();
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
		$("#items").waitUntil(visible, 15000);
		$(".br-dark").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		pageLoadCheck = $(".modal-title").text();
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
		pageLoadCheck = $("h1").text();
		if (pageLoadCheck.equals("Daum\n" + "메일")) {
			System.out.println(" *** scriptList daum mail list page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList daum mail list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).waitUntil(visible, 15000);
		$(".tit_subject", 0).click();
		$(".txt_filename").waitUntil(visible, 15000);
		pageLoadCheck = $(".txt_filename").text();
		if (pageLoadCheck.equals("script(ap0420121150.com).zip")) {
			System.out.println(" *** scriptList send mail fileName check Success !! *** ");
		} else {
			System.out.println(" *** scriptList send mail fileName check Fail ... !@#$%^&*() *** ");
			close();
		}
		//메일삭제
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		//$(".link_basket").click();
		$(By.linkText("휴지통")).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".btn_toolbar", 0).click();
		sleep(1500);
		//$(".check_type2").waitUntil(visible, 15000);
		$(".check_type2").click();
		$(".link_mail", 2).click();
		switchTo().window(0);
		pageLoadCheck = $("#scriptList").text();
		if (pageLoadCheck.equals("분석스크립트")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121331").waitUntil(visible, 15000);
		$("#scroll_target_121331").click();
		$(".text-danger", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-danger", 0).text();
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

	@Test(priority = 11)
	public void installApply() {
		System.out.println(" ! ----- installApply Start ----- ! ");
		$(By.linkText("분석스크립트 설치신청")).click();
		pageLoadCheck = $(".panel-title", 0).text();
		if (pageLoadCheck.equals("1서비스 선택")) {
			System.out.println(" *** installApply page load Success !! *** ");
		} else {
			System.out.println(" *** installApply page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_next_step1").click();
		valCheck("installApply_null");
		$("#checkbox_svc_0").click();
		$("#btn_next_step1").click();
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_name_null");
		$("#applicant_nm").scrollTo();
		$("#applicant_nm").setValue("name");
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_email_null");
		$("#applicant_email").scrollTo();
		$("#applicant_email").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_email_validation");
		$("#applicant_email").scrollTo();
		$("#applicant_email").setValue(date + "@net.com");
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_FTP_null");
		$("#host_0").scrollTo();
		$("#host_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_port_null");
		$("#port_0").scrollTo();
		$("#port_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_id_null");
		$("#id_0").scrollTo();
		$("#id_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_pw_null");
		$("#pwd_0").scrollTo();
		$("#pwd_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck("installApply_agree_null");
		System.out.println(" ! ----- installApply End ----- ! ");
	}

	@Test(priority = 21)
	public void memberInfo() {
		System.out.println(" ! ----- memberInfo Start ----- ! ");
		$(By.linkText("회원정보")).click();
		$("h3", 2).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("비밀번호 재확인")) {
			System.out.println(" *** memberInfo Recongirming page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo Recongirming page load Fail ... !@#$%^&*() *** ");			
		}
		$("#pwd").setValue(pw + A);
		$("#btn-ok").click();
		$("h3", 2).waitUntil(visible, 15000);
		System.out.println(" *** Password change Page access Success !! *** ");
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
			$("#changePwd").setValue(pw1);
			$("#changePwdConfirm").setValue(pw1);
			$("#modifyProc").click();
			$(".modal-backdrop").waitUntil(visible, 15000);
			$("#btn-modal-alert-yes").click();
			$(".modal-backdrop").waitUntil(visible, 15000);
			String mbn = $(".mbn").text();
			if(mbn.equals("비밀번호 변경이 완료되었습니다.")) {
				System.out.println(" *** Change Password(" + i + ") Success !! *** ");
				$("#okButton").click();
				$(".modal-backdrop").waitUntil(hidden, 10000);
			} else {
				System.out.println(" *** Change Password(" + i + ") Fail ... !@#$%^&*() *** ");
				close();
			}
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
	    String modalBody = $(".modal-body", 1).text();
		$(".modal-backdrop").waitUntil(visible, 15000);
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
	    $("#s_name").waitUntil(visible, 15000);
		sleep(500);
	    $("#s_name").setValue("원래이름");
		$("#s_company").setValue("원래회사명");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz092888@daum.net");
	    $(".btn-lg", 1).click();
	    $(".modal-dialog").waitUntil(visible, 15000);
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

	@Test(priority = 31)
	public void myCoupon() {
		System.out.println(" ! ----- myCoupon Start ----- ! ");
		sleep(1000);
		$(By.linkText("쿠폰관리")).click();
		$("#btn-save").waitUntil(visible, 15000);
		pageLoadCheck = $("#btn-save").text();
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
		valCheck("myCoupon_5_null");
		System.out.println(" ! ----- myCoupon End ----- ! ");
	}

	@Test(priority = 41)
	public void addService() {
		System.out.println(" ! ----- addService Start ----- ! ");
		$(By.linkText("서비스추가")).click();
		$("#btn_submit").waitUntil(visible, 15000);
		pageLoadCheck = $("#btn_submit").text();
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
		valCheck("addService_siteGroup2_null");
		$(By.name("middleCategoryCd")).click();
		$(By.xpath("//option[@value='188']")).click();
		$("label", 12).click();
		$("#btn_submit").click();
		valCheck("addService_coupon_null");
		System.out.println(" ! ----- addService End ----- ! ");
	}

	@Test(priority = 51)
	public void addView() {
		System.out.println(" ! ----- addView Start ----- ! ");
		$(By.linkText("뷰필터 추가")).click();
		$(".div_not_paid").waitUntil(visible, 15000);
		pageLoadCheck = $(".div_not_paid").text();
		if (pageLoadCheck.equals("뷰필터는 유료 서비스 전환 후에 추가 하실 수 있습니다.")) {
			System.out.println(" *** addView page load Success !! *** ");
		} else {
			System.out.println(" *** addView page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- addView End ----- ! ");
	}

	@Test(priority = 61)
	public void addIntegralReport() {
		System.out.println(" ! ----- addIntegralReport Start ----- ! ");
		$(By.linkText("통합리포트 생성")).click();
		$(".nano-content", 2).waitUntil(visible, 15000);
		pageLoadCheck = $(".nano-content", 2).text();
		if (pageLoadCheck.equals("이용중인 서비스가 없습니다.")) {
			System.out.println(" *** addIntegralReport page load Success !! *** ");
		} else {
			System.out.println(" *** addIntegralReport page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).scrollTo();
		$(".btn-info", 0).click();
		valCheck("addIntegralReport_name_null");
		$("#report_nm").scrollTo();
		$("#report_nm").setValue(date);
		$(".btn-info", 0).scrollTo();
		$(".btn-info", 0).click();
		valCheck("addIntegralReport_service_null");
		System.out.println(" ! ----- addIntegralReport End ----- ! ");
	}

	@Test(priority = 71)
	public void editService() {
		System.out.println(" ! ----- editService Start ----- ! ");
		$(By.linkText("정보수정")).click();
		$("#svc_nm_title_1").waitUntil(visible, 15000);
		pageLoadCheck = $(".btn-info", 0).text();
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
		sleep(500);
		//$(".btn-info", 1).waitUntil(hidden, 10000);
		$(".btn-info", 1).waitUntil(visible, 15000);
		pageLoadCheck = $("#svc_nm_title_0").text();
		if (pageLoadCheck.substring(21, 33).equals(date)) {
			System.out.println(" *** editService edit Success !! *** ");
		} else {
			System.out.println(" *** editService edit Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".cross", 0).click();
		$(".gui-input", 1).setValue("ap0420121150.com");
		$(".br-dark", 0).click();
		$(".cross", 0).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck("editService_restore_alert");
		$(".btn-info", 1).waitUntil(visible, 15000);
		sleep(1000);
		pageLoadCheck = $("#svc_nm_title_0").text();
		if (pageLoadCheck.substring(21, 33).equals("ap0420121150")) {
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
		$(By.linkText("발송메일 설정")).click();
		$("#btn-sendMail").waitUntil(visible, 15000);
		$(".cross", 0).click();
		$(".sendEmail").waitUntil(hidden, 10000);
		$("#btn-sendMail").click();
		valCheck("summaryReport_sendEmail_null");
		$(".gui-input", 0).setValue("apzz092888@");
		$("#btn-sendEmail").click();
		valCheck("summaryReport_sendEmail_check");
		$(".gui-input", 0).setValue("apzz092888@daum.net");
		$("#btn-sendEmail").click();
		$(".sendEmail").waitUntil(visible, 15000);
		$("#btn-sendMail").click();
		$(".modal-center", 5).waitUntil(visible, 15000);
		valCheck("summaryReport_sendEmail_send");
		switchTo().window(1);
		refresh();
		pageLoadCheck = $(".tit_subject", 0).text();
		if (pageLoadCheck.substring(15, 22).equals("주간요약리포트")) {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		$("h1", 1).waitUntil(visible, 15000);
		pageLoadCheck = $("h1", 1).text();
		if (pageLoadCheck.equals("주간요약 리포트입니다.")) {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		//메일삭제
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		/*$(By.linkText("휴지통"), 0).hover();
		$(".link_empty", 1).click();*/
		//$(".link_basket").click();
		$(By.linkText("휴지통")).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".btn_toolbar", 0).click();
		$(".check_type2").waitUntil(visible, 15000);
		$(".check_type2").click();
		$(".link_mail", 2).click();
		switchTo().window(0);
		$("#btn-save").scrollTo();
		$(".cross", 1).click();
		$(".reserveEmail").waitUntil(hidden, 10000);
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_null");
		$(".gui-input", 3).setValue("apzz092888@");
		$("#btn-reserveEmail").click();
		valCheck("summaryReport_reserveEmail_check");
		$(".gui-input", 3).setValue("apzz092888@daum.net");
		$("#btn-reserveEmail").click();
		$(".reserveEmail").waitUntil(visible, 15000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 15000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck("summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 15000);
		System.out.println(" ! ----- summaryReport End ----- ! ");
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
		$("#submanager_nm").setValue("최영권");
		$("#btn_mail").click();
		valCheck("subManager_email_null");
		$("#submanager_email").setValue("apzz092888@");
		$("#btn_mail").click();
		valCheck("subManager_email_check");
		$("#submanager_email").setValue("apzz092888@daum.net");
		$("#btn_mail").click();
		valCheck("subManager_service_null");
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='*'])[3]/following::button[1]")).click();
	    $(By.id("treeDemo_2_check")).click();
	    $(By.id("select_auth")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='서비스추가해지테스트0001(test00011.org)'])[2]/following::option[3]")).click();
	    $("#btn_add_svc").click();
		$("#btn_mail").click();
		valCheck("subManager_email_send");
		switchTo().window(1);
		refresh();
		sleep(2000);
		pageLoadCheck = $(".tit_subject", 0).text();
		if(pageLoadCheck.substring(22).equals("부관리자 안내 메일입니다.")) {
			System.out.println(" *** subManager guide mail title Check Success !! *** ");
		} else {
			System.out.println(" *** subManager guide mail title Check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		pageLoadCheck = $("h1", 1).text();
		if (pageLoadCheck.equals("부관리자 회원가입 안내")) {
			System.out.println(" *** subManager signin mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** subManager signin mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
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
		//메일삭제
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("휴지통")).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".btn_toolbar", 0).click();
		sleep(1500);
		$(".check_type2").click();
		$(".link_mail", 2).click();
		switchTo().window(0);
		refresh();
		pageLoadCheck = $(".text-dark", 3).text();
		if(pageLoadCheck.substring(0, 4).equals("가입완료")) {
			System.out.println(" *** subManager_signUp Check Success !! *** ");
		} else {
			System.out.println(" *** subManager_signUp Check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".indicator").click();
		$(".btn-info", 1).waitUntil(visible, 15000);
	    $(By.id("select_added_auth_11000")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='[Web Free]'])[5]/following::option[1]")).click();
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
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A); 
		$(".btn_login").click();
		open("https://new.acecounter.com/manage/serviceInfo/submanager");
		$(".br-dark", 2).click();
		$("#btn-modal-alert-yes").waitUntil(visible, 15000);
		//$("#btn-modal-alert-yes").click();
		valCheck("subManager_delete");
		System.out.println(" ! ----- subManager_modifyInfoAndDel End ----- ! ");
	}
	
	//@Test(priority = 101)
	public void leaveService() {
		System.out.println(" ! ----- subManager_modifyInfoAndDel Start ----- ! ");
		$(By.linkText("서비스 해지")).click();
		
		System.out.println(" ! ----- subManager_modifyInfoAndDel End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}