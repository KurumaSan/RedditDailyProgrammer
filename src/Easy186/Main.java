package Easy186;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Main 
{
	static HashMap<String, Integer> candyHashMap = new HashMap<String, Integer>();
	static int totalCandy = 0;
	
	public static void main( String[] args )
	{
		CaluclateCandyAmounts();
		PrintResults(); 
	}
	
	private static void CaluclateCandyAmounts()
	{
		try 
		{
			BufferedReader candyList = new BufferedReader( new FileReader( "C:/Path/To/Your/File/186Easy.txt" ) );
			
			String candyDescription = null;
			
			while( ( candyDescription = candyList.readLine() ) != null  )
			{
				AddCandyPiece( candyDescription );
			}
		} 
		catch (Exception fileNotFoundException) 
		{
			fileNotFoundException.printStackTrace();
		}
	}
	
	private static void AddCandyPiece( String candyDescription )
	{
		totalCandy++;
		
		if( candyHashMap.get( candyDescription ) != null )
		{
			candyHashMap.put( candyDescription, candyHashMap.get( candyDescription ) + 1 );
		}
		else
		{
			candyHashMap.put( candyDescription, 1 );
		}
	}
	
	private static void PrintResults()
	{
		System.out.println( "There are " + totalCandy + " pieces of Candy!\n" );
		
		for( Map.Entry<String, Integer> entry : candyHashMap.entrySet() )
		{
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			System.out.println( key + " : " + value + " Pieces" + " (" +  (float)value / totalCandy * 100 + "%)" );
		}
	}
}