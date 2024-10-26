package arrays;

public class Maximum {

	public static void main(String[] args) {
		int a[] = {6,3,8,65,98,2};
		int max1 = a[0];
		int max2 = a[0];
		
		for(int i = 1; i<a.length; i++) {
			if(a[i] > max1) {
				max1 = a[i];
			}
		}System.out.println("highest value: " +max1);
		
		for(int i = 0; i<a.length; i++) {
			if(a[i] > max2 && a[i] != max1) {
				max2 = a[i];
			}
			
		}System.out.println("Second highest value: " +max2);
	
	}

}
