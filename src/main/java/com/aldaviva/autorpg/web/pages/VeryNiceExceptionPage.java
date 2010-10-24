package com.aldaviva.autorpg.web.pages;

public class VeryNiceExceptionPage extends BasePage {
	public VeryNiceExceptionPage() {
		throw new RuntimeException("Exceptions in the browser, neat!");
	}
}
