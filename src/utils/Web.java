package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class Web {
	public static WebElement element;
	public static String WebElementID;
	

	public static void LoadURL(String URL) {
		if (Browser.BrowserDriver.toString() != null) {
			Timers.ActionStart();
			Debug.Log("Navigating to URL: " + URL);
			Browser.BrowserDriver.get(URL);
			//WaitForPageLoad();
			Browser.BrowserDriver.manage().window().maximize();
			Timers.ActionEnd();
			Debug.Log("Navigated to URL in " + Timers.ActionTotal() + " sec(s).");
			Debug.Screenshot("BaseURLLoad");
			
		} else {
			Debug.Log("Could not load URL! Browser cannot be accessed.");
		}
	}
	
	public static void MovePage(int pages) {
		if (Browser.BrowserDriver.toString() != null) {
			String movement = "";
			if (pages < 0) {
				movement = "back";
				pages = pages * -1;
			} else {
				movement = "forward";
			}
			int ctr;
			for (ctr = 0; ctr < pages; ctr++) {
				if (movement.contentEquals("back")) {
					Browser.BrowserDriver.navigate().back();
				} else {
					Browser.BrowserDriver.navigate().forward();
				}
			}
			Debug.Log("Web page moved " + movement + " " + ctr + " times.");
		}
	}
	
/*	public static void WaitForPageLoad() {
	    WebDriverWait wait = new WebDriverWait(Browser.BrowserDriver, Global.secsMaxTimeOut);

	    Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

	        @Override
	        public boolean apply(WebDriver input) {
	            return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
	        }

	    };
	    wait.until(pageLoaded);
	    Browser.BrowserDriver.manage().timeouts().implicitlyWait(Global.secsMaxTimeOut, TimeUnit.SECONDS);
	}
*/	
	
	public static boolean WaitUntilVisible(WebElement e){
		if (e!=null) {
			for (int ctr = 0; ctr < Global.secsMaxTimeOut; ctr++) {
				try {
					if(e.getLocation().getX() > 0 && e.getLocation().getY() > 0) {
						return true;	
					};
					Thread.sleep(1000);
				} catch (ElementNotVisibleException|InterruptedException enve) {
					//Debug.ExceptionError(enve);
				} catch (StaleElementReferenceException sere) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static WebElement FindElement(String identifier) throws InterruptedException {
		WebElementID = "";
		element = null;
		WebDriver page = Browser.BrowserDriver;
			if (! identifier.equals("")) {
				WebElement elements;
				int ElementFound = 0;
				for (int ctrRetries = 0; ctrRetries < Global.ctrMaxStepRetry; ctrRetries++) {
					Debug.Log("Finding " + identifier + "... Tries: " + (ctrRetries+1));
					try{
						Debug.Trace("Using CSS Selector");
						elements = page.findElement(By.cssSelector(identifier));
						if (elements!=null) {
							element = elements;
							Debug.Trace("Found using CSS Selector");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Debug.Trace("Using ID");
						elements = page.findElement(By.id(identifier));
						if (elements!=null) {
							element = elements;
							Debug.Trace("Found using ID");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Debug.Trace("Using Text");
						elements = page.findElement(By.partialLinkText(identifier));
						if (elements!=null) {
							element = elements;
							Debug.Trace("Found using Text");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Debug.Trace("Using Name");
						elements = page.findElement(By.name(identifier));
						if (elements!=null) {
							element = elements;
							Debug.Trace("Found using Name");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Debug.Trace("Using xPath");
						elements = page.findElement(By.xpath(identifier));
						if (elements!=null) {
							element = elements;
							Debug.Trace("Found using xPath");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
				}
				if (ElementFound > 0) {
					WebElementID = identifier;
					Debug.Log("Element set to: [" + element.getTagName() + "]" + WebElementID);
				} else {
					Debug.Log("Unable to identify: " + identifier);
					element = null;
					WebElementID = "";
					return null;
				}				
			}
		return element;
	}

	public static int Perform(String action, String argument) {
		int status = 0;
		WebElement e = element;
		String[] argSplit = argument.split(":");
		if (argSplit.length > 1) {
			if (Maps.GetTestValue(argSplit[1])!=null && argSplit[0].equalsIgnoreCase("get")) {
				argument = Maps.GetTestValue(argSplit[1]);
				Global.ObjectParameter = argument;
			}
		}
		if (e != null) {

			//waitForPageLoad();
			if(WaitUntilVisible(e)) {
				switch (action.toUpperCase()) {
				case "CLICK":
					for (int ctrTry=0; ctrTry<Global.ctrMaxStepRetry;ctrTry++) {
						try {
							e.click();
							status = 0;
							break;
						} catch (WebDriverException wde) {
							try {
								Actions actions = new Actions(Browser.BrowserDriver);
								actions.moveToElement(e).click().perform();
								status = 0;
								break;
							} catch (Exception ee) {
								
							}
							
							status = 2;
							Debug.ExceptionError(wde);
						}
					}
					break;
				case "HOVER":
					FocusOnObject();
					status = 0;
					break;
				case "SELECT":
					try {
						
						Select select = new Select(e);
						if(argument != "") {
							select.selectByVisibleText(argument);
							status = 0;
						} else {
							select.getFirstSelectedOption();
							status = 0;
						}
					} catch (NoSuchElementException nsee) {
						Debug.Log("Select value: " + argument + " not found in list.");
						status = 5;
					}
					break;
				case "TOGGLE":
					try {
						if (argument.equalsIgnoreCase("true")) {
							if (! e.isSelected()) {
								e.sendKeys(" ");
							}
						} else {
							if (e.isSelected()) {
								e.sendKeys(" ");
							}
						}
						status = 0;
					} catch (Exception ee) {
						Debug.ExceptionError(ee);
						status = 2;
					}
					
					
					break;
				case "TYPE":
					//element.sendKeys(argument);
					for (int typeCtr=0; typeCtr<Global.ctrMaxStepRetry;typeCtr++) {
						try {
							e.clear();
							} catch (InvalidElementStateException iese) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException ie) {
									
								}
							}
					}
					if(argument != "") {
						try {
							e.sendKeys(argument);
							status = 0;
						} catch (InvalidElementStateException iese) {
							Debug.ExceptionError(iese);
							status = 2;
						}
/*						if (Environment.ActionObject.toUpperCase().contains("PASSWORD") && Run.Password != "") {
							element.clear();
							element.sendKeys(Run.Password);
							status = 0;
						} else {
							element.clear();
							element.sendKeys(argument);
							status = 0;
						}*/
					}
					
					break;
				case "STORE":
					if (! argument.equals("")) {
						Maps.StoreTestValue(argument, e.getText()); 
						status = 0;
						
					} else {
						status = 2;
						Debug.Log("No test value name specified!");
					}
					
					break;
				case "VALIDATE":
					if (! argument.equals("")) {
						if (argument.toUpperCase().contains("EXISTS")) {
							if (Web.ValidateObjectExists()) {
								status = 0;
							} else {
								status = 2;
							}
						} else if (argument.toUpperCase().contains("IS TICKED")) {
							if (Web.ValidateObjectEnabled()) {
								status = 0;
							} else {
								status = 2;
							}
						} else if (argument.toUpperCase().contains("IS NOT TICKED")) {
							if (! Web.ValidateObjectEnabled()) {
								status = 0;
							} else {
								status = 2;
							}
						} else {
							if (Web.ValidateObjectText(argument)) {
								status = 0;
							} else {
								status = 2;
							}
						}
					} 
					break;
				}
				Debug.Screenshot(e);
			};

		} else {
			status = 2;
		}
		Global.ctrActionStatus = status;
		return status;
	}
	
	public static void PerformOnObject (String action, String identifier, String argument) {
		try {
			if (argument==null) {
				argument = "";
			}
			//Debug.Trace("Perform on object: " + action + "," + identifier + "," + argument );
			if (Global.ObjectAction != "") {
				if(action.toUpperCase().contains("VALIDATE") && argument.length()==0) {
					Global.ctrActionStatus = 3; //skip
				} else if (argument.equalsIgnoreCase("SKIP")){
					Global.ctrActionStatus = 3; //skip
				} else if (argument.equalsIgnoreCase("DOES NOT EXIST")) {
					if (FindElement(identifier) != null) {
						Global.ctrActionStatus = 2;
						TestCase.ctrFail++;
					} else {
						Global.ctrActionStatus = 0;
						TestCase.ctrPass++;
					}
				
				}	else {
					if (FindElement(identifier) != null) {
						
						if (Perform(action, argument)==0) {
							Global.ctrActionStatus = 0; //pass
							TestCase.ctrPass++;

						} else {
							Global.ctrActionStatus = 2; //fail
							TestCase.ctrFail++;
						}
					} else {
						Global.ctrActionStatus = 4; //object not found
					}
				}
				
			} else {
				Global.ctrActionStatus = 3; //skip because no object found
			}
			
		} catch (InterruptedException ie) {
			Debug.ExceptionError(ie);
		}
	}

	public static void PerformOnObject (String action, String identifier) {
		try {
			FindElement(identifier);
			Perform(action, "");
		} catch (InterruptedException ie) {
			Debug.ExceptionError(ie);
		}
	}	
	
	public static int FocusOnObject() {
		WebElement e = element;
		Actions builder = new Actions(Browser.BrowserDriver);
		builder.moveToElement(e).build().perform();
		try {
			e.sendKeys("");
		} catch (WebDriverException wde) {
			return 2;
		}
		return 0;
	}
	
	public static boolean ValidateObjectAccessible() {
		if (ValidateObjectExists() && ValidateObjectEnabled()) {
			return true;
		}
		return false;
	}
	
	public static boolean ValidateObjectExists() {
		if (element.isDisplayed()) {
			return true;
		}
		return false;
	}
	public static boolean ValidateObjectEnabled() {
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}
	
	public static boolean ValidateObjectText(String text) {
		if (element.getText().equalsIgnoreCase(text)) {
			return true;
		}
		if (element.getText().toUpperCase().indexOf(text.toUpperCase())>0  ) {
			return true;
		}
		Debug.Trace("Text validation failed: " + text + " is not in " + element.getText());
		return false;
	}
	
	
	public static boolean ValidateObjectClass(String className) {
		if (element.getClass().toString().equalsIgnoreCase(className)) {
			return true;
		}
		return false;
	}
}
