import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException,FileNotFoundException{
        
        if(args.length != 8) 
		{
			System.out.println("Please run using the following format:");
			System.out.println(">java Main <ham-training-folder-path> <spam-training-folder-path> <ham-test-folder-path> <spam-test-folder-path> <stopwords-file-path> <learningRate> <regularizationFactor> <Remove stopwords? Give yes OR no>");
			System.exit(0);
		}
		
			double learningRate = Double.parseDouble(args[5]);//0.07;
			double regularizationFactor = Double.parseDouble(args[6]);//0.06;
			boolean stopWords = (args[7].equalsIgnoreCase("yes")) ?  true : false; 
			
			if(args[7].equalsIgnoreCase("yes")) 
				System.out.println(" \nYou have chosen 'Without stopwords' option ");
				
				else System.out.println(" \nYou have chosen 'With stopwords' option ");
				
		 
			TrainingSet td= new TrainingSet();
			td.train_weights(args[0],args[1],args[4],stopWords);
			
			NB nb= new NB(td);
			nb.test(args[2],args[3],stopWords);
			
			LR lr= new LR(td,learningRate,regularizationFactor);
			lr.train_weights();
			lr.test(args[2],args[3],stopWords);
		
		
    }
}
	