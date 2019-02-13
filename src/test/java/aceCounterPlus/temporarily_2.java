package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	private static String baseUrl, hubUrl, TestBrowser, id, pw, A, pageLoadCheck, dateCheck, nodata;
	
	Date date = new Date();
    SimpleDateFormat date_format = new SimpleDateFormat("YYYYMMddHHmmss");
    String id_date = date_format.format(date);
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
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
	public void KPI() {
		System.out.println(" ! ----- KPI Start ----- ! ");
		$("#KPI").click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("KPI")) {
			System.out.println(" *** KPI page load Success !! *** ");
		} else {
			System.out.println(" *** KPI page load Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=1;i<=2;i++) {
		    //캘린더로 날짜 계산
		    Calendar cal = Calendar.getInstance(); //캘린더 선언
		    cal.setTime(new Date()); //캘린더 시간을 오늘 날짜로 설정
		    cal.add(Calendar.DATE, (-i)); //캘린더에 -1일
		    //날짜를 계산할 형식으로 변경
		    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd"); // 사용할 날짜 포멧 설정
		    String id_date = date_format.format(cal.getTime()); //id_date를 포멧에 맞춰서 캘린더의 시간으로 설정
			pageLoadCheck = $(".th-inner", i).text().trim();
			//테이블에서 날짜 표시 부분 텍스트 추출해와서 오늘 -1, -2일이랑 비교
			String[] pLC = pageLoadCheck.split(" ~ ");
			if(pLC[1].equals(id_date)) {
				System.out.println(" *** KPI table data base Period((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** KPI table data base Period((" + i + ")) Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"페이지뷰", "회원수", "신규가입수", "로그인수", "로그인율", "해지수", "해지율", "방문수", "신규방문수", "재방문수", "반송수", "반송률"
				, "방문당 페이지뷰", "방문당 체류시간"};
		for(int i=0,x=0;i<=27;i++) {
			if(i%2 == 1) {
				pageLoadCheck = $(".another-class", i).text().trim();
				if(pageLoadCheck.equals(tableDataCheck[x])) {
			        System.out.println(" *** KPI table data ((" + x + ")) check Success !! *** ");
					x = ++x;
				} else {
			        System.out.println(" *** KPI table data ((" + x + ")) check Fail ... !@#$%^&*() *** ");
			        close();
			    }
			}
		}
		System.out.println(" ! ----- KPI End ----- ! ");
	}
	@Test(priority = 11)
	public void segment_list() {
		System.out.println(" ! ----- segment_list Start ----- ! ");
		$("#SEGMENT").click();
		$(".notokr-bold").waitUntil(visible, 15000);
		pageLoadCheck = $(".notokr-bold").text().trim();
		if (pageLoadCheck.equals("세그먼트")) {
			System.out.println(" *** segment_list page load Success !! *** ");
		} else {
			System.out.println(" *** segment_list page load Fail ... !@#$%^&*() *** ");
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
		for(int i=0;i<=20;i++) {
			if($("td", 7).text().trim().equals(nodata)) {
				System.out.println(" *** segment_list table data check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** segment_list table data loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** segment_list table data check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		for(int i=0;i<=18;i++) { //방문속성 선택
			if(!(i==1)) {
				$("#accord_dimension > ul > .li-selec", i).click();
			}
		}
		for(int i=1;i<=32;i++) { //성과 선택
			$("#accord_metric > ul > .li-selec", i).click();
		}
		$("#filterRegist").click();
		$(".blickUI").waitUntil(hidden, 10000);
		$("td", 71).waitUntil(visible, 10000);
		for(int i=0;i<=18;i++) {
			String dimensionDataCheck = $(".selected > .pr > .li-selec", i).text().trim();
			pageLoadCheck = $(".th-inner", i+2).text().trim();
			if(pageLoadCheck.equals(dimensionDataCheck)) {
		        System.out.println(" *** segment_list table visit property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** segment_list table visit property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		String[] metricDataCheck = {"방문수", "방문수 비중", "신규방문수", "신규방문수 비중", "재방문수", "재방문수 비중", "순방문수", "순방문수 비중", "반송수", "반송수 비중", "반송률(%)"
				, "페이지뷰", "페이지뷰 비중", "방문당 페이지뷰", "체류시간", "방문당 체류시간", "전환수", "전환수 비중", "전환율(%)", "전환 매출액", "전환 매출액 비중"
				, "전환당 매출액", "구매건수", "구매건수 비중", "구매율(%)", "매출액", "매출액 비중", "구매당 매출액", "구매수량", "구매수량 비중", "가입수", "가입수 비중"
				, "가입률(%)", "해지수", "로그인수", "내부검색 횟수", "내부검색후 이탈수", "장바구니담기", "위시리스트담기", "즉시구매버튼", "담김수", "구매건수/담김수(%)"
				, "간편결제클릭수", "제품 페이지뷰", "제품 페이지뷰 비중", "담김수/제품 페이지뷰(%)"};
		for(int i=0;i<=45;i++) {
			if((i+21)%10 == 0) {
				$(".th-inner", i+21).scrollIntoView("{behavior: 'smooth', inline: 'start'}");
			}
			pageLoadCheck = $(".th-inner", i+21).text().trim();
			if(pageLoadCheck.equals(metricDataCheck[i])) {
		        System.out.println(" *** segment_list table visit performance ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** segment_list table visit performance ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".btn-dark", 0).scrollIntoView(false);
		$(".btn-dark", 0).click();
		$(".gui-input", 0).waitUntil(visible, 10000);
		$(".gui-input").setValue(id_date);
		for(int i=0;i<=18;i++) {
			if(i%3 == 0) {
				$(".dimension > .ui-sortable > .list-group-item").scrollIntoView(true);
			}
			String mySegmentSaveDimension = $(".selected > .pr > .li-selec", i).text().trim();
			pageLoadCheck = $(".dimension > .ui-sortable > .list-group-item", i).text().trim();
			if(pageLoadCheck.equals(mySegmentSaveDimension)) {
		        System.out.println(" *** mySegmentSave layer dimension property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** mySegmentSave layer dimension property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		for(int i=0;i<=32;i++) {
			if(i%3 == 0) {
				$(".metric > .ui-sortable > .list-group-item").scrollIntoView(true);
			}
			String mySegmentSaveMetric = $(".selected > ul > .li-selec", i+19).text().trim();
			pageLoadCheck = $(".metric > .ui-sortable > .list-group-item", i).text().trim();
			if(pageLoadCheck.equals(mySegmentSaveMetric)) {
		        System.out.println(" *** mySegmentSave layer metric property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** mySegmentSave layer metric property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".btnSave").click();
		if($$("p").last().text().trim().equals("나의 세그먼트가 저장되었습니다.")) {
			$$(".btn-sm").last().click();
	        System.out.println(" *** mySegmentSave layer save alert msg check Success !! *** ");
		} else {
	        System.out.println(" *** mySegmentSave layer save alert msg check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		System.out.println(" ! ----- segment_list End ----- ! ");
	}
	@Test(priority = 12)
	public void segment_mySegment() {
		System.out.println(" ! ----- segment_mySegment Start ----- ! ");
		$(".delete", 0).waitUntil(visible, 10000);
		$(".text-underline", 0).click();
		$(".fs16", 0).waitUntil(visible, 10000);
		if($(".fs16", 0).text().trim().equals(id_date)) {
	        System.out.println(" *** segment_mySegment page name check Success !! *** ");
	        $(".filter-view", 0).click();
		} else {
	        System.out.println(" *** segment_mySegment page name check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		$(".viewSegment", 0).waitUntil(visible, 10000);
		$(".viewSegment", 0).click();
		$(".my-segment-nm").waitUntil(visible, 10000);
		if($(".my-segment-nm").text().trim().equals(id_date)) {
	        System.out.println(" *** segment_mySegment layer name check Success !! *** ");
	        $(".btnOk").click();
		} else {
	        System.out.println(" *** segment_mySegment layer name check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		$(".br-l-n").waitUntil(enabled, 10000);
		$(".br-l-n").setValue("aaa");
		$("#btn-search").click();
		for(int i=0;i<=20;i++) {
			if($("td", 0).text().trim().equals("등록된 나의 세그먼트 내역이 없습니다.")) {
				System.out.println(" *** segment_mySegment search check Success !! *** ");
				break;
			} else if(i<=19) {
				System.out.println(" *** segment_mySegment search check wait 0." + i + " second *** ");
				sleep(100);
			} else {
				System.out.println(" *** segment_mySegment search check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".br-l-n").waitUntil(enabled, 10000);
		$(".br-l-n").setValue(id_date);
		$("#btn-search").click();
		for(int i=0;i<=20;i++) {
			if($("td", 1).text().trim().equals(id_date)) {
				System.out.println(" *** segment_mySegment search check Success !! *** ");
				break;
			} else if(i<=19) {
				System.out.println(" *** segment_mySegment search check wait 0." + i + " second *** ");
				sleep(100);
			} else {
				System.out.println(" *** segment_mySegment search check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".delete", 0).click();
		$("#btn-modal-alert-yes").waitUntil(visible, 10000);
		$$("#btn-modal-alert-yes").last().click();
		if($$("p").last().text().trim().equals("삭제가 완료되었습니다.")) {
	        System.out.println(" *** segment_mySegment delete alert msg check Success !! *** ");
			$$(".btn-sm").last().click();
		} else {
	        System.out.println(" *** segment_mySegment delete alert msg check Fail ... !@#$%^&*() *** ");
	        close();
		}
		System.out.println(" ! ----- segment_mySegment End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}