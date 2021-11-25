package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulation {
    public static void main(String[] args) throws FileNotFoundException, InputMismatchException {
        final int amount_of_parameters = 15;

        File file = new File("zad1/board.txt");

        Board board = new Board(file);

        HashMap<String, Integer> integers = new HashMap<>();

        integers.put("number_of_rounds", 1);
        integers.put("how_often_print", 1);
        integers.put("number_of_robs_start", 1);
        integers.put("round_cost", 1);
        integers.put("division_limit", 1);
        integers.put("energy_in_food", 1);
        integers.put("refuel_food_time", 1);
        integers.put("start_energy", 1);

        HashMap<String, Float> non_integers = new HashMap<String, Float>();

        non_integers.put("division_probability", 1.0F);
        non_integers.put("delete_instruction_probability", 1.0F);
        non_integers.put("add_instruction_probability", 1.0F);
        non_integers.put("change_instruction_probability", 1.0F);
        non_integers.put("parent_energy_fraction", 1.0F);

        HashMap<String, String> strings = new HashMap<>();

        strings.put("instructions", " ");
        strings.put("start_program", " ");

        File file2 = new File("zad1/parameters.txt");

        Scanner scanner = new Scanner(file2);

        int loaded_parameters = 0;
        for (int i = 0; i < amount_of_parameters; ++i) {
            String name = scanner.next();
            if (integers.containsKey(name)) {
                int a = scanner.nextInt();
                integers.replace(name, a);
                ++loaded_parameters;
            }
            if (non_integers.containsKey(name)) {
                float b = scanner.nextFloat();
                non_integers.replace(name, b);
                ++loaded_parameters;
            }
            if (strings.containsKey(name)) {
                String c = scanner.next();
                strings.replace(name, c);
                ++loaded_parameters;
            }
        }

        if (loaded_parameters != amount_of_parameters) {
            System.exit(1);
        }

        if (scanner.hasNext()) {
            System.exit(1);
        }
        scanner.close();

        Evolution evolution = new Evolution(board, integers.get("number_of_rounds"),
                integers.get("how_often_print"), integers.get("number_of_robs_start"),
                integers.get("round_cost"), non_integers.get("division_probability"),
                integers.get("division_limit"), integers.get("energy_in_food"),
                integers.get("refuel_food_time"));

        ArrayList<String> instructions = new ArrayList<>();

        for (int i = 0; i < strings.get("instructions").length(); ++i) {
            switch (strings.get("instructions").charAt(i)) {
                case 'l' -> instructions.add(i, "l");
                case 'p' -> instructions.add(i, "p");
                case 'i' -> instructions.add(i, "i");
                case 'w' -> instructions.add(i, "w");
                case 'j' -> instructions.add(i, "j");
            }
        }

        ArrayList<String> start_instructions = new ArrayList<>();

        int program_length = strings.get("start_program").length();

        for (int i = 0; i < program_length; ++i) {
            switch (strings.get("start_program").charAt(i)) {
                case 'l' -> start_instructions.add(i, "l");
                case 'p' -> start_instructions.add(i, "p");
                case 'i' -> start_instructions.add(i, "i");
                case 'w' -> start_instructions.add(i, "w");
                case 'j' -> start_instructions.add(i, "j");
            }
        }

        Program start_program = new Program(instructions, program_length, start_instructions,
                non_integers.get("delete_instruction_probability"),
                non_integers.get("add_instruction_probability"),
                non_integers.get("change_instruction_probability"));

        evolution.fill_robs_in(non_integers.get("parent_energy_fraction"),
                integers.get("start_energy"), start_program);

        evolution.simulate();
    }
}
