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

public class convert {
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
	public void convert_status() {
		System.out.println(" ! ----- convert_status Start ----- ! ");
		$("#conv").click();
		$(By.linkText("전환")).waitUntil(visible, 10000);
		$(By.linkText("전환")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("전환")) {
			System.out.println(" *** convert_status page load Success !! *** ");
		} else {
			System.out.println(" *** convert_status page load Fail ... !@#$%^&*() *** ");
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
		$("#compareTermText").waitUntil(visible, 10000);
		dateCheck = $("#compareTermText").text();
		String[] pLC = dateCheck.split(" ");
		if (pLC[0].equals("2018.12.20") && pLC[2].equals("2018.12.20")) {
			System.out.println(" *** convert_status date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** convert_status date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".summary-data", 0).waitUntil(visible, 10000);
		String[] panelDataCheck = {"0", "0", "0", "21", "21", "((Number of purchases))", "((sales))", "((Number of member registrations))", "((convert-signup))", "((convert-apply))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** convert_status panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_status panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] tableDataCheck1 = {"/", "78", "87.18%", "9", "11.54%", "90", "1", "((landingpage-URL))", "((visit number))", "((return percent))", "((convert number))", "((convert percent))", "((convert sales))", "((visit sales))"};
		$("td", 46).waitUntil(visible, 10000);
		for(int i=0; i<=6; i++) {
			pageLoadCheck = $("td", (i+46)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck1[i])) {
				System.out.println(" *** convert_status table1 data " + tableDataCheck1[i+7] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_status table1 data " + tableDataCheck1[i+7] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck2 = {"1 페이지", "356", "12", "3.37%", "120", "0", "((visit depth))", "((visit number))", "((convert number))", "((convert percent))", "((conversion sales))", "((visit sales))"};
		$("td", 505).waitUntil(visible, 10000);
		for(int i=0; i<=5; i++) {
			pageLoadCheck = $("td", (i+505)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck2[i])) {
				System.out.println(" *** convert_status table2 data " + tableDataCheck2[i+6] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_status table2 data " + tableDataCheck2[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] pieChartDataCheck = {"/62.8%", "21 페이지 이상62.8%", "1 페이지37.2%", "당일93.0%", "2일7.0%", "((landingPage))", "((visit path depth))", "((convert time required))"};
		$(".highcharts-series-0 > path", 0).hover();
		$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		if (pageLoadCheck.equals(pieChartDataCheck[0])) {
			System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[5] + "((0))" + " check Success !! *** ");
		} else {
			System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[5] + "((0))" + " check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=1;i++) {
			$(".highcharts-series-0 > path", (i+10)).hover();
			$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
			pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
			if (pageLoadCheck.equals(pieChartDataCheck[(i+1)])) {
				System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[6] + "((" + (i+1) + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[6] + "((" + (i+1) + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=1;i++) {
			$(".highcharts-series-0 > path", (i+14)).hover();
			$(".highcharts-series-0 > path", (i+16)).hover();
			$(".highcharts-tooltip", 2).waitUntil(visible, 10000);
			pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
			if (pageLoadCheck.equals(pieChartDataCheck[(i+3)])) {
				System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[7] + "((" + (i+3) + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** convert_status pie chart tooltip data " + pieChartDataCheck[7] + "((" + (i+3) + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartBar").click();
		$(".highcharts-tracker", 6).waitUntil(visible, 10000);
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] barChartDataCheck = {"2018.12.21(금)", "전환-주문: 21", "전환-가입: 21", "전환-예약: 21", "전환-신청: 21", "전환-기타1: 9", "기타: 36", "합계: 129", "((date))", "((conv-order))", "((conv-signup))", "((conv-booking))", "((conv-apply))", "((conv-other))", "((other))", "((total))"};
		for(int i=0;i<=7;i++) {
			if(i>=1 && i<=5) {
				if(pLC[i].substring(pLC[i].length()-2, pLC[i].length()).equals(barChartDataCheck[i].substring(barChartDataCheck[i].length()-2, barChartDataCheck[i].length()))) {
					System.out.println(" *** convert_status bar chart data " + barChartDataCheck[i+8] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** convert_status bar chart data " + barChartDataCheck[i+8] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].equals(barChartDataCheck[i])) {
					System.out.println(" *** convert_status bar chart data " + barChartDataCheck[i+8] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** convert_status bar chart data " + barChartDataCheck[i+8] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		pLC = null;	
		$("#btnChartLine").click();
		String[] tableDataCheck3 = {"당일", "20", "95.24%", "200", "0", "((convert time required))", "((convert number))", "((convert percent))", "((conversion sales))", "((visit sales))"};
		$("td", 661).waitUntil(visible, 10000);
		for(int i=0; i<=4; i++) {
			pageLoadCheck = $("td", (i+661)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck3[i])) {
				System.out.println(" *** convert_status table3 data " + tableDataCheck3[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_status table3 data " + tableDataCheck3[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 8).waitUntil(visible, 10000);
		$(".highcharts-tracker", 10).hover();
		$(".highcharts-tracker", 8).hover();
		$(".highcharts-tracker", 10).hover();
		$(".highcharts-tracker", 8).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		String[] lineChartDataCheck = {"2018.12.21(금)", "전환-주문: 21", "전환-가입: 21", "전환-예약: 21", "전환-신청: 21", "전환-기타1: 9", "((date))", "((conv-order))", "((conv-signup))", "((conv-booking))", "((conv-apply))", "((conv-other))"};
		for(int i=0;i<=4;i++) {
			if(i>=1 && i<=5) {
				if(pLC[i].substring(pLC[i].length()-2, pLC[i].length()).equals(lineChartDataCheck[i].substring(lineChartDataCheck[i].length()-2, lineChartDataCheck[i].length()))) {
					System.out.println(" *** convert_status line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** convert_status line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}	
			} else {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** convert_status line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** convert_status line chart data " + lineChartDataCheck[i+6] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		pLC = null;
	    System.out.println(" ! ----- convert_status End ----- ! ");
	}
	@Test(priority = 2)
	public void convert_step() {
		System.out.println(" ! ----- convert_step Start ----- ! ");
		$(By.linkText("전환단계")).waitUntil(visible, 10000);
		$(By.linkText("전환단계")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("전환단계")) {
			System.out.println(" *** convert_step page load Success !! *** ");
		} else {
			System.out.println(" *** convert_step page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"자연유입", "270", "0", "270", "260", "96.30%", "0", "0", "0", "0", "0", "21", "21", "((inflow source))", "((visit number))", "((new visit number))", "((re visit number))", "((return number))", "((return percent))", 
				"((1step))", "((1~2stpe))", "((1~3step))", "((1~4step))", "((1~5step))", "((other))", "((total))"};
		$("td", 51).waitUntil(visible, 10000);
		for(int i=0; i<=12; i++) {
			pageLoadCheck = $("td", (i+51)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** convert_step table data " + tableDataCheck[i+13] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_step table data " + tableDataCheck[i+13] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 5).hover();
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 6).hover();
		String[] barChartDatacheck = {"2018.12.21(금)", "1단계: 0", "1~2단계: 0", "1~3단계: 0", "1~4단계: 0", "1~5단계: 0", "기타: 21", "합계: 21", "((date))", "((1step))", "((1~2step))", "((1~3step))", "((1~4step))", "((1~5step))", "((other))", "((total))"};
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=7;i++) {
			if (pLC[i].equals(barChartDatacheck[i])) {
				System.out.println(" *** convert_step bar chart data " + barChartDatacheck[i+8] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** convert_step bar chart data " + barChartDatacheck[i+8] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
	    System.out.println(" ! ----- convert_step End ----- ! ");
	}
	@Test(priority = 11)
	public void multiChannelConvert_indirect() {
		System.out.println(" ! ----- multiChannelConvert_indirect Start ----- ! ");
		$(By.linkText("멀티채널 전환")).waitUntil(visible, 10000);
		$(By.linkText("멀티채널 전환")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("멀티채널 전환")) {
			System.out.println(" *** multiChannelConvert_indirect page load Success !! *** ");
		} else {
			System.out.println(" *** multiChannelConvert_indirect page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"합계", "48", "100%", "48", "100%", "((advertisement product))", "((visit number))", "((visit percent))", "((re visit number))", "((re visit percent))"};
		$("td", 51).waitUntil(visible, 10000);
		for(int i=0; i<=4; i++) {
			pageLoadCheck = $("td", (i+17)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** multiChannelConvert_indirect table data " + tableDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** multiChannelConvert_indirect table data " + tableDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#divChartBar").hover();
		$("#divChartBar").hover();
		String[] barChartDatacheck = {"2018-12-21(금)", "직접전환 구매건수: 0", "간접전환 구매건수: 0", "((date))", "((directly convert))", "((indirect convert))"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if (pLC[i].equals(barChartDatacheck[i])) {
				System.out.println(" *** multiChannelConvert_indirect bar chart data " + barChartDatacheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** multiChannelConvert_indirect bar chart data " + barChartDatacheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$("tspan", 1).waitUntil(hidden, 10000);
		$("tspan", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan", 0).text();
		pLC = pageLoadCheck.split("\\.");
		if(pLC[0].equals("조회된 데이터가 없습니다")) {
			System.out.println(" *** multiChannelConvert_indirect line chart data check Success !! *** ");
		} else {
			System.out.println(" *** multiChannelConvert_indirect line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- multiChannelConvert_indirect End ----- ! ");
	}
	@Test(priority = 12)
	public void multiChannelConvert_weighted() {
		System.out.println(" ! ----- multiChannelConvert_weighted Start ----- ! ");
		$(By.linkText("가중치")).waitUntil(visible, 10000);
		$(By.linkText("가중치")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("가중치")) {
			System.out.println(" *** multiChannelConvert_weighted page load Success !! *** ");
		} else {
			System.out.println(" *** multiChannelConvert_weighted page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 22).waitUntil(visible, 10000);
		$("td", 22).click();
		for(int i=0;i<=9;i++) {
			pageLoadCheck = $("td", 32).text().trim();
			if(pageLoadCheck.equals("└ 다이렉트")) {
				System.out.println(" *** multiChannelConvert_weighted table drill down data load check Success *** ");
				break;
			} else {
				System.out.println(" *** multiChannelConvert_weighted table drill down data loading wait 0." + i + " second *** ");
				sleep(100);
			}
		}
		String[] tableDataCheck = {"└ 다이렉트", "208", "56.83%", "((inflow source))", "((visit number))", "((visit percent))"};
		for(int i=0; i<=2; i++) {
			pageLoadCheck = $("td", (i+32)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** multiChannelConvert_weighted table data " + tableDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** multiChannelConvert_weighted table data " + tableDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=1;i++) {
			if(i==1) {
				i=2;
			}
			pageLoadCheck = $("tspan", i).text().trim();
			if (pageLoadCheck.equals(nodata)) {
				System.out.println(" *** multiChannelConvert_weighted pie chart data ((" + (i/2) + ")) check Success !! *** ");
			} else {
				System.out.println(" *** multiChannelConvert_weighted pie chart data ((" + (i/2) + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartLine").click();
		$(".highcharts-tracker", 4).waitUntil(visible, 10000);
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 6).hover();
		$(".highcharts-tracker", 4).hover();
		String[] lineChartDataCheck = {"2018-12-21(금)", "0", "0", "0", "0", "((date))", "((normal marketing))", "((inhouse marketing))", "((charged marketing))", "((nature marketing))"};
		pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=4;i++) {
			if(i==0) {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** multiChannelConvert_weighted line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** multiChannelConvert_weighted line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].substring(pLC[i].length()-1, pLC[i].length()).equals(lineChartDataCheck[i])) {
					System.out.println(" *** multiChannelConvert_weighted line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** multiChannelConvert_weighted line chart data " + lineChartDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}		
	    System.out.println(" ! ----- multiChannelConvert_weighted End ----- ! ");
	}
	@Test(priority = 13)
	public void multiChannelConvert_path() {
		System.out.println(" ! ----- multiChannelConvert_path Start ----- ! ");
		$(By.linkText("경로")).waitUntil(visible, 10000);
		$(By.linkText("경로")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("경로")) {
			System.out.println(" *** multiChannelConvert_path page load Success !! *** ");
		} else {
			System.out.println(" *** multiChannelConvert_path page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 6).waitUntil(visible, 10000);
		for(int i=0;i<=7;i++) {
			pageLoadCheck = $("td", i+6).text().trim();
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** multiChannelConvert_path table data((" + (i/7) + ")) check Success !! *** ");
			} else {
				System.out.println(" *** multiChannelConvert_path table data((" + (i/7) + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
			i = i+6;
		}
	    System.out.println(" ! ----- multiChannelConvert_path End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}