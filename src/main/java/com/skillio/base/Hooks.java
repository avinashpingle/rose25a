package com.skillio.base;


import com.skillio.utils.App;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

import static com.skillio.base.Keyword.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

	private static final Logger LOG = LogManager.getLogger(Hooks.class);
	
	@Before
	public void setUp() throws Exception {
		openBrowser(App.getBrowserName());
		launchUrl(App.getAppUrl("qa"));
	}
	
	@After
	public void tearDown() throws Exception {
		quitBrowser();
		LOG.info("Driver is quite successully...!");
	}
	
}
