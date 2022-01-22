import java.lang.Math;

class Trigo {
    private static final int MAXBITS = 53;

    /* define this to perform all (non 2power) multiplications
    * and divisions with the cordic linear method.
    */

    /* these are the constants needed */
    double invGain1;
    double invGain2;
    static double[] atanTable = new double[MAXBITS];
    static double[] atanhTable = new double[MAXBITS];

    public void initCordic()
    {
        /* must call this first to initialise the constants.
        * of course, here i use the maths library, but the
        * values would be precomputed.
        */
        double t = 1;
        int i;
        for (i = 0; i < MAXBITS; ++i) {
            atanTable[i] = java.lang.Math.atan(t);
            t /= 2;
            atanhTable[i] = 0.5*java.lang.Math.log((1+t)/(1-t));
        }

        /* set constants */
        invGain1 = 1/gain1Cordic();
        invGain2 = 1/gain2Cordic();
    }

    public static void cordit1(double x0, double y0, double z0, double vecmode)
    {
        /* this is the circular method. 
        * one slight change from the other methods is the y < vecmode 
        * test. this is to implement arcsin, otherwise it can be
        * y < 0 and you can compute arcsin from arctan using
        * trig identities, so its not essential.
        */

        double t;
        double x, y, z;
        int i;

        t = 1;
        x = x0; y = y0; z = z0;

        for (i = 0; i < MAXBITS; ++i) {
            double x1;

            if (vecmode >= 0 && y < vecmode || vecmode<0  && z >= 0) {
                x1 = x - y*t;
                y = y + x*t;
                z = z - atanTable[i];
            }
            else {
                x1 = x + y*t;
                y = y - x*t;
                z = z + atanTable[i];
            }

            x = x1;
            t /= 2;
        }

        x0 = x;
        y0 = y;
        z0 = z;
    }

    public void cordit2(double x0, double y0, double z0, double vecmode)
    {
        /* here's the hyperbolic methods. its very similar to
        * the circular methods, but with some small differences.
        *
        * the `x' iteration have the opposite sign.
        * iterations 4, 7, .. 3k+1 are repeated.
        * iteration 0 is not performed.
        */

        double t;
        double x, y, z;
        int i;

        t = 0.5;
        x = x0; y = y0; z = z0;

        int k = 3;
        for (i = 0; i < MAXBITS; ++i) {
            double x1;
            int j;

            for (j = 0; j < 2; ++j) {
                if (vecmode >= 0 && y < 0 || vecmode<0  && z >= 0) {
                    x1 = x + y*t;
                    y = y + x*t;
                    z = z - atanhTable[i];
                }
                else {
                    x1 = x - y*t;
                    y = y - x*t;
                    z = z + atanhTable[i];
                }
                x = x1;

                if (k!=3) {
                    --k;
                    break;
                }
                else k = 3;
            }

            t /= 2;
        }

        x0 = x;
        y0 = y;
        z0 = z;
    }

    public void cordit0(double x0, double y0, double z0, double vecmode)
    {

        /* the linear methods is the same as the circular but
        * ive simplified out the x iteration as it doesnt change.
        */
        double t;
        double x, y, z;
        int i;

        t = 1;
        x = x0; y = y0; z = z0;

        for (i = 0; i < MAXBITS; ++i) {

            if (vecmode >= 0 && y < 0 || vecmode<0  && z >= 0) {
                y = y + x*t;
                z = z - t;
            }
            else {
                y = y - x*t;
                z = z + t;
            }
            t /= 2;
        }

        x0 = x;
        y0 = y;
        z0 = z;
    }

    /** Linear features ***********************************************/

    public double mulCordic(double a, double b)
    {
        double x, y, z;
        x = a;
        y = 0;
        z = b;
        cordit0(x, y, z, -1);
        return y;
    }

    public double divCordic(double a, double b)
    {
        double x, y, z;
        x = b;
        y = a;
        z = 0;
        cordit0(x, y, z, 0);
        return z;
    }

    /** circular features ***********************************************/

    public static double gain1Cordic()
    {
        /* compute gain by evaluating cos(0) without inv gain */
        double x, y, z;

        x = 1;
        y = 0;
        z = 0;
        cordit1(x, y, z, -1);
        return x;
    }

    public double atanCordic(double a)
    {
        /* domain: all a */
        double x = 1;
        double z = 0;
        cordit1(x, a, z, 0);
        return z;
    }

    public double sincosCordic(double a, double cosp)
    {
        /* |a| < 1.74 */
        double sinp = 0;
        cosp = invGain1;
        cordit1(cosp, sinp, a, -1);
        return sinp;
    }

    public double tanCordic(double a)
    {
        /* |a| < 1.74 */
        double sinp = 0;
        double cosp = invGain1;
        cordit1(cosp, sinp, a, -1);
        return divCordic(sinp, cosp);
    }

    public double asinCordic(double a)
    {
        /* |a| < 0.98 */
        double x, y, z;
        

        x = invGain1;
        y = 0;
        z = 0;

        int neg = 1;
        if (a < 0) {
            a = -a;
            neg = 0;
        }
            

        cordit1(x, y, z, a);

        if (neg!=1) z = -z;
        return z;
    }

    /** hyperbolic features ********************************************/

    public double gain2Cordic()
    {
        /* calculate hyperbolic gain */
        double x, y, z;
        x = 1;
        y = 0;
        z = 0;
        cordit2(x, y, z, -1);
        return x;
    }

    public double sinhcoshCordic(double a, double coshp)
    {
        /* |a| < 1.13 */
        double y;
        coshp = invGain2;
        y = 0;
        cordit2(coshp, y, a, -1);
        return y;
    }

    public double tanhCordic(double a)
    {
        /* |a| < 1.13 */
    double sinhp, coshp;
    coshp = invGain2;
    sinhp = 0;
    cordit2(coshp, sinhp, a, -1);
    return divCordic(sinhp,coshp);
    }

    public double atanhCordic(double a)
    {
        /* |a| < 1.13 */
        double x, z;
        x = 1;
        z = 0;
        cordit2(x, a, z, 0);
        return z;
    }

    public double logCordic(double a)
    {
        /* 0.1 < a < 9.58 */
        double x, y, z;

        x = a + 1;
        y = a - 1;
        z = 0;
        cordit2(x, y, z, 0);
        return 2*z;
    }

    public double sqrtCordic(double a)
    {
        /* 0.03 < a < 2 */
        double x, y, z;

        x = a + 0.25;
        y = a - 0.25;
        z = 0;
        cordit2(x, y, z, 0);
        return mulCordic(x, invGain2);
    }

    public double expCordic(double a)
    {
        double sinhp, coshp;
        coshp = invGain2;
        sinhp = 0;
        cordit2(coshp, sinhp, a, -1);
        return sinhp + coshp;
    }
}    

    public class TestTrigo{
        public static void main(String[] args ){

            double v;
            double x;
            double c;
            Trigo a = new Trigo();

            a.initCordic();

            x = 1;
            v = a.atanCordic(x);
            System.out.println("atan "+ x +" = " + v);

            x = 1;
            c = a.invGain1;
            v = a.sincosCordic(x, c);
            System.out.println("sin" + x +" =  " + v);
            System.out.println("cos" + x +" = " + v);

            // x = 1;
            // v = tanCordic(x);
            // printf("tan %f = %.18e\n", x, v);

            // x = 0.5;
            // v = asinCordic(x);
            // printf("asin %f = %.18e\n", x, v);

            // x = 1;
            // v = sinhcoshCordic(x, c);
            // printf("sinh %f = %.18e\n", x, v);
            // printf("cosh %f = %.18e\n", x, c);

            // x = 1;
            // v = tanhCordic(x);
            // printf("tanhh %f = %.18e\n", x, v);

            // x = 0.5;
            // v = atanhCordic(x);
            // printf("atanh %f = %.18e\n", x, v);

            // x = 0.8;
            // v = logCordic(x);
            // printf("log %f = %.18e\n", x, v);

            // x = 2;
            // v = sqrtCordic(x);
            // printf("sqrt %f = %.18e\n", x, v);

            // x = 1;
            // v = expCordic(x);
            // printf("exp %f = %.18e\n", x, v);
        }
    }   

