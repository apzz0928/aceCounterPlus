package aceCounterPlus; //통계설정 > 사용자설정(IP필터링, 회원그룹)

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

public class userSetting {
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
	      case "IPFilterring_Check": checkMsg = "IP를 입력하세요.";
	      break;
	      case "IPFilterring_valCheck": checkMsg = "시스템 오류가 발생되었습니다. 다시 시도해주세요.";
	      break;
	      case "IPFilterring_register": checkMsg = "IP가 등록되었습니다.";
	      break;
	      case "IPFilterring_duplication": checkMsg = "등록된 IP 입니다. 다시 입력해주세요.";
	      break;
	      case "IPFilterring_searchInputCheck": checkMsg = "검색어를 입력해 주세요";
	      break;
	      case "IPFilterring_delCheck": checkMsg = "삭제할 IP를 선택하세요.";
	      break;
	      case "IPFilterring_del": checkMsg = "IP가 삭제되었습니다.";
	      break;
	      case "userGroupSetting_Name_null": checkMsg = "회원 그룹명을 입력하세요.";
	      break;
	      case "userGroupSetting_Variable": checkMsg = "회원 변수값을 입력하세요.";
	      break;
	      case "userGroupSetting_Register": checkMsg = "등록이 완료되었습니다.";
	      break;
	      case "userGroupSetting_searchNull": checkMsg = "검색어를 입력하세요.";
	      break;
	      case "userGroupSetting_Modify": checkMsg = "수정이 완료되었습니다.";
	      break;
	      case "userGroupSetting_selectCheck": checkMsg = "삭제할 회원그룹을 선택하세요.";
	      break;
	      case "userGroupSetting_del_confirm": checkMsg = "전체 회원그룹을 삭제하시겠습니까?";
	      break;
	      case "userGroupSetting__alert": checkMsg = "회원 그룹이 삭제 되었습니다.";
	      break;
	    }
	    if(val.equals("userGroupSetting_del_confirm")) {
	    	// 회원그룹 삭제 confirm만 modal-backdrop로 element 체크시 간헐적 에러나서 체크 제외
	    	$(".btn-sm", 5).waitUntil(visible, 10000);
	    } else {
		    $(".modal-backdrop").waitUntil(visible, 10000);	    	
	    }
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val과 checkMsg 비교해서 맞으면
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val 끝에 7자리 confirm이랑 비교해서 맞으면 btn-info 클릭
	            System.out.println(" *** val : " + val +  " - confirm check Success !! *** ");
	            if(val.equals("userGroupSetting_del_confirm")) { //회원그룹설정 페이지 삭제 confirm만 UI가 달라서 따로 처리
	            	System.out.println(" *** val : " + val +  " - userGroupSetting_del_confirm check Success !! *** ");
	            	$(".btn-sm", 5).click(); //id로 체크하려했더니 prevObject 라는 것 때문인지 class로만 체크가됨
	            } else {
		            $$(".btn-info").last().click();
	            }
	            if(val.equals("userGroupSetting_del_confirm")) {
	            	// 회원그룹 삭제 confirm만 modal-backdrop로 element 체크시 간헐적 에러나서 체크 제외
	            } else {
		            $(".modal-backdrop").waitUntil(hidden, 10000);
	            }
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
	public void IPFilterring_add() {
		System.out.println(" ! ----- IP Filterring_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".accordion-toggle", 1).click();
		$(By.linkText("IP필터링설정")).waitUntil(visible, 10000);
		$(By.linkText("IP필터링설정")).click();
		$("h5", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		$(".btn-info", 0).click();
		$(".btn-info", 2).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		valCheck("IPFilterring_Check");
		$("#filter-ipa").setValue("127");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_Check");
		$("#filter-ipb").setValue("0");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_Check");
		$("#filter-ipc").setValue("0");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_Check");
		$("#filter-ipd").setValue("ㅇ");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_valCheck");
		$(".btn-info", 2).waitUntil(hidden, 10000);
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page val check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page val check Reload Fail ... *** ");
			close();
		}
		$(".btn-info", 0).waitUntil(visible, 10000);
		$(".btn-info", 0).click();
		$(".btn-info", 2).waitUntil(visible, 10000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		$(".btn-info", 2).waitUntil(hidden, 10000);
		valCheck("IPFilterring_register");
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals("127.0.0.1")) {
			System.out.println(" *** IP Filterring Page register check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page register check Reload Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- IPFilterring_add End ----- ! ");
	}
	@Test(priority = 2)
	public void IPFilterring_duplicationCheck() {
		System.out.println(" ! ----- IPFilterring_duplicationCheck Start ----- ! ");
		$(".btn-info", 0).click();
		$(".btn-info", 2).waitUntil(visible, 10000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_duplication");
		$(".btn-light", 0).click();
		System.out.println(" ! ----- IPFilterring_duplicationCheck End ----- ! ");
	}
	@Test(priority = 3)
	public void IPFilterring_search() {
		System.out.println(" ! ----- IPFilterring_search Start ----- ! ");
		$(".btn-default", 5).click();
		valCheck("IPFilterring_searchInputCheck");
		$("#searchIp").setValue("03");
		$(".btn-default", 5).click();
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring don`t input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring don`t input search check Fail ... *** ");
			close();
		}
		$("#searchIp").setValue("127");
		$(".btn-default", 5).click();
		$("td", 3).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 3).text();
		if(pageLoadCheck.equals("127.0.0.1")) {
			System.out.println(" *** IP Filterring input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring input search check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- IPFilterring_search End ----- ! ");
	}
	@Test(priority = 4)
	public void IPFilterring_del() {
		System.out.println(" ! ----- IPFilterring_del Start ----- ! ");
		$(".btn-gray", 0).click();
		$(".btn-info", 1).waitUntil(visible, 10000);
		$(".btn-info", 1).click();
		valCheck("IPFilterring_delCheck");
		$("#chkAll").click();
		$(".btn-info", 1).click();
		$(".btn-info", 1).waitUntil(hidden, 10000);
		sleep(1000);
		valCheck("IPFilterring_del");
		$("h5", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- IPFilterring_del End ----- ! ");
	}
	@Test(priority = 11)
	public void userGroupSetting_add() {
		$(By.linkText("회원그룹설정")).click();
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 회원그룹이 없습니다.")) {
			System.out.println(" *** userGroupSetting list Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting list Page load Fail ... *** ");
			close();
		}
		$("#memgrpAdd").click();
		$(".notokr-medium").waitUntil(visible, 10000);
		pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("회원그룹 추가하기")) {
			System.out.println(" *** userGroupSetting add Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting add Page load Fail ... *** ");
			close();
		}
		$("#add_group_regist").click();
		valCheck("userGroupSetting_Name_null");
		$("#md_p_name").setValue(date);
		$("#add_group_regist").click();
		valCheck("userGroupSetting_Variable");
		$("#md_name_1").setValue(date);
		$("#add_group_regist").click();
		valCheck("userGroupSetting_Variable");
		$("#md_name_2").setValue(date);
		$("#add_group_regist").click();
		valCheck("userGroupSetting_Register");
		$("#memgrpAdd").waitUntil(visible, 10000);
		pageLoadCheck = $("#memgrpAdd").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** userGroup Register Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroup Register Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- userGroupSetting_add End ----- ! ");
	}
	@Test(priority = 12)
	public void userGroupSetting_search() {
		System.out.println(" ! ----- userGroupSetting_search Start ----- ! ");
		$("#frmBtn").waitUntil(visible, 10000);
		$("#frmBtn").click();
		valCheck("userGroupSetting_searchNull");
		$("#searchNm").setValue("qwer");
		$("#frmBtn").click();
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 회원그룹이 없습니다.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
			close();
		}
		$("#searchNm").setValue(date);
		$("#frmBtn").click();
		$("td", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** userGroupSetting Date search Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Date search Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- userGroupSetting_search End ----- ! ");
	}
	@Test(priority = 13)
	public void userGroupSetting_modify() {
		System.out.println(" ! ----- userGroupSetting_modify Start ----- ! ");
		$(".btn-xs").click();
		$(".notokr-medium").waitUntil(visible, 10000);
		pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("회원그룹 추가하기")) {
			System.out.println(" *** userGroupSetting add Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting add Page load Fail ... *** ");
			close();
		}
		$("#md_p_name").setValue(date + "수정");
		$("#md_name_1").setValue(date + "수정");
		$("#md_name_2").setValue(date + "수정");
		$("#add_group_regist").click();
		confirm("수정하시겠습니까?");
		valCheck("userGroupSetting_Modify");
		$("#memgrpAdd").waitUntil(visible, 10000);
		pageLoadCheck = $("#memgrpAdd").text();
		if(pageLoadCheck.equals("추가")) {
			System.out.println(" *** userGroup Register Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroup Register Page load Fail ... *** ");
			close();
		}		
		System.out.println(" ! ----- userGroupSetting_modify End ----- ! ");
	}
	@Test(priority = 14)
	public void userGroupSetting_del() {
		System.out.println(" ! ----- userGroupSetting_del Start ----- ! ");
		$(".btn-gray", 0).click();
		$(".btn-gray", 1).waitUntil(visible, 10000);
		pageLoadCheck = $(".btn-gray", 1).text();
		if(pageLoadCheck.equals("취소")) {
			System.out.println(" *** userGroupSetting delete UI load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting delete UI load Fail ... *** ");
			close();
		}
		$(".btn-gray", 1).click();
		$(".btn-gray", 0).waitUntil(visible, 10000);
		$(".btn-gray", 0).click();
		$(".btn-gray", 1).waitUntil(visible, 10000);
		$(".btn-info", 1).click();
		valCheck("userGroupSetting_selectCheck");
		$("#chkAll").click();
		$(".btn-info", 1).click();
		valCheck("userGroupSetting_del_confirm");
		$(".btn-info", 1).waitUntil(hidden, 10000);
		$("#memgrpAdd").waitUntil(visible, 10000);
		$("#modify_1").waitUntil(hidden, 10000);
		valCheck("userGroupSetting__alert");
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 회원그룹이 없습니다.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- userGroupSetting_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}