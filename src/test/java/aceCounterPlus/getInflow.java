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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}

	public static void valCheck(String val) {
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
		case "memberInfo_change_alert":
			checkMsg = "ȸ�������� �����Ǿ����ϴ�.";
			break;
		case "memberInfo_password_null":
			checkMsg = "��й�ȣ�� �Է��ϼ���.";
			break;
		case "memberInfo_now_password_null":
			checkMsg = "���� ��й�ȣ�� �Է��ϼ���.";
			break;
		case "memberInfo_new_password_null":
			checkMsg = "�� ��й�ȣ�� �Է��ϼ���.";
			break;
		case "memberInfo_new_password_check":
			checkMsg = "�� ��й�ȣ Ȯ���� �ʿ��մϴ�.";
			break;
		case "memberInfo_new_password_fail":
			checkMsg = "�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
			break;
		case "memberInfo_change_password_confirm":
			checkMsg = "��й�ȣ�� ���� �Ͻðڽ��ϱ�?";
			break;
		case "memberInfo_change_password_check":
			checkMsg = "��й�ȣ ������ �Ϸ�Ǿ����ϴ�.";
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
		case "myCoupon_number_check":
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
		case "notifyReport_save_confirm!":
			checkMsg = "�����Ͻ� �˸� ������ �����ϴ�.\n" + "Ʈ���� �˸��� ���� �Ͻðڽ��ϱ�?";
			break;
		case "notifyReport_save_check":
			checkMsg = "�����Ͻ� ������ ����Ǿ����ϴ�.\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.";
			break;
		case "notifyReport_modify_confirm":
			checkMsg = "�����Ͻ� �˸� ������ �����ϴ�.\n" + "Ʈ���� �˸��� ���� �Ͻðڽ��ϱ�?";
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
		case "subManager_delete_confirm":
			checkMsg = "�ΰ����� ������ �����Ͻðڽ��ϱ�?";
			break;
		case "subManager_delete_check":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
		case "leaveService_service_null":
			checkMsg = "�����Ͻ� ���񽺸� �����Ͽ� �ּ���.";
			break;
		case "leaveService_name_null":
			checkMsg = "�̸��� �Է����ּ���.";
			break;
		case "leaveService_number_null":
			checkMsg = "����ó�� �޴���ȭ��ȣ ��\n" + "�ϳ��� �ݵ�� �Է��� �ּ���";
			break;
		case "leaveService_email_null":
			checkMsg = "�̸����� �Է����ּ���.";
			break;
		case "leaveService_email_check":
			checkMsg = "�̸��� �ּҰ� �ùٸ��� �ʽ��ϴ�.";
			break;
		case "leaveService_casue_null":
			checkMsg = "���������� �������ּ���.";
			break;
		case "extendCharge_service_null":
			checkMsg = "���񽺸� ������ �ּ���.";
			break;
		case "extendCharge_name_null":
			checkMsg = "�̸��� �Է��� �ּ���.";
			break;
		case "extendCharge_number_null":
			checkMsg = "����ó�� �޴���ȭ��ȣ ��\n" + "�ϳ��� �ݵ�� �Է��� �ּ���.";
			break;
		case "extendCharge_email_null":
			checkMsg = "�̸����� �Է����ּ���.";
			break;
		case "extendCharge_email_check":
			checkMsg = "�̸��� �ּҰ� �ùٸ��� �ʽ��ϴ�.";
			break;
		case "additionalCharge_service_null":
			checkMsg = "���񽺸� �������ּ���.";
			break;
		case "paymentBill_number_null":
			checkMsg = "����ڵ�Ϲ�ȣ�� �Է��� �ּ���.";
			break;
		case "paymentBill_number_only":
			checkMsg = "���ڸ� �Է��� �� �ֽ��ϴ�.";
			break;
		case "paymentBill_char_only":
			checkMsg = "���ڸ� �Է��� �� �ֽ��ϴ�.";
			break;
		case "paymentBill_numberOrchar_only":
			checkMsg = "���� �Ǵ� ���ڸ� �Է��� �� �ֽ��ϴ�.";
			break;
		case "paymentBill_company_null":
			checkMsg = "ȸ����� �Է��� �ּ���.";
			break;
		case "paymentBill_ceoname_null":
			checkMsg = "��ǥ�ڸ��� �Է��� �ּ���.";
			break;
		case "paymentBill_address_null":
			checkMsg = "ȸ���ּҸ� �Է��� �ּ���.";
			break;
		case "paymentBill_businessType_null":
			checkMsg = "���¸� �Է��� �ּ���.";
			break;
		case "paymentBill_businessTypeSection_null":
			checkMsg = "������ �Է��� �ּ���.";
			break;
		case "paymentBill_check":
			checkMsg = "����Ǿ����ϴ�.";
			break;
		}
		$(".modal-backdrop").waitUntil(visible, 10000);
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val�� checkMsg ���ؼ� ������
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val ���� 7�ڸ� confirm�̶� ���ؼ� ������ btn-info Ŭ��
	        	System.out.println(" *** " + val +  " - confirm check Success !! *** ");
				$$(".btn-info").last().click();
			    $(".modal-backdrop").waitUntil(hidden, 10000);
	        } else { //confirm �ƴϸ� .btn-smŬ��
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            //$(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert �ε��� �ʰų� ������� �ʾ����� üũ�ϱ����� �� üũ
	        System.out.println(" *** �١ڡ١ڡ١� val : " + val + " // pTag text is : " + msgCheck +  " // - msgCheck is Empty ... �١ڡ١ڡ١� *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg����, confirm&alert����, �� ���� üũ �� fail
	        System.out.println(" *** // val : " + val + " // pTag text is : " + msgCheck +  " // - check Fail ... !@#$%^&*() *** ");
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
		if (loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_setting").click();
		$(".notokr-bold", 0).waitUntil(visible, 1000);
		pageLoadCheck = $(".notokr-bold", 0).text().trim();
		if (pageLoadCheck.equals("���� ����")) {
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
		$(By.linkText("�м���ũ��Ʈ")).click();
		$("#items").waitUntil(visible, 15000);
		$(".br-dark").click();
		$(".modal-backdrop").waitUntil(visible, 15000);
		pageLoadCheck = $(".modal-title").text().trim();
		if (pageLoadCheck.equals("�м���ũ��Ʈ ���Ϲ߼�")) {
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
		//�������� ������ ��Ŀ�� ����
		switchTo().window(1);
		$("#id").setValue("apzz092888");
		$("#inputPwd").setValue(pw + A);
		$("#loginBtn").click();
		$(".link_check").waitUntil(visible, 15000);
		sleep(1000);
		refresh();
		pageLoadCheck = $("h1").text().trim();
		if (pageLoadCheck.equals("Daum\n" + "����")) {
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
		//���ϻ���
		$(By.linkText("����������")).click();
		$(".select_all").waitUntil(visible, 15000);
		$(".select_all").click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("������")).click();
		$(".select_all").waitUntil(visible, 15000);
		$(".select_all").click();
		$(".wrap_bold > .btn_permanent").click();
		sleep(2000);
		$(".check_type2").click();
		$(By.linkText("����������")).click();
		switchTo().window(0);
		pageLoadCheck = $("#scriptList").text().trim();
		if (pageLoadCheck.equals("�м���ũ��Ʈ")) {
			System.out.println(" *** scriptList aceCounter+ page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList aceCounter+ page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#scroll_target_121331").waitUntil(visible, 15000);
		$("#scroll_target_121331").click();
		$(".text-danger", 0).waitUntil(visible, 15000);
		pageLoadCheck = $(".text-danger", 0).text().trim();
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

	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}