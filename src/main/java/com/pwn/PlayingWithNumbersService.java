package com.pwn;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class PlayingWithNumbersService {
    private int DEFAULT_START_RANGE = 0;
    private int DEFAULT_END_RANGE = 50;
    private int startRange = DEFAULT_START_RANGE;
    private int endRange = DEFAULT_END_RANGE;

    public void startDefault() {
        ArrayList<ArrayList<Integer>> arrays;
        System.out.println("Generating arrays within the default range (0-50) and size (15).");
        arrays = generateArrays(0, 50, 3, 15);
        int largestPrime = getLargestPrimeFromArrays(arrays);
        if(largestPrime != Integer.MIN_VALUE) {
            System.out.println("Largest Prime Number: " + largestPrime);
        }
        else {
            System.out.println("No prime numbers found.");
        }
    }

    public void startUserStories() {
        int largestPrime = Integer.MIN_VALUE;
        startRange = DEFAULT_START_RANGE;
        endRange = DEFAULT_END_RANGE;
        ArrayList<ArrayList<Integer>> arrays;
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like customize your arrays? (y/n) Defaults; Random values, Range: 0-50, Array Size: 15, Number of Arrays: 3");
        while(true) {
            String input = scan.nextLine();
            if(input.equals("y")) {
                arrays = getUserArrays();
                largestPrime = getLargestPrimeFromArrays(arrays);
                break;
            }
            else if(input.equals("n")) {
                System.out.println("Generating arrays within the default range (0-50) and size (15).");
                arrays = generateArrays(0, 50, 3, 15);
                largestPrime = getLargestPrimeFromArrays(arrays);
                break;
            }
            else {
                System.out.println("Invalid response. Enter y for yes, or n for no.");
                System.out.println("Would you like customize your arrays? (y/n)");
            }
        }
        if(largestPrime != Integer.MIN_VALUE) {
            System.out.println("Largest Prime Number: " + largestPrime);
        }
        else {
            System.out.println("No prime numbers found.");
        }
    }

    /* take in an arraylist of arraylists and output the largest prime found within those arrays. */
    private int getLargestPrimeFromArrays(ArrayList<ArrayList<Integer>> arrays) {
        //default value to return -- indicates no prime number was found
        int largestPrime = Integer.MIN_VALUE;

        //combine the lists together
        ArrayList<Integer> combinedLists = new ArrayList<>();
        for(ArrayList<Integer> a : arrays) {
            combinedLists.addAll(a);
        }

        //get available numbers from the combined list
        ArrayList<Integer> availableNumbersArray = findAvailableNumbers(combinedLists, startRange, endRange);

        //if the available numbers contain a prime number, find all primes, then find the largest
        if(containsPrime(availableNumbersArray)) {
            ArrayList<Integer> primeNumbersArray = findPrimes(availableNumbersArray);
            largestPrime = Collections.max(primeNumbersArray);
        }
        return largestPrime;
    }

    //Allows user to customize their arrays, and then outputs the arrays that are produced, either manually or randomly generated
    private ArrayList<ArrayList<Integer>> getUserArrays() {
        int arraySize = 15;
        int numOfArrays = 3;
        startRange = 0;
        endRange = 50;
        ArrayList<ArrayList<Integer>> userArrays = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        //a little bit of wonky code, but basically retrieve corresponding value from user, or send back default value(s)
        int[] range = getUserStringInput("range?", startRange, endRange);
        startRange = range[0];
        endRange = range[1];
        arraySize = getUserStringInput("arraySize?", arraySize, 0)[0];
        numOfArrays = getUserStringInput("numOfArrays?", numOfArrays, 0)[0];

        //if yes, allow user to input their own arrays with their new bounds
        //if no, generate array with the user's new bounds
        System.out.println("Would you like to input the arrays manually? (y/n)");
        while(true) {
            String input = scan.nextLine();
            if(input.equals("y")) {
                userArrays = getManualUserArrays(numOfArrays, arraySize);
                break;
            }
            else if(input.equals("n")) {
                userArrays = generateArrays(startRange, endRange, numOfArrays, arraySize);
                break;
            }
            else {
                System.out.println("Invalid response. Enter y for yes, or n for no.");
                System.out.println("Would you like to input the arrays manually? (y/n)");
            }
        }

        return userArrays;
    }

    private ArrayList<ArrayList<Integer>> getManualUserArrays(int numOfArrays, int arraySize) {
        ArrayList<ArrayList<Integer>> userArrays = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        for(int i=1; i<=numOfArrays;i++) {
            System.out.println(String.format("Array %d:", i));
            System.out.println(String.format("Enter %d integers within the range of %d-%d. Formats accepted: [0,1,2,3,4] or 0 1 2 3 4", arraySize, startRange, endRange));
            while(true) {
                try {
                    String input = scan.nextLine();
                    //if starting and ending with brackets, indicating [0,1,2,3,4] format, parse out the numbers and store in array
                    if(input.charAt(0) == '[' && input.charAt(input.length() - 1) == ']') {
                        int[] a1 = Arrays.stream(input.split("[,\\[\\]]")).filter(w -> !w.equals("")).mapToInt(Integer::parseInt).toArray();
                        ArrayList<Integer> arr = Arrays.stream(a1).boxed().collect(Collectors.toCollection(ArrayList::new));
                        for(int j=0;j<arr.size(); j++) {
                            if(arr.get(j) < startRange || arr.get(j) > endRange) {
                                arr.remove(j);
                            }
                        }
                        if(arr.size() == arraySize) {
                            userArrays.add(arr);
                        }
                        else {
                            System.out.println(String.format("Incorrect number of integers within the range of %d-%d added.", startRange, endRange));
                            i--;
                        }
                        break;
                    }
                    //if input starts with digit, indicates 0 1 2 3 4 format, parse out the numbers and put into array
                    else if (Character.isDigit(input.charAt(0))) {
                        ArrayList<Integer> arr = new ArrayList<>();
                        String[] s = input.split(" ");
                        for (String value : s) {
                            int num = Integer.parseInt(value);
                            if(num >= startRange && num <= endRange) {
                                arr.add(num);
                            }

                        }
                        //if the correct number of valid integers have been added, go ahead and add it to the list of user arrays
                        if(arr.size() == arraySize) {
                            userArrays.add(arr);
                        }
                        //otherwise, basically undo this last iteration of input
                        else {
                            System.out.println("Incorrect number of valid integers added.");
                            i--;
                        }
                        break;
                    }
                    else {
                        System.out.println("Invalid Input");
                        System.out.println(String.format("Enter %d integers within the range of %d-%d. Formats accepted: [0,1,2,3,4] or 0 1 2 3 4", arraySize, startRange, endRange));
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                    System.out.println(String.format("Enter %d integers within the range of %d-%d. Formats accepted: [0,1,2,3,4] or 0 1 2 3 4", arraySize, startRange, endRange));
                }
            }
        }

        return userArrays;
    }

    //take in a string for inputType and default values, then get corresponding values based on inputType and return them
    private int[] getUserStringInput(String inputType, int defaultVal1, int defaultVal2) {
        int[] retVal = new int[2];
        retVal[0] = defaultVal1;
        retVal[1] = defaultVal2;
        Scanner scan = new Scanner(System.in);
        inputResponse(inputType);
        while(true) {
            String input = scan.nextLine();
            if(input.equals("y")) {
                switch(inputType) {
                    case "range?":
                        retVal[0] = getUserNumberInput("startRange");
                        retVal[1] = getUserNumberInput("endRange");
                        break;
                    case "numOfArrays?":
                        retVal[0] = getUserNumberInput("numOfArrays");
                        break;
                    case "arraySize?":
                        retVal[0] = getUserNumberInput("arraySize");
                        break;
                }
                break;
            }
            else if(input.equals("n")) {
                break;
            }
            else {
                System.out.println("Invalid response. Enter y for yes, or n for no.");
                inputResponse(inputType);
            }
        }
        return retVal;
    }

    //take in a string for inputType and return the int provided by the user if the user provides valid input
    private int getUserNumberInput(String inputType) {
        int retVal;
        Scanner scan = new Scanner(System.in);
        inputResponse(inputType);
        while(true) {
            if(scan.hasNextInt()) {
                int input = scan.nextInt();
                if(input > 0 && (inputType.equals("arraySize") || inputType.equals("numOfArrays") || inputType.equals("endRange"))) {
                    retVal = input;
                    break;
                }
                else if(input >= 0 && inputType.equals("startRange")) {
                    retVal = input;
                    break;
                }
                else {
                    System.out.println("Invalid response - input is not a positive integer");
                    inputResponse(inputType);
                }
            }
            else {
                scan.next();
                System.out.println("Invalid response - input is not an integer");
                inputResponse(inputType);
            }
        }
        return retVal;
    }

    //helper class that cleans up system.outs depending on what is needed
    private void inputResponse(String inputType) {
        switch(inputType) {
            case "arraySize":
                System.out.println("Enter the size of array. Must be a positive integer (default is 15).");
                break;
            case "numOfArrays":
                System.out.println("Enter the number of arrays to include. Must be a positive integer (default is 3).");
                break;
            case "startRange":
                System.out.println("Enter the starting range. Must be 0 or a positive integer (default is 0).");
                break;
            case "endRange":
                System.out.println("Enter the end range. Must be a positive integer (default is 50).");
                break;
            case "range?":
                System.out.println("Would you like a new range of numbers? (y/n). The default is 0-50.");
                break;
            case "numOfArrays?":
                System.out.println("Would you like to customize the number of arrays to include? (y/n). The default is 3.");
                break;
            case "arraySize?":
                System.out.println("Would you like a new size for your arrays? (y/n). The default is 15.");
                break;


        }
    }

    //finds all values excluded from the provided list within the provided range
    public ArrayList<Integer> findAvailableNumbers(ArrayList<Integer> arr, int startRange, int endRange) {
        ArrayList<Integer> retArr = new ArrayList<>();

        for(int i=startRange; i<=endRange; i++) {
            if(!arr.contains(i)) {
                retArr.add(i);
            }
        }
        Collections.sort(retArr);
        System.out.println("Available numbers:");
        System.out.print("[");
        for(int i=0; i<retArr.size(); i++) {
            System.out.print(retArr.get(i));
            if(i != retArr.size()-1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
        return retArr;
    }

    //unused now, but could be used in a loop instead of generate arrays
    public ArrayList<Integer> generateArray(int startRange, int endRange, int numbers) {
        ArrayList<Integer> retArr = new ArrayList<>();
        int possibleNumbers = endRange - startRange;
        System.out.print("[");
        for(int i=0;i<numbers;i++) {
            Random random = new Random();
            retArr.add(i, random.nextInt(possibleNumbers) + startRange);
            System.out.print(retArr.get(i) + ",");
        }
        System.out.println("]");
        return retArr;
    }

    //given the bounds of an array, generate random numbers and return an arraylist containing all of the generated arrays
    public ArrayList<ArrayList<Integer>> generateArrays(int startRange, int endRange, int numOfArrays, int arrSize) {
        ArrayList<ArrayList<Integer>> retArrays = new ArrayList<>();
        int possibleNumbers = endRange - startRange;
        for(int i=1; i<=numOfArrays; i++) {
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("Array " + i + " :");
            System.out.print("[");
            for(int j=0;j<arrSize;j++) {
                Random random = new Random();
                arr.add(j, random.nextInt(possibleNumbers) + startRange);
                System.out.print(arr.get(j));
                if(j != arrSize-1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
            retArrays.add(arr);
        }

        return retArrays;
    }

    //some duplicate code here; didn't make much sense to have a containsPrime method that didn't actually check a list of numbers, but I could have avoided this method altogether
    public boolean containsPrime(ArrayList<Integer> arr) {
        int maxFromArr = Collections.max(arr);
        ArrayList<Boolean> primes = new ArrayList<>();
        for(int i=0; i<maxFromArr+1; i++)
            primes.add(Boolean.TRUE);

		/*We know 0 & 1 aren't primes and we can't multiply other values with 0 or 1 to determine primes,
		so let's make them false and start at p=2
		*/
        primes.add(0, Boolean.FALSE);
        primes.add(1, Boolean.FALSE);
        for(int p=2; p*p <= maxFromArr; p++) {
            if(primes.get(p)) {
                for(int i=p*2; i<=maxFromArr; i += p)
                    primes.set(i, Boolean.FALSE);
            }
        }

        for (Integer integer : arr) {
            if (primes.get(integer)) {
                return true;
            }
        }
        return false;
    }

    //Find all primes given an array, and return that list of primes. Also prints the primes, as well as if a prime exists. Could have parsed out this method into a few methods
    public ArrayList<Integer> findPrimes(ArrayList<Integer> arr) {
        ArrayList<Integer> retArr = new ArrayList<>();
        int maxFromArr = Collections.max(arr);
        ArrayList<Boolean> primes = new ArrayList<>();
        for(int i=0; i<maxFromArr+1; i++)
            primes.add(Boolean.TRUE);

		/*We know 0 & 1 aren't primes and we can't multiply other values with 0 or 1 to determine primes,
		so let's make them false and start at p=2
		*/
        primes.add(0, Boolean.FALSE);
        primes.add(1, Boolean.FALSE);

        for(int p=2; p*p <= maxFromArr; p++) {
            if(primes.get(p)) {
                for(int i=p*2; i<=maxFromArr; i += p)
                    primes.set(i, Boolean.FALSE);
            }
        }
        System.out.println("Are there any prime numbers? ");
        boolean containsPrime = Boolean.FALSE;
        for (Integer integer : arr) {
            if (primes.get(integer)) {
                containsPrime = Boolean.TRUE;
                break;
            }
        }
        if(containsPrime) {
            System.out.println("yes");
        }
        else {
            System.out.println("no");
            retArr.add(Integer.MIN_VALUE);
            return retArr;
        }

        System.out.println("Prime numbers:");
        System.out.print("[");
        for (Integer integer : arr) {
            if (primes.get(integer)) {
                retArr.add(integer);
                System.out.print(integer + " ");
            }
        }
        System.out.println("]");

        return retArr;
    }

    //not necessary
    public int findLargestFromCollection(ArrayList<Integer> arr) {
        return Collections.max(arr);
    }
}
