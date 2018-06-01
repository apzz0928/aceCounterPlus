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

public class contentSetting {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("MMddhhmmss");
    String date = number_format.format(number_date);
    
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
		if(val.equals("URL_null")) {
			checkMsg = "����������URL�� �Է��ϼ���.";
		} else if (val.equals("URL_check")) {
			checkMsg = "�Է��Ͻ� ������URL��\n" + 
					"������������ �ش���� �ʽ��ϴ�.\n" + 
					"�ٽ� Ȯ�� �� �Է����ּ���.";
		} else if (val.equals("URL_del_check")) {
			checkMsg = "������ ������������ �����ϼ���.";
		}  else if (val.equals("exceptChar_alert")) {
			checkMsg = "���ܹ��ڸ� �Է��ϼ���.";
		} else if (val.equals("exceptAdd_confirm")) {
			checkMsg = "������ ���� ���ڸ� ����Ͻðڽ��ϱ�?\n" + 
					"Ȯ���� Ŭ���ϸ�\n" + 
					"URL�� ������ ���� ���� ����\n" + 
					"�����Ͽ� �м��մϴ�.";
		} else if (val.equals("exceptAdd_alert")) {
			checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
		} else if (val.equals("delExceptCharCheck_alert")) {
			checkMsg = "������ URL ���ܹ��ڸ� �����ϼ���.";
		} else if (val.equals("delExceptChar_confirm")) {
			checkMsg = "������ URL ���ܹ��ڸ� �����ϰڽ��ϱ�?\n" + 
					"URL ���� ���ڿ� ���� ����/�м��� �����Ǹ�,\n" + 
					"���� �� ������ �Ұ����մϴ�.";
		} else if (val.equals("delExceptChar_alert")) {
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
		} else if (val.equals("")) {
			checkMsg = "";
		} else if (val.equals("")) {
			checkMsg = "";
		}
		$(".modal-backdrop").waitUntil(visible, 5000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " - check Fail ... *** ");
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
			System.out.println(" *** Login Fail ... *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�湮��")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#redirectConfBtn").waitUntil(visible, 3000);
		$(".accordion-toggle", 3).click();
		$(By.linkText("URL ����")).click();
		Thread.sleep(1000);
		$(".btn-info", 0).waitUntil(visible, 3000);
		pageLoadCheck = $(".btn-info", 0).text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** URLSetting_URLInclusion Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 3000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... *** ");
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
		//$(By.linkText("URL ����")).click();
		$(By.linkText("URL����")).click();
		Thread.sleep(1000);
		$(".col-xs-9").waitUntil(visible, 3000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 3000);
		pageLoadCheck = $("th", 0).text();
		if(pageLoadCheck.equals("���� ���� ����")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... *** ");
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
		Thread.sleep(1000);
		valCheck(8, 7, "exceptAdd_alert");
		Thread.sleep(2500);
		$(".col-xs-9", 0).waitUntil(visible, 5000);
		pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL ���� ����(����)")) {
			System.out.println(" *** URLSetting_URLExcept_Add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_Add Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_Add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_URLExcept_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$("td", 3).waitUntil(visible, 3000);
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_search End ----- ! ");
	}
	@Test(priority = 3)
	public void URLSetting_URLExcept_del() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 3000);
	    $("#btn-list-select-delete").click();
	    valCheck(5, 3, "delExceptCharCheck_alert");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(6, 4, "delExceptChar_confirm");
	    Thread.sleep(1000);
	    valCheck(7, 6, "delExceptChar_alert");
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("����� �����ϴ�.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}