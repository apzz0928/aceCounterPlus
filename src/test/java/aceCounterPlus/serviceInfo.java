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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class serviceInfo {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;

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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
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
		switch(val) {
		case "website_sub":
			checkMsg = "������Ʈ �̸��� �Է����ּ���.";
			break;
		case "domain_input_sub":
			checkMsg = "��ϵ� �������� �����ϴ�.";
			break;
		case "domain_input_sm":
			checkMsg = "�������� �Է��ϼ���.";
			break;
		case "domain_form_sm":
			checkMsg = "������ ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "email_input_sendbtn":
			checkMsg = "�ùٸ� �̸����� �Է��ϼ���.";
			break;
		case "email_input_sub":
			checkMsg = "���� �̸����� �߰��� �ּ���.";
			break;
		case "email_send_sub":
			checkMsg = "��ฮ��Ʈ�� �߼۵Ǿ����ϴ�.";
			break;
		case "email_h1_check":
			checkMsg = "�ְ���� ����Ʈ�Դϴ�.";
			break;
		case "saveCheck":
			checkMsg = "�����Ͻ� ������ ����Ǿ����ϴ�.\n���������� ���Ϻ��� �ݿ��˴ϴ�.";
			break;
		case "name_input_mail":
			checkMsg = "����� �̸��� �Է��� �ּ���.";
			break;
		case "pEmail_input_mail":
			checkMsg = "����� �̸����� �Է��� �ּ���.";
			break;
		case "pEmail_form_mail":
			checkMsg = "�̸��� ������ �ùٸ��� �ʽ��ϴ�.";
			break;
		case "service_input_mail":
			checkMsg = "���񽺸� �߰��� �ּ���.";
		break;
		case "subManager_add_mail":
			checkMsg = "�̸����� �߼۵Ǿ����ϴ�.\n\n�߼۵� �̸����� ȸ������ ��ũ ��ȿ�Ⱓ�� �� 7�Ϸ�\n7�� �̳� �̰��� �� ��ȿ�Ⱓ�� ����˴ϴ�.";
		break;
		case "subManager_del":
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
		break;
		}
		$(".modal-backdrop").waitUntil(visible, 15000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + "-btn validation check Success !! *** ");
			$(".btn-sm", btnNum).click();
			if(val.equals("saveCheck")) {
				System.out.println(" *** summary report mail send Success !! *** ");
			}
		} else {
			System.out.println(" *** " + val + "-btn validation check Fail !! *** ");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			close();
		}
	}

	@Test(priority = 0)
	public void modifyInfo() {
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
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
		$(".go_setting").click();
		$(By.linkText("��������")).click();
		String btninfo = $(".btn-info", 0).text();
		if(btninfo.equals("���� �߰�")) {
			System.out.println(" *** modify Info page access Success !! *** ");
		} else {
			System.out.println(" *** modify Info page access Fail !! *** ");
			close();
		}
		$(".collapsed", 0).click();
		$(".ace-btn-edit").click();
		$(".modal-backdrop").waitUntil(visible, 3000);
		String msgCheck = $("p", 120).text();
		if(msgCheck.equals("����� ������ �����ϴ�.")) {
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
		$(".modal-backdrop").waitUntil(visible, 5000);
		if(msgCheck.equals("������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** modify info check Success !! *** ");
			$(".btn-sm", 32).click();
		} else {
			System.out.println(" *** modify info check Fail !! *** ");
			close();
		}
	}
	@Test(priority = 1)
	public void sendMailSetting() {
		$(".nav-tabs").waitUntil(visible, 3000);
		$(By.linkText("�߼۸��� ����")).click();
		String mt10 = $(".mt10").text();
		$(".mt10").waitUntil(visible, 5000);
		if(mt10.equals("* ǥ�ô� �ʼ� �Է�")) {
			System.out.println(" *** sendMailSetting page access Success !! *** ");
		} else {
			System.out.println(" *** sendMailSetting page access Fail !! *** ");
			close();
		}
		$(".btn-del-sendEmail").waitUntil(visible, 5000);
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
		$(".modal-backdrop").waitUntil(visible, 12000);
		validationCheck(9, 8, "email_send_sub");
		open("https://google.com"); //acecounter ���������� �η��� �ٷ� ������ �̵��� �ȵǼ� �ٸ��������� ���İ�����
		open("https://nhnent.dooray.com/mail/new");
		String btnRed = $(".btn_red").text();
		if(btnRed.equals("LOGIN")) {
			System.out.println(" *** Dooray login page access Success !! *** ");
		} else {
			System.out.println(" *** Dooray login page access Fail !! *** ");
			close();
		}
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn_red").click();
		String name = $(".name", 8).text();
		$(".name").waitUntil(visible, 5000);
		if(name.equals("�ֿ���")) {
			System.out.println(" *** Dooray newMail page access Success !! *** ");
		} else {
			System.out.println(" *** Dooray newMail page access Fail !! *** ");
			close();
		}
		$(".one-line-block").click();
		String mailCheck = $("h1", 0).text();
		if(mailCheck.equals("�ְ���� ����Ʈ�Դϴ�.")) {
			System.out.println(" *** mail Title check Success !! *** ");
		} else {
			System.out.println(" *** mail Title check Fail !! *** ");
			close();
		}
		open("https://google.com");//acecounter ���������� �η��� �ٷ� ������ �̵��� �ȵǼ� �ٸ��������� ���İ�����
		open("https://new.acecounter.com/manage/mailing/summaryReport");
		$("#btn-reserveEmail").click();
		validationCheck(6, 5,"email_input_sendbtn");
		$(".btn-del-reserveEmail").click();
		$("#btn-save").click();
		validationCheck(7, 6,"email_input_sub");
		$(".gui-input", 3).setValue(number);
		$("#btn-reserveEmail").click();
		validationCheck(8, 7,"email_input_sendbtn");
		$(".gui-input", 3).setValue("apzz0928@nhnent.com");
		$("#btn-reserveEmail").click();
		for(int i=18;i<=25;i++) {
			$("label", i).click();
		}
		$("#btn-save").click();
		validationCheck(9, 8,"saveCheck");
		$(".nav-tabs").waitUntil(visible, 3000);
		$(By.linkText("�˸����� �߼�")).click();
		
	}
	@Test(priority = 2)
	public void subManager() {
		$(".nav-tabs").waitUntil(visible, 3000);
		$(By.xpath("(//a[contains(text(),'�ΰ�����')])[2]")).click();
		String btnMail = $("#btn_mail").text();
		$("#btn_mail").waitUntil(visible, 5000);
		if(btnMail.equals("���Ϲ߼�")) {
			System.out.println(" *** subManager page access Success !! *** ");
		} else {
			System.out.println(" *** subManager page access Fail !! *** ");
			close();
		}
		$("#btn_mail").click();
		validationCheck(5, 7, "name_input_mail");
		$("#submanager_nm").setValue("�ΰ�����" + number);
		$("#btn_mail").click();
		validationCheck(6, 8, "pEmail_input_mail");
		$("#submanager_email").setValue("apzz0928@nhnent");
		$("#btn_mail").click();
		validationCheck(7, 9, "pEmail_form_mail");
		$("#submanager_email").setValue("apzz0928@nhnent.com");
		$("#btn_mail").click();
		validationCheck(8, 10, "service_input_mail");
	    $(By.xpath("//button[@type='button']")).click();
	    $(By.id("treeDemo_2_check")).click();
	    $(By.id("select_auth")).click();
	    $(By.xpath("(//option[@value='10'])[2]")).click();
	    $(By.id("btn_add_svc")).click();
	    $("#btn_mail").click();
		validationCheck(9, 11, "subManager_add_mail");
		System.out.println(" *** subManager add Success !! *** ");
		open("https://google.com");
		open("https://nhnent.dooray.com/mail/new");
		//���Ϲ߼۶� �α��εǾ��־ �ּ�
		/*String btnRed = $(".btn_red").text();
		if(btnRed.equals("LOGIN")) {
			System.out.println(" *** Dooray login page access Success !! *** ");
		} else {
			System.out.println(" *** Dooray login page access Fail !! *** ");
			close();
		}
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn_red").click();*/
		String name = $(".name", 8).text();
		$(".name").waitUntil(visible, 5000);
		if(name.equals("�ֿ���")) {
			System.out.println(" *** Dooray newMail page access Success !! *** ");
		} else {
			System.out.println(" *** Dooray newMail page access Fail !! *** ");
			close();
		}
		$(".one-line-block").click();
		String mailCheck = $("h1", 0).text();
		if(mailCheck.equals("�ΰ����� ȸ������ �ȳ�")) {
			System.out.println(" *** subManager mail Title check Success !! *** ");
		} else {
			System.out.println(" *** subManager mail Title check Fail !! *** ");
			close();
		}
		$(By.xpath("//div[@id='mailContentsView-subject-anchor']/section/div[3]/div/div/div/p/a/strong")).click();
		switchTo().window(1);
		$(By.xpath("//input[@name='']")).click();
		$(By.linkText("Ȯ��")).click();
		$(By.xpath("//form[@id='joinForm']/div/span/label")).click();
		$("#userid").sendKeys(id + number);
		$("#recheck").click();
		$("#userpw").sendKeys("qordlf!@34");
		$("#repeatpw").sendKeys("qordlf!@34");
		$(".btn_join").click();
		$(By.xpath("(//a[contains(text(),'Ȯ��')])[2]")).click();
		switchTo().window(0);
		open("https://google.com");
		open("https://new.acecounter.com/manage/serviceInfo/submanager");
		$("#btn_mail").waitUntil(visible, 5000);
		if(btnMail.equals("���Ϲ߼�")) {
			System.out.println(" *** subManager page access2 Success !! *** ");
		} else {
			System.out.println(" *** subManager page access2 Fail !! *** ");
			close();
		}
		$(".btn-sm", 5).click();
		String alert = $("p", 5).text();
		$("#btn_mail").waitUntil(visible, 5000);
		if(alert.equals("�ΰ����� ������ �����Ͻðڽ��ϱ�?")) {
			System.out.println(" *** subManager delete message Success !! *** ");
			$("#btn-modal-alert-yes").click();
		} else {
			System.out.println(" *** subManager delete message Fail !! *** ");
			close();
		}
		validationCheck(6, 13,"subManager_del");
		$("#btn_mail").waitUntil(visible, 5000);
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}