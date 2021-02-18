package com.pwn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PlayingWithNumbersApplicationTests {

	@Autowired
	private PlayingWithNumbersService pwnService;

	@Test
	public void findAvailableNumbersTest1() {
		ArrayList<Integer> testArray1 = new ArrayList<>(Arrays.asList(3, 4, 5));
		ArrayList<Integer> testArray2 = new ArrayList<>(Arrays.asList(1, 4, 5));
		ArrayList<Integer> testArray3 = new ArrayList<>(Arrays.asList(0, 1, 5));
		ArrayList<Integer> combinedTestArrays = new ArrayList<>();
		combinedTestArrays.addAll(testArray1);
		combinedTestArrays.addAll(testArray2);
		combinedTestArrays.addAll(testArray3);
		ArrayList<Integer> actual = pwnService.findAvailableNumbers(combinedTestArrays, 0, 5);
		ArrayList<Integer> expected = new ArrayList<>(Collections.singletonList(2));

		assertEquals(expected, actual);

	}

	@Test
	public void findAvailableNumbersTest2() {
		ArrayList<Integer> testArray1 = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41));
		ArrayList<Integer> testArray2 = new ArrayList<>(Arrays.asList(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45));
		ArrayList<Integer> testArray3 = new ArrayList<>(Arrays.asList(5, 7, 10, 14, 19, 25, 32, 40, 49, 39, 28, 16, 3, 17, 32));
		ArrayList<Integer> combinedTestArrays = new ArrayList<>();
		combinedTestArrays.addAll(testArray1);
		combinedTestArrays.addAll(testArray2);
		combinedTestArrays.addAll(testArray3);
		ArrayList<Integer> actual = pwnService.findAvailableNumbers(combinedTestArrays, 0, 50);
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(0, 11, 13, 20, 22, 23, 26, 29, 31, 43, 44, 46, 47, 48, 50));

		assertEquals(expected, actual);

	}

	@Test
	public void findLargestPrimeTest1() {
		ArrayList<Integer> testArray = new ArrayList<Integer>(Arrays.asList(0, 11, 13, 20, 22, 23, 26, 29, 31, 43, 44, 46, 47, 48, 50));
		ArrayList<Integer> primes = pwnService.findPrimes(testArray);
		int actual = pwnService.findLargestFromCollection(primes);
		int expected = 47;

		assertEquals(expected, actual);

	}

	@Test
	public void findLargestPrimeTest2() {
		ArrayList<Integer> testArray = new ArrayList<Integer>(Arrays.asList(0, 7, 21, 40));
		ArrayList<Integer> primes = pwnService.findPrimes(testArray);
		int actual = pwnService.findLargestFromCollection(primes);
		int expected = 7;

		assertEquals(expected, actual);
	}

	@Test
	public void findLargestPrimeTest3() {
		ArrayList<Integer> testArray = new ArrayList<Integer>(Arrays.asList(4, 8, 12));
		ArrayList<Integer> primes = pwnService.findPrimes(testArray);
		int actual = pwnService.findLargestFromCollection(primes);
		int expected = Integer.MIN_VALUE;

		assertEquals(expected, actual);
	}

	@Test
	public void containsPrimeTest1() {
		ArrayList<Integer> testArray = new ArrayList<Integer>(Arrays.asList(8, 12, 30));
		boolean actual = pwnService.containsPrime(testArray);
		boolean expected = Boolean.FALSE;

		assertEquals(expected, actual);
	}
	@Test
	public void containsPrimeTest2() {
		ArrayList<Integer> testArray = new ArrayList<Integer>(Arrays.asList(7, 21, 17));
		boolean actual = pwnService.containsPrime(testArray);
		boolean expected = Boolean.TRUE;

		assertEquals(expected, actual);
	}
}
