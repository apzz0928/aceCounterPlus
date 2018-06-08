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
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("YYMMDDhhmmss");
    SimpleDateFormat number_format1 = new SimpleDateFormat("YYYY-MM-DD");
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
		String msgCheck = $("p", pTagNum).text();
        switch(val){
            case "URL_null": checkMsg = "����������URL�� �Է��ϼ���.";
            break;
            case "URL_check": checkMsg = "�Է��Ͻ� ������URL��\n" + "������������ �ش���� �ʽ��ϴ�.\n" + "�ٽ� Ȯ�� �� �Է����ּ���.";
            break;
            case "URL_del_check": checkMsg = "������ ������������ �����ϼ���.";
            break;
            case "exceptChar_alert": checkMsg = "���ܹ��ڸ� �Է��ϼ���.";
            break;
            case "exceptAdd_confirm": checkMsg = "������ ���� ���ڸ� ����Ͻðڽ��ϱ�?\n" + "Ȯ���� Ŭ���ϸ�\n" + "URL�� ������ ���� ���� ����\n" + "�����Ͽ� �м��մϴ�.";
            break;
            case "exceptAdd_alert": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "delExceptCharCheck_alert": checkMsg = "������ URL ���ܹ��ڸ� �����ϼ���.";
            break;
            case "delExceptChar_confirm": checkMsg = "������ URL ���ܹ��ڸ� �����ϰڽ��ϱ�?\n" + "URL ���� ���ڿ� ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "delExceptChar_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "analysisURL_null": checkMsg = "�м��� URL�� �Է��ϼ���.";
            break;
            case "searchVar_null": checkMsg = "���ΰ˻� ��� ���������� �˻��� ������ �Է��ϼ���.";
            break;
            case "innerSearch_add_alert": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "innerSearch_deplication_add_alert": checkMsg = "��ϵ� ���ΰ˻� ������ URL�� �ֽ��ϴ�.\n" + "�ٽ� �Է����ּ���.";
            break;
            case "innerSearch_del_null": checkMsg = "������ ���ΰ˻� URL�� �Է��ϼ���.";
            break;
            case "innerSearch_del_confirm": checkMsg = "������ ���ΰ˻� URL�� �����Ͻðڽ��ϱ�?\n" + "���ΰ˻��� ������ ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "innerSearch_del_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "menuDel_confirm": checkMsg = "�޴��� �����Ͻðڽ��ϱ�?\n" + "�޴��� �����ϸ�, �޴��� ���� �м��� �����˴ϴ�.";
            break;
            case "pageManage_selectNull": checkMsg = "������ �������� �����ϼ���.";
            break;
            case "pattern_menu_null": checkMsg = "�޴��� ���� �����ϼ���.";
            break;
            case "pattern_null": checkMsg = "������ �Է��ϼ���.";
            break;
            case "pattern_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "pattern_del_null": checkMsg = "������ ������ �����ϼ���.";
            break;
            case "pattern_del_confirm": checkMsg = "������ ������ �����Ͻðڽ��ϱ�?\n" + "�ش� ���Ͽ� ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "pattern_del_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "campaignName_null": checkMsg = "ķ���θ��� �Է��ϼ���.";
            break;
            case "bannerName_null": checkMsg = "���縦 �Է��ϼ���.";
            break;
            case "linkURL_null": checkMsg = "���� URL�� �Է��ϼ���.";
            break;
            case "innerBanner_add_alert": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "innerBanner_duplicationAdd": checkMsg = "�̹� ����� ķ���θ��Դϴ�.\n" + "�ٸ� ķ���θ��� ������ּ���.";
            break;
            case "innerBanner_del_null": checkMsg = "������ ���ι�� ������ �����ϼ���.";
            break;
            case "innerBanner_del_confirm": checkMsg = "������ ���ι�� ������ �����Ͻðڽ��ϱ�?\n" + "���ι�� ������ ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "innerBanner_del_alert": checkMsg = "���ι�� ���� ������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "downPattern_null": checkMsg = "�ٿ�ε� ������ �Է��ϼ���.";
            break;
            case "downPattern_add_alert": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "downPattern_duplicationAdd_alert": checkMsg = "�ٿ�ε� ���ϰ��� �̹� ��ϵǾ� �ֽ��ϴ�.";
            break;
            case "downPattern_del_null": checkMsg = "������ �ٿ�ε� ������ �����ϼ���.";
            break;
            case "downPattern_del_confirm": checkMsg = "�ٿ�ε� ������ �����Ͻðڽ��ϱ�?\n" + "�ٿ�ε� ������ �����ϸ�,\n" + "���ϴٿ�ε� �м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "downPattern_del_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "outLinkBanner_promotionName_null": checkMsg = "���θ�Ǹ��� �Է��ϼ���.";
            break;
            case "outLinkBanner_name_null": checkMsg = "�ƿ���ũ ��ʸ��� �Է��ϼ���.";
            break;
            case "filePath_null": checkMsg = "���ϰ�θ� �Է��ϼ���.";
            break;
            case "outLinkBanner_linkURL_null": checkMsg = "����URL�� �Է��ϼ���.";
            break;
            case "outLinkBanner_linkURL_badURL": checkMsg = "�ùٸ� URL�� �Է��ϼ���.";
            break;
            case "outLinkBanner_add_alert": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "outLinkBanner_promotionName_duplication": checkMsg = "�̹� ����� ���θ�Ǹ��Դϴ�.\n" + "�ٸ� ���θ�Ǹ��� ������ּ���.";
            break;
            case "outLinkBanner_modify_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "outLinkBanner_del_null": checkMsg = "������ �ƿ���ũ ��ʸ� �����ϼ���.";
            break;
            case "outLinkBanner_del_confirm": checkMsg = "���θ���� �����Ͻðڽ��ϱ�?\n" + "��ϵ� ��ʵ� �Բ� ���� �˴ϴ�.\n" + "\n" + "�ƿ���ũ ��ʿ� ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.\n" + "\n" + "������Ʈ�� ����� �������� �����ڵ带\n" + "������ �ֽñ� �ٶ��ϴ�.";
            break;
            case "outLinkBanner_del_alert": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
            break;
            
        }
		$(".modal-backdrop").waitUntil(visible, 10000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " - check Fail ... !@#$%^&*() *** ");
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
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " *****");
            	close();
            } else {
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " *****");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }

	@Test(priority = 0)
	public void URLSetting_URLInclusion() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLInclusion Start ----- ! ");
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�湮��")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#redirectConfBtn").waitUntil(visible, 10000);
		$(".accordion-toggle", 3).click();
		$(By.linkText("URL ����")).click();
		Thread.sleep(1000);
		$(".btn-info", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-info", 0).text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** URLSetting_URLInclusion Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(9, 7, "URL_null");
		$("#page-url").setValue("/test");
		$("#btn-add").click();
		valCheck(10, 8, "URL_check");
		$("#btn-add-cancel").click();
		$("#btn-add").waitUntil(hidden, 3000);
		$("#btn-list-delete").click();
		$("#btn-list-select-delete").click();
		valCheck(11, 9, "URL_del_check");
		System.out.println(" ! ----- URLSetting_URLInclusion End ----- ! ");
	}
	@Test(priority = 1)
	public void URLSetting_URLExcept_Add() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_Add Start ----- ! ");
		$(By.linkText("URL����")).click();
		Thread.sleep(1000);
		$(".col-xs-9").waitUntil(visible, 10000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("th", 0).text();
		if(pageLoadCheck.equals("���� ���� ����")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(5, 3, "exceptChar_alert");
		$(By.xpath("//div[@id='addset']/div/table/tbody/tr/td/div/div/label[4]")).click();
		Thread.sleep(1000);
		$("#btn-add").click();
		valCheck(6, 4, "exceptChar_alert");
		$(".input-sm", 0).setValue(date);
		$("#btn-add").click();
		valCheck(7, 5, "exceptAdd_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
		valCheck(8, 7, "exceptAdd_alert");
		Thread.sleep(2500);
		$(".col-xs-9", 0).waitUntil(visible, 5000);
		pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_Add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_Add Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_Add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_URLExcept_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 10000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    Thread.sleep(1500);
	    pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(1500);
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_search End ----- ! ");
	}
	@Test(priority = 3)
	public void URLSetting_URLExcept_del() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(5, 3, "delExceptCharCheck_alert");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(6, 4, "delExceptChar_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    valCheck(7, 6, "delExceptChar_alert");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
		$(".muted").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_del End ----- ! ");
	}
	@Test(priority = 4)
	public void URLSetting_innerSearch_add() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_add Start ----- ! ");
		$(By.linkText("���ΰ˻�")).click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* �м��ϰ��� �ϴ� ���ΰ˻� ��� ������ URL�� �Է��մϴ�.")) {
			System.out.println(" *** URLSetting_innerSearch_add UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(4, 3, "analysisURL_null");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$("#page-url").setValue("/" + date);
		$("#btn-add").click();
		valCheck(5, 4, "searchVar_null");	
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		Thread.sleep(1000);
		valCheck(6, 5, "innerSearch_add_alert");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$(".text-nowrap", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_add next page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add next page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_add End ----- ! ");
	}
	@Test(priority = 5)
	public void URLSetting_innerSearch_duplicationAdd() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* �м��ϰ��� �ϴ� ���ΰ˻� ��� ������ URL�� �Է��մϴ�.")) {
			System.out.println(" *** URLSetting_innerSearch_add UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#page-url").setValue("/" + date);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		Thread.sleep(1000);
		valCheck(4, 3, "innerSearch_deplication_add_alert");
		$("#btn-add-cancel").click();
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd End ----- ! ");
	}
	@Test(priority = 6)
	public void URLSetting_innerSearch_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_search Start ----- ! ");
		$(By.linkText("���ΰ˻�")).click();
		Thread.sleep(3000);
		String pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch search_selectbox load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    Thread.sleep(1500);
	    $(".col-xs-8").waitUntil(visible, 10000);
		pageLoadCheck = $(".col-xs-8").text();
		if(pageLoadCheck.equals("������ URL")) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 10000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    Thread.sleep(1500);
	    $(".text-nowrap", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(1500);
		$("td", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch setList search Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch setList page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_search End ----- ! ");
	}
	@Test(priority = 7)
	public void URLSetting_innerSearch_del() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "innerSearch_del_null");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "innerSearch_del_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    valCheck(6, 6, "innerSearch_del_alert");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
		$(".muted").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_innerSearch_del Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_del End ----- ! ");
	}
	@Test(priority = 8)
	public void pageGroupSetting_menuAddDel() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_menuAddDel Start ----- ! ");
	    $(By.linkText("�������׷� ����")).click();
	    $("#btn-tree-add").waitUntil(visible, 10000);
		String pageLoadCheck = $("#btn-tree-add").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** pageGroupSetting_menuAdd Page load Success !! *** ");
		} else {
			System.out.println(" ***pageGroupSetting_menuAdd Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $("#btn-tree-add").click();
	    $(By.xpath("(//input[@value=''])[2]")).setValue(date);
	    $(By.id("ui-id-1")).click();
	    $(".fancytree-lastsib").click();
	    $("#btn-tree-delete").click();
	    valCheck(5, 5, "menuDel_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- pageGroupSetting_menuAddDel End ----- ! ");
	}
	@Test(priority = 9)
	public void pageGroupSetting_pageManage() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_pageManage Start ----- ! ");
		brokenLinkCheck("pageDownload", "https://new.acecounter.com/setting/contents/pageGroup/downloadPage?key=&match=false&query=&use_yn=y");
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    String pageLoadCheck = $("td", 0).text();
	    if(pageLoadCheck.equals("��ϵ� �������� �����ϴ�.\n" + 
	    		"�޴��� ������ �� �������� ����ϼ���.")) {
	    	System.out.println(" *** pageGroupSetting_pageManage search Success !! *** ");
	    } else {
	    	System.out.println(" *** pageGroupSetting_pageManage search Fail ... !@#$%^&*() *** ");
	    	close();
	    }
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 5000);
	    $("#btn-list-select-delete").click();
	    valCheck(6, 7, "pageManage_selectNull");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    System.out.println(" ! ----- pageGroupSetting_pageManage End ----- ! ");
	}
	@Test(priority = 10)
	public void pageGroupSetting_patternRegister() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_patternRegister Start ----- ! ");
		$(By.linkText("���ϵ��")).click();
		valCheck(7, 8, "pattern_menu_null");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$(".fancytree-title", 0).click();
		$(By.linkText("���ϵ��")).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("���ϵ��")) {
			System.out.println(" *** pageGroupSetting_patternRegister page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternRegister page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-pattern-add").click();
		valCheck(9, 9, "pattern_null");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		$(".input-sm").setValue(date);
		$("#btn-pattern-add").click();
		valCheck(10, 10, "pattern_register");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- pageGroupSetting_patternRegister End ----- ! ");
	}
	@Test(priority = 11)
	public void pageGroupSetting_patternManagement() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_patternManagement Start ----- ! ");
		$(By.linkText("���ϰ���")).click();
		String pageLoadCheck = $(".col-xs-5", 1).text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** pageGroupSetting_patternManagement page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternManagement page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("pattern_query")).sendKeys(date);
	    $("#btn-pattern-search").click();
		$("#btn-list-pattern-delete").click();
		$("#btn-list-select-pattern-delete").waitUntil(visible, 10000);
		$("#btn-list-select-pattern-delete").click();
		valCheck(11, 11, "pattern_del_null");
		$(".modal-backdrop").waitUntil(hidden, 10000);
	    $(By.xpath("(//input[@id='inlineCheckbox1'])[2]")).click();
		$("#btn-list-select-pattern-delete").click();
		valCheck(12, 12, "pattern_del_confirm");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		valCheck(13, 14, "pattern_del_alert");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- pageGroupSetting_patternManagement End ----- ! ");
	}
	@Test(priority = 12)
	public void pageGroupSetting_pageUpload() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_pageUpload Start ----- ! ");
		$(By.linkText("���������ε�")).click();
		$("h3", 3).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 3).text();
		if(pageLoadCheck.equals("���������ε�")) {
			System.out.println(" *** pageGroupSetting_pageUpload page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_pageUpload page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- pageGroupSetting_pageUpload End ----- ! ");
	}
	@Test(priority = 13)
	public void innerBanner_add() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_add Start ----- ! ");
	    $(By.linkText("���ι�� ����")).click();
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    Thread.sleep(3000);
		String pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("��ũURL �ٿ�ε�")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 5000);
	    pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** innerBanner_add Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(4, 4, "campaignName_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".input-sm", 0).setValue(date);
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(5, 5, "bannerName_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".input-sm", 1).setValue(date);
	    $(".input-sm", 2).setValue("");
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(6, 6, "linkURL_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".input-sm", 2).setValue("http://" + date + ".com");
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(7, 7, "innerBanner_add_alert");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("��ũURL �ٿ�ε�")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_add End ----- ! ");
	}
	@Test(priority = 14)
	public void innerBanner_duplicationAdd() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 5000);
	    String pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** innerBanner_duplicationAdd Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".input-sm", 0).setValue(date);
	    $(".input-sm", 1).setValue(date);
	    $("#btn-save").click();
	    valCheck(4, 4, "innerBanner_duplicationAdd");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".w100", 1).click();
	    Thread.sleep(1500);
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("��ũURL �ٿ�ε�")) {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 15)
	public void innerBanner_search() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_search Start ----- ! ");
	    $("#s_key").setValue(date);
	    $("#btn-search").click();
		String pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("��ũURL �ٿ�ε�")) {
			System.out.println(" *** innerBanner_search list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-detail-view").click();
		$(".text-left", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-left", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** innerBanner_search show link load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search show link load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_search End ----- ! ");
	}
	@Test(priority = 16)
	public void innerBanner_linkURLdownload() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_linkURLdownload Start ----- ! ");
	    $(".btn-dark", 0).click();
	    $(".modal-backdrop").waitUntil(visible, 5000);
	    String pageLoadCheck = $(".btn-fileDown").text();
	    if(pageLoadCheck.equals("�ٿ�ε�")) {
	    	System.out.println(" *** innerBanner_linkURLdownload popup load Success !! *** ");
	    } else {
	    	System.out.println(" *** innerBanner_linkURLdownload popup load Fail ... !@#$%^&*() *** ");	   
	    	close();
	    }
	    brokenLinkCheck("linkURLdownload", "https://new.acecounter.com/setting/contents/inBanner/download?campaign_type=inlink&down_type=urlDown&down_key=" + date1 + "~" + date1);
	    $(".close", 0).click();
		System.out.println(" ! ----- innerBanner_linkURLdownload End ----- ! ");
	}
	@Test(priority = 17)
	public void innerBanner_del() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_del Start ----- ! ");
	    $(".btn-gray", 0).click();
	    $("#btn-del").waitUntil(visible, 10000);
	    String pageLoadCheck = $("#btn-del").text();
	    if(pageLoadCheck.equals("���� �׸� ����")) {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    } else {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    	close();
	    }
	    $("#btn-del").click();
	    valCheck(3, 8, "innerBanner_del_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $("#campaignAllChk").click();
	    $("#btn-del").click();
	    valCheck(4, 9, "innerBanner_del_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    valCheck(5, 11, "innerBanner_del_alert");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("��ũURL �ٿ�ε�")) {
			System.out.println(" *** innerBanner_del Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_del End ----- ! ");
	}
	@Test(priority = 18)
	public void fileDownload_add() {
		System.out.println(" ! ----- fileDownload_add Start ----- ! ");
	    $(By.linkText("���ϴٿ�ε�")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
		String pageLoadCheck = $(".col-xs-9").text();
		if(pageLoadCheck.equals("���ϴٿ�ε�����")) {
			System.out.println(" *** fileDownload_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".btn-info", 0).click();
	    $(".mv20").waitUntil(visible, 10000);
	    pageLoadCheck = $(".mv20").text();
	    if(pageLoadCheck.equals("'*'�� �̿��� ������ ���ڿ��� �������� ����մϴ�.(*.zip���� ����� ��� �������� �ٿ�ε� ��ũ�� ���� �м��մϴ�.)")) {
			System.out.println(" *** fileDownload_add add UI Success !! *** ");	    	
	    } else {
			System.out.println(" *** fileDownload_add add UI Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
	    $("#btn-add").click();
	    valCheck(4, 3, "downPattern_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $("#download-pattern").setValue(date);
	    $("#btn-add").click();
	    valCheck(5, 4, "downPattern_add_alert");
	    $(".mv20").waitUntil(hidden, 10000);
	    pageLoadCheck = $(".col-xs-9").text();
		if(pageLoadCheck.equals("���ϴٿ�ε�����")) {
			System.out.println(" *** fileDownload_add refresh Page load Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_add refresh Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_add End ----- ! ");
	}
	@Test(priority = 19)
	public void fileDownload_duplicationAdd() {
		System.out.println(" ! ----- fileDownload_duplicationAdd Start ----- ! ");
	    $(".btn-info", 0).click();
	    $(".mv20").waitUntil(visible, 10000);
	    String pageLoadCheck = $(".mv20").text();
	    if(pageLoadCheck.equals("'*'�� �̿��� ������ ���ڿ��� �������� ����մϴ�.(*.zip���� ����� ��� �������� �ٿ�ε� ��ũ�� ���� �м��մϴ�.)")) {
			System.out.println(" *** fileDownload_add add UI Success !! *** ");	    	
	    } else {
			System.out.println(" *** fileDownload_add add UI Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
	    $("#download-pattern").setValue(date);
	    $("#btn-add").click();
	    valCheck(4, 3, "downPattern_duplicationAdd_alert");
	    $("#btn-add-cancel").click();
	    
		System.out.println(" ! ----- innerBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 20)
	public void fileDownload_search() {
		System.out.println(" ! ----- fileDownload_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
		String pageLoadCheck = $(".col-xs-9").text();
		if(pageLoadCheck.equals("���ϴٿ�ε�����")) {
			System.out.println(" *** fileDownload_search delList selectbox Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search delList selectbox Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    $("td", 1).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 1).text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** fileDownload_search delList search Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
	    pageLoadCheck = $(".col-xs-9").text();
		if(pageLoadCheck.equals("���ϴٿ�ε�����")) {
			System.out.println(" *** fileDownload_search setList selectbox Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search setList selectbox Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    $("td", 3).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 3).text();
	    if(pageLoadCheck.equals(date)) {
			System.out.println(" *** fileDownload_search setList search Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_search End ----- ! ");
	}
	@Test(priority = 21)
	public void fileDownload_del() {
		System.out.println(" ! ----- fileDownload_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "downPattern_del_null");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "downPattern_del_confirm");
	    valCheck(6, 6, "downPattern_del_alert");
	    $("#inlineCheckbox1").waitUntil(hidden, 10000);
		String pageLoadCheck = $("td", 1).text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** fileDownload_del page load Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_del page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    
		System.out.println(" ! ----- innerBanner_del End ----- ! ");
	}
	@Test(priority = 22)
	public void outLinkBanner_add() {
		System.out.println(" ! ----- outLinkBanner_add Start ----- ! ");
	    $(By.linkText("�ƿ���ũ ���")).click();
		String pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** outLinkBanner_add list page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** outLinkBanner_add register page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		valCheck(4, 4, "outLinkBanner_promotionName_null");
		$("#promotion-name").setValue(date);
		$(".btn-info").click();
		valCheck(5, 5, "outLinkBanner_name_null");
		$(".input-sm", 2).setValue(date);
		$(".btn-info").click();
		valCheck(6, 6, "filePath_null");
		$(".input-sm", 3).setValue(date);
		$(".btn-info").click();
		valCheck(7, 7, "outLinkBanner_linkURL_null");
		$(".input-sm", 4).setValue(date);
		$(".btn-info").click();
		valCheck(8, 8, "outLinkBanner_linkURL_badURL");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$(".btn-info").click();
		valCheck(9, 9, "outLinkBanner_add_alert");		
		$("#btn-search").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** outLinkBanner_add register Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- outLinkBanner_add End ----- ! ");
	}
	@Test(priority = 23)
	public void outLinkBanner_duplicationAdd() {
		System.out.println(" ! ----- outLinkBanner_duplicationAdd Start ----- ! ");
	    $(".btn-info", 0).click();
		$("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** outLinkBanner_add register page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#promotion-name").setValue(date);
		$(".input-sm", 2).setValue(date);
		$(".input-sm", 3).setValue(date);
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$(".btn-info").click();
		valCheck(4, 4, "outLinkBanner_promotionName_duplication");		
		$(".btn-light").click();
		$("#btn-search").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** outLinkBanner_duplicationAdd check Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_duplicationAdd check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- outLinkBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 24)
	public void outLinkBanner_search() {
		System.out.println(" ! ----- outLinkBanner_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $("th", 5).waitUntil(visible, 10000);
	    String pageLoadCheck = $("th", 5).text();
	    if(pageLoadCheck.equals("������")) {
	    	System.out.println(" *** outLinkBanner_search delList Selectbox Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search delList Selectboxt Fail ... !@#$%^&*() *** ");
			close();
	    }
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td").waitUntil(visible, 10000);
		pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** outLinkBanner_search delList search Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    pageLoadCheck = $("th", 5).text();
	    if(pageLoadCheck.equals("������")) {
	    	System.out.println(" *** outLinkBanner_search setList Selectbox Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search setList Selectboxt Fail ... !@#$%^&*() *** ");
			close();
	    }
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 2).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 2).text();
	    if(pageLoadCheck.equals(date)) {
	    	System.out.println(" *** outLinkBanner_search setList search Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search setList search Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_search End ----- ! ");
	}
	@Test(priority = 25)
	public void outLinkBanner_modify() {
		System.out.println(" ! ----- outLinkBanner_modify Start ----- ! ");
	    $(".br-dark", 0).click();
	    $("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�����ϱ�")) {
			System.out.println(" *** outLinkBanner_modify page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_modify page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#promotion-name").setValue(date + "����");
		$(".input-sm", 2).setValue(date + "����");
		$(".input-sm", 3).setValue(date + "����");
		$(".btn-info").click();
		valCheck(4, 4, "outLinkBanner_modify_alert");
		$(".btn-promotion-detail-view").waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 2).text();
	    System.out.println(pageLoadCheck);
	    if(pageLoadCheck.equals(date + "����")) {
	    	System.out.println(" *** outLinkBanner_modify Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_modify Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_modify End ----- ! ");
	}
	@Test(priority = 26)
	public void outLinkBanner_del() {
		System.out.println(" ! ----- outLinkBanner_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "outLinkBanner_del_null");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "outLinkBanner_del_confirm");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    valCheck(6, 6, "outLinkBanner_del_alert");
	    $(".modal-backdrop").waitUntil(hidden, 10000);
	    $("td").waitUntil(visible, 10000);
	    String pageLoadCheck = $("td").text();
	    if(pageLoadCheck.equals("����� �����ϴ�.")) {
	    	System.out.println(" *** outLinkBanner_del Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_del Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}