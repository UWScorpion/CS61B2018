package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    public Clorus(double e){
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;

    }

    @Override
    public void move() {
        energy -= 0.03;// Cloruses should lose 0.03 units of energy on a MOVE action
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Creature replicate() {
        energy *= 0.5;
        return new Clorus(0.5 * energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;// Cloruses should lose 0.01 units of energy on a STAY action.
    }

    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        // If there are no empty spaces, the Clorus should STAY.
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        //if any Plips are seen, the Clorus will ATTACK one of them randomly.
        List<Direction> pilps = getNeighborsOfType(neighbors, "plip");
        if (pilps.size() != 0) {
            Direction moveDir = HugLifeUtils.randomEntry(pilps);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }

        //if the Clorus has energy greater than or equal to one,
        // it will REPLICATE to a random empty square
        if(energy >= 1){
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }

        //the Clorus will MOVE to a random empty square
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }


}