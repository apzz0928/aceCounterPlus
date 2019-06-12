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

public class content {
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
			//driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
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
			System.out.println(" *** stats convert login Success !! *** ");
		} else {
			System.out.println(" *** stats convert login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}
	@Test(priority = 1)
	public void page_popularPage() {
		System.out.println(" ! ----- page_popularPage Start ----- ! ");
		$("#contents").click();
		$(By.linkText("페이지")).waitUntil(visible, 10000);
		$(By.linkText("페이지")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("페이지")) {
			System.out.println(" *** page_popularPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_popularPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 98).waitUntil(visible, 30000);
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
					$("td[data-title=r3c5]", i).click(); //21일 선택
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
		$(".pull-right", 3).waitUntil(visible, 10000);
		dateCheck = $(".pull-right", 2).text().trim();
		String[] pLC = dateCheck.split(" 어제");
		if (pLC[0].equals("오늘 순위를")) {
			System.out.println(" *** page_popularPage date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** page_popularPage date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"/", "79", "11.50%", "00:00:15", "00:00:00", "78", "78", "69", "68"};
	    $("td", 36).waitUntil(visible, 10000);
		//td 36
		for(int i=0;i<=8;i++) {
			pageLoadCheck = $("td", (i+36)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_popularPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_popularPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "페이지뷰: 687", "페이지 방문수: 665"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_popularPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_popularPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 9).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 5).hover();
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 9).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();		
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21(금)", "/: 79", "/search/label/missing/missingPage: 27", "/search/label/marketing-normal: 21", "/search/label/change-order: 21", "/search/label/inHouse-Talk/: 21"};
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** page_popularPage line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_popularPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- page_popularPage End ----- ! ");
	}
	@Test(priority = 2)
	public void page_pageGroup() {
		System.out.println(" ! ----- page_pageGroup Start ----- ! ");
		$(By.linkText("페이지그룹")).waitUntil(visible, 10000);
		$(By.linkText("페이지그룹")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("페이지그룹")) {
			System.out.println(" *** page_pageGroup page load Success !! *** ");
		} else {
			System.out.println(" *** page_pageGroup page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"미등록페이지", "687", "100%", "366", "1.88"};
		$("td", 14).waitUntil(visible, 10000);
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $("td", (i+14)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_pageGroup table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_pageGroup table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pageLoadCheck = $("tspan", 0).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** page_pageGroup line chart data check Success !! *** ");
		} else {
			System.out.println(" *** page_pageGroup line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		$("tspan", 1).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			pageLoadCheck = $("tspan", i).text().trim();
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** page_pageGroup pie chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_pageGroup pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}	
		}
		System.out.println(" ! ----- page_pageGroup End ----- ! ");
	}
	@Test(priority = 3)
	public void page_inlinkPage() {
		System.out.println(" ! ----- page_inlinkPage Start ----- ! ");
		$(By.linkText("내부배너")).waitUntil(visible, 10000);
		$(By.linkText("내부배너")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("내부배너")) {
			System.out.println(" *** page_inlinkPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_inlinkPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"내부배너( banner-inner )\n9", "내부배너( banner-inner )\n0%", "-\n0"};
		$(".top-component", 0).waitUntil(visible, 10000);
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $(".top-component", (i)).text().trim();
			if(pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** page_page_inlinkPage panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_inlinkPage panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("td", 21).click();
		$("td", 30).waitUntil(visible, 10000);
		String[] tableDataCheck = {"└ banner-inner", "9", "9", "100%"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("td", (i+30)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_page_inlinkPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_inlinkPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "내부배너: 9", "합계: 9"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_page_inlinkPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_inlinkPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 3).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();		
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 3).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21(금)", "내부배너: 9"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** page_page_inlinkPage line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_inlinkPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- page_page_inlinkPage End ----- ! ");
	}
	@Test(priority = 4)
	public void page_internalPage() {
		System.out.println(" ! ----- page_internalPage Start ----- ! ");
		$(By.linkText("내부검색")).waitUntil(visible, 10000);
		$(By.linkText("내부검색")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("내부검색")) {
			System.out.println(" *** page_internalPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_internalPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("tr.text-nowrap.another-class > td", 0).waitUntil(visible, 10000);
		String[] tableDataCheck = {"합계", "149", "100%", "144"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("tr.text-nowrap.another-class > td", i).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_internalPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_internalPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21 (금)", "검색횟수: 149", "구매건수: 0"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_internalPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_internalPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		$(".highcharts-tracker > path", 6).waitUntil(visible, 10000);
		String[] pieChartDataCheck = {"8.1%", "8.1%", "8.1%", "8.1%", "8.1%", "기타검색횟수: 59.7%"};
		for(int i=1;i<=6;i++) {
			js("$('.highcharts-tracker > path').eq(" + i + ").mouseover()");
			js("$('.highcharts-tracker > path').eq(" + i + ").mouseover()");
			pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
			if(i<6) {
				if(pageLoadCheck.substring(pageLoadCheck.length()-4, pageLoadCheck.length()).equals(pieChartDataCheck[i-1])) {
					System.out.println(" *** page_internalPage pie chart data ((" + (i-1) + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_internalPage pie chart data ((" + (i-1) + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(pieChartDataCheck[i-1])) {
					System.out.println(" *** page_internalPage pie chart data ((" + (i-1) + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_internalPage pie chart data ((" + (i-1) + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
			System.out.println("for문 " + i + " 번째 도는중");
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 11).waitUntil(visible, 10000);
		$(".highcharts-tracker", 9).hover();		
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 11).hover();
		pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21 (금)", "12", "12", "12", "12", "12"};
		for(int i=0;i<=5;i++) {
			if(i == 0) {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** page_internalPage line chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_internalPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].substring(pLC[i].length()-2, pLC[i].length()).equals(lineChartDataCheck[i].substring(lineChartDataCheck[i].length()-2, lineChartDataCheck[i].length()))) {
					System.out.println(" *** page_internalPage line chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_internalPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- page_internalPage End ----- ! ");
	}
	@Test(priority = 11)
	public void route_contents_moveRoute() {
		System.out.println(" ! ----- path_movePath Start ----- ! ");
		$(By.linkText("경로")).waitUntil(visible, 10000);
		$(By.linkText("경로")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("경로")) {
			System.out.println(" *** path_movePath page load Success !! *** ");
		} else {
			System.out.println(" *** path_movePath page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".blockUI", 2).waitUntil(hidden, 10000);
		String[] depthLevDataCheck = {"366", "356", "", "10", "0", "", "10", "0", "", "10", "0"};
		for(int i=0;i<=10;i++) {
			if((i+25)%3 > 0) {
				pageLoadCheck = $("text", (i+25)).text().trim();
				String[] pLC = pageLoadCheck.split(" : ");
				if(pLC[1].equals(depthLevDataCheck[i])) {
					System.out.println(" *** path_movePath depth level data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** path_movePath depth level data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
				pLC = null;
			}
		}
		String[] nodeVisitDataCheck = {"78 (21.31%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "240 (65.57%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)"};
		for(int i=0,x=0;i<=23;i++) {
			if(i%2 == 1) {
				pageLoadCheck = $("text", i).text().trim();
				String[] pLC = pageLoadCheck.split("수 ");
				if(pLC[1].equals(nodeVisitDataCheck[(x)])) {
					System.out.println(" *** path_movePath node data ((" + x + ")) check Success !! *** ");
				} else {
					System.out.println(" *** path_movePath node data ((" + x + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
				x++;
				pLC = null;
			}
		}		
		System.out.println(" ! ----- path_movePath End ----- ! ");
	}
	@Test(priority = 12)
	public void path_preNextPage() {
		System.out.println(" ! ----- path_preNextPage Start ----- ! ");
		$(By.linkText("이전/다음 페이지")).waitUntil(visible, 10000);
		$(By.linkText("이전/다음 페이지")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("이전/다음 페이지")) {
			System.out.println(" *** path_preNextPage page load Success !! *** ");
		} else {
			System.out.println(" *** path_preNextPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#infoTitle_0").waitUntil(visible, 10000);
		String[] prePageDataCheck = {"방문시작", "12", "/", "9"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("text", i).text().trim();
			if(pageLoadCheck.equals(prePageDataCheck[i])) {
				System.out.println(" *** path_preNextPage pre page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** path_preNextPage pre page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] nextPageDataCheck = {"/search/label/marketing...", "9", "방문종료", "12"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("text", (i+7)).text().trim();
			if(pageLoadCheck.equals(nextPageDataCheck[i])) {
				System.out.println(" *** path_preNextPage next page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** path_preNextPage next page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}		
		String[] standardPageDataCheck = {"페이지 방문수21", "페이지뷰21", "방문당 페이지뷰1", "내부이동수9", "종료수12", "종료율57.14%"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("#infoTitle_" + i).text().trim();
			if(pageLoadCheck.equals(standardPageDataCheck[i])) {
				System.out.println(" *** path_preNextPage standard page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** path_preNextPage standard page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- path_preNextPage End ----- ! ");
	}
	@Test(priority = 13)
	public void path_scenario() {
		System.out.println(" ! ----- path_scenario Start ----- ! ");
		$(By.linkText("시나리오")).waitUntil(visible, 10000);
		$(By.linkText("시나리오")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("시나리오")) {
			System.out.println(" *** path_scenario page load Success !! *** ");
		} else {
			System.out.println(" *** path_scenario page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("h2").text().trim();
		if(pageLoadCheck.equals("달성률0.0%")) {
			System.out.println(" *** path_scenario page check Success !! *** ");
		} else {
			System.out.println(" *** path_scenario page check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- path_scenario End ----- ! ");
	}
	@Test(priority = 21)
	public void link_event() {
		System.out.println(" ! ----- link_event Start ----- ! ");
		$(By.linkText("링크")).waitUntil(visible, 10000);
		$(By.linkText("링크")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("링크")) {
			System.out.println(" *** link_event page load Success !! *** ");
		} else {
			System.out.println(" *** link_event page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("td", 13).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_event table chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_event table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-tracker", 1).waitUntil(visible, 10000);
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "순클릭: 0", "클릭수: 0"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_event bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_event bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$("tspan", 7).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan", 7).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_event line chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_event line data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		$("tspan", 9).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			pageLoadCheck = $("tspan", (i+8)).text().trim();
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** link_event pie chart data check Success !! *** ");
			} else {
				System.out.println(" *** link_event pie chart data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- link_event End ----- ! ");
	}
	@Test(priority = 22)
	public void link_share() {
		System.out.println(" ! ----- link_share Start ----- ! ");
		$(By.linkText("공유")).waitUntil(visible, 10000);
		$(By.linkText("공유")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("공유")) {
			System.out.println(" *** link_share page load Success !! *** ");
		} else {
			System.out.println(" *** link_share page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 7).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_share table data check Success !! *** ");
		} else {
			System.out.println(" *** link_share table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] pLC = {"", ""};
		pLC[0] = $(".highcharts-axis-labels.highcharts-xaxis-labels > text").text().trim();
		pLC[1] = $(".highcharts-legend-item > text").text().trim();
		String[] barChartDataCheck = {"2018-12-21(금)", "공유클릭수"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_share bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_share bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		pageLoadCheck = $("tspan").text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_share pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_share pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartLine").click();
		$("tspan").waitUntil(visible, 10000);
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_share line chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_share line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- link_share End ----- ! ");
	}
	@Test(priority = 23)
	public void link_outlinkBanner() {
		System.out.println(" ! ----- link_outlinkBanner Start ----- ! ");
		$(By.linkText("아웃링크배너")).waitUntil(visible, 10000);
		$(By.linkText("아웃링크배너")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("아웃링크배너")) {
			System.out.println(" *** link_outlinkBanner page load Success !! *** ");
		} else {
			System.out.println(" *** link_outlinkBanner page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"노출수\n0\n-", "클릭수\n9\n9(0.00%)", "순클릭\n9\n9(0.00%)"};
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $(".panel-body", i).text().trim();
			if(pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** link_outlinkBanner panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_outlinkBanner panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}		
		$("td", 27).waitUntil(visible, 10000);
		$("td", 27).click();
		$("td", 34).waitUntil(visible, 10000);
		String[] tableDataCheck = {"└ 아웃링크배너.", "0", "9", "0%", "9", "0%"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("td", (i+34)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** link_outlinkBanner table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_outlinkBanner table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();		
		$(".highcharts-tracker", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "노출수: 0", "클릭수: 9"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_outlinkBanner bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_outlinkBanner bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 3).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 3).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21(금)", "아웃링크배너.: 0"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** link_outlinkBanner line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_outlinkBanner line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartPie").click();		
		$("tspan", 11).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan", 11).text().trim();
		if(pageLoadCheck.equals(nodata) ) {
			System.out.println(" *** link_outlinkBanner left pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_outlinkBanner left pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 8).hover();
		$(".highcharts-tracker", 7).hover();
		pageLoadCheck = $(".highcharts-tooltip", 3).text().trim();
		if(pageLoadCheck.equals("아웃링크배너.클릭수: 100.0%")) {
			System.out.println(" *** link_outlinkBanner right pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_outlinkBanner right pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- link_outlinkBanner End ----- ! ");
	}
	@Test(priority = 31)
	public void perfornamce_pageSpeed() {
		System.out.println(" ! ----- perfornamce_pageSpeed Start ----- ! ");
		$(By.linkText("성능")).waitUntil(visible, 10000);
		$(By.linkText("성능")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("성능")) {
			System.out.println(" *** perfornamce_pageSpeed page load Success !! *** ");
		} else {
			System.out.println(" *** perfornamce_pageSpeed page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $(".th-inner", 6).text().trim();
		for(int i=0;i<=20;i++) {
			if(pageLoadCheck.equals("페이지 방문수")) {
				System.out.println(" *** perfornamce_pageSpeed table data loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** perfornamce_pageSpeed table data loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** perfornamce_pageSpeed table data loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".th-inner", 6).click();
		for(int i=0;i<=19;i++) { //로딩 체크용이 아니고 데이터 소팅 체크용이라 다름
			if($("td", 17).text().trim().equals("조회된 데이터가 없습니다.")) {
				System.out.println(" *** perfornamce_pageSpeed table data sorting wait 0." + i + " second *** ");
				sleep(100);
			}
		}
		String[] tableDataCheck = {"/", "78", "0", "0", "69", "88.46%", "((page name))", "((page visit number))", "((page loading time))", "((refresh number))", "((leave number))", "((leave percent))"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("td", i+23).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** perfornamce_pageSpeed table data " + tableDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** perfornamce_pageSpeed table data " + tableDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		String[] barChartDataCheck = {"2018-12-21(금)", "평균 페이지 로딩시간(초): 0", "새로고침수: 0", "((date))", "((page loading time))", "((refresh number))"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** perfornamce_pageSpeed bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** perfornamce_pageSpeed bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$("#lineChartSelect").waitUntil(visible, 10000);
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tracker", 8).hover();
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tracker", 8).hover();
		String[] lineChartDataCheck = {"2018-12-21(금)", "0", "0", "0", "0", "0", "((date))", "((line chart data1))", "((line chart data2))", "((line chart data3))", "((line chart data4))", "((line chart data5))"};
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=pLC.length-1;i++) {
			System.out.println("pLC[" + i + "] is " + pLC[i]);
		}
		for(int i=0;i<=5;i++) {
			if(i==0) {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** perfornamce_pageSpeed line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** perfornamce_pageSpeed line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].substring(pLC[i].length()-1, pLC[i].length()).equals(lineChartDataCheck[i])) {
					System.out.println(" *** perfornamce_pageSpeed line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** perfornamce_pageSpeed line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- perfornamce_pageSpeed End ----- ! ");
	}
	@Test(priority = 32)
	public void perfornamce_errorPage() {
		System.out.println(" ! ----- perfornamce_errorPage Start ----- ! ");
		$(By.linkText("에러페이지")).waitUntil(visible, 10000);
		$(By.linkText("에러페이지")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("에러페이지")) {
			System.out.println(" *** perfornamce_errorPage page load Success !! *** ");
		} else {
			System.out.println(" *** perfornamce_errorPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("td", 13).text().trim();
		for(int i=0;i<=20;i++) {
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** perfornamce_errorPage table data check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** perfornamce_errorPage table data loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** perfornamce_errorPage table data check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		String[] barChartDataCheck = {"2018-12-21(금)", "에러횟수: 0", "페이지뷰: 0", "((date))", "((error number))", "((pageview))"};
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** perfornamce_errorPage bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** perfornamce_errorPage bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartLine").click();
		$("tspan").waitUntil(visible, 10000);
		pageLoadCheck = $("tspan").text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** perfornamce_errorPage line chart data check Success !! *** ");
		} else {
			System.out.println(" *** perfornamce_errorPage line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- perfornamce_errorPage End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}