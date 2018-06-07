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
    SimpleDateFormat number_format = new SimpleDateFormat("YYMMDDhhmmss");
    SimpleDateFormat number_format1 = new SimpleDateFormat("YYYY-MM-DD");
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
        switch(val){
            case "URL_null": checkMsg = "동적페이지URL을 입력하세요.";
            break;
            case "URL_check": checkMsg = "입력하신 페이지URL은\n" + "동적페이지에 해당되지 않습니다.\n" + "다시 확인 후 입력해주세요.";
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
            case "1": checkMsg = "";
            break;
            case "2": checkMsg = "";
            break;
            case "3": checkMsg = "";
            break;
            case "4": checkMsg = "";
            break;
            case "5": checkMsg = "";
            break;
            case "6": checkMsg = "";
            break;
            case "7": checkMsg = "";
            break;
            case "8": checkMsg = "";
            break;
            case "9": checkMsg = "";
            break;
            
        }
		$(".modal-backdrop").waitUntil(visible, 10000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " - check Fail ... !@#$%^&*() *** ");
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
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("방문수")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... !@#$%^&*() *** ");
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
			System.out.println(" *** URLSetting_URLInclusion Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("#page-url").waitUntil(visible, 3000);
		pageLoadCheck = $("#btn-add").text();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** URLSetting_URLInclusion register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLInclusion register UI load Fail ... !@#$%^&*() *** ");
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
		$(By.linkText("URL제외")).click();
		Thread.sleep(1000);
		$(".col-xs-9").waitUntil(visible, 3000);
		String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		Thread.sleep(1500);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 3000);
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
			System.out.println(" *** URLSetting_URLExcept_Add Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_Add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_URLExcept_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_URLExcept_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    String pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    Thread.sleep(1500);
	    pageLoadCheck = $(".col-xs-9", 0).text();
		if(pageLoadCheck.equals("URL 제외 문자(패턴)")) {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept_selectbox Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(1500);
		$("td", 3).waitUntil(visible, 3000);
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
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_URLExcept_del End ----- ! ");
	}
	@Test(priority = 4)
	public void URLSetting_innerSearch_add() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_add Start ----- ! ");
		$(By.linkText("내부검색")).click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 3000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* 분석하고자 하는 내부검색 결과 페이지 URL을 입력합니다.")) {
			System.out.println(" *** URLSetting_innerSearch_add UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck(4, 3, "analysisURL_null");
		Thread.sleep(1000);
		$("#page-url").setValue("/" + date);
		$("#btn-add").click();
		valCheck(5, 4, "searchVar_null");	
		Thread.sleep(1000);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		Thread.sleep(1000);
		valCheck(6, 5, "innerSearch_add_alert");
		Thread.sleep(3000);
		$(".text-nowrap", 1).waitUntil(visible, 3000);
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
	public void URLSetting_innerSearch_duplicationAdd() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
		$("h5", 1).waitUntil(visible, 3000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("* 분석하고자 하는 내부검색 결과 페이지 URL을 입력합니다.")) {
			System.out.println(" *** URLSetting_innerSearch_add UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_add UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#page-url").setValue("/" + date);
		$(".input-sm", 1).setValue(date);
		$("#btn-add").click();
		Thread.sleep(1000);
		valCheck(4, 3, "innerSearch_deplication_add_alert");
		$("#btn-add-cancel").click();
		System.out.println(" ! ----- URLSetting_innerSearch_duplicationAdd End ----- ! ");
	}
	@Test(priority = 6)
	public void URLSetting_innerSearch_search() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_search Start ----- ! ");
		$(By.linkText("내부검색")).click();
		Thread.sleep(3000);
		String pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch search_selectbox load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    Thread.sleep(1500);
	    $(".col-xs-8").waitUntil(visible, 3000);
		pageLoadCheck = $(".col-xs-8").text();
		if(pageLoadCheck.equals("페이지 URL")) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox N select page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_URLExcept delList search Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    Thread.sleep(1500);
	    $(".text-nowrap", 1).waitUntil(visible, 3000);
		pageLoadCheck = $(".text-nowrap", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_search_selectbox Y select load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		Thread.sleep(1500);
		$("td", 5).waitUntil(visible, 3000);
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
	public void URLSetting_innerSearch_del() throws InterruptedException {
		System.out.println(" ! ----- URLSetting_innerSearch_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 3000);
	    $("#btn-list-select-delete").click();
	    valCheck(4, 3, "innerSearch_del_null");
	    $("#inlineCheckbox1").click();
	    $("#btn-list-select-delete").click();
	    valCheck(5, 4, "innerSearch_del_confirm");
	    Thread.sleep(1500);
	    valCheck(6, 6, "innerSearch_del_alert");
	    Thread.sleep(3000);
		$(".muted").waitUntil(visible, 3000);
		String pageLoadCheck = $(".muted").text();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** URLSetting_innerSearch_del Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_innerSearch_del Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_innerSearch_del End ----- ! ");
	}
	@Test(priority = 8)
	public void pageGroupSetting_menuAddDel() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_menuAddDel Start ----- ! ");
	    $(By.linkText("페이지그룹 설정")).click();
	    $("#btn-tree-add").waitUntil(visible, 3000);
		String pageLoadCheck = $("#btn-tree-add").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** pageGroupSetting_menuAdd Page load Success !! *** ");
		} else {
			System.out.println(" ***pageGroupSetting_menuAdd Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $("#btn-tree-add").click();
	    $(By.xpath("(//input[@value=''])[2]")).setValue(date);
	    $(By.id("ui-id-1")).click();
	    $(".fancytree-lastsib").click();
	    $("#btn-tree-delete").click();
	    valCheck(5, 5, "menuDel_confirm");
		System.out.println(" ! ----- pageGroupSetting_menuAddDel End ----- ! ");
	}
	@Test(priority = 9)
	public void pageGroupSetting_pageManage() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_pageManage Start ----- ! ");
		brokenLinkCheck("pageDownload", "https://new.acecounter.com/setting/contents/pageGroup/downloadPage?key=&match=false&query=&use_yn=y");
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    String pageLoadCheck = $("td", 0).text();
	    if(pageLoadCheck.equals("등록된 페이지가 없습니다.\n" + 
	    		"메뉴를 선택한 후 페이지를 등록하세요.")) {
	    	System.out.println(" *** pageGroupSetting_pageManage search Success !! *** ");
	    } else {
	    	System.out.println(" *** pageGroupSetting_pageManage search Fail ... !@#$%^&*() *** ");
	    	close();
	    }
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 5000);
	    $("#btn-list-select-delete").click();
	    valCheck(6, 7, "pageManage_selectNull");
	    System.out.println(" ! ----- pageGroupSetting_pageManage End ----- ! ");
	}
	@Test(priority = 10)
	public void pageGroupSetting_patternRegister() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_patternRegister Start ----- ! ");
		$(By.linkText("패턴등록")).click();
		valCheck(7, 8, "pattern_menu_null");
		$(".fancytree-title", 0).click();
		$(By.linkText("패턴등록")).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("패턴등록")) {
			System.out.println(" *** pageGroupSetting_patternRegister page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternRegister page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-pattern-add").click();
		valCheck(9, 9, "pattern_null");
		$(".input-sm").setValue(date);
		$("#btn-pattern-add").click();
		valCheck(10, 10, "pattern_register");
		System.out.println(" ! ----- pageGroupSetting_patternRegister End ----- ! ");
	}
	@Test(priority = 11)
	public void pageGroupSetting_patternManagement() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_patternManagement Start ----- ! ");
		$(By.linkText("패턴관리")).click();
		String pageLoadCheck = $(".col-xs-5", 1).text();
		if(pageLoadCheck.equals("패턴")) {
			System.out.println(" *** pageGroupSetting_patternManagement page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternManagement page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("pattern_query")).sendKeys(date);
	    $("#btn-pattern-search").click();
		$("#btn-list-pattern-delete").click();
		$("#btn-list-select-pattern-delete").waitUntil(visible, 3000);
		$("#btn-list-select-pattern-delete").click();
		valCheck(11, 11, "pattern_del_null");
	    $(By.xpath("(//input[@id='inlineCheckbox1'])[2]")).click();
		$("#btn-list-select-pattern-delete").click();
		valCheck(12, 12, "pattern_del_confirm");
		Thread.sleep(1000);
		valCheck(13, 14, "pattern_del_alert");
		System.out.println(" ! ----- pageGroupSetting_patternManagement End ----- ! ");
	}
	@Test(priority = 12)
	public void pageGroupSetting_pageUpload() throws InterruptedException {
		System.out.println(" ! ----- pageGroupSetting_pageUpload Start ----- ! ");
		$(By.linkText("페이지업로드")).click();
		$("h3", 3).waitUntil(visible, 3000);
		String pageLoadCheck = $("h3", 3).text();
		if(pageLoadCheck.equals("페이지업로드")) {
			System.out.println(" *** pageGroupSetting_pageUpload page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_pageUpload page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- pageGroupSetting_pageUpload End ----- ! ");
	}
	@Test(priority = 13)
	public void innerBanner_add() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_add Start ----- ! ");
	    $(By.linkText("내부배너 설정")).click();
	    $(".btn-dark", 0).waitUntil(visible, 3000);
	    Thread.sleep(3000);
		String pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 5000);
	    pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** innerBanner_add Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(4, 4, "campaignName_null");
	    $(".input-sm", 0).setValue(date);
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(5, 5, "bannerName_null");
	    $(".input-sm", 1).setValue(date);
	    $(".input-sm", 2).setValue("");
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(6, 6, "linkURL_null");
	    $(".input-sm", 2).setValue("http://" + date + ".com");
	    $("#btn-save").click();
	    Thread.sleep(1000);
	    valCheck(7, 7, "innerBanner_add_alert");
	    Thread.sleep(2500);
	    $(".btn-dark", 0).waitUntil(visible, 3000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_add End ----- ! ");
	}
	@Test(priority = 14)
	public void innerBanner_duplicationAdd() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 5000);
	    String pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** innerBanner_duplicationAdd Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".input-sm", 0).setValue(date);
	    $(".input-sm", 1).setValue(date);
	    $("#btn-save").click();
	    valCheck(4, 4, "innerBanner_duplicationAdd");
	    $(".w100", 1).click();
	    Thread.sleep(1500);
	    $(".btn-dark", 0).waitUntil(visible, 3000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 15)
	public void innerBanner_search() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_search Start ----- ! ");
	    $("#s_key").setValue(date);
	    $("#btn-search").click();
		String pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_search list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-detail-view").click();
		$(".text-left", 1).waitUntil(visible, 3000);
		pageLoadCheck = $(".text-left", 1).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** innerBanner_search show link load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search show link load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_search End ----- ! ");
	}
	@Test(priority = 16)
	public void innerBanner_linkURLdownload() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_linkURLdownload Start ----- ! ");
	    $(".btn-dark", 0).click();
	    $(".modal-backdrop").waitUntil(visible, 5000);
	    String pageLoadCheck = $(".btn-fileDown").text();
	    if(pageLoadCheck.equals("다운로드")) {
	    	System.out.println(" *** innerBanner_linkURLdownload popup load Success !! *** ");
	    } else {
	    	System.out.println(" *** innerBanner_linkURLdownload popup load Fail ... !@#$%^&*() *** ");	   
	    	close();
	    }
	    brokenLinkCheck("linkURLdownload", "https://new.acecounter.com/setting/contents/inBanner/download?campaign_type=inlink&down_type=urlDown&down_key=" + date1 + "~" + date1);
	    $(".close", 0).click();
		System.out.println(" ! ----- innerBanner_linkURLdownload End ----- ! ");
	}
	@Test(priority = 17)
	public void innerBanner_del() throws InterruptedException {
		System.out.println(" ! ----- innerBanner_del Start ----- ! ");
	    $(".btn-gray", 0).click();
	    $("#btn-del").waitUntil(visible, 3000);
	    String pageLoadCheck = $("#btn-del").text();
	    if(pageLoadCheck.equals("선택 항목 삭제")) {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    } else {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    	close();
	    }
	    $("#btn-del").click();
	    valCheck(3, 8, "innerBanner_del_null");
	    $("#campaignAllChk").click();
	    $("#btn-del").click();
	    valCheck(4, 9, "innerBanner_del_confirm");
	    Thread.sleep(1500);
	    valCheck(5, 11, "innerBanner_del_alert");
	    Thread.sleep(1500);
	    $(".btn-dark", 0).waitUntil(visible, 3000);
	    pageLoadCheck = $(".btn-dark", 0).text();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_del Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}