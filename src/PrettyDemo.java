import jm.constants.Durations;
import jm.constants.ProgramChanges;
import jm.music.data.Rest;
import jm.util.Play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class PrettyDemo
{
	private static final double[] DURATIONS     = new double[]{0.083, 0.125, 0.16, 0.25, 0.33, 0.375, 0.5, 0.66, 0.75, 0.875, 1.0, 1.33, 1.5, 1.75, 2.0, 3.0, 3.5, 4.0};
	private static final double   LONGDURATION  = 0.4;
	private static final double   SHORTDURATION = 0.2;

	public static void main(String[] args) throws IOException
	{
		blues();
	}//end main

	private static double getRandomDuration(int divisions)
	{
		return DURATIONS[(int) (Math.random() * DURATIONS.length) / divisions];
	}//end getRandomDuration

	private static void prettyC()
	{
		PrettyScore ps = new PrettyScore("Pretty ,Pretty, Pretty");
		ps.addInstrument(ProgramChanges.PIANO, 0);
		ps.instruments.get(0).addNote(48, Durations.C);
		ps.endScore();
		String name = "Pretty.mid";
		ps.save(name);
		Play.mid(name);
	}//end prettyC

	private static void stochastic()
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.ACCORDION, 5);

		for (int i = 0; i < 24; i++)
			ps.instruments.get(0).addNote((int) (Math.random() * 128), Durations.C);

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end stochastic

	private static void randomDurations()
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.ACCORDION, 5);

		for (int i = 0; i < 24; i++)
			ps.instruments.get(0).addNote((int) (Math.random() * 128), getRandomDuration(2));

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end randomDurations

	private static void addInstruments()
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.ACCORDION, 5     );
		ps.addInstrument(ProgramChanges.ALTO_SAXOPHONE, 5);

		for (int i = 0; i < 24; i++)
		{
			ps.instruments.get(0).addNote(48, Durations.QUAVER);
			ps.instruments.get(1).addNote(50, Durations.QUAVER);
		}//end for - i

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end addInstruments

	private static void addChords()
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.ACCORDION, 5);

		for (int i = 0; i < 24; i++)
			ps.instruments.get(0).addChord(Durations.QUAVER, 48, 50, 55, 60);

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end addChords

	private static void addScale() throws IOException
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.ACCORDION, 5);
		Scale myScale = new Scale("scale_c_ionian");

		for (int i = 0; i < 24; i++)
			ps.instruments.get(0).addNote(myScale.getRandomNote(true), getRandomDuration(2));

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end addScale

	private static void blues() throws IOException
	{
		PrettyScore ps = new PrettyScore("Stochy");
		ps.addInstrument(ProgramChanges.PIANO, 5);
		//		ps.addInstrument(ProgramChanges.ACCORDION, 5);
		ps.selectScale("scale_d_blues");

		ps.instruments.get(0).addChord(LONGDURATION, ps.getChord(0)[0]                    );
		ps.instruments.get(0).addChord(SHORTDURATION, ps.getChord(0)[0]                   );
		ps.instruments.get(0).addChord(LONGDURATION, ps.getChord(0)[1], ps.getChord(0)[2] );
		ps.instruments.get(0).addChord(SHORTDURATION, ps.getChord(0)[1], ps.getChord(0)[2]);

		int[] bassTonic       = new int[]{ps.tonic[0]                         };
		int[] highTonic       = new int[]{ps.tonic[1], ps.tonic[2]            };

		int[] bassSubDominant = new int[]{ps.subDominant[0]                   };
		int[] highSubDominant = new int[]{ps.subDominant[1], ps.subDominant[2]};

		int[] bassDominant    = new int[]{ps.dominant[0]                      };
		int[] highDominant    = new int[]{ps.dominant[1], ps.dominant[2]      };

		bluesCompass(ps, bassTonic, highTonic, 4            );
		bluesCompass(ps, bassSubDominant, highSubDominant, 2);
		bluesCompass(ps, bassTonic, highTonic, 2            );
		bluesCompass(ps, bassDominant, highDominant, 1      );
		bluesCompass(ps, bassSubDominant, highSubDominant, 1);
		bluesCompass(ps, bassTonic, highTonic, 2            );


		ps.addInstrument(ProgramChanges.PIANO, 2);
		ps.instruments.get(1).addSilence(getRandomDuration(1));

		for (int i = 0; i < 32; i++)
		{
			if(Math.random() > 0.8)
				ps.instruments.get(1).addSilence(getRandomDuration(1));
			else
				ps.instruments.get(1).addNote(ps.getRandomNote(true), getRandomDuration(3));

		}//end for - i

		ps.endScore();
		String name = "Stochy.mid";
		ps.save(name);
		Play.mid(name);
	}//end blues

	private static void bluesCompass(PrettyScore ps, int[] bass, int[] highs, int times)
	{
		for (int i = 0; i < times; i++)
		{
			ps.instruments.get(0).addChord(LONGDURATION, bass);
			ps.instruments.get(0).addChord(SHORTDURATION, bass);
			ps.instruments.get(0).addChord(LONGDURATION, highs );
			ps.instruments.get(0).addChord(SHORTDURATION, highs);
		}//end for - i
	}//end bluesCompass
}//end PrettyDemo - class