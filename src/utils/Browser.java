package utils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {
	public static WebDriver BrowserDriver;
	
	public static void LoadBrowser(String browsername) {
		if (browsername.equalsIgnoreCase("FF") || browsername.equalsIgnoreCase("FIREFOX")) {
			if (SetBrowserFF()) {
				SetProxyFF();
			}  else {
				Cleanup.Terminate();
			}
		} else if (browsername.equalsIgnoreCase("CHROME")) {
			if (SetBrowserChrome()) {
				SetProxyChrome();
			}  else {
				Cleanup.Terminate();
			}
		}
	}
	
	private static boolean SetBrowserFF() {
		String LibGeckoDriver = System.getProperty("user.dir")+"\\libs\\geckodriver.exe";
		if (Files.CheckIfExists(LibGeckoDriver)) {
			System.setProperty("webdriver.gecko.driver", LibGeckoDriver);
			return true;
		} else {
			Debug.Log("Could not set the Firefox driver properly. GeckoDriver is missing!");
		}
		return false;
		
		
	}

	private static boolean SetBrowserChrome() {
		String LibChromeDriver = System.getProperty("user.dir")+"\\libs\\chromedriver.exe";
		if (Files.CheckIfExists(LibChromeDriver)) {
			System.setProperty("webdriver.chrome.driver", LibChromeDriver);
			return true;
		} else {
			Debug.Log("Could not set the Chrome driver properly. ChromeDriver is missing!");
		}
		return false;
		
		
	}
	
	
	private static void SetProxyFF() {
		FirefoxProfile prof = new FirefoxProfile();
		prof.setPreference("network.proxy.type", 4); //Set proxy to auto-detect
		BrowserDriver = new FirefoxDriver(prof);
		Debug.Log(prof.toString());
		
	}
	
	private static void SetProxyChrome() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		Proxy proxy = new Proxy();
		proxy.setAutodetect(true);
		capabilities.setCapability("proxy", proxy);
		BrowserDriver = new ChromeDriver(capabilities);
		Debug.Log(capabilities.toString());
	}
	
	public static void Close() {
		try {
			BrowserDriver.quit();
		} catch (Exception e) {
			Debug.ExceptionError(e);
		}
	}

}
