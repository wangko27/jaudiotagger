package org.jaudiotagger.issues;

import java.nio.file.Path;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.ID3v23Frames;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRDA;
import org.jaudiotagger.tag.reference.ID3V2Version;

/**
 * TDRA - TDRC
 */
public class Issue435Test extends AbstractTestCase {
	public void testConvertV23TRDAToV24TRDC() {
		Throwable e = null;
		try {
			final ID3v23Frame frame = new ID3v23Frame(ID3v23Frames.FRAME_ID_V3_TRDA);
			final FrameBodyTRDA fb = new FrameBodyTRDA((byte) 0, "2008");
			frame.setBody(fb);

			final Path testFile = AbstractTestCase.copyAudioToTmp("testV25.mp3");
			AudioFile af = AudioFileIO.read(testFile);
			TagOptionSingleton.getInstance().setToDefault();
			TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V23);
			af.getTagOrCreateAndSetDefault();
			((ID3v23Tag) af.getTag()).setFrame(frame);
			af.commit();

			af = AudioFileIO.read(testFile);
			TagOptionSingleton.getInstance().setToDefault();
			TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V24);
			af.getTagAndConvertOrCreateAndSetDefault();
			af.commit();
			assertTrue(af.getTag() instanceof ID3v24Tag);
			assertTrue(((ID3v24Tag) af.getTag()).getFrame("TDRC") instanceof AbstractID3v2Frame);

			TagOptionSingleton.getInstance().setToDefault();
		} catch (final Exception ex) {
			e = ex;
		}
		assertNull(e);
	}
}
