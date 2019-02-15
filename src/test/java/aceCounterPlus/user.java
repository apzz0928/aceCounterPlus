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

public class user {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, dateCheck, nodata;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		pw = "qordlf";
		A = "!@34";
		nodata = "조회된 데이터가 없습니다.";

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

	@SuppressWarnings("unused")
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
			System.out.println(" *** stats user login Success !! *** ");
		} else {
			System.out.println(" *** stats user login Fail ... !@#$%^&*() *** ");
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
		String[] panelDataCheck = {"127", "2", "0", "1,178", "9.28", "((visit number))", "((unique visit))", "((new visit))", "((page view))", "((visit page view))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** user_stats panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] chartDataCheck = {"2018.12.07(금)", "방문수: 127", "순방문수: 2", "신규방문수: 0", "페이지뷰: 1,178", "((date))", "((visit number))", "((unique visit))", "((new visit))", "((page view))"};
		$(".highcharts-series-group").hover();
		$(".highcharts-tooltip").waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_stats chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] tableDataCheck = {"2018.12.07(금)", "127", "100%", "2", "1,178", "9.28", "00:24:51", "00:00:11", "((date))", "((visit number))", "((visit percent))", "((unique visit))", "((page view))", "((visit page view))", "((stay time))", "((visit stay time))"};
		$("td", 15).waitUntil(visible, 10000);
		for(int i=0;i<=7;i++) {
			pageLoadCheck = $("td", i+15).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** user_stats table data " + tableDataCheck[i+8] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** user_stats table data " + tableDataCheck[i+8] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- user_stats End ----- ! ");
	}
	@Test(priority = 2)
	public void user_activeStats() {
		System.out.println(" ! ----- user_activeStats Start ----- ! ");
		$(By.linkText("액티브 사용자")).waitUntil(visible, 10000);
		$(By.linkText("액티브 사용자")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("액티브 사용자")) {
			System.out.println(" *** user_activeStats page load Success !! *** ");
		} else {
			System.out.println(" *** user_activeStats page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] chartDataCheck = {"2018.12.07(금)", "1일 사용자: 2", "7일 사용자: 2", "14일 사용자: 2", "30일 사용자: 2", "((date))", "((1day visit))", "((7day visit))", "((14day visit))", "((30day visit))"};
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-series-group", 1).hover();
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_activeStats accrue active user chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** user_activeStats accrue active user chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		chartDataCheck[1] = "1일 사용자: 0";
		$(".highcharts-series-group", 1).hover();
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-series-group", 1).hover();
		$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if (pLC[i].equals(chartDataCheck[i])) {
				System.out.println(" *** user_activeStats new active user chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** user_activeStats new active user chart tooltip data " + chartDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- user_activeStats End ----- ! ");
	}
	@Test(priority = 3)
	public void userStatsPercent() {
		System.out.println(" ! ----- userStatsPercent Start ----- ! ");
		$(By.linkText("유지율")).waitUntil(visible, 10000);
		$(By.linkText("유지율")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("유지율")) {
			System.out.println(" *** userStatsPercent page load Success !! *** ");
		} else {
			System.out.println(" *** userStatsPercent page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".another-class", 3).waitUntil(visible, 10000);
		pageLoadCheck = $(".another-class", 3).text().trim();
		if (pageLoadCheck.equals("2018.12.07 (금)")) {
			System.out.println(" *** userStatsPercent table text check Success !! *** ");
		} else {
			System.out.println(" *** userStatsPercent table text check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- userStatsPercent End ----- ! ");
	}
	@Test(priority = 4)
	public void userVisitFrequency() {
		System.out.println(" ! ----- userVisitFrequency Start ----- ! ");
		$(By.linkText("방문빈도")).waitUntil(visible, 10000);
		$(By.linkText("방문빈도")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("방문빈도")) {
			System.out.println(" *** userVisitFrequency page load Success !! *** ");
		} else {
			System.out.println(" *** userVisitFrequency page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] barChartDataCheck = {"당일", "방문수: 125", "방문당 페이지뷰: 9", "2~7일", "방문수: 2", "방문당 페이지뷰: 18", "((date))", "((visit number))", "((visit pageview number))"};
		for(int y=1;y>=0;y--){
			$(".highcharts-series-1.highcharts-tracker > path", y).waitUntil(visible, 10000);
			$(".highcharts-series-1.highcharts-tracker > path", y).hover();
			$(".highcharts-series-1.highcharts-tracker > path", y).hover();
			$(".highcharts-series-1.highcharts-tracker > path", y).hover();
			$(".highcharts-tooltip").waitUntil(visible, 10000);
			pageLoadCheck = $(".highcharts-tooltip").text().trim();
			String[] pLC = pageLoadCheck.split("● ");
			for(int i=0;i<=2;i++) {
				if(y==1){
					if (pLC[i].equals(barChartDataCheck[i])) {
						System.out.println(" *** userVisitFrequency bar chart today tooltip data " + barChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
					} else {
						System.out.println(" *** userVisitFrequency bar chart today tooltip data " + barChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
						close();
					}
				} else {
					if (pLC[i].equals(barChartDataCheck[i+3])) {
						System.out.println(" *** userVisitFrequency bar chart 2~7day tooltip data " + barChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
					} else {
						System.out.println(" *** userVisitFrequency bar chart 2~7day tooltip data " + barChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
						close();
					}
			    }
			}
			pLC = null;
	    }	
		$("#btnChartPie").click();
		String[] pieChartDataCheck = {"당일방문수: 98.4%", "2~7일방문수: 1.6%", "((today visit number))", "((2~7day visit number))", "((today convert number))", "((2~7day convert number))"};
		$(".highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
		for(int x=0;x<=1;x++) {
			if(x==0) {
				$(".highcharts-series-" + x + ".highcharts-tracker > path", 0).hover();	
			} else {
				$(".highcharts-series-" + x + ".highcharts-tracker > path", 2).hover();
				$(".highcharts-series-" + x + ".highcharts-tracker > path", 4).hover();
			}
			$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
			pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
			for(int i=0;i<=1;i++) {
				if (pageLoadCheck.equals(pieChartDataCheck[i])) {
					if(x==0) {
						System.out.println(" *** userVisitFrequency pie chart visit number tooltip data " + pieChartDataCheck[i+2] + "((" + i + "))" + " check Success !! *** ");
					} else {
						System.out.println(" *** userVisitFrequency pie chart convert number tooltip data " + pieChartDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
					}

				} else {
					if(x==0) {
						System.out.println(" *** userVisitFrequency pie chart visit number tooltip data " + pieChartDataCheck[i+2] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
						close();						
					} else {
						System.out.println(" *** userVisitFrequency pie chart convert number tooltip data " + pieChartDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
						System.out.println("." + pieChartDataCheck[i] + ".");
						System.out.println("." + pageLoadCheck + ".");
						close();
					}
				}
				if(x==0 && i==0) {
					$(".highcharts-series-" + x + ".highcharts-tracker > path", 3).waitUntil(visible, 10000);
					$(".highcharts-series-" + x + ".highcharts-tracker > path", 3).hover();
					$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
					pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
				} else if(x==1 && i==0) {
					$(".highcharts-series-" + x + ".highcharts-tracker > path", 5).waitUntil(visible, 10000);
					$(".highcharts-series-" + x + ".highcharts-tracker > path", 5).hover();
					$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
					pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();					
				}
			}
			pieChartDataCheck[0] = "당일전환수: 96.7%";
			pieChartDataCheck[1] = "2~7일전환수: 3.3%";
		}
		$("#btnChartLine").click();
		$(".highcharts-series-0.highcharts-tracker", 3).waitUntil(visible, 10000);
		$(".highcharts-series-0.highcharts-tracker", 3).hover();
		String[] lineChartDataCheck = {"2018.12.07(금)", "당일: 125", "1일: 0", "2~7일: 2", "8~15일: 0", "16~30일: 0", "1개월~3개월: 0", "3개월 이후: 0", "((date))", "((today))", "((1day))", "((2~7days))", "((8~15days))", "((16~30days))", "((1month~3month))", "((3month after this))"};
		$(".highcharts-tooltip", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 2).text();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=7;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** userVisitFrequency line chart visit number tooltip data " + lineChartDataCheck[i+8] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** userVisitFrequency line chart visit number tooltip data " + lineChartDataCheck[i+8] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		String[] tableDataCheck = {"당일", "125", "98.43%", "00:00:11", "9.13", "29", "23.20%", "290", "2.32", "((return visit interval))", "((visit number))", "((visit percent))", "((visit time))", "((visit pageview))", "((convert number))", "((convert parcent))", "((convert sales))", "((visit sales))"};
		for(int i=0,x=32;i<=8;i++,x++) {
			pageLoadCheck = $("td", x).text();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** userVisitFrequency table data " + tableDataCheck[i+9] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** userVisitFrequency table data " + tableDataCheck[i+9] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- userVisitFrequency End ----- ! ");
	}
	@Test(priority = 11)
	public void system() {
		System.out.println(" ! ----- system Start ----- ! ");
		$(By.linkText("시스템")).waitUntil(visible, 10000);
		$(By.linkText("시스템")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("시스템")) {
			System.out.println(" *** system page load Success !! *** ");
		} else {
			System.out.println(" *** system page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] pieChartDataCheck = {"기기타입PC", "웹브라우저firefox 62.0chrome 70.0", "운영체제windows 10"};
		$("svg", 0).waitUntil(visible, 10000);
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $("svg", i).text().trim();
			String[] pLC = pageLoadCheck.split("Created with Highcharts 4.2.5");
			if (pLC[0].equals(pieChartDataCheck[i])) {
				System.out.println(" *** system chart a legend(" + i + ") check Success !! *** ");
				pLC = null;
			} else {
				System.out.println(" *** system chart a legend(" + i + ") check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] barChartDataCheck = {"2018.12.07(금)", "방문수: 127", "방문당 페이지뷰: 9", "((date))", "((visit number))", "((visit pageview))"};
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		pageLoadCheck = $(".highcharts-tooltip", 3).text();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** system bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** system bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		/*for(int i=0;i<=20;i++) {
			if($("highcharts-legend-item", 6).text().trim().equals("PC")) {
				System.out.println(" *** system line chart load check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** system line chart loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** system line chart load check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}*/
		$(".highcharts-tracker", 6).waitUntil(visible, 10000);
		$(".highcharts-tracker", 6).hover();
		String[] lineChartDataCheck = {"2018.12.07(금)", "PC: 127", "((date))", "((system type))"};
		pageLoadCheck = $(".highcharts-tooltip", 4).text();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** system line chart data " + lineChartDataCheck[i+2] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** system line chart data " + lineChartDataCheck[i+2] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		String[] tableDataCheck = {"PC", "127", "100%", "2", "9.28", "00:00:12", "((equipment type))", "((visit number))", "((visit percent))", "((unique visit))", "((visit pageview))", "((visit stay time))"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("td", (i+17)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** system table data " + tableDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** system table data " + tableDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- system End ----- ! ");
	}
	@Test(priority = 21)
	public void region() {
		System.out.println(" ! ----- region Start ----- ! ");
		$(By.linkText("지역")).waitUntil(visible, 10000);
		$(By.linkText("지역")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("지역")) {
			System.out.println(" *** region page load Success !! *** ");
		} else {
			System.out.println(" *** region page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"알수없음", "127", "100%", "2", "9.28", "00:00:12", "((region))", "((visit number))", "((visit percent))", "((unique visit))", "((visit page view))", "((visit stay time))"};
		$("td", 13).waitUntil(visible, 10000);
		for(int i=0,x=13;i<=5;i++,x++) {
			pageLoadCheck = $("td", x).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** region table data " + tableDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** region table data " + tableDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- region End ----- ! ");
	}
	@Test(priority = 31)
	public void member_memberStatus() {
		System.out.println(" ! ----- member_memberStatus Start ----- ! ");
		$(By.linkText("회원")).waitUntil(visible, 10000);
		$(By.linkText("회원")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("회원")) {
			System.out.println(" *** member_memberStatus page load Success !! *** ");
		} else {
			System.out.println(" *** member_memberStatus page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] barChartDataCheck = {"2018.12.07(금)", "회원: 0", "비회원: 127", "방문수: 127", "((date))", "((member))", "((nonmember))", "((visit number))"};
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** member_memberStatus bar chart data " + barChartDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** member_memberStatus bar chart data " + barChartDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		String[] lineChartDataCheck = {"2018.12.07(금)", "회원: 0", "비회원: 127", "방문수: 127", "((date))", "((member))", "((nonmember))", "((visit number))"};
		$(".highcharts-tracker", 6).waitUntil(visible, 10000);
		$(".highcharts-tracker", 8).hover();
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 1).text();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** member_memberStatus line chart data " + lineChartDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** member_memberStatus line chart data " + lineChartDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"2018.12.07(금)", "127", "0", "127", "0", "0%", "0", "0", "0", "0%", "0", "0", "((date))", "((visit number))", "((member))", "((nonmember))", "((signin number))", "((signin percent))", "((login number))", "((cancel number))", "((member convert number))", "((member convert percent))", "((member convert sales))", "((visit sales))"};
		for(int i=19;i<=30;i++) {
			pageLoadCheck = $("td", i).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i-19])) {
				System.out.println(" *** member_memberStatus table data " + tableDataCheck[i-7] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** member_memberStatus table data " + tableDataCheck[i-7] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- member_memberStatus End ----- ! ");
	}
	@Test(priority = 32)
	public void member_memberFavorite() {
	    System.out.println(" ! ----- member_memberFavorite Start ----- ! ");
		$(By.linkText("회원 인기 페이지")).click();
		$("#btnTopCond").waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if(pageLoadCheck.equals("회원 인기 페이지")) {
			System.out.println(" *** member_memberFavorite page load check Success !! *** ");
		} else {
			System.out.println(" *** member_memberFavorite page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("td", 7).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** member_memberFavorite table data check Success !! *** ");
		} else {
			System.out.println(" *** member_memberFavorite table data check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- member_memberFavorite End ----- ! ");
	}
	@Test(priority = 41)
	public void userList() {
		System.out.println(" ! ----- userList Start ----- ! ");
		$(By.linkText("사용자리스트")).waitUntil(visible, 10000);
		$(By.linkText("사용자리스트")).click();
		$(".notokr-bold", 0).waitUntil(visible, 10000);
		$("td", 12).waitUntil(visible, 10000);
		pageLoadCheck = $(".notokr-bold", 0).text().trim();
		if (pageLoadCheck.equals("사용자리스트")) {
			System.out.println(" *** userList page load Success !! *** ");
		} else {
			System.out.println(" *** userList page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("td", 12).text().trim();
		if (pageLoadCheck.equals(nodata)) {
			System.out.println(" *** userList table data check Success !! *** ");
		} else {
			System.out.println(" *** user_member_status table data check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- userList End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}