package org.epde.bracITinterview;

public class MatrixRotation {
    public static void rotateMatrix(int[][] matrix) {
        int matrixSize = matrix.length;
        for (int row = 0; row < matrixSize / 2; row++) {
            int last = matrixSize - 1 - row;
            for (int column = row; column < last; column++) {
                int offset = column - row;
                int top = matrix[row][column];

                // left -> top
                matrix[row][column] = matrix[last - offset][row];

                // bottom -> left
                matrix[last - offset][row] = matrix[last][last - offset];

                // right -> bottom
                matrix[last][last - offset] = matrix[column][last];

                // top -> right
                matrix[column][last] = top;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        rotateMatrix(matrix);

        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
