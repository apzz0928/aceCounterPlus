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

public class contentSetting {
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
		if(val.equals("URL_null")) {
			checkMsg = "동적페이지URL을 입력하세요.";
		} else if (val.equals("URL_check")) {
			checkMsg = "입력하신 페이지URL은\n" + 
					"동적페이지에 해당되지 않습니다.\n" + 
					"다시 확인 후 입력해주세요.";
		} else if (val.equals("URL_del_check")) {
			checkMsg = "삭제할 동적페이지를 선택하세요.";
		}  else if (val.equals("exceptChar_alert")) {
			checkMsg = "제외문자를 입력하세요.";
		} else if (val.equals("exceptAdd_confirm")) {
			checkMsg = "선택한 제외 문자를 등록하시겠습니까?\n" + 
					"확인을 클릭하면\n" + 
					"URL에 선택한 문자 이후 값은\n" + 
					"제외하여 분석합니다.";
		} else if (val.equals("exceptAdd_alert")) {
			checkMsg = "등록이 완료되었습니다.";
		} else if (val.equals("delExceptCharCheck_alert")) {
			checkMsg = "삭제할 URL 제외문자를 선택하세요.";
		} else if (val.equals("delExceptChar_confirm")) {
			checkMsg = "선택한 URL 제외문자를 삭제하겠습니까?\n" + 
					"URL 제외 문자에 대해 수집/분석이 중지되며,\n" + 
					"삭제 후 복구가 불가능합니다.";
		} else if (val.equals("delExceptChar_alert")) {
			checkMsg = "삭제가 완료되었습니다.";
		} else if (val.equals("")) {
			checkMsg = "";
		} else if (val.equals("")) {
			checkMsg = "";
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
	public void URLSetting_URLInclusion() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLInclusion Start ----- ! ");
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
		$(".accordion-toggle", 3).click();
		$(By.linkText("URL 설정")).click();
		Thread.sleep(1000);
		$(".btn-info", 0).waitUntil(visible, 3000);
		pageLoadCheck = $(".btn-info", 0).text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** URLSetting_URLInclusion Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 3000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(9, 7, "URL_null");
		$("#page-url").setValue("/test");
		$("#btn-add").click();
		valCheck(10, 8, "URL_check");
		$("#btn-add-cancel").click();
		$("#btn-add").waitUntil(hidden, 3000);
		$("#btn-list-delete").click();
		$("#btn-list-select-delete").click();
		valCheck(11, 9, "URL_del_check");
		System.out.println(" ! ----- URLSetting_URLInclusion End ----- ! ");
	}
	@Test(priority = 1)
	public void URLSetting_URLExcept_Add() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_Add Start ----- ! ");
		//$(By.linkText("URL 제외")).click();
		$(By.linkText("URL제외")).click();
		Thread.sleep(1000);
		$(".col-xs-9").waitUntil(visible, 3000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 3000);
		pageLoadCheck = $("th", 0).text();
		if(pageLoadCheck.equals("제외 문자 선택")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(5, 3, "exceptChar_alert");
		$(By.xpath("//div[@id='addset']/div/table/tbody/tr/td/div/div/label[4]")).click();
		Thread.sleep(1000);
		$("#btn-add").click();
		valCheck(6, 4, "exceptChar_alert");
		$(".input-sm", 0).setValue(date);
		$("#btn-add").click();
		valCheck(7, 5, "exceptAdd_confirm");
		Thread.sleep(1000);
		valCheck(8, 7, "exceptAdd_alert");
		Thread.sleep(2500);
		$(".col-xs-9", 0).waitUntil(visible, 5000);
		pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_Add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_Add Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_Add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_URLExcept_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$("td", 3).waitUntil(visible, 3000);
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_search End ----- ! ");
	}
	@Test(priority = 3)
	public void URLSetting_URLExcept_del() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 3000);
	    $("#btn-list-select-delete").click();
	    valCheck(5, 3, "delExceptCharCheck_alert");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(6, 4, "delExceptChar_confirm");
	    Thread.sleep(1000);
	    valCheck(7, 6, "delExceptChar_alert");
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}