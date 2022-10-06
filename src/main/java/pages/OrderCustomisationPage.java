package pages;

import elements.Size;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OrderCustomisationPage extends MainPage{

    public String checkName(){
        return driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[2]/div[1]/div/div/div[1]/div/div[1]")).getText();
    }//Проверяем корректность наименования выбранной позиции

    public String customiseSize(Size size){
        WebElement addButton = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[2]/div[2]/button/span/span[1]"));
        String price = addButton.getText();
        switch (size){
            case SMALL -> {
                driver.findElement(By.xpath("//*[@data-testid='menu__pizza_size_1']")).click();
                break;
            }
            case MEDIUM -> {
                driver.findElement(By.xpath("//*[@data-testid='menu__pizza_size_2']")).click();
                break;
            }
            case LARGE -> {
                driver.findElement(By.xpath("//*[@data-testid='menu__pizza_size_3']")).click();
                break;
            }
        }
        return price;
    }//Функция возвращает значение перед изменениями

    public String addPizzaToCart() {
        WebElement addButton = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div/div[2]/div[2]/button/span/span[1]"));//видит бог я пыталась составить нормальный селектор, но усталость начинает брать своё.
        String price = addButton.getText();
        addButton.click();
        return price;}//Функция вернёт итоговую перед добавлением в корзину цену
}

