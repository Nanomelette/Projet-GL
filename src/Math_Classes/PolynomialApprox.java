import java.lang.Math;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

class Polynomial {

    float pi = 3.141592653589793f;
    float pi2 = 6.28318530718f;

    /*
    *   Returns the exponant of a float
    *   For more information about the exponent, you can check : https://www.keil.com/support/man/docs/c51/c51_ap_floatingpt.htm 
    *
    */
    public int getExponent(float x){
        int exp = 0;
		if (x < 0) {
			return getExponent(-x);
		}
		if (x < 1) {
			while (x < 1) {
				exp = exp -1;
				x = x * 2;
			}
			return exp;
		}
		else {
			while (x >= 2) {
				exp = exp + 1;
				x = x/2;
			}
			return exp;
		}
    }

    /*
    *   
    *   Returns the square root of a float.
    *
    */
    public float sqrt(float x){
        float u = x;
		int i = 10;
		assert x>=0;
		if (x == 0) {
			return 0f;
		}
					
		while (i > 0) {
			float div = x/u;
			u = (u + div)*0.5f;
			i  = i-1;
		}
		return u;
    }

    /*
    *
    *   Returns a to the power of b (a^b).
    *
    */
    public float pow(float a, int b) {
		if (b < 0 ) {
			return pow(1/a, -b);
		}
        else if (b==0 ) {
			return 1;
		}
		else if ( b==1 ) {
			return a;
		}
		else if (b%2== 0) {
			return pow(a*a, b/2);
		}
		else {
			return a*pow(a*a ,(b-1)/2);
		}
		
	}

    /*
    *
    *   Multiply the first two values and add the third one.
    *
    */
    public float fmaf(float a, float b, float c){
        return a*b+c;
    }

    /*
    *
    *   Returns the absolute value of a float.
    *
    */
    public float abs(float x) {
        if(x>=0){
            return x;
        }
        else{
            return -x;
        }
    }

    /*
    *
    *   Returns the cosinus value of a float.
    *
    */
    public float cos(float x){

        //x = x%(2*pi); // as cos function is 2pi periodic, we bring the float value back in [0,2pi]
 
        float res = 1f;
        float sign = 1, fact = 1, pow = 1;
        for (float i = 1; i < 20; i++) {
            sign = sign * -1;
            fact = fact * (2 * i - 1) * (2 * i);
            pow = pow * x * x;
            res = res + sign * pow / fact;
        }
    
        return res;
    }

    /*
    *
    *   Returns the sinus value of a float.
    *
    */
    public float sin(float a){

        //a = a%(2f*pi); // as sin function is 2pi periodic, we bring the float value back in [0,2pi]
        
        float sinx, pterm;
        float i, sign=-1,n=20;
        sinx = a;
        pterm = a;
        for(i=1;i<=n;i++){
            sinx = sinx + sign*pterm*a*a/(2*i*(2*i+1));
            pterm = pterm * a* a /(2 * i * (2 * i + 1));
            sign = -1 * sign;
        }
        return sinx;
    }

    /*
    *
    *   Returns the arctan value of a float.
    *   Works only on [-1,1] or calls FastArctan;
    *
    */
    public float atan(float a) {
        if(a > 1f){
            return FastArctan(a);
        } 
        if(a < -1f){
            return FastArctan(a);
        }
        float s = a * a, u = fmaf(a, -a, 0x1.fde90cp-1f);
        float r1 =               0x1.74dfb6p-9f;
        float r2 = fmaf (r1, u,  0x1.3a1c7cp-8f);
        float r3 = fmaf (r2, s, -0x1.7f24b6p-7f);
        float r4 = fmaf (r3, u, -0x1.eb3900p-7f);
        float r5 = fmaf (r4, s,  0x1.1ab95ap-5f);
        float r6 = fmaf (r5, u,  0x1.80e87cp-5f);
        float r7 = fmaf (r6, s, -0x1.e71aa4p-4f);
        float r8 = fmaf (r7, u, -0x1.b81b44p-3f);
        float r9 = r8 * s;
        float r10 = fmaf (r9, a, a);
        return r10;
    }


    /*
    *
    *   Returns the arctan value of a float.
    *   Works only on [-1,1];
    *
    */
    float FastArctan(float x) {
        float sign = 1;
        if(x<0){
            sign = -1;
        }
        float A = 1.57079632679f;

        return A*sign - 4f*x/fmaf(4f,x*x,1);
    }


    /*
    *
    *   Returns the Arcsin value of a float.
    *
    */
    public float arcsin(float x){
        if(x > 1f || x < -1f){
            throw new Error("the valued entered for arcsin must be in [-1,1]");
        }
        else{
            float a = (1f + sqrt(1f-x*x));
            return 2 * atan(x/a); 
        }

    }

    /*
    *
    *   Returns the ulp value of a float.
    *
    */
    public float ulp(float x){
        int e;
		if (x ==0 ) {
			return 0;
		}
		else {
			e = getExponent(x);
			if (e >= -127) {
				return pow(2.0f, e-23);
			}
			
			else {
				return pow(2.0f, -127-23);
			}
			
		}
    }

    
}

public class PolynomialApprox {
    public static void main(String[] args ) {
        Polynomial p = new Polynomial();
        System.out.println(Math.cos(x));
        System.out.println(p.cos(x)-Math.cos(x));
        System.out.println(p.ulp(x));
        
        ArrayList<Float> listFloat = new ArrayList<Float>();
        ArrayList<Float> listUlp = new ArrayList<Float>();
        ArrayList<Float> arrayListCos = new ArrayList<Float>();
        ArrayList<Float> arrayListRCos = new ArrayList<Float>();
        ArrayList<Float> err = new ArrayList<Float>();
        for(x=-3.14f*2f; x<=3.14f*2f; x = x + 0.01F ){
            arrayListCos.add(p.atan(x));
            arrayListRCos.add((float)Math.atan(x));
            listFloat.add(x);
            listUlp.add(Math.ulp(x));
            err.add(Math.abs(p.atan(x)-(float)Math.atan(x)));
        }

        System.out.println(listFloat.toString());
        System.out.println(listUlp.toString());
        System.out.println(arrayListCos.toString());
        System.out.println(arrayListRCos.toString());
        System.out.println(err.toString());
        
    }
}
