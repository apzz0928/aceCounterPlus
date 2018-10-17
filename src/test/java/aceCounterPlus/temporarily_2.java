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
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
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
		ScreenShooter.captureSuccessfulTests = true;

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
	
	public static void valCheck(int pTagNum, int btnNum, String val) {
		$(".modal-backdrop").waitUntil(visible, 10000);
		String msgCheck = $("p", pTagNum).text();
        switch(val){
            case "dynamicPage_URL_null": checkMsg = "����������URL�� �Է��ϼ���.";
            break;
            case "dynamicPage_URL_check": checkMsg = "�Է��Ͻ� ������URL��\n" + "������������ �ش���� �ʽ��ϴ�.\n" + "�ٽ� Ȯ�� �� �Է����ּ���.";
            break;
            case "dynamicPage_exclude_URL_char_check": checkMsg = "?, %, = �� ���ܹ��ڷ� ����Ͻ� �� �����ϴ�.";
            break;
            case "dynamicPage_URL_add_success": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
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
		Thread.onSpinWait();
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		    $(".modal-backdrop").waitUntil(hidden, 10000);
		} else if (msgCheck.isEmpty()) {
			System.out.println(" *** �١ڡ١ڡ١� pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - msgCheck is Empty ... �١ڡ١ڡ١� *** ");
			close();
		} else {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Fail ... !@#$%^&*() *** ");
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
	public void URLSetting_dynamicPage_add() {
		System.out.println(" ! ----- URLSetting_dynamicPage_add Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
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
		$("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�湮��")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".accordion-toggle", 3).click();
		$(By.linkText("URL ����")).waitUntil(visible, 10000);
		$(By.linkText("URL ����")).click();
		$(".col-xs-5").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-info", 0).text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(10, 7, "dynamicPage_URL_null");
		$("#page-url").setValue("/test");
		$("#btn-add").click();
		valCheck(11, 8, "dynamicPage_URL_check");
		$("#page-url").setValue("http://test00011.org/search?q=123");
		$(".w300").setValue("=");
		$("#btn-add").click();
		valCheck(12, 9, "dynamicPage_exclude_URL_char_check");
		$(".w300").setValue("test");
		$("#btn-add").click();
		valCheck(13, 10, "dynamicPage_URL_add_success");
		$(".text-nowrap").waitUntil(visible, 10000);
		
		
		
		
		
		
		
		System.out.println(" ! ----- URLSetting_URLInclusion End ----- ! ");
	}
	@Test(priority = 1)
	public void URLSetting_URLExcept_Add() {
		System.out.println(" ! ----- URLSetting_URLExcept_Add Start ----- ! ");
		$(By.linkText("URL����")).click();
		$(".col-xs-9").waitUntil(visible, 10000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... !@#$%^&*() *** ");
			close();
		}
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
		$(".gui-input").waitUntil(visible, 10000);
		$("#btn-add").click();
		valCheck(6, 4, "exceptChar_alert");
		$(".input-sm", 0).setValue(date);
		$("#btn-add").click();
		valCheck(7, 5, "exceptAdd_confirm");
		valCheck(8, 7, "exceptAdd_alert");
		$("th", 0).waitUntil(hidden, 10000);
		$(".col-xs-9", 0).waitUntil(visible, 10000);
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
	public void URLSetting_URLExcept_search() {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $("td", 8).waitUntil(visible, 10000);
	    String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox del Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
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
	    $(".muted").waitUntil(hidden, 10000);
	    pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox set Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox set Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
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
	public void URLSetting_URLExcept_del() {
		System.out.println(" ! ----- URLSetting_URLExcept_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(5, 3, "delExceptCharCheck_alert");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(6, 4, "delExceptChar_confirm");
	    valCheck(7, 6, "delExceptChar_alert");
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
	public void URLSetting_innerSearch_add() {
		System.out.println(" ! ----- URLSetting_innerSearch_add Start ----- ! ");
		$(By.linkText("���ΰ˻�")).click();
		$(".col-xs-8").waitUntil(visible, 10000);
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
		$("#page-url").setValue("/" + date);
		$("#btn-add").click();
		valCheck(5, 4, "searchVar_null");
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		valCheck(6, 5, "innerSearch_add_alert");
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
	public void URLSetting_innerSearch_duplicationAdd() {
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* �м��ϰ��� �ϴ� ���ΰ˻� ��� ������ URL�� �Է��մϴ�.")) {
			System.out.println(" *** URLSetting_innerSearch_duplicationAdd UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_duplicationAdd UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#page-url").setValue("/" + date);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		valCheck(4, 3, "innerSearch_deplication_add_alert");
		$("#btn-add-cancel").click();
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd End ----- ! ");
	}
	@Test(priority = 6)
	public void URLSetting_innerSearch_search() {
		System.out.println(" ! ----- URLSetting_innerSearch_search Start ----- ! ");
		$(By.linkText("���ΰ˻�")).click();
		String pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch search_selectbox load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
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
	public void URLSetting_innerSearch_del() {
		System.out.println(" ! ----- URLSetting_innerSearch_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "innerSearch_del_null");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "innerSearch_del_confirm");
	    valCheck(6, 6, "innerSearch_del_alert");
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
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}