package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends AbstractPage{
    public List<WebElement> pizzasList;
    public void setPizzasList(){
        pizzasList=driver.findElements(By.xpath("//*[@data-testid='cart__list']/article"));
    }
    public String getName(int order){
        return pizzasList.get(order).findElement(By.tagName("h3")).getText();
    }
}
