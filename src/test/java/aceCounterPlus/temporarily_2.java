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

import com.codeborne.selenide.testng.ScreenShooter;

public class temporarily_2 {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
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
		ScreenShooter.captureSuccessfulTests = true;

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
	
	public static void valCheck(int pTagNum, int btnNum, String val) {
		$(".modal-backdrop").waitUntil(visible, 10000);
		String msgCheck = $("p", pTagNum).text();
        switch(val){
            case "dynamicPage_URL_null": checkMsg = "동적페이지URL을 입력하세요.";
            break;
            case "dynamicPage_URL_check": checkMsg = "입력하신 페이지URL은\n" + "동적페이지에 해당되지 않습니다.\n" + "다시 확인 후 입력해주세요.";
            break;
            case "dynamicPage_exclude_URL_char_check": checkMsg = "?, %, = 는 제외문자로 사용하실 수 없습니다.";
            break;
            case "dynamicPage_URL_add_success": checkMsg = "등록이 완료되었습니다.";
            break;
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            case "URL_del_check": checkMsg = "삭제할 동적페이지를 선택하세요.";
            break;
            case "exceptChar_alert": checkMsg = "제외문자를 입력하세요.";
            break;
            case "exceptAdd_confirm": checkMsg = "선택한 제외 문자를 등록하시겠습니까?\n" + "확인을 클릭하면\n" + "URL에 선택한 문자 이후 값은\n" + "제외하여 분석합니다.";
            break;
            case "exceptAdd_alert": checkMsg = "등록이 완료되었습니다.";
            break;
            case "delExceptCharCheck_alert": checkMsg = "삭제할 URL 제외문자를 선택하세요.";
            break;
            case "delExceptChar_confirm": checkMsg = "선택한 URL 제외문자를 삭제하겠습니까?\n" + "URL 제외 문자에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "delExceptChar_alert": checkMsg = "삭제가 완료되었습니다.";
            break;
            case "analysisURL_null": checkMsg = "분석할 URL을 입력하세요.";
            break;
            case "searchVar_null": checkMsg = "내부검색 결과 페이지에서 검색어 변수를 입력하세요.";
            break;
            case "innerSearch_add_alert": checkMsg = "등록이 완료되었습니다.";
            break;
            case "innerSearch_deplication_add_alert": checkMsg = "등록된 내부검색 페이지 URL이 있습니다.\n" + "다시 입력해주세요.";
            break;
            case "innerSearch_del_null": checkMsg = "삭제할 내부검색 URL을 입력하세요.";
            break;
            case "innerSearch_del_confirm": checkMsg = "선택한 내부검색 URL을 삭제하시겠습니까?\n" + "내부검색어 변수에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "innerSearch_del_alert": checkMsg = "삭제가 완료되었습니다.";
            break;
            case "menuDel_confirm": checkMsg = "메뉴를 삭제하시겠습니까?\n" + "메뉴를 삭제하면, 메뉴에 대한 분석이 중지됩니다.";
            break;
            case "pageManage_selectNull": checkMsg = "삭제할 페이지를 선택하세요.";
            break;
            case "pattern_menu_null": checkMsg = "메뉴를 먼저 선택하세요.";
            break;
            case "pattern_null": checkMsg = "패턴을 입력하세요.";
            break;
            case "pattern_register": checkMsg = "등록이 완료되었습니다.";
            break;
            case "pattern_del_null": checkMsg = "삭제할 패턴을 선택하세요.";
            break;
            case "pattern_del_confirm": checkMsg = "선택한 패턴을 삭제하시겠습니까?\n" + "해당 패턴에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "pattern_del_alert": checkMsg = "삭제가 완료되었습니다.";
            break;
            case "campaignName_null": checkMsg = "캠페인명을 입력하세요.";
            break;
            case "bannerName_null": checkMsg = "소재를 입력하세요.";
            break;
            case "linkURL_null": checkMsg = "연결 URL을 입력하세요.";
            break;
            case "innerBanner_add_alert": checkMsg = "등록이 완료되었습니다.";
            break;
            case "innerBanner_duplicationAdd": checkMsg = "이미 사용한 캠페인명입니다.\n" + "다른 캠페인명을 사용해주세요.";
            break;
            case "innerBanner_del_null": checkMsg = "삭제할 내부배너 설정을 선택하세요.";
            break;
            case "innerBanner_del_confirm": checkMsg = "선택한 내부배너 설정을 삭제하시겠습니까?\n" + "내부배너 설정에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "innerBanner_del_alert": checkMsg = "내부배너 설정 삭제가 완료되었습니다.";
            break;
            case "downPattern_null": checkMsg = "다운로드 패턴을 입력하세요.";
            break;
            case "downPattern_add_alert": checkMsg = "등록이 완료되었습니다.";
            break;
            case "downPattern_duplicationAdd_alert": checkMsg = "다운로드 패턴값이 이미 등록되어 있습니다.";
            break;
            case "downPattern_del_null": checkMsg = "삭제할 다운로드 패턴을 선택하세요.";
            break;
            case "downPattern_del_confirm": checkMsg = "다운로드 패턴을 삭제하시겠습니까?\n" + "다운로드 패턴을 삭제하면,\n" + "파일다운로드 분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
            break;
            case "downPattern_del_alert": checkMsg = "삭제가 완료되었습니다.";
            break;
            case "outLinkBanner_promotionName_null": checkMsg = "프로모션명을 입력하세요.";
            break;
            case "outLinkBanner_name_null": checkMsg = "아웃링크 배너명을 입력하세요.";
            break;
            case "filePath_null": checkMsg = "파일경로를 입력하세요.";
            break;
            case "outLinkBanner_linkURL_null": checkMsg = "연결URL을 입력하세요.";
            break;
            case "outLinkBanner_linkURL_badURL": checkMsg = "올바른 URL을 입력하세요.";
            break;
            case "outLinkBanner_add_alert": checkMsg = "등록이 완료되었습니다.";
            break;
            case "outLinkBanner_promotionName_duplication": checkMsg = "이미 사용한 프로모션명입니다.\n" + "다른 프로모션명을 사용해주세요.";
            break;
            case "outLinkBanner_modify_alert": checkMsg = "수정이 완료되었습니다.";
            break;
            case "outLinkBanner_del_null": checkMsg = "삭제할 아웃링크 배너를 선택하세요.";
            break;
            case "outLinkBanner_del_confirm": checkMsg = "프로모션을 삭제하시겠습니까?\n" + "등록된 배너도 함께 삭제 됩니다.\n" + "\n" + "아웃링크 배너에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.\n" + "\n" + "웹사이트에 적용된 페이지내 삽입코드를\n" + "삭제해 주시기 바랍니다.";
            break;
            case "outLinkBanner_del_alert": checkMsg = "삭제가 완료되었습니다.";
            break;
            
        }
		Thread.onSpinWait();
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		    $(".modal-backdrop").waitUntil(hidden, 10000);
		} else if (msgCheck.isEmpty()) {
			System.out.println(" *** ☆★☆★☆★ pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - msgCheck is Empty ... ☆★☆★☆★ *** ");
			close();
		} else {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Fail ... !@#$%^&*() *** ");
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
  	public static void sleep(long millis) {
  		try {
  			Thread.sleep(millis);
  		} catch (InterruptedException ex) {
  		}
  	}

	@Test(priority = 0)
	public void URLSetting_dynamicPage_add() {
		System.out.println(" ! ----- URLSetting_dynamicPage_add Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 10000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		$("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("방문수")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".accordion-toggle", 3).click();
		$(By.linkText("URL 설정")).waitUntil(visible, 10000);
		$(By.linkText("URL 설정")).click();
		$(".col-xs-5").waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-info", 0).text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(10, 7, "dynamicPage_URL_null");
		$("#page-url").setValue("/test");
		$("#btn-add").click();
		valCheck(11, 8, "dynamicPage_URL_check");
		$("#page-url").setValue("http://test00011.org/search?q=123");
		$(".w300").setValue("=");
		$("#btn-add").click();
		valCheck(12, 9, "dynamicPage_exclude_URL_char_check");
		$(".w300").setValue("test");
		$("#btn-add").click();
		valCheck(13, 10, "dynamicPage_URL_add_success");
		$(".text-nowrap").waitUntil(visible, 10000);
		
		
		
		
		
		
		
		System.out.println(" ! ----- URLSetting_URLInclusion End ----- ! ");
	}
	@Test(priority = 1)
	public void URLSetting_URLExcept_Add() {
		System.out.println(" ! ----- URLSetting_URLExcept_Add Start ----- ! ");
		$(By.linkText("URL제외")).click();
		$(".col-xs-9").waitUntil(visible, 10000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("th", 0).text();
		if(pageLoadCheck.equals("제외 문자 선택")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(5, 3, "exceptChar_alert");
		$(By.xpath("//div[@id='addset']/div/table/tbody/tr/td/div/div/label[4]")).click();
		$(".gui-input").waitUntil(visible, 10000);
		$("#btn-add").click();
		valCheck(6, 4, "exceptChar_alert");
		$(".input-sm", 0).setValue(date);
		$("#btn-add").click();
		valCheck(7, 5, "exceptAdd_confirm");
		valCheck(8, 7, "exceptAdd_alert");
		$("th", 0).waitUntil(hidden, 10000);
		$(".col-xs-9", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_Add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_Add Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_Add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_URLExcept_search() {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $("td", 8).waitUntil(visible, 10000);
	    String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox del Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		$(".muted").waitUntil(visible, 10000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".muted").waitUntil(hidden, 10000);
	    pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox set Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox set Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept setList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_search End ----- ! ");
	}
	@Test(priority = 3)
	public void URLSetting_URLExcept_del() {
		System.out.println(" ! ----- URLSetting_URLExcept_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(5, 3, "delExceptCharCheck_alert");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(6, 4, "delExceptChar_confirm");
	    valCheck(7, 6, "delExceptChar_alert");
		$(".muted").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_del End ----- ! ");
	}
	@Test(priority = 4)
	public void URLSetting_innerSearch_add() {
		System.out.println(" ! ----- URLSetting_innerSearch_add Start ----- ! ");
		$(By.linkText("내부검색")).click();
		$(".col-xs-8").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* 분석하고자 하는 내부검색 결과 페이지 URL을 입력합니다.")) {
			System.out.println(" *** URLSetting_innerSearch_add UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(4, 3, "analysisURL_null");
		$("#page-url").setValue("/" + date);
		$("#btn-add").click();
		valCheck(5, 4, "searchVar_null");
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		valCheck(6, 5, "innerSearch_add_alert");
		$(".text-nowrap", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_add next page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add next page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_add End ----- ! ");
	}
	@Test(priority = 5)
	public void URLSetting_innerSearch_duplicationAdd() {
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* 분석하고자 하는 내부검색 결과 페이지 URL을 입력합니다.")) {
			System.out.println(" *** URLSetting_innerSearch_duplicationAdd UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_duplicationAdd UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#page-url").setValue("/" + date);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		valCheck(4, 3, "innerSearch_deplication_add_alert");
		$("#btn-add-cancel").click();
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd End ----- ! ");
	}
	@Test(priority = 6)
	public void URLSetting_innerSearch_search() {
		System.out.println(" ! ----- URLSetting_innerSearch_search Start ----- ! ");
		$(By.linkText("내부검색")).click();
		String pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch search_selectbox load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $(".col-xs-8").waitUntil(visible, 10000);
		pageLoadCheck = $(".col-xs-8").text();
		if(pageLoadCheck.equals("페이지 URL")) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		$(".muted").waitUntil(visible, 10000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".text-nowrap", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch setList search Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch setList page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_search End ----- ! ");
	}
	@Test(priority = 7)
	public void URLSetting_innerSearch_del() {
		System.out.println(" ! ----- URLSetting_innerSearch_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "innerSearch_del_null");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "innerSearch_del_confirm");
	    valCheck(6, 6, "innerSearch_del_alert");
		$(".muted").waitUntil(visible, 10000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_innerSearch_del Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}