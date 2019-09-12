import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import here.MidiToWavRenderer;

public class Inst
{
	public static void render(String songName)
	{
		
		// TODO Auto-generated method stub
		final String		dir;
		MidiToWavRenderer	doer;
		File				soundBank, midiFile, outputFile;
		String				soundFont, midiPath, wavPath;
		
		dir			= System.getProperty("user.dir");
		soundFont	= dir + "/CMP.sf2";
		midiPath	= dir + "/" + songName + ".mid";
		wavPath		= dir + "/" + songName + ".wav";
		
		soundBank	= new File(soundFont);
		midiFile	= new File(midiPath);
		outputFile	= new File(wavPath);
		
		// start
		long lStartTime = System.nanoTime();
		try
		{
			doer = new MidiToWavRenderer();
			doer.createWavFile(soundBank, midiFile, outputFile);
		}
		catch (MidiUnavailableException | InvalidMidiDataException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end try - catch
		
		System.out.println("");
		System.out.println("DONE !!!:");
		long	lEndTime	= System.nanoTime();
		// end
		
		long	output		= lEndTime - lStartTime; // time elapsed
		
		System.out.println("Elapsed time in milliseconds: " + output / 1000000);
	}// end main
}// end Inst - class
