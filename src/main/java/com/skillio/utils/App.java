package com.skillio.utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

	private static final String filePath = "./src/main/resources/app.properties";

	private static final Logger LOG = LogManager.getLogger(App.class);
	
	private App() {
		// TODO Auto-generated constructor stub
	}

	public static String getBrowserName() {
		PropUtil prop = new PropUtil();
		String browserName = null;

		try {
			browserName = prop.getProperty(filePath, "browser_name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return browserName;
	}

	public static String getAppUrl(String env) {
		PropUtil prop = new PropUtil();
		String appUrl = null;
		try {
			appUrl = prop.getProperty(filePath, "app." + env + ".url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appUrl;
	}

	public static String getUserName(String env) {
		// TODO Auto-generated method stub
		PropUtil prop = new PropUtil();
		String username = null;
		try {
			username = prop.getProperty(filePath, "app." + env + ".username");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		System.out.println("Username: " + username);
		return username;
	}
	
	public static boolean isOnGrid() {
		PropUtil prop = new PropUtil();
		String isGrid = null;
		try {
			isGrid = prop.getProperty(filePath, "isOnGrid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (isGrid == null) {
			LOG.info("isOnGrid property not set; defaulting to false (local run)");
			return false;
		}
		
		if(isGrid.equalsIgnoreCase("true")) {
			LOG.info("Running tests on Selenium Grid...");
		} else {
			LOG.info("Running tests locally...");
		}
		return Boolean.parseBoolean(isGrid);
	}

	/**
	 * Returns the Grid hub URL from properties file. If not present, returns a
	 * sensible default (http://localhost:4444).
	 */
	public static String getGridUrl() {
		PropUtil prop = new PropUtil();
		String gridUrl = null;
		try {
			gridUrl = prop.getProperty(filePath, "grid.url");
		} catch (IOException e) {
			// Log and continue to return default
			e.printStackTrace();
		}
		if (gridUrl == null || gridUrl.trim().isEmpty()) {
			gridUrl = "http://localhost:4444";
		}
		LOG.info("Using Grid URL: " + gridUrl);
		return gridUrl;
	}
}