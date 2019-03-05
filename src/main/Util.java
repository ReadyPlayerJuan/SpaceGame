package main;

public class Util {
    public static double skewPctPow(double pct, double pow) {
        if(pct < 0.5) {
            return Math.pow(pct * 2, pow) * 0.5;
        } else {
            return 1 - (Math.pow((1 - pct) * 2, pow) * 0.5);
        }
    }

    public static double mix(double a, double b, double pct) {
        return (a * (1 - pct)) + (b * pct);
    }
}
