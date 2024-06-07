package demo.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities {
    
    public static void goToUrlAndWait(WebDriver driver, String url){
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> ((JavascriptExecutor) webDriver).executescript
        ("return document.readyState"));
    }

    public static void sendKeysWrapper(WebDriver driver, By locator, String textToSend){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement textInput = driver.findElement(locator);
            textInput.clear();
            textInput.sendKeys(textToSend);
            textInput.sendKeys(Keys.ENTER);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void clickTillUnclickable(WebDriver driver, By locator, Integer maxItrerations) throws InterruptedException{
        Integer numIter = 0;
        while(numIter < maxItrerations){
            try{
                findElementAndClick(driver,locator);
            }catch(TimeoutException e){
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        Thread.sleep(1000);
    }

    public static void findElementAndClickWE(WebDriver driver, WebElement we, By locator){
        WebElement element = we.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",element);
        element.click();

    }

    public static String findElementAndPrint(WebDriver driver, By locator, int elemntNo){
        WebElement we = driver.findElements(locator).get(elemntNo);
        String txt = we.getText();
        return txt;
    }

    public static long convertToNumericValue(String value){
        value = value.trim().toUpperCase();
        char lastChar = value.charAt(value.length()-1);
        int multiplier = 1;
        switch(lastChar){
            case 'K':
                multiplier = 1000;
                break;
            case 'M':
                multiplier = 1000000;
                break;
            case 'B':
                multiplier = 1000000000;
                break;
            default:
                if(Character.isDigit(lastChar)){
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid format: "+ value);
        }
        String numericPart = value.substring(0,value.length()-1);
        double number = Double.parseDouble(numericPart);
    }
}
