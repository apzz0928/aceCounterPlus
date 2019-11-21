package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;

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

public class getInflow {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, dateCheck;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
		pw = "qordlf";
		A = "!@34";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

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
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("로그아웃")) {
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
	public void getInflow_summary() {
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
		//날짜선택 (2018.12.07)
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) { //과거 날짜 : 시작캘린더부터 선택(0과 1 변경, 부등호 변경, ++와 --변경)
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
			}
			dateCheck = $(".month", i).text().trim(); //2번째 달력 월 확인
			for(int x=0;x<=100;x++) { //2018.12가 될때까지 << 클릭
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r1c5]", i).click(); //7일 선택
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
		dateCheck = $("#compareTermText").text();
		String[] pLC = dateCheck.split(" ");
		if (pLC[0].equals("2018.12.06") && pLC[2].equals("2018.12.06")) { ////////////////데이터 다시 쌓는중
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
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + ")) check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(tableDataCheck[i])) {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + ")) check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_Summary table data " + tableDataCheck[i+12] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}				
			}
		}
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.07(금)", "유료마케팅: 0", "일반마케팅: 0", "인하우스마케팅: 0", "자연유입: 127", "합계: 127", "((date))", "((charge marketing))", "((no charge marketing))", "((inhouse marketing))", "((nature inflow))", "((total))"};
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** getInflow_Summary bar chart data " + barChartDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Summary bar chart data " + barChartDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		sleep(500);
		$(".highcharts-series-0", 2).waitUntil(visible, 10000);
		$(".highcharts-series-0", 2).hover();
		$(".highcharts-series-0", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		System.out.println("highcharts-tooltip : " + pageLoadCheck);
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
		String[] lineChartDataCheck = {"2018.12.07(금)", "유료마케팅: 0", "일반마케팅: 0", "인하우스마케팅: 0", "자연유입: 127", "((date))", "((charge marketing))", "((no charge marketing))", "((inhouse marketing))", "((nature inflow))"};
		for(int i=0;i<=4;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** getInflow_Summary line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_Summary line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- getInflow_Summary End ----- ! ");
	}
	@Test(priority = 2)
	public void getInflow_detail() {
		System.out.println(" ! ----- getInflow_detail Start ----- ! ");
		$(By.linkText("상세")).waitUntil(visible, 10000);
		$(By.linkText("상세")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("상세")) {
			System.out.println(" *** getInflow_detail page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_detail page load Fail ... !@#$%^&*() *** ");
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
					System.out.println(" *** getInflow_detail table data " + tableDataCheck[i+11] + "((" + i + ")) check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_detail table data " + tableDataCheck[i+11] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(tableDataCheck[i])) {
					System.out.println(" *** getInflow_detail table data " + tableDataCheck[i+11] + "((" + i + ")) check Success !! *** ");		
				} else {
					System.out.println(" *** getInflow_detail table data " + tableDataCheck[i+11] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}				
			}
		}
		
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.07(금)", "신규방문: 0", "재방문: 127", "방문수: 127", "((date))", "((new visit))", "((re visit))", "((visit number))"};
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** getInflow_detail bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_detail bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
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
		String[] lineChartDataCheck = {"2018.12.07(금)", "검색엔진: 89", "다이렉트: 38", "((date))", "((search engine))", "((direct))"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** getInflow_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
	    System.out.println(" ! ----- getInflow_detail End ----- ! ");
	}
	@Test(priority = 3)
	public void getInflow_treeMap() {
		System.out.println(" ! ----- getInflow_treeMap Start ----- ! ");
		$(By.linkText("트리맵")).waitUntil(visible, 10000);
		$(By.linkText("트리맵")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("트리맵")) {
			System.out.println(" *** getInflow_treeMap page load Success !! *** ");
		} else {
			System.out.println(" *** getInflow_treeMap page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] treeMapdataCheck = {"자연유입 : 127", "검색엔진 : 89", "Google USA : 89", "구글 검색어없음 : 89"};
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
				System.out.println(" *** getInflow_treeMap treeMap data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** getInflow_treeMap treeMap data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- getInflow_treeMap End ----- ! ");
	}
	@Test(priority = 11)
	public void searchEngine() {
		System.out.println(" ! ----- searchEngine Start ----- ! ");
		open("https://new.acecounter.com/stats/getInflowSearch");
		$(By.linkText("검색엔진")).waitUntil(visible, 10000);
		$(By.linkText("검색엔진")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("검색엔진")) {
			System.out.println(" *** searchEngine page load Success !! *** ");
		} else {
			System.out.println(" *** searchEngine page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 22).waitUntil(visible, 10000);
		String[] tableDataCheck = {"합계", "100.0%\n89", "100%", "((search word))", "((visit number))", "((return percent google))"};
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $("td", (i+16)).text().trim().replace(" ", "");
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** searchEngine table data " + tableDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** searchEngine table data " + tableDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] barChartDataCheck = {"2018.12.07(금)", "무료: 89", "유료: 0", "합계: 89", "((date))", "((no charge))", "((charge))", "((total))"};
		$(".highcharts-tracker > path", 2).waitUntil(visible, 10000);
		sleep(1000);
		js("$('rect').eq(11).mouseover()");
		js("$('.highcharts-tracker > path').eq(2).mouseover()");
		$("rect", 11).waitUntil(visible, 10000);
		$("rect", 11).hover();
		$(".highcharts-tracker > path", 2).hover();
		$("#highcharts-6", 0).hover();
		$(".highcharts-tracker > path", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 3).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** searchEngine bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** searchEngine bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$(By.linkText("유입도메인")).waitUntil(visible, 10000);
	    System.out.println(" ! ----- searchEngine End ----- ! ");
	}
	@Test(priority = 21)
	public void inflowDomain() {
		System.out.println(" ! ----- inflowDomain Start ----- ! ");
		$(By.linkText("유입도메인")).waitUntil(visible, 10000);
		$(By.linkText("유입도메인")).click();
		$("td", 38).waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("유입 도메인")) {
			System.out.println(" *** inflowDomain page load Success !! *** ");
		} else {
			System.out.println(" *** inflowDomain page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".panel-body", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".panel-body", 0).text().trim();
		if (pageLoadCheck.equals("방문수\n89\n89(0.00%)")) {
			System.out.println(" *** inflowDomain panel text check Success !! *** ");
		} else {
			System.out.println(" *** inflowDomain panel text check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 28).waitUntil(visible, 10000);
		$("td", 28).click();
		$("td", 40).waitUntil(visible, 10000);
		String[] tableDataCheck = {"└ www.google.com", "89", "100%", "89", "100%", "1", "00:00:00", "0", "0%", "0", "0", "((inflow domain))", "((visit number))", "((visit percent))", "((return number))", "((return percent))", "((visit pageview))", "((visit time))", "((convert number))", "((convert percent))", "((convert seles))", "((visit sales))"};
		for(int i=0;i<=10;i++) {
			pageLoadCheck = $("td", (i+40)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** inflowDomain table data " + tableDataCheck[i+11] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomain table data " + tableDataCheck[i+11] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] barChartDataCheck = {"2018.12.07(금)", "신규방문: 0", "재방문: 89", "방문수: 89", "((date))", "((new visit))", "((return visit))", "((visit number))"};
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=3;i++) {
			if (pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** inflowDomain bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomain bar chart data " + barChartDataCheck[i+4] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 4).waitUntil(visible, 10000);
		$(".highcharts-tracker", 5).waitUntil(visible, 10000);
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 5).hover();
		$(".highcharts-tracker", 4).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.07(금)", "google.com: 89", "((date))", "((inflow domain))"};
		for(int i=0;i<=1;i++) {
			if (pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** inflowDomain line chart data " + lineChartDataCheck[i+2] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomain line chart data " + lineChartDataCheck[i+2] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- inflowDomain End ----- ! ");
	}
	@Test(priority = 31)
	public void overlabInflow() {
		System.out.println(" ! ----- overlabInflow Start ----- ! ");
		$(By.linkText("중복유입")).waitUntil(visible, 10000);
		$(By.linkText("중복유입")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("중복유입")) {
			System.out.println(" *** overlabInflow page load Success !! *** ");
		} else {
			System.out.println(" *** overlabInflow page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		for(int i=1;i>=0;i--) { //종료캘린더부터 선택(0과 1 변경, 부등호 변경, ++와 --변경)
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
			}
			dateCheck = $(".month", i).text().trim(); //2번째 달력 월 확인
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
		sleep(2000);
		$("td", 38).waitUntil(visible, 10000);
		$("td", 9).click();
		$("td", 25).waitUntil(visible, 10000);
		String[] tableDataCheck = {"10.77.129.79", "네이버브랜드검색", "다이렉트", "네이버브랜드-마케팅", "naverbrand-marketing", "-", "1", "-", "2018-12-19 09:39", "((IP))", "((advertising product))", "((inflow domain detail))", "((campaign))", "((search keyword))", "((domain))", "((page view))", "((convert number))", "((inflow time))"};
		for(int i=0;i<=8;i++) {
			pageLoadCheck = $("td", (i+17)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** overlabInflow table data " + tableDataCheck[i+9] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** overlabInflow table data " + tableDataCheck[i+9] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- overlabInflow End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}