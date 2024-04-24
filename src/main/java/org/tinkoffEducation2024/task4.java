package org.tinkoffEducation2024;

import java.util.ArrayDeque;
import java.util.Scanner;

public class task4 {

    enum Direction {
        LEFT,
        RIGHT
    }

    static int sizeMatrix;

    public static void transpose(long[][] matrix) {
        long tmp;
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = i; j < sizeMatrix; j++) {
                tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    public static void changeLines(long[][] matrix) {
        long tmp;
        for (int i = 0; i < sizeMatrix / 2; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                tmp = matrix[j][i];
                matrix[j][i] = matrix[j][sizeMatrix - 1 - i];
                matrix[j][sizeMatrix - 1 - i] = tmp;
            }
        }
    }

    public static void turnRight(long[][] matrix) {
        transpose(matrix);
        changeLines(matrix);
    }

    public static void turnLeft(long[][] matrix) {
        turnRight(matrix);
        turnRight(matrix);
        turnRight(matrix);
    }

    public record Cell(int x1, int y1, int x2, int y2) {
        @Override
        public String toString() {
            return String.format("%d %d %d %d", x1, y1, x2, y2);
        }
    }

    public static ArrayDeque<Cell> turnRightMatrixByLayout(long[][] matrix) {
        ArrayDeque<Cell> queueOperations = new ArrayDeque<>();

        //перемещаемся по слоям
        final int K = sizeMatrix - 1; //постоянно используемая константа
        for (int i = 0; i < sizeMatrix / 2; i++) {
            //перемещение по позициям
            for (int j = i; j < (K - i); j++) {

                long tmp = matrix[i][j];
                if (matrix[i][j] != matrix[K - j][i]) {
                    matrix[i][j] = matrix[K - j][i];
                    queueOperations.offer(new Cell(i, j, K - j, i));
                }
                if (matrix[K - j][i] != matrix[K - i][K - j]) {
                    matrix[K - j][i] = matrix[K - i][K - j];
                    queueOperations.offer(new Cell(K - j, i, K - i, K - j));
                }
                if (matrix[K - i][K - j] != matrix[j][K - i]) {
                    matrix[K - i][K - j] = matrix[j][K - i];
                    queueOperations.offer(new Cell(K - i, K - j, j, K - i));
                }
                matrix[j][K - i] = tmp;
                queueOperations.offer(new Cell(j, K - i, i, j));
            }
        }
        return queueOperations;
    }

    public static ArrayDeque<Cell> turnLeftMatrixByLayout(long[][] matrix) {
        ArrayDeque<Cell> queueOperations = new ArrayDeque<>();
        //перемещаемся по слоям
        final int K = sizeMatrix - 1; //постоянно используемая константа
        for (int i = 0; i < sizeMatrix / 2; i++) {
            //перемещение по позициям
            for (int j = i; j < (K - i); j++) {

                long tmp = matrix[i][j];

                if (matrix[i][j] != matrix[j][K - i]) {
                    matrix[i][j] = matrix[j][K - i];
                    queueOperations.offer(new Cell(i, j, j, K - i));
                }
                if (matrix[j][K - i] != matrix[K - i][K - j]) {
                    matrix[j][K - i] = matrix[K - i][K - j];
                    queueOperations.offer(new Cell(j, K - i, K - i, K - j));
                }
                if (matrix[K - i][K - j] != matrix[K - j][i]) {
                    matrix[K - i][K - j] = matrix[K - j][i];
                    queueOperations.offer(new Cell(K - i, K - j, K - j, i));
                }
                matrix[K - j][i] = tmp;
                queueOperations.offer(new Cell(K - i, i, i, j));
            }
        }
        return queueOperations;
    }

    public static void main(String[] args) {
        long[][] matrix;
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] dataString = input.split(" ");

        sizeMatrix = Integer.parseInt(dataString[0]);
        //по условию размер матрицы не более 10*3
        if (sizeMatrix > 0 && sizeMatrix <= (int) Math.pow(10, 3)) {
            matrix = new long[sizeMatrix][sizeMatrix];
        } else {
            throw new IllegalArgumentException("Неверные параметры диапазона массива");
        }

        Direction direction;
        if (dataString[1].charAt(0) == 'L') {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }

        //заполняем матрицу. Значения в диапазоне 0 <= v <= 10 в степени 18 соответствует типу long
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                long number = scanner.nextLong();
                if (number < 0) throw new IllegalArgumentException("Число должно быть неотрицательным");
                matrix[i][j] = number;
            }
        }

        ArrayDeque<Cell> operations;
        if (direction == Direction.RIGHT) {
            operations = turnRightMatrixByLayout(matrix);
        } else {
            operations = turnLeftMatrixByLayout(matrix);
        }

        System.out.println();
        System.out.println(operations.size());
        operations.forEach(System.out::println);
    }
}