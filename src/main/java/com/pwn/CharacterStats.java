package com.pwn;

import java.util.ArrayList;

class CharacterStats implements Comparable<CharacterStats> {
    ArrayList<Integer> primes = new ArrayList<>();
    int count;
    int rank;

    double getPrimeAvg() {
        return primes.stream().mapToInt(p -> p).average().orElse(0);
    }

    int getPrimeCount() {
        return primes.size();
    }

    int getCount() {
        return count;
    }

    double getPrimePercentage() {
        return (double) primes.size()/count;
    }

    int getRank() {
        return rank;
    }

    @Override
    public int compareTo(CharacterStats cs) {
        return Double.compare(cs.getPrimePercentage(), this.getPrimePercentage());
    }
}
