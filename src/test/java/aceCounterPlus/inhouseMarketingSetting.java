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

public class inhouseMarketingSetting {
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
            case "viralAdd_cmpName_null": checkMsg = "ķ���θ��� �Է��ϼ���.";
            break;
            case "viralAdd_cmpName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "viralAdd_subjectMatter_null": checkMsg = "���縦 �Է��ϼ���.";
            break;
            case "viralAdd_subjectMatter_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "viralAdd_URL_null": checkMsg = "���� URL�� �Է��ϼ���.";
            break;
            case "viralAdd_URL_validation": checkMsg = "�ùٸ� URL�� �Է��ϼ���.";
            break;
            case "viralAdd_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "viraldupAdd_confirm": checkMsg = "��ϵ� ķ������ �ֽ��ϴ�. �ش縮��Ʈ�� ������Ʈ�Ͻðڽ��ϱ�?";
            break;
            case "viraldupAdd_alert": checkMsg = "�ߺ��� ���簡 �ֽ��ϴ�. �ٽ� �Է��ϼ���.";
            break;
            case "viralSetting_del_null": checkMsg = "������ ���̷� ������ �����ϼ���.";
            break;
            case "viralSetting_del_confirm": checkMsg = "������ ���̷� ������ �����Ͻðڽ��ϱ�?\n" + "���̷� ������ ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "viralSetting_del_alert": checkMsg = "���̷� ���� ������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "emailAdd_cmpName_null": checkMsg = "ķ���θ��� �Է��ϼ���.";
            break;
            case "emailAdd_cmpName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "emailAdd_sendDay_null": checkMsg = "�߼����� �Է��ϼ���.";
            break;
            case "emailAdd_sendNumber_null": checkMsg = "�߼ۼ��� �Է��ϼ���.";
            break;
            case "emailAdd_sendNumber_validation": checkMsg = "���ڸ� �Է��ϼ���.";
            break;
            case "emailAdd_URL_null": checkMsg = "���� URL�� �Է��ϼ���.";
            break;
            case "emailAdd_URL_validation": checkMsg = "�ùٸ� URL�� �Է��ϼ���.";
            break;
            case "emailAdd_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "emailAdd_dupAdd_alert": checkMsg = "�̹� ����� ķ���θ��Դϴ�.\n" + "�ٸ� ķ���θ��� ������ּ���.";
            break;
            case "emailSetting_del_null": checkMsg = "������ �̸��� ������ �����ϼ���.";
            break;
            case "emailSetting_del_confirm": checkMsg = "������ �̸��� ������ �����Ͻðڽ��ϱ�?\n" + "�̸��� ������ ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "emailSetting_del_alert": checkMsg = "�̸��� ���� ������ �Ϸ�Ǿ����ϴ�.";
            break;
            case "talkAdd_cmpName_null": checkMsg = "ķ���θ��� �Է��ϼ���.";
            break;
            case "talkAdd_cmpName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "talkAdd_sendDay_null": checkMsg = "�߼����� �Է��ϼ���.";
            break;
            case "talkAdd_subjectMatter_null": checkMsg = "���縦 �Է��ϼ���.";
            break;
            case "talkAdd_subjectMatter_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
            break;
            case "talkAdd_sendNumber_null": checkMsg = "�߼ۼ��� �Է��ϼ���.";
            break;
            case "talkAdd_sendNumber_validation": checkMsg = "���ڸ� �Է��ϼ���.";
            break;
            case "talkAdd_URL_null": checkMsg = "���� URL�� �Է��ϼ���.";
            break;
            case "talkAdd_URL_validation": checkMsg = "�ùٸ� URL�� �Է��ϼ���.";
            break;
            case "talkAdd_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
            break;
            case "talkdupAdd_confirm": checkMsg = "��ϵ� ķ������ �ֽ��ϴ�. �ش縮��Ʈ�� ������Ʈ�Ͻðڽ��ϱ�?";
            break;
            case "talkdupAdd_alert": checkMsg = "�ߺ��� ���簡 �ֽ��ϴ�. �ٽ� �Է��ϼ���.";
            break;
            case "talkSetting_del_null": checkMsg = "������ TALK ������ �����ϼ���.";
            break;
            case "talkSetting_del_confirm": checkMsg = "������ TALK ������ �����Ͻðڽ��ϱ�?\n" + "TALK ������ ���� ����/�м��� �����Ǹ�,\n" + "���� �� ������ �Ұ����մϴ�.";
            break;
            case "talkSetting_del_alert": checkMsg = "TALK ���� ������ �Ϸ�Ǿ����ϴ�.";
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
	public void viralSetting_add() {
		System.out.println(" ! ----- viralSetting_add Start ----- ! ");
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
		$(".accordion-toggle", 4).click();
		$(By.linkText("���̷� ����")).waitUntil(visible, 10000);
		$(By.linkText("���̷� ����")).click();
		$("td", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 0).text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** viralSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-save").text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** viralSetting_add add page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_add add page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		valCheck(4, 4, "viralAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck(5, 5, "viralAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$("#btn-save").click();
		valCheck(6, 6, "viralAdd_subjectMatter_null");
		$(".input-sm", 1).setValue(date + "@");
		$("#btn-save").click();
		valCheck(7, 7, "viralAdd_subjectMatter_validation");
		$(".input-sm", 1).setValue(date);
		$(".input-sm", 2).setValue("");
		$("#btn-save").click();
		valCheck(8, 8, "viralAdd_URL_null");
		$(".input-sm", 2).setValue(date);
		$("#btn-save").click();
		valCheck(9, 9, "viralAdd_URL_validation");
		$(".input-sm", 2).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(10, 10, "viralAdd_register");
		$(".btn-fileDown").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-fileDown").text();
		if(pageLoadCheck.equals("�ٿ�ε�")) {
			System.out.println(" *** viralSetting_add register Success !! *** ");			
		} else {
			System.out.println(" *** viralSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_add End ----- ! ");
	}
	@Test(priority = 1)
	public void viralSetting_duplicationAdd() {
		System.out.println(" ! ----- viralSetting_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		String pageLoadCheck = $("#btn-save").text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** viralSetting_duplicationAdd add page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_duplicationAdd add page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date);
		$(".input-sm", 2).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(4, 4, "viraldupAdd_confirm");
		valCheck(5, 6, "viraldupAdd_alert");
		$(".w100", 1).click();
		$("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_duplicationAdd list page load Success !! *** ");			
		} else {
			System.out.println(" *** viralSetting_duplicationAdd list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_duplicationAdd End ----- ! ");
	}
	@Test(priority = 2)
	public void viralSetting_search() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $(".btn-gray", 0).waitUntil(hidden, 10000);
		String pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			System.out.println(" *** viralSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 0).text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** viralSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".text-left").waitUntil(visible, 10000);
	    pageLoadCheck = $(".text-left").text();
	    if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search set selectBox page load Success !! *** ");	    	
	    } else {
			System.out.println(" *** viralSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
		$("#s_key").setValue(date);
		$("#btn-search").click();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search setList search Success !! *** ");  	
	    } else {
			System.out.println(" *** viralSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 3)
	public void viralSetting_mktCodeDownload() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(".btn-dark", 0).click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		String pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			System.out.println(" *** viralSetting_mktCodeDownload popup load Success !! *** ");  	
		} else {
			System.out.println(" *** viralSetting_mktCodeDownload popup load Fail ... !@#$%^&*() *** ");
			close();
		}
		brokenLinkCheck("mktCodeDownload", "https://new.acecounter.com/setting/inhouse/download?campaign_type=viral&down_type=urlDown&down_key=" + date1 + "~" + date1);
		$(".close", 0).click();
		$(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 4)
	public void viralSetting_del() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(".btn-gray", 0).click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck(3, 4, "viralSetting_del_null");
		$("#campaignAllChk").click();
		$("#btn-del").click();
		valCheck(4, 5, "viralSetting_del_confirm");
		valCheck(5, 7, "viralSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		String pageLoadCheck = $("td", 0).text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** viralSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 5)
	public void emailSetting_add() {
		System.out.println(" ! ----- emailSetting_add Start ----- ! ");
		$(By.linkText("�̸��� ����")).click();
		$("td").waitUntil(visible, 10000);
		String pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** emailSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** emailSetting_add add Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		valCheck(3, 3, "emailAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck(4, 4, "emailAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue("");
		$("#btn-save").click();
		valCheck(5, 5, "emailAdd_sendDay_null");
		$(".input-sm", 1).setValue(date1);
		$("#btn-save").click();
		valCheck(6, 6, "emailAdd_sendNumber_null");
		$(".input-sm", 2).setValue("test");
		$("#btn-save").click();
		valCheck(7, 7, "emailAdd_sendNumber_validation");
		$(".input-sm", 2).setValue("1234");
		$(".input-sm", 3).setValue("");
		$("#btn-save").click();
		valCheck(8, 8, "emailAdd_URL_null");
		$(".input-sm", 3).setValue(date);
		$("#btn-save").click();
		valCheck(9, 9, "emailAdd_URL_validation");
		$(".input-sm", 3).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(10, 10, "emailAdd_register");
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** emailSetting_add register Success !! *** ");			
		} else {
			System.out.println(" *** emailSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_add End ----- ! ");
	}
	@Test(priority = 6)
	public void emailSetting_duplicationAdd() {
		System.out.println(" ! ----- emailSetting_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** emailSetting_add add Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date1);
		$(".input-sm", 2).setValue("1234");
		$(".input-sm", 3).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(3, 3, "emailAdd_dupAdd_alert");
		$(".btn-light", 0).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** emailSetting_duplicationAdd list page load Success !! *** ");			
		} else {
			System.out.println(" *** emailSetting_duplicationAdd  list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_duplicationAdd End ----- ! ");
	}
	@Test(priority = 7)
	public void emailSetting_search() {
		System.out.println(" ! ----- emailSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".btn-gray").waitUntil(hidden, 10000);
		String pageLoadCheck = $(".btn-info").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** emailSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		$(".no-records-found").waitUntil(visible, 10000);
		pageLoadCheck = $(".no-records-found").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** viralSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** emailSetting_search set selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search setList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_search End ----- ! ");
	}
	@Test(priority = 8)
	public void emailSetting_del() {
		System.out.println(" ! ----- emailSetting_del Start ----- ! ");
		$(".btn-gray").click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck(3, 3, "emailSetting_del_null");
		$("#campaignAllChk").click();
		$("#btn-del").click();
		valCheck(4, 4, "emailSetting_del_confirm");
		valCheck(5, 6, "emailSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		String pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** viralSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_del End ----- ! ");
	}
	@Test(priority = 9)
	public void talkSetting_add() {
		System.out.println(" ! ----- talkSetting_add Start ----- ! ");
		$(By.linkText("Talk ����")).click();
		$("td").waitUntil(visible, 10000);
		String pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** talkSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		$("#btn-save").waitUntil(visible, 10000);
		$("#btn-save").click();
		valCheck(3, 3, "talkAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck(4, 4, "talkAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue("");
		$("#btn-save").click();
		valCheck(5, 5, "talkAdd_sendDay_null");
		$(".input-sm", 1).setValue(date1);
		$("#btn-save").click();
		valCheck(6, 6, "talkAdd_subjectMatter_null");
		$(".input-sm", 2).setValue(date + "@");
		$("#btn-save").click();
		valCheck(7, 7, "talkAdd_subjectMatter_validation");
		$(".input-sm", 2).setValue(date);
		$("#btn-save").click();
		valCheck(8, 8, "talkAdd_sendNumber_null");
		$(".input-sm", 3).setValue("test");
		$("#btn-save").click();
		valCheck(9, 9, "talkAdd_sendNumber_validation");
		$(".input-sm", 3).setValue("1234");
		$(".input-sm", 4).setValue("");
		$("#btn-save").click();
		valCheck(10, 10, "talkAdd_URL_null");
		$(".input-sm", 4).setValue(date);
		$("#btn-save").click();
		valCheck(11, 11, "talkAdd_URL_validation");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(12, 12, "talkAdd_register");
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_add End ----- ! ");
	}
	@Test(priority = 10)
	public void talkSetting_duplicationAdd() {
		System.out.println(" ! ----- talkSetting_duplicationAdd Start ----- ! ");
		$(".btn-info").click();
		$("#btn-save").waitUntil(visible, 10000);
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date1);
		$(".input-sm", 2).setValue(date);
		$(".input-sm", 3).setValue("1234");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck(3, 3, "talkdupAdd_confirm");
		valCheck(4, 5, "talkdupAdd_alert");
		$(".btn-light", 0).click();
		$(".btn-gray").waitUntil(visible, 10000);
		String pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_duplicationAdd  End ----- ! ");
	}
	@Test(priority = 11)
	public void talkSetting_search() {
		System.out.println(" ! ----- talkSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".btn-gray").waitUntil(hidden, 10000);
		String pageLoadCheck = $(".btn-material-list").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** talkSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		$(".no-records-found").waitUntil(visible, 10000);
		pageLoadCheck = $(".no-records-found").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** talkSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** talkSetting_search set selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_search setList search Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_search  End ----- ! ");
	}
	@Test(priority = 12)
	public void talkSetting_del() {
		System.out.println(" ! ----- talkSetting_del Start ----- ! ");
		$(".btn-gray").click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck(3, 3, "talkSetting_del_null");
		$("#campaignAllChk").click();
		$("#btn-del").click();
		valCheck(4, 4, "talkSetting_del_confirm");
		valCheck(5, 6, "talkSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		String pageLoadCheck = $("td").text();
		if(pageLoadCheck.equals("��ϵ� ķ������ �����ϴ�.\n" + "�߰��� Ŭ���� ķ������ ����ϼ���.")) {
			System.out.println(" *** talkSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_del  End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}