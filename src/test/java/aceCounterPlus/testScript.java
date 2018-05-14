package aceCounterPlus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.ie.InternetExplorerDriver;
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

import com.codeborne.selenide.testng.ScreenShooter;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class testScript {
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser;
	private WebDriver driver;
	@SuppressWarnings("unused")
	private int invalidLinksCount;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://new.acecounter.com/admin/login";
		hubUrl = "http://10.77.129.79:5555/wd/hub";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = false;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 900));
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			cap = DesiredCapabilities.firefox();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 900));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 900));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); //  보안설정
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 900));
		}
	}
	private static void js(String javaScriptSource) {
		Object obj = executeJavaScript(javaScriptSource);
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
		String[] data2 = {"다음Ad@m", "인모비", "크리테오", "캐시슬라이드", "EDNPlus", "N2S", "모비온", "모비팝", "애드팝콘", "애드풀", "애드픽", "adCRM", "appcoach", "OK캐쉬백락", "넥슨플레이락", "노티투미", "온누리DMC",
				"애드플러스", "쿠차핫클릭"};
		String[] data3 = {"다음adam", "인모비", "크리테오", "캐시슬라이드", "ednplus", "n2s", "모비온", "모비팝", "애드팝콘", "애드풀", "애드픽", "adcrm", "appcoach", "ok캐쉬백락", "넥슨플레이락", "노티투미", "온누리dmc",
				"애드플러스", "쿠차핫클릭"};
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
	        $(By.id("campaign_nm")).sendKeys("일반-" + data3[i] + "-광고상품-url");
	        Thread.sleep(1000);
	        $(By.id("campaign_material_value0")).click();
	        $(By.id("campaign_material_value0")).sendKeys("일반-" + data3[i] + "-광고상품-url");
	        Thread.sleep(1000);
	        $(By.id("app_market_cd0")).click();
	        Thread.sleep(1000);
			//$(By.id("inflow_media_cd")).selectOption("구글플레이스토어");
	        //new Select(driver.findElement(By.id("app_market_cd0"))).selectByVisibleText("구글플레이스토어");
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
			System.out.println(i + " 까지 했습니당");
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
	        new Select($(By.name("campaignMaterialCd[]"))).selectByVisibleText("검색");
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
	public void 파트너_추가() throws Exception {
		open("http://10.160.231.21");
		Thread.sleep(1000);
		$("#username").setValue("admin");
		$("#password").setValue("nhnace");
		$("input", 3).click();
		Thread.sleep(1000);
		open("http://10.160.231.21/advertiser/detail?agencyid=19");
		Thread.sleep(2000);
	    for(int i=0;i<=101;i++) {
	    	$(By.name("clientname")).setValue("삭제할파트너_a" + i);
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
	public void 에이전시_추가() throws Exception {
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
	@Test(priority = 7)
	public void 파트너_담당자_추가() throws Exception {
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
	    	$(By.name("contact_name")).setValue("삭제할사용자_a" + i);	    	
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
	public void 에이전시_담당자_추가() throws Exception {
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
	    	$(By.name("contact_name")).setValue("삭제할사용자_a" + i);	    	
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
	public void 캠페인_추가() throws Exception {
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
	public void 매체_사이트_추가() throws Exception {
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
	public void 매체_영역_추가() throws Exception {
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
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}

}