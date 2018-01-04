import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.*;

public class Registration
{
	public static ArrayList<String> users = new ArrayList<String>();
	public static ArrayList<String> passwords = new ArrayList<String>();
	public static HashSet<String> dictionary = new HashSet<String>();
	public static ArrayList<String> strings = new ArrayList<String>();
	static boolean weak = false;
	static boolean moderate = false;
	
	public static void main (String args[]) throws Exception
	{
		String infileName = "dictionary.txt";
		dictionary = loadDictionary(infileName);
		loadAllStrings(dictionary);
		boolean duplicate;
		Scanner scan = new Scanner(System.in);
		String response = "Y";
		while(response.equals("Y")||response.equals("y"))
		{
			System.out.print("Enter your username: ");
			String user = scan.next();
			users.add(user);
			for(int i=users.size()-1; i>0; i--)
			{
				duplicate = false;
				if(users.size()>1 && users.get(i).equals(users.get(i-1))) 
				{
					System.out.println("Username already taken, please enter a different user name");
					duplicate = true;
					users.remove(users.get(i));
					while(duplicate == true)
					{
						System.out.print("Enter your username: ");
						user = scan.next();
						if(users.size()>1 && users.get(i).equals(users.get(i-1))) 
						{
							
							System.out.println(users + "Username already taken, please enter a different user name");
							duplicate = true;
						}
						else
						{
							users.add(user);
							duplicate = false;
						}
					}
				}
			}
			
			boolean length = false;
			String password;
			while(length == false)
			{
				System.out.print("Enter a password: ");
				password = scan.next();
				if(password.length()<=5)
				{
					length = false;
					System.out.println("Your password must be at least 6 characters");
				}
				else
				{
					passwords.add(md5(password));
				}
				for(String d: dictionary)
				{
					if(password.toLowerCase().equals(d.toLowerCase()))
					{
						System.out.println("You have a weak password");
						weak = true;
						length = true;
						break;
					}
					else
						weak = false;
				}
				for(String s: strings)
				{
					if(password.toLowerCase().equals(s.toLowerCase()))
					{
						System.out.println("You have a moderate password");
						moderate = true;
						length = true;
						break;
					}
					else
						moderate = false;
				}
				length = true;
			}
				if(weak==false && moderate==false)
				{
					System.out.println("You have a strong password");
					length = true;
				}

			System.out.print("Register another user? (Y/N) ");
			response = scan.next();
		}
		
		
		FileWriter writer = new FileWriter("usersandpass.txt");
		for(int i=0; i<users.size(); i++)
		{
			writer.write(users.get(i));
			writer.write(" ");
			writer.write(passwords.get(i) + "\r\n");
		}
		writer.close();
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
}