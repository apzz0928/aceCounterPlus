package aceCounterPlus;

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

public class userSetting {
	@SuppressWarnings("unused")
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg;
	
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
	
	public static void valCheck(int pTagNum, int btnNum, String val) {
		String msgCheck = $("p", pTagNum).text();
		if(val.equals("CheckIP")) {
			checkMsg = "IP�� �Է��ϼ���.";
		} else if (val.equals("IPvalCheck")) {
			checkMsg = "�ý��� ������ �߻��Ǿ����ϴ�. �ٽ� �õ����ּ���.";
		} else if (val.equals("IPregister")) {
			checkMsg = "IP�� ��ϵǾ����ϴ�.";
		}  else if (val.equals("IPduplication")) {
			checkMsg = "��ϵ� IP �Դϴ�. �ٽ� �Է����ּ���.";
		}else if (val.equals("searchInputCheck")) {
			checkMsg = "�˻�� �Է��� �ּ���";
		} else if (val.equals("delcheck")) {
			checkMsg = "������ IP�� �����ϼ���.";
		} else if (val.equals("delIP")) {
			checkMsg = "IP�� �����Ǿ����ϴ�.";
		} else if (val.equals("groupName_null")) {
			checkMsg = "ȸ�� �׷���� �Է��ϼ���.";
		} else if (val.equals("groupVariable")) {
			checkMsg = "ȸ�� �������� �Է��ϼ���.";
		} else if (val.equals("groupRegister")) {
			checkMsg = "����� �Ϸ�Ǿ����ϴ�.";
		} else if (val.equals("groupSearch_null")) {
			checkMsg = "�˻�� �Է��ϼ���.";
		} else if (val.equals("groupModify")) {
			checkMsg = "������ �Ϸ�Ǿ����ϴ�.";
		} else if (val.equals("delGroup_check")) {
			checkMsg = "������ ȸ���׷��� �����ϼ���.";
		} else if (val.equals("delGroup_confirm")) {
			checkMsg = "��ü ȸ���׷��� �����Ͻðڽ��ϱ�?";
		} else if (val.equals("delGroup_alert")) {
			checkMsg = "ȸ�� �׷��� ���� �Ǿ����ϴ�.";
		}
		$(".modal-backdrop").waitUntil(visible, 10000);
		if(msgCheck.equals(checkMsg)) {
			System.out.println(" *** " + val + " - check Success !! *** ");
			$(".btn-sm", btnNum).click();
		} else {
			System.out.println(" *** " + val + " -  check Fail ... *** ");
			close();
		}
	}

	@Test(priority = 0)
	public void IPFilterring_add() throws InterruptedException {
		System.out.println(" ! ----- IPFilterring_add Start ----- ! ");
		open(baseUrl);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text();
		$(".btn_logout").getValue();
		if(loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... *** ");
			close();
		}
		$(".go_stat", 1).click();
		String pageLoadCheck = $("h3", 2).text();
		if(pageLoadCheck.equals("�湮��")) {
			System.out.println(" *** statsLiveDashboard Page access Success !! *** ");
		} else {
			System.out.println(" *** statsLiveDashboard Page access Fail ... *** ");
			close();
		}
		$("#redirectConfBtn").click();
		Thread.sleep(1000);
		$("#redirectConfBtn").waitUntil(visible, 10000);
		$(".accordion-toggle", 1).click();
		$(By.linkText("IP���͸�����")).click();
		Thread.sleep(1000);
		$("h5", 2).waitUntil(visible, 10000);
		pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 10000);
		$(".btn-info", 2).click();
		Thread.sleep(1500);
		valCheck(4, 3, "CheckIP");
		Thread.sleep(3500);
		$("#filter-ipa").setValue("127");
		$(".btn-info", 2).click();
		valCheck(5, 4, "IPCheck");
		$("#filter-ipb").setValue("0");
		$(".btn-info", 2).click();
		valCheck(6, 5, "IPCheck");
		$("#filter-ipc").setValue("0");
		$(".btn-info", 2).click();
		valCheck(7, 6, "IPCheck");
		$("#filter-ipd").setValue("��");
		$(".btn-info", 2).click();
		valCheck(8, 7, "IPvalCheck");
		Thread.sleep(1000);
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring Page val check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page val check Reload Fail ... *** ");
			close();
		}
		$(".btn-info", 0).waitUntil(visible, 10000);
		$(".btn-info", 0).click();
		$("th", 0).waitUntil(visible, 10000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring Page register check Reload Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page register check Reload Fail ... *** ");
			close();
		}
		valCheck(3, 3, "IPregister");
		$(".modal-backdrop").waitUntil(hidden, 10000);
		System.out.println(" ! ----- IPFilterring_add End ----- ! ");
	}
	@Test(priority = 1)
	public void IPFilterring_duplicationCheck() throws InterruptedException {
		System.out.println(" ! ----- IPFilterring_duplicationCheck Start ----- ! ");
		$(".btn-info", 0).click();
		Thread.sleep(1500);
		$("th", 0).waitUntil(visible, 10000);
		$("#filter-ipa").setValue("127");
		$("#filter-ipb").setValue("0");
		$("#filter-ipc").setValue("0");
		$("#filter-ipd").setValue("1");
		$(".btn-info", 2).click();
		valCheck(4, 4, "IPduplication");
		Thread.sleep(1500);
		$(".btn-light", 0).waitUntil(visible, 10000);
		$(".btn-light", 0).click();
		System.out.println(" ! ----- IPFilterring_duplicationCheck End ----- ! ");
	}
	@Test(priority = 2)
	public void IPFilterring_search() {
		System.out.println(" ! ----- IPFilterring_search Start ----- ! ");
		$(".btn-default", 5).click();
		valCheck(5, 5, "searchInputCheck");
		$("#searchIp").setValue("03");
		$(".btn-default", 5).click();
		String search = $("h5", 2).text();
		if(search.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring don`t input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring don`t input search check Fail ... *** ");
			close();
		}
		$("#searchIp").setValue("127");
		$(".btn-default", 5).click();
		$("td", 3).waitUntil(visible, 10000);
		search = $("td", 3).text();
		if(search.equals("127.0.0.1")) {
			System.out.println(" *** IP Filterring input search check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring input search check Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- IPFilterring_search End ----- ! ");
	}
	@Test(priority = 3)
	public void IPFilterring_del() throws InterruptedException {
		System.out.println(" ! ----- IPFilterring_del Start ----- ! ");
		$(".btn-gray", 0).click();
		String pageCheck = $(".btn-info", 1).text();
		Thread.sleep(1000);
		if(pageCheck.equals("�����׸����")) {
			System.out.println(" *** IP Filterring delete page Load check Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring delete page Load check Fail ... *** ");
			close();
		}
		$(".btn-info", 1).click();
		valCheck(3, 3, "delcheck");
		$("#chkAll").click();
		$(".btn-info", 1).click();		
		Thread.sleep(1000);
		$("h5", 2).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 2).text();
		if(pageLoadCheck.equals("��ϵ� IP�� �����ϴ�.")) {
			System.out.println(" *** IP Filterring Page load Success !! *** ");
		} else {
			System.out.println(" *** IP Filterring Page load Fail ... *** ");
			close();
		}
		valCheck(4, 3, "delIP");
		System.out.println(" ! ----- IPFilterring_del End ----- ! ");
	}
	@Test(priority = 4)
	public void userGroupSetting_add() throws InterruptedException {
		$(By.linkText("ȸ���׷켳��")).click();
		Thread.sleep(1000);
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
			close();
		}
		Thread.sleep(1500);
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
		valCheck(3, 3, "groupName_null");
		$("#md_p_name").setValue(date);
		$("#add_group_regist").click();
		valCheck(4, 4, "groupVariable");
		$("#md_name_1").setValue(date);
		$("#add_group_regist").click();
		valCheck(5, 5, "groupVariable");
		$("#md_name_2").setValue(date);
		$("#add_group_regist").click();
		Thread.sleep(1000);
		$("#memgrpAdd").waitUntil(visible, 10000);
		pageLoadCheck = $("#memgrpAdd").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** userGroup Register Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroup Register Page load Fail ... *** ");
			close();
		}
		valCheck(3, 3, "groupRegister");
		System.out.println(" ! ----- userGroupSetting_add End ----- ! ");
	}
	@Test(priority = 5)
	public void userGroupSetting_search() throws InterruptedException {
		System.out.println(" ! ----- userGroupSetting_search Start ----- ! ");
		$("#frmBtn").click();
		valCheck(4, 4, "groupSearch_null");
		$("#searchNm").setValue("qwer");
		$("#frmBtn").click();
		$("h5", 1).waitUntil(visible, 10000);
		String pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
			close();
		}
		$("#searchNm").setValue(date);
		$("#frmBtn").click();
		pageLoadCheck = $("td", 2).text();
		if(pageLoadCheck.equals(date)) {
			System.out.println(" *** userGroupSetting Date search Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Date search Fail ... *** ");
			close();
		}
		System.out.println(" ! ----- userGroupSetting_search End ----- ! ");
	}
	@Test(priority = 6)
	public void userGroupSetting_modify() throws InterruptedException {
		System.out.println(" ! ----- userGroupSetting_modify Start ----- ! ");
		$(".btn-xs").click();
		String pageLoadCheck = $(".notokr-medium").text();
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
		Thread.sleep(1000);
		$("#memgrpAdd").waitUntil(visible, 10000);
		pageLoadCheck = $("#memgrpAdd").text();
		if(pageLoadCheck.equals("�߰�")) {
			System.out.println(" *** userGroup Register Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroup Register Page load Fail ... *** ");
			close();
		}
		valCheck(3, 3, "groupModify");		
		System.out.println(" ! ----- userGroupSetting_modify End ----- ! ");
	}
	@Test(priority = 7)
	public void userGroupSetting_del() throws InterruptedException {
		System.out.println(" ! ----- userGroupSetting_del Start ----- ! ");
		$(".btn-gray", 0).click();
		String pageLoadCheck = $(".btn-gray", 1).text();
		if(pageLoadCheck.equals("���")) {
			System.out.println(" *** userGroupSetting delete UI load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting delete UI load Fail ... *** ");
			close();
		}
		$(".btn-gray", 1).click();
		$(".btn-gray", 0).click();
		$(".btn-info", 1).click();
		valCheck(4, 4, "delGroup_check");
		$("#chkAll").click();
		$(".btn-info", 1).click();
		valCheck(5, 5, "delGroup_confirm");
		pageLoadCheck = $("h5", 1).text();
		if(pageLoadCheck.equals("��ϵ� ȸ���׷��� �����ϴ�.")) {
			System.out.println(" *** userGroupSetting Page load Success !! *** ");
		} else {
			System.out.println(" *** userGroupSetting Page load Fail ... *** ");
			close();
		}
		valCheck(4, 3, "delGroup_alert");
		
		System.out.println(" ! ----- userGroupSetting_del End ----- ! ");
	}
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}