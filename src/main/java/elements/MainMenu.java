package elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainMenu {
    //Добавляем основные кнопки, используемые тестами. Доступ к ним доступен из шапки сайта, поэтому выделила их в отдельный элемент.
    @FindBy(xpath = "/html/body/div[3]/nav/div/div[2]/button")
    private WebElement cartButton;
    @FindBy(xpath = "//span[contains(text(),'Пицца')]")
    private WebElement pizzaButton;
    @FindBy(css = ".jjxTjA .header__about-slogan-text_locality")//Я знаю, что стоило подобрать локатор получше, но усталость берёт своё
    private WebElement locationIndex;
    @FindBy(xpath = "/html/body/div[3]/nav/div/div[2]/button/div[2]")
    private WebElement cartAmount;

    public MainMenu(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    //Методы работы с общими кнопками прописаны здесь же
    public String getLocationIndex(){
        return locationIndex.getText();
    }//Проверяем регион
    public void clickCartButton(){
    cartButton.click();}
    public void clickPizzaButton(){
        pizzaButton.click();
    }
    public String checkCartAmount() {;
        return cartAmount.getText();
    }
}
