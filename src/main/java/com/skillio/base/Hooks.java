package com.skillio.base;


import com.skillio.utils.App;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

import static com.skillio.base.Keyword.*;

public class Hooks {

	@Before
	public void setUp() throws Exception {
		openBrowser(App.getBrowserName());
		launchUrl(App.getAppUrl("qa"));
	}
	
	@After
	public void tearDown() throws Exception {
		quitBrowser();
		System.out.println("Driver is quite successully...!");
	}
	
	@BeforeStep
	public void beforeStep() {
		System.err.println("Before step...");
	}
}
