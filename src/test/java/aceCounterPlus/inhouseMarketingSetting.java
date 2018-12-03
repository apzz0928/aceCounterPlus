package aceCounterPlus; //통계설정 > 인하우스마케팅 설정 (바이럴, 이메일, Talk)

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

import com.codeborne.selenide.testng.ScreenShooter;

public class inhouseMarketingSetting {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
    SimpleDateFormat number_format1 = new SimpleDateFormat("yyyy-MM-dd");
    String date = number_format.format(number_date);
    String date1 = number_format1.format(number_date);
    
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
		ScreenShooter.captureSuccessfulTests = false;

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
            case "viralAdd_cmpName_null": checkMsg = "캠페인명을 입력하세요.";
            break;
            case "viralAdd_cmpName_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
            break;
            case "viralAdd_subjectMatter_null": checkMsg = "소재를 입력하세요.";
            break;
            case "viralAdd_subjectMatter_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
            break;
            case "viralAdd_URL_null": checkMsg = "연결 URL을 입력하세요.";
            break;
            case "viralAdd_URL_validation": checkMsg = "올바른 URL을 입력하세요.";
            break;
            case "viralAdd_register": checkMsg = "등록이 완료되었습니다.";
            break;
            case "viraldupAdd_confirm": checkMsg = "등록된 캠페인이 있습니다. 해당리스트에 업데이트하시겠습니까?";
            break;
            case "viraldupAdd_alert": checkMsg = "중복된 소재가 있습니다. 다시 입력하세요.";
            break;
            case "viralSetting_del_null": checkMsg = "삭제할 바이럴 설정을 선택하세요.";
            break;
            case "viralSetting_del_confirm": checkMsg = "선택한 바이럴 설정을 삭제하시겠습니까?\n" + "바이럴 설정에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "viralSetting_del_alert": checkMsg = "바이럴 설정 삭제가 완료되었습니다.";
            break;
            case "emailAdd_cmpName_null": checkMsg = "캠페인명을 입력하세요.";
            break;
            case "emailAdd_cmpName_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
            break;
            case "emailAdd_sendDay_null": checkMsg = "발송일을 입력하세요.";
            break;
            case "emailAdd_sendNumber_null": checkMsg = "발송수를 입력하세요.";
            break;
            case "emailAdd_sendNumber_validation": checkMsg = "숫자만 입력하세요.";
            break;
            case "emailAdd_URL_null": checkMsg = "연결 URL을 입력하세요.";
            break;
            case "emailAdd_URL_validation": checkMsg = "올바른 URL을 입력하세요.";
            break;
            case "emailAdd_register": checkMsg = "등록이 완료되었습니다.";
            break;
            case "emailAdd_dupAdd_alert": checkMsg = "이미 사용한 캠페인명입니다.\n" + "다른 캠페인명을 사용해주세요.";
            break;
            case "emailSetting_del_null": checkMsg = "삭제할 이메일 설정을 선택하세요.";
            break;
            case "emailSetting_del_confirm": checkMsg = "선택한 이메일 설정을 삭제하시겠습니까?\n" + "이메일 설정에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "emailSetting_del_alert": checkMsg = "이메일 설정 삭제가 완료되었습니다.";
            break;
            case "talkAdd_cmpName_null": checkMsg = "캠페인명을 입력하세요.";
            break;
            case "talkAdd_cmpName_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
            break;
            case "talkAdd_sendDay_null": checkMsg = "발송일을 입력하세요.";
            break;
            case "talkAdd_subjectMatter_null": checkMsg = "소재를 입력하세요.";
            break;
            case "talkAdd_subjectMatter_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
            break;
            case "talkAdd_sendNumber_null": checkMsg = "발송수를 입력하세요.";
            break;
            case "talkAdd_sendNumber_validation": checkMsg = "숫자만 입력하세요.";
            break;
            case "talkAdd_URL_null": checkMsg = "연결 URL을 입력하세요.";
            break;
            case "talkAdd_URL_validation": checkMsg = "올바른 URL을 입력하세요.";
            break;
            case "talkAdd_register": checkMsg = "등록이 완료되었습니다.";
            break;
            case "talkdupAdd_confirm": checkMsg = "등록된 캠페인이 있습니다. 해당리스트에 업데이트하시겠습니까?";
            break;
            case "talkdupAdd_alert": checkMsg = "중복된 소재가 있습니다. 다시 입력하세요.";
            break;
            case "talkSetting_del_null": checkMsg = "삭제할 TALK 설정을 선택하세요.";
            break;
            case "talkSetting_del_confirm": checkMsg = "선택한 TALK 설정을 삭제하시겠습니까?\n" + "TALK 설정에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "talkSetting_del_alert": checkMsg = "TALK 설정 삭제가 완료되었습니다.";
            break;
        }
		$(".modal-backdrop").waitUntil(visible, 10000);
		$$("p").last().click();
		String msgCheck = $$("p").last().text().trim();
        Thread.onSpinWait();
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
	        System.out.println(" *** ☆★☆★☆★ val : " + val + " // pTag text is : " + msgCheck +  " // - msgCheck is Empty ... ☆★☆★☆★ *** ");
			System.out.println(checkMsg);
			close();
		} else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** // val : " + val + " // pTag text is : " + msgCheck +  " // - check Fail ... !@#$%^&*() *** ");
			System.out.println(checkMsg);
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
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() *** ");
            	close();
            } else {
            	System.out.println("***** " + urlName +" : Link Status HTTP : " + respCode + " - check Success !! *** ");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }

  	@Test(priority = 0)
	public void Login() {
		System.out.println(" ! ----- Login Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text().trim();
		if(loginCheck.equals("로그아웃")) {
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
	public void viralSetting_add() {
		System.out.println(" ! ----- viralSetting_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".accordion-toggle", 4).click();
		$(By.linkText("바이럴 설정")).waitUntil(visible, 10000);
		$(By.linkText("바이럴 설정")).click();
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("디코딩-인하우스마케팅-바이럴")) {
			System.out.println(" *** viralSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-save").text();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** viralSetting_add add page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_add add page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		valCheck("viralAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck("viralAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$("#btn-save").click();
		valCheck("viralAdd_subjectMatter_null");
		$(".input-sm", 1).setValue(date + "@");
		$("#btn-save").click();
		valCheck("viralAdd_subjectMatter_validation");
		$(".input-sm", 1).setValue(date);
		$(".input-sm", 2).setValue("");
		$("#btn-save").click();
		valCheck("viralAdd_URL_null");
		$(".input-sm", 2).setValue(date);
		$("#btn-save").click();
		valCheck("viralAdd_URL_validation");
		$(".input-sm", 2).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("viralAdd_register");
		$(".btn-fileDown").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-fileDown").text();
		if(pageLoadCheck.equals("다운로드")) {
			System.out.println(" *** viralSetting_add register Success !! *** ");			
		} else {
			System.out.println(" *** viralSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_add End ----- ! ");
	}
	@Test(priority = 2)
	public void viralSetting_duplicationAdd() {
		System.out.println(" ! ----- viralSetting_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-save").text();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** viralSetting_duplicationAdd add page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_duplicationAdd add page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date);
		$(".input-sm", 2).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("viraldupAdd_confirm");
		valCheck("viraldupAdd_alert");
		$(".w100", 1).click();
		$("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_duplicationAdd list page load Success !! *** ");			
		} else {
			System.out.println(" *** viralSetting_duplicationAdd list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_duplicationAdd End ----- ! ");
	}
	@Test(priority = 3)
	public void viralSetting_search() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $(".btn-gray", 0).waitUntil(hidden, 10000);
		pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("광고코드 다운로드")) {
			System.out.println(" *** viralSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 0).text();
		if(pageLoadCheck.equals("등록된 캠페인이 없습니다.\n" + "추가를 클릭해 캠페인을 등록하세요.")) {
			System.out.println(" *** viralSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".text-left").waitUntil(visible, 10000);
	    pageLoadCheck = $(".text-left").text();
	    if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search set selectBox page load Success !! *** ");	    	
	    } else {
			System.out.println(" *** viralSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
		$("#s_key").setValue(date);
		$("#btn-search").click();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search setList search Success !! *** ");  	
	    } else {
			System.out.println(" *** viralSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 4)
	public void viralSetting_mktCodeDownload() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(".btn-dark", 0).click();
		$(".modal-backdrop").waitUntil(visible, 10000);
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("광고코드 다운로드")) {
			System.out.println(" *** viralSetting_mktCodeDownload popup load Success !! *** ");  	
		} else {
			System.out.println(" *** viralSetting_mktCodeDownload popup load Fail ... !@#$%^&*() *** ");
			close();
		}
		brokenLinkCheck("mktCodeDownload", "https://new.acecounter.com/setting/inhouse/download?campaign_type=viral&down_type=urlDown&down_key=" + date1 + "~" + date1);
		$(".close", 0).click();
		$(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 5)
	public void viralSetting_del() {
		System.out.println(" ! ----- viralSetting_search Start ----- ! ");
		$(".btn-gray", 0).click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck("viralSetting_del_null");
		$(".campaignChk", 0).click();
		$("#btn-del").click();
		valCheck("viralSetting_del_confirm");
		valCheck("viralSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split("\n");
		if(pLC[0].equals("등록된 캠페인이 없습니다.")) {
			System.out.println(" *** viralSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- viralSetting_search End ----- ! ");
	}
	@Test(priority = 11)
	public void emailSetting_add() {
		System.out.println(" ! ----- emailSetting_add Start ----- ! ");
		$(By.linkText("이메일 설정")).click();
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("직접입력")) {
			System.out.println(" *** emailSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** emailSetting_add add Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-save").click();
		valCheck("emailAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck("emailAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue("");
		$("#btn-save").click();
		valCheck("emailAdd_sendDay_null");
		$(".input-sm", 1).setValue(date1);
		$("#btn-save").click();
		valCheck("emailAdd_sendNumber_null");
		$(".input-sm", 2).setValue("test");
		$("#btn-save").click();
		valCheck("emailAdd_sendNumber_validation");
		$(".input-sm", 2).setValue("1234");
		$(".input-sm", 3).setValue("");
		$("#btn-save").click();
		valCheck("emailAdd_URL_null");
		$(".input-sm", 3).setValue(date);
		$("#btn-save").click();
		valCheck("emailAdd_URL_validation");
		$(".input-sm", 3).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("emailAdd_register");
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("삭제")) {
			System.out.println(" *** emailSetting_add register Success !! *** ");			
		} else {
			System.out.println(" *** emailSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_add End ----- ! ");
	}
	@Test(priority = 12)
	public void emailSetting_duplicationAdd() {
		System.out.println(" ! ----- emailSetting_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("#btn-save").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** emailSetting_add add Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_add add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date1);
		$(".input-sm", 2).setValue("1234");
		$(".input-sm", 3).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("emailAdd_dupAdd_alert");
		$(".btn-light", 0).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("삭제")) {
			System.out.println(" *** emailSetting_duplicationAdd list page load Success !! *** ");			
		} else {
			System.out.println(" *** emailSetting_duplicationAdd  list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_duplicationAdd End ----- ! ");
	}
	@Test(priority = 13)
	public void emailSetting_search() {
		System.out.println(" ! ----- emailSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".btn-gray").waitUntil(hidden, 10000);
		pageLoadCheck = $(".btn-info").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** emailSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		$(".no-records-found").waitUntil(visible, 10000);
		pageLoadCheck = $(".no-records-found").text();
		if(pageLoadCheck.equals("등록된 캠페인이 없습니다.\n" + "추가를 클릭해 캠페인을 등록하세요.")) {
			System.out.println(" *** viralSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("삭제")) {
			System.out.println(" *** emailSetting_search set selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** viralSetting_search setList search Success !! *** ");
		} else {
			System.out.println(" *** viralSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_search End ----- ! ");
	}
	@Test(priority = 14)
	public void emailSetting_del() {
		System.out.println(" ! ----- emailSetting_del Start ----- ! ");
		$(".btn-gray").click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck("emailSetting_del_null");
		$(".campaignChk", 0).click();
		$("#btn-del").click();
		valCheck("emailSetting_del_confirm");
		valCheck("emailSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split("\n");
		if(pLC[0].equals("등록된 캠페인이 없습니다.")) {
			System.out.println(" *** emailSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** emailSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- emailSetting_del End ----- ! ");
	}
	@Test(priority = 21)
	public void talkSetting_add() {
		System.out.println(" ! ----- talkSetting_add Start ----- ! ");
		$(By.linkText("Talk 설정")).click();
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals("인하우스 - talk.")) {
			System.out.println(" *** talkSetting_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		$("#btn-save").waitUntil(visible, 10000);
		$("#btn-save").click();
		valCheck("talkAdd_cmpName_null");
		$(".input-sm", 0).setValue(date + "@");
		$("#btn-save").click();
		valCheck("talkAdd_cmpName_validation");
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue("");
		$("#btn-save").click();
		valCheck("talkAdd_sendDay_null");
		$(".input-sm", 1).setValue(date1);
		$("#btn-save").click();
		valCheck("talkAdd_subjectMatter_null");
		$(".input-sm", 2).setValue(date + "@");
		$("#btn-save").click();
		valCheck("talkAdd_subjectMatter_validation");
		$(".input-sm", 2).setValue(date);
		$("#btn-save").click();
		valCheck("talkAdd_sendNumber_null");
		$(".input-sm", 3).setValue("test");
		$("#btn-save").click();
		valCheck("talkAdd_sendNumber_validation");
		$(".input-sm", 3).setValue("1234");
		$(".input-sm", 4).setValue("");
		$("#btn-save").click();
		valCheck("talkAdd_URL_null");
		$(".input-sm", 4).setValue(date);
		$("#btn-save").click();
		valCheck("talkAdd_URL_validation");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("talkAdd_register");
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_add End ----- ! ");
	}
	@Test(priority = 22)
	public void talkSetting_duplicationAdd() {
		System.out.println(" ! ----- talkSetting_duplicationAdd Start ----- ! ");
		$(".btn-info").click();
		$("#btn-save").waitUntil(visible, 10000);
		$(".input-sm", 0).setValue(date);
		$(".input-sm", 1).setValue(date1);
		$(".input-sm", 2).setValue(date);
		$(".input-sm", 3).setValue("1234");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$("#btn-save").click();
		valCheck("talkdupAdd_confirm");
		valCheck("talkdupAdd_alert");
		$(".btn-light", 0).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_duplicationAdd  End ----- ! ");
	}
	@Test(priority = 23)
	public void talkSetting_search() {
		System.out.println(" ! ----- talkSetting_search Start ----- ! ");
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
		$(".btn-gray").waitUntil(hidden, 10000);
		pageLoadCheck = $(".btn-material-list").text();
		if(pageLoadCheck.equals("보기")) {
			System.out.println(" *** talkSetting_search del selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search del selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		$(".no-records-found").waitUntil(visible, 10000);
		pageLoadCheck = $(".no-records-found").text();
		if(pageLoadCheck.equals("등록된 캠페인이 없습니다.\n" + "추가를 클릭해 캠페인을 등록하세요.")) {
			System.out.println(" *** talkSetting_search delList search Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
		$(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
		$(".btn-gray").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray").text();
		if(pageLoadCheck.equals("삭제")) {
			System.out.println(" *** talkSetting_search set selectBox page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search set selectBox page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#s_key").setValue(date);
		$("#btn-search").click();
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** talkSetting_search setList search Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_search  End ----- ! ");
	}
	@Test(priority = 24)
	public void talkSetting_del() {
		System.out.println(" ! ----- talkSetting_del Start ----- ! ");
		$(".btn-gray").click();
		$("#btn-del").waitUntil(visible, 10000);
		$("#btn-del").click();
		valCheck("talkSetting_del_null");
		$(".campaignChk", 0).click();
		$("#btn-del").click();
		valCheck("talkSetting_del_confirm");
		valCheck("talkSetting_del_alert");
		$("td", 3).waitUntil(hidden, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split("\n");
		if(pLC[0].equals("등록된 캠페인이 없습니다.")) {
			System.out.println(" *** talkSetting_del list Page load Success !! *** ");
		} else {
			System.out.println(" *** talkSetting_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- talkSetting_del  End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}