
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class Gain 
{
	HashMap<String, ArrayList<String>> attribute_map;
	HashMap<String, Double> attributeIG_map;
	public HashMap<String, ArrayList<String>> map_attributes(ArrayList<ArrayList<String>> dataSet) 
	{
		HashMap<String, ArrayList<String>> theAttributeMap = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> attr_arraylist = dataSet.get(0);
		
		for(int i = 0; i < attr_arraylist.size(); i++) 
		{
			for(int j = 1; j < dataSet.size(); j++) 
			{
				if(theAttributeMap.containsKey(attr_arraylist.get(i))) 
					theAttributeMap.get(attr_arraylist.get(i)).add(dataSet.get(j).get(i));
				
				else 
				{
					ArrayList<String> val = new ArrayList<String>();
					val.add(dataSet.get(j).get(i));
					theAttributeMap.put(attr_arraylist.get(i), val);
				}
			}
		}
		return theAttributeMap;
	}
	
	public double get_Entropy(double positive_total, double negative_total) 
	{
		
		if(positive_total == negative_total) 
			return 1;
		
		
		else if(positive_total == 0 || negative_total == 0) 
			return 0;
		
		
		else 
		{
			double positive_prob = positive_total / (positive_total + negative_total);
			double negative_prob = negative_total / (positive_total + negative_total);
			
			double entropy = ((-positive_prob) * (Math.log(positive_prob) / Math.log(2))) + ((-negative_prob) * (Math.log(negative_prob) / Math.log(2)));
			return entropy;
		}
	}
	
	public double get_VI(double positive_total, double negative_total) 
	{
		
		if(positive_total == negative_total) 
			return 1;
		
		
		else if(positive_total == 0 || negative_total == 0) 
			return 0;
		
		
		else 
		{
			double positive_prob = positive_total / (positive_total + negative_total);
			double negative_prob = negative_total / (positive_total + negative_total);
			
			double VI = positive_prob * negative_prob;
			return VI;
		}
	}
	
	
	
	public double get_attributeIG(double posClass, double negClass, double zeroClassPos, double zeroClassNeg, double oneClassPos, double oneClassNeg, boolean isVI) 
	{
		double entropy_class;
		double entropy_zero;
		double entropy_one;
		double IG;
		
		double classCount = posClass + negClass;
		double zeroClassCount = zeroClassPos + zeroClassNeg; 
		double oneClassCount = oneClassPos + oneClassNeg;
		
		
		if(isVI == false) 
		{
			 entropy_class = get_Entropy(posClass, negClass); //H(S)
			 entropy_zero = get_Entropy(zeroClassPos, zeroClassNeg); //H(0)
			 entropy_one = get_Entropy(oneClassPos, oneClassNeg); //H(1)
		}
		else 
		{
			 entropy_class = get_VI(posClass, negClass);
			 entropy_zero = get_VI(zeroClassPos, zeroClassNeg);
			 entropy_one = get_VI(oneClassPos, oneClassNeg);
		}
		
		IG = entropy_class - (((zeroClassCount / classCount) * entropy_zero) +
				((oneClassCount / classCount) * entropy_one));
		
		return IG;
	}
	
	public String nextAttr(ArrayList<ArrayList<String>> data, ArrayList<String> attributes, boolean isVI) 
	{
		String best = "";
		attributeIG_map = new HashMap<String, Double>();
		attribute_map = map_attributes(data);
		
		double posClass = 0; //1 is p
		double negClass = 0; //0 is neg
		
		for(String str : attribute_map.get("Class")) 
		{
			if(str.equalsIgnoreCase("1")) 
				posClass++; //find 1 count
			
			else 
				negClass++; //find 0 count
			
		}
		
		//excluding the class attribute from the attribute list		
		for(String anAttribute : attributes.subList(0, attributes.size() - 1)) 
		{//start from XB
			ArrayList<String> dataSetOfAttr = attribute_map.get(anAttribute);
			double zeroClassPos = 0;
			double oneClassPos = 0;
			double zeroClassNeg = 0;
			double oneClassNeg = 0;
			
			for(int i = 0; i < dataSetOfAttr.size(); i++) 
			{
				if(dataSetOfAttr.get(i).equalsIgnoreCase("0")) 
				{ // If value of XB is 0
					if(attribute_map.get("Class").get(i).equalsIgnoreCase("1"))  // If Class (last column) val is 1 
						zeroClassPos++; //XB 0 and target 1 
					 else 
						zeroClassNeg++; //XB 0 and target 0
					
				} 
				else 
				{
					if(attribute_map.get("Class").get(i).equalsIgnoreCase("1")) 
						oneClassPos++; //XB 1 and target 1
					 else 
						oneClassNeg++; // XB 1 and target 0
					
				}
			}
			
			Double attrInformationGain = get_attributeIG(posClass, negClass, zeroClassPos, zeroClassNeg, oneClassPos, oneClassNeg, isVI);
			attributeIG_map.put(anAttribute, attrInformationGain); // attributeIG_map stores attribute and IG pair.
		}
		
		ArrayList<Double> attrValuesList = new ArrayList<Double>(attributeIG_map.values()); //take values alone
		Collections.sort(attrValuesList);
		Collections.reverse(attrValuesList);
		
		for(String key : attributeIG_map.keySet())
		{
			if(attrValuesList.get(0).equals(attributeIG_map.get(key))) 
			{
				best = key;
				break;
			}
		}
		return best;
	}
	
	public HashMap<String, ArrayList<ArrayList<String>>> getMapDataForBestAttr(String best, ArrayList<ArrayList<String>> dataSet) 
	{
		HashMap<String, ArrayList<ArrayList<String>>> bestAttrMap = new HashMap<String, ArrayList<ArrayList<String>>>();
		int bestAttrIdx = dataSet.get(0).indexOf(best);
		
		
		for(int i = 1; i < dataSet.size(); i++) 
		{
			if(dataSet.get(i).get(bestAttrIdx).equalsIgnoreCase("0")) 
			{
				if(bestAttrMap.containsKey("0")) 
				
					bestAttrMap.get("0").add(dataSet.get(i));
				
				else 
				{
					ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
					dataList.add(dataSet.get(0));
					dataList.add(dataSet.get(i));
					bestAttrMap.put("0", dataList);
				}
			}
			else 
			{
				if(bestAttrMap.containsKey("1")) 
					bestAttrMap.get("1").add(dataSet.get(i));
				
				else 
				{
					ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
					dataList.add(dataSet.get(0));
					dataList.add(dataSet.get(i));
					bestAttrMap.put("1", dataList);
				}
			}
		}
		
		return bestAttrMap;
	}

}
