package aceCounterPlus; //��輳�� - ���������Լ���

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

public class marketingInflowSetting {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
    String date = number_format.format(number_date);
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
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
	      case "mktInflowSetting_add_cmpName_null": checkMsg = "ķ���θ��� �Է����ּ���.";
	      break;
	      case "mktInflowSetting_add_cmpName_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
	      break;
	      case "mktInflowSetting_add_subjectMatter_null": checkMsg = "���縦 �Է����ּ���.";
	      break;
	      case "mktInflowSetting_add_subjectMatter_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
	      break;
	      case "mktInflowSetting_add_linkURL_null": checkMsg = "���� URL�� �Է��ϼ���.";
	      break;
	      case "mktInflowSetting_add_linkURL_validation": checkMsg = "�ùٸ� URL�� �Է��ϼ���.";
	      break;
	      case "mktInflowSetting_register": checkMsg = "��Ͽ� �����߽��ϴ�.";
	      break;
	      case "mktInflowSetting_del_null": checkMsg = "������ ������ ���� ������ �����ϼ���.";
	      break;
	      case "mktInflowSetting_del_confirm": checkMsg = "������ ������ ���� ������ �����Ͻðڽ��ϱ�?\n" + "���� �� ������ �Ұ����մϴ�.\n" + "(������ ķ���θ�� ������ ķ���θ��� �߰� �Ұ��մϴ�.)";
	      break;
	      case "mktInflowSetting_del_alert": checkMsg = "������ ���� ���� ������ �Ϸ�Ǿ����ϴ�.";
	      break;
	      case "inflowMediaNm_add_null": checkMsg = "�����ǰ���� �Է��ϼ���.";
	      break;
	      case "inflowMediaNm_add_validation": checkMsg = "�ѱ�, ����(�ҹ���), ����, Ư������(!%()+-_=:./~#), ����� �Է��ϼ���.";
	      break;
	      case "campaignMaterial_null": checkMsg = "���� �Ӽ��� �����ϼ���.";
	      break;
	      case "advertisingProductManage_add_duplication": checkMsg = "�̹� ����� �����ǰ���Դϴ�.\n" + "�ٸ� �����ǰ���� ������ּ���.";
	      break;
	      case "advertisingProductManage_add_register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
	      break;
	      case "advertisingProductManage_del_null": checkMsg = "������ �����ǰ���� ������ �����ϼ���.";
	      break;
	      case "advertisingProductManage_del_confirm": checkMsg = "������ �����ǰ ���� ������ �����Ͻðڽ��ϱ�?\n" + "�����ǰ���� ������ ���� ����/�м��� �����Ǹ�, ���� �� ������ �Ұ����մϴ�.";
	      break;
	      case "advertisingProductManage_del_alert": checkMsg = "�����ǰ���� ���� ������ �Ϸ�Ǿ����ϴ�.";
	      break;
	    }
	    $(".modal-backdrop").waitUntil(visible, 10000);	
	    sleep(800);
	    String msgCheck = $$("p").last().text().trim();
	    //Thread.onSpinWait();
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
	
  	//�Էµ� URL ���� ���� Ȯ��
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400){
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() ***");
            	close();
            } else {
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " - check Success !! ***");
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
	public void mktInflowSetting_add() {
		System.out.println(" ! ----- mktInflowSetting_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$("#inflowAddBtn").waitUntil(visible, 10000);
		pageLoadCheck = $("#inflowAddBtn").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** mktInflowSetting_add list page Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add list page Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#inflowAddBtn").click();
		$("#btnReg").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�߰��ϱ�")) {
			System.out.println(" *** mktInflowSetting_add add page Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add add page Fail ... !@#$%^&*() *** ");
			close();
		}
		$("label", 3).click();
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_cmpName_null");
		$("#campaign_nm").setValue(domain + date + "@");
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_cmpName_validation");
		$("#campaign_nm").setValue(domain + date);
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_subjectMatter_null");
		$("#campaign_material_value0").setValue(domain + date + "@");
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_subjectMatter_validation");
		$("#campaign_material_value0").setValue(domain + date);
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_linkURL_null");
		$("#original_url0").setValue(domain + date);
		$("#btnReg").click();
		valCheck("mktInflowSetting_add_linkURL_validation");
		$("#original_url0").setValue(domain + date + ".com");
		$("#btnReg").click();
		valCheck("mktInflowSetting_register");
		$("#inflowMrkCodeDown").waitUntil(visible, 10000);
		pageLoadCheck = $("#inflowMrkCodeDown").text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			System.out.println(" *** mktInflowSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- mktInflowSetting_add End ----- ! ");
	}
	@Test(priority = 2)
	public void mktInflowSetting_searchAndDel() {
		System.out.println(" ! ----- mktInflowSetting_searchAndDel Start ----- ! ");
		$(".br-l-n").setValue("99999");
		$("#searchBtn").click();
		$("td", 1).waitUntil(hidden, 10000);
		$("td", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[3].equals("�ʽ��ϴ�.")) {
			System.out.println(" *** mktInflowSetting_del no search result Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del no search result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#searchBtn").click();
		$("td", 0).waitUntil(hidden, 10000);
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		pLC = pageLoadCheck.split("apzz");
		if(pLC[1].equals(date)) {
			System.out.println(" *** mktInflowSetting_del search result Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del search result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#searchBtn").click();
		$("td", 0).waitUntil(hidden, 10000);
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		pLC = pageLoadCheck.split("apzz");
		if(pLC[1].equals(date)) {
			System.out.println(" *** mktInflowSetting_del default list result Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del default list result Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#deleteViewBtn").click();
		$("#deleteBtn").waitUntil(visible, 10000);
		$("#deleteBtn").click();
		valCheck("mktInflowSetting_del_null");
		$(".clsDelbox", 0).click();
		$("#deleteBtn").click();
		valCheck("mktInflowSetting_del_confirm");
		valCheck("mktInflowSetting_del_alert");
		sleep(800);
		pageLoadCheck = $("td", 0).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[3].equals("�ʽ��ϴ�.")) {
			System.out.println(" *** mktInflowSetting_del del Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del del Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- mktInflowSetting_searchAndDel End ----- ! ");
	}
	@Test(priority = 11)
	public void advertisingCodeDownload() {
		System.out.println(" ! ----- advertisingCodeDownload Start ----- ! ");
		$("#inflowMrkCodeDown").waitUntil(visible, 10000);
		$("#inflowMrkCodeDown").click();
		sleep(1000);
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("�����ڵ� �ٿ�ε�")) {
			brokenLinkCheck("advCodeDown", "https://new.acecounter.com/setting/appmarketing/codedown?inflow_media_cd=&inflow_mrkt_channel_dcd=10&down_term_cate=ALL&create_dt_st=&create_dt_ed=&original_url_yn=n&use_yn=y");
			System.out.println(" *** advCodeDownload layer check Sueecss !! *** ");
			$(".close", 0).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** advCodeDownload layer check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingCodeDownload End ----- ! ");
	}
	@Test(priority = 12)
	public void advertisingProductManage_add() {
		System.out.println(" ! ----- advertisingProductManage_add Start ----- ! ");
		sleep(1000);
		$(".btn-dark", 0).click();
		$("#addViewBtn").waitUntil(visible, 10000);
		pageLoadCheck = $("#addViewBtn").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** advProductManage_add list page load Success !! *** ");
		} else {
			System.out.println(" *** advProductManage_add list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#addViewBtn").click();
		$(".gui-input").waitUntil(visible, 10000);
		$("#btnRegister").click();
		valCheck("inflowMediaNm_add_null");
		$(".gui-input").setValue("@");
		$("#btnRegister").click();
		valCheck("inflowMediaNm_add_validation");
		$(".gui-input").setValue("1234test");
		$("#btnRegister").click();
		valCheck("campaignMaterial_null");
		$(By.name("campaignMaterialCd[]")).click();
	    $(By.xpath("//option[@value='90']")).click();
		$("#btnRegister").click();
		valCheck("advertisingProductManage_add_duplication");
		$(".gui-input").setValue(date);
		$("#btnRegister").click();		
		valCheck("advertisingProductManage_add_register");
		$("#addViewBtn").waitUntil(visible, 10000);
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** advertisingProductManage_add register Success !! *** ");
		} else {
			System.out.println(" *** advertisingProductManage_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingProductManage_add End ----- ! ");
	}
	@Test(priority = 13)
	public void advertisingProductManage_del() {
		System.out.println(" ! ----- advertisingProductManage_del Start ----- ! ");
		$("#deleteViewBtn").click();
		$("#deleteBtn").waitUntil(visible, 10000);
		$("#deleteBtn").click();
		valCheck("advertisingProductManage_del_null");
		$("#checkAllCamp").click();
		$("#deleteBtn").click();
		valCheck("advertisingProductManage_del_confirm");
		valCheck("advertisingProductManage_del_alert");
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("��ϵ� ��������� ������ �����ϴ�.")) {
			System.out.println(" *** advertisingProductManage_del del Success !! *** ");
		} else {
			System.out.println(" *** advertisingProductManage_del del Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingProductManage_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}