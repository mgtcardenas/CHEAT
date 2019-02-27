import jm.music.data.*;
import jm.util.Write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrettyScore
{
	       List<PrettyInstrument> instruments = new ArrayList<>();
	       Score                  score;
	       Scale                  scale;
	public int[]                  tonic;
	public int[]                  subDominant;
	public int[]                  dominant;

	public Scale getScale()
	{
		return scale;
	}//end getScale

	public PrettyScore(String name)
	{
		score = new Score(name);
	}//end PrettyScore

	public void addInstrument(final int CONSTANT, int channel)
	{
		instruments.add(new PrettyInstrument(CONSTANT, channel));
	}//end addInstrument

	public void endScore()
	{
		for (PrettyInstrument pi : instruments)
			score.addPart(pi.returnPart());
	}//end endScore

	public void save(String name)
	{
		Write.midi(score, name);
	}//end save

	public void selectScale(String scaleName) throws IOException
	{
		scale       = new Scale(scaleName);
		tonic       = scale.getChords().get(0);
		subDominant = scale.getChords().get(2);
		dominant    = scale.getChords().get(4);
	}//end selectScale

	public int getRandomNote(boolean octaves)
	{
		return scale.getRandomNote(octaves);
	}//end getRandomNote

	public int[] getChord(int i)
	{
		return scale.getChords().get(i);
	}//end getChord
}//end PrettyScore - class