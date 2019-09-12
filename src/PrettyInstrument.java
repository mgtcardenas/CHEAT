import jm.music.data.*;

public class PrettyInstrument
{
	private static final String[]	INST_NAMES	= new String[] { "Acoustic_Grand_Piano", "Bright_Acoustic_Piano", "Electric_Grand_Piano", "Honky_tonk_Piano", "Electric_Piano_1", "Electric_Piano_2", "Harpsichord", "Clavi", "Celesa", "Glockenspiel", "Music_Box", "Vibraphone", "Marimba", "Xylophone", "Tubular_Bells", "Dulcimer", "Drawbar_Organ", "Percussive_Organ", "Rock_Organ", "Church_Organ", "Reed_Organ", "Accordion", "Harmonica", "Tango_Accordion", "Acoustic_Guitar_nylon", "Acoustic_Guitar_steel", "Electric_Guitar_jazz", "Electric_Guitar_clean", "Electric_Guitar_muted", "Overdriven_Guitar", "Distortion_Guitar", "Guitar_harmonics", "Acoustic_Bass", "Electric_Bass_finger", "Electric_Bass_pick", "Fretless_Bass", "Slap_Bass_1", "Slap_Bass_2", "Synth_Bass_1", "Synth_Bass_2", "Violin", "Viola", "Cello", "Contrabass", "Tremolo_Strings", "Pizzicato_Strings", "Orchestral_Harp", "Timpani", "String_Ensemble_1", "String_Ensemble_2", "SynthString_1", "SynthString_2", "Choir_Aahs", "Voice_Oohs", "Synth_Voice", "Orchestra_Hit", "Trumpet", "Trombone", "Tuba", "Muted_Trumpet", "French_Horn", "Brass_Section", "SynthBrass_1", "SynthBrass_2", "Soprano_Sax", "Alto_Sax", "Tenor_Sax", "Baritone_Sax", "Oboe", "English_Horn", "Basoon", "Clarinet", "Piccolo", "Flute", "Recorder", "Pan_Flute", "Blown_Bottle", "Shakuhachi", "Whistle", "Ocarina", "Lead_1_square", "Lead_2_sawtooth", "Lead_3_calliope", "Lead_4_chiff", "Lead_5_charang", "Lead_6_voice", "Lead_7_fifths", "Lead_8_bass_and_lead", "Pad_1_new_age", "Pad_2_warm", "Pad_3_polysynth", "Pad_4_choir", "Pad_5_bowed", "Pad_6_metallic", "Pad_7_halo", "Pad_8_sweep", "FX_1_rain", "FX_2_soundtrack", "FX_3_crystal", "FX_4_atmosphere", "FX_5_brightness", "FX_6_goblins", "FX_7_echoes", "FX_8_sci_fi", "Sitar", "Banjo", "Shamisen", "Koto", "Kalimba", "Bag_pipe", "Fiddle", "Shanai", "Tinkle_Bell", "Agogo", "Steel_Drums", "Woodblock", "Taiko_Drum", "Melodic_Tom", "Synth_Drum", "Reverse_Cymbal", "Guitar_Fret_Noise", "Breath_Noise", "Seashore", "Bird_Tweet", "Telephone_Ring", "Helicopter", "Applause", "Gushot", "Percussion" };
	
	Part							instrument;
	Phrase							phrase;
	CPhrase							cPhrase;
	double							instant;
	int								instrumentNumber;
	int								channel;
	
	public double getInstant()
	{
		return instant;
	}// end getInstant
	
	public PrettyInstrument(int CONSTANT, int channel)
	{
		this.instrumentNumber	= CONSTANT;
		this.channel			= channel;
		instrument				= new Part(INST_NAMES[CONSTANT], CONSTANT, channel);
		phrase					= new Phrase(0.0);
		cPhrase					= new CPhrase(0.0);
		instant					= 0;
	}// end PrettyInstrument - constructor
	
	public void addChord(double duration, int... notes)
	{
		Note[] tmpNotes = new Note[notes.length];
		
		for (int i = 0; i < notes.length; i++)
			tmpNotes[i] = new Note(notes[i], duration);
		
		cPhrase.addChord(tmpNotes);
		
		this.instant += duration;
	}// end addChord
	
	public void addChord(boolean down, double duration, int... notes)
	{
		Note[] tmpNotes = new Note[notes.length];
		
		if (down)
		{
			for (int i = 0; i < notes.length; i++)
				tmpNotes[i] = new Note(notes[i] - 12, duration);
		}
		else
		{
			for (int i = 0; i < notes.length; i++)
				tmpNotes[i] = new Note(notes[i] + 12, duration);
		}// end if - else
		
		cPhrase.addChord(tmpNotes);
	}// end addChord
	
	public void addNote(int pitch, double duration)
	{
		instant += duration;
		phrase.addNote(new Note(pitch, duration));
	}// end addNote
	
	public void addSilence(double duration)
	{
		instant += duration;
		phrase.addNote(new Rest(duration));
	}// end addNote
	
	public Part returnPart()
	{
		instrument.addPhrase(phrase);
		instrument.addCPhrase(cPhrase);
		return instrument;
	}// end returnPart
	
	@Override
	public String toString()
	{
		return "PrettyInstrument{" + "instrumentNumber=" + instrumentNumber + ", channel=" + channel + '}';
	}// end toString
}// end PrettyInstrument - class