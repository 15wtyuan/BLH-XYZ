package strategy;

public class Copy implements ICalculator {
    @Override
    public String[] calculate(String[] str) {
        return str;
    }
}
