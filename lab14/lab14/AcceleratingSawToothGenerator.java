package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator extends SawToothGenerator {


    private double factor;
    public AcceleratingSawToothGenerator(int period, double factor){
        super(period);
        this.factor = factor;
    }

    @Override
    public double next() {
        state = (state + 1);
        state %= period;
        if (state == 0) {
            period = (int) (period * factor);
        }
        return normalize();
    }

}