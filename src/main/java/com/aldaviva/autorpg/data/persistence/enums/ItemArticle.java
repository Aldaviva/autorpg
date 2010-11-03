package com.aldaviva.autorpg.data.persistence.enums;

public enum ItemArticle {
	INDEFINITE_CONSONANT("a "), INDEFINITE_VOWEL("an "), DEFINITE("The "), POSSESSIVE(""), PROPER("");
	
	private String article;

	private ItemArticle(String article) {
		this.article = article;
	}

	public String toString() {
		return article;
	}
	
}
