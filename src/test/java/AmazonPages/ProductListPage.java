package AmazonPages;

import org.openqa.selenium.By;

public class ProductListPage {

	//public static  By shopbyDropDown = By.xpath("//select[@id='searchDropdownBox']");
	public static  By searchText = By.xpath("//*[@id=\"twotabsearchtextbox\"]");
	//public static  By bookName = By.xpath("//div[contains(@class,'s-result-list')]//a[contains(@class,'a-link-normal')]/span[text()='The Alchemist']");
	public static  By bookName = By.xpath("//div[@class='s-widget-container s-spacing-small s-widget-container-height-small celwidget slot=MAIN template=SEARCH_RESULTS widgetId=search-results_1']//span[@class='a-size-medium a-color-base a-text-normal'][normalize-space()='The Alchemist']");
	public static  By downPage = By.xpath("//span[@id='pagnPrevString']");
	public static  By nextPage = By.xpath("//span[@id='pagnNextString']");

	
	
}
