package com.skillio.stepdefinitions;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Parameterization {
	double x;
	int y;
	double result;
	String fruitName;
	DataTable fruitNames;
	
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
}
