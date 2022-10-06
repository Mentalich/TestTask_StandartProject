package pages;

import elements.MainMenu;

public class MainPage extends AbstractPage{
public MainMenu MainMenu_access(){
    return new MainMenu(driver);
}
}
