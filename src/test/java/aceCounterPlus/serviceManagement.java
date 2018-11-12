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

public class serviceManagement {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;

	// �ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}

	public static void valCheck(int pTagNum, int btnNum, String val) {
	    $(".modal-backdrop").waitUntil(visible, 10000);
		$("p", pTagNum).click();
	    String msgCheck = $("p", pTagNum).text();
		switch (val) {
		case "scriptList_email_null":
			checkMsg = "���� �̸����� �Է��� �ּ���.";
			break;
		case "scriptList_email_validation":
			checkMsg = "�̸��� �ּҰ� �ùٸ��� �ʽ��ϴ�.";
			break;
		case "scriptList_email_send":
			checkMsg = "��ġ�ȳ� ������ �߼۵Ǿ����ϴ�.";
			break;
		case "installApply_null":
			checkMsg = "��û�� ���񽺸� ������ �ּ���.";
			break;
		case "installApply_name_null":
			checkMsg = "�̸��� �Է��� �ּ���.";
			break;
		case "installApply_email_null":
			checkMsg = "�̸����� �Է��� �ּ���.";
			break;
		case "installApply_email_validation":
			checkMsg = "�̸��� ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "installApply_FTP_null":
			checkMsg = "FTP������ �Է��� �ּ���.";
			break;
		case "installApply_port_null":
			checkMsg = "��Ʈ��ȣ�� �Է��� �ּ���.";
			break;
		case "installApply_id_null":
			checkMsg = "���̵� �Է��� �ּ���.";
			break;
		case "installApply_pw_null":
			checkMsg = "��й�ȣ�� �Է��� �ּ���.";
			break;
		case "installApply_agree_null":
			checkMsg = "�������� ���� �� �̿뿡 ���� �ȳ��� ������ �ּ���.";
			break;
		case "myCoupon_1_null":
			checkMsg = "������ȣ�� �Է����ּ���.";
			break;
		case "myCoupon_2_null":
			checkMsg = "������ȣ�� �Է����ּ���.";
			break;
		case "myCoupon_3_null":
			checkMsg = "������ȣ�� �Է����ּ���.";
			break;
		case "myCoupon_4_null":
			checkMsg = "������ȣ�� �Է����ּ���.";
			break;
		case "myCoupon_5_null":
			checkMsg = "������ȣ�� ���� �ʽ��ϴ�.\n" + "�ٽ� Ȯ���� �ּ���.";
			break;
		case "editService_modify_check":
			checkMsg = "����� ������ �����ϴ�.";
			break;
		case "addService_siteName_null":
			checkMsg = "������Ʈ �̸��� �Է����ּ���.";
			break;
		case "addService_domain_null":
			checkMsg = "��ϵ� �������� �����ϴ�.";
			break;
		case "addService_domain_validation":
			checkMsg = "������ ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "addService_domain_duplication":
			checkMsg = "�̹� �߰��� �������Դϴ�.";
			break;
		case "addService_siteGroup1_null":
			checkMsg = "������Ʈ �з��� �������ּ���.";
			break;
		case "addService_siteGroup2_null":
			checkMsg = "������Ʈ �з��� �������ּ���.";
			break;
		case "addService_coupon_null":
			checkMsg = "������ȣ�� ���� �ʽ��ϴ�.\n" + "�ٽ� Ȯ���� �ּ���.";
			break;
		case "addIntegralReport_name_null":
			checkMsg = "���ո���Ʈ �̸��� �Է��� �ּ���.";
			break;
		case "addIntegralReport_service_null":
			checkMsg = "2�� �̻��� ���񽺸� ������ �ּ���.";
			break;
		case "editService_siteName_null":
			checkMsg = "������Ʈ �̸��� �Է����ּ���.";
			break;
		case "editService_domain_null":
			checkMsg = "��ϵ� �������� �����ϴ�.";
			break;
		case "editService_domain_validation":
			checkMsg = "������ ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "editService_edit_alert":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
		case "editService_restore_alert":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
		case "summaryReport_sendEmail_null":
			checkMsg = "���� �̸����� �߰��� �ּ���.";
			break;
		case "summaryReport_sendEmail_check":
			checkMsg = "�ùٸ� �̸����� �Է��ϼ���.";
			break;
		case "summaryReport_sendEmail_send":
			checkMsg = "��ฮ��Ʈ�� �߼۵Ǿ����ϴ�.";
			break;
		case "summaryReport_reserveEmail_null":
			checkMsg = "���� �̸����� �߰��� �ּ���.";
			break;
		case "summaryReport_reserveEmail_check":
			checkMsg = "�ùٸ� �̸����� �Է��ϼ���.";
			break;
		case "summaryReport_reserveEmail_send":
			checkMsg = "�����Ͻ� ������ ����Ǿ����ϴ�.\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.";
			break;
		case "subManager_name_null":
			checkMsg = "����� �̸��� �Է��� �ּ���.";
			break;
		case "subManager_email_null":
			checkMsg = "����� �̸����� �Է��� �ּ���.";
			break;
		case "subManager_email_check":
			checkMsg = "�̸��� ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "subManager_service_null":
			checkMsg = "���񽺸� �߰��� �ּ���.";
			break;
		case "subManager_email_send":
			checkMsg = "�̸����� �߼۵Ǿ����ϴ�.\n" + "\n" + "�߼۵� �̸����� ȸ������ ��ũ ��ȿ�Ⱓ�� �� 7�Ϸ�\n" + "7�� �̳� �̰��� �� ��ȿ�Ⱓ�� ����˴ϴ�.";
			break;
		case "submanager_authority_modify_check":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
		case "subManager_pw_alert":
			checkMsg = "��й�ȣ ������ �Ϸ�Ǿ����ϴ�.";
			break;
		case "subManager_delete":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
		}
		Thread.onSpinWait();
		if (msgCheck.equals(checkMsg)) {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
			if (!val.split("_")[0].equals("scriptList")) {
				$(".modal-backdrop").waitUntil(hidden, 10000);
			} else {

			}
		} else if (msgCheck.isEmpty()) {
			System.out.println(" *** �١ڡ١ڡ١� pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val	+ " - msgCheck is Empty ... �١ڡ١ڡ١� *** ");
			close();
		} else {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val + " - check Fail ... !@#$%^&*() *** ");
			System.out.println(msgCheck);
			System.out.println("-------------------------------");
			System.out.println(checkMsg);
			close();
		}
	}

	// �Էµ� URL ���� ���� Ȯ��
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
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if (loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_setting").click();
		$(".notokr-bold", 0).waitUntil(visible, 1000);
		String pageLoadCheck = $(".notokr-bold", 0).text();
		if (pageLoadCheck.equals("���� ����")) {
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
		$(By.linkText("�м���ũ��Ʈ")).click();
		$("#items").waitUntil(visible, 15000);
		$(".br-dark").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		String pageLoadCheck = $(".modal-title").text();
		if (pageLoadCheck.equals("�м���ũ��Ʈ ���Ϲ߼�")) {
			System.out.println(" *** scriptList layerPopup load Success !! *** ");
		} else {
			System.out.println(" *** scriptList layerPopup load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-dark", 1).click();
		valCheck(12, 3, "scriptList_email_null");
		$("#mail_to").setValue(date);
		$(".btn-dark", 1).click();
		valCheck(13, 4, "scriptList_email_validation");
		$("#mail_to").setValue("apzz092888@daum.net");
		$(".btn-dark", 1).click();
		$(".btn-dark", 1).waitUntil(hidden, 10000);
		valCheck(14, 5, "scriptList_email_send");
		js("window.open('https://logins.daum.net/accounts/loginform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		//�������� ������ ��Ŀ�� ����
		switchTo().window(1);
		$("#id").setValue("apzz092888");
		$("#inputPwd").setValue(pw);
		$("#loginBtn").click();
		$(".link_check").waitUntil(visible, 15000);
		sleep(1000);
		refresh();
		pageLoadCheck = $("h1").text();
		if (pageLoadCheck.equals("Daum\n" + "����")) {
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
		//���ϻ���
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		//$(".link_basket").click();
		$(By.linkText("������")).click();
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
		if (pageLoadCheck.equals("�м���ũ��Ʈ")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121331").waitUntil(visible, 15000);
		$("#scroll_target_121331").click();
		$(".text-danger", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-danger", 0).text();
		if (pageLoadCheck.equals("������ ����/�м�����")) {
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
		$(By.linkText("�м���ũ��Ʈ ��ġ��û")).click();
		String pageLoadCheck = $(".panel-title", 0).text();
		if (pageLoadCheck.equals("1���� ����")) {
			System.out.println(" *** installApply page load Success !! *** ");
		} else {
			System.out.println(" *** installApply page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn_next_step1").click();
		valCheck(20, 4, "installApply_null");
		$("#checkbox_svc_0").click();
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
		$("#host_0").scrollTo();
		$("#host_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(26, 9, "installApply_port_null");
		$("#port_0").scrollTo();
		$("#port_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(27, 10, "installApply_id_null");
		$("#id_0").scrollTo();
		$("#id_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(28, 11, "installApply_pw_null");
		$("#pwd_0").scrollTo();
		$("#pwd_0").setValue(date);
		$("#btn_submit").scrollTo();
		$("#btn_submit").click();
		valCheck(29, 12, "installApply_agree_null");
		System.out.println(" ! ----- installApply End ----- ! ");
	}

	@Test(priority = 21)
	public void memberInfo() {
		System.out.println(" ! ----- memberInfo Start ----- ! ");
		$(By.linkText("ȸ������")).click();
		$("h3", 2).waitUntil(visible, 15000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("��й�ȣ ��Ȯ��")) {
			System.out.println(" *** memberInfo Recongirming page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo Recongirming page load Fail ... !@#$%^&*() *** ");			
		}
		$("#pwd").setValue(pw);
		$("#btn-ok").click();
		$("h3", 2).waitUntil(visible, 15000);
		System.out.println(" *** Password change Page access Success !! *** ");
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		$("#btn-modal-alert-yes").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		String mbn = $(".mbn").text();
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
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
		$(".modal-backdrop").waitUntil(visible, 15000);
		$("#btn-modal-alert-yes").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		if(mbn.equals("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** Restoration Password Success !! *** ");
			$("#okButton").click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** Restoration Password Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='011']")).click();
	    $("#s_hp2").setValue("0928");
	    $("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@naver.com");
	    $(".btn-lg", 1).click();
	    String modalBody = $(".modal-body", 1).text();
		$(".modal-backdrop").waitUntil(visible, 15000);
		sleep(500);
	    if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
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
	    $("#s_name").setValue("�����̸�");
		$("#s_company").setValue("����ȸ���");
		$(By.name("s_hp1")).click();
	    $(By.xpath("//option[@value='010']")).click();
	    $("#s_hp2").setValue("9743");
	    $("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928@gmail.com");
	    $(".btn-lg", 1).click();
	    $(".modal-dialog").waitUntil(visible, 15000);
		if(modalBody.equals("ȸ�������� �����Ǿ����ϴ�.")) {
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
		$(By.linkText("��������")).click();
		$("#btn-save").waitUntil(visible, 15000);
		String pageLoadCheck = $("#btn-save").text();
		if (pageLoadCheck.equals("�������")) {
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

	@Test(priority = 41)
	public void addService() {
		System.out.println(" ! ----- addService Start ----- ! ");
		$(By.linkText("�����߰�")).click();
		$("#btn_submit").waitUntil(visible, 15000);
		String pageLoadCheck = $("#btn_submit").text();
		if (pageLoadCheck.equals("����ϱ�")) {
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
		$(".cross").waitUntil(visible, 15000);
		$(".gui-input", 1).setValue(date + ".com");
		$(".ace-btn-add-domain").click();
		valCheck(13, 8, "addService_domain_duplication");
		$("#btn_submit").click();
		valCheck(14, 9, "addService_siteGroup1_null");
		$(".br-dark", 1).click();
		$("h3", 3).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 3).text();
		if (pageLoadCheck.equals("�з���ü����")) {
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

	@Test(priority = 51)
	public void addView() {
		System.out.println(" ! ----- addView Start ----- ! ");
		$(By.linkText("������ �߰�")).click();
		$(".div_not_paid").waitUntil(visible, 15000);
		String pageLoadCheck = $(".div_not_paid").text();
		if (pageLoadCheck.equals("�����ʹ� ���� ���� ��ȯ �Ŀ� �߰� �Ͻ� �� �ֽ��ϴ�.")) {
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
		$(By.linkText("���ո���Ʈ ����")).click();
		$(".nano-content", 2).waitUntil(visible, 15000);
		String pageLoadCheck = $(".nano-content", 2).text();
		if (pageLoadCheck.equals("�̿����� ���񽺰� �����ϴ�.")) {
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

	@Test(priority = 71)
	public void editService() {
		System.out.println(" ! ----- editService Start ----- ! ");
		$(By.linkText("��������")).click();
		$("#svc_nm_title_1").waitUntil(visible, 15000);
		String pageLoadCheck = $(".btn-info", 0).text();
		if (pageLoadCheck.equals("���� �߰�")) {
			System.out.println(" *** editService page load Success !! *** ");
		} else {
			System.out.println(" *** editService page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#svc_nm_title_0").click();
		$(".btn-info", 1).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck(15, 5, "editService_modify_check");
		$(".input-sm", 0).setValue("");
		$(".cross", 0).click();
		$(".btn-info", 1).click();
		valCheck(16, 6, "editService_siteName_null");
		$(".input-sm", 0).setValue(date);
		$(".btn-info", 1).click();
		valCheck(17, 7, "editService_domain_null");
		$(".gui-input", 1).setValue(date);
		$(".br-dark", 0).click();
		valCheck(18, 8, "editService_domain_validation");
		$(".gui-input", 1).setValue(date + ".com");
		$(".br-dark", 0).click();
		$(".cross", 0).waitUntil(visible, 15000);
		$(".btn-info", 1).click();
		valCheck(19, 9, "editService_edit_alert");
		sleep(500);
		//$(".btn-info", 1).waitUntil(hidden, 10000);
		$(".btn-info", 1).waitUntil(visible, 15000);
		pageLoadCheck = $("#svc_nm_title_0").text();
		System.out.println("date is " + date);
		System.out.println("svc_nm_title_0 is " + pageLoadCheck);
		System.out.println("svc_nm_title_0 (substring 21, 33) is " + pageLoadCheck.substring(21, 33));
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
		valCheck(15, 5, "editService_restore_alert");
		$(".btn-info", 1).waitUntil(visible, 15000);
		sleep(1000);
		pageLoadCheck = $("#svc_nm_title_0").text();
		System.out.println("date is " + date);
		System.out.println("svc_nm_title_0 is " + pageLoadCheck);
		System.out.println("svc_nm_title_0 (substring 21, 33) is " + pageLoadCheck.substring(21, 33));
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
		$(By.linkText("�߼۸��� ����")).click();
		$("#btn-sendMail").waitUntil(visible, 15000);
		$(".cross", 0).click();
		$(".sendEmail").waitUntil(hidden, 10000);
		$("#btn-sendMail").click();
		valCheck(6, 5, "summaryReport_sendEmail_null");
		$(".gui-input", 0).setValue("apzz092888@");
		$("#btn-sendEmail").click();
		valCheck(7, 6, "summaryReport_sendEmail_check");
		$(".gui-input", 0).setValue("apzz092888@daum.net");
		$("#btn-sendEmail").click();
		$(".sendEmail").waitUntil(visible, 15000);
		$("#btn-sendMail").click();
		$(".modal-center", 5).waitUntil(visible, 15000);
		valCheck(8, 7, "summaryReport_sendEmail_send");
		switchTo().window(1);
		refresh();
		String pageLoadCheck = $(".tit_subject", 0).text();
		if (pageLoadCheck.substring(15, 22).equals("�ְ���ฮ��Ʈ")) {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail subject check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		$("h1", 1).waitUntil(visible, 15000);
		pageLoadCheck = $("h1", 1).text();
		if (pageLoadCheck.equals("�ְ���� ����Ʈ�Դϴ�.")) {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** scriptList weeklySummary Report mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		//���ϻ���
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		/*$(By.linkText("������"), 0).hover();
		$(".link_empty", 1).click();*/
		//$(".link_basket").click();
		$(By.linkText("������")).click();
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
		valCheck(6, 5, "summaryReport_reserveEmail_null");
		$(".gui-input", 3).setValue("apzz092888@");
		$("#btn-reserveEmail").click();
		valCheck(7, 6, "summaryReport_reserveEmail_check");
		$(".gui-input", 3).setValue("apzz092888@daum.net");
		$("#btn-reserveEmail").click();
		$(".reserveEmail").waitUntil(visible, 15000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck(8, 7, "summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 15000);
		for (int i = 18; i <= 25; i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		valCheck(6, 5, "summaryReport_reserveEmail_send");
		$("#btn-save").waitUntil(visible, 15000);
		System.out.println(" ! ----- summaryReport End ----- ! ");
	}

	@Test(priority = 91)
	public void add_subManager() {
		System.out.println(" ! ----- add_subManager Start ----- ! ");
		sleep(1000);
		$(By.linkText("�ΰ�����")).waitUntil(visible, 10000);
		$(By.linkText("�ΰ�����")).click();
		$("#btn_mail").waitUntil(visible, 10000);
		$("#btn_mail").click();
		valCheck(5, 7, "subManager_name_null");
		$("#submanager_nm").setValue("�ֿ���");
		$("#btn_mail").click();
		valCheck(6, 8, "subManager_email_null");
		$("#submanager_email").setValue("apzz092888@");
		$("#btn_mail").click();
		valCheck(7, 9, "subManager_email_check");
		$("#submanager_email").setValue("apzz092888@daum.net");
		$("#btn_mail").click();
		valCheck(8, 10, "subManager_service_null");
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='*'])[3]/following::button[1]")).click();
	    $(By.id("treeDemo_2_check")).click();
	    $(By.id("select_auth")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='�����߰������׽�Ʈ0001(test00011.org)'])[2]/following::option[3]")).click();
	    $("#btn_add_svc").click();
		$("#btn_mail").click();
		valCheck(9, 11, "subManager_email_send");
		switchTo().window(1);
		refresh();
		sleep(2000);
		String pageLoadCheck = $(".tit_subject", 0).text();
		if(pageLoadCheck.substring(22).equals("�ΰ����� �ȳ� �����Դϴ�.")) {
			System.out.println(" *** subManager guide mail title Check Success !! *** ");
		} else {
			System.out.println(" *** subManager guide mail title Check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).click();
		pageLoadCheck = $("h1", 1).text();
		if (pageLoadCheck.equals("�ΰ����� ȸ������ �ȳ�")) {
			System.out.println(" *** subManager signin mail detailView check Success !! *** ");
		} else {
			System.out.println(" *** subManager signin mail detailView check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.linkText("�ΰ����� ȸ������ �ٷΰ���")).click();
		switchTo().window(2);
		$("label", 0).waitUntil(visible, 15000);
		$("label", 0).click();
		$("#userid").setValue(id + date2);
		$("#recheck").click();
		$("#userpw").setValue(pw);
		$("#repeatpw").setValue(pw);
		$(".btn_join").click();
		$(".btn_pop_submit").click();
		switchTo().window(1);
		//���ϻ���
		$(".link_mail", 2).click();
		$(".ico_check", 0).waitUntil(visible, 15000);
		$(".link_page", 0).click();
		$(".ico_check", 0).click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("������")).click();
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
		if(pageLoadCheck.substring(0, 4).equals("���ԿϷ�")) {
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
		valCheck(6, 13, "submanager_authority_modify_check");
		System.out.println(" ! ----- add_subManager End ----- ! ");
	}
	
	@Test(priority = 92)
	public void subManager_modifyInfoAndDel() {
		System.out.println(" ! ----- subManager_modifyInfoAndDel Start ----- ! ");
		open("https://new.acecounter.com/auth/logout");
		$("#uid").waitUntil(visible, 15000);
		$("#uid").setValue(id + date2);
		$("#upw").setValue(pw);
		$(".btn_login").click();
		$(".go_setting").waitUntil(visible, 10000);
		$(".go_setting").click();
		$("#prePwd").setValue(pw);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").waitUntil(visible, 15000);
		$("#btn-modal-alert-yes").click();
		$("p", 4).waitUntil(visible, 15000);
		valCheck(4, 3, "subManager_pw_alert");
		open("https://new.acecounter.com/auth/logout");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		open("https://new.acecounter.com/manage/serviceInfo/submanager");
		$(".br-dark", 2).click();
		$("#btn-modal-alert-yes").waitUntil(visible, 15000);
		$("#btn-modal-alert-yes").click();
		valCheck(6, 13, "subManager_delete");
		System.out.println(" ! ----- subManager_modifyInfoAndDel End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}