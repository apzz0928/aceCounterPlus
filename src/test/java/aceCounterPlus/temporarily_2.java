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

/*import com.codeborne.selenide.testng.ScreenShooter;*/

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
	public void marketing_summary() {
		System.out.println(" ! ----- marketing_summary Start ----- ! ");
		$("#marketing").click();
		$(By.linkText("마케팅")).waitUntil(visible, 10000);
		$(By.linkText("마케팅")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("마케팅")) {
			System.out.println(" *** marketing_summary page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_summary page load Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		//날짜선택
		dateCheck = $(".month", 1).text().trim(); //1번째 달력 월 확인
		for(int i=0;i<=1;i++) {//과거 날짜 : 시작캘린더부터 선택(0과 1 변경, 부등호 변경, ++와 --변경)
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
				dateCheck = $(".month", 0).text().trim(); //2번째 달력 월 확인
			}
			for(int x=0;x<=100;x++) { //2018.12가 될때까지 >> 클릭
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r3c3]", i).click(); //19일 선택
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
		if (pLC[0].equals("2018.12.18") && pLC[2].equals("2018.12.18")) {
			System.out.println(" *** marketing_summary date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** marketing_summary date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".summary-data", 0).waitUntil(visible, 10000);
		$("td", 81).waitUntil(visible, 10000);
		String[] panelDataCheck = {"0", "0", "0", "0", "0", "((CTR))", "((sales))", "((advertising cost))", "((CPA))", "((ROAS))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** marketing_summary panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] tableDataCheck = {"네이버브랜드검색", "13", "12", "92.31%", "0", "0", "0", "0%", "0", "0", "0%", "0", "0%", "0", "0", "((media))", "((visit number))"
				, "((return number))", "((return percent))", "((reach number))", "((click number))", "((advertising cost))", "((CTR))", "((CPC))", "((CPA))", "((ROAS))", "((convert number))", "((convert parcent))", "((convert sales))", "((visit sales))"};
		$("td", 37).waitUntil(visible, 10000);
		for(int i=0;i<=14;i++) {
			pageLoadCheck = $("td", (i+37)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** marketing_summary table data " + tableDataCheck[i+15] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary table data " + tableDataCheck[i+15] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] barChartDataCheck = {"2018.12.19(수)", "다음브랜드검색: 19", "네이버브랜드검색: 13", "((daily publication))", "((daum brand search))", "((naver brand search))"}; 
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if (pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** marketing_summary bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		$("#btnChartPie").click();
		String[] pieChartDataCheck = {"네이버브랜드검색방문수: 23.2%", "다음브랜드검색방문수: 33.9%", "카카오톡방문수: 21.4%", "구글애드워즈방문수: 21.4%", "((naver brand search))", "((daum brand search))", "((kakaotalk))", "((google adwords))"};
		$(".highcharts-series-0.highcharts-tracker > path", 7).waitUntil(visible, 10000);
		for(int i=0;i<=3;i++) {
			$(".highcharts-series-0.highcharts-tracker > path", i).hover();
			$(".highcharts-series-0.highcharts-tracker > path", i).hover();
			pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
			/*System.out.println("pageLoadCheck is :" + pageLoadCheck + ".");*/
			if(pageLoadCheck.equals(pieChartDataCheck[i])) {
				System.out.println(" *** marketing_summary pie chart data " + pieChartDataCheck[i+4] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary pie chart data " + pieChartDataCheck[i+4] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartLine").click();
		String[] lineChartDataCheck = {"2018.12.19(수)", "다음브랜드검색: 19", "네이버브랜드검색: 13", "((date))", "((daum brand search))", "((naver brand search))"};
		$(".highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
		$(".highcharts-series-0.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("● ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** marketing_summary line chart data " + lineChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary line chart data " + lineChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- marketing_summary End ----- ! ");
	}
	@Test(priority = 2)
	public void marketing_detail() {
		System.out.println(" ! ----- marketing_detail Start ----- ! ");
		$(By.linkText("상세")).waitUntil(visible, 10000);
		$(By.linkText("상세")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("상세")) {
			System.out.println(" *** marketing_detail page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_detail page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.id("chartSelect1")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='조회'])[1]/following::option[1]")).click();
	    $(By.id("chartSelect2")).click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='조회'])[1]/following::option[23]")).click();
	    $(".highcharts-series-0.highcharts-tracker > rect").waitUntil(visible, 10000);
	    String[] tableDataCheck = {"네이버브랜드검색", "13", "92.31%", "0", "0", "0", "0%", "0", "0", "0%", "0", "0%", "0", "0", "((product))", "((visit number))", "((return percent))", 
	    		"((reach number))", "((click number))", "((advertising cost))", "((CTR))", "((CPC))", "((CPA))", "((ROAS))", "((convert number))", "((convert percent))", "((convert sales))", "((visit sales))"};
	    $("td", 41).waitUntil(visible, 10000);
	    for(int i=0;i<=13;i++) {
			pageLoadCheck = $("td", (i+41)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** marketing_detail table data " + tableDataCheck[i+14] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail table data " + tableDataCheck[i+14] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    
	    String[] barChartDataCheck = {"2018.12.19(수)", "방문수: 56", "노출수: 0", "daily publication", "visit number", "reach number"};
	    $(".highcharts-series-0.highcharts-tracker", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("● ");
	    for(int i=0;i<=2;i++) {
			if (pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** marketing_detail bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail bar chart data " + barChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    pLC = null;
	    $("#btnChartLine").click();
	    $("#chartSelect3").waitUntil(visible, 10000);
	    $("#chartSelect3").click();
	    $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='조회'])[2]/following::option[1]")).click();
	    $(".highcharts-tracker", 3).hover();
	    $(".highcharts-tracker", 5).hover();
	    $(".highcharts-tracker", 7).hover();
	    $(".highcharts-tracker", 3).hover();
	    String[] lineChartDataCheck = {"2018.12.19(수)", "다음브랜드검색: 19", "네이버브랜드검색: 13", "((daily publication))", "((daum brand search))", "((naver brand search))"};
	    pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
	    pLC = pageLoadCheck.split("● ");
	    for(int i=0;i<=2;i++) {
			if (pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** marketing_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    System.out.println(" ! ----- marketing_detail End ----- ! ");
	}
	@Test(priority = 3)
	public void marketing_clickchoice() {
		System.out.println(" ! ----- marketing_clickchoice Start ----- ! ");
		$(By.linkText("네이버 검색광고")).waitUntil(visible, 10000);
		$(By.linkText("네이버 검색광고")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("네이버 검색광고")) {
			System.out.println(" *** marketing_clickchoice page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_clickchoice page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"0", "0", "0", "0", "0", "((visit number))", "((new visit number))", "((return percent))", "((purchase number))", "((sales))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** marketing_clickchoice panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Success !! *** ");
			} else {
				System.out.println(" *** marketing_clickchoice panel summary data " + panelDataCheck[i+5] + "((" + i + "))" + " check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"조회된 데이터가 없습니다.", "((no-records-found))"};
		pageLoadCheck = $("td", 7).text();
		if(pageLoadCheck.equals(tableDataCheck[0])) {
			System.out.println(" *** marketing_clickchoice table data " + panelDataCheck[1] + "((0))" + " check Success !! *** ");
		} else {
			System.out.println(" *** marketing_clickchoice panel summary data " + panelDataCheck[1] + "((0))" + " check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- marketing_clickchoice End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}