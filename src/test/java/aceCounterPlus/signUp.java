package aceCounterPlus; //회원가입

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

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

/*import com.codeborne.selenide.testng.ScreenShooter;*/

public class signUp {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, temp_pw, A, B, pageLoadCheck;
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
		pw = "qordlf";
		pw1 = "qordlf";
		A = "!@34";
		B = "14@#";
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
            case "restorationPassword_change_password_confirm": 
            	checkMsg = "비밀번호를 변경 하시겠습니까?";
            	break;
            case "restorationPassword_change_password_check": 
            	checkMsg = "비밀번호 변경이 완료되었습니다.";
            	break;
        }
		sleep(800);
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
	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}	
  	@Test(priority = 0)
  	public void page_open() {
  		System.out.println(" ! ----- page_open Start ----- ! ");
		open(baseUrl);
		$(".go_signup", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".go_signup", 1).text().trim();
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
		pageLoadCheck = $("h3", 1).text().trim();
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
		$("#userpw").setValue(pw + A);
		$("#repeatpw").setValue(pw + A);
		$("#usernm").setValue("회원가입테스트");
		$("#useremail").setValue("apzz0928@nate.com");
		$("#mp2").setValue("0000");
		$("#mp3").setValue("0000");
		$("#stepOneCompleted").click(); //서비스 신청
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
		pageLoadCheck = "";
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
			System.out.println(" *** app_signUp complete Page load Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** app_signUp complete Page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_join").click();
		$(".ace-svc-name", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".ace-svc-name", 0).text().trim(); //서비스관리 메인에서 gnb로 서비스명 체크
		if(pageLoadCheck.equals(domain + domain_date + ".com")) {
			System.out.println(" *** app_signUp service add check Success !! *** ");			
		} else {
			System.out.println(" *** app_signUp service add check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- app_signUp End ----- ! ");
  	}
  	@Test(priority = 11) 
	public void findAccount() {
		System.out.println(" ! ----- findAccount Start ----- ! ");
		open("https://new.acecounter.com/auth/logout");
		$(".go_find_account").waitUntil(visible, 10000);
		$(".go_find_account").click();
		pageLoadCheck = $("h2", 0).text().trim();
		if(pageLoadCheck.equals("아이디찾기")) {
			System.out.println(" *** findAccount page load check Success !! *** ");			
		} else {
			System.out.println(" *** findAccount page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#find_id_uid").setValue("apzz0928.egloos.com");
		$("#find_id_umail").setValue("apzz0928@gmail.com");
		$(".btn_find_id").click();
		$("#span_find_id").waitUntil(visible, 10000);
		pageLoadCheck = $("#span_find_id").text().trim();
		if(pageLoadCheck.equals("apzz092**")) {
			System.out.println(" *** find web ID check Success !! *** ");			
		} else {
			System.out.println(" *** find web ID check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_wht").click();
		open("https://new.acecounter.com/common/front/findAccount");
		pageLoadCheck = $("h2", 0).text().trim();
		if(pageLoadCheck.equals("아이디찾기")) {
			System.out.println(" *** findAccount page load check Success !! *** ");			
		} else {
			System.out.println(" *** findAccount page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#find_pw_uid").setValue("apzz0928888");
		$("#find_pw_umail").setValue("apzz092888@daum.net");
		$(".btn_send_mail").click();
		$("#span_find_pw").waitUntil(visible, 10000);
		pageLoadCheck = $("#span_find_pw").text().trim();
		if(pageLoadCheck.equals("apzz092888@daum.net")) {
			System.out.println(" *** find web PW check Success !! *** ");			
		} else {
			System.out.println(" *** find web PW check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_pop_submit", 1).click();
		js("window.open('https://logins.daum.net/accounts/loginform.do?url=https%3A%2F%2Fmail.daum.net%2F');"); //자바스크립트로 메일 확인용 주소 띄움
		switchTo().window(1); //브라우저 새탭으로 이동
		$("#loginBtn").waitUntil(visible, 10000);
		$("#id").setValue("apzz092888");
		$("#inputPwd").setValue("qordlf!@34");
		$("#loginBtn").click();
		refresh();
		$(".tit_subject", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".tit_subject", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		if(pLC[2].equals("임시비밀번호를")) {
			System.out.println(" *** daum mail list page load check Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** daum mail list page load check Fail ... !@#$%^&*() *** ");
			
			close();
		}
		$(".tit_subject", 0).click();
		$("h1", 1).waitUntil(visible, 10000);
		String temp_id = $("td", 10).text().trim();
		temp_pw = $("td", 11).text().trim(); //비번 원복할때 써야해서 전역으로 변경
		//메일삭제 완전삭제
		$(By.linkText("받은메일함")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("휴지통")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_permanent").click();
		sleep(2000);
		$(".check_type2").click();
		$(By.linkText("받은메일함")).click();
		switchTo().window(0); //원래 탭으로 이동
		open("https://new.acecounter.com/common/front");
		$("#uid").waitUntil(visible, 10000);
		$("#uid").setValue(temp_id);
		$("#upw").setValue(temp_pw);
		$(".btn_login").click();
		pageLoadCheck = $(".personal_info").text().trim();
		pLC = pageLoadCheck.split("님");
		if(pLC[0].equals("원래이름")) {
			System.out.println(" *** findAccount page load check Success !! *** ");			
		} else {
			System.out.println(" *** findAccount page load check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- findAccount End ----- ! ");
  	}
  	@Test(priority = 12)
	public void restorationPassword() {
		System.out.println(" ! ----- restorationPassword Start ----- ! ");
		$(".go_setting").click();
		$("#member_info").waitUntil(visible, 10000);
		$("#member_info").click();
		$("h3", 2).waitUntil(visible, 15000);
		pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("비밀번호 재확인")) {
			System.out.println(" *** memberInfo Recongirming page load Success !! *** ");
		} else {
			System.out.println(" *** memberInfo Recongirming page load Fail ... !@#$%^&*() *** ");			
		}
		$("#pwd").setValue(temp_pw);
		$("#btn-ok").click();
		$("h3", 2).waitUntil(visible, 15000);
		System.out.println(" *** Password change Page access Success !! *** ");
		for(int i=1;i<=2;i++) { //이전 사용 비밀번호로 변경 불가해서 2번바꿈
			if(i==1) {
				pw = temp_pw;
				pw1 = pw1 + B;
			} else if(i==2) {
				pw = pw + B;
				pw1 = pw1 + A;
			}
			$("#prePwd").waitUntil(visible, 10000);
			$("#prePwd").setValue(pw);
			$("#changePwd").setValue(pw1);
			$("#changePwdConfirm").setValue(pw1);
			$("#modifyProc").click();
			valCheck("restorationPassword_change_password_confirm"); 
			valCheck("restorationPassword_change_password_check");
			pw = "qordlf";
			pw1 = "qordlf";
		}
		System.out.println(" ! ----- restorationPassword End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}