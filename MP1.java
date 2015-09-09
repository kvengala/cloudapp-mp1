import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    Map<String,Integer> hashmap = new HashMap<String,Integer>();
    String delimiters = " \t,;+[0-9]%'&\".?!-:@\\[\\]\\(\\)\\{\\}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(inputFileName));
    String Currline;
   // Pattern pattern = Pattern.compile(delimiters);
    
    while((Currline = br.readLine() )!=null)
             {  
                StringTokenizer st = new StringTokenizer(Currline,delimiters);
               String [] words = Currline.split(delimiters);
               
            while(st.hasMoreTokens())
            {
             String word = st.nextToken().toString();
        	   //	System.out.println(word);
	        if(hashmap.containsKey(word) )
                       	{ 
	        	 int num = hashmap.get(word);
	        	 num = num +1;
	        	 hashmap.put(word,num);
			}
		else{
			hashmap.put(word,1);
		    }
            }
        	
            }
            int j=0;
            String[] ret = new String[20];
 			for(Map.Entry<String,Integer> entry : hashmap.entrySet())
 			{
 				ret[j] = entry.getKey();
                                j++;
 				if(j >19)
                                   break;

 			}
 			
    		br.close();
    		return ret;
    }

    public static void main(String[] args) throws Exception {
    	File file = new File("./output.txt");
    	file.createNewFile();
    	
    	
    	
        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true); 
        
    	BufferedWriter bw = new BufferedWriter(fw);
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
            	bw.write(item+"\n");
            	//bw.close();
                System.out.println(item);
            }
           bw.close();
        }
    }
}
