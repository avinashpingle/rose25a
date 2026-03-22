package com.skillio.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/Parameterization",
				glue="com.skillio", 
				dryRun=false)

public class Runner extends AbstractTestNGCucumberTests{

}
