package utils;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ViewportPastingDecorator;
import ru.yandex.qatools.ashot.shooting.cutter.CutStrategy;
import ru.yandex.qatools.ashot.shooting.cutter.VariableCutStrategy;

import org.apache.commons.io.FileUtils;


public class Debug {
	public static int Verbosity = 1; 
	
	//Log to console and Logs global variable
	public static void Log(String msg) {
		String msgLog = Scribe.DateTimeGetNow() + ": " + msg;
		System.out.println(msgLog);
		Collections.writeLog(msgLog);
	}

	//Log to console if Verbosity > 1
	public static void Trace(String msg) {
		if (Verbosity > 1) {
			System.out.println("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "-" + Thread.currentThread().getStackTrace()[2].getMethodName() + "] "  + Scribe.TimeGetNow() + ": " + msg);
		}
	}
	
	//Log exception error and the class name
	public static void ExceptionError(Exception except) {
		Log("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "-" + Thread.currentThread().getStackTrace()[2].getMethodName() + "(" + except.getClass() + ")] "  + except.getMessage());
	}
	
	public static void Screenshot(String object) {
		if (Verbosity > 2) {
			//File screenshot = ((TakesScreenshot)Browser.BrowserDriver).getScreenshotAs(OutputType.FILE);
			Files.SetFilesScreenshot();
			//Files.FileScreenshot = Files.DirScreenshots + "\\" + object + Scribe.DateTimeGetStamp() + ".png";
			Debug.Trace(Files.FileScreenshot);
			File output = new File(Files.FileScreenshot);
			
			try {
				Screenshot sshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(Browser.BrowserDriver);
				ImageIO.write(sshot.getImage(), "PNG", output);
				Debug.Trace("Writing screenshot to: " + output);
					/*.shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(Browser.BrowserDriver);*/
			} catch (Exception e) {
				Debug.ExceptionError(e);
			}
			
			/*try {
				FileUtils.copyFile(screenshot, output);
				Debug.Trace("Writing screenshot to: " + output);
				
			} catch (IOException|StaleElementReferenceException|RasterFormatException e) {
				Debug.ExceptionError(e);
			}			*/
		} else {
			Debug.Trace("Screenshot saving is disabled.");
		}

	}
	
	public static void Screenshot(WebElement element) {
		if (Verbosity > 2) {
			Web.FocusOnObject();
			Files.SetFilesScreenshot();
			Debug.Trace(Files.FileScreenshot);
			File output = new File(Files.FileScreenshot);
			
			try {
				Screenshot sshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(Browser.BrowserDriver, element);
				ImageIO.write(sshot.getImage(), "PNG", output);
				Debug.Trace("Writing screenshot to: " + output);
					/*.shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(Browser.BrowserDriver);*/
			} catch (Exception e) {
				Debug.ExceptionError(e);
			}				
/*				File screenshot = ((TakesScreenshot)Browser.BrowserDriver).getScreenshotAs(OutputType.FILE);
				BufferedImage img = ImageIO.read(screenshot);
				Point objPointer = element.getLocation();
				
				int objWidth = element.getSize().getWidth();
				int objHeight = element.getSize().getHeight();
				
				int objX = objPointer.getX();
				int objY = objPointer.getY();
				
				BufferedImage tmp = img.getSubimage(objX, objY, objWidth, objHeight);
				
				ImageIO.write(tmp, "png", screenshot);
//				Files.FileScreenshot = Files.DirScreenshots + "\\" + object + Scribe.DateTimeGetStamp() + ".png";
				File output = new File(Files.FileScreenshot);
				FileUtils.copyFile(screenshot, output);
				Debug.Trace("Writing screenshot to: " + output);
			} catch (IOException|StaleElementReferenceException|RasterFormatException e) {
				Debug.ExceptionError(e);
			}	*/		
		} else {
			Debug.Trace("Screenshot saving is disabled.");
		}
		

		
	}
	
	//Dump 
	public static void Dump(String[] container) {
		for (String line : container) {
			System.out.println(line);
		}
	}
	

	
}
