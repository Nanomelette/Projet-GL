import java.lang.Math;

class Trigo {
    private static final int MAXBITS = 53;

    /* define this to perform all (non 2power) multiplications
    * and divisions with the cordic linear method.
    */

    /* these are the constants needed */
    float invGain1;
    float invGain2;
    static float[] atanTable = new float[MAXBITS];
    static float[] atanhTable = new float[MAXBITS];

    public void initCordic()
    {
        /* must call this first to initialise the constants.
        * of course, here i use the maths library, but the
        * values would be precomputed.
        */
        float t = 1;
        int i;
        for (i = 0; i < MAXBITS; ++i) {
            atanTable[i] = (float) java.lang.Math.atan(t);
            t /= 2;
            atanhTable[i] = 0.5f*((float)java.lang.Math.log((1+t)/(1-t)));
        }

        /* set constants */
        invGain1 = 1/gain1Cordic();
    }

    public void cordit1(Triple triple, float vecmode)
    {
        /* this is the circular method. 
        * one slight change from the other methods is the y < vecmode 
        * test. this is to implement arcsin, otherwise it can be
        * y < 0 and you can compute arcsin from arctan using
        * trig identities, so its not essential.
        */

        float t;
        float x, y, z;
        int i;

        t = 1;
        x = triple.getX(); y = triple.getY(); z = triple.getX();

        for (i = 0; i < MAXBITS; ++i) {
            float x1;

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

        triple.setX(x);
        triple.setY(y);
        triple.setZ(z);
    }

    public void cordit0(Triple triple, float vecmode)
    {

        /* the linear methods is the same as the circular but
        * ive simplified out the x iteration as it doesnt change.
        */
        float t;
        float x, y, z;
        int i;

        t = 1;
        x = triple.getX(); y = triple.getY(); z = triple.getX();

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

        triple.setX(x);
        triple.setY(y);
        triple.setZ(z);
    }

    /** Linear features ***********************************************/

    public float mulCordic(float a, float b)
    {
        Triple triple = new Triple();
        triple.setX(a);
        triple.setY(0);
        triple.setZ(b);
        cordit0(triple, -1);
        return triple.getY();
    }

    public float divCordic(float a, float b)
    {
        Triple triple = new Triple();
        triple.setX(b);
        triple.setY(a);
        triple.setZ(0);
        cordit0(triple, 0);
        return triple.getZ();
    }

    /** circular features ***********************************************/

    public float gain1Cordic()
    {
        /* compute gain by evaluating cos(0) without inv gain */
        Triple triple = new Triple();
        triple.setX(1);
        triple.setY(0);
        triple.setZ(0);
        cordit1(triple, -1);
        return triple.getX();
    }

    public float atanCordic(float a)
    {
        /* domain: all a */
        Triple triple = new Triple();
        triple.setX(1);
        triple.setY(a);
        triple.setZ(0);
        cordit1(triple, 0);
        return triple.getZ();
    }

    public float sincosCordic(float a, float cosp)
    {
        /* |a| < 1.74 */
        Triple triple = new Triple();
        triple.setX(invGain1);
        triple.setY(0);
        triple.setZ(a);
        cordit1(triple, -1);
        return triple.getY();
    }

    public float tanCordic(float a)
    {
        /* |a| < 1.74 */
        Triple triple = new Triple();
        triple.setX(invGain1);
        triple.setY(0);
        triple.setZ(a);
        cordit1(triple, -1);
        return divCordic(triple.getY(), triple.getX());
    }

    public float asinCordic(float a)
    {
        /* |a| < 0.98 */
        Triple triple = new Triple();
        triple.setX(invGain1);
        triple.setY(0);
        triple.setZ(0);

        int neg = 1;
        if (a < 0) {
            a = -a;
            neg = 0;
        }
            

        cordit1(triple, a);

        if (neg!=1) {
            triple.setZ(-triple.getZ());
        }
        return triple.getZ();
    }
}

    
public class TestTrigo{
        public static void main(String[] args ){

            float v;
            float x;
            float c;
            Trigo a = new Trigo();

            a.initCordic();

            x = 1;
            v = a.atanCordic(x);
            System.out.println("atan "+ x +" = " + v);

            x = 1;
            c = a.invGain1;
            v = a.sincosCordic(x, c);
            System.out.println("sin" + x +" =  " + v);
            System.out.println("cos" + x +" = " + c);

            // x = 1;
            // v = tanCordic(x);
            // System.out.println("tan " + x + "=" + v);

            // x = 0.5f;
            // v = asinCordic(x);
            // System.out.println("asin" + x + "=" + v);

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

