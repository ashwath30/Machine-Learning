import java.util.Set;

public class Tree {
	private String name;
	private Tree left;
	private Tree right;
	private String leaf;
	private boolean isLeaf;
	
	private static int level = -1;
	private Set<String> attributes;
	private int nodeNo; 
	
	public Tree() 
	{
		super();
	}
	
	public Tree(String leaf) 
	{
		this.leaf = leaf;
		this.setIsLeaf(true);
	}
	
	public Tree(String name, Tree left, Tree right) 
	{
		this.name = name;
		this.setIsLeaf(false);
		this.left = left;
		this.right = right;
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean val) {
		this.isLeaf = val;
	}
	
	public Tree getLeftNode() {
		return left;
	}
	
	public void setLeftNode(Tree left) {
		this.left = left;
	}
	
	public Tree getRightNode() {
		return right;
	}
	
	public void setRightNode(Tree right) {
		this.right = right;
	}
	
	public Set<String> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLeafName() {
		return leaf;
	}
	
	public void setLeafName(String leaf) {
		this.leaf = leaf;
	}
	
	public int getNodeNo() {
		return nodeNo;
	}
	
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}
	
	public void printTree()
	{
		level++;
		if(this.name == null) 
			System.out.print(" : " + leaf);
		
		else 
		{
			System.out.println();
			for(int i = 0; i < level; i++) 
				System.out.print(" | ");
			
			System.out.print(name + " = 0");
		}
		
		if(left != null) 
		{
			left.printTree();
			if(this.name == null) 
				System.out.print(" : " + leaf);
			
			else 
			{
				System.out.println();
				for(int i = 0; i < level; i++) 
					System.out.print(" | ");
				
				System.out.print(name + " = 1");
			}
			right.printTree();
		}
		level--;
	}

}
