package com.skillio.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/Parameterization",
				glue="com.skillio", 
				dryRun=false)

public class Runner extends AbstractTestNGCucumberTests{

	@DataProvider(parallel=true)
	@Override
	public Object[][] scenarios() {
		// TODO Auto-generated method stub
		return super.scenarios();
	}
}
