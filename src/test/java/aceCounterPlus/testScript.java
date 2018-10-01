package aceCounterPlus; //������ƮQA�Ҷ� ������ �׽�Ʈ�� �ڵ�ȭ�ϱ����� ����ϴ� ����

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.testng.ScreenShooter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class testScript {
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, hubPort, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;
	private WebDriver driver;
	
	@SuppressWarnings("unused")
	private int invalidLinksCount;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://new.acecounter.com/admin/login";
		hubUrl = "http://10.77.129.79:"; //��Ʈ��ȣ ������ �ٲ㼭 ���������� ����� ��Ʈ��ȣ �����ֱ�
		domain = "apzz";
		pw = "qordlf!@34";
		
		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = false;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			hubPort = "5556";
			/*cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);*/
			//cap = DesiredCapabilities.chrome();
			System.setProperty("webdriver.chrome.driver", "E:/000. Selenium/driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD + hubPort + "/wd/hub") , options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			//driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			hubPort = "5555";
			//cap = DesiredCapabilities.firefox();
			//RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			//cap = DesiredCapabilities.firefox();
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD + hubPort + "/wd/hub") , options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			hubPort = "5558";
			EdgeOptions options = new EdgeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD + hubPort + "/wd/hub") , options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			hubPort = "5557";
			cap = DesiredCapabilities.internetExplorer();
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			options.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			driver = new RemoteWebDriver(new URL(urlToRemoteWD + hubPort + "/wd/hub"), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} /*else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}*/
	}
	private static void js(String javaScriptSource) {
		Object obj = executeJavaScript(javaScriptSource);
	}
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
  	public static void sleep(long millis) {
  		try {
  			Thread.sleep(millis);
  		} catch (InterruptedException ex) {
  		}
  	}
  	//@Test(priority = 0)
	public void login() throws InterruptedException {
		open(baseUrl);
		$(".gui-input", 0).setValue("apzz0928");
		$(".gui-input", 1).setValue("qordlf!@34");
		$(".btn-primary").click();
		Thread.sleep(5000);
	}
	//@Test(priority = 1)
	public void memoTest() throws Exception {
		open("https://new.acecounter.com/stats/getInflowSummary");
		$("#uid").setValue("apzz09288");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		open("https://new.acecounter.com/stats/getInflowSummary");
	    $(By.linkText("Web Trial")).click();
	    $(By.cssSelector("li.list-group-item > a > span.ace-svc-name.text-dark")).click();
		$(By.id("memoHead")).click();
		int x = 31;
	    for(int i=0;i<=105;i++) {
		    $(By.id("memoDatepicker")).click();
		    Thread.sleep(1000);
		    $(By.cssSelector("td.available.active.start-date.end-date")).click();
		    Thread.sleep(1000);
		    $(By.id("memo")).click();
		    $(By.id("memo")).clear();
		    $(By.id("memo")).sendKeys(i + "");
		    Thread.sleep(1000);
		    $(By.id("chartmemo")).click();
		    Thread.sleep(1000);
		    $(By.id("btn-submit")).click();
		    Thread.sleep(2000);
		    $(".btn-default", x).click();	   
		    x++;
	    }
	}
	//@Test(priority = 2)
	public void appMarketing() throws Exception {
		open("https://new.acecounter.com/stats/getInflowSummary");
		$("#uid").setValue("apzz0928888");
		Thread.sleep(1000);
		$("#upw").setValue("qordlf!@34");
		Thread.sleep(1000);
		$(".btn_login").click();
		Thread.sleep(1000);
		open("https://new.acecounter.com/setting/appmarketing/");
		Thread.sleep(1000);
		$(By.cssSelector("span.ace-svc-name.text-dark")).click();
	    $(By.cssSelector("li.list-group-item > a > span.ace-svc-name.text-dark")).click();
		int[] data1 = {270, 510, 520, 530, 540, 550, 560, 570, 580, 590, 600, 610, 620, 630, 640, 650, 660, 670, 680};
		String[] data2 = {"����Ad@m", "�θ��", "ũ���׿�", "ĳ�ý����̵�", "EDNPlus", "N2S", "����", "�����", "�ֵ�����", "�ֵ�Ǯ", "�ֵ���", "adCRM", "appcoach", "OKĳ�����", "�ؽ��÷��̶�", "��Ƽ����", "�´���DMC",
				"�ֵ��÷���", "������Ŭ��"};
		String[] data3 = {"����adam", "�θ��", "ũ���׿�", "ĳ�ý����̵�", "ednplus", "n2s", "����", "�����", "�ֵ�����", "�ֵ�Ǯ", "�ֵ���", "adcrm", "appcoach", "okĳ�����", "�ؽ��÷��̶�", "��Ƽ����", "�´���dmc",
				"�ֵ��÷���", "������Ŭ��"};
	    for(int i=0;i<=13;i++) {
	        $(By.id("inflowAddBtn")).click();
	        Thread.sleep(1000);
	        js("$('#channelDcd2').click();");
	        //$(By.id("inflow_media_cd")).click();
	        Thread.sleep(1000);
			/*$(By.id("inflow_media_cd")).selectOption(data2[i]);
	        //new Select(driver.findElement(By.id("inflow_media_cd"))).selectByVisibleText(data2[i]);
	        Thread.sleep(1000);
	        $(By.cssSelector("option[value=\"" + data1[i] + "\"]")).click();
	        Thread.sleep(1000);*/
	        $(By.id("campaign_nm")).click();
	        Thread.sleep(1000);
	        $(By.id("campaign_nm")).sendKeys("�Ϲ�-" + data3[i] + "-�����ǰ-url");
	        Thread.sleep(1000);
	        $(By.id("campaign_material_value0")).click();
	        $(By.id("campaign_material_value0")).sendKeys("�Ϲ�-" + data3[i] + "-�����ǰ-url");
	        Thread.sleep(1000);
	        $(By.id("app_market_cd0")).click();
	        Thread.sleep(1000);
			//$(By.id("inflow_media_cd")).selectOption("�����÷��̽����");
	        //new Select(driver.findElement(By.id("app_market_cd0"))).selectByVisibleText("�����÷��̽����");
	        Thread.sleep(1000);
	        $(By.cssSelector("#app_market_cd0 > option[value=\"make\"]")).click();
	        //$(By.cssSelector("option[value=\"make\"]")).click();
	        Thread.sleep(1000);
	        $("#original_url0").setValue(i + "testtest.com");
	        $(By.id("btnReg")).click();
	        Thread.sleep(1000);
	        $(By.cssSelector("button.btn.btn-default.btn-sm")).click();
	        Thread.sleep(1000);
	    }
	}
	//@Test(priority = 3)
	public void appPage() throws Exception {
		open("http://naver.com");
		String[] appData = {"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=daum_ad&nac_cpi=1&nac_m=1&nac_c=1&nac_sm=%EC%9C%A0%EB%A3%8C-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-1&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=inmobi&nac_cpi=2&nac_m=1&nac_c=2&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%9D%B8%EB%AA%A8%EB%B9%84-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=criteo&nac_cpi=3&nac_m=1&nac_c=3&nac_sm=%EC%9C%A0%EB%A3%8C-%ED%81%AC%EB%A6%AC%ED%85%8C%EC%98%A4-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=cashslide&nac_cpi=4&nac_m=1&nac_c=4&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%BA%90%EC%8B%9C%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=ednplus&nac_cpi=5&nac_m=1&nac_c=5&nac_sm=%EC%9C%A0%EB%A3%8C-ednplus-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=cashslide&nac_cpi=6&nac_m=1&nac_c=6&nac_sm=asf124&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=n2s&nac_cpi=7&nac_m=1&nac_c=7&nac_sm=%EC%9C%A0%EB%A3%8C-n2s-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=mobon&nac_cpi=8&nac_m=1&nac_c=8&nac_sm=%EC%9C%A0%EB%A3%8C-%EB%AA%A8%EB%B9%84%EC%98%A8-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=mobipop&nac_cpi=9&nac_m=1&nac_c=9&nac_sm=%EC%9C%A0%EB%A3%8C-%EB%AA%A8%EB%B9%84%ED%8C%9D-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=adpop&nac_cpi=10&nac_m=1&nac_c=10&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%95%A0%EB%93%9C%ED%8C%9D%EC%BD%98-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=adpool&nac_cpi=11&nac_m=1&nac_c=11&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%95%A0%EB%93%9C%ED%92%80-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=adpick&nac_cpi=12&nac_m=1&nac_c=12&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%95%A0%EB%93%9C%ED%94%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=adcrm&nac_cpi=13&nac_m=1&nac_c=13&nac_sm=%EC%9C%A0%EB%A3%8C-adcrm-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=appcoach&nac_cpi=14&nac_m=1&nac_c=14&nac_sm=%EC%9C%A0%EB%A3%8C-appcoach-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=oklock&nac_cpi=15&nac_m=1&nac_c=15&nac_sm=%EC%9C%A0%EB%A3%8C-ok%EC%BA%90%EC%89%AC%EB%B0%B1%EB%9D%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=playlock&nac_cpi=16&nac_m=1&nac_c=16&nac_sm=%EC%9C%A0%EB%A3%8C-%EB%84%A5%EC%8A%A8%ED%94%8C%EB%A0%88%EC%9D%B4%EB%9D%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=notitome&nac_cpi=17&nac_m=1&nac_c=17&nac_sm=%EC%9C%A0%EB%A3%8C-%EB%85%B8%ED%8B%B0%ED%88%AC%EB%AF%B8-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=onnuridmc&nac_cpi=18&nac_m=1&nac_c=18&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%98%A8%EB%88%84%EB%A6%ACdmc-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=adplus&nac_cpi=19&nac_m=1&nac_c=19&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%95%A0%EB%93%9C%ED%94%8C%EB%9F%AC%EC%8A%A4-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=coocha_hc&nac_cpi=20&nac_m=1&nac_c=20&nac_sm=%EC%9C%A0%EB%A3%8C-%EC%BF%A0%EC%B0%A8%ED%95%AB%ED%81%B4%EB%A6%AD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=21&nac_m=1&nac_c=21&nac_sm=%EC%9D%BC%EB%B0%98-%EB%8B%A4%EC%9D%8Cadam-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=22&nac_m=1&nac_c=22&nac_sm=%EC%9D%BC%EB%B0%98-%EC%9D%B8%EB%AA%A8%EB%B9%84-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=23&nac_m=1&nac_c=23&nac_sm=%EC%9D%BC%EB%B0%98-%ED%81%AC%EB%A6%AC%ED%85%8C%EC%98%A4-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=24&nac_m=1&nac_c=24&nac_sm=%EC%9D%BC%EB%B0%98-%EC%BA%90%EC%8B%9C%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=25&nac_m=1&nac_c=25&nac_sm=%EC%9D%BC%EB%B0%98-ednplus-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=26&nac_m=1&nac_c=26&nac_sm=%EC%9D%BC%EB%B0%98-n2s-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=27&nac_m=1&nac_c=27&nac_sm=%EC%9D%BC%EB%B0%98-%EB%AA%A8%EB%B9%84%EC%98%A8-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=28&nac_m=1&nac_c=28&nac_sm=%EC%9D%BC%EB%B0%98-%EB%AA%A8%EB%B9%84%ED%8C%9D-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=29&nac_m=1&nac_c=29&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%8C%9D%EC%BD%98-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=30&nac_m=1&nac_c=30&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%92%80-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=31&nac_m=1&nac_c=31&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%94%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=32&nac_m=1&nac_c=32&nac_sm=%EC%9D%BC%EB%B0%98-adcrm-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=33&nac_m=1&nac_c=33&nac_sm=%EC%9D%BC%EB%B0%98-appcoach-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=34&nac_m=1&nac_c=34&nac_sm=%EC%9D%BC%EB%B0%98-ok%EC%BA%90%EC%89%AC%EB%B0%B1%EB%9D%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-playstore&nac_s=20	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=35&nac_m=1&nac_c=35&nac_sm=%EC%9D%BC%EB%B0%98-%EB%8B%A4%EC%9D%8Cadam-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=100	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=36&nac_m=1&nac_c=36&nac_sm=%EC%9D%BC%EB%B0%98-%EC%9D%B8%EB%AA%A8%EB%B9%84-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=101	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=37&nac_m=1&nac_c=37&nac_sm=%EC%9D%BC%EB%B0%98-%ED%81%AC%EB%A6%AC%ED%85%8C%EC%98%A4-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=102	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=38&nac_m=1&nac_c=38&nac_sm=%EC%9D%BC%EB%B0%98-%EC%BA%90%EC%8B%9C%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=103	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=39&nac_m=1&nac_c=39&nac_sm=%EC%9D%BC%EB%B0%98-ednplus-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=104	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=40&nac_m=1&nac_c=40&nac_sm=%EC%9D%BC%EB%B0%98-n2s-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=105	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=41&nac_m=1&nac_c=41&nac_sm=%EC%9D%BC%EB%B0%98-%EB%AA%A8%EB%B9%84%EC%98%A8-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=106	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=42&nac_m=1&nac_c=42&nac_sm=%EC%9D%BC%EB%B0%98-%EB%AA%A8%EB%B9%84%ED%8C%9D-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=107	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=43&nac_m=1&nac_c=43&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%8C%9D%EC%BD%98-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=108	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=44&nac_m=1&nac_c=44&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%92%80-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=109	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=45&nac_m=1&nac_c=45&nac_sm=%EC%9D%BC%EB%B0%98-%EC%95%A0%EB%93%9C%ED%94%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=110	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=46&nac_m=1&nac_c=46&nac_sm=%EC%9D%BC%EB%B0%98-adcrm-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=111	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=47&nac_m=1&nac_c=47&nac_sm=%EC%9D%BC%EB%B0%98-appcoach-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=112	"
				,"https://mbr.acecounter.com:10000/v1/bridge?sid=111283&nac_md=normal_mkt&nac_cpi=48&nac_m=1&nac_c=48&nac_sm=%EC%9D%BC%EB%B0%98-ok%EC%BA%90%EC%89%AC%EB%B0%B1%EB%9D%BD-%EA%B4%91%EA%B3%A0%EC%83%81%ED%92%88-url&nac_s=113	"
		};
		for(int i=0;i<=appData.length-1;i++) {
			if(i<appData.length-25) {
				open("http://naver.com");
			} else if (i<appData.length-15) {
				open("http://google.com");
			} else {
				open("http://daum.net");
			}
			open(appData[i]);
			Thread.sleep(2000);
			System.out.println(i + " ���� �߽��ϴ�");
			clearBrowserCache();
			Thread.sleep(2000);
			close();
			Thread.sleep(2000);
		}
		Thread.sleep(10000);
	}
	//@Test(priority = 4)
	public void user() throws Exception {
		open("https://new.acecounter.com/common/front");
		$("#uid").setValue("apzz09288");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
	    Thread.sleep(1000);
		open("https://new.acecounter.com/setting/inflowmedia/config/userinflow?searchUseYn=y");
	    Thread.sleep(1000);
		$(By.linkText("Web Trial")).click();
	    $(By.cssSelector("li.list-group-item > a > span.ace-svc-name.text-dark")).click();
	    Thread.sleep(1000);
	    for(int i=0;i<=105;i++) {
		    $(By.id("addViewBtn")).click();
		    Thread.sleep(1000);
		    $(By.name("inflowMediaNm[]")).click();
		    Thread.sleep(1000);
		    $(By.name("inflowMediaNm[]")).sendKeys("test" + i);
		    Thread.sleep(1000);
		    $(By.name("campaignMaterialCd[]")).click();
		    Thread.sleep(1000);
		    $(By.cssSelector("option[value=\"10\"]")).click();
		    Thread.sleep(1000);
		    $(By.id("btnRegister")).click();
		    Thread.sleep(1000);
		    $(".btn-default", 6).click();
		    Thread.sleep(1000);
	    }
	}
	//@Test(priority = 4)
	public void appTestPlus() throws Exception {
		open("https://new.acecounter.com/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
	    Thread.sleep(1000);
		open("https://new.acecounter.com/setting/inflowmedia/config/userinflow?searchUseYn=y");
	    $(By.linkText("Web Trialapzz0928888.org")).click();
	    $(By.xpath("//div[@id='menu-top-service-items']/li[3]/a")).click();
	    Thread.sleep(1000);
	    int a = 3;
	    for(int i=0;i<=300;i++) {
	    	$("#addViewBtn").click();
	    	Thread.sleep(1000);
	    	$(".gui-input", 2).setValue("testdata:::" + i);
	        $(By.name("campaignMaterialCd[]")).click();
	        new Select($(By.name("campaignMaterialCd[]"))).selectByVisibleText("�˻�");
	        $(By.xpath("//option[@value='10']")).click();
	        Thread.sleep(1000);
	        $("#btnRegister").click();
	        Thread.sleep(1000);
	        $(".btn-sm", a).click();
	        //a++;
	        Thread.sleep(1000);
	    }
	}
	//@Test(priority = 4)
	public void appTestPlusMemo() throws Exception {
		open("https://new.acecounter.com/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
	    Thread.sleep(1000);
		open("https://new.acecounter.com/setting/inflowmedia/config/userinflow?searchUseYn=y");
	    $(By.linkText("Web Trialapzz0928888.org")).click();
	    $(By.xpath("//div[@id='menu-top-service-items']/li[0]/a")).click();
	    Thread.sleep(1000);
	    for(int i=0;i<=300;i++) {

	    }
	}
	//@Test(priority = 5)
	public void ��Ʈ��_�߰�() throws Exception {
		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();
		Thread.sleep(1000);
		open("http://10.160.231.21/advertiser/detail?agencyid=19");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("clientname")).setValue("��������Ʈ��_a" + i);
	    	$(By.name("email")).setValue(i + "@m.net");
	    	$(By.name("contact_2")).setValue("0000");
	    	$(By.name("contact_3")).setValue("1111");
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 6)
	public void ��������_�߰�() throws Exception {
		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();
		Thread.sleep(2000);
		open("http://10.160.231.21/agency/list");
		Thread.sleep(1000);
    	$(".btn-red").click();
    	Thread.sleep(1000);
	    for(int i=0;i<=101;i++) {
	    	$(".i_text", 0).setValue("Agency_a" + i);    	
	    	$(".i_text", 1).setValue("a" + i + "@m.net");
	    	$(By.name("contact_2")).setValue("0000");
	    	$(By.name("contact_3")).setValue("1111");
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 7)
	public void ��Ʈ��_�����_�߰�() throws Exception {
		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();
		Thread.sleep(1000);
		open("http://10.160.231.21/advertiser/user/detail?accountid=158");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("username")).setValue("Advertiser_a" + i);
	    	$(".btn-red", 0).click();
	    	$(By.name("contact_name")).setValue("�����һ����_a" + i);	    	
	    	$(By.name("email_address")).setValue(i + "@m.net");
	    	$(By.name("contact_2")).setValue("0000");
	    	$(By.name("contact_3")).setValue("1111");
	    	Thread.sleep(1000);
	    	$(".btn-red", 1).click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 8)
	public void ��������_�����_�߰�() throws Exception {
/*		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();*/
		Thread.sleep(1000);
		open("http://10.160.231.21/agency/user/detail?accountid=72");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("username")).setValue("delAgency_a" + i);
	    	$(".btn-red", 0).click();
	    	$(By.name("contact_name")).setValue("�����һ����_a" + i);	    	
	    	$(By.name("email_address")).setValue(i + "@m.net");
	    	$(By.name("contact_2")).setValue("0000");
	    	$(By.name("contact_3")).setValue("1111");
	    	Thread.sleep(1000);
	    	$(".btn-red", 1).click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 9)
	public void ķ����_�߰�() throws Exception {
/*		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();*/
		Thread.sleep(1000);
		open("http://10.160.231.21/campaign/detail?agencyid=19&clientid=29");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("campaignname")).setValue("delCampaign_a" + i);
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 10)
	public void ��ü_����Ʈ_�߰�() throws Exception {
		/*open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();*/
		Thread.sleep(1000);
		open("http://10.160.231.21/site/detail?agencyid=19");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("name1")).setValue("delSite_a" + i);
	    	$(By.name("website1")).setValue("http://URL_a" + i + ".com");
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 11)
	public void ��ü_����_�߰�() throws Exception {
		/*open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();*/
		Thread.sleep(1000);
		open("http://10.160.231.21/zone/detail?agencyid=19&affiliateid=41");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("zonename1")).setValue("delZone_a" + i);
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    	Thread.sleep(1000);
	    	$(".btn-red").click();
	    }
	}
	//@Test(priority = 0)
	public void ����ǽ�_����Ʈ����Ȯ��() throws Exception {
		int x=1;
		int y=1;
		int z=0;
		int w=3;
		String reportCheck = "";
		String siteName = "";
		String siteEmail = "";
		String[] siteInfo = new String[400];

		open("https://new.acecounter.com/admin/login");
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		$(".btn-dark").waitUntil(visible, 10000);
	    $(By.id("__BVID__1_")).click();
	    sleep(1500);
	    $(By.xpath("//option[@value='ACE+']")).click();
	    sleep(1500);
	    $(By.id("__BVID__4_")).click();
	    sleep(1500);
	    $(By.xpath("//option[@value='p1']")).click();
	    sleep(1500);
	    $(".btn-dark").click();
		sleep(2000);
		for(int i=49;i<=229;i=i+9) {
			siteName = $(".text-left", y).text();
			y = y+2;
			$("a", i).click();
			switchTo().window(x);
			x++;
			open("https://new.acecounter.com/setting/reportDown/download");
			$("h4").waitUntil(visible, 20000);
			reportCheck = $(".muted").text();
			if(reportCheck.equals("����Ʈ ���� �̷��� �����ϴ�.\n" + "�߰� �ǿ��� ����Ʈ�� �����ϼ���.")) {
				System.out.println(siteName + "�� ������ ����Ʈ�� �����ϴ�.");
				System.out.println("----------------���뼱-----------------");
			} else {
				siteInfo[z] = siteName;
				z++;
				siteInfo[z] = siteEmail;
				z++;
				System.out.println(siteName + "�� ������ ����Ʈ�� �ֽ��ϴ�!!!!!!!!!!!!!!!!!!!!!!!!! �˻��� : " + siteEmail);
				System.out.println("------------------------���뼱-------------------------");
			}
			driver.close();
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
			switchTo().window(0);
			if(y==41 || y==61 || y==81 || y==101) {
				$(".page-link", 0).scrollTo();
				$(".page-link", w).click();
				w++;
				sleep(2000);
			}
		}
		System.out.println("Ȯ�οϷ�");
		System.out.println(siteInfo);
	}
	//@Test(priority = 0)
	public void go_signup() {
		open("http://10.77.129.52:8083/common/front");
		$(".btn_login").waitUntil(visible, 100000);
		for(int x=4;x<=100; x++) {
			$(".go_signup").waitUntil(visible, 8000);
			String signUp = $(".go_signup").text();
			if(signUp.contentEquals("ȸ������")) {
				System.out.println(" SignUp Button check Success!! ");
				$(".go_signup").click();		
			} else {
				System.out.println(" SignUp Button check Fail !! ");
			}
			$("input", 1).click();
			js("$('#using').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
			js("$('#info').click();");
			$("#stepOneCompleted").scrollIntoView(true);
			$("#userid").setValue("apzz" + x);
			$("#recheck").click();
			$("#userpw").setValue(pw);
			$("#repeatpw").setValue(pw);
			$("#usernm").setValue("ȸ�������׽�Ʈ");
			$("#useremail").setValue("apzz0928@nate.com");
			js("$('#news').click();"); // üũ�ڽ� üũ�� �ȸԾ js��
			$("#mp2").setValue("0000");
			$("#mp3").setValue("0000");
			$("#stepOneCompleted").click();
			String stepOne = $(".request").text(); 
			if(stepOne.equals("�� ���� ������ �����Ű���? �� �´� ���񽺸� ��õ�� �帳�ϴ�.��õ �ޱ�")) {
				System.out.println(" *** SignUp Step.1 Success !! *** ");			
			} else {
				System.out.println(" *** SignUp Step.1 Fail !! *** ");
				close();
			}
			$(By.name("input-domain")).setValue(domain + x + ".com");
			$(".ace-btn-add-domain").waitUntil(visible, 1000);
			$(By.name("largeCategoryCd")).selectOptionByValue("22");
			$(By.name("middleCategoryCd")).selectOptionByValue("188");
			$(".ace-btn-add-domain").click(); // ������ �̵� �ӵ������� ������ üũ�� ���߿�
			$("#stepTwoCompleted").click();
			String stepTwo = $("h3").text();
			if(stepTwo.equals("ȸ������(����ü��) ��û�� �Ϸ�Ǿ����ϴ�.")) {
				System.out.println(" *** SignUp Step.2 Success !! *** ");
				System.out.println("ID is : " + "apzz" + x);
			} else {
				System.out.println(" *** SignUp Step.2 Fail !! *** ");
				close();
			}
			$(".btn_join").click();
			$(".dropdown-toggle", 3).click();
			$(".btn-logout").click();
			/*System.out.println(" *** SignUp *** Success !! *** ");
			$("#uid").setValue("apzz" + i);
			$("#upw").setValue(pw);
			sleep(1000);
			$(".btn_login").click();
			String loginCheck = $(".btn_logout").text();
			$(".btn_logout").getValue();
			if(loginCheck.equals("�α׾ƿ�")) {
				System.out.println(" *** Login Success !! *** ");
			} else {
				System.out.println(" *** Login Fail !! *** ");
				close();
			}*/
		}
	}
	//@Test(priority = 0)
	public void api_setting() {
		open("http://10.77.129.80:8081/common/front");
		$(".btn_login").waitUntil(visible, 100000);
		for(int i=68;i<=100; i++) {
			$("#uid").setValue("apzz" + i);
			$("#upw").setValue(pw);
			$(".btn_login").click();
			$(".btn_logout").waitUntil(visible, 10000);
			open("http://10.77.129.80:8081/setting/marketing-set1");
			$(".btn-info", 0).waitUntil(visible, 10000);
			$(".btn-info", 0).click();
			$(".btn-info", 1).waitUntil(visible, 10000);
			$("#apiId").setValue("1415862");
			$("#apiKey").setValue("0100000000e59db4dd8ed5d4ec04144437159badb7ff5f461ecadc595577bd3c72757c97a8");
			$("#pwdKey").setValue("AQAAAADlnbTdjtXU7AQURDcVm6237Rlu7v2zA/SqgFA3zMC7jA==");
			$("#agree").click();
			$(".btn-info", 1).click();
			sleep(1000);
			$(".btn-sm", 3).click();
			$(".btn-info", 1).waitUntil(hidden, 10000);
			for(int x=0;x<=2;x++) {
				sleep(1500);
				$(".br-dark", 0).click();
				sleep(1500);
				$(".btn-sm", 3).click();
				sleep(1500);
				System.out.println("x is : " + x);
			}
			open("http://10.77.129.80:8081/common/front");
			$(".btn_logout").click();
			$(".btn_login").waitUntil(visible, 10000);
		}
	}
	//@Test(priority = 0)
	public void api_add() {
		open("http://10.77.129.80:8081/common/front");
		$(".btn_login").waitUntil(visible, 100000);
		$("#username").setValue("apzz0928");
		$("#password").setValue(pw);
		$(".btn-primary").click();
		open("http://10.77.129.80:8081/admin/monitoring/apiManualCalling/form");
		for(int i=0;i<=4;i++) {
			$("label", i).click();	
		}
		$(".form-control", 2).waitUntil(visible, 10000);
	}
	//@Test(priority = 1)
	public void decoding_add() {
		for(int i=0;i<=101;i++) {
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%A3%BC%EB%AC%B8"); //���ڵ�-�ֹ�
			open("http://apzz0928.egloos.com/category/%25EA%25B0%2580");
			open("https://new.acecounter.com/common/front");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EA%B0%80%EC%9E%85"); //���ڵ�-����
			open("http://apzz0928.egloos.com/category/%25EB%2582%2598");
			open("https://new.acecounter.com/common/front");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%98%88%EC%95%BD"); //���ڵ�-����
			open("http://apzz0928.egloos.com/category/%25EB%258B%25A4");
			open("https://new.acecounter.com/common/front");
			open("http://apzz0928.egloos.com/category/%25EA%25B0%2580%25EB%2582%2598");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%98%88%EC%95%BD?nac_md=kakao_ta&nac_cpi=61&nac_sm=%EA%B2%80%EC%83%89%EC%96%B4-%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%86%A1-%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%98%88%EC%95%BD");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%A3%BC%EB%AC%B8?apzz0928.blogs nac_md=naver_br&nac_cpi=60&nac_kw=%EA%B2%80%EC%83%89%EC%96%B4-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%A3%BC%EB%AC%B8");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EA%B0%80%EC%9E%85?apzz0928.b nac_md=daum_br&nac_cpi=59&nac_kw=%EA%B2%80%EC%83%89%EC%96%B4-%EC%9D%B8%EC%BD%94%EB%94%A9-%EA%B0%80%EC%9E%85");
			System.out.println("���ڵ� ��ȯ, ����, ���� �������� �� " + (i+1) + "���� �湮�߽��ϴ�.");
		}
	}
	//@Test(priority = 2)
	public void decoding_add1() {
		for(int i=0;i<=100;i++) {
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%A3%BC%EB%AC%B8"); //���ڵ�-�ֹ�
			open("http://apzz0928.blogspot.com/");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EA%B0%80%EC%9E%85"); //���ڵ�-����
			open("http://apzz0928.blogspot.com/");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%EC%BD%94%EB%94%A9-%EC%98%88%EC%95%BD"); //���ڵ�-����
			open("http://apzz0928.blogspot.com/");
			open("http://apzz0928.egloos.com/category/%25EA%25B0%2580");
			open("http://apzz0928.egloos.com");
			open("http://apzz0928.egloos.com/category/%25EB%2582%2598");
			open("http://apzz0928.egloos.com");
			open("http://apzz0928.egloos.com/category/%25EB%258B%25A4");
			open("http://apzz0928.egloos.com");
			open("http://apzz0928.egloos.com/category/%25EA%25B0%2580%25EB%2582%2598");
			System.out.println("������ - ����� �̵���ο� ����/���� �������� �������� �������� �� " + (i+1) + "���� �湮�߽��ϴ�.");
		}
	}
	//@Test(priority = 0)
	public void URL����_��������üȮ�ο�() {
		for(int i=0;i<=100;i++) {
			open("http://apzz0928.blogspot.com/search/label/missing/missingA"); //���ڵ�-�ֹ�
			open("http://apzz0928.blogspot.com/search/label/missing/missingB"); //���ڵ�-����
			open("http://apzz0928.blogspot.com/search/label/missing/missingC"); //���ڵ�-����
			open("http://apzz0928.blogspot.com/2018/01/blog-post_14.html");
			open("http://apzz0928.blogspot.com/2018/01/3.html");
			open("http://apzz0928.blogspot.com/2018/01/2.html");
			open("http://apzz0928.blogspot.com/2018/01/1.html");
			open("http://apzz0928.blogspot.com/2018/01/blog-post_77.html");
			open("http://apzz0928.blogspot.com/2018/05/test.html");
			System.out.println("������ ��ü Ȯ�ο� �������� �� " + (i+1) + "���� �湮�߽��ϴ�.");
		}
	}
	//@Test(priority = 1)
	public void switchTowindow() {
		open("https://new.acecounter.com/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		open("https://new.acecounter.com/manage/mailing/summaryReport");
		$("#btn-sendMail").waitUntil(visible, 10000);
		$("#btn-sendMail").click();
		$(".btn-default", 5).waitUntil(visible, 10000);
		$(".btn-default", 5).click();
		System.out.println("1 ��° ���� �߼�");
		js("window.open('https://nhnent.dooray.com/mail/folders/2241107403578885832');");
		switchTo().window(1);
		$("#username").setValue("apzz0928");
		$("#password").setValue(pw);
		$(".btn_red").click();
		String time = $(".list-item-time", 0).text();
		String subject = $(".subject", 0).text();
		System.out.println("1 ��° �׽�Ʈ���� ������ ���� �߽Žð� " + time);
		System.out.println("1 ��° �׽�Ʈ���� ������ ���� ���� : " + subject);
		$(".subject", 0).click();
		$(".d-toolbar-white-btn", 2).click();
		$(".navi-item", 6).click();
		$(".subject", 0).click();
		System.out.println("1 ��° �������� ������ ���� �߽Žð� " + time);
		System.out.println("1 ��° �������� ������ ���� ���� : " + subject);
		$(".d-toolbar-white-btn", 2).click();
		$(".action-btn").click();
		$(".navi-name", 22).click();
		switchTo().window(0);
		for(int i=2;i<=102;i++) {
			$("#btn-sendMail").waitUntil(visible, 10000);
			$("#btn-sendMail").click();
			$(".btn-default", 5).waitUntil(visible, 10000);
			$(".btn-default", 5).click();
			System.out.println(i + " ��° ���� �߼�");
			switchTo().window(1);
			time = $(".list-item-time", 0).text();
			subject = $(".subject", 0).text();
			System.out.println(i + " ��° �׽�Ʈ���� ������ ���� �߽Žð� " + time);
			System.out.println(i + " ��° �׽�Ʈ���� ������ ���� ���� : " + subject);
			$(".subject", 0).click();
			$(".d-toolbar-white-btn", 2).click();
			$(".navi-item", 6).click();
			$(".subject", 0).click();
			time = $(".list-item-time", 0).text();
			subject = $(".subject", 0).text();
			System.out.println(i + " ��° �������� ������ ���� �߽Žð� " + time);
			System.out.println(i + " ��° �������� ������ ���� ���� : " + subject);
			$(".d-toolbar-white-btn", 2).click();
			$(".action-btn").click();
			$(".navi-name", 22).click();
			switchTo().window(0);
			sleep(3000);
		}
	}
	//@Test(priority = 1)
	public void convList_add() {
		for(int i=0;i<=300;i++) {
			open("http://apzz0928.blogspot.com/search/label/Category1");
			open("http://apzz0928.blogspot.com/search/label/Category2");
			open("http://apzz0928.blogspot.com/search/label/Category3");
			open("http://apzz0928.blogspot.com/search/label/Category4");
			open("http://apzz0928.blogspot.com/search/label/Category5");
			open("http://apzz0928.blogspot.com/search/label/Category6");
			open("http://apzz0928.blogspot.com/search?q=aaaaa");
			js("console.log(document.cookie)");
			driver.manage().deleteAllCookies();
			System.out.println("��Ű ĳ�� ����");
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%ED%95%98%EC%9A%B0%EC%8A%A4%EB%A7%88%EC%BC%80%ED%8C%85"); //���Ͽ콺������
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%ED%95%98%EC%9A%B0%EC%8A%A4-%EB%B0%94%EC%9D%B4%EB%9F%B4/"); //���Ͽ콺-���̷�/
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%ED%95%98%EC%9A%B0%EC%8A%A4-%EC%9D%B4%EB%A9%94%EC%9D%BC/"); //���Ͽ콺-�̸���/
			open("http://apzz0928.blogspot.com/search/label/%EC%9D%B8%ED%95%98%EC%9A%B0%EC%8A%A4-Talk/"); //���Ͽ콺-Talk
			open("http://apzz0928.blogspot.com/2018/01/2018-01-10_9.html");
			open("http://apzz0928.blogspot.com/search?q=bbbbb");
			open("http://apzz0928.blogspot.com/search?q=ccccc");
			js("console.log(document.cookie)");
			driver.manage().deleteAllCookies();
			js("console.log(document.cookie)");
			System.out.println("��Ű ĳ�� ����");
			System.out.println("��ȯ " + (i+1) + "���� �湮�߽��ϴ�.");
		}
	}
	//@Test(priority = 2)
	public void URLsetting() {
		open("https://new.acecounter.com/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		open("https://new.acecounter.com/setting/contents/url");
		for(int i=60;i<=160;i++) {
			$(".btn-info", 0).click();
			sleep(1000);
			$("#page-url").setValue("/common/front/learningCenter/trend/deta" + i + "?post_no=1");
			sleep(1000);
			$("#btn-add").click();
			sleep(1000);
			$(".btn-sm", 7).click();
			sleep(1000);
		}
	}
	//@Test(priority = 0)
	public void ncp_add() {
		open("http://10.160.231.210:8082/admin/login");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".pull-right").click();
		sleep(3000);
		open("http://10.160.231.210:8082/admin/comCooperation/ncpManagement");
		for(int i=1;i<=300;i++) {
			$(".w150").click();
			sleep(1000);
			$("#aName_1").setValue("�̸�" + i);
			if(i % 2 == 0) {
				$("#aPhone_1").setValue("010-1234-5678");				
			} else {
				$("#aPhone_1").setValue("010-9876-5432");
			}
			if(i % 2 == 0) {
				$("#aEmail_1").setValue(i + "abc@mail.com");
			} else {
				$("#aEmail_1").setValue(i + "cba@mail.com");
			}
			if(i % 2 == 0) {
				$("#aCompany_1").setValue("ȸ���" + i);
			} else {
				$("#aCompany_1").setValue("company" + i);
			}
			$(".btn-primary").click();
			sleep(1000);
			$("#btn-modal-alert-yes").click();
			sleep(1000);
			$(".btn-default", 1).click();
			sleep(2000);
		}
	}
	//@Test(priority = 0)
	public void URL����_����������_�߰���ũ��Ʈ() {
		open("http://10.77.129.80:8081/setting/contents/url");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(2000);
		open("http://10.77.129.80:8081/setting/contents/url");
		for(int i=1;i<=300;i++) {
			$(".btn-info", 0).click();
			sleep(1000);
			$(By.name("page_url")).setValue("http://test.com/test" + i + "?a=a&b=b");
			$(By.name("exclude_url_char")).setValue("" + i);
			$("#btn-add").click();
			sleep(1500);
			$(".btn-sm", 7).click();
			sleep(2500);
		}
	}
	//@Test(priority = 0)
	public void URL����_��������ü_�߰���ũ��Ʈ() {
		open("http://10.77.129.80:8081/setting/contents/url");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(2000);
		open("http://10.77.129.80:8081/setting/contents/pageReplacement");
		for(int i=1;i<=300;i++) {
			$(".btn-info", 0).click();
			sleep(1000);
			$(By.name("targetPage")).setValue("/test" + i + "/.*");
			$(By.name("replacementPage")).setValue("/test/" + i + "/");
			$("#btn-add").click();
			sleep(1500);
			$(".btn-sm", 3).click();
			sleep(2500);
		}
	}
	//@Test(priority = 0)
	public void URL����_���ΰ˻�_�߰���ũ��Ʈ() {
		open("http://10.77.129.80:8081/setting/contents/url");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(2000);
		open("http://10.77.129.80:8081/setting/contents/internalSearch");
		for(int i=1;i<=300;i++) {
			$(".btn-info", 0).click();
			sleep(1000);
			$(By.name("page_url")).setValue("/test" + i);
			$(By.name("internal_search_param")).setValue("" + i);
			$("#btn-add").click();
			sleep(1500);
			$(".btn-sm", 3).click();
			sleep(1500);
		}
	}
	//@Test(priority = 0)
	public void �������α���_����ǽ�_�����߰�() {
		open("http://10.77.129.52:8083/admin/contents/offline");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(2000);
		open("http://10.77.129.52:8083/admin/contents/offline");
		for(int i=1;i<=300;i++) {
			$(".label-br-g", 0).click();
			sleep(2000);
			$(".form-control", 17).setValue("Test " + i + "��° ���");
			js("$('#thumbnail').click();");
		    sleep(3000);
			$(".btn-primary").scrollTo();
			$(".btn-primary").click();
			sleep(1000);
			confirm("����� �Ϸ�Ǿ����ϴ�.");
			sleep(2000);
		}
	}
	//@Test(priority = 0)
	public void �������α���_����ǽ�_������������������¡ó��() {
		open("http://10.77.129.52:8083/admin/login");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(2000);
		open("http://10.77.129.52:8083/admin/contents/offline/applyList?postNo=100");
		for(int i=220;i<=300;i++) {
			$(".btn-primary", 0).click();
			sleep(1000);
			$(".form-control", 0).setValue("Test" + i);
			$(".form-control", 1).setValue("Test" + i);
			$(".form-control", 2).setValue("Test" + i);
			$(".form-control", 4).setValue("1234");
			$(".form-control", 5).setValue("1234");
			$(".form-control", 6).setValue("Test" + i);
			$(".btn-primary").click();
			sleep(1000);
			confirm("��û������ �߰��Ͻðڽ��ϱ�?");
			sleep(500);
			confirm("�߰��Ǿ����ϴ�.");
			sleep(1000);
		}
	}
	//@Test(priority = 0)
	public void �������Ա�_�߰���ݰ���ã��() {
		open("http://10.77.129.82:8080//admin/login");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(2000);
		open("http://10.77.129.82:8080/admin/noPassbookDeposit/makeview");
		String checker = "";
		String Msg = "";
	    $(By.id("chargeType")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='��ݱ���'])[1]/following::option[3]")).click();
		for(int i=100010;i>=0;i--) {
			$(By.name("cSid")).setValue("" + i);
			$(".btn-dark").click();
			//checker = $(".extraTr").text();
			int a = 0;
			Msg = $("p", a).text();
			if(checker.equals("�߰����������� �����ϴ�.")) {
				System.out.println("SID " + i + " �� �߰��������� ����..");
			} else if (checker.equals("�ش� sid�� �������� �ʽ��ϴ�.")) {
				System.out.println("SID " + i + " �� DB�� ���� SID �Դϴ�..");
				sleep(500);
				js("$('.btn-default:last').click();");
				//$(".btn-default").click();
				a++;
				sleep(500);
			} else {
				System.out.println("�١ڡ١� �߰��������� �ִ� SID " + i + " �Դϴ�!! �١ڡ١�");
				close();
			}
			sleep(1000);
		}
	}
	//@Test(priority = 0)
	public void ���񽺸���Ʈ��������_�����߰�() {
		open("http://10.77.129.22:18080/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(2000);
		open("http://10.77.129.22:18080/manage/serviceInfo/addService");
	    for(int i=1;i<=200;i++) {
	    	sleep(1000);
	    	System.out.println("���� �߰� ������ ����");
	    	$(".input-sm", 0).setValue("��������" + i);
	    	System.out.println("������Ʈ �̸� �Է�");
	    	$(".gui-input", 1).setValue("testWeb" + i + ".com");
	    	System.out.println("������Ʈ ������ �Է�");
	    	$(".ace-btn-add-domain").click();
	    	System.out.println("������Ʈ ������ �߰�");
	    	$(By.name("largeCategoryCd")).click();
	    	sleep(500);
	        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='��з�'])[1]/following::option[9]")).click();
	        sleep(500);
	        System.out.println("������Ʈ �з�(��з�) ����");
	    	$(By.name("middleCategoryCd")).click();
	    	sleep(500);
	    	$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='�ߺз�'])[1]/following::option[3]")).click();
	        sleep(500);
	        System.out.println("������Ʈ �з�(�ߺз�) ����");
	        $("#btn_submit").click();
	        System.out.println("���� " + i + "�� ° �߰�");
	        sleep(2000);
	        open("http://10.77.129.22:18080/manage/serviceInfo/addService");
	    }
	}
	//@Test(priority = 0)
	public void ����Ʈ������û_����Ʈ�߰�() {
		open("http://10.77.129.80:8082/common/front");
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(2000);
		open("http://10.77.129.80:8082/setting/reportDown/add");
	    for(int i=298;i<=300;i++) {
	    	sleep(1000);
	    	$(By.name("report-name")).setValue("������Ȯ��" + i);
	    	sleep(1000);
	    	$(By.id("datepicker-reportdown-onetime")).click();
	    	sleep(1000);
	    	$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='����'])[1]/following::li[1]")).click();
	    	sleep(1000);
	    	$("#btn-select-report-all").click();
	    	sleep(1000);
	    	$("#btn-add-report").scrollTo();
	    	$("#btn-add-report").click();
	    	sleep(1000);
	    	open("http://10.77.129.80:8082/setting/reportDown/add");	
	    }
	}
	//@Test(priority = 0)
	public void ����Ʈ������û_������û��ưŬ��() {
		open("http://10.77.129.80:8082/admin/login");
		$("#username").setValue("apzz0928");
		$("#password").setValue("qordlf!@34");
		$(".btn-primary").click();
		sleep(2000);
		open("http://10.77.129.80:8082/admin/apply/reportCreation?page=9&solutionCd=&status=&searchType=url&keyword=");
		int a = 0;
		int b = 10;
		int c = 0;
	    for(int i=0;i<=300;i++) {
	    	//$(By.linkText("[������û]"), a).scrollTo();
	    	$(By.linkText("[������û]"), a).click();
	    	sleep(1000);
	        $(By.id("btn-modal-alert-yes")).click();
	        sleep(1000);
	        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='�˸�'])[2]/following::button[1]")).click();
	        sleep(1000);
	        if(c==19) {
	        	c = 0;
	        	$(By.linkText(b + "")).scrollTo();
	        	$(By.linkText(b + "")).click();
	        	b++;
		        System.out.println("b �� " + b + " �Դϴ�.");
	        }
	        System.out.println("�̹��� Ŭ���� ��ư�� " + (c+1) + " ��° ��ư�Դϴ�.");
	        c++;
	    }
	}
	//@Test(priority = 0)
	public void ��ȯ����_��ȯ�߰�() {
		open("http://new.acecounter.com");
		$("#uid").setValue("apzz09288");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		$(".go_stat").click();
		sleep(1000);
		$(By.linkText("Web Trialgithub.com")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Web Free'])[2]/following::span[1]")).click();
	    sleep(1000);
	    open("http://10.77.129.80:8082/admin/apply/reportCreation?page=9&solutionCd=&status=&searchType=url&keyword=");
		int a = 0;
		int b = 10;
		int c = 0;
	    for(int i=0;i<=300;i++) {
	    	//$(By.linkText("[������û]"), a).scrollTo();
	    	$(By.linkText("[������û]"), a).click();
	    	sleep(1000);
	        $(By.id("btn-modal-alert-yes")).click();
	        sleep(1000);
	        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='�˸�'])[2]/following::button[1]")).click();
	        sleep(1000);
	        if(c==19) {
	        	c = 0;
	        	$(By.linkText(b + "")).scrollTo();
	        	$(By.linkText(b + "")).click();
	        	b++;
		        System.out.println("b �� " + b + " �Դϴ�.");
	        }
	        System.out.println("�̹��� Ŭ���� ��ư�� " + (c+1) + " ��° ��ư�Դϴ�.");
	        c++;
	    }
	}
	//@Test(priority = 0)
	public void ������_���ǳ�����ȸ����¡ó��Ȯ��() {
		open("http://10.77.129.80:8081/common/front");
		$("#uid").setValue("apzz09288");
		$("#upw").setValue("qordlf!@34");
		$(".btn_login").click();
		sleep(1000);
		for(int i=0;i<=120;i++) {
		    open("http://10.77.129.80:8081/common/front/svcCenter/inquiry");
		    sleep(1000);
			$("label", 0).click();
			$("#emailAddress").setValue("test@test.com");
			$("#tel_2").setValue("0000");
			$("#tel_3").setValue("0000");
			$(".btn_join").scrollTo();
			$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='��������'])[1]/following::select[1]")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='��������'])[1]/following::option[2]")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='����'])[1]/following::input[1]")).setValue("�׽�Ʈ����" + i);
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='����'])[1]/following::textarea[1]")).setValue("�׽�Ʈ����" + i);
			$(".btn_join").scrollTo();
			$(".btn_join").click();
			sleep(1000);
	        System.out.println("1:1���� " + (i+1) + "��° �ۼ�");
		    sleep(1500);
		}	    
	}
	//@Test(priority = 0)
	public void ���ǿ�ݰ���_ȸ������() {
		for(int i = 1;i<=30;i++) {
			open("http://10.77.129.80:8082/common/front/signUpStep");
			$("label", 0).click();
			$("label", 1).click();
			$("#userid").setValue(domain + "a" + i);
			$("#recheck").click();
			$("#userpw").setValue("qordlf!@34");
			$("#repeatpw").setValue("qordlf!@34");
			$("#usernm").setValue("�ֿ���");
			$("#useremail").setValue("mail@mail.com");
			$("#hp2").setValue("1234");
			$("#hp3").setValue("1234");
			$("#stepOneCompleted").click();
			$(By.name("input-domain")).setValue(domain + "a" + i + ".com");
			$(".ace-btn-add-domain").click();
		    $(By.name("largeCategoryCd")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='������Ʈ�з�'])[1]/following::option[9]")).click();
		    $(By.name("middleCategoryCd")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='������Ʈ�з�'])[1]/following::option[27]")).click();
		    $("#stepTwoCompleted").click();
		}
	}
	@Test(priority = 0)
	public void ���ǿ�ݰ���_�����߰�() {
		open("http://10.77.129.80:8082/common/front");
		for(int i = 1;i<=10;i++) {
			$("#uid").setValue(domain + "a" + i);
			$("#upw").setValue("qordlf!@34");
			$(".btn_login").click();
			sleep(1500);
			open("http://10.77.129.80:8082/manage/serviceInfo/addService");
			$(".gui-input", 0).setValue(domain + "aaa" + i);
			$(".gui-input", 1).setValue(domain + "aaa" + i + ".com");
			$(".ace-btn-add-domain").click();
		    $(By.name("largeCategoryCd")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='��з�'])[1]/following::option[9]")).click();
		    $(By.name("middleCategoryCd")).click();
		    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='�ߺз�'])[1]/following::option[3]")).click();
		    $("#btn_submit").click();
		    sleep(1000);
		    open("http://10.77.129.80:8082/auth/logout");
		    sleep(1000);
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}

}