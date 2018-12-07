package aceCounterPlus; //��輳�� > ����ڼ���(IP���͸�, ȸ���׷�)

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
	
	//�ű԰����Ҷ����� number�� ����������ؼ� id+���Ͻú��� �� ������� ���� �����ϵ��� �߰�
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
	
	public static void valCheck(String val) {
	    switch(val){
	      case "IPFilterring_Check": checkMsg = "IP�� �Է��ϼ���.";
	      break;
	      case "IPFilterring_valCheck": checkMsg = "�ý��� ������ �߻��Ǿ����ϴ�. �ٽ� �õ����ּ���.";
	      break;
	      case "IPFilterring_register": checkMsg = "IP�� ��ϵǾ����ϴ�.";
	      break;
	      case "IPFilterring_duplication": checkMsg = "��ϵ� IP �Դϴ�. �ٽ� �Է����ּ���.";
	      break;
	      case "IPFilterring_searchInputCheck": checkMsg = "�˻�� �Է��� �ּ���";
	      break;
	      case "IPFilterring_delCheck": checkMsg = "������ IP�� �����ϼ���.";
	      break;
	      case "IPFilterring_del": checkMsg = "IP�� �����Ǿ����ϴ�.";
	      break;
	      case "userGroupSetting_Name_null": checkMsg = "ȸ�� �׷���� �Է��ϼ���.";
	      break;
	      case "userGroupSetting_Variable": checkMsg = "ȸ�� �������� �Է��ϼ���.";
	      break;
	      case "userGroupSetting_Register": checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
	      break;
	      case "userGroupSetting_searchNull": checkMsg = "�˻�� �Է��ϼ���.";
	      break;
	      case "userGroupSetting_Modify": checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
	      break;
	      case "userGroupSetting_selectCheck": checkMsg = "������ ȸ���׷��� �����ϼ���.";
	      break;
	      case "userGroupSetting_del_confirm": checkMsg = "��ü ȸ���׷��� �����Ͻðڽ��ϱ�?";
	      break;
	      case "userGroupSetting__alert": checkMsg = "ȸ�� �׷��� ���� �Ǿ����ϴ�.";
	      break;
	    }
	    if(val.equals("userGroupSetting_del_confirm")) {
	    	// ȸ���׷� ���� confirm�� modal-backdrop�� element üũ�� ������ �������� üũ ����
	    	$(".btn-sm", 5).waitUntil(visible, 10000);
	    } else {
		    $(".modal-backdrop").waitUntil(visible, 10000);	    	
	    }
	    $$("p").last().click();
	    String msgCheck = $$("p").last().text().trim();
	    Thread.onSpinWait();
	    if(msgCheck.equals(checkMsg)) { //val�� checkMsg ���ؼ� ������
	        if(val.substring(val.length()-7, val.length()).equals("confirm")) { //val ���� 7�ڸ� confirm�̶� ���ؼ� ������ btn-info Ŭ��
	            System.out.println(" *** val : " + val +  " - confirm check Success !! *** ");
	            if(val.equals("userGroupSetting_del_confirm")) { //ȸ���׷켳�� ������ ���� confirm�� UI�� �޶� ���� ó��
	            	System.out.println(" *** val : " + val +  " - userGroupSetting_del_confirm check Success !! *** ");
	            	$(".btn-sm", 5).click(); //id�� üũ�Ϸ��ߴ��� prevObject ��� �� �������� class�θ� üũ����
	            } else {
		            $$(".btn-info").last().click();
	            }
	            if(val.equals("userGroupSetting_del_confirm")) {
	            	// ȸ���׷� ���� confirm�� modal-backdrop�� element üũ�� ������ �������� üũ ����
	            } else {
		            $(".modal-backdrop").waitUntil(hidden, 10000);
	            }
	        } else { //confirm �ƴϸ� .btn-smŬ��
	            System.out.println(" *** " + val +  " - check Success !! *** ");
	            $$(".btn-sm").last().click();
	            $(".modal-backdrop").waitUntil(hidden, 10000);
	        }
	    } else if (msgCheck.isEmpty()) { //alert �ε��� �ʰų� ������� �ʾ����� üũ�ϱ����� �� üũ
	        System.out.println(" *** �١ڡ١ڡ١� " + val +  " - msgCheck is Empty ... �١ڡ١ڡ١� *** ");
	        System.out.println(checkMsg);
	        close();
	    } else { // msgCheck=checkMsg����, confirm&alert����, �� ���� üũ �� fail
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
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat", 1).click();
		$("h3", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h3", 2).text().trim();
		if(pageLoadCheck.equals("�湮��")) {
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
		$(By.linkText("IP���͸�����")).waitUntil(visible, 10000);
		$(By.linkText("IP���͸�����")).click();
		$("h5", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
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
		$("#filter-ipd").setValue("��");
		$(".btn-info", 2).click();
		valCheck("IPFilterring_valCheck");
		$(".btn-info", 2).waitUntil(hidden, 10000);
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
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
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
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
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- IPFilterring_del End ----- ! ");
	}
	@Test(priority = 11)
	public void userGroupSetting_add() {
		$(By.linkText("ȸ���׷켳��")).click();
		$("h5", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
			System.out.println(" *** userGroupSetting list Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting list Page load Fail ... *** ");
			close();
		}
		$("#memgrpAdd").click();
		$(".notokr-medium").waitUntil(visible, 10000);
		pageLoadCheck = $(".notokr-medium").text();
		if(pageLoadCheck.equals("ȸ���׷� �߰��ϱ�")) {
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
		if(pageLoadCheck.equals("�߰�")) {
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
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
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
		if(pageLoadCheck.equals("ȸ���׷� �߰��ϱ�")) {
			System.out.println(" *** userGroupSetting add Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting add Page load Fail ... *** ");
			close();
		}
		$("#md_p_name").setValue(date + "����");
		$("#md_name_1").setValue(date + "����");
		$("#md_name_2").setValue(date + "����");
		$("#add_group_regist").click();
		confirm("�����Ͻðڽ��ϱ�?");
		valCheck("userGroupSetting_Modify");
		$("#memgrpAdd").waitUntil(visible, 10000);
		pageLoadCheck = $("#memgrpAdd").text();
		if(pageLoadCheck.equals("�߰�")) {
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
		if(pageLoadCheck.equals("���")) {
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
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
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