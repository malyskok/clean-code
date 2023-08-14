package com.b.simple.design.business.text;

public class TextHelper {

	public String swapLastTwoCharacters(String str) {
		if (str == null) {
			return null;
		}

		if (str.length() < 2) {
			return str;
		}

		int length = str.length();
		char last = str.charAt(length - 1);
		char beforeLast = str.charAt(length - 2);
		String start = str.substring(0, length - 2);

		return start + last + beforeLast;
	}

	public String truncateAInFirst2Positions(String str) {
		if (str == null) {
			return null;
		}

		if (str.length() < 2) {
			return str.replace("A", "");
		}

		String end = str.substring(2);
		String start = str.substring(0, 2).replaceAll("A", "");

		return start + end;
	}
}
