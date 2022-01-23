import java.lang.Math;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

class Polynomial {

    /*
    *   Returns the exponant of a float
    *   For more information about the exponent, you can check : https://www.keil.com/support/man/docs/c51/c51_ap_floatingpt.htm 
    *
    */
    public float getExponent(float x){
        float exp = 0;
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
 
        float res = 1f;
        float sign = 1, fact = 1, pow = 1;
        for (float i = 1; i < 5; i++) {
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
    *
    */
    public float atan(float a) {
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
    *   Returns the Arcsin value of a float.
    *
    */
    public float arcsin(float x){
        if(x > 1f || x < -1f){
            return 0;
        }
        float a = (1f + sqrt(1f-x*x));
        return 2 * atan_poly(x/a);
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
        // Polynomial p = new Polynomial();
        // float x;
        // ArrayList<Float> listFloat = new ArrayList<Float>();
        // ArrayList<Float> listUlp = new ArrayList<Float>();
        // ArrayList<Float> listMathUlp = new ArrayList<Float>();
        // float a = p.ArcSin(1.5f);
        // ArrayList<Float> arrayListAtan = new ArrayList<Float>();
        // ArrayList<Float> arrayListRAtan = new ArrayList<Float>();
        // ArrayList<Float> arrayListCos = new ArrayList<Float>();
        // ArrayList<Float> arrayListRCos = new ArrayList<Float>();
        // ArrayList<Float> arrayListAsin = new ArrayList<Float>();
        // ArrayList<Float> arrayListRAsin = new ArrayList<Float>();
        // ArrayList<Float> err = new ArrayList<Float>();
        // for(x=-3f; x<=3; x = x + 0.1F ){
        //     listUlp.add(p.ulp(x));
        //     listMathUlp.add(Math.ulp(x));
        //     listFloat.add(x);
        // }
        // System.out.println(listUlp.toString());
        // System.out.println(listMathUlp.toString());
        //System.out.println(listFloat.toString());
        // System.out.println(arrayListAsin.toString());
        // System.out.println(arrayListRAsin.toString());
        //System.out.println(arrayListCos.toString());
        // System.out.println(arrayListRCos.toString());
        //System.out.println(err.toString());
      
        
        // float poly = p.atan_poly(x);
        // float asin = p.ArcSin(x);
        // float acos = p.ArcCos(x);
        // float cos = p.cosf_poly(x);
        // float sin = p.my_sinf(x);
        // System.out.println("FastArctan : " + y_fast1 + " | résultat attendu : 0.46364760");
        // System.out.println("Fast2Arctan : " + y_fast2 + " | résultat attendu : 0.46364760");
        // System.out.println("PolyArctan : " + poly + " | résultat attendu : " + Math.atan(x));
        // System.out.println("Arcsin : " + asin + " | résultat attendu : " + Math.asin(x)) ;
        // //System.out.println("Arccos : " + acos + " | résultat attendu : " + Math.acos(x));
        // System.out.println("cos : " + cos + " | résultat attendu : " + Math.cos(x));
        // System.out.println("sin : " + sin + " | résultat attendu : " + Math.sin(x));
        // System.out.println(Math.ulp(y_fast2));
        // System.out.println(Math.ulp(y_fast1));
        // System.out.println("|atan-val_réélle| = " + Math.abs(poly-Math.atan(x)) + "  | nbr ulp = " + Math.abs(poly-Math.atan(x))/Math.ulp(x));
        // System.out.println("|asin-val_réélle| = " + Math.abs(asin-Math.asin(x)) + "  | nbr ulp = " + Math.abs(asin-Math.asin(x))/Math.ulp(x));
        // System.out.println("|sin-val_réélle|  = " + Math.abs(sin-Math.sin(x)) + "  | nbr ulp = " + Math.abs(Math.ulp(x)/(sin-Math.sin(x))));
        // System.out.println("|cos-val_réélle|  = " + Math.abs(cos-Math.cos(x)) + " | nbr ulp = " + Math.abs(Math.ulp(x)/(cos-Math.cos(x))));
        // System.out.println("ulp(x) = " + (Math.ulp(x)));
        
    }
}
