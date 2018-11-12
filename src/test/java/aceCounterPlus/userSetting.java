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
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	
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
	      case "CheckIP": checkMsg = "IP를 입력하세요.";
	      break;
	      case "IPvalCheck": checkMsg = "시스템 오류가 발생되었습니다. 다시 시도해주세요.";
	      break;
	      case "IPregister": checkMsg = "IP가 등록되었습니다.";
	      break;
	      case "IPduplication": checkMsg = "등록된 IP 입니다. 다시 입력해주세요.";
	      break;
	      case "searchInputCheck": checkMsg = "검색어를 입력해 주세요";
	      break;
	      case "delCheck": checkMsg = "삭제할 IP를 선택하세요.";
	      break;
	      case "delIP": checkMsg = "IP가 삭제되었습니다.";
	      break;
	      case "groupName_null": checkMsg = "회원 그룹명을 입력하세요.";
	      break;
	      case "groupVariable": checkMsg = "회원 변수값을 입력하세요.";
	      break;
	      case "groupRegister": checkMsg = "등록이 완료되었습니다.";
	      break;
	      case "groupSearch_null": checkMsg = "검색어를 입력하세요.";
	      break;
	      case "groupModify": checkMsg = "수정이 완료되었습니다.";
	      break;
	      case "delGroup_check": checkMsg = "삭제할 회원그룹을 선택하세요.";
	      break;
	      case "delGroup_confirm": checkMsg = "전체 회원그룹을 삭제하시겠습니까?";
	      break;
	      case "delGroup_alert": checkMsg = "회원 그룹이 삭제 되었습니다.";
	      break;
	    }
	    Thread.onSpinWait();
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
			if(!val.equals("delGroup_confirm")) {
				$(".modal-backdrop").waitUntil(hidden, 10000);				
			}
		} else if (msgCheck.isEmpty()) {
			System.out.println(" *** ☆★☆★☆★ pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - msgCheck is Empty ... ☆★☆★☆★ *** ");
			close();
		} else {
			System.out.println(" *** pTagNum : " + pTagNum + " / btnNum : " + btnNum + " / val : " + val +  " - check Fail ... !@#$%^&*() *** ");
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
		$(".btn_logout").getValue();
		if(loginCheck.equals("로그아웃")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		$("h3", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h3", 2).text().trim();
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
		System.out.println(" ! ----- IPFilterring_add Start ----- ! ");
		$("#redirectConfBtn").click();
		$(".input-sm").waitUntil(visible, 10000);
		$(".accordion-toggle", 1).click();
		$(By.linkText("IP필터링설정")).waitUntil(visible, 10000);
		$(By.linkText("IP필터링설정")).click();
		$("h5", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("등록된 IP가 없습니다.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		$(".btn-info", 0).click();
		$(".btn-info", 2).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		valCheck(4, 3, "CheckIP");
		$("#filter-ipa").setValue("127");
		$(".btn-info", 2).click();
		valCheck(5, 4, "CheckIP");
		$("#filter-ipb").setValue("0");
		$(".btn-info", 2).click();
		valCheck(6, 5, "CheckIP");
		$("#filter-ipc").setValue("0");
		$(".btn-info", 2).click();
		valCheck(7, 6, "CheckIP");
		$("#filter-ipd").setValue("ㅇ");
		$(".btn-info", 2).click();
		valCheck(8, 7, "IPvalCheck");
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
		valCheck(3, 3, "IPregister");
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
		valCheck(4, 4, "IPduplication");
		$(".btn-light", 0).click();
		System.out.println(" ! ----- IPFilterring_duplicationCheck End ----- ! ");
	}
	@Test(priority = 3)
	public void IPFilterring_search() {
		System.out.println(" ! ----- IPFilterring_search Start ----- ! ");
		$(".btn-default", 5).click();
		valCheck(5, 5, "searchInputCheck");
		$("#searchIp").setValue("03");
		$(".btn-default", 5).click();
		String pageLoadCheck = $("h5", 2).text();
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
		valCheck(3, 3, "delCheck");
		$("#chkAll").click();
		$(".btn-info", 1).click();
		$(".btn-info", 1).waitUntil(hidden, 10000);
		sleep(1000);
		valCheck(4, 3, "delIP");
		$("h5", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 2).text();
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
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("등록된 회원그룹이 없습니다.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
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
		valCheck(3, 3, "groupName_null");
		$("#md_p_name").setValue(date);
		$("#add_group_regist").click();
		valCheck(4, 4, "groupVariable");
		$("#md_name_1").setValue(date);
		$("#add_group_regist").click();
		valCheck(5, 5, "groupVariable");
		$("#md_name_2").setValue(date);
		$("#add_group_regist").click();
		valCheck(3, 3, "groupRegister");
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
		$("#frmBtn").click();
		valCheck(4, 4, "groupSearch_null");
		$("#searchNm").setValue("qwer");
		$("#frmBtn").click();
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
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
		String pageLoadCheck = $(".notokr-medium").text();
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
		valCheck(3, 3, "groupModify");
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
		String pageLoadCheck = $(".btn-gray", 1).text();
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
		valCheck(4, 4, "delGroup_check");
		$("#chkAll").click();
		$(".btn-info", 1).click();
		valCheck(5, 5, "delGroup_confirm");
		$(".btn-info", 1).waitUntil(hidden, 10000);
		$("#memgrpAdd").waitUntil(visible, 10000);
		valCheck(4, 3, "delGroup_alert");
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