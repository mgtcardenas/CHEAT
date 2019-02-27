import jm.audio.Instrument;
import jm.constants.Durations;
import jm.music.data.*;

public class PrettyInstrument
{
	Part   instrument;
	Phrase phrase;
	CPhrase cPhrase;
	double instant;

	public double getInstant()
	{
		return instant;
	}//end getInstant

	public PrettyInstrument(final int CONSTANT, int channel)
	{
		instrument = new Part("", CONSTANT, channel);
		phrase     = new Phrase(0.0);
		cPhrase = new CPhrase(0.0);
		instant    = 0;
	}//end PrettyInstrument - constructor

	public void addChord(double duration, int... notes)
	{
		Note[]  tmpNotes = new Note[notes.length];

		for (int i = 0; i < notes.length; i++)
			tmpNotes[i] = new Note(notes[i], duration);

		cPhrase.addChord(tmpNotes);

		this.instant += duration;
	}//end addChord

	public void addChord(boolean down, double duration, int... notes)
	{
		Note[]  tmpNotes = new Note[notes.length];

		if (down)
		{
			for (int i = 0; i < notes.length; i++)
				tmpNotes[i] = new Note(notes[i] - 12, duration);
		}
		else
		{
			for (int i = 0; i < notes.length; i++)
				tmpNotes[i] = new Note(notes[i] + 12, duration);
		}//end if - else

		cPhrase.addChord(tmpNotes);
	}//end addChord

	public void addNote(int pitch, double duration)
	{
		instant += duration;
		phrase.addNote(new Note(pitch, duration));
	}//end addNote

	public void addSilence(double duration)
	{
		instant += duration;
		phrase.addNote(new Rest(duration));
	}//end addNote

	public Part returnPart()
	{
		instrument.addPhrase(phrase);
		instrument.addCPhrase(cPhrase);
		return instrument;
	}//end returnPart
}//end PrettyInstrument - class