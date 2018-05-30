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

public class commercePrice {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("MMddhhmmss");
    String date = number_format.format(number_date);
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf!@34";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		ScreenShooter.captureSuccessfulTests = true;

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			// driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			cap = DesiredCapabilities.firefox();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
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
	
	public static void valCheck(int pTagNum, int btnNum, String val) {
		String msgCheck = $("p", pTagNum).text();
		if(val.equals("priceSetup")) {
			checkMsg = "등록이 완료되었습니다.";
		} else if (val.equals("modifyPrice_confirm")) {
			checkMsg = "수정하시겠습니까?\n" + 
					"제품가격대를 수정하실 경우 기존 데이터도 함께 변경됩니다.";
		} else if (val.equals("modifyPrice_alert")) {
			checkMsg = "수정이 완료되었습니다.";
		}  else if (val.equals("priceDel_confirm")) {
			checkMsg = "항목을 삭제하시겠습니까?";
		} else if (val.equals("priceDel_alert")) {
			checkMsg = "삭제가 완료되었습니다.";
		} else if (val.equals("minPrice_alert")) {
			checkMsg = "최저가격을 입력하세요.";
		} else if (val.equals("maxPrice_alert")) {
			checkMsg = "최고가격을 입력하세요.";
		} else if (val.equals("min>maxPrice_alert")) {
			checkMsg = "올바른 가격설정이 아닙니다.";
		} else if (val.equals("currencyUnit_alert")) {
			checkMsg = "설정된 통화 단위값과 동일합니다.\n" + 
					"다시 선택해주세요.";
		} else if (val.equals("currencyUnit_modify_confirm")) {
			checkMsg = "통계 표기 통화 단위를 수정하시겠습니까?";
		} else if (val.equals("currencyUnit_modify_alert")) {
			checkMsg = "수정이 완료되었습니다.";
		}
		$(".modal-backdrop").waitUntil(visible, 5000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " - check Fail ... *** ");
			close();
		}
	}
	
  	//입력된 URL 정상 여부 확인
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400){
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " *****");
            	close();
            } else {
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " *****");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }

	@Test(priority = 0)
	public void commercePrice_directAdd() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_directAdd Start ----- ! ");
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("방문수")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#redirectConfBtn").waitUntil(visible, 3000);
		$(".accordion-toggle", 2).click();
		$(By.linkText("제품가격대")).click();
		Thread.sleep(1000);
		$("h5", 1).waitUntil(visible, 3000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info").click();
		$("h3", 2).waitUntil(visible, 3000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... *** ");
			close();
		}
		$(".input-sm", 3).setValue("1000");
		$(".input-sm", 4).setValue("5000");
		$(".input-sm", 5).setValue("5001");
		$(".input-sm", 6).setValue("10000");
		//가격대 입력여부 유효성체크가 안되서 빼둠
		$("#priceRangeInsert").click();
		Thread.sleep(1000);
		valCheck(3, 4, "priceSetup");
		$(".btn-xs", 0).waitUntil(visible, 5000);
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
					System.out.println(" *** commercePrice directAdd twoStepPrice check Fail ... *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice directAdd oneStepPrice check Fail ... *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice directAdd check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd End ----- ! ");
	}
	@Test(priority = 1)
	public void commercePrice_directAdd_modify() {
		System.out.println(" ! ----- commercePrice_directAdd_modify Start ----- ! ");
		$(".btn-xs", 0).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice directAdd_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice directAdd_modify Page load Fail ... *** ");
			close();
		}
		$(".input-sm", 3).setValue("2000");
		$(".input-sm", 4).setValue("10000");
		$(".input-sm", 5).setValue("10001");
		$(".input-sm", 6).setValue("20000");
		$("#priceRangeInsert").click();
		valCheck(3, 4, "modifyPrice_confirm");
		valCheck(4, 6, "modifyPrice_alert");
		$(".btn-xs", 0).waitUntil(visible, 5000);
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
					System.out.println(" *** commercePrice directAdd modify twoStepPrice check Fail ... *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice directAdd modify oneStepPrice check Fail ... *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice directAdd modify check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd_modify End ----- ! ");
	}
	@Test(priority = 2)
	public void commercePrice_directAdd_del() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_directAdd_del Start ----- ! ");
		$(".btn-xs", 1).waitUntil(visible, 3000);
		$(".btn-xs", 1).click();
		valCheck(3, 3, "priceDel_confirm");
		Thread.sleep(1000);
		valCheck(4, 5, "priceDel_alert");
		$("h5", 1).waitUntil(visible, 3000);
		String	pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd_del End ----- ! ");
	}
	@Test(priority = 3)
	public void commercePrice_autoAdd() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_autoAdd Start ----- ! ");
		Thread.sleep(1000);
		$(".btn-info").click();
		$("h3", 2).waitUntil(visible, 3000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... *** ");
			close();
		}
		$("label", 2).click();
		$(".input-sm", 1).waitUntil(enabled, 3000);
		$("#rangeProc").click();
		valCheck(3, 4, "minPrice_alert");
		$(".input-sm", 1).setValue("1000");
		$("#rangeProc").click();
		valCheck(4, 5, "maxPrice_alert");
		$(".input-sm", 2).setValue("100");
		$("#rangeProc").click();
		valCheck(5, 6, "min>maxPrice_alert");
		$(".input-sm", 2).setValue("10000");
		$("#rangeProc").click();
		Thread.sleep(1000);
		$("#priceRangeInsert").click();
		valCheck(6, 7, "priceSetup");
		Thread.sleep(3500);
		$(".btn-xs", 0).waitUntil(visible, 5000);
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
					System.out.println(" *** commercePrice autoAdd twoStepPrice check Fail ... *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice autoAdd oneStepPrice check Fail ... *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice autoAdd check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_autoAdd End ----- ! ");
	}
	@Test(priority = 4)
	public void commercePrice_autoAdd_modify() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_autoAdd_modify Start ----- ! ");
		$(".btn-xs", 0).click();
		$("h3", 2).waitUntil(visible, 3000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("제품가격대 신규등록")) {
			System.out.println(" *** commercePrice add Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice add Page load Fail ... *** ");
			close();
		}
		$("#rangeProc").click();
		valCheck(3, 4, "minPrice_alert");
		$(".input-sm", 1).setValue("2000");
		$("#rangeProc").click();
		valCheck(4, 5, "maxPrice_alert");
		$(".input-sm", 2).setValue("100");
		$("#rangeProc").click();
		valCheck(5, 6, "min>maxPrice_alert");
		$(".input-sm", 2).setValue("20000");
		$("#rangeProc").click();
		Thread.sleep(1000);
		$("#priceRangeInsert").click();
		valCheck(6, 7, "modifyPrice_confirm");
		Thread.sleep(1000);
		valCheck(7, 9, "modifyPrice_alert");
		$(".btn-xs", 1).waitUntil(visible, 3000);
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
					System.out.println(" *** commercePrice autoAdd twoStepPrice modify check Fail ... *** ");
					close();
				}
			} else {
				System.out.println(" *** commercePrice autoAdd oneStepPrice modify check Fail ... *** ");
				close();
			}
		} else {
			System.out.println(" *** commercePrice autoAdd modify check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_autoAdd_modify End ----- ! ");
	}
	@Test(priority = 5)
	public void commercePrice_autoAdd_del() throws InterruptedException {
		System.out.println(" ! ----- commercePrice_directAdd_del Start ----- ! ");
		$(".btn-xs", 1).waitUntil(visible, 3000);
		$(".btn-xs", 1).click();
		valCheck(3, 3, "priceDel_confirm");
		Thread.sleep(1000);
		valCheck(4, 5, "priceDel_alert");
		$("h5", 1).waitUntil(visible, 3000);
		String	pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 제품가격대가 없습니다.")) {
			System.out.println(" *** commercePrice Page load Success !! *** ");
		} else {
			System.out.println(" *** commercePrice Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commercePrice_directAdd_del End ----- ! ");
	}
	@Test(priority = 6)
	public void commerce_currencyUnit() throws InterruptedException {
		System.out.println(" ! ----- commerce_currencyUnit Start ----- ! ");
		$(By.linkText("통화 단위")).click();
		$("#mViewBtn").waitUntil(visible, 3000);
		String	pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... *** ");
			close();
		}
		$("#mViewBtn").click();
		$("h3", 2).waitUntil(visible, 3000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("수정하기")) {
			System.out.println(" *** commerce_currencyUnit_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit_modify Page load Fail ... *** ");
			close();
		}
		$("#modifyBtn").click();
		valCheck(3, 3, "currencyUnit_alert");
	    $(By.name("nextIso")).click();
	    $(By.xpath("//option[@value='USD']")).click();
		Thread.sleep(1000);
		$("#modifyBtn").click(); 
		valCheck(4, 4, "currencyUnit_modify_confirm");
		Thread.sleep(1000);
		valCheck(5, 6, "currencyUnit_modify_alert");
		Thread.sleep(2000);
		$("#mViewBtn").waitUntil(visible, 3000);
		pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... *** ");
			close();
		}
		$("#mViewBtn").click();
		$("h3", 2).waitUntil(visible, 3000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("수정하기")) {
			System.out.println(" *** commerce_currencyUnit_modify Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit_modify Page load Fail ... *** ");
			close();
		}
	    $(By.name("nextIso")).click();
	    $(By.xpath("//option[@value='KRW']")).click();
		Thread.sleep(1000);
		$("#modifyBtn").click(); 
		valCheck(3, 3, "currencyUnit_modify_confirm");
		Thread.sleep(1000);
		valCheck(4, 5, "currencyUnit_modify_alert");
		Thread.sleep(2000);
		$("#mViewBtn").waitUntil(visible, 3000);
		pageLoadCheck = $("#mViewBtn").text();
		if(pageLoadCheck.equals("수정")) {
			System.out.println(" *** commerce_currencyUnit Page load Success !! *** ");
		} else {
			System.out.println(" *** commerce_currencyUnit Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- commerce_currencyUnit End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}