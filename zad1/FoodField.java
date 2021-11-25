package zad1;

public class FoodField extends Field {
    private int energy_in_food;
    private int refuel_food_time;
    private int last_eaten_round;
    private boolean is_there_food;

    public FoodField(boolean is_empty, int x, int y, int energy_in_food, int refuel_food_time) {
        super(is_empty, x, y);
        this.energy_in_food = energy_in_food;
        this.refuel_food_time = refuel_food_time;
        this.last_eaten_round = 0;
        this.is_there_food = true;
    }

    public boolean give_energy(Rob rob, int number_of_round) {
        if (is_there_food) {
            rob.add_energy(energy_in_food);
            last_eaten_round = number_of_round;
            is_there_food = false;
            return true;
        } else {
            return false;
        }
    }

    public void setis_there_food(boolean is_there_food) {
        this.is_there_food = is_there_food;
    }

    public int getlast_eaten_round() {
        return last_eaten_round;
    }

    public int getrefuel_food_time() {
        return refuel_food_time;
    }

    public boolean isis_there_food() {
        return is_there_food;
    }
}
