Readme.txt file

---------------------------------------------------------------------------------------------------------------------------------------------------------------
Execution Steps:

Navigate to the path where the java files are present. Then in cmd, execute the following commands:-

Step 1: Compile the java files.

> javac *.java
(Please ignore the warnings that arise)

Step 2: Run the Main file. Please specify the arguments in the format mentioned below.

> java Main <ham_Trainingset_directory_path> <spam_Trainingset_directory_path> <ham_Testset_directory_path> <spam_Testset_directory_path> <Stopwords_FilePath> <learning-rate> <regularization-factor> <remove-stopwords?-yes-or-no>

----------------------------------------------------------------------------------------------------------------------------------------------------------------

Sample Input 1: 

(learning rate = 0.07 
regularization factor = 0.06
Remove Stopwords ? = NO)

> java Main D:\Sem2\ML\hw2_train\ham D:\Sem2\ML\hw2_train\spam D:\Sem2\ML\hw2_test\ham D:\Sem2\ML\hw2_test\spam D:\Sem2\ML\stopwords.txt 0.07 0.06 no

output 1:

Accuracy using Naive Bayes 72.80334728033473

Accuracy using Losigtic Regression 74.47698744769873


Sample Input 2: 

(learning rate = 0.07 
regularization factor = 0.06
Remove Stopwords ? = YES)

> java Main D:\Sem2\ML\hw2_train\ham D:\Sem2\ML\hw2_train\spam D:\Sem2\ML\hw2_test\ham D:\Sem2\ML\hw2_test\spam D:\Sem2\ML\stopwords.txt 0.07 0.06 yes

output 2:

Accuracy using Naive Bayes 72.80334728033473

Accuracy using Losigtic Regression 78.45188284518828


---------------------------------------------------------------------------------------------------------------------------------------------------------------



