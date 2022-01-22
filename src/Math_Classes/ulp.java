package fr.ensimag.deca.tree;

public class ulp {
    
    private static final long MASK = Long.MIN_VALUE;
    double MAX_ULP = 1.9958403095347198116563727130368E+292;

    public double ulp(double d){
        if(Double.isNaN(d)){
            return Double.NaN;
        }
        if(Double.isInfinite(d)){
            return Double.POSITIVE_INFINITY;
        }
        if( d == 0){
            return Double.MIN_VALUE;
        }
        d= abs(d);
        if(d == Double.MAX_VALUE || d == - Double.MAX_VALUE){
            return MAX_ULP;
        }
        else{
            d = abs(d);
            return nextAfter(d, Double.MAX_VALUE) - d;
        }
    }

    public double copySign(double target, double source) {
        if ((Double.doubleToLongBits(target) & MASK) == (Double.doubleToLongBits(source) & MASK)) {
            return target;
        }
        return Double.longBitsToDouble(Double.doubleToLongBits(target) ^ MASK);
    }

    private boolean isSameSign(double x, double y) {
        return copySign(x, y) == x;
    }

    public double nextAfter(double start, double direction) {
        if (Double.isNaN(start) || Double.isNaN(direction)) {
            // If either argument is a NaN, then NaN is returned.
            return Double.NaN;
        } else if (start == direction) {
            // If both arguments compare as equal the second argument is returned.
            return direction;
        } else if ((start == Double.MIN_VALUE || start == -Double.MIN_VALUE)
                && (!isSameSign(start, direction) || Math.abs(direction) < Math.abs(start))) {
            // If start is ±Double.MIN_VALUE and direction has a value such that
            // the result should have a smaller magnitude, then a zero with
            // the same sign as start is returned.
            return copySign(0.0, start);
        } else if (Double.isInfinite(start) && !Double.isInfinite(direction)) {
            // If start is infinite and direction has a value such that the
            // result should have a smaller magnitude, Double.MAX_VALUE with
            // the same sign as start is returned.
            return copySign(Double.MAX_VALUE, start);
        } else if ((start == Double.MAX_VALUE || start == -Double.MAX_VALUE)
                && (Double.isInfinite(direction) && isSameSign(start, direction))) {
            // If start is equal to ± Double.MAX_VALUE and direction has a
            // value such that the result should have a larger magnitude, an
            // infinity with same sign as start is returned.
            return start > 0.0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        } else if (start==0.0) {
            return copySign(Double.MIN_VALUE, direction);
        } else {
            long d = ((direction > start) == (start>=0)) ? 1L : -1L;
            long x = Double.doubleToLongBits(start) + d;
            return Double.longBitsToDouble(x);
        }

    }

    private double abs(double x) {
        if(x>=0){
            return x;
        }
        else{
            return -x;
        }
    }
    

    
}
