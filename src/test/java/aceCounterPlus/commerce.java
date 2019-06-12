package aceCounterPlus; //Ŀ�ӽ� ���� > (��ǰ���ݴ�, ��ȭ����)

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

public class commerce {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, nodata;

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
		$(".btn_logout").waitUntil(visible, 10000);
		if ($(".btn_logout").text().trim().equals("�α׾ƿ�")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".go_stat").click();
		$("#top-menu-name").waitUntil(visible, 10000);
		if ($("#top-menu-name").text().trim().equals("Live ��ú���")) {
			System.out.println(" *** stats convert login Success !! *** ");
		} else {
			System.out.println(" *** stats convert login Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- login End ----- ! ");
	}
	@Test(priority = 1)
	public void product_popularity() {
		System.out.println(" ! ----- product_popularity Start ----- ! ");
		$("#commerce").click();
		$(By.linkText("��ǰ")).waitUntil(visible, 10000);
		$(By.linkText("��ǰ")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		if ($("#top-menu-name").text().trim().equals("��ǰ")) {
			System.out.println(" *** product_popularity page load Success !! *** ");
		} else {
			System.out.println(" *** product_popularity page load Fail ... !@#$%^&*() *** ");
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
			for(int x=0;x<=100;x++) { //�޷��� �� Ȯ�� �� 2018.12�� �ɶ����� << Ŭ��
				if($(".month", i).text().trim().equals("2018.12")) {
					$("td[data-title=r3c5]", i).click(); //21�� ����
					break;
				} else {
					System.out.println("no"+ (i+1) + ". calender month is  : " + $(".month", i).text().trim() + " // need nextBtn(" + x + ") click");
					$(".prev", i).click();
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
		$("td", 10).waitUntil(visible, 10000);
		for(int i=0;i<=1;i++) {
			if($("td", i).text().trim().equals(nodata.substring(nodata.length()-10, nodata.length()))) {
				System.out.println(" *** product_popularity ranking table data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity ranking table data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 0) {
				if($("tspan", i).text().trim().equals(nodata)) {
					System.out.println(" *** product_popularity pie chart data check Success !! *** ");
				} else {
					System.out.println(" *** product_popularity pie chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		$("#btnChartLine").click();
		$("tspan", 2).waitUntil(hidden, 10000);
		if($("tspan", 0).text().trim().equals(nodata)) {
			System.out.println(" *** product_popularity line chart data check Success !! *** ");
		} else {
			System.out.println(" *** product_popularity line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=1;i++) {
			if($("td", i+9).text().trim().equals(nodata)) {
				System.out.println(" *** product_popularity product table data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity product table data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=1;i++) {
			if($("tspan", i+5).text().trim().equals(nodata)) {
				System.out.println(" *** product_popularity purchase chart data check Success !! *** ");
			} else {
				System.out.println(" *** product_popularity purchase chart data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		System.out.println(" ! ----- product_popularity End ----- ! ");
	}
	@Test(priority = 2)
	public void product_basket() {
		System.out.println(" ! ----- product_basket Start ----- ! ");
		$(By.linkText("��ٱ���")).waitUntil(visible, 10000);
		$(By.linkText("��ٱ���")).click();
		if ($(".active", 2).text().trim().equals("��ٱ���")) {
			System.out.println(" *** product_basket page load Success !! *** ");
		} else {
			System.out.println(" *** product_basket page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		String[] panelDatacheck = {"0", "0", "0", "0%", "0%"};
		for(int i=0;i<=4;i++) {
			if($(".summary-data", i).text().trim().equals(panelDatacheck[i])) {
				System.out.println(" *** product_basket panel data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** product_basket panel data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		
		if($("td", 7).text().trim().equals(nodata)) {
			System.out.println(" *** product_basket table data check Success !! *** ");
		} else {
			System.out.println(" *** product_basket table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 0) {
				if($("tspan", i).text().trim().equals(nodata)) {
					System.out.println(" *** product_basket bar chart data check Success !! *** ");
				} else {
					System.out.println(" *** product_basket bar chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- product_basket End ----- ! ");
	}
	@Test(priority = 3)
	public void product_postScript() {
		System.out.println(" ! ----- product_postScript Start ----- ! ");
		$(By.linkText("�ı�")).waitUntil(visible, 10000);
		$(By.linkText("�ı�")).click();
		if ($(".active", 2).text().trim().equals("�ı�")) {
			System.out.println(" *** product_postScript page load Success !! *** ");
		} else {
			System.out.println(" *** product_postScript page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 7).waitUntil(visible, 10000);
		if($("td", 7).text().trim().equals(nodata)) {
			System.out.println(" *** product_postScript table data check Success !! *** ");
		} else {
			System.out.println(" *** product_postScript table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("path", 1).hover();
		$("path", 2).hover();
		$("path", 3).hover();
		$("path", 2).hover();
		String[] reviewChartDataCheck = {"2018.12.21(��)�Ҹ���:", "0%����:", "0%����:", "0%����:", "0%�ſ츸��:", "0%"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split(" ");
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(reviewChartDataCheck[i])) {
				System.out.println(" *** product_postScript review chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** product_postScript review chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		for(int i=0;i<=3;i++) {
			if(i%2 == 0) {
				if($("tspan", i+11).text().trim().equals(nodata)) {
					System.out.println(" *** product_postScript TOP5 chart data check Success !! *** ");
				} else {
					System.out.println($("tspan", i+11).text().trim());
					System.out.println(" *** product_postScript TOP5 chart data check Fail ... !@#$%^&*() *** ");
					close();
				}
			}
		}
		System.out.println(" ! ----- product_postScript End ----- ! ");
	}
	@Test(priority = 11)
	public void correlationProduct() {
		System.out.println(" ! ----- correlationProduct Start ----- ! ");
		$(By.linkText("������ǰ")).waitUntil(visible, 10000);
		$(By.linkText("������ǰ")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		if ($("#top-menu-name").text().trim().equals("������ǰ")) {
			System.out.println(" *** correlationProduct page load Success !! *** ");
		} else {
			System.out.println(" *** correlationProduct page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 6).waitUntil(visible, 10000);
		String[] tableDataCheck = {"��ǰ��", "��������", "��ٱ���", "���ż���", "���ŰǼ�", "��ǰ�ݾ�", "�α⿬��", "�α⼺��"};
		for(int i=0;i<=7;i++) {
			if(i==0) {
				if($(".th-inner", i).text().trim().equals(tableDataCheck[i])) {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}
			} else {
				if($(".th-inner", i).text().trim().substring(0, 4).equals(tableDataCheck[i])) {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** correlationProduct table head data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();	
				}
			}			
		}
		if($("td", 6).text().trim().equals(nodata)) {
			System.out.println(" *** correlationProduct table data check Success !! *** ");
		} else {
			System.out.println(" *** correlationProduct table data check Fail ... !@#$%^&*() *** ");
			close();	
		}
		System.out.println(" ! ----- correlationProduct End ----- ! ");
	}
	@Test(priority = 21)
	public void potentialGuest_all() {
		System.out.println(" ! ----- potentialGuest_all Start ----- ! ");
		$(By.linkText("�����")).waitUntil(visible, 10000);
		$(By.linkText("�����")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		if ($("#top-menu-name").text().trim().equals("�����")) {
			System.out.println(" *** potentialGuest_all page load Success !! *** ");
		} else {
			System.out.println(" *** potentialGuest_all page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 27).waitUntil(visible, 10000);
		String[] tableDataCheck = {"2018.12.21(��)", "0", "0", "0%", "0", "0%", "0", "0%", "0", "0%", "0"};
		for(int i=0;i<=10;i++) {
			if($("td", i+17).text().trim().equals(tableDataCheck[i])) {
				System.out.println(" *** potentialGuest_all table data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println($("td", i+17).text().trim());
				System.out.println(" *** potentialGuest_all table data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();	
			}
		}
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 5).hover();
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 5).hover();
		String[] barChartDataCheck = {"2018.12.21(��)", "Ž��: 0", "��: 0", "���: 0", "����: 0", "�湮��: 0"};
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=5;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** potentialGuest_all bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest_all bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();	
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		for(int i=0;i<=20;i++) {
			if($("text", 1).text().trim().equals("Ž��")) {
				System.out.println(" *** potentialGuest_all line chart data loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** potentialGuest_all line chart loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** potentialGuest_all line chart data loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 2).hover();
		String[] lineChartDataCheck = {"2018.12.21(��)", "Ž��: 0", "��: 0", "���: 0", "����: 0"};
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=4;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** potentialGuest_all line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest_all line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();	
			}
		}
		System.out.println(" ! ----- potentialGuest_all End ----- ! ");
	}
	@Test(priority = 22)
	public void potentialGuest_detail() {
		System.out.println(" ! ----- potentialGuest_detail Start ----- ! ");
		$(By.linkText("��")).waitUntil(visible, 10000);
		$(By.linkText("��")).click();
		if ($(".active", 2).text().trim().equals("��")) {
			System.out.println(" *** potentialGuest_detail page load Success !! *** ");
		} else {
			System.out.println(" *** potentialGuest_detail page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 6).waitUntil(visible, 10000);
		String[] tableDataCheck = {"����", "�湮�Ͻ�", "IP", "������ó", "����������", "ȸ������", "�� ��ǰ��"};
		for(int i=0;i<=6;i++) {;
			if($(".th-inner", i).text().trim().equals(tableDataCheck[i])) {
				System.out.println(" *** potentialGuest_detail table head data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** potentialGuest_detail table head data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		if($("td", 6).text().trim().equals(nodata)) {
			System.out.println(" *** potentialGuest_detail table data check Success !! *** ");
		} else {
			System.out.println(" *** potentialGuest_detail table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- potentialGuest_detail End ----- ! ");
	}
	@Test(priority = 31)
	public void mainProduct() {
		System.out.println(" ! ----- mainProduct Start ----- ! ");
		$(By.linkText("������ǰ")).waitUntil(visible, 10000);
		$(By.linkText("������ǰ")).click();
		$("#top-menu-name").waitUntil(visible, 15000);
		if ($("#top-menu-name").text().trim().equals("������ǰ")) {
			System.out.println(" *** mainProduct page load Success !! *** ");
		} else {
			System.out.println(" *** mainProduct page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 10).waitUntil(visible, 10000);
		if($("td", 10).text().trim().equals(nodata)) {
			System.out.println(" *** mainProduct table data check Success !! *** ");
		} else {
			System.out.println(" *** mainProduct table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		for(int i=0;i<=1;i++) {
			if($("td", i).text().trim().equals(nodata.substring(nodata.length()-10, nodata.length()))) {
				System.out.println(" *** mainProduct ranking data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** mainProduct ranking data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();
			}
		} 
		if($("tspan", 0).text().trim().equals(nodata)) {
			System.out.println(" *** mainProduct bar chart data check Success !! *** ");
		} else {
			System.out.println(" *** mainProduct bar chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$("#btnChartPie").click();
		/*for(int i=1;i<=6;i++) {
			System.out.println(i + "�� " + $("tspan", i).text().trim() + " �Դϴ�.");
		}*/
		for(int i=1;i<=3;i++) {
			if(i==1 || i==3) {
				if($("tspan", i).text().trim().equals(nodata)) {
					System.out.println(" *** mainProduct pie chart data ((" + i + ")) check Success !! *** ");
				} else {
					System.out.println(" *** mainProduct pie chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
					close();
				}			
			}
		}
		$("#btnChartLine").click();
		if($("tspan", 1).text().trim().equals(nodata)) {
			System.out.println(" *** mainProduct line chart data check Success !! *** ");
		} else {
			System.out.println(" *** mainProduct line chart data check Fail ... !@#$%^&*() *** ");
			close();
		}
		System.out.println(" ! ----- mainProduct End ----- ! ");
	}
	@Test(priority = 41)
	public void buyStatus() {
		System.out.println(" ! ----- buyStatus Start ----- ! ");
		$(By.linkText("������Ȳ")).waitUntil(visible, 10000);
		$(By.linkText("������Ȳ")).click();
		$(".notokr-bold").waitUntil(visible, 15000);
		if ($(".notokr-bold").text().trim().equals("������Ȳ")) {
			System.out.println(" *** buyStatus page load Success !! *** ");
		} else {
			System.out.println(" *** buyStatus page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 25).waitUntil(visible, 10000);
		if($("td", 25).text().trim().equals("2018.12.21(��)")) {
			System.out.println(" *** buyStatus table data check Success !! *** ");
		} else {
			System.out.println(" *** buyStatus table data check Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 3).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 3).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text().trim();
		String[] pLC = pageLoadCheck.split("�� ");
		String[] barChartDataCheck = {"2018.12.21(��)", "�űԱ���: 0", "�籸��: 0", "�հ�: 0"};
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(barChartDataCheck[i])) {
				System.out.println(" *** buyStatus bar chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** buyStatus bar chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();	
			}
		}
		pLC = null;
		$("#btnChartLine").click();
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 4).hover();
		$(".highcharts-tracker", 0).hover();
		$(".highcharts-tracker", 2).hover();
		$(".highcharts-tracker", 4).hover();
		String[] lineChartDataCheck = {"2018.12.21(��)", "�űԱ���: 0", "�籸��: 0", "�հ�: 0"};
		pageLoadCheck = $(".highcharts-tooltip", 1).text().trim();
		pLC = pageLoadCheck.split("�� ");
		for(int i=0;i<=3;i++) {
			if(pLC[i].equals(lineChartDataCheck[i])) {
				System.out.println(" *** buyStatus line chart data ((" + i + ")) check Success !! *** ");
			} else {
				System.out.println(" *** buyStatus line chart data ((" + i + ")) check Fail ... !@#$%^&*() *** ");
				close();	
			}
		}
		pLC = null;
		System.out.println(" ! ----- buyStatus End ----- ! ");
	}
	@Test(priority = 42)
	public void buyStatusPeriod() {
		System.out.println(" ! ----- buyStatusPeriod Start ----- ! ");
		$(By.linkText("�ֱ�")).waitUntil(visible, 10000);
		$(By.linkText("�ֱ�")).click();
		if ($(".active", 2).text().trim().equals("�ֱ�")) {
			System.out.println(" *** buyStatusPeriod page load Success !! *** ");
		} else {
			System.out.println(" *** buyStatusPeriod page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$("td", 42).waitUntil(visible, 10000);
		String[] purchaseElementTime = {"10�� �̳�", "30�� �̳�", "1�ð� �̳�", "2�ð� �̳�", "2�ð� �ʰ�"};
		for(int i=0,x=14;i<=4;i++) {
			if($("td", x).text().trim().equals(purchaseElementTime[i])) {
				System.out.println(" *** purchaseElementTime data check Success !! *** ");
				x = x+7;
			} else {
				System.out.println(" *** purchaseElementTime data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] firstPurchaseTime = {"����", "1��", "2~4��", "5~14��", "15~29��", "1����~3����", "3���� ����"};
		for(int i=0, x=60;i<=6;i++) {
			if($("td", x).text().trim().equals(firstPurchaseTime[i])) {
				System.out.println(" *** firstPurchaseTime data check Success !! *** ");
				x = x+5;
			} else {
				System.out.println(" *** firstPurchaseTime data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}
		String[] rePurchaseTerm = {"����~1��", "2~4��", "5~14��", "15~29��", "1����~3����", "3���� ����"};
		for(int i=0, x=106;i<=5;i++) {
			if($("td", x).text().trim().equals(rePurchaseTerm[i])) {
				System.out.println(" *** rePurchaseTerm data check Success !! *** ");
				x = x+5;
			} else {
				System.out.println(" *** rePurchaseTerm data check Fail ... !@#$%^&*() *** ");
				close();
			}
		}		
		System.out.println(" ! ----- buyStatusPeriod End ----- ! ");
	}
	@Test(priority = 43)
	public void buyStatusOrder() {
		System.out.println(" ! ----- buyStatusOrder Start ----- ! ");
		$(By.linkText("�ֹ�")).waitUntil(visible, 10000);
		$(By.linkText("�ֹ�")).click();
		if ($(".active", 2).text().trim().equals("�ֹ�")) {
			System.out.println(" *** buyStatusOrder page load Success !! *** ");
		} else {
			System.out.println(" *** buyStatusOrder page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".no-records-found > td").waitUntil(visible, 10000);
		pageLoadCheck = "2018.12.21(��)";
		sleep(2000);
		System.out.println($(".highcharts-xaxis-labels > text").text());
		if(pageLoadCheck.equals($(".highcharts-xaxis-labels > text").text())) {
			System.out.println(" *** buyStatusOrder chart data check Success !! *** ");
		} else {
			System.out.println(" *** buyStatusOrder chart data check Fail ... !@#$%^&*() *** ");
			close();	
		}
		String[] tableDataCheck = {"�ֹ���ȣ", "������ó", "���ż���"};
		for(int i=3;i<=5;i++) {
			pageLoadCheck = $(".th-inner", i).text().trim();
			if(i == 7 || i == 9) {
				if(pageLoadCheck.substring(0, 3).equals(tableDataCheck[i-3])) {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Success !! *** ");
				} else {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Fail ... !@#$%^&*() *** ");
					close();	
				}
			} else if (i == 8) {
				if(pageLoadCheck.substring(0, 12).equals(tableDataCheck[i-3])) {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Success !! *** ");
				} else {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Fail ... !@#$%^&*() *** ");
					close();	
				}
			} else {
				if(pageLoadCheck.substring(0, 4).equals(tableDataCheck[i-3])) {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Success !! *** ");
				} else {
					System.out.println(" *** buyStatusOrder table data ((" + (i-3) + ")) check Fail ... !@#$%^&*() *** ");
					close();	
				}
			}
		}
		System.out.println(" ! ----- buyStatusOrder End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}