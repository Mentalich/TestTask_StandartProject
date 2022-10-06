import elements.Size;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoTest {
    private  WebDriver driver;
    @BeforeEach
    public void setup() {
        setDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://dodopizza.ru/moscow");//1. Первый пункт общий для всех тест-кейсов, вынесен в предзапуск.
    }

    private void setDriver(){
        driver= new ChromeDriver();
        AbstractPage.setDriver(driver);
    }
    @Test
    public void Test_Case0(){
        MainPage mainPage=new MainPage();
        MenuList menuList=new MenuList();
        assertEquals("https://dodopizza.ru/moscow", driver.getCurrentUrl());//1. Проверить, на какой странице мы находимся
        assertEquals(34, menuList.getNumberOfPizzaPositions());//2. проверить общее кол-во товаров в РАЗДЕЛЕ "Пицца";
        assertEquals("Москва", mainPage.MainMenu_access().getLocationIndex());//3. в шапке страницы, рядом с надписью "Доставка пиццы ", отображается наименование региона
    }
    @Test
    public void Test_Case1(){
        MenuList menuList=new MenuList();
        OrderCustomisationPage orderCustomisationPage =new OrderCustomisationPage();

        String name_expected = menuList.clickOnRandom();
        String name_actual = orderCustomisationPage.checkName();
        String price_before = orderCustomisationPage.customiseSize(Size.SMALL);
        String price_after = orderCustomisationPage.addPizzaToCart();
        assertEquals(name_expected, name_actual);//Проверка совпадения имён
        Assertions.assertNotSame(price_after, price_before, "diff");//проверка изменения цены
        assertEquals("1", menuList.MainMenu_access().checkCartAmount());//проверка количества объектов корзины
    }

    @Test()
    public void Test_case2(){
        MenuList menuList=new MenuList();
        OrderCustomisationPage orderCustomisationPage = new OrderCustomisationPage();
        CartPage cartPage = new CartPage();
        List<String>orderNames=new ArrayList<String>();

        for (int count=0; count<5;count++){
            menuList.clickOnRandom();
            orderNames.add(orderCustomisationPage.checkName());
            orderCustomisationPage.addPizzaToCart();
        }
        orderCustomisationPage.MainMenu_access().clickCartButton();
        cartPage.setPizzasList();
        assertEquals(orderNames.get(0),cartPage.getName(0));
        assertEquals(orderNames.get(1), cartPage.getName(1));
        assertEquals(orderNames.get(2), cartPage.getName(2));
        assertEquals(orderNames.get(3),cartPage.getName(3));
        assertEquals(orderNames.get(4),cartPage.getName(4));
    }

    private static void savePic(File picture) throws IOException {
        FileUtils.copyFile(picture, new File("C:\\Users\\Mentali\\IdeaProjects\\TestTask_StandartProject\\src\\test\\screenshot.jpg"));
    }
    @AfterEach
    public void save_and_close() throws IOException {
        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        savePic(screenshot);
        //driver.close();
    }
}


