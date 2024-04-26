package org.tinkoffEducation2024;

import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] coord = input.split(" ");
        int y = Integer.parseInt(coord[0]);
        int x = Integer.parseInt(coord[1]);
        int[][] matrix;
        if (y > 0 && y <= 1000 && x > 0 && x <= 1000) {
            matrix = new int[y][x];
        } else {
            throw new IllegalArgumentException("Неверные параметры");
        }

        //заполняем матрицу
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        //поворот
        int[][] matrixTurn = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                matrixTurn[i][j] = matrix[y - 1 - j][i];
            }
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(matrixTurn[i][j] + " ");
            }
            System.out.println();
        }
    }
}
