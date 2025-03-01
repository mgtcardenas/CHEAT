/*

<This Java Class is part of the jMusic API version 1.5, March 2004.>

Copyright (C) 2000 Andrew Brown

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or any
later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.

*/

package jm.gui.cpn;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

import java.awt.*;
import java.awt.event.*;

public class JStaveActionHandler implements JMC, MouseListener, MouseMotionListener, ActionListener { //, KeyListener {

    private JGrandStave theApp;
	private int selectedNote = -1;
	private boolean topTimeSelected = false, keySelected = false;
	private int clickedPosY, clickedPosX, storedPitch = 72;
	private double[] rhythmValues = {104.0, 103.0, 102.0, 101.5, 101.0, 100.75, 100.5, 100.25, 0.0, 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 3.0, 4.0};
	
	// constructor
    JStaveActionHandler(JGrandStave stave) {
		theApp = stave;
		// allow for negative rv's as a rest flag
	}

	// Mouse Listener stubs
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	//mouseMotionListener stubs
	public void mouseMoved(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(!theApp.editable) return;
		// add a new note if necessary
		// no notes yet?
		Integer lastX;
		if (theApp.notePositions.size() < 2) {
			lastX =  new Integer(theApp.getTotalBeatWidth() );
		} else {
			lastX = (Integer)theApp.notePositions.elementAt(theApp.notePositions.size() -2);
		}
		if(e.getX() > (lastX.intValue() + 15) && e.getX() < theApp.getTotalBeatWidth() + 50) {
			
			// new note close to mouse click pitch
			int newPitch = 85 - (e.getY() + theApp.staveDelta - theApp.bPos)/2;
			// make sure it is not an accidental
			int[] blackNotes = {1,3,6,8,10};
			boolean white = true;
			for(int k=0;k<blackNotes.length;k++) {
				if(newPitch%12 == blackNotes[k]) newPitch--;
			}
			Note n = new Note(newPitch,1.0);
			Phrase phr = theApp.getPhrase();
			phr.addNote(n);
			theApp.repaint();
			// set cursor
			theApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// get ready to drag it
			selectedNote = phr.size()-1;
			//theApp.playCurrentNote(selectedNote);
			clickedPosY = e.getY()  + theApp.staveDelta;
			clickedPosX = e.getX();
		} else {
			// check for a click on a note  - head?
			for(int i=0;i< theApp.notePositions.size(); i += 2) {
				Integer tempX = (Integer)theApp.notePositions.elementAt(i);
				Integer tempY = (Integer)theApp.notePositions.elementAt(i+1);
				if(e.getX() > tempX.intValue() && e.getX() < tempX.intValue() + 15 && e.getY() + 
						theApp.staveDelta > tempY.intValue() + 22 && e.getY() + 
						theApp.staveDelta < tempY.intValue() + 35) {
					// set cursor
					theApp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					selectedNote = i/2;
					clickedPosY = e.getY() + theApp.staveDelta;
					clickedPosX = e.getX();
					//System.out.println("Hit note "+ selectedNote);
					// get out of loop ASAP
					i = theApp.notePositions.size();
				}
			}
		}
		if (selectedNote < 0) { // no note clicked on or yet made
			// check which note to insert
			for(int j=0;j< theApp.notePositions.size() - 2; j += 2) {
				Integer tempX = (Integer)theApp.notePositions.elementAt(j);
				Integer nextTempX = (Integer)theApp.notePositions.elementAt(j+2);
				if(e.getX() > tempX.intValue() + 15 && e.getX() < nextTempX.intValue()) {
					// change cursor
					theApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// add new note close to mouse clicked pitch
					int newPitch = 85 - (e.getY() + theApp.staveDelta - theApp.bPos)/2;
					// make sure it is not an accidental
					int[] blackNotes = {1,3,6,8,10};
					boolean white = true;
					for(int k=0;k<blackNotes.length;k++) {
						if(newPitch%12 == blackNotes[k]) newPitch--;
					}					
					Note n = new Note(newPitch ,1.0);
					Phrase phr = theApp.getPhrase();
					phr.getNoteList().insertElementAt(n, j/2 + 1);
					theApp.repaint();
					// play and update variables for dragging it
					selectedNote = j/2 + 1;
					clickedPosY = e.getY() + theApp.staveDelta;
					clickedPosX = e.getX();
					// get out of the loop
					j = theApp.notePositions.size();
				}
			}
		}
		// check if the time signature is clicked
		int theSpace = theApp.rightMargin + theApp.clefWidth + theApp.keySigWidth;
		if(e.getX() > theSpace && e.getX() < theSpace + 10) {
			theApp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			topTimeSelected = true;
			clickedPosY = e.getY() + theApp.staveDelta;
			clickedPosX = e.getX();
			//System.out.println("Time Signature clicked!");
		}
		// check if the key signature is clicked
		int theClefSpace = theApp.rightMargin + theApp.clefWidth;
		int minKeySpace = 10;
		if (theApp.keySigWidth > minKeySpace) minKeySpace = theApp.keySigWidth;
		if(e.getX() > theClefSpace - 10 && e.getX() < theClefSpace + minKeySpace) {
			theApp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			keySelected = true;
			clickedPosY = e.getY() + theApp.staveDelta;
			clickedPosX = e.getX();
			//System.out.println("Key Signature clicked!");
		}
	}
		
	public void mouseDragged(MouseEvent e) {
		if(!theApp.editable) return;
		//theApp.dragNote(e.getX(), e.getY());
		if (selectedNote >= 0) {
			//System.out.println("Dragging "+ selectedNote);
			Phrase phr = theApp.getPhrase();
			Note n = phr.getNote(selectedNote);
			// move note down
			if(e.getY() + theApp.staveDelta > clickedPosY + 2 && theApp.getPhrase().getNote(selectedNote).getPitch() != REST) {
				n.setPitch(n.getPitch() - 1);
				if (n.getPitch() < theApp.getMinPitch()) n.setPitch(theApp.getMinPitch());
				// update the current mouse location
				clickedPosY += 2;
				// update the visual display
				theApp.repaint();
				// keep pitch to reinstate it after displaying a rest
				storedPitch = n.getPitch();
			}
			// move note up
			if(e.getY() + theApp.staveDelta < clickedPosY - 2  && theApp.getPhrase().getNote(selectedNote).getPitch() != REST) {
				n.setPitch(n.getPitch() + 1);
				if (n.getPitch() > theApp.getMaxPitch()) n.setPitch(theApp.getMaxPitch());
				//theApp.playCurrentNote(selectedNote);
				clickedPosY -= 2;
				theApp.repaint();
				storedPitch = n.getPitch();
			}
			// move note right - increase RV
			if(e.getX() > clickedPosX + 6) {
				double tempRV = n.getRhythmValue();
				int tempPitch = n.getPitch();
				// use +100 numbers for rests
				if (tempPitch == REST) tempRV = tempRV + 100;
				int currRVindex = rhythmValues.length;
				// find current rhythm value and update RV
				for(int i=0; i<rhythmValues.length - 1;i++) {
					if(tempRV == rhythmValues[i]) n.setRhythmValue(rhythmValues[i + 1]); 
				}				
				clickedPosX = e.getX();
				// update pitch
				if (n.getRhythmValue() > 100.0) {
					n.setPitch(REST);
					n.setRhythmValue(n.getRhythmValue() - 100);
					// update duration value
					n.setDuration(n.getRhythmValue()*0.9);
				} else {
					if (tempPitch == REST) n.setPitch(storedPitch);
				}
				theApp.repaint();
			}
			// move note left - decrease RV
			if(e.getX() < clickedPosX - 6) {
				double tempRV = n.getRhythmValue();
				int tempPitch = n.getPitch();
				// use +100 numbers for rests
				if (tempPitch == REST) tempRV = tempRV + 100;
				// find current rhythm value position in the array
				int currRVindex = 0;
				for(int i=0; i<rhythmValues.length;i++) {
					if(tempRV == rhythmValues[i]) currRVindex = i;
				}
				// update rv
				if (currRVindex > 0) {
					n.setRhythmValue(rhythmValues[currRVindex - 1]);
					clickedPosX = e.getX();
					// update pitch
					if (n.getRhythmValue() > 100.0) {
						n.setPitch(REST);
						n.setRhythmValue(n.getRhythmValue()-100);
						// update duration value
						n.setDuration(n.getRhythmValue()*0.9);
					} else {
						if (tempPitch == REST) n.setPitch(storedPitch);
					}
					theApp.repaint();
				}
			}
		} 
		// check for time signature change
		if (topTimeSelected) {
			//increase?
			if(e.getY() + theApp.staveDelta< clickedPosY - 4  ) {
				theApp.setMetre(theApp.getMetre() + 1.0);
				if (theApp.getMetre() > 9.0) theApp.setMetre(9.0);
				if (theApp.getMetre() < 1.0) theApp.setMetre(1.0);
				clickedPosY -= 4;
				theApp.repaint();
				theApp.updateChange();
			}
			//decrease
			if(e.getY() + theApp.staveDelta > clickedPosY + 4) {
				theApp.setMetre(theApp.getMetre() - 1.0);
				if (theApp.getMetre() < 1.0) theApp.setMetre(1.0);
				if (theApp.getMetre() > 9.0) theApp.setMetre(9.0);
				clickedPosY += 4;
				theApp.repaint();
				theApp.updateChange();
			}
		}
		// check for key signature change
		if (keySelected) {
			//increase?
			if(e.getY() + theApp.staveDelta < clickedPosY - 4) {
				theApp.setKeySignature(theApp.getKeySignature() + 1);
				if (theApp.getKeySignature() > 7) theApp.setKeySignature(7);
				clickedPosY -= 4;
				theApp.repaint();
				theApp.updateChange();
			}
			//decrease
			if(e.getY() + theApp.staveDelta > clickedPosY + 4) {
				theApp.setKeySignature(theApp.getKeySignature() - 1);
				if (theApp.getKeySignature() < -7) theApp.setKeySignature(-7);
				clickedPosY += 4;
				theApp.repaint();
				theApp.updateChange();
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(!theApp.editable) return;
		// delete note if necessary
		for(int i=0; i< theApp.getPhrase().getNoteList().size(); i++) {
			if (theApp.getPhrase().getNote(i).getRhythmValue() == 0.0) {
				theApp.getPhrase().getNoteList().removeElementAt(i);
			}
		}
		// unflag any note being selected
		theApp.repaint();
		theApp.updateChange();
		selectedNote = -1;
		topTimeSelected = false;
		keySelected = false;
		theApp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void actionPerformed(ActionEvent e) { // 1.1 Event Model. Trap button clicks.
	}
	
	// key listener stubs
	/*
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == '\b') theApp.deleteLastNote();
	}
	*/
}
