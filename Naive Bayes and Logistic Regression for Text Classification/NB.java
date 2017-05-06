import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;


//list of emails
enum Class {
    spam, ham;
}

public class NB {

    
    double words_count_hamdocs = 0;
	double samples=0;
    double words_count_spamdocs = 0;
    double true_positive=0;
    double words_count = 0;
	TrainingSet tS;
	
    HashMap<String, LexCount> words = new HashMap(); 
    

    public NB(TrainingSet td) {
        tS = td;
    }

    //count lex occurances
    void lexhash(Email email) {
		int countBuffer;
        for (String s : email.pMsg.keySet()) {
            countBuffer = email.pMsg.get(s);
            
            if(email.emailClass==Class.ham)
                words_count_hamdocs=words_count_hamdocs+countBuffer;
            else
                words_count_spamdocs=words_count_spamdocs+countBuffer;
            
            
            if (words.containsKey(s)) {
                LexCount lc = words.get(s);
                long ham_Count = lc.hamOccurance_count;
                long spam_Count = lc.spamOccurance_count;

                if (email.emailClass == Class.ham) 
                    ham_Count+=countBuffer;
                 else 
                    spam_Count+=   countBuffer;
                
                words.put(s, new LexCount(ham_Count, spam_Count));
            } else {
                long ham_Count=0,spam_Count=0;
                if(email.emailClass==Class.ham)
                    ham_Count=countBuffer;
                else
                    spam_Count=countBuffer;
				
                words.put(s, new LexCount(ham_Count,spam_Count));
            }
        }
    }

	 
    //find the clss for the test email.
    
    Class findClass(Email email,boolean wantStopwords) {
        
        double pHAM = 0,log2 = Math.log(2),pSPAM = 0;
        long ham_Count, spam_Count;

        for (String s : email.pMsg.keySet()) {
            double n = email.pMsg.get(s);
            
            if (words.containsKey(s)) {
                LexCount lcTemp = words.get(s);
                ham_Count = lcTemp.hamOccurance_count;
                spam_Count = lcTemp.spamOccurance_count;
            } else {
                ham_Count = 0;spam_Count = 0;
            }
            pHAM = pHAM + ((Math.log(Math.pow(((ham_Count + 1) / (double) (words_count_hamdocs + words.size())), n))) / log2);
            pSPAM = pSPAM + ((Math.log(Math.pow(((spam_Count + 1) / (double) (words_count_spamdocs + words.size())), n))) / log2);
        }
        
        
		return (Math.log((tS.hamDocs / (double) (tS.hamDocs + tS.spamDocs)))) / log2 > (Math.log((tS.spamDocs / (double) (tS.hamDocs + tS.spamDocs)))) / log2 ? Class.ham : Class.spam;
			
    }

    
    void lexhash() {
        
			int hEmails = 0;
			while(hEmails < tS.Emails_ham.length)
			{
				lexhash(tS.Emails_ham[hEmails]);
				hEmails++;
			}
        
       
			int sEmails = 0;
			while(sEmails < tS.Emails_spam.length)
			{
		
				lexhash(tS.Emails_spam[sEmails]);
				sEmails++;
			}
        
    }

    
    void truePositive(String dirName, Class emailClass,boolean wantStopwords) throws IOException {
        Email testEmails[];
        File dir = new File(dirName);
        File files[] = dir.listFiles();
		int batchLimit;

        samples=samples + files.length;
        //double true_positive = 0;
        for (int j = 0; j < files.length;) {
            batchLimit = Math.min(20, files.length - j);
            testEmails = new Email[batchLimit];
            int k = 0;
            for (k = 0; k < batchLimit; k++) 
                testEmails[k] = new Email(files[j + k], emailClass);
            
            for (k = 0; k < batchLimit; k++) {
                testEmails[k].preProcess(tS.stopwords,wantStopwords);
                if (testEmails[k].emailClass == findClass(testEmails[k],wantStopwords)) 
                    true_positive++;
                
            }
            j = j + k;
        }
        
    }

   
    //test the emails
    void test(String hamPath, String spamPath,boolean wantStopwords) throws IOException {
        lexhash();
        
        truePositive(hamPath, Class.ham,wantStopwords);
        truePositive(spamPath, Class.spam,wantStopwords);
        
        System.out.println("\nAccuracy using Naive Bayes " + (true_positive / (double) samples)*100);
		System.out.println("\nThe number of iterations has been set to 5 by default for which Logistic Regression computation takes approximately 6 mins. Please wait for the output..");
    }
}
