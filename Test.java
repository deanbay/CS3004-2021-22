
public class Test {

	public static void main(String[] array)
	{
		String example = "ADD 200";
		String newstr = example.replaceAll("[^A-Za-z]+", "");
		String newint = example.replaceAll("[A-Za-z]+", "").trim();
		int newInt = Integer.valueOf(newint);

		System.out.println(newstr);
		System.out.println(newint);
		System.out.println(newInt); 
	}
	
	
	
	
	
}
