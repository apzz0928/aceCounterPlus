package aceCounterPlus;

import java.net.MalformedURLException;
import java.net.URL;

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

public class marketing {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, dateCheck;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		pw = "qordlf";
		A = "!@34";

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
			driver.manage().window().setSize(new Dimension(1650, 1000));
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
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat").click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("Live ��ú���")) {
			System.out.println(" *** stats marketing login Success !! *** ");
		} else {
			System.out.println(" *** stats marketing login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}

	@Test(priority = 1)
	public void marketing_summary() {
		System.out.println(" ! ----- marketing_summary Start ----- ! ");
		$("#marketing").click();
		$(By.linkText("������")).waitUntil(visible, 10000);
		$(By.linkText("������")).click();
		$("#top-menu-name").waitUntil(visible, 10000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("������")) {
			System.out.println(" *** marketing_summary page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_summary page load Fail ... !@#$%^&*() *** ");
			close();
		}
		sleep(1500);
		$("#daterangepicker2").waitUntil(visible, 10000);
		$("#daterangepicker2").click();
		$(".month", 0).waitUntil(visible, 10000);
		//��¥����
		for(int i=0;i<=1;i++) { //���� ��¥ : ����Ķ�������� ����(0�� 1 ����, �ε�ȣ ����, ++�� --����)
			if(i==0) {
				System.out.println("start calender date selecting..");	
			} else {
				System.out.println("end calender date selecting..");				
			}
			dateCheck = $(".month", i).text().trim(); //2��° �޷� �� Ȯ��
			for(int x=0;x<=100;x++) { //2018.12�� �ɶ����� << Ŭ��
				if(dateCheck.equals("2018.12")) {
					$("td[data-title=r3c3]", i).click(); //19�� ����
					break;
				} else {
					System.out.println("no"+ (i+1) + ". calender month is  : " + dateCheck + " // nextBtn(" + x + ") click");
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
		$("#compareTermText").waitUntil(visible, 10000);
		dateCheck = $("#compareTermText").text();
		String[] pLC = dateCheck.split(" ");
		if (pLC[0].equals("2018.12.18") && pLC[2].equals("2018.12.18")) {
			System.out.println(" *** marketing_summary date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** marketing_summary date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".summary-data", 0).waitUntil(visible, 10000);
		$("td", 81).waitUntil(visible, 10000);
		String[] panelDataCheck = {"0", "0", "0", "0", "0", "((CTR))", "((sales))", "((advertising cost))", "((CPA))", "((ROAS))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** marketing_summary panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		String[] tableDataCheck = {"���̹��귣��˻�", "13", "12", "92.31%", "0", "0", "0", "0%", "0", "0", "0%", "0", "0%", "0", "0", "((media))", "((visit number))"
				, "((return number))", "((return percent))", "((reach number))", "((click number))", "((advertising cost))", "((CTR))", "((CPC))", "((CPA))", "((ROAS))", "((convert number))", "((convert parcent))", "((convert sales))", "((visit sales))"};
		$("td", 37).waitUntil(visible, 10000);
		for(int i=0;i<=14;i++) {
			pageLoadCheck = $("td", (i+37)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** marketing_summary table data " + tableDataCheck[i+15] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary table data " + tableDataCheck[i+15] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] barChartDataCheck = {"2018.12.19(��)", "īī���귣��˻�: 19", "���̹��귣��˻�: 13", "((daily publication))", "((daum brand search))", "((naver brand search))"}; 
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-series-group", 0).hover();
		$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=2;i++) {
			if (pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** marketing_summary bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}			
		}
		$("#btnChartPie").click();
		String[] pieChartDataCheck = {"���̹��귣��˻��湮��: 23.2%", "īī���귣��˻��湮��: 33.9%", "īī����湮��: 21.4%", "����Ads�湮��: 21.4%", "((naver brand search))", "((daum brand search))", "((kakaotalk))", "((google adwords))"};
		$(".highcharts-series-0.highcharts-tracker > path", 7).waitUntil(visible, 10000);
		for(int i=0;i<=3;i++) {
			$(".highcharts-series-0.highcharts-tracker > path", i).hover();
			$(".highcharts-series-0.highcharts-tracker > path", i).hover();
			pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
			/*System.out.println("pageLoadCheck is :" + pageLoadCheck + ".");*/
			if(pageLoadCheck.equals(pieChartDataCheck[i])) {
				System.out.println(" *** marketing_summary pie chart data " + pieChartDataCheck[i+4] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary pie chart data " + pieChartDataCheck[i+4] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartLine").click();
		sleep(1000);
		String[] lineChartDataCheck = {"2018.12.19(��)", "īī���귣��˻�: 19", "���̹��귣��˻�: 13", "((date))", "((daum brand search))", "((naver brand search))"};
		$(".highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
		$(".highcharts-series-0.highcharts-tracker > path", 0).hover();
		$(".highcharts-series-1.highcharts-tracker > path", 0).hover();
		$(".highcharts-series-0.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** marketing_summary line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_summary line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
	    System.out.println(" ! ----- marketing_summary End ----- ! ");
	}
	@Test(priority = 2)
	public void marketing_detail() {
		System.out.println(" ! ----- marketing_detail Start ----- ! ");
		$(By.linkText("��")).waitUntil(visible, 10000);
		$(By.linkText("��")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("��")) {
			System.out.println(" *** marketing_detail page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_detail page load Fail ... !@#$%^&*() *** ");
			close();
		}
	    $(By.id("chartSelect1")).click();
	    $(By.id("chartSelect1")).selectOption("�湮��");
	    $(By.id("chartSelect2")).click();
	    $(By.id("chartSelect2")).selectOption("�����");
	    $(".highcharts-series-0.highcharts-tracker > rect").waitUntil(visible, 10000);
	    String[] tableDataCheck = {"���̹��귣��˻�", "13", "92.31%", "0", "0", "0", "0%", "0", "0", "0%", "0", "0%", "0", "0", "((product))", "((visit number))", "((return percent))", 
	    		"((reach number))", "((click number))", "((advertising cost))", "((CTR))", "((CPC))", "((CPA))", "((ROAS))", "((convert number))", "((convert percent))", "((convert sales))", "((visit sales))"};
	    $("td", 41).waitUntil(visible, 10000);
	    for(int i=0;i<=13;i++) {
			pageLoadCheck = $("td", (i+41)).text().trim();
			if (pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** marketing_detail table data " + tableDataCheck[i+14] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail table data " + tableDataCheck[i+14] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    
	    String[] barChartDataCheck = {"2018.12.19(��)", "�湮��: 56", "�����: 0", "daily publication", "visit number", "reach number"};
	    $(".highcharts-series-0.highcharts-tracker", 0).hover();
	    $(".highcharts-series-0.highcharts-tracker", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
	    for(int i=0;i<=2;i++) {
			if (pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** marketing_detail bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail bar chart data " + barChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    pLC = null;
	    $("#btnChartLine").click();
	    $("#chartSelect3").waitUntil(visible, 10000);
		/*
		 * $("#chartSelect3").click(); $(By.
		 * xpath("(.//*[normalize-space(text()) and normalize-space(.)='��ȸ'])[2]/following::option[1]"
		 * )).click();
		 */
	    $(By.id("chartSelect3")).click();
	    $(By.id("chartSelect3")).selectOption("�湮��");
	    $(".highcharts-tracker", 3).hover();
	    $(".highcharts-tracker", 5).hover();
	    $(".highcharts-tracker", 7).hover();
	    $(".highcharts-tracker", 3).hover();
	    //������Ʈ ���׷����� �ּ�ó��
	    String[] lineChartDataCheck = {"2018.12.19(��)", "īī���귣��˻�: 19", "���̹��귣��˻�: 13", "((daily publication))", "((daum brand search))", "((naver brand search))"};
	    pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
	    pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=pLC.length-1;i++) {
			System.out.println("pLC[" + i + "] is : " + pLC[i]);
		}
	    for(int i=0;i<=2;i++) {
			if (pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** marketing_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_detail line chart data " + lineChartDataCheck[i+3] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
	    }
	    System.out.println(" ! ----- marketing_detail End ----- ! ");
	}
	@Test(priority = 3)
	public void marketing_clickChoice() {
		System.out.println(" ! ----- marketing_clickChoice Start ----- ! ");
		//$(By.linkText("���̹� �˻�����")).waitUntil(visible, 10000);
		$(By.linkText("���̹� �˻�����")).click();
		$(".active", 2).waitUntil(visible, 10000);
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("���̹� �˻�����")) {
			System.out.println(" *** marketing_clickChoice page load Success !! *** ");
		} else {
			System.out.println(" *** marketing_clickChoice page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"0", "0", "0", "0", "0", "((visit number))", "((new visit number))", "((return percent))", "((purchase number))", "((sales))"};
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $(".summary-data", i).text().trim();
			if (pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** marketing_clickChoice panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** marketing_clickChoice panel summary data " + panelDataCheck[i+5] + "((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] tableDataCheck = {"��ȸ�� �����Ͱ� �����ϴ�.", "((no-records-found))"};
		pageLoadCheck = $("td", 7).text();
		if(pageLoadCheck.equals(tableDataCheck[0])) {
			System.out.println(" *** marketing_clickChoice table data " + panelDataCheck[1] + "((0))" + " check Success !! *** ");
		} else {
			System.out.println(" *** marketing_clickChoice panel summary data " + panelDataCheck[1] + "((0))" + " check Fail ... !@#$%^&*() *** ");
			close();
		}
	    System.out.println(" ! ----- marketing_clickChoice End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}