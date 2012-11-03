package org.jaudiotagger.tag.id3;

import java.nio.file.Path;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.mp3.MP3File;

/**
 * Test TDRCFrame
 */
public class FrameTDRCTest extends AbstractTestCase {
	public void testReadFileContainingTDRCAndTYERFrames() throws Exception {
		final Path testFile = AbstractTestCase.copyAudioToTmp("Issue73.id3", "testV1.mp3");

		final MP3File mp3File = new MP3File(testFile);
		final ID3v23Frame v23frame = (ID3v23Frame) mp3File.getID3v2Tag().getFrame(ID3v23Frames.FRAME_ID_V3_TYER);
		final ID3v24Frame v24frame = (ID3v24Frame) mp3File.getID3v2TagAsv24().getFrame(ID3v23Frames.FRAME_ID_V3_TYER);
	}

}
