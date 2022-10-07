package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends AbstractPage{
    public List<WebElement> pizzasList;
    public void setPizzasList(){
        pizzasList=driver.findElements(By.xpath("//*[@data-testid='cart__list']/article"));//список позиций внутри корзины
    }
    public String getName(int order){
        return pizzasList.get(order).findElement(By.tagName("h3")).getText();//получаем имя из позиции
    }
    public String getPrice(int order){
        String price=pizzasList.get(order).getText();
        price.replaceAll("\\D","");//оставляем только число
        return price;
    }
    public int getSummary(){
        WebElement sumLine=driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[1]/main/div/div[1]/div"));
        String summary = sumLine.getText();
        return Integer.valueOf(summary.replaceAll("(\\D*)",""));
    }
}
