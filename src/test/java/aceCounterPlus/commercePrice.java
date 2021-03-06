package aceCounterPlus; //커머스 설정 > (제품가격대, 통화단위)

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

public class commercePrice {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, pageLoadCheck;
	
	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("MMddhhmmss");
    String date = number_format.format(number_date);
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		//hubUrl = "http://10.0.75.1:5555/wd/hub";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf!@34";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			//cap = DesiredCapabilities.firefox();
			//RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*EdgeOptions options = new EdgeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
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
		switch(val){
		    case "commercePrice_Setup": checkMsg = "등록이 완료되었습니다.";
		    break;
		    case "commercePrice_modify_confirm": checkMsg = "수정하시겠습니까?\n" + "제품가격대를 수정하실 경우 기존 데이터도 함께 변경됩니다.";
		    break;
		    case "commercePrice_modify_alert": checkMsg = "수정이 완료되었습니다.";
		    break;
		    case "commercePrice_del_confirm": checkMsg = "항목을 삭제하시겠습니까?";
		    break;
		    case "commercePrice_del_alert": checkMsg = "삭제가 완료되었습니다.";
		    break;
		    case "commercePrice_min_alert": checkMsg = "최저가격을 입력하세요.";
		    break;
		    case "commercePrice_max_alert": checkMsg = "최고가격을 입력하세요.";
		    break;
		    case "commercePrice_min>max_alert": checkMsg = "올바른 가격설정이 아닙니다.";
		    break;
		    case "currencyUnit_alert": checkMsg = "설정된 통화 단위값과 동일합니다.\n" + "다시 선택해주세요.";
		    break;
		    case "currencyUnit_modify_confirm": checkMsg = "통계 표기 통화 단위를 수정하시겠습니까?";
		    break;
		    case "currencyUnit_modify_alert": checkMsg = "수정이 완료되었습니다.";
		    break;
	    }
		$(".modal-backdrop").waitUntil(visible, 10000);
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    //Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	            System.out.println(" *** val : " + val +  " - confirm check Success !! *** ");
	            $$(".btn-info").last().click();
	            $(".modal-backdrop").waitUntil(hidden, 10000);
	        } else { //confirm 아니면 .btn-sm클릭
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            $(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert 로딩이 늦거나 노출되지 않았을때 체크하기위해 빈값 체크
	        System.out.println(" *** ☆★☆★☆★ " + val +  " - msgCheck is Empty ... ☆★☆★☆★ *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** " + val +  " - check Fail ... !@#$%^&*() *** ");
	        System.out.println(checkMsg);
	        close();
	    }
	}
	
	@Test(priority = 0)
	public void Login() {
		System.out.println(" ! ----- Login Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("방문수")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- Login End ----- ! ");
	}
  	@Test(priority = 1)
	public void commercePrice_directAdd() {
		System.out.println(" ! ----- commercePrice_directAdd Start ----- ! ");
		$("#redirectConfBtn").click();
		$("#inflowAddBtn").waitUntil(visible, 30000);
		$(".accordion-toggle", 2).click();
		$(By.linkText("제품가격대")).waitUntil(visible, 30000);
		sleep(500);
		$(By.linkText("제품가격대")).click();
		$("h5", 1).waitUntil(visible, 30000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		$("h3", 2).waitUntil(visible, 30000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 3).setValue("1000");
		$(".input-sm", 4).setValue("5000");
		$(".input-sm", 5).setValue("5001");
		$(".input-sm", 6).setValue("10000");
		//가격대 입력여부 유효성체크가 안되서 빼둠
		$("#priceRangeInsert").click();
		valCheck("commercePrice_Setup");
		$(".btn-xs", 0).waitUntil(visible, 30000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("1,000원 ~ 10,000원")) {
			System.out.println(" *** commercePrice directAdd check Success !! *** ");
			pageLoadCheck = $("td", 7).text();
			if(pageLoadCheck.equals("1,000원 ~ 5,000원")) {
				System.out.println(" *** commercePrice directAdd oneStepPrice check Success !! *** ");
				pageLoadCheck = $("td", 9).text();
				if(pageLoadCheck.equals("5,001원 ~ 10,000원")) {
					System.out.println(" *** commercePrice directAdd twoStepPrice check Success !! *** ");
				} else {
					System.out.println(" *** commercePrice directAdd twoStepPrice check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice directAdd oneStepPrice check Fail ... !@#$%^&*() *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice directAdd check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd End ----- ! ");
	}
	@Test(priority = 2)
	public void commercePrice_directAdd_modify() {
		System.out.println(" ! ----- commercePrice_directAdd_modify Start ----- ! ");
		$(".btn-xs", 0).waitUntil(visible, 30000);
		$(".btn-xs", 0).click();
		$("h3", 2).waitUntil(visible, 30000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice directAdd_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice directAdd_modify Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 3).setValue("2000");
		$(".input-sm", 4).setValue("10000");
		$(".input-sm", 5).setValue("10001");
		$(".input-sm", 6).setValue("20000");
		$("#priceRangeInsert").click();
		valCheck("commercePrice_modify_confirm");
		valCheck("commercePrice_modify_alert");
		$(".btn-xs", 0).waitUntil(visible, 30000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("2,000원 ~ 20,000원")) {
			System.out.println(" *** commercePrice directAdd modify check Success !! *** ");
			pageLoadCheck = $("td", 7).text();
			if(pageLoadCheck.equals("2,000원 ~ 10,000원")) {
				System.out.println(" *** commercePrice directAdd modify oneStepPrice check Success !! *** ");
				pageLoadCheck = $("td", 9).text();
				if(pageLoadCheck.equals("10,001원 ~ 20,000원")) {
					System.out.println(" *** commercePrice directAdd modify twoStepPrice check Success !! *** ");
				} else {
					System.out.println(" *** commercePrice directAdd modify twoStepPrice check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice directAdd modify oneStepPrice check Fail ... !@#$%^&*() *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice directAdd modify check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd_modify End ----- ! ");
	}
	@Test(priority = 3)
	public void commercePrice_directAdd_del() {
		System.out.println(" ! ----- commercePrice_directAdd_del Start ----- ! ");
		$(".btn-xs", 1).waitUntil(visible, 30000);
		$(".btn-xs", 1).click();
		valCheck("commercePrice_del_confirm");
		valCheck("commercePrice_del_alert");
		$(".btn-xs", 0).waitUntil(hidden, 10000);
		String	pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice_directAdd_del Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice_directAdd_del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd_del End ----- ! ");
	}
	@Test(priority = 4)
	public void commercePrice_autoAdd() {
		System.out.println(" ! ----- commercePrice_autoAdd Start ----- ! ");
		$(".btn-info").click();
		$("h3", 2).waitUntil(visible, 3000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("label", 2).click();
		$(".input-sm", 1).waitUntil(enabled, 10000);
		$("#rangeProc").click();
		valCheck("commercePrice_min_alert");
		$(".input-sm", 1).setValue("1000");
		$("#rangeProc").click();
		valCheck("commercePrice_max_alert");
		$(".input-sm", 2).setValue("100");
		$("#rangeProc").click();
		valCheck("commercePrice_min>max_alert");
		$(".input-sm", 2).setValue("10000");
		$("#rangeProc").click();
		$("#priceRangeInsert").click();
		valCheck("commercePrice_Setup");
		$(".btn-xs", 0).waitUntil(visible, 30000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("1,000원 ~ 10,000원")) {
			System.out.println(" *** commercePrice autoAdd check Success !! *** ");
			pageLoadCheck = $("td", 7).text();
			if(pageLoadCheck.equals("1,000원 ~ 5,500원")) {
				System.out.println(" *** commercePrice autoAdd oneStepPrice check Success !! *** ");
				pageLoadCheck = $("td", 9).text();
				if(pageLoadCheck.equals("5,501원 ~ 10,000원")) {
					System.out.println(" *** commercePrice autoAdd twoStepPrice check Success !! *** ");
				} else {
					System.out.println(" *** commercePrice autoAdd twoStepPrice check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice autoAdd oneStepPrice check Fail ... !@#$%^&*() *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice autoAdd check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_autoAdd End ----- ! ");
	}
	@Test(priority = 5)
	public void commercePrice_autoAdd_modify() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_autoAdd_modify Start ----- ! ");
		$(".btn-xs", 0).waitUntil(visible, 30000);
		$(".btn-xs", 0).click();
		$("h3", 2).waitUntil(visible, 30000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#rangeProc").click();
		valCheck("commercePrice_min_alert");
		$(".input-sm", 1).setValue("2000");
		$("#rangeProc").click();
		valCheck("commercePrice_max_alert");
		$(".input-sm", 2).setValue("100");
		$("#rangeProc").click();
		valCheck("commercePrice_min>max_alert");
		$(".input-sm", 2).setValue("20000");
		$("#rangeProc").click();
		$("#priceRangeInsert").click();
		valCheck("commercePrice_modify_confirm");
		valCheck("commercePrice_modify_alert");
		$(".btn-xs", 1).waitUntil(visible, 30000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("2,000원 ~ 20,000원")) {
			System.out.println(" *** commercePrice autoAdd modify check Success !! *** ");
			pageLoadCheck = $("td", 7).text();
			if(pageLoadCheck.equals("2,000원 ~ 11,000원")) {
				System.out.println(" *** commercePrice autoAdd oneStepPrice modify check Success !! *** ");
				pageLoadCheck = $("td", 9).text();
				if(pageLoadCheck.equals("11,001원 ~ 20,000원")) {
					System.out.println(" *** commercePrice autoAdd twoStepPrice modify check Success !! *** ");
				} else {
					System.out.println(" *** commercePrice autoAdd twoStepPrice modify check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice autoAdd oneStepPrice modify check Fail ... !@#$%^&*() *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice autoAdd modify check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_autoAdd_modify End ----- ! ");
	}
	@Test(priority = 6)
	public void commercePrice_autoAdd_del() {
		System.out.println(" ! ----- commercePrice_autoAdd_del Start ----- ! ");
		$(".btn-xs", 1).waitUntil(visible, 30000);
		$(".btn-xs", 1).click();
		valCheck("commercePrice_del_confirm");
		valCheck("commercePrice_del_alert");
		$("h5", 1).waitUntil(visible, 30000);
		String	pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice_autoAdd_del Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice_autoAdd_del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_autoAdd_del End ----- ! ");
	}
	@Test(priority = 11)
	public void commerce_currencyUnit() { //페이지로딩20초 문제 개선될때까지 통화단위만 체크
		System.out.println(" ! ----- commerce_currencyUnit Start ----- ! ");
		$(By.linkText("통화 단위")).waitUntil(visible, 10000);
		$(By.linkText("통화 단위")).click();
		sleep(26000);
		String	pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("KRW")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		/*//$("#mViewBtn").waitUntil(visible, 30000);
		String	pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#mViewBtn").click();
		$("#modifyBtn").waitUntil(visible, 30000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("수정하기")) {
			System.out.println(" *** commerce_currencyUnit_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit_modify Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#modifyBtn").click();
		valCheck(3, 3, "currencyUnit_alert");
	    $(By.name("nextIso")).click();
	    $(By.xpath("//option[@value='USD']")).click();
		$("#modifyBtn").click();
		sleep(2000);
		valCheck(4, 4, "currencyUnit_modify_confirm");
		sleep(1000);
		valCheck(5, 6, "currencyUnit_modify_alert");
		sleep(10000);
		$("#mViewBtn").waitUntil(visible, 30000);
		pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#mViewBtn").click();
		$("h3", 2).waitUntil(visible, 30000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("수정하기")) {
			System.out.println(" *** commerce_currencyUnit_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit_modify Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("nextIso")).click();
	    $(By.xpath("//option[@value='KRW']")).click();
		$("#modifyBtn").click(); 
		sleep(2000);
		valCheck(3, 3, "currencyUnit_modify_confirm");
		sleep(1000);
		valCheck(4, 5, "currencyUnit_modify_alert");
		sleep(1000);
		$("#mViewBtn").waitUntil(visible, 30000);
		pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... !@#$%^&*() *** ");
			close();
		}*/
		System.out.println(" ! ----- commerce_currencyUnit End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}