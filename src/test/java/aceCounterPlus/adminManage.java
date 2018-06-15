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

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;

public class adminManage {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw1, pw2, name1, name2;
	private static String number = "0001";
	private static int number1 = 01;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://new.acecounter.com";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "apzz0928888";
		pw1 = "qordlf!@34";
		pw2 = "qordlf12#$";
		name1 = "�ֿ���";
		name2 = "�ֿ���1";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = true;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
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
		Object obj = executeJavaScript(javaScriptSource);
	}

	//@Test(priority = 0)
	public void login() {
		open(baseUrl);
		$("#uid").setValue(id);
		$("#upw").setValue(pw1);
		$(".btn_login").click();
		$(".go_setting").click();
	}

	// //@Test(priority = 1)
	public void my_info_modify() {
		$(By.linkText("ȸ������")).click();
		$("#pwd").setValue(pw1);
		$("#btn-ok").click();
		$("#prePwd").setValue(pw1);
		$("#changePwd").setValue(pw2);
		$("#changePwdConfirm").setValue(pw2);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		$("#okButton").click();
		System.out.println(" --- Password Change : ��й�ȣ ���� : Pass --- ");
		$("#prePwd").setValue(pw2);
		$("#changePwd").setValue(pw1);
		$("#changePwdConfirm").setValue(pw1);
		$("#modifyProc").click();
		$("#btn-modal-alert-yes").click();
		$("#okButton").click();
		System.out.println(" --- Password rollBack : ��й�ȣ ���� : Pass --- ");
		$("#s_name").setValue(name2);
		$("#s_company").setValue("����");
		js("window.scrollBy(999, 999);");
		$("#s_hp2").setValue("0928");
		$("#s_hp3").setValue("9743");
		$("#s_email").setValue("apzz0928@nhnent.com");
		$(".btn-lg", 1).click();
		$(".close", 2).click();
		System.out.println(" --- memberInfo Change : ȸ������ ���� : Pass --- ");
		$("#s_name").setValue(name1);
		$("#s_company").setValue("������");
		js("window.scrollBy(999, 999);");
		$("#s_hp2").setValue("9743");
		$("#s_hp3").setValue("0928");
		$("#s_email").setValue("apzz0928@gmail.com");
		$(".btn-lg", 1).click();
		$(".close", 1).click();
		System.out.println(" --- memberInfo rollBack : ȸ������ ���� : Pass --- ");
	}

	// //@Test(priority = 2)
	public void serviceInfo_addService() {
		$("#addService").click();
		$(By.name("svcNm")).setValue("�����߰������׽�Ʈ" + number);
		$(By.name("input-domain")).setValue("test" + number + ".org");
		$(".ace-btn-add-domain").click();
		$(By.name("largeCategoryCd")).selectOptionByValue("22");
		$(By.name("middleCategoryCd")).selectOptionByValue("188");
		$("#btn_submit").click();
		$(By.linkText("Ȯ��")).click();
		System.out.println(" --- addService : ���� �߰� : �����߰� : Pass --- ");
	}

	// //@Test(priority = 3)
	public void serviceInfo_editService() {
		$("#editService").click();
		$(".opened", 0).click();
		$(".ace-btn-remove", 0).click();
		$(By.name("input-domain")).setValue("test" + number + number1 + ".org");
		$(".ace-btn-add-domain").click();
		$(".ace-btn-edit", 0).click();
		$(".close", 3).waitUntil(appear, 1000).click();
		System.out.println(" --- editService : ���� ���� : ���� ���� : Pass --- ");
	}

	// //@Test(priority = 4)
	public void mailing_summartReport() throws InterruptedException {
		$("#summaryReport").click();
		$("#btn-sendMail").click();
		$(".close", 1).click();
		System.out.println(" --- summaryReport : �߼۸��� ���� : ���� �߼� : Pass --- ");
		js("window.scrollBy(999, 999);"); // ��ฮ��Ʈ ���� ���Ϸ� ��ũ�� �̵�
		js("$('.reserve_day').click();");
		/*
		 * for(int i=1;i<=6;i++) { js("$('.reserve_day').eq(" + i + ").click();");
		 * //$(".reserve_day", i).click(); System.out.println("i is " + i); }
		 */
		$("#btn-save").waitUntil(visible, 3000).click();
		$(".br6").waitUntil(visible, 3000);
		$(".close", 1).waitUntil(visible, 3000).click();
		System.out.println(" --- summaryReport : �߼۸��� ���� : ��ฮ��Ʈ ���� �߼� ���� : Pass --- ");
		$("#btn-save").waitUntil(visible, 3000);
		js("$('.reserve_day').click();");
		/*
		 * for(int i=1;i<=6;i++) { $(".reserve_day", i).click();
		 * System.out.println("i is " + i); }
		 */
		$("#btn-save").waitUntil(visible, 3000).click();
		$(".br6").waitUntil(visible, 3000);
		$(".close", 1).waitUntil(visible, 3000).click();
		System.out.println(" --- summaryReport : �߼۸��� ���� : ��ฮ��Ʈ ���� �߼� ���� : Pass --- ");
	}

	// //@Test(priority = 5)
	public void serviceInfo_submanager() throws InterruptedException {
		$("#submanager").click();
		$("#submanager_nm").setValue("�ֿ���");
		$("#submanager_email").setValue("apzz0928@nhnent.com");
		$(".multiselect").click();
		$("#treeDemo_1_check").click();
		$("#select_auth").selectOptionByValue("20");
		$("#btn_add_svc").click();
		$("#btn_mail").waitUntil(visible, 3000).click();
		$(".close", 2).waitUntil(visible, 3000).click();
		refresh();
		System.out.println(" --- serviceInfo : �ΰ����� : �ʴ���� �߼� : Pass --- ");
		$(".indicator").click();
		$(".cross", 0).waitUntil(visible, 3000).click();
		$(".w100").waitUntil(visible, 3000).click();
		$("#btn-modal-alert-yes").click();
		$(".close", 3).waitUntil(visible, 3000).click();
		refresh();
		System.out.println(" --- serviceInfo : �ΰ����� : ���� ���� : Pass --- ");
		$(".br-dark", 2).waitUntil(visible, 3000).click();
		$("#btn-modal-alert-yes").waitUntil(visible, 3000).click();
		$(".close", 3).waitUntil(visible, 3000).click();
		System.out.println(" --- serviceInfo : �ΰ����� : ���� ���� : Pass --- ");
	}

	//@Test(priority = 6)
	public void inhouseViralAddtestMethod() throws Exception {
		open("https://new.acecounter.com/setting/inhouse/viral/add");
		for (int i = 13; i <= 100; i++) {
			$(By.name("campaign_name")).setValue("campaign_name" + i);
			Thread.sleep(2000);
			$(".material_name").setValue("material_name" + i);
			Thread.sleep(2000);
			$("#btn-save").click();
			Thread.sleep(2000);
			$(".close", 1).click();
			Thread.sleep(2000);
			$(By.linkText("�߰�")).click();
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}