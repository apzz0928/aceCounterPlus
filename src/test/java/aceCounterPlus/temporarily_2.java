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
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, A, B, C, domain, checkMsg, pageLoadCheck, dateCheck;
	private static HttpURLConnection huc;
	private static int respCode;

	// 신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
	SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
	SimpleDateFormat number_format1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat number_format2 = new SimpleDateFormat("MMddhhmmss");
	String date = number_format.format(number_date);
	String date1 = number_format1.format(number_date);
	String date2 = number_format2.format(number_date);

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf";
		pw1 = "qordlf";
		A = "!@34";
		B = "1@#4";
		C = "12#$";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = false;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*
			 * ChromeOptions options = new ChromeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			// cap = DesiredCapabilities.firefox();
			// RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*
			 * EdgeOptions options = new EdgeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*
			 * InternetExplorerOptions options = new InternetExplorerOptions(); driver = new
			 * RemoteWebDriver(new URL(urlToRemoteWD), options);
			 */
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

	public static void valCheck(String val) {
		switch (val) {
		case "scriptList_email_null":
			checkMsg = "";
			break;
		case "scriptList_email_validation":
			checkMsg = "";
			break;
		case "scriptList_email_send":
			checkMsg = "";
			break;
		case "installApply_null":
			checkMsg = "";
			break;
		case "installApply_name_null":
			checkMsg = "";
			break;
		case "installApply_email_null":
			checkMsg = "";
			break;
		case "installApply_email_validation":
			checkMsg = "";
			break;
		case "installApply_FTP_null":
			checkMsg = "";
			break;
		case "installApply_port_null":
			checkMsg = "";
			break;
		case "installApply_id_null":
			checkMsg = "";
			break;
		case "installApply_pw_null":
			checkMsg = "";
			break;
		case "installApply_agree_null":
			checkMsg = "";
			break;
		case "memberInfo_change_alert":
			checkMsg = "";
			break;
		}
		$(".modal-backdrop").waitUntil(visible, 10000);
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	        	System.out.println(" *** " + val +  " - confirm check Success !! *** ");
				$$(".btn-info").last().click();
			    $(".modal-backdrop").waitUntil(hidden, 10000);
	        } else { //confirm 아니면 .btn-sm클릭
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            //$(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert 로딩이 늦거나 노출되지 않았을때 체크하기위해 빈값 체크
	        System.out.println(" *** ☆★☆★☆★ val : " + val + " // pTag text is : " + msgCheck +  " // - msgCheck is Empty ... ☆★☆★☆★ *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** // val : " + val + " // pTag text is : " + msgCheck +  " // - check Fail ... !@#$%^&*() *** ");
	        System.out.println(checkMsg);
	        close();
	    }
	}

	// 입력된 URL 정상 여부 확인
	public static boolean brokenLinkCheck(String urlName, String urlLink) {
		try {
			huc = (HttpURLConnection) (new URL(urlLink).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			if (respCode >= 400) {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() *** ");
				close();
			} else {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Success !! *** ");
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
			ex.printStackTrace();
		}
	}

	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}

	@Test(priority = 0)
	public void login() {
		System.out.println(" ! ----- login Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 15000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text().trim();
		$(".btn_logout").getValue();
		if (loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat").click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("Live 대시보드")) {
			System.out.println(" *** stats getInflow login Success !! *** ");
		} else {
			System.out.println(" *** stats getInflow login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}

	@Test(priority = 1)
	public void user_stats() {
		System.out.println(" ! ----- user_stats Start ----- ! ");
		$("#user").click();
		$(By.linkText("사용자")).waitUntil(visible, 10000);
		$(By.linkText("사용자")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("사용자")) {
			System.out.println(" *** user_stats page load Success !! *** ");
		} else {
			System.out.println(" *** user_stats page load Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		//날짜선택
		dateCheck = $(".month", 1).text().trim(); //1번째 달력 월 확인
		for(int i=0;i<=1;i++) {
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
				dateCheck = $(".month", 0).text().trim(); //2번째 달력 월 확인
			}
			for(int x=0;x<=100;x++) { //2018.12가 될때까지 >> 클릭
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r1c5]", i).click(); //3일 선택
					break;
				} else {
					System.out.println("no"+ (i+1) + ". calender month is  : " + dateCheck + " // need nextBtn(" + x + ") click");
					$(".next", i).click();
					dateCheck = $(".month", i).text().trim();
				}
			}
			if(i==0) {
				System.out.println("start calender date select!");	
			} else {
				System.out.println("end calender date select!");				
			}
		}
		$(".btn-apply").click();
		refresh();
		$("#compareTermText").waitUntil(visible, 10000);
		dateCheck = $("#compareTermText").text();
		String[] pLC = dateCheck.split(" ");
		if (pLC[0].equals("2018.12.06") && pLC[2].equals("2018.12.06")) {
			System.out.println(" *** user_stats date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** user_stats date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".summary-data", 0).waitUntil(visible, 10000);
		String[] panelDataCheck = {"127", "2", "0", "1,178", "9.28", "visit number", "unique visit", "new visit", "page view", "visit page view"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** user_stats panel summary data " + panelDataCheck[i+5] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats panel summary data " + panelDataCheck[i+5] + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] chartDataCheck = {"2018.12.07(금)", "방문수: 127", "순방문수: 2", "신규방문수: 0", "페이지뷰: 1,178", "daily publication", "visit number", "unique visit", "new visit", "page view"};
		$(".highcharts-series-group").hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_stats chart tooltip data " + chartDataCheck[i+5] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats chart tooltip data " + chartDataCheck[i+5] + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] tableDataCheck = {"2018.12.07(금)", "127", "100%", "2", "1,178", "9.28", "00:24:51", "00:00:11", "daily publication", "visit number", "visit percent", "unique visit", "page view", "visit page view", "stay time", "visit stay time"};
		$("td", 15).waitUntil(visible, 10000);
		for(int i=0, x=15; i<=7; i++, x++) {
			pageLoadCheck = $("td", x).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** user_stats table data " + tableDataCheck[i+8] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats table data " + tableDataCheck[i+8] + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}

	    System.out.println(" ! ----- user_stats End ----- ! ");
	}
	@Test(priority = 2)
	public void user_active_stats() {
		System.out.println(" ! ----- user_active_stats Start ----- ! ");
		$(By.linkText("액티브 사용자")).waitUntil(visible, 10000);
		$(By.linkText("액티브 사용자")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("액티브 사용자")) {
			System.out.println(" *** user_active_stats page load Success !! *** ");
		} else {
			System.out.println(" *** user_active_stats page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] chartDataCheck = {"2018.12.07(금)", "1일 사용자: 2", "7일 사용자: 2", "14일 사용자: 2", "30일 사용자: 2", "daily publication", "1day visit", "7day visit", "14day visit", "30day visit"};
		$(".highcharts-series-group", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_active_stats accrue active user chart tooltip data " + chartDataCheck[i+5] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_active_stats accrue active user chart tooltip data " + chartDataCheck[i+5] + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		chartDataCheck[1] = "1일 사용자: 0";
		$(".highcharts-series-group", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_active_stats new active user chart tooltip data " + chartDataCheck[i+5] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_active_stats new active user chart tooltip data " + chartDataCheck[i+5] + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- user_active_stats End ----- ! ");
	}
	@Test(priority = 3)
	public void user_stats_percent() {
		System.out.println(" ! ----- user_stats_percent Start ----- ! ");
		$(By.linkText("유지율")).waitUntil(visible, 10000);
		$(By.linkText("유지율")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("유지율")) {
			System.out.println(" *** user_stats_percent page load Success !! *** ");
		} else {
			System.out.println(" *** user_stats_percent page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".another-class", 3).waitUntil(visible, 10000);
		pageLoadCheck = $(".another-class", 3).text().trim();
		if (pageLoadCheck.equals("2018.12.07 (금)")) {
			System.out.println(" *** user_stats_percent table text check Success !! *** ");
		} else {
			System.out.println(" *** user_stats_percent table text check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- user_stats_percent End ----- ! ");
	}
	@Test(priority = 4)
	public void user_visit_frequency() {
		System.out.println(" ! ----- user_visit_frequency Start ----- ! ");
		$(By.linkText("방문빈도")).waitUntil(visible, 10000);
		$(By.linkText("방문빈도")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("방문빈도")) {
			System.out.println(" *** user_visit_frequency page load Success !! *** ");
		} else {
			System.out.println(" *** user_visit_frequency page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"방문수", "방문당 페이지뷰", "당일", "2~7일", "당일", "1일", "2~7일", "8~15일", "16~30일", "1개월~3개월", "3개월 이후"};
		for(int i=0;i<=10;i++) {
			$(".highcharts-legend-item", i).waitUntil(visible, 10000);
			pageLoadCheck = $(".highcharts-legend-item", i).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** user_visit_frequency Chart a legend(" + i + ") check Success !! *** ");
			} else {
				System.out.println(" *** user_visit_frequency Chart a legend(" + i + ") check Fail ... !@#$%^&*() *** ");
				close();
			}
			if(i==1) {
				$("#btnChartPie").click();
				$(".highcharts-legend-item", i).waitUntil(visible, 10000);
			} else if (i==3) {
				$("#btnChartLine").click();
				$(".highcharts-legend-item", i).waitUntil(visible, 10000);
			}
		}
	    System.out.println(" ! ----- user_visit_frequency End ----- ! ");
	}
	@Test(priority = 11)
	public void user_system_web() {
		System.out.println(" ! ----- user_system_web Start ----- ! ");
		$(By.linkText("시스템")).waitUntil(visible, 10000);
		$(By.linkText("시스템")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("시스템")) {
			System.out.println(" *** user_system_web page load Success !! *** ");
		} else {
			System.out.println(" *** user_system_web page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"기기타입PC", "웹브라우저firefox 62.0chrome 70.0", "운영체제windows 10"};
		$("svg", 0).waitUntil(visible, 10000);
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $("svg", i).text().trim();
			String[] pLC = pageLoadCheck.split("Created with Highcharts 4.2.5");
			if (pLC[0].equals(tableDataCheck[i])) {
				System.out.println(" *** user_visit_frequency Chart a legend(" + i + ") check Success !! *** ");
				pLC = null;
			} else {
				System.out.println(" *** user_visit_frequency Chart a legend(" + i + ") check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- user_system_web End ----- ! ");
	}
	@Test(priority = 21)
	public void user_region() {
		System.out.println(" ! ----- user_region Start ----- ! ");
		$(By.linkText("지역")).waitUntil(visible, 10000);
		$(By.linkText("지역")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("지역")) {
			System.out.println(" *** user_region page load Success !! *** ");
		} else {
			System.out.println(" *** user_region page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] user_region_check = {"알수없음", "127", "100%", "2", "9.28", "00:00:12", "region", "visit number", "visit percent", "unique visit", "visit page view", "visit stay time"};
		$("td", 13).waitUntil(visible, 10000);
		for(int i=0,x=13;i<=5;i++,x++) {
			pageLoadCheck = $("td", x).text().trim();
			if (pageLoadCheck.equals(user_region_check[i])) {
				System.out.println(" *** user_region table data " + user_region_check[i+6] + " check Success !! *** ");
			} else {
				System.out.println(" *** user_region table data " + user_region_check[i+6] + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- user_region End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}