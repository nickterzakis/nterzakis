import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Authentication extends Registration
{
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<String> passwords = new ArrayList<String>();
	static boolean no;
	public static void main (String args[]) throws Exception
	{
		int j=0;
		Scanner userPassScan = null;
		while(j<6)
		{
			do{
				try
				{
					userPassScan = new Scanner(new File("usersandpass.txt"));
					break;
				}
				catch(FileNotFoundException e)
				{
					System.out.print("You need to register before you can authenticate, run the registration program first");
					System.exit(0);
				}
			}while(true);
			
			while(userPassScan.hasNext())
			{
				users.add(userPassScan.next());
				passwords.add(userPassScan.next());
			}
			userPassScan.close();
			Scanner s = new Scanner(System.in);
			System.out.print("Username: ");
			String u = s.nextLine();
			System.out.print("Password: ");
			String p = md5(s.nextLine());
			boolean hit = false;
			for(int i=0; i<passwords.size(); i++)
			{
				if(u.equals(users.get(i)) && p.equals(passwords.get(i)))
				{
					hit=true;
				}
				else
					hit = false;
				if(hit==true)
				{
					System.out.println("Welcome back, " + users.get(i));
					System.exit(0);
				}
			}
			if(hit == false)
			{
				++j;
				System.out.println("You've entered an incorrect password, you have " + (6-j) + " more tries");
			}
		}
		if(j>=5)
		{
			System.out.println("You've entered an incorrect password, you have 0 tries left and your account has been locked for the next hour because of a possible security breach");
			System.exit(0);
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