package zad1;

import java.util.ArrayList;
import java.util.Random;

public class Program {
    private ArrayList<String> all_instructions;
    private int program_length;
    private ArrayList<String> instructions;
    private float delete_instruction_probability;
    private float add_instruction_probability;
    private float change_instruction_probability;

    public Program(ArrayList<String> all_instructions, int program_length, ArrayList<String> instructions,
                   float delete_instruction_probability, float add_instruction_probability, float change_instruction_probability) {
        this.all_instructions = all_instructions;
        this.program_length = program_length;
        this.instructions = instructions;
        this.delete_instruction_probability = delete_instruction_probability;
        this.add_instruction_probability = add_instruction_probability;
        this.change_instruction_probability = change_instruction_probability;
    }

    private String draw_instruction() {
        Random random = new Random();
        int a = random.nextInt(all_instructions.size());
        return all_instructions.get(a);
    }

    public Program mutate() {
        ArrayList<String> new_all_instructions = new ArrayList<>();
        for (int i = 0; i < all_instructions.size(); ++i) {
            new_all_instructions.add(i, all_instructions.get(i));
        }
        ArrayList<String> new_instructions = new ArrayList<>();
        for (int i = 0; i < program_length; ++i) {
            new_instructions.add(i, instructions.get(i));
        }
        Program new_program = new Program(new_all_instructions, program_length, new_instructions,
                delete_instruction_probability, add_instruction_probability, change_instruction_probability);

        Random random = new Random();

        float v = random.nextFloat();
        if (v <= delete_instruction_probability && new_program.getprogram_length() > 0) {
            new_program.delete_instructions(new_program.getprogram_length() - 1);
            new_program.decrease_program_length();
        }

        float x = random.nextFloat();
        if (x <= add_instruction_probability) {
            new_program.addinstruction(new_program.getprogram_length());
            new_program.increase_program_length();
        }

        float z = random.nextFloat();
        if (z <= change_instruction_probability && new_program.getprogram_length() > 0) {
            int i = random.nextInt(new_program.getprogram_length());
            new_program.change_instruction(i);
        }
        return new_program;
    }

    public void print_program() {
        for (String instruction: instructions) {
            System.out.print(instruction);
        }
    }

    public void increase_program_length() {
        ++this.program_length;
    }

    public void decrease_program_length() {
        --this.program_length;
    }

    public int getprogram_length() {
        return program_length;
    }

    public String getinstructions(int i) {
        return instructions.get(i);
    }

    public void delete_instructions(int i) {
        instructions.remove(i);
    }

    public void addinstruction(int i) {
        instructions.add(i, draw_instruction());
    }

    public void change_instruction(int i) {
        instructions.set(i, draw_instruction());
    }
}
