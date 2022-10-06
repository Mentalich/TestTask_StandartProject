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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoTest {
    private  WebDriver driver;
    final Logger logger= LoggerFactory.getLogger(AutoTest.class);
    @BeforeEach
    public void setup() {
        setDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://dodopizza.ru/moscow");//1. Первый пункт общий для всех тест-кейсов, вынесен в предзапуск.
        logger.info("Открываем начальную веб-страницу");
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
        logger.info("Проверка количества позиций пицц в меню");
        assertEquals(33, menuList.getNumberOfPizzaPositions());//2. проверить общее кол-во товаров в РАЗДЕЛЕ "Пицца";
        logger.info("Проверка тега локации");
        assertEquals("Москва", mainPage.MainMenu_access().getLocationIndex());//3. в шапке страницы, рядом с надписью "Доставка пиццы ", отображается наименование региона
        logger.info("Тест пройден!");
    }
    @Test
    public void Test_Case1(){
        MenuList menuList=new MenuList();
        OrderCustomisationPage orderCustomisationPage =new OrderCustomisationPage();


        String name_expected = menuList.clickOnRandom();//Нажимаем на случайную позицию, сохраняя имя позиции
        logger.info("Выбираем случайную позицию, ",name_expected);
        String name_actual = orderCustomisationPage.checkName();//сохраняем имя позиции в виджете
        logger.info("Выбираем маленькую пиццу");
        int price_before = orderCustomisationPage.customiseSize(Size.SMALL);
        logger.info("Добавляем позицию в корзину");
        int price_after = orderCustomisationPage.addPizzaToCart();
        logger.info("Проверяем совпадение имён");
        assertEquals(name_expected, name_actual);//Проверка совпадения имён
        logger.info("Проверяем разницу цен");
        Assertions.assertNotSame(price_after, price_before);//проверка изменения цены
        logger.info("Проверяем наличие позиции в корзине");
        assertEquals("1", menuList.MainMenu_access().checkCartAmount());//проверка количества объектов корзины
        logger.info("Тест пройден!");
    }

    @Test()
    public void Test_case2() {
        MenuList menuList=new MenuList();
        OrderCustomisationPage orderCustomisationPage = new OrderCustomisationPage();
        CartPage cartPage = new CartPage();

        List<String>orderNames=new ArrayList<String>();//список имён добавляемых позиций
        int objectsToAdd=5;//Количество позиций, добавляемых в корзину, согласно тест-кейсу
        logger.debug("Добавляем {} позиций", objectsToAdd);

        for (int count=0; count<objectsToAdd;count++){
            menuList.clickOnRandom();//нажимаем на случайную позицию
            orderNames.add(orderCustomisationPage.checkName());//сохраняем имя позиции в списке
            logger.debug("Добавляем в корзину позицию {} стоимостью {}", orderNames.get(count), orderCustomisationPage.addPizzaToCart());//добавляем в корзину, запоминая цену
        }
        orderCustomisationPage.MainMenu_access().clickCartButton();
        logger.info("Открываем корзину");
        cartPage.setPizzasList();//составляем список позиций в корзине
        logger.info("Читаем список позиций в корзине");
        //соотносим имена
        for (int count=0; count<objectsToAdd;count++){
            assertEquals(orderNames.get(count),cartPage.getName(count));
        }
        logger.info("Тест пройден!");
    }

    private static int savePic(File picture) throws IOException {
        Random randomID=new Random();
        int index=randomID.nextInt(100000);
        FileUtils.copyFile(picture, new File("*\\test\\screenshot"+index+".jpg"));
        return index;
    }
    @AfterEach
    public void save_and_close() throws IOException {
        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        logger.info("Сохраняем скриншот, код:{}", savePic(screenshot));
        driver.close();
    }
}


