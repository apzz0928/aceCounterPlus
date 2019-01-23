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

public class temporarily_2 {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, dateCheck;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
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
			System.out.println(" *** stats convert login Success !! *** ");
		} else {
			System.out.println(" *** stats convert login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}
	@Test(priority = 1)
	public void contents_popularPage() {
		System.out.println(" ! ----- contents_popularPage Start ----- ! ");
		$("#contents").click();
		$(By.linkText("페이지")).waitUntil(visible, 10000);
		$(By.linkText("페이지")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("페이지")) {
			System.out.println(" *** contents_popularPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_popularPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 125).waitUntil(visible, 30000);
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
		dateCheck = $(".pull-right", 3).text().trim();
		String[] pLC = dateCheck.split(" 어제");
		if (pLC[0].equals("오늘 순위를")) {
			System.out.println(" *** contents_popularPage date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** contents_popularPage date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"/", "79", "11.50%", "00:00:15", "00:00:00", "78", "78", "69", "68", "((page name))", "((page view))", "((percentage))", "((stay time))", "((visit stay time))", "((page visit number))", "((start number))", "((end number))", "((return number))"};
	    $("td", 36).waitUntil(visible, 10000);
		//td 36
		for(int i=0;i<=8;i++) {
			pageLoadCheck = $("td", (i+36)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** contents_popularPage table data " + tableDataCheck[i+9] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_popularPage table data " + tableDataCheck[i+9] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "페이지뷰: 687", "페이지 방문수: 665", "((date))", "((page view))", "((page visit number))"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** contents_popularPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_popularPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
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
		String[] lineChartDataCheck = {"2018.12.21(금)", "/: 79", "/search/label/missing/missingPage: 27", "/search/label/marketing-normal: 21", "/search/label/change-order: 21", "/search/label/inHouse-Talk/: 21", "((date))", "((page name : /))", "((page name : missingPage))", "((page name : marketing-normal))", "((page name : change-order))", "((page name : inHouse-Talk))"};
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** contents_popularPage line chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_popularPage line chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- contents_popularPage End ----- ! ");
	}
	//@Test(priority = 2)
	public void contents_groupPage() {
		System.out.println(" ! ----- contents_groupPage Start ----- ! ");
		$(By.linkText("페이지그룹")).waitUntil(visible, 10000);
		$(By.linkText("페이지그룹")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("페이지그룹")) {
			System.out.println(" *** contents_groupPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_groupPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"미등록페이지", "687", "100%", "366", "1.88", "((page group name))", "((page view))", "((percentage))", "((page group visit number))", "((visit page view))"};
		$("td", 14).waitUntil(visible, 10000);
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $("td", (i+14)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** contents_groupPage table data " + tableDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage table data " + tableDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pageLoadCheck = $("tspan", 0).text().trim();
		if(pageLoadCheck.equals("조회된 데이터가 없습니다.")) {
			System.out.println(" *** contents_groupPage line chart data check Success !! *** ");
		} else {
			System.out.println(" *** contents_groupPage line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		$("tspan", 1).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			pageLoadCheck = $("tspan", i).text().trim();
			if(pageLoadCheck.equals("조회된 데이터가 없습니다.")) {
				System.out.println(" *** contents_groupPage pie chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}	
		}
		System.out.println(" ! ----- contents_groupPage End ----- ! ");
	}
	//@Test(priority = 3)
	public void contents_InlinkPage() {
		System.out.println(" ! ----- contents_InlinkPage Start ----- ! ");
		$(By.linkText("내부배너")).waitUntil(visible, 10000);
		$(By.linkText("내부배너")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("내부배너")) {
			System.out.println(" *** contents_InlinkPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_InlinkPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"내부배너( banner-inner )\n9", "내부배너( banner-inner )\n0%", "-\n0", "((TOP page view))", "((TOP purchase rate))", "((TOP sales))"};
		$(".top-component", 0).waitUntil(visible, 10000);
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $(".top-component", (i)).text().trim();
			if(pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** contents_groupPage panel data " + panelDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage panel data " + panelDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("td", 21).click();
		$("td", 30).waitUntil(visible, 10000);
		String[] tableDataCheck = {"└ banner-inner", "9", "9", "100%", "((campaign))", "((page view))", "((page visit number))", "((percentage))"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("td", (i+30)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** contents_groupPage table data " + tableDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage table data " + tableDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "내부배너: 9", "합계: 9", "((date))", "((inner banner))", "((total))"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** contents_groupPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
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
		String[] lineChartDataCheck = {"2018.12.21(금)", "내부배너: 9", "((date))", "((inner banner))"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** contents_groupPage line chart data " + lineChartDataCheck[i+2] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_groupPage line chart data " + lineChartDataCheck[i+2] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- contents_InlinkPage End ----- ! ");
	}
	//@Test(priority = 4)
	public void contents_InternalPage() {
		System.out.println(" ! ----- contents_InternalPage Start ----- ! ");
		$(By.linkText("내부검색")).waitUntil(visible, 10000);
		$(By.linkText("내부검색")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("내부검색")) {
			System.out.println(" *** contents_InternalPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_InternalPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 29).waitUntil(visible, 10000);
		String[] tableDataCheck = {"합계", "149", "100%", "144", "((inner search word))", "((number of searches))", "((percentage))", "((number of leave after a search))"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("td", (i+29)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** contents_InternalPage table data " + tableDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_InternalPage table data " + tableDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21 (금)", "검색횟수: 149", "구매건수: 0", "((date))", "((number of searches))", "((number of purchases))"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** contents_InternalPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** contents_InternalPage bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		$(".highcharts-tracker > path", 6).waitUntil(visible, 10000);
		String[] pieChartDataCheck = {"8.1%", "8.1%", "8.1%", "8.1%", "8.1%", "기타검색횟수: 59.7%", "((naverbrand))", "((daumbrand))", "((change-order))", "((marketing-normal))", "((inhouse-email))", "(())"};
		for(int i=0;i<=5;i++) {
			$(".highcharts-tracker > path", (i+1)).hover();
			$(".highcharts-tracker > path", (i+1)).hover();
			$(".highcharts-tracker > path", (i+1)).hover();
			pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
			if(i<5) {
				if(pageLoadCheck.substring(pageLoadCheck.length()-4, pageLoadCheck.length()).equals(pieChartDataCheck[i])) {
					System.out.println(" *** contents_InternalPage pie chart data " + pieChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage pie chart data " + pieChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(pieChartDataCheck[i])) {
					System.out.println(" *** contents_InternalPage pie chart data " + pieChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage pie chart data " + pieChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			}	
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 11).waitUntil(visible, 10000);
		$(".highcharts-tracker", 9).hover();		
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 11).hover();
		pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21 (금)", "12", "12", "12", "12", "12", "((date))", "((chart index1))", "((chart index2))", "((chart index3))", "((chart index4))", "((chart index5))"};
		for(int i=0;i<=5;i++) {
			if(i == 0) {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** contents_InternalPage pie chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage pie chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].substring(pLC[i].length()-2, pLC[i].length()).equals(lineChartDataCheck[i].substring(lineChartDataCheck[i].length()-2, lineChartDataCheck[i].length()))) {
					System.out.println(" *** contents_InternalPage pie chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage pie chart data " + lineChartDataCheck[i+6] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- contents_InternalPage End ----- ! ");
	}
	@Test(priority = 11)
	public void contents_MoveRoute() {
		System.out.println(" ! ----- contents_MoveRoute Start ----- ! ");
		$(By.linkText("경로")).waitUntil(visible, 10000);
		$(By.linkText("경로")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("경로")) {
			System.out.println(" *** contents_popularPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_popularPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".blockUI", 2).waitUntil(hidden, 10000);
		String[] depthLevDataCheck = {"366", "356", "", "10", "0", "", "10", "0", "", "10", "0", "((start page visit number))", "((start page end number))", "", "((1step page visit number))", "((1step page end number))", "", "((2step page visit number))", "((2step page end number))", "", "((3step page visit number))", "((3step page end number))"};
		for(int i=0;i<=10;i++) {
			if((i+25)%3 > 0) {
				pageLoadCheck = $("text", (i+25)).text().trim();
				String[] pLC = pageLoadCheck.split(" : ");
				if(pLC[1].equals(depthLevDataCheck[i])) {
					System.out.println(" *** contents_InternalPage depth level data " + depthLevDataCheck[i+11] + "((" + i + "))" + " check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage depth level data " + depthLevDataCheck[i+11] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
					close();
				}
				pLC = null;
			}
		}
		String[] nodeVisitDataCheck = {"78 (21.31%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "240 (65.57%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)"};
		for(int i=0;i<=23;i++) {
			if(i%2 == 1) {
				pageLoadCheck = $("text", i).text().trim();
				String[] pLC = pageLoadCheck.split(" 수");
				if(pLC[1].equals(nodeVisitDataCheck[i])) {
					System.out.println(" *** contents_InternalPage node data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** contents_InternalPage node data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}		
		System.out.println(" ! ----- contents_MoveRoute End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}