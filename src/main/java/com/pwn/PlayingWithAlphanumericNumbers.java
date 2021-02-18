package com.pwn;

import org.apache.commons.math3.primes.Primes;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayingWithAlphanumericNumbers {

    private static int invalidCellsCount;

    public void start() {

        HashMap<Character, CharacterStats> characterMap = new HashMap<>();

        String filePath = "/SearchForPrimes.csv";
        ArrayList<String> input = new ArrayList<>();
        try {
            input = processFile(filePath);
        }
        catch (Exception e) {
            System.out.println("Error Processing File: " + e);
        }
        processAllInput(characterMap, input);
        setCharacterRanks(characterMap);
        printTotalCountForAllCharacters(characterMap);
        printPrimeCountForAllCharacters(characterMap);
        //printPrimeAvgForAllCharacters(characterMap);
        printRankForAllCharacters(characterMap);
        printCharacterWithLargestAvgPrime(characterMap);
        //printPrimePercentageForAllCharacters(characterMap);
    }

    private static void setCharacterRanks(HashMap<Character, CharacterStats> characterMap) {
        HashMap<Character, CharacterStats> map = characterMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Iterator<Map.Entry<Character, CharacterStats>> entryIterator = map.entrySet().iterator();
        int i = 1;
        while(entryIterator.hasNext()) {
            Map.Entry<Character, CharacterStats> entry = entryIterator.next();
            characterMap.get(entry.getKey()).rank = i;
            i++;
        }
    }

    private static <K, V extends Comparable<V>> void printCharacterWithLargestAvgPrime(Map<Character, CharacterStats> map) {
        Optional<Map.Entry<Character, CharacterStats>> maxEntry = map.entrySet()
                .stream()
                .max((Map.Entry<Character, CharacterStats> e1, Map.Entry<Character, CharacterStats> e2) -> Double.compare(e1.getValue().getPrimeAvg(), e2.getValue().getPrimeAvg()));

        System.out.println("Character with largest avg prime: " + maxEntry.get().getKey());
    }

    private static void printPrimePercentageForAllCharacters(HashMap<Character, CharacterStats> characterMap) {
        System.out.println("Prime Percentage:");
        characterMap.forEach((key, value) -> System.out.println(key + ": " + value.getPrimePercentage()));
    }

    private static void printRankForAllCharacters(HashMap<Character, CharacterStats> characterMap) {
        System.out.println("Rank (highest to lowest prime percentage):");
        characterMap.forEach((key, value) -> System.out.println(key + ": " + value.getRank()));
    }

    private static void printTotalCountForAllCharacters(HashMap<Character, CharacterStats> characterMap) {
        System.out.println("Total Count:");
        characterMap.forEach((key, value) -> System.out.println(key + ": " + value.getCount()));
    }

    private static void printPrimeCountForAllCharacters(HashMap<Character, CharacterStats> characterMap) {
        System.out.println("Prime Count:");
        characterMap.forEach((key, value) -> System.out.println(key + ": " + value.getPrimeCount()));
    }

    private static void printPrimeAvgForAllCharacters(HashMap<Character, CharacterStats> characterMap) {
        System.out.println("Prime Average:");
        characterMap.forEach((key, value) -> System.out.println(key + ": " + value.getPrimeAvg()));
    }

    private static void processAllInput(HashMap<Character, CharacterStats> map, ArrayList<String> input) {
        for(String s : input) {
            processCell(map, s);
        }
    }

    private static void processCell(HashMap<Character, CharacterStats> map, String input) {
        try {
            if(Character.isAlphabetic(input.charAt(0))) {
                Character c = input.substring(0, 1).charAt(0);
                int num = Integer.parseInt(input.substring(1));
                if(map.containsKey(c)) {
                    processCharacterStat(map.get(c), num);
                }
                else {
                    CharacterStats x = new CharacterStats();
                    processCharacterStat(x, num);
                    map.put(c, x);
                }
            }
        }
        catch (Exception e) {
            invalidCellsCount++;
            System.out.println("Invalid Input--Ignoring Cell. Invalid Cell: " + invalidCellsCount);
        }
    }

    private static void processCharacterStat(CharacterStats c, int num) {
        if(Primes.isPrime(num)) {
            c.primes.add(num);
        }
        c.count++;
    }

    private static ArrayList<String> processFile(String filePath) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        InputStream inputStream = PlayingWithAlphanumericNumbers.class.getResourceAsStream(filePath);
        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] str = line.split(",");
                input.addAll(Arrays.asList(str));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
        return input;

    }
}
