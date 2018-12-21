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

public class getInflow {
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

	//@Test(priority = 1)
	public void getInflow_Summary() {
		System.out.println(" ! ----- getInflow_Summary Start ----- ! ");
		$("#uip").click();
		$(By.linkText("유입출처")).waitUntil(visible, 10000);
		$(By.linkText("유입출처")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("유입출처")) {
			System.out.println(" *** getInflow_Summary page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_Summary page load Fail ... !@#$%^&*() *** ");
			close();
		}
		//날짜선택
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
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
		dateCheck = $("#compareTermText").text();
		String[] pLC = dateCheck.split(" ");
		if (pLC[0].equals("2018.12.06") && pLC[2].equals("2018.12.06")) {
			System.out.println(" *** getInflow_Summary date range pick Success !! *** ");
		} else {
			System.out.println(" *** getInflow_Summary date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		pLC = null;
		String[] tableDataCheck = {"연유입", "100%", "96", "75.59%", "9.28", "00:00:11", "30", "23.62%", "300", "2.36", "100.0%", "127", "((getInflow))", "((visit percent))", "((return number))", "((return percent))", "((visit pageview))", "((visit stay time))", "((convert number))", "((convert percent))", "((convert sales))", "((visit sales))", "((visit number compare))", "((visit number))"};
		$("td", 21).waitUntil(visible, 10000);
		for(int i=0;i<=11;i++) {
			pageLoadCheck = $("td", (i+21)).text().trim();
			if(i==0) { //드릴다운때문에 자연유입만 따로체크
				String[] getInflowSummaryCheck = pageLoadCheck.split("자");
				if(getInflowSummaryCheck[1].equals(tableDataCheck[0])) {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + "))" + " check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(tableDataCheck[i])) {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + "))" + " check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}				
			}
		}
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] barChartData = {"2018.12.07(금)", "유료마케팅: 0", "일반마케팅: 0", "인하우스마케팅: 0", "자연유입: 127", "합계: 127", "((daily publication))", "((charge marketing))", "((no charge marketing))", "((inhouse marketing))", "((nature inflow))", "((total))"};
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(barChartData[i])) {
				System.out.println(" *** getInflow_Summary bar chart data " + barChartData[i+6] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Summary bar chart data " + barChartData[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		$(".highcharts-series-0", 2).waitUntil(visible, 10000);
		$(".highcharts-series-0", 2).hover();
		$(".highcharts-series-0", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		if(pageLoadCheck.equals("자연유입방문수: 100.0%")) {
			System.out.println(" *** getInflow_Summary pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** getInflow_Summary pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartLine").click();
		$(".highcharts-tracker", 12).waitUntil(visible, 10000);
		$(".highcharts-tracker", 10).hover();
		$(".highcharts-tracker", 12).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartData = {"2018.12.07(금)", "유료마케팅: 0", "일반마케팅: 0", "인하우스마케팅: 0", "자연유입: 127", "((daily publication))", "((charge marketing))", "((no charge marketing))", "((inhouse marketing))", "((nature inflow))"};
		for(int i=0;i<=4;i++) {
			if(pLC[i].equals(lineChartData[i])) {
				System.out.println(" *** getInflow_Summary line chart data " + lineChartData[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Summary line chart data " + lineChartData[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- getInflow_Summary End ----- ! ");
	}
	//@Test(priority = 2)
	public void getInflow_Detail() {
		System.out.println(" ! ----- getInflow_Detail Start ----- ! ");
		$(By.linkText("상세")).waitUntil(visible, 10000);
		$(By.linkText("상세")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("상세")) {
			System.out.println(" *** getInflow_Detail page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_Detail page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#select_inflow_options_0").waitUntil(visible, 10000);
		$("#select_inflow_options_0").click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='트리맵'])[1]/following::option[4]")).click();
	    $("#btn_inflow_options_search").click();
		String[] tableDataCheck = {"이렉트", "38", "29.92%", "7", "18.42%", "28.66", "00:00:39", "30", "78.95%", "300", "7.89", "((sort))", "((visit number))", "((visit percent))", "((return number))", "((return percent))", "((visit pageview))", "((visit stay time))", "((convert number))", "((convert percent))", "((convert sales))", "((visit sales))"};
	    $("td", 62).waitUntil(visible, 10000);
		for(int i=0;i<=10;i++) {
			pageLoadCheck = $("td", (i+62)).text().trim();
			if(i==0) { //드릴다운때문에 구분만 따로체크
				String[] getInflowDetailCheck = pageLoadCheck.split("다");
				if(getInflowDetailCheck[1].equals(tableDataCheck[0])) {
					System.out.println(" *** getInflow_Detail table data " + tableDataCheck[i+11] + "((" + i + "))" + " check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Detail table data " + tableDataCheck[i+11] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(tableDataCheck[i])) {
					System.out.println(" *** getInflow_Detail table data " + tableDataCheck[i+11] + "((" + i + "))" + " check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Detail table data " + tableDataCheck[i+11] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}				
			}
		}
		
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartData = {"2018.12.07(금)", "신규방문: 0", "재방문: 127", "방문수: 127", "((daily publication))", "((new visit))", "((re visit))", "((visit number))"};
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartData[i])) {
				System.out.println(" *** getInflow_Detail bar chart data " + barChartData[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Detail bar chart data " + barChartData[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 6).waitUntil(visible, 10000);
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tracker", 4).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartData = {"2018.12.07(금)", "검색엔진: 89", "다이렉트: 38", "((daily publication))", "((search engine))", "((direct))"};
		for(int i=0;i<=4;i++) {
			if(pLC[i].equals(lineChartData[i])) {
				System.out.println(" *** getInflow_Detail line chart data " + lineChartData[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Detail line chart data " + lineChartData[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
	    System.out.println(" ! ----- getInflow_Detail End ----- ! ");
	}
	//@Test(priority = 3)
	public void getInflow_TreeMap() {
		System.out.println(" ! ----- getInflow_TreeMap Start ----- ! ");
		$(By.linkText("트리맵")).waitUntil(visible, 10000);
		$(By.linkText("트리맵")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("트리맵")) {
			System.out.println(" *** getInflow_TreeMap page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_TreeMap page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] treeMapdataCheck = {"자연유입 : 127", "검색엔진 : 89", "Google USA : 89", "구글 검색어 없음 : 89"};
		for(int i=0;i<=3;i++) {
			if(i>0) {
				$("tspan", 0).click();
				$(".treemap_nav", i).waitUntil(visible, 10000);
				$(".treemap_nav", i).hover();
				$("tspan", 0).hover();
			}
			$(".highcharts-tracker", 1).waitUntil(visible, 10000);
			$(".highcharts-tracker", 1).hover();
			pageLoadCheck = $(".highcharts-tooltip").text().trim();
			if(pageLoadCheck.equals(treeMapdataCheck[i])) {
				System.out.println(" *** getInflow_TreeMap treeMap data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_TreeMap treeMap data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- getInflow_TreeMap End ----- ! ");
	}
	@Test(priority = 11)
	public void getInflow_Search() {
		System.out.println(" ! ----- getInflow_Search Start ----- ! ");
		open("https://new.acecounter.com/stats/getInflowSearch");
		$(By.linkText("검색엔진")).waitUntil(visible, 10000);
		$(By.linkText("검색엔진")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("검색엔진")) {
			System.out.println(" *** getInflow_Search page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_Search page load Fail ... !@#$%^&*() *** ");
			close();
		}
		//날짜선택 (검색엔진 데이터가 많지 않아서 많은날로 변경)
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		dateCheck = $(".month", 1).text().trim(); //1번째 달력 월 확인
		for(int i=1;i>=0;i--) { //종료일 캘린더부터 선택
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
				dateCheck = $(".month", 0).text().trim(); //2번째 달력 월 확인
			}
			for(int x=0;x<=100;x++) { //2018.12가 될때까지 << 클릭
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r3c3]", i).click(); //19일 선택
					break;
				} else {
					System.out.println("no"+ (i+1) + ". calender month is  : " + dateCheck + " // need nextBtn(" + x + ") click");
					$(".prev", i).click();
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
		$("td", 16).waitUntil(visible, 10000); 		//여기하던중 네이버랑 다음이랑 수치가 같아서 순서가 바뀔때도 있으므로 다음 데이터 더 채움
		String[] pieChartData = {"다음: 43.2%", "네이버: 29.5%", "구글: 27.3%", "유료: 100.0%", "PC: 100.0%", "((search engine))", "((search engine))", "((search engine))", "((charge VS no charge))", "((device))"};
		for(int i=0;i<=4;i++) {
			sleep(2000);
			js("$('.highcharts-tracker > path').eq(" + i + ").mouseover()");
			sleep(2000);
			if(i<3) {
				js("ace.alert($('.highcharts-tooltip').eq(0).text())");
			} else {
				js("ace.alert($('.highcharts-tooltip').eq(" + (i-2) + ").text())");
			}
			sleep(3000);
			$(".modal-body").click();
			pageLoadCheck = $(".modal-body").text().trim();
			sleep(2000);
			$$(".btn-sm").last().click();
			//$(".btn-sm", (i+22)).click();
			sleep(2000);
			System.out.println("pageLoadCheck is :" + pageLoadCheck + ".");
			if(pageLoadCheck.equals(pieChartData[i])) {
				System.out.println(" *** getInflow_Search pie chart data " + pieChartData[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Search pie chart data " + pieChartData[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}

		
		/*for(int i=0,x=0;i<=4;i++) {
			if(i>=2) {
				x++;
			}
			js("$('.highcharts-tracker > path').eq(" + i + ").mouseover()");
			pageLoadCheck = $(".highcharts-tooltip", x).text().trim();
			if(pageLoadCheck.equals(pieChartData[i])) {
				System.out.println(" *** getInflow_Search pie chart data " + pieChartData[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Search pie chart data " + pieChartData[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}

		}*/
		String[] barChartData = {"2018.12.19(수)", "유료: 44", "무료: 0", "합계: 44", "((daily publication))", "((charge))", "((no charge))", "((total))"};
		$(".highcharts-tracker > path", 5).hover();
		$(".highcharts-tracker > path", 4).hover();
		$(".highcharts-tracker > path", 5).hover();
		pageLoadCheck = $(".highcharts-tooltip", 3).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartData[i])) {
				System.out.println(" *** getInflow_Search bar chart data " + barChartData[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Search bar chart data " + barChartData[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		String[] tableDataCheck = {"합계", "          43.2%       29.5%       27.3%          44", "100%", "92.31%", "100%", "((total))", "((visit number))", "((return percent daum))", "((return percent naver))", "((return percent google))"};
		pageLoadCheck = $("td", 17).text().trim().replace(" ", "");
		System.out.println("pageLoadCheck is :" + pageLoadCheck + ".");
		System.out.println("tableDataCheck is :" + tableDataCheck[1].trim().replace(" ", "") + ".");
		
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $("td", (i+16)).text().trim().replace(" ", "");
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** getInflow_Search table data " + tableDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Search table data " + tableDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- getInflow_Search End ----- ! ");
	}
	//@Test(priority = 21)
	public void getInflowDomain() {
		System.out.println(" ! ----- getInflowDomain Start ----- ! ");
		$(By.linkText("유입도메인")).waitUntil(visible, 10000);
		$(By.linkText("유입도메인")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("유입 도메인")) {
			System.out.println(" *** getInflowDomain page load Success !! *** ");
		} else {
			System.out.println(" *** getInflowDomain page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".panel-body", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".panel-body", 0).text().trim();
		if (pageLoadCheck.equals("방문수\n" + "89\n" + 	"89(0.00%)")) {
			System.out.println(" *** getInflowDomain panel text check Success !! *** ");
		} else {
			System.out.println(" *** getInflowDomain panel text check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 28).waitUntil(visible, 10000);
		$("td", 28).click();
		$("td", 40).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 40).text().trim();
		if (pageLoadCheck.equals("└ www.google.com")) {
			System.out.println(" *** getInflowDomain table text check Success !! *** ");
		} else {
			System.out.println(" *** getInflowDomain table text check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- getInflowDomain End ----- ! ");
	}
	//@Test(priority = 31)
	public void getInflowOver() {
		System.out.println(" ! ----- getInflowOver Start ----- ! ");
		$(By.linkText("중복유입")).waitUntil(visible, 10000);
		$(By.linkText("중복유입")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("중복유입")) {
			System.out.println(" *** getInflowOver page load Success !! *** ");
		} else {
			System.out.println(" *** getInflowOver page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("tr", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("tr", 5).text().trim();
		if (pageLoadCheck.equals("조회된 데이터가 없습니다.")) {
			System.out.println(" *** getInflowOver data check Success !! *** ");
		} else {
			System.out.println(" *** getInflowOver data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- getInflowOver End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}