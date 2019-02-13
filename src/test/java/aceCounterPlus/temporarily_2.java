package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class temporarily_2 {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, A, pageLoadCheck, dateCheck, nodata;
	
	Date date = new Date();
    SimpleDateFormat date_format = new SimpleDateFormat("YYYYMMddHHmmss");
    String id_date = date_format.format(date);
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf";
		A = "!@34";
		nodata = "��ȸ�� �����Ͱ� �����ϴ�.";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*
			 * ChromeOptions options = new ChromeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			// cap = DesiredCapabilities.firefox();
			// RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			//driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*
			 * EdgeOptions options = new EdgeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*
			 * InternetExplorerOptions options = new InternetExplorerOptions(); driver = new
			 * RemoteWebDriver(new URL(urlToRemoteWD), options);
			 */
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
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}

	@Test(priority = 0)
	public void login() {
		System.out.println(" ! ----- login Start ----- ! ");
		open(baseUrl);
		$(".gnb").waitUntil(visible, 15000);
		$("#uid").setValue("apzz0928888");
		$("#upw").setValue(pw + A);
		$(".btn_login").click();
		String loginCheck = $(".btn_logout").text().trim();
		$(".btn_logout").getValue();
		if (loginCheck.equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat").click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("Live ��ú���")) {
			System.out.println(" *** stats convert login Success !! *** ");
		} else {
			System.out.println(" *** stats convert login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}
	@Test(priority = 1)
	public void KPI() {
		System.out.println(" ! ----- KPI Start ----- ! ");
		$("#KPI").click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("KPI")) {
			System.out.println(" *** KPI page load Success !! *** ");
		} else {
			System.out.println(" *** KPI page load Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=1;i<=2;i++) {
		    //Ķ������ ��¥ ���
		    Calendar cal = Calendar.getInstance(); //Ķ���� ����
		    cal.setTime(new Date()); //Ķ���� �ð��� ���� ��¥�� ����
		    cal.add(Calendar.DATE, (-i)); //Ķ������ -1��
		    //��¥�� ����� �������� ����
		    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd"); // ����� ��¥ ���� ����
		    String id_date = date_format.format(cal.getTime()); //id_date�� ���信 ���缭 Ķ������ �ð����� ����
			pageLoadCheck = $(".th-inner", i).text().trim();
			//���̺��� ��¥ ǥ�� �κ� �ؽ�Ʈ �����ؿͼ� ���� -1, -2���̶� ��
			String[] pLC = pageLoadCheck.split(" ~ ");
			if(pLC[1].equals(id_date)) {
				System.out.println(" *** KPI table data base Period((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** KPI table data base Period((" + i + ")) Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"��������", "ȸ����", "�ű԰��Լ�", "�α��μ�", "�α�����", "������", "������", "�湮��", "�űԹ湮��", "��湮��", "�ݼۼ�", "�ݼ۷�"
				, "�湮�� ��������", "�湮�� ü���ð�"};
		for(int i=0,x=0;i<=27;i++) {
			if(i%2 == 1) {
				pageLoadCheck = $(".another-class", i).text().trim();
				if(pageLoadCheck.equals(tableDataCheck[x])) {
			        System.out.println(" *** KPI table data ((" + x + ")) check Success !! *** ");
					x = ++x;
				} else {
			        System.out.println(" *** KPI table data ((" + x + ")) check Fail ... !@#$%^&*() *** ");
			        close();
			    }
			}
		}
		System.out.println(" ! ----- KPI End ----- ! ");
	}
	@Test(priority = 11)
	public void segment_list() {
		System.out.println(" ! ----- segment_list Start ----- ! ");
		$("#SEGMENT").click();
		$(".notokr-bold").waitUntil(visible, 15000);
		pageLoadCheck = $(".notokr-bold").text().trim();
		if (pageLoadCheck.equals("���׸�Ʈ")) {
			System.out.println(" *** segment_list page load Success !! *** ");
		} else {
			System.out.println(" *** segment_list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		//��¥���� (2018.12.21)
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) { //���� ��¥ : ����Ķ�������� ����(0�� 1 ����, �ε�ȣ ����, ++�� --����)
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
			}
			dateCheck = $(".month", i).text().trim(); //2��° �޷� �� Ȯ��
			for(int x=0;x<=100;x++) { //2018.12�� �ɶ����� << Ŭ��
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r3c5]", i).click(); //21�� ����
					break;
				} else {
					System.out.println("no"+ (i+1) + ". calender month is  : " + dateCheck + " // need nextBtn(" + x + ") click");
					$(".prev", i).click();
					dateCheck = $(".month", i).text().trim();
				}
			}
			if(i==0) {
				System.out.println("start calender date select!");	
			} else {
				System.out.println("end calender date select!");				
			}
		}
		$(".btn-apply").click();
		refresh();
		for(int i=0;i<=20;i++) {
			if($("td", 7).text().trim().equals(nodata)) {
				System.out.println(" *** segment_list table data check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** segment_list table data loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** segment_list table data check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		for(int i=0;i<=18;i++) { //�湮�Ӽ� ����
			if(!(i==1)) {
				$("#accord_dimension > ul > .li-selec", i).click();
			}
		}
		for(int i=1;i<=32;i++) { //���� ����
			$("#accord_metric > ul > .li-selec", i).click();
		}
		$("#filterRegist").click();
		$(".blickUI").waitUntil(hidden, 10000);
		$("td", 71).waitUntil(visible, 10000);
		for(int i=0;i<=18;i++) {
			String dimensionDataCheck = $(".selected > .pr > .li-selec", i).text().trim();
			pageLoadCheck = $(".th-inner", i+2).text().trim();
			if(pageLoadCheck.equals(dimensionDataCheck)) {
		        System.out.println(" *** segment_list table visit property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** segment_list table visit property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		String[] metricDataCheck = {"�湮��", "�湮�� ����", "�űԹ湮��", "�űԹ湮�� ����", "��湮��", "��湮�� ����", "���湮��", "���湮�� ����", "�ݼۼ�", "�ݼۼ� ����", "�ݼ۷�(%)"
				, "��������", "�������� ����", "�湮�� ��������", "ü���ð�", "�湮�� ü���ð�", "��ȯ��", "��ȯ�� ����", "��ȯ��(%)", "��ȯ �����", "��ȯ ����� ����"
				, "��ȯ�� �����", "���ŰǼ�", "���ŰǼ� ����", "������(%)", "�����", "����� ����", "���Ŵ� �����", "���ż���", "���ż��� ����", "���Լ�", "���Լ� ����"
				, "���Է�(%)", "������", "�α��μ�", "���ΰ˻� Ƚ��", "���ΰ˻��� ��Ż��", "��ٱ��ϴ��", "���ø���Ʈ���", "��ñ��Ź�ư", "����", "���ŰǼ�/����(%)"
				, "�������Ŭ����", "��ǰ ��������", "��ǰ �������� ����", "����/��ǰ ��������(%)"};
		for(int i=0;i<=45;i++) {
			if((i+21)%10 == 0) {
				$(".th-inner", i+21).scrollIntoView("{behavior: 'smooth', inline: 'start'}");
			}
			pageLoadCheck = $(".th-inner", i+21).text().trim();
			if(pageLoadCheck.equals(metricDataCheck[i])) {
		        System.out.println(" *** segment_list table visit performance ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** segment_list table visit performance ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".btn-dark", 0).scrollIntoView(false);
		$(".btn-dark", 0).click();
		$(".gui-input", 0).waitUntil(visible, 10000);
		$(".gui-input").setValue(id_date);
		for(int i=0;i<=18;i++) {
			if(i%3 == 0) {
				$(".dimension > .ui-sortable > .list-group-item").scrollIntoView(true);
			}
			String mySegmentSaveDimension = $(".selected > .pr > .li-selec", i).text().trim();
			pageLoadCheck = $(".dimension > .ui-sortable > .list-group-item", i).text().trim();
			if(pageLoadCheck.equals(mySegmentSaveDimension)) {
		        System.out.println(" *** mySegmentSave layer dimension property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** mySegmentSave layer dimension property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		for(int i=0;i<=32;i++) {
			if(i%3 == 0) {
				$(".metric > .ui-sortable > .list-group-item").scrollIntoView(true);
			}
			String mySegmentSaveMetric = $(".selected > ul > .li-selec", i+19).text().trim();
			pageLoadCheck = $(".metric > .ui-sortable > .list-group-item", i).text().trim();
			if(pageLoadCheck.equals(mySegmentSaveMetric)) {
		        System.out.println(" *** mySegmentSave layer metric property ((" + i + ")) check Success !! *** ");
			} else {
		        System.out.println(" *** mySegmentSave layer metric property ((" + i + ")) check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".btnSave").click();
		if($$("p").last().text().trim().equals("���� ���׸�Ʈ�� ����Ǿ����ϴ�.")) {
			$$(".btn-sm").last().click();
	        System.out.println(" *** mySegmentSave layer save alert msg check Success !! *** ");
		} else {
	        System.out.println(" *** mySegmentSave layer save alert msg check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		System.out.println(" ! ----- segment_list End ----- ! ");
	}
	@Test(priority = 12)
	public void segment_mySegment() {
		System.out.println(" ! ----- segment_mySegment Start ----- ! ");
		$(".delete", 0).waitUntil(visible, 10000);
		$(".text-underline", 0).click();
		$(".fs16", 0).waitUntil(visible, 10000);
		if($(".fs16", 0).text().trim().equals(id_date)) {
	        System.out.println(" *** segment_mySegment page name check Success !! *** ");
	        $(".filter-view", 0).click();
		} else {
	        System.out.println(" *** segment_mySegment page name check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		$(".viewSegment", 0).waitUntil(visible, 10000);
		$(".viewSegment", 0).click();
		$(".my-segment-nm").waitUntil(visible, 10000);
		if($(".my-segment-nm").text().trim().equals(id_date)) {
	        System.out.println(" *** segment_mySegment layer name check Success !! *** ");
	        $(".btnOk").click();
		} else {
	        System.out.println(" *** segment_mySegment layer name check Fail ... !@#$%^&*() *** ");
	        close();
	    }
		$(".br-l-n").waitUntil(enabled, 10000);
		$(".br-l-n").setValue("aaa");
		$("#btn-search").click();
		for(int i=0;i<=20;i++) {
			if($("td", 0).text().trim().equals("��ϵ� ���� ���׸�Ʈ ������ �����ϴ�.")) {
				System.out.println(" *** segment_mySegment search check Success !! *** ");
				break;
			} else if(i<=19) {
				System.out.println(" *** segment_mySegment search check wait 0." + i + " second *** ");
				sleep(100);
			} else {
				System.out.println(" *** segment_mySegment search check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".br-l-n").waitUntil(enabled, 10000);
		$(".br-l-n").setValue(id_date);
		$("#btn-search").click();
		for(int i=0;i<=20;i++) {
			if($("td", 1).text().trim().equals(id_date)) {
				System.out.println(" *** segment_mySegment search check Success !! *** ");
				break;
			} else if(i<=19) {
				System.out.println(" *** segment_mySegment search check wait 0." + i + " second *** ");
				sleep(100);
			} else {
				System.out.println(" *** segment_mySegment search check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".delete", 0).click();
		$("#btn-modal-alert-yes").waitUntil(visible, 10000);
		$$("#btn-modal-alert-yes").last().click();
		if($$("p").last().text().trim().equals("������ �Ϸ�Ǿ����ϴ�.")) {
	        System.out.println(" *** segment_mySegment delete alert msg check Success !! *** ");
			$$(".btn-sm").last().click();
		} else {
	        System.out.println(" *** segment_mySegment delete alert msg check Fail ... !@#$%^&*() *** ");
	        close();
		}
		System.out.println(" ! ----- segment_mySegment End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}