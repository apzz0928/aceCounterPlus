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
	public void product_popularity() {
		System.out.println(" ! ----- product_popularity Start ----- ! ");
		$("#commerce").click();
		$(By.linkText("제품")).waitUntil(visible, 10000);
		$(By.linkText("제품")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("제품")) {
			System.out.println(" *** product_popularity page load Success !! *** ");
		} else {
			System.out.println(" *** product_popularity page load Fail ... !@#$%^&*() *** ");
			close();
		}
		//날짜선택 (2018.12.21)
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
		$("td", 10).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			if($("td", i).text().trim().equals(nodata.substring(nodata.length()-10, nodata.length()))) {
				System.out.println(" *** product_popularity ranking table data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity ranking table data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=3;i++) {
			System.out.println(i + "을(를) 1로 나누면 몫은" + i/1);
			System.out.println(i + "을(를) 1로 나누면 나머지는" + i%1);
			System.out.println(i + "을(를) 2로 나누면 몫은" + i/2);
			System.out.println(i + "을(를) 2로 나누면 나머지는" + i%2);
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 0) {
				if($("tspan", i).text().trim().equals(nodata)) {
					System.out.println(" *** product_popularity pie chart data check Success !! *** ");
				} else {
					System.out.println(" *** product_popularity pie chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		$("#btnChartLine").click();
		$("tspan", 2).waitUntil(hidden, 10000);
		if($("tspan", 0).text().trim().equals(nodata)) {
			System.out.println(" *** product_popularity line chart data check Success !! *** ");
		} else {
			System.out.println(" *** product_popularity line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=1;i++) {
			if($("td", i+9).text().trim().equals(nodata)) {
				System.out.println(" *** product_popularity product table data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity product table data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=1;i++) {
			if($("tspan", i+5).text().trim().equals(nodata)) {
				System.out.println(" *** product_popularity purchase chart data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity purchase chart data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- product_popularity End ----- ! ");
	}
	@Test(priority = 2)
	public void product_basket() {
		System.out.println(" ! ----- product_basket Start ----- ! ");
		$(By.linkText("장바구니")).waitUntil(visible, 10000);
		$(By.linkText("장바구니")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("장바구니")) {
			System.out.println(" *** product_basket page load Success !! *** ");
		} else {
			System.out.println(" *** product_basket page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		String[] panelDatacheck = {"0", "0", "0", "0%", "0%"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if(pageLoadCheck.equals(panelDatacheck[i])) {
				System.out.println(" *** product_basket panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** product_basket panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		
		if($("td", 7).text().trim().equals(nodata)) {
			System.out.println(" *** product_basket table data check Success !! *** ");
		} else {
			System.out.println(" *** product_basket table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 0) {
				if($("tspan", i).text().trim().equals(nodata)) {
					System.out.println(" *** product_basket bar chart data check Success !! *** ");
				} else {
					System.out.println(" *** product_basket bar chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- product_basket End ----- ! ");
	}
	@Test(priority = 3)
	public void product_postScript() {
		System.out.println(" ! ----- product_postScript Start ----- ! ");
		$(By.linkText("후기")).waitUntil(visible, 10000);
		$(By.linkText("후기")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("후기")) {
			System.out.println(" *** product_postScript page load Success !! *** ");
		} else {
			System.out.println(" *** product_postScript page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		if($("td", 7).text().trim().equals(nodata)) {
			System.out.println(" *** product_postScript table data check Success !! *** ");
		} else {
			System.out.println(" *** product_postScript table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("path", 1).hover();
		$("path", 2).hover();
		$("path", 3).hover();
		$("path", 2).hover();
		String[] reviewChartDataCheck = {"2018-12-21(금)불만족:", "0%미흡:", "0%보통:", " 0%만족:", " 0%매우만족:", " 0%"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(reviewChartDataCheck[i])) {
				System.out.println(" *** product_postScript review chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** product_postScript review chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 1) {
				if($("tspan", i+11).text().trim().equals(nodata)) {
					System.out.println(" *** product_postScript TOP5 chart data check Success !! *** ");
				} else {
					System.out.println(" *** product_postScript TOP5 chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- product_postScript End ----- ! ");
	}
	@Test(priority = 11)
	public void correlationProduct() {
		System.out.println(" ! ----- correlationProduct Start ----- ! ");
		$(By.linkText("연관제품")).waitUntil(visible, 10000);
		$(By.linkText("연관제품")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("연관제품")) {
			System.out.println(" *** correlationProduct page load Success !! *** ");
		} else {
			System.out.println(" *** correlationProduct page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 6).waitUntil(visible, 10000);
		String[] tableDataCheck = {"제품명", "페이지뷰", "장바구니", "구매수량", "구매건수", "제품금액", "인기연령", "인기성별"};
		for(int i=0;i<=7;i++) {
			pageLoadCheck = $(".th-inner", i).text().trim();
			if(i==0) {
				if(pageLoadCheck.equals(tableDataCheck[i])) {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.substring(0, 4).equals(tableDataCheck[i])) {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();	
				}
			}			
		}
		if($("td", 6).text().trim().equals(nodata)) {
			System.out.println(" *** correlationProduct table data check Success !! *** ");
		} else {
			System.out.println(" *** correlationProduct table data check Fail ... !@#$%^&*() *** ");
			close();	
		}
		System.out.println(" ! ----- correlationProduct End ----- ! ");
	}
	@Test(priority = 21)
	public void potentialGuest() {
		$(By.linkText("잠재고객")).waitUntil(visible, 10000);
		$(By.linkText("잠재고객")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("잠재고객")) {
			System.out.println(" *** potentialGuest page load Success !! *** ");
		} else {
			System.out.println(" *** potentialGuest page load Fail ... !@#$%^&*() *** ");
			close();
		}
		
		
		
		
		
		
		
		
		
		$("td", 25).waitUntil(visible, 10000);
		$("td", 25).click();
		String[] panelDatacheck = {"12", "100%", "1", "0", "0", "((visit number))", "((return percent))", "((visit pageview))", "((purchases number))", "((sales))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if(pageLoadCheck.equals(panelDatacheck[i])) {
				System.out.println(" *** potentialGuest panel data " + panelDatacheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest panel data " + panelDatacheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"└ inhouse-potentialGuest", "2018-12-03", "5", "12", "100%", "240%", "100%", "1", "((campaign))", "((send date))", "((send number))"
				, "((visit number))", "((visit percent))", "((inflow percent))", "((return percent))", "((visit pageview))"};
		for(int i=0;i<=7;i++) {
			pageLoadCheck = $("td", i+38).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** potentialGuest table data " + tableDataCheck[i+8] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest table data " + tableDataCheck[i+8] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		String[] barChartDataCheck = {"2018.12.21(금)", "인하우스 - potentialGuest.: 12", "합계: 12", "((date))", "((inhouse - potentialGuest))", "((total))"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** potentialGuest bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		for(int i=0;i<=20;i++) {
			if($("text", 8).text().trim().equals("인하우스 - potentialGuest.")) {
				System.out.println(" *** potentialGuest line chart data loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** potentialGuest line chart loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** potentialGuest line chart data loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 3).hover();
		String[] lineChartDataCheck = {"2018.12.21(금)", "인하우스 - potentialGuest.: 12", "((date))", "((inhouse-potentialGuest))"};
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** potentialGuest line chart data " + lineChartDataCheck[i+2] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest line chart data " + lineChartDataCheck[i+2] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- potentialGuest End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}