import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DecisionTree 
{
	int notLeaves = 0;
	public Tree makeTree(ArrayList<String> attributes, ArrayList<ArrayList<String>> dataSet, boolean isVI) 
	{
		int zeros = 0; int ones = 0; 
		
		for(int i = 1; i < dataSet.size(); i++) 
		{
			if(dataSet.get(i).get(dataSet.get(i).size() - 1).equalsIgnoreCase("0")) //for Class column
				zeros++;
			else
				ones++;
		}
		
		if(attributes.isEmpty() || zeros == dataSet.size() - 1)  //if all are zeros
			return new Tree("0");
		
		else if(attributes.isEmpty() || ones == dataSet.size() - 1) 
			return new Tree("1");
		
		else 
		{
			Gain IG = new Gain();
			String best = IG.nextAttr(dataSet, attributes, isVI);
			
			
			HashMap<String, ArrayList<ArrayList<String>>> bestAttrMap =  IG.getMapDataForBestAttr(best, dataSet);
			
			
			ArrayList<String> newAttributes = new ArrayList<String>();
			for(String anAttribute : attributes) 
			{
				if(!anAttribute.equalsIgnoreCase(best)) 
					newAttributes.add(anAttribute);
				
			}
			
			if(bestAttrMap.size() < 2) 
			{
				String name = "0";
				if(zeros < ones) 
					name = "1";
				return new Tree(name);
			}	
			
			Tree left = makeTree(newAttributes, bestAttrMap.get("0"), isVI);
			Tree right = makeTree(newAttributes, bestAttrMap.get("1"), isVI);
			return new Tree(best, left, right);
		}
	}
	
	public boolean treeOutput(Tree tree, ArrayList<String> row, ArrayList<String> attributes) 
	{
		Tree ptr = tree;
		
		while(true) 
		{
			if(ptr.isLeaf()) 
			{
				if(ptr.getLeafName().equalsIgnoreCase(row.get(row.size() - 1))) 
					return true;
				else 
					return false;
			}
			
			int index = attributes.indexOf(ptr.getName());
			String val = row.get(index);
			if(val.equalsIgnoreCase("0")) 
				ptr = ptr.getLeftNode();
			 else 
				ptr = ptr.getRightNode();
			
		}
	}
	
	public double findAccuracy(Tree tree, ArrayList<ArrayList<String>> dataSet) 
	{
		int p = 0;
		ArrayList<String> attributes = dataSet.get(0);
		
		for(ArrayList<String> row : dataSet.subList(1, dataSet.size())) 
		{
			
			
			if(treeOutput(tree, row, attributes))
				p++;
		}
		
		double accuracy = ((double) p / (double) (dataSet.size() - 1)) * 100.00;
		return accuracy;
	}
	
	public void totalNonLeaves(Tree root) 
	{
		if(!root.isLeaf()) 
		{
			notLeaves++;
			root.setNodeNo(notLeaves);
			totalNonLeaves(root.getLeftNode());
			totalNonLeaves(root.getRightNode());
		}
	}
	
	public int nonLeafCount(Tree root) 
	{
		totalNonLeaves(root);
		int N = notLeaves;
		notLeaves = 0;
		return N;
	}
	
	public String majorityClass(Tree root) 
	{
		int zeros = 0;
		int ones = 0;
		
		List<Tree> leaves = getLeaves(root);
		for(Tree node : leaves) 
		{
			if(node.getLeafName().equalsIgnoreCase("1")) ones++;
			else zeros++;
		}
		
		String majority = "0";
		if(ones > zeros) 
			majority = "1";
		
		
		return majority;
	}
	
	public List<Tree> getLeaves(Tree root) 
	{
		List<Tree> leafList = new ArrayList<Tree>();
		if(root.isLeaf()) 
			leafList.add(root);
		
		
		else 
		{
			if(!root.getLeftNode().isLeaf()) 
				getLeaves(root.getLeftNode());
			
			if(!root.getRightNode().isLeaf()) 
				getLeaves(root.getRightNode());
			
		}
		return leafList;
	}
	
	

}
