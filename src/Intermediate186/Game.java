package Intermediate186;
import java.util.Random;

public class Game 
{
	String[][] world;
	String[] movements; 
	public int zombies , victims, hunters , ticks;
	int zombieStumbleUnits, victimFleeUnits, hunterSeekUnits, hunterSingleKills, hunterDoubleKills, victimsBitten, huntersBitten ;
	
	Game()
	{	
		movements = new String[]{ "Left", "Right", "Up", "Down", "DiagonalUpLeft", "DiagonalUpRight", "DiagonalDownLeft", "DiagonalDownRight" };
		world = new String[20][20];
		
		zombies = 30;
		victims = 200;
		hunters = 8;
		ticks = 100;
		
		zombieStumbleUnits = 0;
		victimFleeUnits = 0;
		hunterSeekUnits = 0;
		
		hunterSingleKills = 0;
		hunterDoubleKills = 0; 
		
		victimsBitten = 0;
		huntersBitten = 0;
		
		PopulateWorld();
		UpdateWorld( ticks );
		PrintStatistics();
	}
	
	private void PopulateWorld()
	{
		for( int i = 0; i < world.length ; i++ )
		{
			for( int j = 0; j < world[0].length ; j++ )
			{
				world[ i ][ j ] = "Empty";
			}
		}
		
		SpawnCharacter( zombies, "Zombie" );
		SpawnCharacter( victims, "Victim" );
		SpawnCharacter( hunters, "Hunter" );
	}	
	
	private void SpawnCharacter( int numberOfCharacters, String name )
	{
		Random randomizer = new Random();
		
		do
		{	
			int row = randomizer.nextInt( 20 ) + 0;
			int column = randomizer.nextInt( 20 ) + 0;
			
			if( world[ row ][ column ] == "Empty" )
			{
				world[ row ][ column ] = name;
				numberOfCharacters--;
			}
		}
		while( numberOfCharacters > 0 );
	}
	
	private void UpdateWorld( int ticks )
	{
		do
		{
			//PrintZombieWorld();
			UpdateSlay();
			UpdateBite();
			UpdateCharacterMovements();
			
			ticks --;
		}
		while( ticks > 0 );
	}
	
	private void UpdateSlay()
	{	
		for( int i = 0; i < world.length ; i++ )
		{
			for( int j = 0; j < world[0].length ; j++ )
			{
				if( world[ i ][ j ].equals( "Hunter" ) )
				{
					SlaySurroundingZombies( i , j );
				}
			}
		}
	}
	
	private void SlaySurroundingZombies( int rowPosition, int columnPosition )
	{
		int killCount = 0;
		
		int leftBoundary = ( rowPosition == 0 ) ? 0 : rowPosition - 1;
 		int rightBoundary = ( columnPosition == world[0].length - 1 ) ? world[0].length - 1 : columnPosition + 1;
		int topBoundary = ( columnPosition == 0 ) ? 0 : columnPosition - 1;
		int bottomBoundary = ( rowPosition == world.length - 1 ) ? world.length - 1 : rowPosition + 1; 
				
		for( int i = leftBoundary; i <= bottomBoundary ; i++ )
		{
			for( int j = topBoundary; j <= rightBoundary ; j++ )
			{
				outerLoop:
				if( world[ i ][ j ].equals( "Zombie" ) )
				{
					killCount++;
					hunterSingleKills ++;
					
					world[ i ][ j ] = "Empty";
					
					if( killCount == 2 )
					{
						hunterDoubleKills ++;
						hunterSingleKills -= 2;
						break outerLoop;
					}
				}
			}
		}
	}
	
	private void UpdateBite()
	{
		for( int i = 0; i < world.length ; i++ )
		{
			for( int j = 0; j < world[0].length ; j++ )
			{
				if( world[ i ][ j ].equals( "Zombie" ) )
				{
					InfectSurroundingHumans( i , j );
				}
			}
		}
	}
	
	private void InfectSurroundingHumans( int rowPosition, int columnPosition )
	{
		int[] infectLeft = CalculateMove( rowPosition, columnPosition, "Left" );
		int[] infectRight = CalculateMove( rowPosition, columnPosition, "Right" );
		int[] infectUp = CalculateMove( rowPosition, columnPosition, "Up" );
		int[] infectDown = CalculateMove( rowPosition, columnPosition, "Down" );
		
		if( world[ infectLeft[0] ][ infectLeft[1] ] == "Hunter" )
		{
			world[ infectLeft[0] ][ infectLeft[1] ] = "Zombie";
			huntersBitten++;
		}
		else if( world[ infectLeft[0] ][ infectLeft[1] ] == "Victim" )
		{
			world[ infectLeft[0] ][ infectLeft[1] ] = "Zombie";
			victimsBitten++;
		}
		
		if( world[ infectRight[0] ][ infectRight[1] ] == "Hunter"  )
		{
			world[ infectRight[0] ][ infectRight[1] ] = "Zombie";
			huntersBitten++;
		}
		else if( world[ infectRight[0] ][ infectRight[1] ] == "Victim" )
		{
			world[ infectRight[0] ][ infectRight[1] ] = "Zombie";
			victimsBitten++;
		}
		
		if( world[ infectUp[0] ][ infectUp[1] ] == "Hunter" )
		{
			world[ infectUp[0] ][ infectUp[1] ] = "Zombie";
			huntersBitten++;
		}
		else if( world[ infectUp[0] ][ infectUp[1] ] == "Victim" )
		{
			world[ infectUp[0] ][ infectUp[1] ] = "Zombie";
			victimsBitten++;
		}
		
		if( world[ infectDown[0] ][ infectDown[1] ] == "Hunter" )
		{
			world[ infectDown[0] ][ infectDown[1] ] = "Zombie";
			huntersBitten++;
		}
		else if( world[ infectDown[0] ][ infectDown[1] ] == "Victim" )
		{
			world[ infectDown[0] ][ infectDown[1] ] = "Zombie";
			victimsBitten++;
		}
	}
	
	private void UpdateCharacterMovements()
	{
		for( int i = 0; i < world.length ; i++ )
		{
			for( int j = 0; j < world[0].length ; j++ )
			{
				if( world[ i ][ j ].equals( "Victim" ) )
				{
					UpdateCharacterPosition( i , j, "Victim" );
				}
				else if( world[ i ][ j ].equals( "Hunter" ) )
				{
					UpdateCharacterPosition( i , j, "Hunter" );
				}
				else if( world[ i ][ j ].equals( "Zombie" ) )
				{
					UpdateCharacterPosition( i , j, "Zombie" );
				}
			}
		}
	}
	
	private void UpdateCharacterPosition(int rowPosition, int columnPosition, String characterName )
	{
		int randmomMovement = new Random().nextInt( movements.length );
		
		if( characterName == "Victim" )
		{
			Boolean shouldVictimMove = ShouldVictimMove( rowPosition , columnPosition );
						
			if( shouldVictimMove == false )
			{
				return;
			}
		}
		else if( characterName == "Zombie" )
		{
			randmomMovement = new Random().nextInt( movements.length - 4 );
		}
		
		int[] newCharacterPosition = CalculateMove( rowPosition, columnPosition, movements[ randmomMovement ] );
		
		if( world[ newCharacterPosition[ 0 ] ][ newCharacterPosition[ 1 ]  ].equals( "Empty" ) )
		{
			world[ newCharacterPosition[ 0 ] ][ newCharacterPosition[ 1 ] ] = characterName;
			world[ rowPosition ][ columnPosition ] = "Empty";
			
			if( characterName == "Zombie" )
			{
				zombieStumbleUnits++;
			}
			else if( characterName == "Victim" )
			{
				victimFleeUnits++;
			}
			else if( characterName == "Hunter" )
			{
				hunterSeekUnits++;
			}
		}
	}
	
	private boolean ShouldVictimMove( int rowPosition, int columnPosition )
	{
		int leftBoundary = ( rowPosition == 0 ) ? 0 : rowPosition - 1;
 		int rightBoundary = ( columnPosition == world[0].length - 1 ) ? world[0].length - 1 : columnPosition + 1;
		int topBoundary = ( columnPosition == 0 ) ? 0 : columnPosition - 1;
		int bottomBoundary = ( rowPosition == world.length - 1 ) ? world.length - 1 : rowPosition + 1; 
				
		for( int i = leftBoundary; i <= bottomBoundary ; i++ )
		{
			for( int j = topBoundary; j <= rightBoundary ; j++ )
			{
				if( world[ i ][ j ].equals( "Zombie" ) )
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private int[] CalculateMove( int rowPosition, int columnPosition, String name )
	{
		int[] blank = {0};
		
		switch( name )
		{
			case "Left":
			{
				int[] moveLeft = { rowPosition , ( columnPosition == 0 ) ? 0 : columnPosition - 1 };
				return moveLeft;
			}
			
			case "Right":
			{
				int[] moveRight = { rowPosition , ( columnPosition == world[0].length - 1 ) ? world[0].length - 1 : columnPosition + 1 };
				return moveRight;
			}
			
			case "Up":
			{
				int[] moveUp = { ( rowPosition == 0 ) ? 0 : rowPosition - 1 , columnPosition };
				return moveUp;
			}
			
			case "Down":
			{
				int[] moveDown = { ( rowPosition == world.length - 1 ) ? world.length - 1 : rowPosition + 1 , columnPosition };
				return moveDown;
			}
			
			case "DiagonalUpLeft":
			{
				int[] moveDiagonalUpLeft = { ( rowPosition == 0 ) ? 0 : rowPosition - 1,  ( columnPosition == 0 ) ? 0 : columnPosition - 1 };
				return moveDiagonalUpLeft;
			}
			
			case "DiagonalUpRight":
			{
				int[] moveDiagonalUpRight = { ( rowPosition == 0 ) ? 0 : rowPosition - 1,  ( columnPosition == world[0].length - 1 ) ? world[0].length - 1 : columnPosition + 1 };
				return moveDiagonalUpRight;
			}
			
			case "DiagonalDownLeft":
			{
				int[] moveDiagonalDownLeft = { ( rowPosition == world.length - 1 ) ? world.length - 1 : rowPosition + 1,  ( columnPosition == 0 ) ? 0 : columnPosition - 1 };
				return moveDiagonalDownLeft;
			}
			
			case "DiagonalDownRight":
			{
				int[] moveDiagonalDownRight = { ( rowPosition == world.length - 1 ) ? world.length - 1 : rowPosition + 1,  ( columnPosition == world[0].length - 1 ) ? world[0].length - 1 : columnPosition + 1 };
				return moveDiagonalDownRight;
				
			}
		
			default:
					break;
		}
		
		return blank;
	}
	
	private void PrintStatistics()
	{
		System.out.println( "Starting Zombies : " + zombies );
		System.out.println( "Starting Victims : " + victims );
		System.out.println( "Starting Hunters : " + hunters );
		System.out.println( "Starting Ticks :" + ticks );
		
		System.out.println(  );
		
		System.out.println( "Total Zombie Stumble Units :" + zombieStumbleUnits );
		System.out.println( "Total Victim Flee Untis : " + victimFleeUnits );
		System.out.println( "Total Hunter Seek Units : " + hunterSeekUnits );
		
		System.out.println(  );
		
		System.out.println( "Number of Victims who have joined the Dead : " + victimsBitten );
		System.out.println( "Number of Hunters who have joined the Dead : " + huntersBitten );
		System.out.println( "Total Number of Ex-Humans : " + ( victimsBitten + huntersBitten ) );
		
		System.out.println(  );
		
		System.out.println( "Total Number of Hunter Single Kills : " + hunterSingleKills );
		System.out.println( "Total Number of Hunter Double Kills : " + hunterDoubleKills );
		System.out.println( "Total Zombies Capped : " + ( hunterSingleKills + hunterDoubleKills * 2 ) );
		
		System.out.println(  );
		
		int zombiesRemaining = zombies + victimsBitten + huntersBitten - hunterSingleKills - ( hunterDoubleKills * 2 );
		
		System.out.println( "Zombies Remaining : " + ( zombiesRemaining ) );
		System.out.println( "Victims Remaining : " + ( victims - victimsBitten ) );
		System.out.println( "Hunters Remaining : " + ( hunters - huntersBitten ) );
		
		System.out.println(  );
		
		if( zombiesRemaining - zombies > 5 )
		{
			System.out.println( "Decay Rate of Zombies Icreasing : (" + zombiesRemaining + " Zombies reamining) The Zombies Win!"  );
		}
		else if( zombiesRemaining - zombies >= -5  && zombiesRemaining - zombies <= 0 )
		{
			System.out.println( "Decay Rate of Zombies is Steady : " + zombiesRemaining + " Zombies remaining). It's a tie!");
		}
		else if( zombiesRemaining - zombies < -5 )
		{
			System.out.println( "Decay Rate of Zombies is Declining : (" + zombiesRemaining + " Zombies remaining). The Humans Will Win The Battle!" );
		}
	}
	
	/*
	private void PrintZombieWorld()
	{	
		System.out.println( "\n\n\n---------------------------------------------------------------------------------" );
		for( int i = 0; i < world.length ; i++ )
		{
			System.out.print( "| " );
			
			for( int j = 0; j < world[0].length ; j++ )
			{
				System.out.print( world[ i ][ j ].charAt(0) + " | " );
			}
			
			System.out.println( );
			System.out.println( "---------------------------------------------------------------------------------" );		
		}
	}
	*/
}
