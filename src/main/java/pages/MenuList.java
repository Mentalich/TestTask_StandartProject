package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

//Страница с позициями меню
public class MenuList extends MainPage{
    private List<WebElement> pizzasList = driver.findElements(By.xpath("//*[@id=\"pizzas\"]/article"));//Список позиций таблицы "Пицца"

    public int getNumberOfPizzaPositions() {
        return pizzasList.size();
    }

    public int pickRandom(){
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(2, pizzasList.size());//Чтобы тесты не ломались об кастомную пиццу (а это было часто), исключаем её из выборки
    }

    public String clickOnRandom(){
        WebElement clickRandom=pizzasList.get(pickRandom());
        String pizzaName = clickRandom.findElement(By.tagName("div")).getText();
        clickRandom.click();
        return pizzaName;
    }
}
