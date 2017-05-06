import java.util.HashMap;
import java.util.HashSet;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Email {

    StringBuffer msg;
	Class emailClass;
    HashMap<String,Integer> pMsg;
    
    
	Email(File f,Class emailClass) throws IOException {
		pMsg=new HashMap<>();
        msg = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(f));
			String str = "";
			while ((str = br.readLine()) != null) 
				msg.append(str);
        this.emailClass=emailClass;
    }
	
	Email(StringBuffer s)
    {
        msg=new StringBuffer(s);
    }

    void preProcess(HashSet<String> stopwords, boolean isStopWords) {
        StringBuffer msg = this.msg;
		int i=0, stringCount;
		int l = msg.length();
		char character;
		String strtemp;
        while(i < l) {
            if (!((msg.charAt(i) >= 'A' && msg.charAt(i) <= 'Z') || (msg.charAt(i) >= 'a' && msg.charAt(i) <= 'z'))) 
                msg.replace(i, i + 1, " ");
			i++;
            
        }
        msg.append(" ");
		this.pMsg = new HashMap<>();
        StringBuffer strBuffer = new StringBuffer();
        
		int k=0;
			while(k < l){
            character = msg.charAt(k);
            if (character != ' ') 
                strBuffer.append(character);
            else {
                if (strBuffer.length() != 0)
                {
                    strtemp=strBuffer.toString().toLowerCase();
                    if (isStopWords || !stopwords.contains(strtemp)) {
                        if(this.pMsg.containsKey(strtemp))
                        {
                            stringCount=this.pMsg.get(strtemp);
                            this.pMsg.put(strtemp, stringCount+1);
                        }
                        else
                             this.pMsg.put(strtemp, 1);   
                    }
                    strBuffer = new StringBuffer();
                }
            }
			k++;
        }
    }
}
