import java.io.IOException;
import java.util.Scanner;
import java.net.URLEncoder;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Scanner s= new Scanner(System.in);
        String outputFolder = args[1];
		int kvalue = Integer.parseInt(args[2]);	
        KMeans k = new KMeans();
		System.out.println("\nIf k-value is large, the program takes some time(approx 5 mins) to complete. Please be patient.");
        k.compress(args[0],outputFolder,kvalue);
    }
}
