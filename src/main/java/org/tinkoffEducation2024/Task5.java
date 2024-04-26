package org.tinkoffEducation2024;

import java.util.Scanner;

public class Task5 {
    //// решение неверное. думаю

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int maxMushrooms = 0;
        int prevPoint = 0;
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            char[] way = line.toCharArray();
            int mushrooms = 0;
            int pointWWW = 0;
            int count = 0;
            for (char cell : way) {
                count++;
                if (cell == 'C' && Math.abs(prevPoint - count) < 2) {
                    prevPoint = count;
                    mushrooms++;
                    break;
                }
                if (cell == 'W') {
                    pointWWW++;
                }
                prevPoint = count;
            }
            if (pointWWW == 3) {
                break;
            }
            maxMushrooms += mushrooms;
            count = 0;
        }

        System.out.println(maxMushrooms);
    }
}

