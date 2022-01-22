import java.lang.Math;

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



}

public class PolynomialApprox {
    public static void main(String[] args ) {
        Polynomial p = new Polynomial();
        float x = 0.5f;
       
        
        float poly = p.atan_poly(x);
        float asin = p.ArcSin(x);
        float acos = p.ArcCos(x);
        // System.out.println("FastArctan : " + y_fast1 + " | résultat attendu : 0.46364760");
        // System.out.println("Fast2Arctan : " + y_fast2 + " | résultat attendu : 0.46364760");
        System.out.println("PolyArctan : " + poly + " | résultat attendu : 0.46364760");
        System.out.println("Arcsin : " + asin + " | résultat attendu : 0.52359877") ;
        System.out.println("Arccos : " + acos + " | résultat attendu : 1.04719755");
        // System.out.println(Math.ulp(y_fast2));
        // System.out.println(Math.ulp(y_fast1));
        System.out.println(abs(poly-Math.atan(x)));
        System.out.println(Math.ulp(x));
        
    }
}
