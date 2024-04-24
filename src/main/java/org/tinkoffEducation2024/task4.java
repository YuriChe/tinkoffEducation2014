/**
 * Рассмотрим задачу с экзамена по программированию Тинькофф образование на Java-разработчик (лето 2024).
 * Я не претендую на правильность и оптимальность решений. В статье хочу рассмотреть, в том числе для себя,
 * различные аспекты связанные с заданием. А именно работа с многомерным массивом, поворот матрицы, транспонирование,
 * тестирование методов.
 * <p>
 * Итак, описание задачи: В одной из предыдущих задач требовалось вывести перевернутую матрицу, теперь задача усложняется:
 * при этом поворот необходимо осуществлять in-place, т. е. без выделения дополнительной памяти. Для этого вместо
 * результирующей матрицы необходимо вывести последовательность операций. За одну операцию можно обменять местами
 * два элемента матрицы. Вам дана матрица n*n, а также указано, надо ли повернуть изображение по часовой R или
 * против часовой L стрелки. Выведите последовательность операций, чтобы исходная матрица повернулась
 * на 90 градусов в указанном направлении.
 * <p>
 * Заметьте, что не обязательно переставлять элементы в том порядке, в котором происходил бы поворот, главное
 * чтобы в результате матрица соответствовала повороту на 90 градусов. Также необязательно, чтобы количество
 * операций было минимальным, нужно только вписаться в ограничения. Формат входных данных
 * Первая строка содержит одно натуральное число n (1 <= n <= 10³) и указание направления поворота - символ R или L.
 * Следующие п строк содержат описание матрицы, по n целых неотрицательных чисел, не превосходящих 10¹⁸.
 * Описание задачи смотри в файле README.md
 * Одним из способов повернуть матрицу по часовой стрелке на 90 градусов in-place, без создания нового объекта
 * матрицы является алгоритм состоящий из декомпозиции задачи на 2 этапа. Первый, это транспонирование матрицы,
 * второй - замена столбцов транспонированной матрицы между собой симметрично центра. В результате мы получим
 * искомую матрицу, элементы которой повернуты на 90 градусов. Для того, чтобы повернуть матрицу в обратную сторону,
 * можно совершить вышеописанный алгоритм трижды. Аналогично возможно реализовать поворот на 180 градусов.
 * <p>
 * Другой способ, это последовательное перемещение элементов последовательно с одной стороны воображаемого квадрата
 * матрицы на другую сторону по слоям. На каждом слое таких перемещений 4, так как 4 стороны. Слоев будет n/2.
 * Это следует из того, что ширина стороны каждого слоя это n - 2i (где i номер слоя и 0 это внешний слой).
 * из уравнения n - 2i = 0  получаем, что слоев i = n/2. Для хранения одного элемента на слой, а не каждого,
 * сохраним первый и пойдем против часовой. В конце, после всех перезаписей элементов по кругу запишем элемент
 * из временной переменной на его место. И так для каждой позиции в стороне/ширине матрицы.
 * <p>
 * Однако, в задаче требуется не реально перемещать элементы, а указать координаты ячейки принимающей и источника.
 * Следовательно, нам нужно эти значения получить и посчитать количество перемещений. Непонятно только как учитывать
 * запись временного значения. Исходя из примеров, нам показано учитывать только явные перемещения элементов между
 * ячейками, из примера 3 следует что их 3, а не 4.. из примера 1 вообще следует, что перемещение учитывается только 1.
 * Можно предположить, что нам предлагается сравнить значения, и если они равны перемещения не осуществлять… Однако в
 * процессе перемещений значения могут изменяться и по идеи при другом алгоритме какие-то перемещения не нужны, но это уже мысли сюр).
 * Я все же буду учитывать запись четвертой итерации записи в ячейку временного значения из первой позиции, тк иначе
 * вывод выглядит бредово.
 * <p>
 * Если не сравнивать значения, то на каждый слой у нас происходит 5 перезаписей с учетом записи во временную переменную и
 * записи из нее. Следовательно, всего 5 * n/2 = операций. Это количество очевидно меньше требуемых 7 * n^2.
 * Ограничения: количество элементов, размер матрицы помещается в Integer, а вот значения в Long. Однако обратим внимание,
 * для решения задачи нам это в принципе не важно, так как повернуть собственно матрицу нам не требуется, а задача состоит
 * вывезти координаты ячеек.
 * <p>
 * В коде я все же реализую поворот матрицы, хоть и на результат это не влияет.
 * Реализованы помимо задачи транспонирование матрицы.
 * Использовал подходящую для этого случая функциональность как классы типа Record {@link org.tinkoffEducation2024.task4.Cell} для хранения координат.
 **/

package org.tinkoffEducation2024;

import java.util.ArrayDeque;
import java.util.Scanner;

public class task4 {

    enum Direction {
        LEFT,
        RIGHT
    }

    static int sizeMatrix;

    public record Cell(int x1, int y1, int x2, int y2) {
        @Override
        public String toString() {
            return String.format("%d %d %d %d", x1, y1, x2, y2);
        }
    }

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