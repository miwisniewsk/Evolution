package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private int board_size_x;
    private int board_size_y;
    private Field[][] field;
    private int number_of_food_fields;

    public Board(File file)  throws FileNotFoundException{
        Scanner scanner = new Scanner(file);

        int number_of_lines = 0;

        ArrayList<String> lines = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(number_of_lines, line);
            ++number_of_lines;
            this.board_size_x = line.length();
        }

        scanner.close();

        this.board_size_y = number_of_lines;

        this.field = new Field[board_size_x][board_size_y];

        int number_of_food_fiels = 0;
        for (int i = 0; i < board_size_x; ++i) {
            for (int j = 0; j < board_size_y; ++j) {
                if (lines.get(j).charAt(i) == 'x') {
                    field[i][j] = new Field(false, i, j);
                    ++number_of_food_fiels;
                } else {
                    field[i][j] = new Field(true, i, j);
                }
            }
        }
        this.number_of_food_fields = number_of_food_fiels;
    }

    public void refuel_board(int energy_in_food, int food_refuel_time) {
        for (int i = 0; i < board_size_x; ++i) {
            for (int j = 0; j < board_size_y; ++j) {
                if (!field[i][j].is_empty()) {
                    field[i][j] = new FoodField(false, i, j,
                            energy_in_food, food_refuel_time);
                }
            }
        }
    }

    public int getBoard_size_x() {
        return board_size_x;
    }

    public int getBoard_size_y() {
        return board_size_y;
    }

    public Field get_field(int i, int j) {
        return field[i][j];
    }

    public FoodField getFoodField (int i, int j) {
        return (FoodField) field[i][j];
    }

    public int getNumber_of_food_fields() {
        return number_of_food_fields;
    }

    public void setNumber_of_food_fields(int number_of_food_fields) {
        this.number_of_food_fields = number_of_food_fields;
    }
}
