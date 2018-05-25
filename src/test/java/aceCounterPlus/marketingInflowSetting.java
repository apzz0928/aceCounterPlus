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

public class marketingInflowSetting {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain;
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
	
	public static void alertCheck(String check1, String check2, int i) {
		String alertCheck = "";
		if(check1.equals("�Է�")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals(check2 + " �Է����ּ���.")) {
				$(".btn-sm", i+1).click();
				System.out.println(" *** " + check2 + " missing check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " missing check Fail *** ");
				close();
			}
		} else if (check1.equals("��ȿ��")) {
			alertCheck = $("p", i).text();
			$("p", i).click();
			$("p", i).click();
			if(alertCheck.equals("�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.")) {
				if(check2.equals("�����ǰ��")) {
					$(".btn-sm", i-1).click();
				} else {
					$(".btn-sm", i+1).click();
				}
				System.out.println(" *** " + check2 + " validation check Success *** ");
			} else {
				System.out.println(" *** " + check2 + " validation check Fail *** ");
				close();
			}
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
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " ***");
            	close();
            } else {
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " ***");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }
	
	@Test(priority = 0)
	public void mktInflowSetting_add() throws InterruptedException {
		System.out.println(" ----- mktInflowSetting_add Start ----- ");
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
			System.out.println(" *** statsLiveDashboard Page load Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page load Fail ... *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#inflowAddBtn").waitUntil(visible, 3000);
		pageLoadCheck = $("#inflowAddBtn").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** appmarketing page Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page Fail ... *** ");
			close();
		}
		$("#inflowAddBtn").click();
		$(By.xpath("//form[@id='mainForm']/div/div[2]/table/tbody/tr[2]/td/div/div/label[2]")).click();
		$("#btnReg").click();
		alertCheck("�Է�", "ķ���θ���", 4);
		$("#campaign_nm").setValue(domain + date + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "ķ���θ�", 5);
		$("#campaign_nm").setValue(domain + date + date + ".com");
		$("#btnReg").click();
		alertCheck("�Է�", "���縦", 6);
		$("#campaign_material_value0").setValue(domain + date + ".com@");
		$("#btnReg").click();
		alertCheck("��ȿ��", "����", 7);
		$("#campaign_material_value0").setValue(domain + date + ".com");
		$("#btnReg").click();
		//alertCheck("�Է�", "���� URL�� ", 8); alert ������ �ϼ���. �� ����ȭ ����Ŵ ���� ���� �ʿ�
		$("p", 8).waitUntil(visible, 3000);
		String msgCheck = $("p", 8).text();
		Thread.sleep(1500);
		if(msgCheck.equals("���� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 9).click();
			System.out.println(" *** link URL Missing Check Success !! *** ");
		} else {
			System.out.println(" *** link URL Missing Check Fail ... *** ");
			close();
		}
		$("#original_url0").setValue("a");
		$("#btnReg").click();
		$("p", 9).waitUntil(visible, 3000);
		msgCheck = $("p", 9).text();
		Thread.sleep(1500);
		if(msgCheck.equals("�ùٸ� URL�� �Է��ϼ���.")) {
			$(".btn-sm", 10).click();
			System.out.println(" *** link URL validation check Success !! *** ");
		} else {
			System.out.println(" *** link URL validation check Fail ... *** ");
			close();
		}
		$("#original_url0").setValue(domain + date + ".com");
		$("#btnReg").click();
		$("p", 10).waitUntil(visible, 3000);
		msgCheck = $("p", 10).text();
		Thread.sleep(2500);
		if(msgCheck.equals("��Ͽ� �����߽��ϴ�.")) {
			$(".btn-sm", 11).click();
			System.out.println(" *** Add Marketing Inflow settings Success !! *** ");
		} else {
			System.out.println(" *** Add Marketing Inflow settings Fail ... *** ");
			close();
		}
		System.out.println(" ----- mktInflowSetting_add End ----- ");
	}
	@Test(priority = 1)
	public void mktInflowSetting_del() throws InterruptedException {
		System.out.println(" ----- mktInflowSetting_del Start ----- ");
		Thread.sleep(2000);
		$("#inflowAddBtn").waitUntil(visible, 3000);
		String pageLoadCheck = $("#inflowAddBtn").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** appmarketing page Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page Fail ... *** ");
			close();
		}
		$("#deleteViewBtn").click();
		$("#deleteBtn").click();
		$("p", 3).waitUntil(visible, 3000);
		String msgCheck = $("p", 3).text();
		Thread.sleep(1000);
		if(msgCheck.equals("������ ������ ���� ������ �����ϼ���.")) {
			$(".btn-sm", 4).click();
			System.out.println(" *** marketing delete validation check Sueecss !! *** ");
		} else {
			System.out.println(" *** marketing delete validation check Fail ... *** ");
			close();
		}
		$("#checkAllCamp").click();
		$("#deleteBtn").click();
		msgCheck = $("p", 4).text();
		Thread.sleep(1000);
		if(msgCheck.equals("������ ������ ���� ������ �����Ͻðڽ��ϱ�?\n" + 
				"������ ���Լ��� ������ ���� ����/�м��� �����Ǹ�,\n" + 
				"���� �� ������ �Ұ����մϴ�.")) {
			$("#btn-modal-alert-yes").click();
			System.out.println(" *** marketing delete confirm check Sueecss !! *** ");
		} else {
			System.out.println(" *** marketing delete confirm check Fail ... *** ");
			close();
		}
		msgCheck = $("p", 5).text();
		Thread.sleep(2000);
		if(msgCheck.equals("������ ���� ���� ������ �Ϸ�Ǿ����ϴ�.")) {
			$(".btn-sm", 7).click();
			System.out.println(" *** marketing delete alert check Sueecss !! *** ");
		} else {
			System.out.println(" *** marketing delete alert confirm check Fail ... *** ");
			close();
		}
		System.out.println(" ----- mktInflowSetting_del End ----- ");
	}
	@Test(priority = 2)
	public void advertisingCodeDownload() throws InterruptedException {
		System.out.println(" ----- advertisingCodeDownload Start ----- ");
		Thread.sleep(2500);
		$("#inflowMrkCodeDown").waitUntil(visible, 3000);
		String pageLoadCheck = $("#inflowMrkCodeDown").text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			System.out.println(" *** appmarketing page load Success !! *** ");
		} else {
			System.out.println(" *** appmarketing page load Fail ... *** ");
			close();
		}
		$("#inflowMrkCodeDown").click();
		Thread.sleep(1000);
		$("h4", 0).waitUntil(visible, 3000);
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			brokenLinkCheck("advCodeDown", "https://new.acecounter.com/setting/appmarketing/codedown?inflow_media_cd=&inflow_mrkt_channel_dcd=10&down_term_cate=ALL&create_dt_st=&create_dt_ed=&original_url_yn=n&use_yn=y");
			System.out.println(" *** advCodeDownload layer check Sueecss !! *** ");
			$(".close", 0).click();
		} else {
			System.out.println(" *** advCodeDownload layer check Fail ... *** ");
			close();
		}
		System.out.println(" ----- advertisingCodeDownload End ----- ");
	}
	@Test(priority = 3)
	public void advertisingProductManage_add() throws InterruptedException {
		System.out.println(" ----- advertisingProductManage_add Start ----- ");
		Thread.sleep(2000);
		$(".btn-dark", 0).click();
		Thread.sleep(1000);
		$("#addViewBtn").waitUntil(visible, 3000);
		String pageLoadCheck = $("#addViewBtn").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** advProductManage page load Success !! *** ");
		} else {
			System.out.println(" *** advProductManage page load Fail ... *** ");
			close();
		}
		$("#addViewBtn").click();
		$(".gui-input").waitUntil(visible, 2000);
		Thread.sleep(1000);
		$("#btnRegister").click();
		Thread.sleep(2000);
		String alertCheck = $("p", 4).text();
		if(alertCheck.equals("�����ǰ���� �Է��ϼ���.")) {
			System.out.println(" *** advProductName input check Sueecss !! *** ");
			$(".btn-sm", 3).click();
		} else {
			Thread.sleep(2000);
			System.out.println(" *** advProductName input check Fail ... *** ");
			Thread.sleep(2000);
			close();
		}
		Thread.sleep(2000);
		$(".gui-input").setValue("@");
		$("#btnRegister").click();
		alertCheck("��ȿ��", "�����ǰ��", 5);
		$(".gui-input").setValue(date);
		$("#btnRegister").click();
		Thread.sleep(1000);
		$("p", 6).waitUntil(visible, 2000);
		alertCheck = $("p", 6).text();
		if(alertCheck.equals("���� �Ӽ��� �����ϼ���.")) {
			System.out.println(" *** advAttribute select check Sueecss !! *** ");
			$(".btn-sm", 5).click();
		} else {
			System.out.println(" *** advAttribute select check Fail ... *** ");
			close();
		}
	    $(By.name("campaignMaterialCd[]")).click();
	    $(By.xpath("//option[@value='90']")).click();
		$("#btnRegister").click();
		Thread.sleep(1000);
		$("p", 7).waitUntil(visible, 2000);
		alertCheck = $("p", 7).text();
		if(alertCheck.equals("����� �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** advAttribute register Sueecss !! *** ");
			$(".btn-sm", 6).click();
		} else {
			System.out.println(" *** advAttribute register Fail ... *** ");
			close();
		}
		System.out.println(" ----- advertisingProductManage_add End ----- ");
	}
	@Test(priority = 4)
	public void advertisingProductManage_del() throws InterruptedException {
		System.out.println(" ----- advertisingProductManage_del Start ----- ");
		Thread.sleep(2500);
		String pageLoadCheck = $("#deleteViewBtn").text();
		if(pageLoadCheck.equals("����")) {
			System.out.println(" *** advProductManage page load Success !! *** ");
		} else {
			System.out.println(" *** advProductManage page load Fail ... *** ");
			close();
		}
		$("#deleteViewBtn").click();
		$(".hiddenCheck", 0).waitUntil(visible, 3000);
		$("#deleteBtn").click();
		$("p", 3).waitUntil(visible, 3000);
		pageLoadCheck = $("p", 3).text();
		if(pageLoadCheck.equals("������ �����ǰ���� ������ �����ϼ���.")) {
			System.out.println(" *** del checkBox check Success !! *** ");
			$(".btn-sm", 3).click();
		} else {
			System.out.println(" *** del checkBox check Fail ... *** ");
			close();
		}
		$("#checkAllCamp").click();
		$("#deleteBtn").click();
		$("p", 4).waitUntil(visible, 3000);
		pageLoadCheck = $("p", 4).text();
		if(pageLoadCheck.equals("������ �����ǰ ���� ������ �����Ͻðڽ��ϱ�?\n" + 
				"�����ǰ���� ������ ���� ����/�м��� �����Ǹ�, ���� �� ������ �Ұ����մϴ�.")) {
			System.out.println(" *** del confirm message Success !! *** ");
			$("#btn-modal-alert-yes").click();
		} else {
			System.out.println(" *** del confirm message Fail ... *** ");
			close();
		}
		$("p", 5).waitUntil(visible, 3000);
		pageLoadCheck = $("p", 5).text();
		if(pageLoadCheck.equals("�����ǰ���� ���� ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println(" *** produce manage delete Success !! *** ");
			$(".btn-sm", 6).click();
		} else {
			System.out.println(" *** produce manage delete Fail ... *** ");
			close();
		}
		Thread.sleep(1000);
		$("#deleteViewBtn").waitUntil(visible, 3000);
		System.out.println(" ----- advertisingProductManage_del End ----- ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}