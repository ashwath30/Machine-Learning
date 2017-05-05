import java.io.File;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFiles 
{
	
	public ArrayList<ArrayList<String>> fileData(String fileName) throws IOException 
	{
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>> ();
		File f = new File(fileName);
		Scanner sc=new Scanner(f);
		
		while(sc.hasNext()) 
		{
			String[] eachRow = sc.next().split(",");
			data.add(new ArrayList<String>(Arrays.asList(eachRow)));
		}
		sc.close();
		return data;
	}
	
	
}
