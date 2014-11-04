package Easy187;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FlagonOfFlags 
{
	static String reader = "";
	static HashMap< Character, String > inputHashMap = new HashMap< Character, String >(); 
	static BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
	static String[] commandParts;
	
	public static void main( String[] args )
	{
		ReadInputs();
		PrintOutputs();
	}
	
	private static void ReadInputs()
	{
		System.out.print( "Number of Inputs: " );
		
		int inputParameters = Integer.parseInt( ReadLine( bufferedReader ) );
		
		for( int i = 0; i < inputParameters ; i++ )
		{
			System.out.print( "Input " + ( i + 1 ) + " : " );
			
			reader = ReadLine( bufferedReader );
			
			inputHashMap.put( reader.charAt(0) , reader.substring( reader.indexOf(':') + 1 ) );
		}
		
		System.out.print( "Enter in your command : " );
		commandParts = ReadLine( bufferedReader ).split(" ");
	}
	
	private static String ReadLine( BufferedReader bufferedReader )
	{
		try 
		{
			reader = bufferedReader.readLine();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reader;
	}
	
	private static void PrintOutputs()
	{
		for( int i = 0; i < commandParts.length ; i++ )
		{
			if( commandParts[ i ].startsWith( "--" ) == true )
			{
				System.out.println( "Flag : " + commandParts[ i ].substring( 2 ) );
			}
			else if( commandParts[ i ].charAt( 0 ) == '-' )
			{
				PrintFlags( commandParts[ i ] );
			}
			else
			{
				System.out.println( "Parameter : " + commandParts[ i ] );
			}
		}
	}
	
	private static void PrintFlags( String flag )
	{
		for( int i = 1; i < flag.length() ; i++ )
		{
			if( inputHashMap.get( ( flag.charAt(i) ) ) != null )
			{
				System.out.println( "Flag : " + inputHashMap.get( ( flag.charAt(i) ) ) );
			}
			else
			{
				System.out.println( "Flag: \"" + flag.charAt( i ) + "\" does Not Exist!" );
			}
		}
	}
}