package game.sound;

import java.io.*;
import java.net.URL;

import game.util.*;

public class PlayerControl {
	private BlockingClip clip;
	private LoopEvent loopClip;
	private LoopEvent loopStream;
	private byte[] rawSound;
	public boolean running;

	public PlayerControl() {}

	private byte[] readBytes(InputStream in) {
		try {
			BufferedInputStream buf = new BufferedInputStream(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read;
			while ((read = buf.read()) != -1) {
				out.write(read);
			}
			in.close();
			return out.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void shutDownClips() {
		running = false;
		if (loopClip != null)
			loopClip.done();
		if (loopStream != null)
			loopStream.done();
	}

	public void loop() {

	}

	private void increaseGain(AudioStream audio) {
		float current = audio.getGain();
		if (current < 10.0f) {
			audio.setGain(current + 3.0f);
		}
	}

	private void decreaseGain(AudioStream audio) {
		float current = audio.getGain();
		if (current > -20.0f) {
			audio.setGain(current - 3.0f);
		}
	}

	public void playBG() {		
		playSong("/resources/sound/Background.wav");
	}
	
	public void playStage1() {
		playSong("/resources/sound/Awkward_Meeting.wav");
	}
	public void playStage2() {
		playSong("/resources/sound/Come_Play_With_Me.wav");
	}
	public void playStage3() {
		playSong("/resources/sound/Town.wav");
	}
	public void playSong(String input){
		shutDownClips();
		running = true;
		FileInputStream in1;
		InputStream in = PlayerControl.class.getResourceAsStream(input);
		rawSound = readBytes(in);
		clip = new BlockingClip(rawSound);
		loopClip = new LoopEvent(clip);
		loopClip.initialize();
		loopClip.fire();
		
	}

}
