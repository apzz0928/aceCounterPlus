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

public class temporarily_2 {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, dateCheck, nodata;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://new.acecounter.com/common/front";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
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
	public void page_contents_popularPage() {
		System.out.println(" ! ----- page_contents_popularPage Start ----- ! ");
		$("#contents").click();
		$(By.linkText("������")).waitUntil(visible, 10000);
		$(By.linkText("������")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("������")) {
			System.out.println(" *** page_contents_popularPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_contents_popularPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 98).waitUntil(visible, 30000);
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
		$(".pull-right", 3).waitUntil(visible, 10000);
		dateCheck = $(".pull-right", 3).text().trim();
		String[] pLC = dateCheck.split(" ����");
		if (pLC[0].equals("���� ������")) {
			System.out.println(" *** page_contents_popularPage date range pick Success !! *** ");
			pLC = null;
		} else {
			System.out.println(" *** page_contents_popularPage date range pick Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"/", "79", "11.50%", "00:00:15", "00:00:00", "78", "78", "69", "68"};
	    $("td", 36).waitUntil(visible, 10000);
		//td 36
		for(int i=0;i<=8;i++) {
			pageLoadCheck = $("td", (i+36)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_contents_popularPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_popularPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21(��)", "��������: 687", "������ �湮��: 665"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_contents_popularPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_popularPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 9).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 5).hover();
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 9).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();		
		pLC = pageLoadCheck.split("�� ");
		String[] lineChartDataCheck = {"2018.12.21(��)", "/: 79", "/search/label/missing/missingPage: 27", "/search/label/marketing-normal: 21", "/search/label/change-order: 21", "/search/label/inHouse-Talk/: 21"};
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** page_contents_popularPage line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_popularPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- page_contents_popularPage End ----- ! ");
	}
	//@Test(priority = 2)
	public void page_page_contents_groupPage() {
		System.out.println(" ! ----- page_contents_groupPage Start ----- ! ");
		$(By.linkText("�������׷�")).waitUntil(visible, 10000);
		$(By.linkText("�������׷�")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("�������׷�")) {
			System.out.println(" *** page_contents_groupPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_contents_groupPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] tableDataCheck = {"�̵��������", "687", "100%", "366", "1.88"};
		$("td", 14).waitUntil(visible, 10000);
		for(int i=0;i<=4;i++) {
			pageLoadCheck = $("td", (i+14)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_contents_groupPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_groupPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pageLoadCheck = $("tspan", 0).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** page_contents_groupPage line chart data check Success !! *** ");
		} else {
			System.out.println(" *** page_contents_groupPage line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		$("tspan", 1).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			pageLoadCheck = $("tspan", i).text().trim();
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** page_contents_groupPage pie chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_groupPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}	
		}
		System.out.println(" ! ----- page_contents_groupPage End ----- ! ");
	}
	//@Test(priority = 3)
	public void page_page_contents_InlinkPage() {
		System.out.println(" ! ----- page_contents_InlinkPage Start ----- ! ");
		$(By.linkText("���ι��")).waitUntil(visible, 10000);
		$(By.linkText("���ι��")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("���ι��")) {
			System.out.println(" *** page_contents_InlinkPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_contents_InlinkPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"���ι��( banner-inner )\n9", "���ι��( banner-inner )\n0%", "-\n0"};
		$(".top-component", 0).waitUntil(visible, 10000);
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $(".top-component", (i)).text().trim();
			if(pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** page_page_contents_InlinkPage panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_contents_InlinkPage panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("td", 21).click();
		$("td", 30).waitUntil(visible, 10000);
		String[] tableDataCheck = {"�� banner-inner", "9", "9", "100%"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("td", (i+30)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_page_contents_InlinkPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_contents_InlinkPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21(��)", "���ι��: 9", "�հ�: 9"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_page_contents_InlinkPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_contents_InlinkPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 3).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();		
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 3).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("�� ");
		String[] lineChartDataCheck = {"2018.12.21(��)", "���ι��: 9"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** page_page_contents_InlinkPage line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_page_contents_InlinkPage line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- page_page_contents_InlinkPage End ----- ! ");
	}
	//@Test(priority = 4)
	public void page_page_contents_InternalPage() {
		System.out.println(" ! ----- page_contents_InternalPage Start ----- ! ");
		$(By.linkText("���ΰ˻�")).waitUntil(visible, 10000);
		$(By.linkText("���ΰ˻�")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("���ΰ˻�")) {
			System.out.println(" *** page_contents_InternalPage page load Success !! *** ");
		} else {
			System.out.println(" *** page_contents_InternalPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 29).waitUntil(visible, 10000);
		String[] tableDataCheck = {"�հ�", "149", "100%", "144"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("td", (i+29)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** page_contents_InternalPage table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_InternalPage table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21 (��)", "�˻�Ƚ��: 149", "���ŰǼ�: 0"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** page_contents_InternalPage bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** page_contents_InternalPage bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartPie").click();
		$(".highcharts-tracker > path", 6).waitUntil(visible, 10000);
		String[] pieChartDataCheck = {"8.1%", "8.1%", "8.1%", "8.1%", "8.1%", "��Ÿ�˻�Ƚ��: 59.7%"};
		for(int i=0;i<=5;i++) {
			$(".highcharts-tracker > path", (i+1)).hover();
			$(".highcharts-tracker > path", (i+1)).hover();
			$(".highcharts-tracker > path", (i+1)).hover();
			pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
			if(i<5) {
				if(pageLoadCheck.substring(pageLoadCheck.length()-4, pageLoadCheck.length()).equals(pieChartDataCheck[i])) {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pageLoadCheck.equals(pieChartDataCheck[i])) {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}	
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 11).waitUntil(visible, 10000);
		$(".highcharts-tracker", 9).hover();		
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 11).hover();
		pageLoadCheck = $(".highcharts-tooltip", 2).text().trim();
		pLC = pageLoadCheck.split("�� ");
		String[] lineChartDataCheck = {"2018.12.21 (��)", "12", "12", "12", "12", "12"};
		for(int i=0;i<=5;i++) {
			if(i == 0) {
				if(pLC[i].equals(lineChartDataCheck[i])) {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if(pLC[i].substring(pLC[i].length()-2, pLC[i].length()).equals(lineChartDataCheck[i].substring(lineChartDataCheck[i].length()-2, lineChartDataCheck[i].length()))) {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_contents_InternalPage pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- page_contents_InternalPage End ----- ! ");
	}
	@Test(priority = 11)
	public void route_route_contents_moveRoute() {
		System.out.println(" ! ----- route_contents_moveRoute Start ----- ! ");
		$(By.linkText("���")).waitUntil(visible, 10000);
		$(By.linkText("���")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("���")) {
			System.out.println(" *** contents_popularPage page load Success !! *** ");
		} else {
			System.out.println(" *** contents_popularPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".blockUI", 2).waitUntil(hidden, 10000);
		String[] depthLevDataCheck = {"366", "356", "", "10", "0", "", "10", "0", "", "10", "0"};
		for(int i=0;i<=10;i++) {
			if((i+25)%3 > 0) {
				pageLoadCheck = $("text", (i+25)).text().trim();
				String[] pLC = pageLoadCheck.split(" : ");
				if(pLC[1].equals(depthLevDataCheck[i])) {
					System.out.println(" *** page_contents_InternalPage depth level data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** page_contents_InternalPage depth level data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
				pLC = null;
			}
		}
		String[] nodeVisitDataCheck = {"78 (21.31%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "12 (3.28%)", "240 (65.57%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)", "9 (2.46%)", "1 (0.27%)"};
		for(int i=0,x=0;i<=23;i++) {
			if(i%2 == 1) {
				pageLoadCheck = $("text", i).text().trim();
				String[] pLC = pageLoadCheck.split("�� ");
				if(pLC[1].equals(nodeVisitDataCheck[(x)])) {
					System.out.println(" *** route_contents_moveRoute node data ((" + x + ")) check Success !! *** ");
				} else {
					System.out.println(" *** route_contents_moveRoute node data ((" + x + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
				x++;
				pLC = null;
			}
		}		
		System.out.println(" ! ----- route_contents_moveRoute End ----- ! ");
	}
	@Test(priority = 12)
	public void route_route_contents_preNextPage() {
		System.out.println(" ! ----- route_contents_preNextPage Start ----- ! ");
		$(By.linkText("����/���� ������")).waitUntil(visible, 10000);
		$(By.linkText("����/���� ������")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("����/���� ������")) {
			System.out.println(" *** route_contents_preNextPage page load Success !! *** ");
		} else {
			System.out.println(" *** route_contents_preNextPage page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#infoTitle_0").waitUntil(visible, 10000);
		String[] prePageDataCheck = {"�湮����", "12", "/", "9"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("text", i).text().trim();
			if(pageLoadCheck.equals(prePageDataCheck[i])) {
				System.out.println(" *** route_contents_preNextPage pre page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** route_contents_preNextPage pre page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] nextPageDataCheck = {"/search/label/marketing...", "9", "�湮����", "12"};
		for(int i=0;i<=3;i++) {
			pageLoadCheck = $("text", (i+7)).text().trim();
			if(pageLoadCheck.equals(nextPageDataCheck[i])) {
				System.out.println(" *** route_contents_preNextPage next page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** route_contents_preNextPage next page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}		
		String[] standardPageDataCheck = {"������ �湮��21", "��������21", "�湮�� ��������1", "�����̵���9", "�����12", "������57.14%"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("#infoTitle_" + i).text().trim();
			if(pageLoadCheck.equals(standardPageDataCheck[i])) {
				System.out.println(" *** route_contents_preNextPage standard page data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** route_contents_preNextPage standard page data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- route_contents_preNextPage End ----- ! ");
	}
	@Test(priority = 13)
	public void route_route_contents_scenario() {
		System.out.println(" ! ----- route_contents_scenario Start ----- ! ");
		$(By.linkText("�ó�����")).waitUntil(visible, 10000);
		$(By.linkText("�ó�����")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("�ó�����")) {
			System.out.println(" *** route_contents_scenario page load Success !! *** ");
		} else {
			System.out.println(" *** route_contents_scenario page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("h2").text().trim();
		if(pageLoadCheck.equals("�޼���0.0%")) {
			System.out.println(" *** route_contents_scenario page check Success !! *** ");
		} else {
			System.out.println(" *** route_contents_scenario page check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- route_contents_scenario End ----- ! ");
	}
	@Test(priority = 21)
	public void link_link_contents_eventLink() {
		System.out.println(" ! ----- link_contents_eventLink Start ----- ! ");
		$(By.linkText("��ũ")).waitUntil(visible, 10000);
		$(By.linkText("��ũ")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		pageLoadCheck = $("#top-menu-name").text().trim();
		if (pageLoadCheck.equals("��ũ")) {
			System.out.println(" *** link_contents_eventLink page load Success !! *** ");
		} else {
			System.out.println(" *** link_contents_eventLink page load Fail ... !@#$%^&*() *** ");
			close();
		}
		pageLoadCheck = $("td", 13).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_contents_eventLink table chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_eventLink table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-tracker", 1).waitUntil(visible, 10000);
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21(��)", "��Ŭ��: 0", "Ŭ����: 0"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_contents_eventLink bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_eventLink bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$("tspan", 7).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan", 7).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_contents_eventLink line chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_eventLink line data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		$("tspan", 9).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			pageLoadCheck = $("tspan", (i+8)).text().trim();
			if(pageLoadCheck.equals(nodata)) {
				System.out.println(" *** link_contents_eventLink pie chart data check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_eventLink pie chart data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- link_contents_eventLink End ----- ! ");
	}
	@Test(priority = 22)
	public void link_link_contents_share() {
		System.out.println(" ! ----- link_contents_share Start ----- ! ");
		$(By.linkText("����")).waitUntil(visible, 10000);
		$(By.linkText("����")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("����")) {
			System.out.println(" *** link_contents_share page load Success !! *** ");
		} else {
			System.out.println(" *** link_contents_share page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		pageLoadCheck = $("td", 7).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_contents_share table data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_share table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-axis", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018-12-21(��)", "����Ŭ����: 0"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_contents_share bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_share bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartPie").click();
		//$("tspan", 1).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan").text().trim();
		System.out.println("pageLoadCheck is :" + pageLoadCheck);
		//pageLoadCheck = $("tspan", 1).text().trim();
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_contents_share pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_share pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartLine").click();
		$("tspan").waitUntil(visible, 10000);
		if(pageLoadCheck.equals(nodata)) {
			System.out.println(" *** link_contents_share line chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_share line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- link_contents_share End ----- ! ");
	}
	@Test(priority = 22)
	public void link_contents_outLinkBanner() {
		System.out.println(" ! ----- link_contents_outLinkBanner Start ----- ! ");
		$(By.linkText("�ƿ���ũ���")).waitUntil(visible, 10000);
		$(By.linkText("�ƿ���ũ���")).click();
		pageLoadCheck = $(".active", 2).text().trim();
		if (pageLoadCheck.equals("�ƿ���ũ���")) {
			System.out.println(" *** link_contents_outLinkBanner page load Success !! *** ");
		} else {
			System.out.println(" *** link_contents_outLinkBanner page load Fail ... !@#$%^&*() *** ");
			close();
		}
		String[] panelDataCheck = {"�����\n0\n-", "Ŭ����\n9\n9(0.00%)", "��Ŭ��\n9\n9(0.00%)"};
		for(int i=0;i<=2;i++) {
			pageLoadCheck = $(".panel-body", i).text().trim();
			if(pageLoadCheck.equals(panelDataCheck[i])) {
				System.out.println(" *** link_contents_outLinkBanner panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_outLinkBanner panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}		
		$("td", 27).waitUntil(visible, 10000);
		$("td", 27).click();
		$("td", 34).waitUntil(visible, 10000);
		String[] tableDataCheck = {"�� �ƿ���ũ���.", "0", "9", "0%", "9", "0%"};
		for(int i=0;i<=5;i++) {
			pageLoadCheck = $("td", (i+34)).text().trim();
			if(pageLoadCheck.equals(tableDataCheck[i])) {
				System.out.println(" *** link_contents_outLinkBanner table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_outLinkBanner table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$(".highcharts-tracker", 1).hover();
		$(".highcharts-tracker", 2).hover();		
		$(".highcharts-tracker", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21(��)", "�����: 0", "Ŭ����: 9"};
		for(int i=0;i<=2;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** link_contents_outLinkBanner bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_outLinkBanner bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 3).waitUntil(visible, 10000);
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 3).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("�� ");
		String[] lineChartDataCheck = {"2018.12.21(��)", "�ƿ���ũ���.: 0"};
		for(int i=0;i<=1;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** link_contents_outLinkBanner line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** link_contents_outLinkBanner line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		$("#btnChartPie").click();		
		$("tspan", 11).waitUntil(visible, 10000);
		pageLoadCheck = $("tspan", 11).text().trim();
		if(pageLoadCheck.equals(nodata) ) {
			System.out.println(" *** link_contents_outLinkBanner left pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_outLinkBanner left pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-tracker", 7).hover();
		$(".highcharts-tracker", 8).hover();
		$(".highcharts-tracker", 7).hover();
		pageLoadCheck = $(".highcharts-tooltip", 3).text().trim();
		if(pageLoadCheck.equals("�ƿ���ũ���.Series 1: 100.0%")) {
			System.out.println(" *** link_contents_outLinkBanner right pie chart data check Success !! *** ");
		} else {
			System.out.println(" *** link_contents_outLinkBanner right pie chart data check Fail ... !@#$%^&*() *** ");
			close();
		}		
		System.out.println(" ! ----- link_contents_outLinkBanner End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}