import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	
	public static void main(String[] input) 
	{
		ReadFiles read = new ReadFiles();
		
		if(input.length != 6) 
		{
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Please run and specify the input in the following format: (specify the full path of files)");
			System.out.println(">java Main.java <L> <K> <TrainingSet_FilePath> <ValidationSet_FilePath> <TestSet_FilePath> <Print_Decision_Tree(yes or no)>");
			System.out.println("Or refer to 'Readme.txt' file for compiling the code");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.exit(0);
		}
		Pruning DT_pruner = new Pruning();
		DecisionTree DT = new DecisionTree();
		
		int L = Integer.parseInt(input[0]);
		int K = Integer.parseInt(input[1]);
		
		String trainingFile = input[2];
		String validationFile = input[3];
		String testFile = input[4];
		String display_Tree = input[5];
		//Storing the data in the form of Arraylist of Arraylist
		
		ArrayList<ArrayList<String>> trainingData = null;
		ArrayList<ArrayList<String>> validationData = null;
		ArrayList<ArrayList<String>> testData = null;
		
		
		
		try 
		{
			trainingData = read.fileData(trainingFile);
			validationData = read.fileData(validationFile);
			testData = read.fileData(testFile);
			
			//isVI = false, calculate Information gain heuristic using entropy.
			boolean isVI = false;
			
			
			ArrayList<String> attributes = trainingData.get(0);
			
			//create DT
			Tree tree_IG_entropy = DT.makeTree(attributes, trainingData, isVI);
			
			if(display_Tree.equalsIgnoreCase("yes")) 
			{
				System.out.println("\n\n");
				System.out.println("------------------Pre-Pruning------------------");
				System.out.println("1. Using Information Gain heuristic; the Decision Tree is");
				System.out.println("-----------------------------------------------");
			
				tree_IG_entropy.printTree();
			}
			
			isVI = true;
			Tree tree_IG_VI = DT.makeTree(attributes, trainingData, isVI);
			
			if(display_Tree.equalsIgnoreCase("yes")) 
			{
				System.out.println("\n\n");
				System.out.println("------------------Pre-Pruning------------------");
				System.out.println("2. Using Variance Impurity heuristic; the Decision Tree is");
				System.out.println("-----------------------------------------------");
				tree_IG_VI.printTree();
			}
			
			System.out.println("\n\n");
			System.out.println("------------------Pre-Pruning------------------");
			System.out.println("1. Using Information Gain heuristic; the Accuracy is " + DT.findAccuracy(tree_IG_entropy, testData));
			System.out.println("-----------------------------------------------");
			
			System.out.println("\n\n");
			System.out.println("------------------Pre-Pruning------------------");
			System.out.println("2. Using Variance Impurity heuristic; the Accuracy is " + DT.findAccuracy(tree_IG_VI, testData));
			System.out.println("-----------------------------------------------");
			
			Tree prunedTreeFromIG = DT_pruner.prune(tree_IG_entropy, L, K, validationData);
			
			if(display_Tree.equalsIgnoreCase("yes")) 
			{
				System.out.println("\n\n");
				System.out.println("------------------Post-Pruning------------------");
				System.out.println("1. Using Information Gain heuristic; the Decision Tree is");
				System.out.println("-----------------------------------------------");
			
				prunedTreeFromIG.printTree();
			}
			
			DT_pruner = new Pruning();
			Tree prunedTreeFromVI = DT_pruner.prune(tree_IG_VI, L, K, validationData);
			
			if(display_Tree.equalsIgnoreCase("yes")) 
			{
				System.out.println("\n\n");
				System.out.println("------------------Post-Pruning------------------");
				System.out.println("2. Using Variance Impurity heuristic; the Decision Tree is");
				System.out.println("-----------------------------------------------");
			
				prunedTreeFromVI.printTree();
			}
			System.out.println("\n\n");
			System.out.println("------------------Post-Pruning------------------");
			System.out.println("1. Using Information Gain heuristic; the Accuracy is " + DT.findAccuracy(prunedTreeFromIG, testData));
			
			System.out.println("-----------------------------------------------");
			
			
			System.out.println("\n\n");
			System.out.println("------------------Post-Pruning------------------");
			System.out.println("2. Using Variance Impurity heuristic; the Accuracy is " + DT.findAccuracy(prunedTreeFromVI, testData));
		
			System.out.println("-----------------------------------------------");
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
	}

}
