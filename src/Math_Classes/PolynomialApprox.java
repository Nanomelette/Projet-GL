import java.lang.Math;

import java.util.ArrayList;

class Polynomial {

    float M_PI_4  = ((3.1415926535897932384626433832795f/4.0f));


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

    // float FastArcTan(float x) {
    // return M_PI_4*x - x*(java.lang.Math.abs(x) - 1)*(0.2447F + 0.0663F*java.lang.Math.abs(x));
    // }

    // float A = 0.0776509570923569f;
    // float B = -0.287434475393028f;
    // float C = (M_PI_4 - A - B);


    // float Fast2ArcTan(float x) {
    // float xx = x * x;
    // return ((A*xx + B)*xx + C)*x;
    // }

    // float PolyArcTan(float x) {
    //     float xx = x*x;
    //     float a = 0.5f;
	// 	float b = 0.2f;
	// 	float c = 0.1428571f;
	// 	float d = 0.1111111f;
    //     return x*(1-xx*(a+xx*(b-xx*(c+xx*d))));
    // }

    float ArcSin(float x){
        float a = (1f + sqrt(1f-x*x));
        return 2 * atan_poly(x/a);
    }


    float ArcCos(float x){
        float a = sqrt(1f-x*x);
        float b = 1f + x;
        return 2 * atan_poly(a/b);
    }

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

    float fmaf(float a, float b, float c){
        return a*b+c;
    }

    float atan_poly (float a) {
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

    float abs(float x) {
        if(x>=0){
            return x;
        }
        else{
            return -x;
        }
    }

    float sinf_poly (float a, float s) {
        float r, t;

        r =              0x1.80a000p-19f;  //  2.86567956e-6
        r = fmaf (r, s, -0x1.a0690cp-13f); // -1.98559923e-4
        r = fmaf (r, s,  0x1.111182p-07f); //  8.33338592e-3
        r = fmaf (r, s, -0x1.555556p-03f); // -1.66666672e-1
        t = fmaf (a, s, 0.0f); // ensure -0 is passed through
        r = fmaf (r, t, a);
        return r;
    }

    float cosf_poly (float x){
 
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
        // float r;
        // r =         0x1.9a8000p-16f; //  2.44677067e-5
        // r = r * s - 0x1.6c0efap-10f; // -1.38877297e-3
        // r = r * s + 0x1.555550p-05f; //  4.16666567e-2
        // r = r * s - 0x1.000000p-01f; // -5.00000000e-1
        // r = r * s + 0x1.000000p+00f; //  1.00000000e+0
        // return r;
    //}

    float my_sinf (float a){
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

    
}

public class PolynomialApprox {
    public static void main(String[] args ) {
        Polynomial p = new Polynomial();
        float x;
        ArrayList<Float> listFloat = new ArrayList<Float>();
        ArrayList<Float> arrayListAtan = new ArrayList<Float>();
        ArrayList<Float> arrayListRAtan = new ArrayList<Float>();
        ArrayList<Float> arrayListCos = new ArrayList<Float>();
        ArrayList<Float> arrayListRCos = new ArrayList<Float>();
        ArrayList<Float> arrayListAsin = new ArrayList<Float>();
        ArrayList<Float> arrayListRAsin = new ArrayList<Float>();
        ArrayList<Float> err = new ArrayList<Float>();
        for(x=-3.14f; x<=3.14f; x = x + 0.01F ){
            arrayListAtan.add(p.cosf_poly(x));
            arrayListRAtan.add((float)Math.cos(x));
            err.add(Math.abs(p.cosf_poly(x)-((float)Math.cos(x))));
            listFloat.add(x);
        }
        System.out.println(arrayListAtan.toString());
        System.out.println(arrayListRAtan.toString());
        System.out.println(listFloat.toString());
        // System.out.println(arrayListAsin.toString());
        // System.out.println(arrayListRAsin.toString());
        //System.out.println(arrayListCos.toString());
        // System.out.println(arrayListRCos.toString());
        System.out.println(err.toString());
      
        
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
