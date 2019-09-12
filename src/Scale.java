import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class that represents one of the several possible scales that are possible and
 * stored in the file called 'all-scales.txt'
 *
 * @author Marco Cardenas
 */
public class Scale
{
	private static final String	SCALES_FILE_PATH		= "src/all-scales.txt";
	private static final int	SCALES_FILE_NUM_LINES	= 4225;
	
	private ArrayList<Integer>	notes;
	private String[]			names;
	private String[]			data;
	private ArrayList<int[]>	chords;
	
	public static void octaveUp(int[] chord)
	{
		for (int i = 0; i < chord.length; i++)
			chord[i] += 12;
	}// end octaveUp
	
	public static void octaveDown(int[] chord)
	{
		for (int i = 0; i < chord.length; i++)
			chord[i] -= 12;
	}// end octaveDown
	
	/**
	 * Scale constructor that uses as many scales given their names
	 * to put it in the objects' notes
	 *
	 * @param  scaleNames  - the names of all the scales as a variable length argument
	 *
	 * @throws IOException - if the file is not found
	 */
	public Scale(String... scaleNames) throws IOException
	{
		this.names	= scaleNames;
		this.notes	= new ArrayList<>();
		this.chords	= new ArrayList<>();
		this.data	= new String[scaleNames.length];
		for (int i = 0; i < scaleNames.length; i++)
		{
			fetchData(scaleNames[i], i);
			setNotes(i);
		}// end for - i
	}// end Scale - constructor
	
	/**
	 * Scale constructor that takes how many number of scales
	 * the user wants to have randomly chosen from all possible scales
	 *
	 * @param  numScales   - how many number of scales from all possible scales
	 *
	 * @throws IOException - if the file is not found
	 */
	public Scale(int numScales) throws IOException
	{
		this.names	= new String[numScales];
		this.notes	= new ArrayList<>();
		this.chords	= new ArrayList<>();
		this.data	= new String[numScales];
		fetchData(numScales);
		for (int i = 0; i < numScales; i++)
			setNotes(i);
	}// end Scale - constructor (random
	
	// region Getters & Setters
	public ArrayList<Integer> getNotes()
	{
		return notes;
	}// end getNotes
	
	public void setNotes(ArrayList<Integer> notes)
	{
		this.notes = notes;
	}// end setNotes
	
	public String[] getNames()
	{
		return names;
	}// end getNames
	
	public void setNames(String[] names)
	{
		this.names = names;
	}// end setNames
	
	public String[] getData()
	{
		return data;
	}// end getData
	
	public void setData(String[] data)
	{
		this.data = data;
	}// end setData
	
	public ArrayList<int[]> getChords()
	{
		return chords;
	}// end getChords
	
	public void setChords(ArrayList<int[]> chords)
	{
		this.chords = chords;
	}// end setChords
		// endregion Getters & Setters
	
	/**
	 * Initializes the objects' data field given the name of a scale
	 * and takes a number to put it in the correct index for the data field
	 *
	 * @param  targetName  - the name of the scale that will be searched in the file
	 * @param  i           - the index for the data field's array
	 *
	 * @throws IOException - if the file is not found
	 */
	private void fetchData(String targetName, int i) throws IOException
	{
		FileReader		fr	= new FileReader(SCALES_FILE_PATH);
		BufferedReader	br	= new BufferedReader(fr);
		
		String			readName;
		String			line;
		
		line		= br.readLine();
		readName	= line.substring(0, line.indexOf(':'));
		
		while (!readName.equals(targetName))
		{
			line		= br.readLine();
			readName	= line.substring(0, line.indexOf(':')); // Set name
		}// end while
		
		this.data[i] = line.substring(line.indexOf(":") + 1); // Set data
		
		br.close();
	}// end fetchData
	
	/**
	 * Initializes the objects' data field with 'i' random numbers
	 *
	 * @param  i           - how many random number of scales will compose the notes
	 *
	 * @throws IOException - if the file is not found
	 */
	private void fetchData(int i) throws IOException
	{
		FileReader			fr;
		BufferedReader		br;
		String				line;
		HashSet<Integer>	randomNumbers;
		int					index;
		
		randomNumbers = new HashSet<>(i);
		while (randomNumbers.size() != i)
			randomNumbers.add((int) (Math.random() * SCALES_FILE_NUM_LINES));
		
		index = 0;
		for (Integer randomNumber : randomNumbers)
		{
			fr		= new FileReader(SCALES_FILE_PATH);
			br		= new BufferedReader(fr);
			line	= br.readLine();
			
			for (int j = 0; j < randomNumber; j++)
				line = br.readLine();
			
			this.names[index]	= line.substring(0, line.indexOf(':')); // Set names
			this.data[index]	= line.substring(line.indexOf(":") + 1); // Set data
			index++;
			
			br.close();
		}// end foreach
	}// end fetchData
	
	/**
	 * If the objects' data field is already initialized, it initializes
	 * the objects' notes field with said data.
	 *
	 * @param i - the index for accessing the correct scale of the data field
	 */
	private void setNotes(int i)
	{
		String[]	notesAndChords;
		String[]	tmpArray;
		String		tmp;
		int[]		tmpChordNotes;
		int			numberOfNotes;
		
		notesAndChords = this.data[i].split(",");
		
		// Get rid of the curly braces
		for (int j = 0; j < notesAndChords.length; j++)
			notesAndChords[j] = notesAndChords[j].substring(1, notesAndChords[j].length() - 1);
		
		// Get number of notes in scale
		numberOfNotes = 0;
		while (!notesAndChords[numberOfNotes].contains(" "))
			numberOfNotes++;
		
		// Set the values of the scale's notes
		for (int j = 0; j < numberOfNotes; j++)
		{
			tmp = notesAndChords[j].substring(0, notesAndChords[j].indexOf('-'));
			this.notes.add(Integer.parseInt(tmp));
		}// end for - j
		
		// Set the values of the scale's
		for (int j = numberOfNotes; j < notesAndChords.length; j++)
		{
			tmp				= notesAndChords[j].substring(0, notesAndChords[j].indexOf('|'));
			tmpArray		= tmp.split(" ");
			tmpChordNotes	= new int[tmpArray.length];
			for (int k = 0; k < tmpArray.length; k++)
				tmpChordNotes[k] = Integer.parseInt(tmpArray[k]);
			
			this.chords.add(tmpChordNotes);
		}// end for - j
	}// end setNotes
	
	public int getRandomNote(boolean octaves)
	{
		if (octaves)
		{
			int	pitch	= this.notes.get((int) (Math.random() * this.notes.size()));
			int	octave	= 0;
			
			if (Math.random() > 0.5)
				octave *= -1;
			
			if (Math.random() > 0.5)
				octave = 12;
			else
				octave = 24;
			
			return pitch + octave;
		}
		else
		{
			return this.notes.get((int) (Math.random() * this.notes.size()));
		}// end if - else
		
	}// end getRandomNote
}// end Scale - class