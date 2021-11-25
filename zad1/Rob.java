package zad1;

import org.jetbrains.annotations.NotNull;

public class Rob {
    private Field field;
    private int energy;
    private String direction;
    private float parent_energy_fraction;
    private Program program;
    private int age;

    public Rob(Field field, String direction, int start_energy,
               float parent_energy_fraction, Program program) {
        this.field = field;
        this.direction = direction;
        this.energy = start_energy;
        this.parent_energy_fraction = parent_energy_fraction;
        this.program = program;
        this.age = 0;
    }

    private void left() {
        switch (direction) {
            case "down" -> direction = "right";
            case "up" -> direction = "left";
            case "left" -> direction = "down";
            case "right" -> direction = "up";
        }
    }

    private void right() {
        switch (direction) {
            case "down" -> direction = "left";
            case "up" -> direction = "right";
            case "left" -> direction = "up";
            case "right" -> direction = "down";
        }
    }

    private void go(Board board, int number_of_round) {
        int x = field.getX();
        int y = field.getY();
        switch (direction) {
            case "right" -> this.field = board.get_field((x + 1)% board.getBoard_size_x(), y);
            case "left" -> this.field = board.get_field((x - 1 + board.getBoard_size_x())%
                    board.getBoard_size_x(), y);
            case "up" -> this.field = board.get_field(x, (y - 1 + board.getBoard_size_y())%
                    board.getBoard_size_y());
            case "down" -> this.field = board.get_field(x, (y + 1)% board.getBoard_size_y());
        }
        if (!field.is_empty()) {
            FoodField food_field = (FoodField) field;
            if (food_field.give_energy(this, number_of_round)) {
                board.setNumber_of_food_fields(board.getNumber_of_food_fields() - 1);
            }
        }
    }

    private void search(@NotNull Board board) {
        int x = field.getX();
        int y = field.getY();
        if (!board.get_field((x + 1)% board.getBoard_size_x(), y).is_empty()) {
            direction = "right";
        }
        if(!board.get_field((x - 1 + board.getBoard_size_x())%
                board.getBoard_size_x(), y).is_empty()) {
            direction = "left";
        }
        if (!board.get_field(x, (y - 1 + board.getBoard_size_y())
                % board.getBoard_size_y()).is_empty()) {
            direction = "up";
        }
        if (!board.get_field(x, (y + 1)% board.getBoard_size_y()).is_empty()) {
            direction = "down";
        }
    }

    private boolean eat_field(Board board, int a, int b, int number_of_round) {
        if (!board.get_field(a % board.getBoard_size_x(),
                b % board.getBoard_size_y()).is_empty()) {
             FoodField food_field = (FoodField) board.get_field
                     (a % board.getBoard_size_x(), b % board.getBoard_size_y());
             if (food_field.give_energy(this, number_of_round)) {
                 board.setNumber_of_food_fields(board.getNumber_of_food_fields() - 1);
                 field = board.get_field(a % board.getBoard_size_x(),
                         b % board.getBoard_size_y());
             }
             return true;
        } else {
            return false;
        }
    }

    private void eat(Board board, int number_of_round) {
        int x = field.getX();
        int y = field.getY();
        boolean eaten;
        eaten = eat_field(board, x + 1, y, number_of_round);
        if (!eaten) {eaten = eat_field(board, x - 1 + board.getBoard_size_x(),
                y, number_of_round);}
        if (!eaten) {eaten = eat_field(board, x, y + 1,  number_of_round);}
        if (!eaten) {eaten = eat_field(board, x, y - 1 + board.getBoard_size_y(),
                number_of_round);}
        if (!eaten) {eaten = eat_field(board, x + 1, y + 1, number_of_round);}
        if (!eaten) {eaten = eat_field(board, x + 1,
                y - 1 + board.getBoard_size_y(), number_of_round);}
        if (!eaten) {eaten = eat_field(board, x - 1 + board.getBoard_size_x()
                , y + 1, number_of_round);}
        if (!eaten) {eat_field(board, x - 1 + board.getBoard_size_x(),
                y - 1 + board.getBoard_size_y(), number_of_round);}
    }

    public void follow_the_instructions(int i, Board board, int number_of_round) {
        switch (program.getinstructions(i)) {
            case "l" -> left();
            case "p" -> right();
            case "i" -> go(board, number_of_round);
            case "w" -> search(board);
            case "j" -> eat(board, number_of_round);
        }
        --energy;
    }

    private String opposite_direction(String direction) {
        String opposite = "up";
        switch (direction) {
            case "down" -> opposite = "up";
            case "up" -> opposite = "down";
            case "left" -> opposite = "right";
            case "right" -> opposite = "left";
        }
        return opposite;
    }

    public Rob divide() {
        int new_energy = (int) (parent_energy_fraction * energy);
        String new_direction = opposite_direction(direction);
        Program new_program = program.mutate();

        return new Rob(field, new_direction, new_energy,
                parent_energy_fraction, new_program);
    }

    public Field get_field() {
        return field;
    }

    public int get_energy() {
        return energy;
    }

    public Program getProgram() {
        return program;
    }

    public int getage() {
        return age;
    }

    public void add_age() {
        ++this.age;
    }

    public void add_energy(int energy) {
        this.energy += energy;
    }
}
