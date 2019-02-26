import jm.music.data.*;
import jm.util.Write;

import java.io.IOException;

import static jm.constants.Durations.*;
import static jm.constants.ProgramChanges.PIANO;

public class Demo
{
	private static final double LONG_RYTHM  = 0.4;
	private static final double SHORT_RYTHM = 0.2;

	private static final String SONG_NAME   = "random-name";

	public static void main(String[] args) throws IOException
	{
		blues(SONG_NAME);
		Inst.render(SONG_NAME);
	}//end notMain

	private static void singleScale() throws IOException
	{
		Score  stochScore    = new Score("JMDemo - Single");
		Part   inst          = new Part("Piano", PIANO, 0);
		Phrase phr           = new Phrase(0.0);
		Scale  myScale       = new Scale("scale_a_blues");
		int    randomInScale = 0;

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			randomInScale = myScale.getNotes().get((int) (Math.random() * myScale.getNotes().size()));
			Note note = new Note(randomInScale, QUAVER);
			phr.addNote(note);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "single.mid");
	}//end singleScale

	private static void multipleScales() throws IOException
	{
		Score  stochScore    = new Score("JMDemo - Single");
		Part   inst          = new Part("Piano", PIANO, 0);
		Phrase phr           = new Phrase(0.0);
		Scale  myScale       = new Scale("scale_c_ionian", "scale_d_ionian", "scale_e_ionian");
		int    randomInScale = 0;

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			randomInScale = myScale.getNotes().get((int) (Math.random() * myScale.getNotes().size()));
			Note note = new Note(randomInScale, QUAVER);
			phr.addNote(note);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "multiple.mid");
	}//end multipleScales

	private static void randomScales() throws IOException
	{
		Score  stochScore    = new Score("JMDemo - Single");
		Part   inst          = new Part("Piano", PIANO, 0);
		Phrase phr           = new Phrase(0.0);
		Scale  myScale       = new Scale(3);
		int    randomInScale = 0;

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			randomInScale = myScale.getNotes().get((int) (Math.random() * myScale.getNotes().size()));
			Note note = new Note(randomInScale, QUAVER);
			phr.addNote(note);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "random-scales.mid");
	}//end randomScales

	private static void singleScaleChords() throws IOException
	{
		Score   stochScore       = new Score("JMDemo - Single");
		Part    inst             = new Part("Piano", PIANO, 0);
		CPhrase chord            = new CPhrase(0.0);
		Scale   myScale          = new Scale("scale_c_ionian_pentatonic");
		int[]   randomPitchArray;

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			randomPitchArray = myScale.getChords().get((int) (Math.random() * myScale.getChords().size()));
			chord.addChord(randomPitchArray, QUAVER);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addCPhrase(chord);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "single-scale-chords.mid");
	}//end singleScaleChords

	private static void randomScaleChords() throws IOException
	{
		Score   stochScore       = new Score("JMDemo - Single");
		Part    inst             = new Part("Piano", PIANO, 0);
		CPhrase chord            = new CPhrase(0.0);
		Scale   myScale          = new Scale(3);
		int[]   randomPitchArray;

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			randomPitchArray = myScale.getChords().get((int) (Math.random() * myScale.getChords().size()));
			chord.addChord(randomPitchArray, QUAVER);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addCPhrase(chord);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "random-scale-chords.mid");
	}//end randomScaleChords

	private static void stochastic()
	{
		Score  stochScore = new Score("JMDemo - Stochastic");
		Part   inst       = new Part("Piano", PIANO, 0);
		Phrase phr        = new Phrase(0.0);

		// create a phrase of randomly pitched quavers over the full MIDI range.
		for (int i = 0; i < 24; i++)
		{
			Note note = new Note((int) (Math.random() * 108) + 21, QUAVER); //Only usable ranges (21 & 108)
			phr.addNote(note);
		}//end for - i

		// add the phrase to an instrument and that to a score
		inst.addPhrase(phr);
		stochScore.addPart(inst);

		// create a MIDI file of the score
		Write.midi(stochScore, "stochy.mid");
	}//end stochastic

	private static void blues(String songName) throws IOException
	{
		Score   stochScore      = new Score(70.0);
		Part    inst            = new Part("Piano", PIANO, 0);
		Part    otherInst       = new Part("Piano", PIANO, 1);
		Scale   myScale         = new Scale("scale_d_blues");

		CPhrase firstPhrase     = new CPhrase(0.0);
		int[]   tonic           = myScale.getChords().get(0);
		int[]   subDominant     = myScale.getChords().get(2);
		int[]   dominant        = myScale.getChords().get(4);

		int[]   bassTonic       = new int[]{tonic[0]                      };
		int[]   highTonic       = new int[]{tonic[1], tonic[2]            };

		int[]   bassSubDominant = new int[]{subDominant[0]                };
		int[]   highSubDominant = new int[]{subDominant[1], subDominant[2]};

		int[]   bassDominant    = new int[]{dominant[0]                   };
		int[]   highDominant    = new int[]{dominant[1], dominant[2]      };

		Scale.octaveDown(bassTonic      );
		Scale.octaveDown(bassSubDominant);
		Scale.octaveDown(bassDominant   );

		bluesCompass(firstPhrase, bassTonic, highTonic, 4);
		bluesCompass(firstPhrase, bassSubDominant, highSubDominant, 2);
		bluesCompass(firstPhrase, bassTonic, highTonic, 2);
		bluesCompass(firstPhrase, bassDominant, highDominant, 1);
		bluesCompass(firstPhrase, bassSubDominant, highSubDominant, 1);
		bluesCompass(firstPhrase, bassTonic, highTonic, 2);

		// add the phrase to an instrument and that to a score
		inst.addCPhrase(firstPhrase);

		int    randomInScale;
		Phrase rightHand     = new Phrase(0.0);
		for (int i = 0; i < 50; i++)
		{
			randomInScale = myScale.getNotes().get((int) (Math.random() * myScale.getNotes().size()));
			if (Math.random() > 0.5)
			{
				Note note = new Note(randomInScale + 12, Math.random() * 0.5 + 0.2);
				rightHand.addNote(note);
			}
			else
			{
				Note note = new Note(randomInScale, Math.random() * 0.5 + 0.2);
				rightHand.addNote(note);
			}
		}//end for - i

		otherInst.addPhrase(rightHand);

		stochScore.addPart(inst     );
		stochScore.addPart(otherInst);

		// create a MIDI file of the score
		Write.midi(stochScore, songName + ".mid");
	}//end blues

	private static void bluesCompass(CPhrase phrase, int[] bass, int[] highs, int times)
	{
		for (int i = 0; i < times; i++)
		{
			phrase.addChord(bass, LONG_RYTHM  );
			phrase.addChord(bass, SHORT_RYTHM );
			phrase.addChord(highs, LONG_RYTHM );
			phrase.addChord(highs, SHORT_RYTHM);
		}//end for - i
	}//end bluesCompass
}//end Demo - class
