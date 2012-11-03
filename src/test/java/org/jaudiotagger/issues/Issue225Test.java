package org.jaudiotagger.issues;

import java.nio.file.Files;
import java.nio.file.Path;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 * Test Mp4 genres can be invalid
 */
public class Issue225Test extends AbstractTestCase {
	/**
	 * Reading a file contains genre field but set to invalid value 149, because Mp4genreField always store the value
	 * the genre is mapped to we return null. This is correct behaviour.
	 */
	public void testReadInvalidGenre() {
		String genre = null;

		final Path orig = AbstractTestCase.dataPath.resolve("test30.m4a");
		if (!Files.isRegularFile(orig))
			return;

		Exception exceptionCaught = null;
		try {
			final Path testFile = AbstractTestCase.copyAudioToTmp("test30.m4a");
			final AudioFile f = AudioFileIO.read(testFile);
			final Tag tag = f.getTag();
			genre = tag.getFirst(FieldKey.GENRE);
		} catch (final Exception e) {
			e.printStackTrace();
			exceptionCaught = e;
		}
		assertNull(exceptionCaught);
		assertNull(genre);

	}
}
