package com.skillio.utils;

import java.io.IOException;

public class App {

	private static final String filePath = "./src/main/resources/app.properties";

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
}
