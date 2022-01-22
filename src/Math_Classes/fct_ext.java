class fct_ext {

    public float abs (float x) {
		if (x >= 0) {
			return x;
		}
		else {
			return -x;
		}
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

    //algorithme de Héron
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
	
}

public class TESTfct_ext{
	public static void main(String[] args){
		fct_ext fct = new fct_ext();
		System.out.println(fct.sqrt(2));
	}
}