import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class KMeans {

    public void compress(String inputImageFile, String outputFolder, int k) {
        try {
			
			double input_bytes,input_kilobytes,bytes,kilobytes;
            BufferedImage originalImage = ImageIO.read(new File(inputImageFile)); 
            Scanner s = new Scanner(System.in);
            
                BufferedImage kmeansJpg = kmeans_helper(originalImage, k);
                ImageIO.write(kmeansJpg, "jpg", new File(outputFolder + "\\K=" + k + ".jpg"));
                kmeansJpg.flush();
				
				File originalFile = new File(inputImageFile);
				File compressedFile = new File(outputFolder + "\\K=" + k + ".jpg");
				
					 input_bytes = originalFile.length();
					 input_kilobytes = (input_bytes / 1024);
					
				
				
				if(compressedFile.exists()){
					
					 bytes = compressedFile.length();
					 kilobytes = (bytes / 1024);
					 
					 
					 System.out.println("\nFor the ouput image, please see the folder "+outputFolder );
					
					if(inputImageFile.indexOf("Koala") > -1)
					{
						
						System.out.println("\nSize of the input(original) Koala.jpg image is " + input_kilobytes+" KB");
						System.out.println("Size of the output(compressed) Koala image for k=" +k +" is " +kilobytes+ " KB");
						System.out.println("\nImage Compression Ratio = Original Image Size / Compressed Image Size = " +input_kilobytes/kilobytes);
					}
				
					else
					{
						
						System.out.println("\nSize of the input(original) Penguins.jpg is " + input_kilobytes+" KB");
						System.out.println("Size of the output(compressed) Penguins image for k="+k+" is "+kilobytes+" KB");
						System.out.println("\nImage Compression Ratio = Original Image Size / Compressed Image Size = " +input_kilobytes/kilobytes);
					}
						
				}
				
				
            //}
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

    }

    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k) {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        BufferedImage kmeansImage = new BufferedImage(w, h, originalImage.getType());
        Graphics2D g = kmeansImage.createGraphics();
        g.drawImage(originalImage, 0, 0, w, h, null);
        
        int[] rgb = new int[w * h];
        int count = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                rgb[count++] = kmeansImage.getRGB(i, j);
            }
        }
        
        kmeans(rgb, k);

        
        count = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) 
                kmeansImage.setRGB(i, j, rgb[count++]);
            
        }
        return kmeansImage;
    }

    private static int dist(int rgbValue, int centroidValue) {

        Color point = new Color(rgbValue);
        Color centroid = new Color(centroidValue);
        double blue = Math.pow((point.getBlue() - centroid.getBlue()), 2);
        double red = Math.pow((point.getRed() - centroid.getRed()), 2);
        double green = Math.pow((point.getGreen() - centroid.getGreen()), 2);
        return (int) Math.sqrt((blue + red + green));
    }

    private static void getCentroid(int rgb[], int[] mu,int clusterNumberForPoints[], int k) {
        for (int j = 0; j < k; j++) {
            int[] s = new int[3];
            int numberOfPointsInCluster = 0;
            for (int i = 0; i < rgb.length; i++) {
                if (clusterNumberForPoints[i] == j) { 
                    int colorValue = rgb[i];
                    Color color = new Color(colorValue);
                    s[0] = s[0] + (color.getRed());
                    s[1] = s[1] + (color.getBlue());
                    s[2] = s[2] + (color.getGreen());
                    numberOfPointsInCluster++;
                }
            }

            if (numberOfPointsInCluster != 0) {
                Color color = new Color(s[0] / numberOfPointsInCluster, s[1] / numberOfPointsInCluster, s[2] / numberOfPointsInCluster);
                mu[j] = color.getRGB();
            }
        }

    }

    private static void kmeans(int[] rgb, int k) {

        int mu[] = new int[k];
        Random random = new Random();
        boolean dummyHash[] = new boolean[rgb.length];

        for (int i = 0; i < k;) {
            int r = random.nextInt(rgb.length);
            if (!dummyHash[r]) {
                mu[i] = rgb[r];
                dummyHash[r] = true;
                i++;
            }
        }

        int clusterNumberForPoints[] = new int[rgb.length];
        for (int iterator = 0; iterator < 100; iterator++) {

            for (int i = 0; i < rgb.length; i++) {
                int min = Integer.MAX_VALUE;
                int clusterNumber = -1;
                for (int j = 0; j < k; j++) {
                    int temp = dist(rgb[i], mu[j]);
                    if (temp < min) {
                        min = temp;
                        clusterNumber = j;
                    }
                }
                clusterNumberForPoints[i] = clusterNumber;
            }
            getCentroid(rgb, mu, clusterNumberForPoints, k);
        }
        for (int i = 0; i < rgb.length; i++) 
            rgb[i] = mu[clusterNumberForPoints[i]];
        
    }
}
