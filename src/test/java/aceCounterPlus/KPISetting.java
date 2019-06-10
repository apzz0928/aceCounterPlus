package aceCounterPlus; // ��輳�� > KPI����, ����Ʈ�ٿ�ε� / ���Ǹ޴�

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

public class KPISetting {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
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
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			//cap = DesiredCapabilities.firefox();
			//RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*EdgeOptions options = new EdgeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
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
        switch(val){
            case "KPIAdd_reporeName_null": checkMsg = "����Ʈ���� �����ϼ���.";
            break;
            case "KPIAdd_indicator_null": checkMsg = "��ǥ�� �����ϼ���.";
            break;
            case "KPIAdd_indicator_duplication": checkMsg = "�̹� �߰��� �׸��Դϴ�.";
            break;
            case "KPIAdd_indicator_badStandard": checkMsg = "���� ������ �Է��ϼ���.";
            break;
            case "KPIAdd_indicator_goodStandard": checkMsg = "��ȭ�� ���������� ���ڸ� �Է� �����մϴ�.";
            break;
            case "KPIAdd_indicator_full": checkMsg = "�ִ� 15�� �׸��� ������ �� �ֽ��ϴ�.";
            break;
            case "KPIAdd_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "KPISetting_modify_confirm": checkMsg = "�����Ͻðڽ��ϱ�?";
            break;
            case "KPISetting_modify_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "KPISetting_del_null": checkMsg = "������ KPI ������ �����ϼ���.";
            break;
            case "KPISetting_del_confirm": checkMsg = "������ KPI ������ �����Ͻðڽ��ϱ�?\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "KPISetting_del_alert": checkMsg = "KPI ���� ������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "reportDownload_reserveAdd_reportName_null": checkMsg = "����Ʈ���� �Է��ϼ���.";
            break;
            case "reportDownload_reserveAdd_reportName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "reportDownload_reserveAdd_email_validation": checkMsg = "�ùٸ� ���� �̸����� �Է��ϼ���.";
            break;
            case "reportDownload_reserveAdd_reportCheck_null": checkMsg = "����Ʈ�� �����ϼ���.";
            break;
            case "reportDownload_reserveAdd_register": checkMsg = "����Ʈ�� �����Ͽ����ϴ�.";
            break;
            case "reportDownload_reserveDel_register": checkMsg = "����Ʈ�� �����Ͽ����ϴ�.";
            break;
            case "reportDownload_reservedel_confirm": checkMsg = "�����Ͻ� ���� ����Ʈ ������ �����Ͻðڽ��ϱ�?";
            break;
            case "reportDownload_reservedel_alert": checkMsg = "���� ������ �����Ǿ����ϴ�.";
            break;
            case "reportDownload_oneshotAdd_reportName_null": checkMsg = "����Ʈ���� �Է��ϼ���.";
            break;
            case "reportDownload_oneshotAdd_reportName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "reportDownload_oneshotAdd_email_validation": checkMsg = "�ùٸ� ���� �̸����� �Է��ϼ���.";
            break;
            case "reportDownload_oneshotAdd_reportCheck_null": checkMsg = "����Ʈ�� �����ϼ���.";
            break;
            case "reportDownload_oneshotAdd_register": checkMsg = "����Ʈ�� �����Ͽ����ϴ�.";
            break;
            case "reportDownload_oneshotDel_confirm": checkMsg = "�����Ͻ� ��ȸ�� ����Ʈ ������ �����Ͻðڽ��ϱ�?";
            break;            
            case "reportDownload_oneshotDel_alert": checkMsg = "���� ������ �����Ǿ����ϴ�.";
            break;
			case "myMenu_add_menu_null": checkMsg = "��踦 �����ϼ���.";
			break;
			case "myMenu_add_menu_duplication": checkMsg = "�̹� �߰��� ����Դϴ�.";
			break;
			case "myMenu_add_menu_max": checkMsg = "�ִ� 20������ ����� �� �ֽ��ϴ�.";
			break;
			case "myMenu_save": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
			break;
        }
		$(".modal-backdrop").waitUntil(visible, 10000);
		$$("p").last().click();
		String msgCheck = $$("p").last().text().trim();
        Thread.onSpinWait();
		if(msgCheck.equals(checkMsg)) { //val�� checkMsg ���ؼ� ������
			if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val ���� 7�ڸ� confirm�̶� ���ؼ� ������ btn-info Ŭ��
				System.out.println(" *** val : " + val +  " - confirm check Success !! *** ");
				$$(".btn-info").last().click();
			    sleep(300);
			} else { //confirm �ƴϸ� .btn-smŬ��
				System.out.println(" *** " + val +  " - check Success !! *** ");
				$$(".btn-sm").last().click();
				sleep(300);
			}
		} else if (msgCheck.isEmpty()) { //alert �ε��� �ʰų� ������� �ʾ����� üũ�ϱ����� �� üũ
			System.out.println(" *** �١ڡ١ڡ١� " + val +  " - msgCheck is Empty ... �١ڡ١ڡ١� *** ");
			System.out.println(checkMsg);
			close();
		} else { // msgCheck=checkMsg����, confirm&alert����, �� ���� üũ �� fail
			System.out.println(" *** " + val +  " - check Fail ... !@#$%^&*() *** ");
			System.out.println(checkMsg);
			close();
		}
	}
	
  	//�Էµ� URL ���� ���� Ȯ��
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400){
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() *** ");
            	close();
            } else {
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " - check Success !! *** ");
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
  	@Test(priority = 0)
	public void Login() {
		System.out.println(" ! ----- Login Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("�湮��")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- Login End ----- ! ");
	}
	@Test(priority = 1)
	public void KPISetting_add() {
		System.out.println(" ! ----- KPISetting_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".sidebar-title", 6).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("KPI����")) {
			System.out.println(" *** KPISetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		/*$("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text().trim();
	    if(pageLoadCheck.equals("�׽�Ʈ")) {
			System.out.println(" *** KPISetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}*/
		$("#addControl").click();
		$("h4", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("Step1. ��ǥ����")) {
			System.out.println(" *** KPISetting_add add Page load Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_add add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPIAdd_reporeName_null");
		$("h3", 1).scrollTo();
		$(".input-sm").setValue(date);
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPIAdd_indicator_null");
		$("h3", 1).scrollTo();
		$(".btn-sm", 11).click();
		$(".btn-sm", 11).click();
		valCheck("KPIAdd_indicator_duplication");
		$(".bad").setValue("");
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPIAdd_indicator_badStandard");
		$("h3", 1).scrollTo();
		$(".bad").setValue("5");
		$(".good").setValue("��");
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPIAdd_indicator_goodStandard");
		$("h3", 1).scrollTo();
		$(".cross").click();
		for(int i=11;i<=17;i++) { //KPI ��ǥ �߰�
			$(".btn-sm", i).click();
			System.out.println("indicatorStatdard add number is " + i);
		}
		$(By.linkText("�湮��")).click();
		for(int i=18;i<=26;i++) {
			$(".btn-sm", i).click();
			System.out.println("indicatorStatdard add number is " + i);
		}
		valCheck("KPIAdd_indicator_full");
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPIAdd_register");		
		$("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** KPISetting_add register Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- KPISetting_add End ----- ! ");
	}
	@Test(priority = 2)
	public void KPISetting_modify() {
		System.out.println(" ! ----- KPISetting_modify Start ----- ! ");
		$(".btn-xs", 0).click();
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("Step1. ��ǥ����")) {
			System.out.println(" *** KPISetting_modify modify Page load Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_modify modify Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm").setValue(date + " ����");
		for(int i=13;i>=0;i--) { // KPI���� ��ǥ ����
			$(".cross", i).click();
			System.out.println("indicatorStatdard modify number is " + i);
		}
		$("#registBtn").scrollTo();
		$("#registBtn").click();
		valCheck("KPISetting_modify_confirm");
		valCheck("KPISetting_modify_alert");
		$(By.name("useYn")).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date + " ����")) {
			System.out.println(" *** KPISetting_modify register Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_modify register Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("useYn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $("#delControl").waitUntil(hidden, 10000);
	    pageLoadCheck = $("#addControl").text();
	    if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** KPISetting_modify del selectBox page load Success !! *** ");
	    } else {
			System.out.println(" *** KPISetting_modify del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
	    }
	    $(By.name("useYn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $("#delControl").waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 2).text();
	    if(pageLoadCheck.equals(date + " ����")) {
			System.out.println(" *** KPISetting_modify set selectBox page load Success !! *** ");	    	
	    } else {
			System.out.println(" *** KPISetting_modify set selectBox page load Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
		System.out.println(" ! ----- KPISetting_modify End ----- ! ");
	}
	@Test(priority = 3)
	public void KPISetting_del() {
		System.out.println(" ! ----- KPISetting_del Start ----- ! ");
		$("#delControl").click();
		$("#delProcess").waitUntil(visible, 10000);
		$("#delProcess").click();
		valCheck("KPISetting_del_null");
		$(".mainDelCheck", 1).click();
		$("#delProcess").click();
		valCheck("KPISetting_del_confirm");
		valCheck("KPISetting_del_alert");
		$("#delProcess").waitUntil(hidden, 10000);
		pageLoadCheck = $("td", 2).text().trim();
	    if(pageLoadCheck.equals("�׽�Ʈ")) {
			System.out.println(" *** KPISetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** KPISetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- KPISetting_del End ----- ! ");
	} 
	@Test(priority = 11)
	public void reportDownload_reserveAdd() {
		System.out.println(" ! ----- reportDownload_reserveAdd Start ----- ! ");
		$(".sidebar-title", 7).click();
		$("td").waitUntil(visible, 10000);
		pageLoadCheck = $("td").text();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("�����")) {
			System.out.println(" *** reportDownload_reserveAdd list Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveAdd list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		$("#btn-select-report-all").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-select-report-all").text();
		if(pageLoadCheck.equals("��ü����")) {
			System.out.println(" *** reportDownload_reserveAdd add Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveAdd add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveAdd_reportName_null");
		$(".form-control", 0).scrollTo();
		$(".form-control", 0).setValue(date + "@");
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveAdd_reportName_validation");
		$(".form-control", 0).scrollTo();
		$(".form-control", 0).setValue(date);
		$(".form-control", 1).setValue(date);
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveAdd_email_validation");
		$(".form-control", 0).scrollTo();
		$(".form-control", 1).setValue(date + "@test.com");
		$("#btn-select-report-all").click();
		$("#btn-select-report-cancle").click();
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveAdd_reportCheck_null");
		$(".form-control", 0).scrollTo();
		$("label", 1).click();
		for(int i=2;i<=8;i++) {
			$("#report-menu-"+i).click();
		}
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveAdd_register");
		$(".text-underline").waitUntil(visible, 10000);
		pageLoadCheck = $(".text-underline").text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** reportDownload_reserveAdd register Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveAdd register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- reportDownload_reserveAdd End ----- ! ");
	}
	@Test(priority = 12)
	public void reportDownload_reserveDel() {
		System.out.println(" ! ----- reportDownload_reserveDel Start ----- ! ");
		$(By.linkText(date)).click();
		$(".form-control", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("th", 0).text();
		if(pageLoadCheck.equals("����Ʈ��")) {
			System.out.println(" *** reportDownload_reserveDel modify Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveDel modify Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".form-control", 0).setValue(date + "����");
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_reserveDel_register");
		$(".text-underline").waitUntil(visible, 10000);
		pageLoadCheck = $(".text-underline").text();
		if(pageLoadCheck.equals(date + "����")) {
			System.out.println(" *** reportDownload_reserveDel modify Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveDel modify Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-dark", 0).click();
		valCheck("reportDownload_reservedel_confirm");
		valCheck("reportDownload_reservedel_alert");
		sleep(500);
		$(".btn-info").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("�����")) {
			System.out.println(" *** reportDownload_reserveDel list Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_reserveDel list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- reportDownload_reserveDel End ----- ! ");
	}
	@Test(priority = 13)
	public void reportDownload_oneshotAdd() {
		System.out.println(" ! ----- reportDownload_oneshotAdd Start ----- ! ");
		sleep(1000);
		$(".btn-info").click();
		$("#btn-select-report-all").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-select-report-all").text();
		if(pageLoadCheck.equals("��ü����")) {
			System.out.println(" *** reportDownload_oneshotAdd add Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_oneshotAdd add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_oneshotAdd_reportName_null");
		$(".form-control", 0).scrollTo();
		$(".form-control", 0).setValue(date + "@");
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_oneshotAdd_reportName_validation");
		$(".form-control", 0).scrollTo();
		$(".form-control", 0).setValue(date);
		$(".form-control", 1).setValue(date);
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_oneshotAdd_email_validation");
		$(".form-control", 0).scrollTo();
		$(".form-control", 1).setValue(date + "@test.com");
		$("#btn-select-report-all").click();
		$("#btn-select-report-cancle").click();
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_oneshotAdd_reportCheck_null");
		$(".form-control", 0).scrollTo();
		for(int i=2;i<=8;i++) {
			$("#report-menu-"+i).click();
		}
		$("#btn-add-report").scrollTo();
		$("#btn-add-report").click();
		valCheck("reportDownload_oneshotAdd_register");
		$(".col-sm-2").waitUntil(visible, 10000);
		pageLoadCheck = $(".col-sm-2").text();
		if(pageLoadCheck.equals("* ����Ʈ ���� ��Ȳ")) {
			System.out.println(" *** reportDownload_oneshotAdd register Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_oneshotAdd register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- reportDownload_oneshotAdd End ----- ! ");
	}
	@Test(priority = 14)
	public void reportDownload_oneshotDel() {
		System.out.println(" ! ----- reportDownload_oneshotDel Start ----- ! ");
		$(".form-control", 1).setValue(date);
		$("#btn-search").click();
		$(".btn-delete").waitUntil(visible, 10000);
		pageLoadCheck = $(".text-left").text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** reportDownload_oneshotDel search Success !! *** ");	
		} else {
			System.out.println(" *** reportDownload_oneshotDel search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-delete").click();
		valCheck("reportDownload_oneshotDel_confirm");
		valCheck("reportDownload_oneshotDel_alert");
		$("td", 2).waitUntil(visible, 10000);
		/*$(".muted").waitUntil(visible, 10000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����Ʈ ���� �̷��� �����ϴ�.\n" + "�߰� �ǿ��� ����Ʈ�� �����ϼ���.")) {
			System.out.println(" *** reportDownload_oneshotDel list Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_oneshotDel modify Fail ... !@#$%^&*() *** ");
			close();
		}*/
		//����Ʈ �ٿ�ε��� �ٿ�ε��ǿ� ����Ʈ ������ ���ؼ� �ӽ÷� �߰���
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("��踮��Ʈ")) {
			System.out.println(" *** reportDownload_oneshotDel list Page load Success !! *** ");
		} else {
			System.out.println(" *** reportDownload_oneshotDel modify Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- reportDownload_oneshotDel End ----- ! ");
	}
	@Test(priority = 21)
	public void myMenu_add() {
		System.out.println(" ! ----- myMenu Start ----- ! ");
		sleep(1000);
		$("#redirectMyMenuBtn").click();
		$("#myMenu").waitUntil(visible, 15000);
		$("#myMenu").click();
		$(".top > .menu-service > .sidebar-title").click();
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("Step1. ��輱��")) {
			System.out.println(" *** myMenu_add list Page access Success !! *** ");
		} else {
			System.out.println(" *** myMenu_add list Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".cross", 0).click();
		$("#noMenu").waitUntil(visible, 10000);
		pageLoadCheck = $("#noMenu").text();
		if(pageLoadCheck.equals("��踦 �����ϼ���.")) {
			System.out.println(" *** myMenu_add delete check Success !! *** ");
		} else {
			System.out.println(" *** myMenu_add delete check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#saveBtn").click();
		valCheck("myMenu_add_menu_null");
		$(".btn-sm", 2).click();
		$(".btn-sm", 2).click();
		valCheck("myMenu_add_menu_duplication");
		for(int i=3,x=1;i<=22;i++) { //���� �޴� �߰� 
			$(".btn-sm", i).click();
			System.out.println("myMenu add btn number is " + i);
			if(i==7) {
				$(".tabs-left > li", x).click();
				System.out.println("myMenu Tab number is " + x);
				x++;
			} else if (i==10) {
				$(".tabs-left > li", x).click();
				System.out.println("myMenu Tab number is " + x);
				x++;
			} else if (i==19) {
				$(".tabs-left > li", x).click();
				System.out.println("myMenu Tab number is " + x);
				x++;
			}
		}
		valCheck("myMenu_add_menu_max");
		$("#saveBtn").click();
		valCheck("myMenu_save");
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("Step1. ��輱��")) {
			System.out.println(" *** myMenu_add register Success !! *** ");
		} else {
			System.out.println(" *** myMenu_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".cross", 1).scrollTo();
		$(".cross", 1).waitUntil(visible, 10000);
		for(int i=1;i>=19;i++) { //���� �޴� ����
			$(".cross", 1).click();
			System.out.println("myMenu del btn number is " + i);
		}
		$("#saveBtn").click();
		valCheck("myMenu_save");
		System.out.println(" ! ----- myMenu End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}