package aceCounterPlus;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

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

/*import com.codeborne.selenide.testng.ScreenShooter;*/

public class temporarily_2 {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, pageLoadCheck, nodata;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://www.acecounter.com/www2/main.amz";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
		//hubUrl = "http://10.77.129.79:5555/wd/hub";
		pw = "qordlf";
		A = "!@34";
		nodata = "��ȸ�� �����Ͱ� �����ϴ�.";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
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
		$(By.name("id")).waitUntil(visible, 15000);
		$(By.name("id")).setValue("apzz09288");
		$(By.name("pw")).setValue(pw + A);
		$(".login_btn").click();
		$(".id_area").waitUntil(visible, 10000);
		if ($(".id_area").text().trim().equals("�α׾ƿ�")) {
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
	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}