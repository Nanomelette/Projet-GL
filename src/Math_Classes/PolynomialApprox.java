import java.lang.Math;

class Polynomial {

    Double dM_PI_4  = ((3.1415926535897932384626433832795/4.0));
    float M_PI_4 = dM_PI_4.floatValue();

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

    float FastArcTan(float x) {
    return M_PI_4*x - x*(java.lang.Math.abs(x) - 1)*(0.2447F + 0.0663F*java.lang.Math.abs(x));
    }

    float A = 0.0776509570923569f;
    float B = -0.287434475393028f;
    float C = (M_PI_4 - A - B);


    float Fast2ArcTan(float x) {
    float xx = x * x;
    return ((A*xx + B)*xx + C)*x;
    }

    float ArcSin(float x){
        float a = (1f + sqrt(1f-x*x));
        return 2 * Fast2ArcTan(x/a);
    }

    float ArcCos(float x){
        float a = sqrt(1f-x*x);
        float b = 1f + x;
        return 2 * Fast2ArcTan(a/b);
    }



}

public class PolynomialApprox {
    public static void main(String[] args ) {
        Polynomial p = new Polynomial();
        float x = 0.5f;
        float y_fast1 = p.FastArcTan(x);
        float y_fast2 = p.Fast2ArcTan(x);
        float asin = p.ArcSin(x);
        float acos = p.ArcCos(x);
        System.out.println("FastArctan : " + y_fast1 + " | résultat attendu : 0.46364760");
        System.out.println("Fast2Arctan : " + y_fast2 + " | résultat attendu : 0.46364760");
        System.out.println("Arcsin : " + asin + " | résultat attendu : 0.52359877") ;
        System.out.println("Arccos : " + acos + " | résultat attendu : 1.04719755");
        
    }
}
