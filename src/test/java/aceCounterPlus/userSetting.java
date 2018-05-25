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

public class userSetting {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // 보안설정 변경
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
	
	public static void valCheck(int pTagNum, int btnNum, String val) {
		String msgCheck = $("p", pTagNum).text();
		if(val.equals("CheckIP")) {
			checkMsg = "IP를 입력하세요.";
		} else if (val.equals("IPvalCheck")) {
			checkMsg = "시스템 오류가 발생되었습니다. 다시 시도해주세요.";
		} else if (val.equals("IPregister")) {
			checkMsg = "IP가 등록되었습니다.";
		}  else if (val.equals("IPduplication")) {
			checkMsg = "등록된 IP 입니다. 다시 입력해주세요.";
		}else if (val.equals("searchInputCheck")) {
			checkMsg = "검색어를 입력해 주세요";
		} else if (val.equals("delcheck")) {
			checkMsg = "삭제할 IP를 선택하세요.";
		} else if (val.equals("delIP")) {
			checkMsg = "IP가 삭제되었습니다.";
		}
		$(".modal-backdrop").waitUntil(visible, 3000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " validation check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " validation check Fail ... *** ");
			close();
		}
	}
	
  	//입력된 URL 정상 여부 확인
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
	public void IPFilterring_add() throws InterruptedException {
		System.out.println(" ----- IPFilterring_add Start ----- ");
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("방문수")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#redirectConfBtn").waitUntil(visible, 3000);
		$(".accordion-toggle", 1).click();
		$(By.linkText("IP필터링설정")).click();
		Thread.sleep(1000);
		$("h5", 2).waitUntil(visible, 3000);
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 3000);
		$(".btn-info", 2).click();
		valCheck(4, 3, "CheckIP");
		$("#filter-ipa").setValue("127");
		$(".btn-info", 2).click();
		valCheck(5, 4, "IPCheck");
		$("#filter-ipb").setValue("0");
		$(".btn-info", 2).click();
		valCheck(6, 5, "IPCheck");
		$("#filter-ipc").setValue("0");
		$(".btn-info", 2).click();
		valCheck(7, 6, "IPCheck");
		$("#filter-ipd").setValue("ㅇ");
		$(".btn-info", 2).click();
		valCheck(8, 7, "IPvalCheck");
		Thread.sleep(1000);
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page val check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page val check Reload Fail ... *** ");
			close();
		}
		Thread.sleep(1000);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 3000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page register check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page register check Reload Fail ... *** ");
			close();
		}
		valCheck(3, 3, "IPregister");
		System.out.println(" ----- IPFilterring_add End ----- ");
	}
	@Test(priority = 1)
	public void IPFilterring_duplicationCheck() throws InterruptedException {
		System.out.println(" ----- IPFilterring_duplicationCheck Start ----- ");
		$(".btn-info", 0).click();
		Thread.sleep(1500);
		$("th", 0).waitUntil(visible, 3000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		valCheck(4, 4, "IPduplication");
		$(".btn-light", 0).click();
		System.out.println(" ----- IPFilterring_duplicationCheck End ----- ");
	}
	@Test(priority = 2)
	public void IPFilterring_search() {
		System.out.println(" ----- IPFilterring_search Start ----- ");
		$(".btn-default", 5).click();
		valCheck(5, 5, "searchInputCheck");
		$("#searchIp").setValue("03");
		$(".btn-default", 5).click();
		String search = $("h5", 2).text();
		if(search.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring don`t input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring don`t input search check Fail ... *** ");
			close();
		}
		$("#searchIp").setValue("127");
		$(".btn-default", 5).click();
		$("td", 3).waitUntil(visible, 3000);
		search = $("td", 3).text();
		if(search.equals("127.0.0.1")) {
			System.out.println(" *** IP Filterring input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring input search check Fail ... *** ");
			close();
		}
		System.out.println(" ----- IPFilterring_search End ----- ");
	}
	@Test(priority = 3)
	public void IPFilterring_del() throws InterruptedException {
		System.out.println(" ----- IPFilterring_del Start ----- ");
		$(".btn-gray", 0).click();
		String pageCheck = $(".btn-info", 1).text();
		Thread.sleep(1000);
		if(pageCheck.equals("선택항목삭제")) {
			System.out.println(" *** IP Filterring delete page Load check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring delete page Load check Fail ... *** ");
			close();
		}
		$(".btn-info", 1).click();
		valCheck(3, 3, "delcheck");
		$("#chkAll").click();
		$(".btn-info", 1).click();		
		Thread.sleep(1000);
		$("h5", 2).waitUntil(visible, 3000);
		String pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		valCheck(4, 3, "delIP");
		System.out.println(" ----- IPFilterring_del End ----- ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}