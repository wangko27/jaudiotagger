package org.jaudiotagger.audio.mp3;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.generic.AudioFileWriter;
import org.jaudiotagger.tag.Tag;

/**
 * Write Mp3 Info (retrofitted to entagged ,done differently to entagged which is why some methods throw
 * RuntimeException) because done elsewhere
 */
public class MP3FileWriter extends AudioFileWriter {
	public void deleteTag(final AudioFile f) throws CannotWriteException {
		// Because audio file is an instanceof MP3File this directs it to save
		// taking into account if the tag has been sent to null in which case it will be deleted
		f.commit();
	}

	public void writeFile(final AudioFile f) throws CannotWriteException {
		// Because audio file is an instanceof MP3File this directs it to save
		f.commit();
	}

	/**
	 * Delete the Id3v1 and ID3v2 tags from file
	 * 
	 * @param af
	 * @throws CannotReadException
	 * @throws CannotWriteException
	 */
	@Override
	public synchronized void delete(final AudioFile af) throws CannotReadException, CannotWriteException {
		((MP3File) af).setID3v1Tag(null);
		((MP3File) af).setID3v2Tag(null);
		af.commit();
	}

	@Override
	protected void writeTag(final Tag tag, final RandomAccessFile raf, final RandomAccessFile rafTemp) throws CannotWriteException, IOException {
		throw new RuntimeException("MP3FileReaderwriteTag should not be called");
	}

	@Override
	protected void deleteTag(final RandomAccessFile raf, final RandomAccessFile tempRaf) throws CannotWriteException, IOException {
		throw new RuntimeException("MP3FileReader.getEncodingInfo should be called");
	}
}
