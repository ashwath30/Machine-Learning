

import java.io.File;

import java.util.HashMap;
import java.io.IOException;


public class LR {

    TrainingSet tS;
    double learningRate;  
	HashMap<String, Double> features;	
    double samples = 0,reg,true_positive = 0;
    

    public LR(TrainingSet td, double learningRate, double reg) {
        tS = td;
		this.reg = reg;
        this.learningRate = learningRate;
        
    }

    //get distinct features.
    void features(Email emails[]) {
        features.put("", 0.0); 
        
			int i=0;
			while(i < emails.length){
            for (String s : emails[i].pMsg.keySet()) 
                if (!features.containsKey(s)) 
                    features.put(s, 0.0);
                
          i++;  
        }
    }
	
    

    //train the weights
    void train_weights() {
		boolean default_Feature;
		double hX,totalFeatures,next,featureWt;
        
		features = new HashMap<>();
        features(tS.Emails_ham);
        features(tS.Emails_spam);
        
		int t=0;
			while(t<5){
            for (String s : features.keySet()) {
                default_Feature = false;
                if (s.equals("")) 
                    default_Feature = true;
                
                double sum = 0;
                Email emails[] = tS.Emails_ham;
                
					int i1=0;
					while(i1 < emails.length){
                    hX = predictedVal(emails[i1]);
                    totalFeatures = 0;

                    if (emails[i1].pMsg.containsKey(s)) 
                        totalFeatures = emails[i1].pMsg.get(s);
                    
					sum += (!default_Feature) ? (1-hX) * totalFeatures : (1-hX) * 1;
					i1++;
                }
                emails = tS.Emails_spam;
                
					int i2=0;
					while(i2 < emails.length){
                    hX = predictedVal(emails[i2]);
                    totalFeatures = 0;

                    if (emails[i2].pMsg.containsKey(s)) 
                        totalFeatures = emails[i2].pMsg.get(s);
                    
                    
					sum += (!default_Feature) ? (0-hX) * totalFeatures : (0-hX) * 1;
					i2++;
                }
                next = learningRate / (double) (tS.hamDocs + tS.spamDocs);
                featureWt = features.get(s);
                if (!default_Feature) {
                    next = next * (sum + reg * featureWt);
                    featureWt += next;
                } else {
                    next *=  sum;
                    featureWt += next;
                }
                features.put(s, featureWt);
            }
			t++;
        }
    }
    

    //hX of LR
    double predictedVal(Email email) {
        double hX = features.get("");
        for (String s : email.pMsg.keySet()) {
            double featureWt = 0;
            if (features.containsKey(s)) 
                featureWt = features.get(s);
            //Sigma wi*xi
            hX += featureWt * email.pMsg.get(s);   
        }
        
        return (1 / (1 + Math.exp(-hX)));
    }

    

    
    void truepos(String dirName, Class emailClass, boolean wantStopwords) throws IOException {
        Email testEmails[];
        File dir = new File(dirName);
        File files[] = dir.listFiles();
        samples += files.length;
        
        for (int j = 0; j < files.length;) {
            int batchLimit = Math.min(20, files.length - j);
            testEmails = new Email[batchLimit];
            int k = 0;
            for (k = 0; k < batchLimit; k++) 
                testEmails[k] = new Email(files[j + k], emailClass);
            
            for (k = 0; k < batchLimit; k++) {
                testEmails[k].preProcess(tS.stopwords, wantStopwords);
				
				 
					if (testEmails[k].emailClass == (predictedVal(testEmails[k]) >=0.5 ? Class.ham : Class.spam))
                    true_positive++;
                
            }
            j = j + k;
        }
    }

    
     //test emails
    void test(String hamPath, String spamPath, boolean wantStopwords) throws IOException {
        
        truepos(hamPath, Class.ham, wantStopwords);
        
        truepos(spamPath, Class.spam, wantStopwords);
        System.out.println("\nAccuracy using Logistic Regression " + (true_positive / (double) samples)*100);
    }
}
