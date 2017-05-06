

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Scanner;


public class TrainingSet {

    
     
    double hamDocs = 0, spamDocs = 0;

    HashSet<String> stopwords;       
	Email Emails_ham[], Emails_spam[];
    //get Training set
    void getData(String dirName, Class emailClass,boolean wantStopwords) throws IOException {
        File dir = new File(dirName);
        File files[] = dir.listFiles();
		int fileCount = files.length;
		
        if (emailClass == Class.ham) 
            Emails_ham = new Email[fileCount];
         else 
            Emails_spam = new Email[fileCount];
        
			int j=0;
			while(j < fileCount){
            if (emailClass == Class.spam) {
				spamDocs++;
                Emails_spam[j] = new Email(files[j], emailClass);
                Emails_spam[j].preProcess(stopwords,wantStopwords);
				
                
            } else {
                hamDocs++;
                Emails_ham[j] = new Email(files[j], emailClass);
                Emails_ham[j].preProcess(stopwords,wantStopwords);
            }
			j++;
        }
    }

   
    void train_weights(String hamPath,String spamPath,String stopWordsPath,boolean wantStopwords) throws IOException {
        if(!wantStopwords)
        {
            stopwords = new HashSet<>();
			
			BufferedReader br = new BufferedReader(new FileReader(stopWordsPath));
			String s = "";
			while ((s = br.readLine()) != null) 
				
				stopwords.add(s);
        
        }
		getData(hamPath, Class.ham, wantStopwords);
        getData(spamPath, Class.spam, wantStopwords);
    }

}
