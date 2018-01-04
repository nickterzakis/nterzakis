import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class PasswordCracker
{
	static HashSet<String> dictionary = new HashSet<String>();
	static ArrayList<String> passwords = new ArrayList<String>();
	static ArrayList<String> strings = new ArrayList<String>();
	static HashMap<String, String> map = new HashMap<String, String>();
	static boolean weak = false;
	static boolean moderate = false;
	static boolean strong = false;
	
	public static void main (String args[]) throws Exception
	{
		if(args.length<=0)
		{
			System.out.println("You must enter a dictionary text file into the command line," +"\n" + "if you don't have it download it.");
			System.exit(0);
		}
		Scanner infile;
		String infileName = args[0];
		
		do
		{
			try{
				infile = new Scanner(new File(infileName));
				break;
			}
			catch(FileNotFoundException e){
				System.out.print("Bad filename, please enter a valid input file name: ");
				Scanner b = new Scanner(System.in);
				infileName = b.nextLine();
			}
		}while(true);
		
		String infileName2 = "usersandpass.txt";
		dictionary = loadDictionary(infileName);
		strings = loadAllStrings(dictionary);
		loadTextFile(infileName2);
		Scanner s = new Scanner(System.in);
		String response = "Y";

		while(response.equals("Y")|| response.equals("y"))
		{
			System.out.print("Enter a username: ");
			String u = s.next();
			Set<String> keys = map.keySet();
			for(String key: keys)
			{
				if(u.equals(key))
				{
					String password = map.get(key);
					for(String d: dictionary)
					{
						String e=md5(d.toLowerCase());
						if(e.equals(password))
						{
							passwords.add(d);
							weak = true;
							System.out.println("You have a weak password");
							break;
						}
					}
					for(int i=0; i<strings.size(); i++)
					{
						String n = strings.get(i);
						String m = md5(strings.get(i));
						if(m.equals(password))
						{
							passwords.add(n);
							moderate = true;
							System.out.println("You have a moderate password");
							break;
						}
						else
						{
							strong = true;
						}
					}
					for(int j=0; j<passwords.size();j++)
					{
						if(!password.equals(md5(passwords.get(j))))
							passwords.remove(passwords.get(j));
					}
				}
			}
			System.out.println("Password is: " + passwords);
			System.out.print("Enter another user? (Y/N): ");
			response = s.next();

		}
		s.close();
	}
	
	private static HashSet<String> loadDictionary( String infileName) throws Exception
	{																	// OPEN UP A SCANNER USING THE INCOMING FILENAME
		File file = new File(infileName);
		BufferedReader dFile = new BufferedReader(new FileReader(file));
		HashSet<String> strings = new HashSet<String>();
		while(dFile.ready())
		{
			strings.add(dFile.readLine());
		}
		return strings;
	}
	private static ArrayList <String> loadAllStrings( HashSet<String> dictionary) throws Exception
	{		
		FileWriter fw = new FileWriter("type2.txt");// OPEN UP A SCANNER USING THE INCOMING FILENAME
		for(String word: dictionary)
		{
			for(int i=0; i<4; i++)
			{
				if(i==0)
				{
					strings.add("$"+word+"%4");
				}
				if(i==1)
				{
					strings.add(word+"#@");
				}
				if(i==2)
				{
					strings.add("5&"+word);
				}
				if(i==3)
				{
					strings.add(word+"8");
				}
			}
		}
		return strings;
	}
	private static void loadTextFile( String infileName) throws Exception
	{																	// OPEN UP A SCANNER USING THE INCOMING FILENAME
		Scanner userPassScan = null;
		do{
			try
			{
				userPassScan = new Scanner(new File(infileName));
				break;
			}
			catch(FileNotFoundException e)
			{
				System.out.print("Run the registration program first");
				Scanner b = new Scanner(System.in);
				infileName = b.nextLine();
			}
		}while(true);

		while(userPassScan.hasNext())
		{
			map.put(userPassScan.next(),userPassScan.next());
		}
	}
	
	public static String md5(String input)
	{
		String md5 = null;
		if(null == input)	return null;
		
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1,digest.digest()).toString(16);
		}catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return md5;
	}
}