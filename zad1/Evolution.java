package zad1;

import java.util.Random;

public class Evolution {
    private Board board;
    private Population population;
    private int how_many_rounds;
    private int round_number;
    private int how_often_print;

    public Evolution(Board board, int how_many_rounds, int how_often_print, int number_of_robs_start,
                     int round_cost, float division_probability, int division_limit,
                     int food_energy, int food_refuel_time) {
        this.board = board;
        board.refuel_board(food_energy, food_refuel_time);
        this.population = new Population(number_of_robs_start,
                round_cost, division_probability, division_limit);
        this.how_many_rounds = how_many_rounds;
        this.round_number = 1;
        this.how_often_print = how_often_print;
    }

    private String draw_direction() {
        Random random = new Random();
        int a = random.nextInt(4);
        String direction = "left";
        switch (a) {
            case 0 -> direction = "left";
            case 1 -> direction = "right";
            case 2 -> direction = "up";
            case 3 -> direction = "down";
        }
        return direction;
    }

    private int draw_coordinates(int range) {
        Random random = new Random();
        int a = random.nextInt(range);
        return a;
    }

    public void fill_robs_in(float parent_energy_fraction, int start_energy,
                             Program start_program) {
        for (int i = 0; i < population.get_number_of_robs(); ++i) {
            String s = draw_direction();
            int x = draw_coordinates(board.getBoard_size_x());
            int y = draw_coordinates(board.getBoard_size_y());
            Rob rob = new Rob(board.get_field(x, y), s, start_energy,
                    parent_energy_fraction, start_program);
            population.setRob(i, rob);
        }
    }

    private void update_food_fields() {
        for (int i = 0; i < board.getBoard_size_x(); ++i) {
            for (int j = 0; j < board.getBoard_size_y(); ++j) {
                if (!board.get_field(i, j).is_empty()) {
                    if (round_number -  board.getFoodField(i, j).getlast_eaten_round() ==
                    board.getFoodField(i, j).getrefuel_food_time() &&
                    !board.getFoodField(i, j).isis_there_food()) {
                        board.getFoodField(i, j).setis_there_food(true);
                        board.setNumber_of_food_fields(board.getNumber_of_food_fields() + 1);
                    }
                }
            }
        }
    }

    private void print() {
        System.out.println("ROBS CONDITIONS:");
        for (int i = 0; i < population.get_number_of_robs(); ++i) {
            System.out.print("coordinates: " + population.getRob(i).get_field().getX() + "," +
                    population.getRob(i).get_field().getY() + " energy: " +
                    population.getRob(i).get_energy() + " age: " + population.getRob(i).getage() +
                    " program: ");
            population.getRob(i).getProgram().print_program();
            System.out.println("");
        }
    }

    public void simulate() {
        print();
        while (round_number <= how_many_rounds){
            population.one_round(round_number, board);
            update_food_fields();

            population.print_round_conditions(round_number, board.getNumber_of_food_fields());

            if (round_number % how_often_print == 0) {
                print();
            }

            ++round_number;
        }
        print();
    }
}

