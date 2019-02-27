import jm.music.data.*;
import jm.util.Write;

import java.io.IOException;

import static jm.constants.Durations.*;
import static jm.constants.ProgramChanges.PIANO;
import jm.util.*;

public class Demo
{
	private static final String SONG_NAME   = "random-name";

	public static void main(String[] args) throws IOException
	{
		demo(SONG_NAME);
	}//end notMain

	private static void demo(String songName) throws IOException
	{
		Score  stochScore = new Score("JMDemo - C4");
		Part   inst       = new Part("Piano", PIANO, 0);
		Phrase phr        = new Phrase(0.0);
		Note   note       = new Note(48, C);

		phr.add(note);
		inst.addPhrase(phr);
		stochScore.addPart(inst);

		String name = "random-name.mid";

		Write.midi(stochScore, name); // create a MIDI file of the score
		Play.mid(name);
	}//end demo
}//end Demo - class
