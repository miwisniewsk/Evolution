package zad1;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public class Population {
    private int number_of_robs;
    private ArrayList<Rob> roby;
    private int round_cost;
    private float division_probability;
    private int division_limit;

    public Population(int number_of_robs_start, int round_cost, float division_probability,
                      int division_limit) {
        this.number_of_robs = number_of_robs_start;
        this.roby = new ArrayList<>();
        this.round_cost = round_cost;
        this.division_probability = division_probability;
        this.division_limit = division_limit;
    }

    public void one_round(int number_of_round, Board board) {
        int akt_roby = number_of_robs;
        for (int i = 0; i < akt_roby; ++i) {
            for (int j = 0; j < roby.get(i).getProgram().getprogram_length(); ++j) {
                roby.get(i).follow_the_instructions(j, board, number_of_round);
            }

            Random random = new Random();
            float x = random.nextFloat();
            if (x <= division_probability) {
                if (roby.get(i).get_energy() >= division_limit) {
                    ++number_of_robs;
                    Rob rob = roby.get(i).divide();
                    roby.add(number_of_robs - 1, rob);
                }
            }
            roby.get(i).add_energy(-round_cost);
            roby.get(i).add_age();
        }
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).get_energy() < 0) {
                --number_of_robs;
                roby.remove(i);
                --i;
                if (number_of_robs == 0) {
                    System.out.println("EXTINCTION");
                    exit(1);
                }
            }
        }
    }

    public int min_program_length() {
        int min = roby.get(0).getProgram().getprogram_length();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).getProgram().getprogram_length() < min) {
                min = roby.get(i).getProgram().getprogram_length();
            }
        }
        return min;
    }

    public int max_program_length() {
        int max = roby.get(0).getProgram().getprogram_length();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).getProgram().getprogram_length() > max) {
                max = roby.get(i).getProgram().getprogram_length();
            }
        }
        return max;
    }

    public float average_program_length() {
        int suma = 0;
        for (int i = 0; i < number_of_robs; ++i) {
            suma += roby.get(i).getProgram().getprogram_length();
        }
        return (float) suma / number_of_robs;
    }

    public int min_energy() {
        int min = roby.get(0).get_energy();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).get_energy() < min) {
                min = roby.get(i).get_energy();
            }
        }
        return min;
    }

    public int max_energy() {
        int max = roby.get(0).get_energy();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).get_energy() > max) {
                max = roby.get(i).get_energy();
            }
        }
        return max;
    }

    public float average_energy() {
        int suma = 0;
        for (int i = 0; i < number_of_robs; ++i) {
            suma += roby.get(i).get_energy();
        }
        return (float) suma / number_of_robs;
    }

    public int min_age() {
        int min = roby.get(0).getage();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).getage() < min) {
                min = roby.get(i).getage();
            }
        }
        return min;
    }

    public int max_age() {
        int max = roby.get(0).getage();
        for (int i = 0; i < number_of_robs; ++i) {
            if (roby.get(i).getage() > max) {
                max = roby.get(i).getage();
            }
        }
        return max;
    }

    public float average_age() {
        int suma = 0;
        for (int i = 0; i < number_of_robs; ++i) {
            suma += roby.get(i).getage();
        }
        return (float) suma / number_of_robs;
    }

    public void print_round_conditions(int number_of_round, int number_of_food_fields) {
        System.out.println(number_of_round + ", rob: " + number_of_robs + ", alive: "
                + number_of_food_fields + " prg: " + min_program_length() + "/"
                + average_program_length() + "/" + max_program_length() + ", energ " +
                min_energy() + "/" + average_energy() + "/" +
                max_energy() + ", age: " + min_age() + "/" +
                average_age() + "/" + max_age());
    }

    public int get_number_of_robs() {
        return number_of_robs;
    }

    public Rob getRob(int i) {
        return roby.get(i);
    }

    public void setRob(int i, Rob rob) {
        this.roby.add(i, rob);
    }
}
