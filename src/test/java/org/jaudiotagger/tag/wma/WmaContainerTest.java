package org.jaudiotagger.tag.wma;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.asf.data.Chunk;
import org.jaudiotagger.audio.asf.data.GUID;
import org.jaudiotagger.audio.asf.data.MetadataContainer;
import org.jaudiotagger.audio.asf.data.MetadataContainerUtils;
import org.jaudiotagger.audio.asf.io.AsfHeaderUtils;
import org.jaudiotagger.audio.asf.io.MetadataReader;
import org.jaudiotagger.audio.asf.util.Utils;

/**
 * @author Christian Laireiter
 * 
 */
public class WmaContainerTest extends WmaTestCase {

	public final static String TEST_FILE = "test6.wma";

	public WmaContainerTest() {
		super(TEST_FILE);
	}

	public void testExtContentAfterWrite() throws Exception {
		final Path prepareTestFile = prepareTestFile(null);
		final AudioFile read = AudioFileIO.read(prepareTestFile);
		read.commit(); // Normalize Text file
		final byte[] ext = AsfHeaderUtils.getFirstChunk(read.getFile(), GUID.GUID_EXTENDED_CONTENT_DESCRIPTION);
		read.commit();
		final byte[] ext2 = AsfHeaderUtils.getFirstChunk(read.getFile(), GUID.GUID_EXTENDED_CONTENT_DESCRIPTION);
		assertTrue(Arrays.equals(ext, ext2));
		// assertEquals(ext, ext2);
	}

	public void testReadWriteEquality() throws IOException {
		final Path prepareTestFile = prepareTestFile(null);
		final byte[] tmp = AsfHeaderUtils.getFirstChunk(prepareTestFile, GUID.GUID_EXTENDED_CONTENT_DESCRIPTION);

		final MetadataReader reader = new MetadataReader();
		ByteArrayInputStream bis = new ByteArrayInputStream(tmp);
		GUID readGUID = Utils.readGUID(bis);
		assertEquals(GUID.GUID_EXTENDED_CONTENT_DESCRIPTION, readGUID);
		final Chunk read1 = reader.read(GUID.GUID_EXTENDED_CONTENT_DESCRIPTION, bis, 0);
		System.out.println(read1);
		assertTrue(read1 instanceof MetadataContainer);
		assertEquals(tmp.length, ((MetadataContainer) read1).getCurrentAsfChunkSize());
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		((MetadataContainer) read1).writeInto(bos);
		assertEquals(tmp.length, bos.toByteArray().length);
		bis = new ByteArrayInputStream(bos.toByteArray());
		readGUID = Utils.readGUID(bis);
		assertEquals(GUID.GUID_EXTENDED_CONTENT_DESCRIPTION, readGUID);
		final Chunk read2 = reader.read(GUID.GUID_EXTENDED_CONTENT_DESCRIPTION, bis, 0);
		System.out.println(MetadataContainerUtils.equals((MetadataContainer) read1, (MetadataContainer) read2));
	}

}
