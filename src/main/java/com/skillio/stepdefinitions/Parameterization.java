package com.skillio.stepdefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.skillio.utils.ExcelUtil;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Parameterization {
	double x;
	int y;
	double result;
	String fruitName;
	int qty;
	DataTable fruitNames;
	List fruitData;
	
	@Given("I have numbers {float} and {int}")
	public void acceptTwoNumbers(float x, int y) {
		this.x = x;
		this.y=y;
	}
	
	@When("I add them")
	public void addNumbers() {
		result = x+y;
	}
	
	@Then("display the result")
	public void displayResult() {
		System.out.println("Result: "+result);
	}
	
	@Given("I have {word}")
	public void acceptFruit(String s) {
		fruitName = s;
	}
	
	@Then("display fruit name")
	public void displayFruitName() {
		System.out.println("Fruit Name: "+fruitName);
	}
	
	@Given("I have following fruits:")
	public void acceptListOfFruits(DataTable dt) {
		this.fruitNames = dt;
	}
	
	@Then("display list of fruits")
	public void displayFruitList() {
		System.out.println("=============Fruites on Menu==========");
		Map<String, List> data = this.fruitNames.asMap(String.class, List.class);
		for (Entry<String,List> entry:data.entrySet()) {
			System.out.println(entry.getKey()+" = "+entry.getValue());
		}
		
		System.out.println("=============XXXXXXXXXXXXXXX==========");
	}
	
	@Given("I have a {word}")
	public void acceptAFruit(String fruitName) {
		this.fruitName = fruitName;
	}
	
	@And("I have {int} of it")
	public void acceptQty(int qty) {
		this.qty = qty;
	}
	
	@Then("display fruit name and its qty")
	public void displayFruitInfo() {
		System.out.println(this.fruitName+" = "+this.qty);
	}
	
	@Given("Read fruit data from {int}")
	public void acceptFruitData(int rowNum) throws IOException {
		String baseDir = System.getProperty("user.dir");
		System.out.println(baseDir+"/src/test/resources/TestData/fruitdata.xlsx");
		ExcelUtil excel = new ExcelUtil(baseDir+"/src/test/resources/TestData/fruitdata.xlsx");
		System.out.println("Reading data from excel sheet for row num: "+rowNum);
		List data = excel.getRowData("FruitData", rowNum);
 		this.fruitName = (String)data.get(1);
 		System.out.println("Fruit Name: "+this.fruitName);
 		this.qty = Integer.parseInt(data.get(2)+"");
	}
	
	@Given("I have {string}")
	public void acceptTwoFruits(String fruitName) {
		this.fruitName = fruitName;
	}
}
