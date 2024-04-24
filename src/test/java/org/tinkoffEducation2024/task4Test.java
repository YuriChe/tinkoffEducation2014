package org.tinkoffEducation2024;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.tinkoffEducation2024.task4.*;

class task4Test {

    static int sizeMatrix;
    static long[][] matrix;
    static long[][] matrixTranspose;

    @BeforeAll
    static void setSize() {
        sizeMatrix = 4; // 4 или 3
        task4.sizeMatrix = sizeMatrix;
    }

    @BeforeEach
    void setUp() {
        matrix = new long[sizeMatrix][sizeMatrix];
        int num = 1;
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                matrix[i][j] = num++;
            }
        }

        switch (sizeMatrix) {
            case 3: {
                matrixTranspose = new long[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
                break;
            }
            case 4: {
                matrixTranspose = new long[][]{{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 16}};
                break;
            }
        }
    }

    @Test
    public void turnRight() {
        turnRightMatrixByLayout(matrix);
        /*transpose(matrix);
        changeLines(matrix);*/
        switch (sizeMatrix) {
            case 3: {
                assertArrayEquals(matrix[2], new long[]{9, 6, 3});
                break;
            }
            case 4: {
                assertArrayEquals(matrix[3], new long[]{16, 12, 8, 4});
                break;
            }
        }
    }

    @Test
    public void transpose3() {
        transpose(matrix);
        int i = 0;
        for (long[] line : matrixTranspose) {
            assertArrayEquals(matrix[i], line);
            i++;
        }
    }

    @Test
    public void changeLines3() {
        changeLines(matrix);
        switch (sizeMatrix) {
            case 3: {
                assertArrayEquals(matrix[1], new long[]{6, 5, 4});
                break;
            }
            case 4: {
                assertArrayEquals(matrix[1], new long[]{8, 7, 6, 5});
                break;
            }
        }
    }

    @Test
    public void turnLeftTest() {
//        turnLeft(matrix);
        turnLeftMatrixByLayout(matrix);
        switch (sizeMatrix) {
            case 3: {
                assertArrayEquals(matrix[2], new long[]{1, 4, 7});
                break;
            }
            case 4: {
                assertArrayEquals(matrix[3], new long[]{1, 5, 9, 13});
                break;
            }
        }
    }
}