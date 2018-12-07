package aceCounterPlus; //통계설정 - 마케팅유입설정

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

public class marketingInflowSetting {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	//신규가입할때마다 number를 변경해줘야해서 id+월일시분초 로 변경없이 가입 가능하도록 추가
	Date number_date = new Date();
    SimpleDateFormat number_format = new SimpleDateFormat("yyMMddHHmmss");
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
	public static void valCheck(int pTagNum, int btnNum, String val) {
	    $(".modal-backdrop").waitUntil(visible, 10000);
		$("p", pTagNum).click();
	    String msgCheck = $("p", pTagNum).text();
	    switch(val){
	      case "mktInflowSetting_add_cmpName_null": checkMsg = "캠페인명을 입력해주세요.";
	      break;
	      case "mktInflowSetting_add_cmpName_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
	      break;
	      case "mktInflowSetting_add_subjectMatter_null": checkMsg = "소재를 입력해주세요.";
	      break;
	      case "mktInflowSetting_add_subjectMatter_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
	      break;
	      case "mktInflowSetting_add_linkURL_null": checkMsg = "연결 URL을 입력하세요.";
	      break;
	      case "mktInflowSetting_add_linkURL_validation": checkMsg = "올바른 URL을 입력하세요.";
	      break;
	      case "mktInflowSetting_register": checkMsg = "등록에 성공했습니다.";
	      break;
	      case "mktInflowSetting_del_null": checkMsg = "삭제할 마케팅 유입 설정을 선택하세요.";
	      break;
	      case "mktInflowSetting_del_confirm": checkMsg = "선택한 마케팅 유입 설정을 삭제하시겠습니까?\n" + "마케팅 유입설정 변수에 대해 수집/분석이 중지되며,\n" + "삭제 후 복구가 불가능합니다.";
	      break;
	      case "mktInflowSetting_del_alert": checkMsg = "마케팅 유입 설정 삭제가 완료되었습니다.";
	      break;
	      case "inflowMediaNm_add_null": checkMsg = "광고상품명을 입력하세요.";
	      break;
	      case "inflowMediaNm_add_validation": checkMsg = "한글, 영문(소문자), 숫자, 특수문자(!%()+-_=:./~#), 띄어쓰기로 입력하세요.";
	      break;
	      case "campaignMaterial_null": checkMsg = "광고 속성을 선택하세요.";
	      break;
	      case "advertisingProductManage_add_duplication": checkMsg = "이미 사용한 광고상품명입니다.\n" + "다른 광고상품명을 사용해주세요.";
	      break;
	      case "advertisingProductManage_add_register": checkMsg = "등록이 완료되었습니다.";
	      break;
	      case "advertisingProductManage_del_null": checkMsg = "삭제할 광고상품관리 설정을 선택하세요.";
	      break;
	      case "advertisingProductManage_del_confirm": checkMsg = "선택한 광고상품 관리 설정을 삭제하시겠습니까?\n" + "광고상품관리 변수에 대해 수집/분석이 중지되며, 삭제 후 복구가 불가능합니다.";
	      break;
	      case "advertisingProductManage_del_alert": checkMsg = "광고상품관리 설정 삭제가 완료되었습니다.";
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
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() ***");
            	close();
            } else {
            	System.out.println("*** " + urlName +" : Link Status HTTP : " + respCode + " - check Success !! ***");
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
	public void mktInflowSetting_add() {
		System.out.println(" ! ----- mktInflowSetting_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$("#inflowAddBtn").waitUntil(visible, 10000);
		pageLoadCheck = $("#inflowAddBtn").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** mktInflowSetting_add list page Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add list page Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#inflowAddBtn").click();
		$("#btnReg").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("추가하기")) {
			System.out.println(" *** mktInflowSetting_add add page Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add add page Fail ... !@#$%^&*() *** ");
			close();
		}
		$("label", 3).click();
		$("#btnReg").click();
		valCheck(4, 5, "mktInflowSetting_add_cmpName_null");
		$("#campaign_nm").setValue(domain + date + "@");
		$("#btnReg").click();
		valCheck(5, 6, "mktInflowSetting_add_cmpName_validation");
		$("#campaign_nm").setValue(domain + date);
		$("#btnReg").click();
		valCheck(6, 7, "mktInflowSetting_add_subjectMatter_null");
		$("#campaign_material_value0").setValue(domain + date + "@");
		$("#btnReg").click();
		valCheck(7, 8, "mktInflowSetting_add_subjectMatter_validation");
		$("#campaign_material_value0").setValue(domain + date);
		$("#btnReg").click();
		valCheck(8, 9, "mktInflowSetting_add_linkURL_null");
		$("#original_url0").setValue(domain + date);
		$("#btnReg").click();
		valCheck(9, 10, "mktInflowSetting_add_linkURL_validation");
		$("#original_url0").setValue(domain + date + ".com");
		$("#btnReg").click();
		valCheck(10, 11, "mktInflowSetting_register");
		$("#inflowMrkCodeDown").waitUntil(visible, 10000);
		pageLoadCheck = $("#inflowMrkCodeDown").text();
		if(pageLoadCheck.equals("광고코드 다운로드")) {
			System.out.println(" *** mktInflowSetting_add register Success !! *** ");
		} else {
			System.out.println(" *** mktInflowSetting_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- mktInflowSetting_add End ----- ! ");
	}
	@Test(priority = 2)
	public void mktInflowSetting_searchAndDel() {
		System.out.println(" ! ----- mktInflowSetting_searchAndDel Start ----- ! ");
		$(".br-l-n").setValue("99999");
		$("#searchBtn").click();
		$("td", 1).waitUntil(hidden, 10000);
		$("td", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[3].equals("않습니다.")) {
			System.out.println(" *** mktInflowSetting_del no search result Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del no search result Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".br-l-n").setValue(date);
		$("#searchBtn").click();
		$("td", 0).waitUntil(hidden, 10000);
		$("td", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 5).text().trim();
		pLC = pageLoadCheck.split("apzz");
		if(pLC[1].equals(date)) {
			System.out.println(" *** mktInflowSetting_del search result Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del search result Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#deleteViewBtn").click();
		$("#deleteBtn").waitUntil(visible, 10000);
		$("#deleteBtn").click();
		valCheck(3, 4, "mktInflowSetting_del_null");
		$(".clsDelbox", 0).click();
		$("#deleteBtn").click();
		valCheck(4, 5, "mktInflowSetting_del_confirm");
		valCheck(5, 7, "mktInflowSetting_del_alert");
		$(".no-records-found").waitUntil(visible, 10000);
		pageLoadCheck = $(".no-records-found").text();
		pLC = pageLoadCheck.split(" ");
		if(pLC[3].equals("않습니다.")) {
			System.out.println(" *** mktInflowSetting_del del Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** mktInflowSetting_del del Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- mktInflowSetting_searchAndDel End ----- ! ");
	}
	//@Test(priority = 11)
	public void advertisingCodeDownload() {
		System.out.println(" ! ----- advertisingCodeDownload Start ----- ! ");
		$("#inflowMrkCodeDown").waitUntil(visible, 10000);
		$("#inflowMrkCodeDown").click();
		sleep(1000);
		pageLoadCheck = $("h4", 0).text();
		if(pageLoadCheck.equals("광고코드 다운로드")) {
			brokenLinkCheck("advCodeDown", "https://new.acecounter.com/setting/appmarketing/codedown?inflow_media_cd=&inflow_mrkt_channel_dcd=10&down_term_cate=ALL&create_dt_st=&create_dt_ed=&original_url_yn=n&use_yn=y");
			System.out.println(" *** advCodeDownload layer check Sueecss !! *** ");
			$(".close", 0).click();
			$(".modal-backdrop").waitUntil(hidden, 10000);
		} else {
			System.out.println(" *** advCodeDownload layer check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingCodeDownload End ----- ! ");
	}
	//@Test(priority = 12)
	public void advertisingProductManage_add() {
		System.out.println(" ! ----- advertisingProductManage_add Start ----- ! ");
		sleep(1000);
		$(".btn-dark", 0).click();
		$("#addViewBtn").waitUntil(visible, 10000);
		pageLoadCheck = $("#addViewBtn").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** advProductManage_add list page load Success !! *** ");
		} else {
			System.out.println(" *** advProductManage_add list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#addViewBtn").click();
		$(".gui-input").waitUntil(visible, 10000);
		$("#btnRegister").click();
		valCheck(4, 3, "inflowMediaNm_add_null");
		$(".gui-input").setValue("@");
		$("#btnRegister").click();
		valCheck(5, 4, "inflowMediaNm_add_validation");
		$(".gui-input").setValue("1234test");
		$("#btnRegister").click();
		valCheck(6, 5, "campaignMaterial_null");
		$(By.name("campaignMaterialCd[]")).click();
	    $(By.xpath("//option[@value='90']")).click();
		$("#btnRegister").click();
		valCheck(7, 6, "advertisingProductManage_add_duplication");
		$(".gui-input").setValue(date);
		$("#btnRegister").click();		
		valCheck(8, 7, "advertisingProductManage_add_register");
		$("#addViewBtn").waitUntil(visible, 10000);
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** advertisingProductManage_add register Success !! *** ");
		} else {
			System.out.println(" *** advertisingProductManage_add register Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingProductManage_add End ----- ! ");
	}
	//@Test(priority = 13)
	public void advertisingProductManage_del() {
		System.out.println(" ! ----- advertisingProductManage_del Start ----- ! ");
		$("#deleteViewBtn").click();
		$("#deleteBtn").waitUntil(visible, 10000);
		$("#deleteBtn").click();
		valCheck(3, 3, "advertisingProductManage_del_null");
		$("#checkAllCamp").click();
		$("#deleteBtn").click();
		valCheck(4, 4, "advertisingProductManage_del_confirm");
		valCheck(5, 6, "advertisingProductManage_del_alert");
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 사용자정의 정보가 없습니다.")) {
			System.out.println(" *** advertisingProductManage_del del Success !! *** ");
		} else {
			System.out.println(" *** advertisingProductManage_del del Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- advertisingProductManage_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}