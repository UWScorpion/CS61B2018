package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {

    private int period;
    private int state;
    private int weirdState;
    public StrangeBitwiseGenerator(int period){
        this.state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = (state + 1);
        //weirdState = state & (state >>> 3) % period;
        weirdState = state & (state >> 3) & (state >> 8) % period;
//        weirdState = state & (state >> 7) % period;
        return normalize();
    }
    private double normalize() {
        return (2 * (double) weirdState / period) - 1;
    }
}