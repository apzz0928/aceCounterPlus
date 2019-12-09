package aceCounterPlus; //통계설정 > 컨텐츠설정 (URL설정, 페이지그룹 설정, 내부배너 설정)

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

public class contentSetting {
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
	        case "URLSetting_dynamicPage_URL_null": checkMsg = "동적페이지URL을 입력하세요.";
	        break;
	        case "URLSetting_dynamicPage_URL_check": checkMsg = "입력하신 페이지URL은\n" + "동적페이지에 해당되지 않습니다.\n" + "다시 확인 후 입력해주세요.";
	        break;
	        case "URLSetting_dynamicPage_exclude_URL_char_check": checkMsg = "?, %, = 는 제외문자로 사용하실 수 없습니다.";
	        break;
	        case "URLSetting_dynamicPage_URL_add_success": checkMsg = "등록이 완료되었습니다.";
	        break;
	        case "URLSetting_dynamicPage_update_confirm": checkMsg = "등록된 동적페이지입니다.\n" + "수정하시겠습니까?";
	        break;
	        case "URLSetting_dynamicPage_update_alert": checkMsg = "수정이 완료되었습니다.";
	        break;                 
	        case "URLSetting_dynamicPage_modify_alert": checkMsg = "수정이 완료되었습니다.";
	        break;                 
	        case "URLSetting_dynamicPage_delete_confirm": checkMsg = "동적페이지를 삭제하시겠습니까?\n" + "동적페이지 변수에 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
	        break;                 
	        case "URLSetting_dynamicPage_delete_alert": checkMsg = "삭제가 완료되었습니다.";
	        break;                 
	        case "URLSetting_pageChange_searchReq_null": checkMsg = "페이지 검색 조건을 입력하세요.";
	        break;                 
	        case "URLSetting_pageChange_URL_regexp": checkMsg = "페이지 검색 조건에 정규식이 포함되어 있지 않습니다.\n" + "다시 확인하세요.";
	        break;                 
	        case "URLSetting_pageChange_pageURL_null": checkMsg = "교체할 페이지URL을 입력하세요." ;
	        break;                 
	        case "URLSetting_pageChange_URL_add": checkMsg = "등록이 완료되었습니다.";
	        break;                 
	        case "URLSetting_pageChange_delete_check_null": checkMsg = "삭제할 페이지 교체를 선택하세요.";
	        break;                 
	        case "URLSetting_pageChange_delete_confirm": checkMsg =  "페이지 교체를 삭제하시겠습니까?\n" + "페이지 교체에 대한 분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다";
	        break;                 
	        case "URLSetting_pageChange_delete_alert": checkMsg =  "삭제가 완료되었습니다.";
	        break;                 
	        case "URLSetting_internalSearch_add_URL_null": checkMsg =  "페이지URL을 입력하세요.";
	        break;                 
	        case "URLSetting_internalSearch_add_var_null": checkMsg =  "내부검색변수를 입력하세요.";
	        break;                 
	        case "URLSetting_internalSearch_add_alert": checkMsg =  "등록이 완료되었습니다.";
	        break;
	        case "URLSetting_internalSearch_delete_check_null": checkMsg =  "삭제할 페이지URL을 선택하세요.";
	        break;        
	        case "URLSetting_internalSearch_delete_confirm": checkMsg =  "내부검색에 대한 페이지URL을 삭제하시겠습니까?\n" + "내부검색에 대한 분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
	        break;
	        case "URLSetting_internalSearch_delete_alert": checkMsg =  "삭제가 완료되었습니다.";
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
            case "innerBanner_del_confirm": checkMsg = "선택한 내부배너 설정을 삭제하시겠습니까?\n" + "삭제 후 복구가 불가능합니다.\n" + "(삭제한 캠페인명과 동일한 캠페인명은 추가 불가합니다.)";
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
		//$(".modal-backdrop").waitUntil(visible, 10000);
        sleep(500);
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
	        System.out.println(" *** ☆★☆★☆★ val : " + val + " // pTag text is : " + msgCheck +  " // - msgCheck is Empty ... ☆★☆★☆★ *** ");
			System.out.println("checkMsg is : " + checkMsg);
			close();
		} else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
	        System.out.println(" *** // val : " + val + " // pTag text is : " + msgCheck +  " // - check Fail ... !@#$%^&*() *** ");
			System.out.println("checkMsg is : " + checkMsg);
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
		$("#redirectConfBtn").click();
		$(".input-sm", 0).waitUntil(visible, 10000);
		$(".accordion-toggle", 3).click();
		System.out.println(" ! ----- Login End ----- ! ");
	}
	@Test(priority = 1)
	public void URLSetting_dynamicPage_add() {
		System.out.println(" ! ----- URLSetting_dynamicPage_add Start ----- ! ");
		$(By.linkText("URL 설정")).waitUntil(visible, 10000);
		$(By.linkText("URL 설정")).click();
		sleep(1500);
		pageLoadCheck = $("td", 5).text().trim();
		if(pageLoadCheck.equals("/search")) {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-add").waitUntil(visible, 15000);
		pageLoadCheck = $("#btn-add").text().trim();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_add register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		valCheck("URLSetting_dynamicPage_URL_null");
		$("#page-url").setValue("/" + date);
		$("#btn-add").click();
		valCheck("URLSetting_dynamicPage_URL_check");
		$("#page-url").click();
		$("#page-url").setValue("/" + date + "?첫등록변수=123");
		$(".w300").setValue("=");
		$("#btn-add").click();
		valCheck("URLSetting_dynamicPage_exclude_URL_char_check");
		$(".w300").setValue("첫등록제외문자");
		$("#btn-add").click();
		valCheck("URLSetting_dynamicPage_URL_add_success");
		$("#btn-add").waitUntil(hidden, 10000);
		$(".text-nowrap").waitUntil(visible, 10000);
		System.out.println(" ! ----- URLSetting_dynamicPage_add End ----- ! ");
	}
	@Test(priority = 2)
	public void URLSetting_dynamicPage_update() {
		System.out.println(" ! ----- URLSetting_dynamicPage_update Start ----- ! ");
		$(".btn-info", 0).waitUntil(visible, 10000);
		$(".btn-info", 0).click();
		$("#btn-add").waitUntil(visible, 15000);
		pageLoadCheck = $("#btn-add").text().trim();
		if(pageLoadCheck.equals("등록")) {
			System.out.println(" *** URLSetting_dynamicPage_update register UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_update register UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#page-url").setValue("/" + date + "?" + date + "=123");
		$(".w300").setValue("test");
		$("#btn-add").click();
		valCheck("URLSetting_dynamicPage_update_confirm");
		valCheck("URLSetting_dynamicPage_update_alert");
		$(".text-nowrap").waitUntil(visible, 10000);
		System.out.println(" ! ----- URLSetting_dynamicPage_update End ----- ! ");
	}
	@Test(priority = 3)
	public void URLSetting_dynamicPage_modify() {
		System.out.println(" ! ----- URLSetting_dynamicPage_modify Start ----- ! ");
		$("#btn-add").waitUntil(hidden, 10000); 
		$(".br-dark", 0).waitUntil(visible, 10000);
		$(".br-dark", 0).click();
		sleep(1000);
		pageLoadCheck = $(".btn-url-save", 0).text().trim();
		if(pageLoadCheck.equals("저장")) {
			System.out.println(" *** URLSetting_dynamicPage_modify save UI load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_modify save UI load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#list-param-0").setValue(date);
		$(".btn-url-save").click();
		valCheck("URLSetting_dynamicPage_modify_alert");
		$(".text-nowrap").waitUntil(visible, 10000);
		System.out.println(" ! ----- URLSetting_dynamicPage_modify End ----- ! ");
	}
	@Test(priority = 4)
	public void URLSetting_dynamicPage_delete() {
		System.out.println(" ! ----- URLSetting_dynamicPage_delete Start ----- ! ");
		$("#btn-search", 0).waitUntil(visible, 10000);
		$(".br-l-n", 0).click();
		$(".br-l-n", 0).setValue(date + "a");
		$("#btn-search", 0).click();
		$("#btn-search", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("tr", 4).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_dynamicPage_delete No Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_delete No Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n", 0).setValue(date);
		$("#btn-search", 0).click();
		$("#btn-search", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		if(pageLoadCheck.equals("/" + date)) {
			System.out.println(" *** URLSetting_dynamicPage_delete Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_delete Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-list-delete").click();
		$("#btn-list-select-delete").waitUntil(visible, 10000);
		$("#list-checkbox-0").click();
		$("#btn-list-select-delete").click();
		valCheck("URLSetting_dynamicPage_delete_confirm");
		valCheck("URLSetting_dynamicPage_delete_alert");
		$(".btn-xs").waitUntil(hidden, 10000);
		pageLoadCheck = $("tr", 4).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_dynamicPage_delete Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_dynamicPage_delete Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_dynamicPage_delete End ----- ! ");
	}
	@Test(priority = 5)
	public void URLSetting_pageChange_add() {
		System.out.println(" ! ----- URLSetting_pageChange_add Start ----- ! ");
		$(By.linkText("페이지 교체")).click();
		$("th", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		if(pageLoadCheck.equals("/search/label/missing/[0-9A-Za-z]*")) {
			System.out.println(" *** URLSetting_pageChange_add Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_pageChange_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("#btn-add").waitUntil(visible, 10000);
		$("#btn-add").click();
		valCheck("URLSetting_pageChange_searchReq_null");
		$(By.name("targetPage")).setValue(date);
		$("#btn-add").click();
		valCheck("URLSetting_pageChange_URL_regexp");
		$(By.name("targetPage")).setValue("/" + date + "/[0-9]*");
		$("#btn-add").click();
		valCheck("URLSetting_pageChange_pageURL_null");
		$(By.name("replacementPage")).setValue("/" + date);
		$("#btn-add").click();
		valCheck("URLSetting_pageChange_URL_add");
		$("#list-page-param-0").waitUntil(visible, 10000);
		sleep(1000);
		pageLoadCheck = $("#list-page-param-0").text().trim();
		if(pageLoadCheck.equals("/" + date)) {
			System.out.println(" *** URLSetting_pageChange_add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_pageChange_add Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_pageChange_add End ----- ! ");
	}
	@Test(priority = 6)
	public void URLSetting_pageChange_delete() {
		System.out.println(" ! ----- URLSetting_pageChange_delete Start ----- ! ");
		$(".br-l-n").waitUntil(visible, 10000);
		$(".br-l-n").setValue(date + "a");
		$("#btn-search").click();
		$("td", 5).waitUntil(hidden, 10000);
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_pageChange_No Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_pageChange_No Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		pLC = pageLoadCheck.split("/");
		if(pLC[1].equals(date)) {
			System.out.println(" *** URLSetting_pageChange_Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_pageChange_Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-list-delete", 0).click();
		$("#btn-list-select-delete").waitUntil(visible, 10000);
		$("#btn-list-select-delete").click();
		valCheck("URLSetting_pageChange_delete_check_null");
		$("#list-checkbox-0").click();
		$("#btn-list-select-delete").click();
		valCheck("URLSetting_pageChange_delete_confirm");
		$("#btn-modal-alert-yes").waitUntil(hidden, 10000);
		//$(".btn-sm", 6).waitUntil(visible, 10000);
		valCheck("URLSetting_pageChange_delete_alert");
		$("td", 7).waitUntil(hidden, 10000);
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_pageChange_delete Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_pageChange_delete Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_pageChange_delete End ----- ! ");
	}
	@Test(priority = 7)
	public void URLSetting_internalSearch_add() {
		System.out.println(" ! ----- URLSetting_internalSearch_add Start ----- ! ");
		$(By.linkText("내부검색")).click();
		$("td", 5).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		if(pageLoadCheck.equals("/search")) {
			System.out.println(" *** URLSetting_internalSearch Page load Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_internalSearch Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-add").click();
		$("#btn-reg").waitUntil(visible, 10000);
		$("#btn-reg").click();
		valCheck("URLSetting_internalSearch_add_URL_null");
		$(By.name("page_url")).setValue("/" + date);
		$("#btn-reg").click();
		valCheck("URLSetting_internalSearch_add_var_null");
		$(By.name("internal_search_param")).setValue(date);
		$("#btn-reg").click();	
		valCheck("URLSetting_internalSearch_add_alert");
		$("#btn-reg").waitUntil(hidden, 10000);
		$("td", 6).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 6).text().trim();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_internalSearch_add Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_internalSearch_add Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_internalSearch_add End ----- ! ");
	}
	@Test(priority = 8)
	public void URLSetting_internalSearch_delete() {
		System.out.println(" ! ----- URLSetting_internalSearch_delete Start ----- ! ");
		$(".br-l-n").setValue(date + "a");
		$("#btn-search").click();
		$("td", 7).waitUntil(hidden, 10000);
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_internalSearch_No Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_internalSearch_No Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 6).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 6).text().trim();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** URLSetting_internalSearch_Search Result Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_internalSearch_Search Result Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-list-delete", 0).click();
		$("#btn-list-select-delete").waitUntil(visible, 10000);
		$("#btn-list-select-delete").click();
		valCheck("URLSetting_internalSearch_delete_check_null");
		$("#list-checkbox-0").click();
		$("#btn-list-select-delete").click();
		valCheck("URLSetting_internalSearch_delete_confirm");
		$("#btn-modal-alert-yes").waitUntil(hidden, 10000);
		//$(".btn-sm", 6).waitUntil(visible, 10000);
		valCheck("URLSetting_internalSearch_delete_alert");
		$("td", 6).waitUntil(hidden, 10000);
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[0].equals("등록된")) {
			System.out.println(" *** URLSetting_internalSearch_delete Success !! *** ");
		} else {
			System.out.println(" *** URLSetting_internalSearch_delete Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- URLSetting_internalSearch_delete End ----- ! ");
	}
	@Test(priority = 11)
	public void pageGroupSetting_menuAddDel() {
		System.out.println(" ! ----- pageGroupSetting_menuAddDel Start ----- ! ");
		$(By.linkText("페이지그룹 설정")).waitUntil(visible, 10000);
	    $(By.linkText("페이지그룹 설정")).click();
	    $("#btn-tree-add").waitUntil(visible, 10000);
		pageLoadCheck = $("#btn-tree-add").text().trim();
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
	    valCheck("menuDel_confirm");
		System.out.println(" ! ----- pageGroupSetting_menuAddDel End ----- ! ");
	}
	@Test(priority = 12)
	public void pageGroupSetting_pageManage() {
		System.out.println(" ! ----- pageGroupSetting_pageManage Start ----- ! ");
		brokenLinkCheck("pageDownload", "https://new.acecounter.com/setting/contents/pageGroup/downloadPage?key=&match=false&query=&use_yn=y");
		$(".br-l-n").waitUntil(visible, 10000);
		$(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    pageLoadCheck = $("td", 0).text().trim();
	    if(pageLoadCheck.equals("등록된 페이지가 없습니다.\n" + "메뉴를 선택한 후 페이지를 등록하세요.")) {
	    	System.out.println(" *** pageGroupSetting_pageManage search Success !! *** ");
	    } else {
	    	System.out.println(" *** pageGroupSetting_pageManage search Fail ... !@#$%^&*() *** ");
	    	close();
	    }
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 5000);
	    $("#btn-list-select-delete").click();
	    valCheck("pageManage_selectNull");
	    System.out.println(" ! ----- pageGroupSetting_pageManage End ----- ! ");
	}
	@Test(priority = 13)
	public void pageGroupSetting_patternRegister() {
		System.out.println(" ! ----- pageGroupSetting_patternRegister Start ----- ! ");
		$(By.linkText("패턴등록")).click();
		valCheck("pattern_menu_null");
		$(".fancytree-title", 0).click();
		$(By.linkText("패턴등록")).click();
		$("h3", 2).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("패턴등록")) {
			System.out.println(" *** pageGroupSetting_patternRegister page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternRegister page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btn-pattern-add").click();
		valCheck("pattern_null");
		$(".input-sm").setValue(date);
		$("#btn-pattern-add").click();
		valCheck("pattern_register");
		System.out.println(" ! ----- pageGroupSetting_patternRegister End ----- ! ");
	}
	@Test(priority = 14)
	public void pageGroupSetting_patternManagement() {
		System.out.println(" ! ----- pageGroupSetting_patternManagement Start ----- ! ");
		sleep(1000);
		$(By.linkText("패턴관리")).click();
		$(".col-xs-5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".col-xs-5", 1).text().trim();
		if(pageLoadCheck.equals("패턴")) {
			System.out.println(" *** pageGroupSetting_patternManagement page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_patternManagement page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("pattern_query")).sendKeys(date);
	    $("#btn-pattern-search").click();
		$("#btn-list-pattern-delete").click();
		$("#btn-list-select-pattern-delete").waitUntil(visible, 10000);
		$("#btn-list-select-pattern-delete").click();
		valCheck("pattern_del_null");
	    $("#pattern-list-checkbox-0").click();
		$("#btn-list-select-pattern-delete").click();
		valCheck("pattern_del_confirm");
		valCheck("pattern_del_alert");
		System.out.println(" ! ----- pageGroupSetting_patternManagement End ----- ! ");
	}
	@Test(priority = 15)
	public void pageGroupSetting_pageUpload() {
		System.out.println(" ! ----- pageGroupSetting_pageUpload Start ----- ! ");
		$(By.linkText("페이지업로드")).click();
		$("h3", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 3).text().trim();
		if(pageLoadCheck.equals("페이지업로드")) {
			System.out.println(" *** pageGroupSetting_pageUpload page load Success !! *** ");
		} else {
			System.out.println(" *** pageGroupSetting_pageUpload page load Fail ... !@#$%^&*() *** ");
			System.out.println("pageLoadCheck is : " + pageLoadCheck);
			close();
		}
	    System.out.println(" ! ----- pageGroupSetting_pageUpload End ----- ! ");
	}
	@Test(priority = 21)
	public void innerBanner_add() {
		System.out.println(" ! ----- innerBanner_add Start ----- ! ");
		$(By.linkText("내부배너 설정")).waitUntil(visible, 10000);
	    $(By.linkText("내부배너 설정")).click();
	    $(".btn-info", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-info", 0).text().trim();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 10000);
	    pageLoadCheck = $(".notokr-medium").text().trim();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** innerBanner_add Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $("#btn-save").click();
	    sleep(1000);
	    valCheck("campaignName_null");
	    $(".input-sm", 0).setValue(date);
	    $("#btn-save").click();
	    valCheck("bannerName_null");
	    $(".input-sm", 1).setValue(date);
	    $(".input-sm", 2).setValue("");
	    $("#btn-save").click();
	    valCheck("linkURL_null");
	    $(".input-sm", 2).setValue("http://" + date + ".com");
	    $("#btn-save").click();
	    valCheck("innerBanner_add_alert");
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text().trim();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_add list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_add list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_add End ----- ! ");
	}
	@Test(priority = 22)
	public void innerBanner_duplicationAdd() {
		System.out.println(" ! ----- innerBanner_duplicationAdd Start ----- ! ");
		$(".btn-info", 0).click();
	    $(".notokr-medium").waitUntil(visible, 5000);
	    pageLoadCheck = $(".notokr-medium").text().trim();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** innerBanner_duplicationAdd Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd Page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".input-sm", 0).setValue(date);
	    $(".input-sm", 1).setValue(date);
	    $("#btn-save").click();
	    valCheck("innerBanner_duplicationAdd");
	    $(".w100", 1).click();
	    $(".panel-function", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text().trim();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_duplicationAdd list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 23)
	public void innerBanner_search() {
		System.out.println(" ! ----- innerBanner_search Start ----- ! ");
	    $("#s_key").setValue(date);
	    $("#btn-search").click();
		pageLoadCheck = $(".btn-dark", 0).text().trim();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_search list Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-detail-view").click();
		$(".text-left", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".text-left", 1).text().trim();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** innerBanner_search show link load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_search show link load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_search End ----- ! ");
	}
	@Test(priority = 24)
	public void innerBanner_linkURLdownload() {
		System.out.println(" ! ----- innerBanner_linkURLdownload Start ----- ! ");
	    $(".btn-dark", 0).click();
	    $(".modal-backdrop").waitUntil(visible, 5000);
	    pageLoadCheck = $(".btn-fileDown").text().trim();
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
	@Test(priority = 25)
	public void innerBanner_del() {
		System.out.println(" ! ----- innerBanner_del Start ----- ! ");
	    $(".btn-gray", 0).click();
	    $("#btn-del").waitUntil(visible, 10000);
	    pageLoadCheck = $("#btn-del").text().trim();
	    if(pageLoadCheck.equals("선택 항목 삭제")) {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    } else {
	    	System.out.println(" *** innerBanner_del UI load Success !! *** ");
	    	close();
	    }
	    $("#btn-del").click();
	    valCheck("innerBanner_del_null");
	    $(".campaignChk", 0).click();
	    $("#btn-del").click();
	    valCheck("innerBanner_del_confirm");
	    valCheck("innerBanner_del_alert");
	    $(".btn-dark", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $(".btn-dark", 0).text().trim();
		if(pageLoadCheck.equals("링크URL 다운로드")) {
			System.out.println(" *** innerBanner_del Page load Success !! *** ");
		} else {
			System.out.println(" *** innerBanner_del list Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_del End ----- ! ");
	}
	@Test(priority = 31)
	public void fileDownload_add() {
		System.out.println(" ! ----- fileDownload_add Start ----- ! ");
		$(By.linkText("파일다운로드")).waitUntil(visible, 10000);
	    $(By.linkText("파일다운로드")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text().trim();
		for(int i=0;i<=1;i++) {
			if(!pageLoadCheck.equals("*.zip")) { //파일 다운로드 패턴이 남아있으면 지우고 페이지 로딩 다시 체크
			    $("#btn-list-delete").click();
			    $("#btn-list-select-delete").waitUntil(visible, 10000);
			    $("#list-checkbox-0").click();
			    $("#btn-list-select-delete").click();
			    valCheck("downPattern_del_confirm");
			    valCheck("downPattern_del_alert");
			    $("#list-checkbox-0").waitUntil(hidden, 10000);
			    pageLoadCheck = "*.zip";
			    System.out.println(" *** fileDownload_add list garbage data delete Success !! *** ");
			    refresh();
				$(".col-xs-9").waitUntil(visible, 10000);
			} else {
				if(pageLoadCheck.equals("*.zip")) {
					System.out.println(" *** fileDownload_add list Page load Success !! *** ");
					break;
				} else {
					System.out.println(" *** fileDownload_add list Page load Fail ... !@#$%^&*() *** ");
					close();
				}	
			}	
		}
	    $(".btn-info", 0).click();
	    $("th", 0).waitUntil(visible, 10000);
	    pageLoadCheck = $("th", 0).text().trim();
	    if(pageLoadCheck.equals("다운로드 패턴")) {
			System.out.println(" *** fileDownload_add add UI Success !! *** ");	    	
	    } else {
			System.out.println(" *** fileDownload_add add UI Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
	    $("#btn-add").click();
	    valCheck("downPattern_null");
	    $("#download-pattern").setValue(date);
	    $("#btn-add").click();
	    valCheck("downPattern_add_alert");
	    $("#btn-add").waitUntil(hidden, 10000);
	    pageLoadCheck = $(".col-xs-9").text().trim();
		if(pageLoadCheck.equals("파일다운로드패턴")) {
			System.out.println(" *** fileDownload_add refresh Page load Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_add refresh Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_add End ----- ! ");
	}
	@Test(priority = 32)
	public void fileDownload_duplicationAdd() {
		System.out.println(" ! ----- fileDownload_duplicationAdd Start ----- ! ");
	    $(".btn-info", 0).click();
	    sleep(1000);
	    //$(".mv20").waitUntil(visible, 10000);
	    pageLoadCheck = $("th", 0).text().trim();
	    if(pageLoadCheck.equals("다운로드 패턴")) {
			System.out.println(" *** fileDownload_add add UI Success !! *** ");	    	
	    } else {
			System.out.println(" *** fileDownload_add add UI Fail ... !@#$%^&*() *** ");
			close();	    	
	    }
	    $("#download-pattern").setValue(date);
	    $("#btn-add").click();
	    valCheck("downPattern_duplicationAdd_alert");
	    $("#btn-add-cancel").click();
	    
		System.out.println(" ! ----- innerBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 33)
	public void fileDownload_search() {
		System.out.println(" ! ----- fileDownload_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
		pageLoadCheck = $(".col-xs-9").text().trim();
		if(pageLoadCheck.equals("파일다운로드패턴")) {
			System.out.println(" *** fileDownload_search delList selectbox Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search delList selectbox Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    $("td", 1).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 1).text().trim();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** fileDownload_search delList search Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    $(".col-xs-9").waitUntil(visible, 10000);
	    pageLoadCheck = $(".col-xs-9").text().trim();
		if(pageLoadCheck.equals("파일다운로드패턴")) {
			System.out.println(" *** fileDownload_search setList selectbox Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search setList selectbox Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(".br-l-n").setValue(date);
	    $("#btn-search").click();
	    $("td", 3).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 3).text().trim();
	    if(pageLoadCheck.equals(date)) {
			System.out.println(" *** fileDownload_search setList search Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_search setList search Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- innerBanner_search End ----- ! ");
	}
	@Test(priority = 34)
	public void fileDownload_del() {
		System.out.println(" ! ----- fileDownload_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck("downPattern_del_null");
	    $("#list-checkbox-0").click();
	    $("#btn-list-select-delete").click();
	    valCheck("downPattern_del_confirm");
	    valCheck("downPattern_del_alert");
	    $("#list-checkbox-0").waitUntil(hidden, 10000);
	    $("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 1).text().trim();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** fileDownload_del page load Success !! *** ");
		} else {
			System.out.println(" *** fileDownload_del page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    
		System.out.println(" ! ----- innerBanner_del End ----- ! ");
	}
	@Test(priority = 41)
	public void outLinkBanner_add() {
		System.out.println(" ! ----- outLinkBanner_add Start ----- ! ");
		$(By.linkText("아웃링크 배너")).waitUntil(visible, 10000);
	    $(By.linkText("아웃링크 배너")).click();
	    $("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text().trim();
		if(pageLoadCheck.equals("아웃링크배너.")) {
			System.out.println(" *** outLinkBanner_add list page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** outLinkBanner_add register page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn-info").click();
		valCheck("outLinkBanner_promotionName_null");
		$("#promotion-name").setValue(date);
		$(".btn-info").click();
		valCheck("outLinkBanner_name_null");
		$(".input-sm", 2).setValue(date);
		$(".btn-info").click();
		valCheck("filePath_null");
		$(".input-sm", 3).setValue(date);
		$(".btn-info").click();
		valCheck("outLinkBanner_linkURL_null");
		$(".input-sm", 4).setValue(date);
		$(".btn-info").click();
		valCheck("outLinkBanner_linkURL_badURL");
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$(".btn-info").click();
		sleep(1000);
		valCheck("outLinkBanner_add_alert");
		$("#btn-search").waitUntil(appears, 10000);
		pageLoadCheck = $("td", 2).text().trim();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** outLinkBanner_add register Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- outLinkBanner_add End ----- ! ");
	}
	@Test(priority = 42)
	public void outLinkBanner_duplicationAdd() {
		System.out.println(" ! ----- outLinkBanner_duplicationAdd Start ----- ! ");
	    $(".btn-info", 0).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** outLinkBanner_add register page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_add register page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#promotion-name").setValue(date);
		$(".input-sm", 2).setValue(date);
		$(".input-sm", 3).setValue(date);
		$(".input-sm", 4).setValue("http://" + date + ".com");
		$(".btn-info").click();
		valCheck("outLinkBanner_promotionName_duplication");		
		$(".btn-light").click();
		$("#btn-search").waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text().trim();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** outLinkBanner_duplicationAdd check Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_duplicationAdd check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- outLinkBanner_duplicationAdd End ----- ! ");
	}
	@Test(priority = 43)
	public void outLinkBanner_search() {
		System.out.println(" ! ----- outLinkBanner_search Start ----- ! ");
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='n']")).click();
	    $("th", 5).waitUntil(visible, 10000);
	    pageLoadCheck = $("th", 5).text().trim();
	    if(pageLoadCheck.equals("삭제일")) {
	    	System.out.println(" *** outLinkBanner_search delList Selectbox Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search delList Selectboxt Fail ... !@#$%^&*() *** ");
			close();
	    }
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td").waitUntil(visible, 10000);
		pageLoadCheck = $("td").text().trim();
		if(pageLoadCheck.equals("목록이 없습니다.")) {
			System.out.println(" *** outLinkBanner_search delList search Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_search delList search Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.name("use_yn")).click();
	    $(By.xpath("//option[@value='y']")).click();
	    pageLoadCheck = $("th", 5).text().trim();
	    if(pageLoadCheck.equals("수정일")) {
	    	System.out.println(" *** outLinkBanner_search setList Selectbox Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search setList Selectboxt Fail ... !@#$%^&*() *** ");
			close();
	    }
	    $(".br-l-n").setValue(date);
		$("#btn-search").click();
		$("td", 2).waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 2).text().trim();
	    if(pageLoadCheck.equals(date)) {
	    	System.out.println(" *** outLinkBanner_search setList search Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_search setList search Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_search End ----- ! ");
	}
	@Test(priority = 44)
	public void outLinkBanner_modify() {
		System.out.println(" ! ----- outLinkBanner_modify Start ----- ! ");
	    $(".br-dark", 0).click();
	    $("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("수정하기")) {
			System.out.println(" *** outLinkBanner_modify page load Success !! *** ");
		} else {
			System.out.println(" *** outLinkBanner_modify page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#promotion-name").setValue(date + "수정");
		$(".input-sm", 2).setValue(date + "수정");
		$(".input-sm", 3).setValue(date + "수정");
		$(".btn-info").click();
		valCheck("outLinkBanner_modify_alert");
		$(".btn-promotion-detail-view").waitUntil(visible, 10000);
	    pageLoadCheck = $("td", 2).text().trim();
	    if(pageLoadCheck.equals(date + "수정")) {
	    	System.out.println(" *** outLinkBanner_modify Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_modify Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_modify End ----- ! ");
	}
	@Test(priority = 45)
	public void outLinkBanner_del() {
		System.out.println(" ! ----- outLinkBanner_del Start ----- ! ");
	    $("#btn-list-delete").click();
	    $("#btn-list-select-delete").waitUntil(visible, 10000);
	    $("#btn-list-select-delete").click();
	    valCheck("outLinkBanner_del_null");
	    $("#list-checkbox-0").click();
	    $("#btn-list-select-delete").click();
	    valCheck("outLinkBanner_del_confirm");
	    valCheck("outLinkBanner_del_alert");
	    $("td", 2).waitUntil(visible, 10000);
	    sleep(1000);
	    pageLoadCheck = $("td", 2).text().trim();
		if(pageLoadCheck.equals("아웃링크배너.")) {
	    	System.out.println(" *** outLinkBanner_del delete Success !! *** ");
	    } else {
			System.out.println(" *** outLinkBanner_del delete Fail ... !@#$%^&*() *** ");
			close();
	    }
	    System.out.println(" ! ----- outLinkBanner_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}