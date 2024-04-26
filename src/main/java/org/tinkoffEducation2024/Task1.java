package org.tinkoffEducation2024;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int countGrades = scanner.nextInt();
        int[] grades = new int[countGrades];
        scanner.nextLine();
        String input = scanner.nextLine();
        String[] strDevNumber = input.split(" ");
        if (strDevNumber.length != countGrades) {
            throw new RuntimeException("Неверное количество оценок");
        }

        for (int i = 0; i < countGrades; i++) {
            int currentGrade = Integer.parseInt(strDevNumber[i]);
            if (currentGrade >= 2 && currentGrade <= 5) {
                grades[i] = currentGrade;
            } else {
                throw new IllegalArgumentException("Неверная оценка");
            }
        }
        int max45 = -1;
        int max45Temp;
        for (int i = 0; i <= grades.length - 7; i++) {
            max45Temp = 0;
            for (int j = i; j < i + 7; j++) {
                if (grades[j] > 3) {
                    if (grades[j] == 5) max45Temp++;
                } else {
                    max45Temp = 0;
                    break;
                }
            }
            if (max45Temp > max45 && max45Temp != 0) max45 = max45Temp;
        }
        System.out.println(max45);
    }
}