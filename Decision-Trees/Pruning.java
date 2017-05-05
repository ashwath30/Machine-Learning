import java.util.ArrayList;
import java.util.Random;

public class Pruning 
{
	Tree finalTree;
	DecisionTree DT;
	Tree tentative;
	Pruning() 
	{
		DT = new DecisionTree();
	}
	
	public void swap(Tree root, int P) 
	{
		if(!root.isLeaf()) 
		{
			if(root.getNodeNo() == P) 
			{

				String 	modifiedLeafName = DT.majorityClass(root);
				root.setLeafName(modifiedLeafName);
				root.setIsLeaf(true);
				root.setLeftNode(null);
				root.setRightNode(null);
			}
			else 
			{
				swap(root.getLeftNode(), P);
				swap(root.getRightNode(), P);
			}
		}
	}
	
	public void copy(Tree tree, Tree finalTree) 
	{
		finalTree.setIsLeaf(tree.isLeaf());
		finalTree.setName(tree.getName());
		finalTree.setLeafName(tree.getLeafName());
		
		if(!tree.isLeaf()) 
		{
			finalTree.setLeftNode(new Tree());
			finalTree.setRightNode(new Tree());
			copy(tree.getLeftNode(), finalTree.getLeftNode());
			copy(tree.getRightNode(), finalTree.getRightNode());
		}
		
	}
	public Tree prune(Tree tree, int L, int K, ArrayList<ArrayList<String>> validationData) 
	{
		finalTree = new Tree();
		copy(tree, finalTree);
		double accFinal = DT.findAccuracy(finalTree, validationData);
		
		tentative = new Tree();
		for(int i = 1; i <= L; i++) 
		{
			copy(tree, tentative);
			Random random = new Random();
			int M = 1 + random.nextInt(K);
			for(int j = 0; j <= M; j++) 
			{
				int N = DT.nonLeafCount(tentative);
				
				if(N > 1) 
				{
					int P = random.nextInt(N) + 1;
					swap(tentative, P);
				} 
				else 
				
					break;
				
			}
			
			double accTentative = DT.findAccuracy(tentative, validationData);
			
			if(accFinal < accTentative) 
			{
				accFinal = accTentative;
				copy(tentative, finalTree);
			}
		}
		
		return finalTree;
	}

}
