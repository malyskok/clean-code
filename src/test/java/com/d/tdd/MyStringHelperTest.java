package com.d.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStringHelperTest {
	MyStringHelper helper = new MyStringHelper();

	//Red
	//Green
	//Refactor

	//	Truncate A in first 2 positions of a String
	//  "ABCD" => "BCD", "AACD"=> "CD", "BACD"=>"BCD", "AAAA" => "AA", "MNAA"=>"MNAA"
	@Test
	void testReplaceAInFirst2Positions() {
		assertEquals("BCD", helper.replaceAInFirst2Positions("ABCD"));
		assertEquals("CD", helper.replaceAInFirst2Positions("AACD"));
		assertEquals("BCD", helper.replaceAInFirst2Positions("BACD"));
		assertEquals("AA", helper.replaceAInFirst2Positions("AAAA"));
		assertEquals("MNAA", helper.replaceAInFirst2Positions("MNAA"));
		assertNull(helper.replaceAInFirst2Positions(null));
		assertEquals("", helper.replaceAInFirst2Positions(""));
		assertEquals("", helper.replaceAInFirst2Positions("A"));
		assertEquals("B", helper.replaceAInFirst2Positions("B"));
		assertEquals("BB", helper.replaceAInFirst2Positions("BB"));
		assertEquals("B", helper.replaceAInFirst2Positions("AB"));
		assertEquals("B", helper.replaceAInFirst2Positions("BA"));
		assertEquals("", helper.replaceAInFirst2Positions("AA"));
	}

	//	Check if first two and last two characters in the string are the same.
	//  ""=>false, "A"=>false, "AB"=>true, "ABC"=>false, "AAA"=>true, "ABCAB"=>true, "ABCDEBA"=>false
	@Test
	void testAreTwoFirstAndTwoLastCharsTheSame(){
		assertFalse(helper.areTwoFirstAndTwoLastCharsTheSame(""));
		assertFalse(helper.areTwoFirstAndTwoLastCharsTheSame("A"));
		assertTrue(helper.areTwoFirstAndTwoLastCharsTheSame("AB"));
		assertFalse(helper.areTwoFirstAndTwoLastCharsTheSame("ABC"));
		assertTrue(helper.areTwoFirstAndTwoLastCharsTheSame("AAA"));
		assertTrue(helper.areTwoFirstAndTwoLastCharsTheSame("ABCAB"));
		assertFalse(helper.areTwoFirstAndTwoLastCharsTheSame("ABCDEBA"));
	}

}
