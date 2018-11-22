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
    
	Date date1 = new Date(); //web 회원가입과 app 회원가입의 도메인을 동일하게 하기위해 공통선언
    SimpleDateFormat number_format = new SimpleDateFormat("YYMMddHHmmss");
    String domain_date = number_format.format(date1);
	
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
	
	public static void valCheck(String val) {
        switch(val){
            case "reportDownload_oneshotAdd_reportCheck_null": checkMsg = "리포트를 선택하세요.";
            break;
            case "reportDownload_oneshotAdd_register": checkMsg = "리포트를 생성하였습니다.";
            break;
            case "reportDownload_oneshotDel_confirm": checkMsg = "선택하신 일회성 리포트 설정을 삭제하시겠습니까?";
            break;            
            case "reportDownload_oneshotDel_alert": checkMsg = "설정 내역이 삭제되었습니다.";
            break;
			case "myMenu_add_menu_null": checkMsg = "통계를 선택하세요.";
			break;
			case "myMenu_add_menu_duplication": checkMsg = "이미 추가한 통계입니다.";
			break;
			case "myMenu_add_menu_max": checkMsg = "최대 20개까지 등록할 수 있습니다.";
			break;
			case "myMenu_save": checkMsg = "저장이 완료되었습니다.";
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
			System.out.println(" *** ☆★☆★☆★ " + val +  " - msgCheck is Empty ... ☆★☆★☆★ *** ");
			System.out.println(checkMsg);
			close();
		} else { // msgCheck=checkMsg여부, confirm&alert여부, 빈값 여부 체크 후 fail
			System.out.println(" *** " + val +  " - check Fail ... !@#$%^&*() *** ");
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
  	public static void sleep(long millis) {
  		try {
  			Thread.sleep(millis);
  		} catch (InterruptedException ex) {
  		}
  	}
  	@Test(priority = 0)
  	public void page_open() {
  		System.out.println(" ! ----- page_open Start ----- ! ");
		open(baseUrl);
		$(".go_signup", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $(".go_signup", 1).text().trim();
		if(pageLoadCheck.equals("회원가입")) {
			System.out.println(" *** main Page load Success !! *** ");
		} else {
			System.out.println(" *** main Page load Fail ... !@#$%^&*() *** ");
			close();
		}
  		System.out.println(" ! ----- page_open End ----- ! ");
  	}
  	public void common_signUp() { //웹, 앱 회원가입 1단계는 동일해서 공통으로 뺌
		System.out.println(" ! ----- common_signUp Start ----- ! ");
		$(".go_signup", 1).click();
		$("#member_exist").waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 1).text().trim();
		String pLC[] = pageLoadCheck.split("에");
		if(pLC[0].trim().equals("신규회원")) {
			System.out.println(" *** common_signUp main Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** common_signUp main Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("input", 1).click();
		$(".progress", 0).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 0).text().trim();
		pLC = pageLoadCheck.split(" ");
		if(pLC[1].trim().equals("이용약관")) {
			System.out.println(" *** common_signUp step1 Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** common_signUp step1 Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=2;i++) {
			$("label", i).click();
		}
		$("#stepOneCompleted").scrollIntoView(false); //입력완료버튼으로 스크롤 이동
		Date date = new Date(); //web 회원가입과 app 회원가입 id가 안겹치게 입력 직전에 새로 정의
	    SimpleDateFormat number_format = new SimpleDateFormat("MMddHHmmss");
	    String id_date = number_format.format(date);
		$("#userid").setValue(id + id_date); //회원정보 입력
		$("#recheck").click();
		pageLoadCheck = $("#checkresult").text().trim();
		if(pageLoadCheck.trim().equals("등록가능한 ID입니다.")) {
			System.out.println(" *** common_signUp ID check Success !! *** ");
		} else {
			System.out.println(" *** common_signUp ID check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#userpw").setValue(pw);
		$("#repeatpw").setValue(pw);
		$("#usernm").setValue("회원가입테스트");
		$("#useremail").setValue("apzz0928@nate.com");
		$("#mp2").setValue("0000");
		$("#mp3").setValue("0000");
		$("#stepOneCompleted").click(); //서비스 신청
		System.out.println("서비스 신청 완료");
		$("h3", 4).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 4).text().trim();
		pLC = pageLoadCheck.split("\\.");
		if(pLC[0].trim().equals("STEP1")) {
			System.out.println(" *** signUp service info input Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** signUp service info input Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- common_signUp End ----- ! ");
	}

  	@Test(priority = 1)
	public void web_signUp() {
		System.out.println(" ! ----- web_signUp Start ----- ! ");
  		common_signUp();
		$(By.name("input-domain")).setValue(domain + domain_date + ".com");
		$(".ace-btn-add-domain").click(); // 페이지 이동 속도때문에 도메인 체크를 나중에
		$(By.name("domainUrl")).waitUntil(visible, 10000);
		$(By.name("largeCategoryCd")).selectOptionByValue("22");
		$(By.name("middleCategoryCd")).selectOptionByValue("188");
		$("#stepTwoCompleted").click();
		$("#scriptBtn").waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 0).text().trim();
		String pLC[] = pageLoadCheck.split(" ");
		if(pLC[2].trim().equals("완료되었습니다.")) {
			System.out.println(" *** web_signUp complete Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** web_signUp complete Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_join").click();
		$(".ace-svc-name", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".ace-svc-name", 0).text().trim();
		if(pageLoadCheck.equals(domain + domain_date + ".com")) {
			System.out.println(" *** web_signUp service add check Success !! *** ");		
		} else {
			System.out.println(" *** web_signUp service add check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- web_signUp End ----- ! ");
		open("https://new.acecounter.com/auth/logout");
  	}
  	@Test(priority = 2)
	public void app_signUp() {
		System.out.println(" ! ----- app_signUp Start ----- ! ");
  		common_signUp();
  		$("#app").click();
  		$(".ace-btn-add-package").waitUntil(visible, 10000);
		$(By.name("svcNm")).setValue(domain + domain_date + ".com");
		String pageLoadCheck = "";
		for(int i=0,stores=10;i<=2;i++) {
			$(By.name("stores")).selectOptionByValue(stores + "");
			$(By.name("input-package")).setValue(domain+domain_date + ".com");
			$(".ace-btn-add-package").click();
			$(".ft14", i).waitUntil(visible, 10000);
			pageLoadCheck = $(".ft14", i).text();
			if(pageLoadCheck.equals("앱스토어")) {
				System.out.println(" *** app_signUp appStore add check Success !! *** ");
			} else if(pageLoadCheck.equals("구글플레이스토어")) {
				System.out.println(" *** app_signUp googlePlayStore add check Success !! *** ");				
			} else if(pageLoadCheck.equals("원스토어")) {
				System.out.println(" *** app_signUp oneStore add check Success !! *** ");
			} else {
				System.out.println(" *** app_signUp store add check Fail ... !@#$%^&*() *** ");
				close();
			}
			stores = stores+10;			
		}
		$("#stepTwoCompleted").click(); 
		$("#scriptBtn").waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 0).text().trim();
		String pLC[] = pageLoadCheck.split(" ");
		if(pLC[2].trim().equals("완료되었습니다.")) {
			System.out.println(" *** web_signUp complete Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** web_signUp complete Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_join").click();
		$(".ace-svc-name", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".ace-svc-name", 0).text().trim();
		if(pageLoadCheck.equals(id + domain_date + ".com")) {
			System.out.println(" *** web_signUp service add check Success !! *** ");			
		} else {
			System.out.println(" *** web_signUp service add check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- app_signUp End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}