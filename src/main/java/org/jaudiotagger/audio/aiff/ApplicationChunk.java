package org.jaudiotagger.audio.aiff;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.jaudiotagger.audio.generic.Utils;

public class ApplicationChunk extends Chunk {

	// private AiffTag aiffTag;
	private final AiffAudioHeader aiffHeader;

	/**
	 * Constructor.
	 * 
	 * @param hdr
	 *            The header for this chunk
	 * @param raf
	 *            The file from which the AIFF data are being read
	 * @param tag
	 *            The AiffTag into which information is stored
	 */
	public ApplicationChunk(final ChunkHeader hdr, final RandomAccessFile raf, final AiffAudioHeader aHdr) {
		super(raf, hdr);
		aiffHeader = aHdr;
	}

	/**
	 * Reads a chunk and puts an Application property into the RepInfo object.
	 * 
	 * @return <code>false</code> if the chunk is structurally invalid, otherwise <code>true</code>
	 */
	@Override
	public boolean readChunk() throws IOException {
		final String applicationSignature = Utils.readString(raf, 4);
		String applicationName = null;
		final byte[] data = new byte[(int) (bytesLeft - 4)];
		raf.readFully(data);
		// If the application signature is 'pdos' or 'stoc',
		// then the beginning of the data area is a Pascal
		// string naming the application. Otherwise, we
		// ignore the data. ('pdos' is for Apple II
		// applications, 'stoc' for the entire non-Apple world.)
		if ("stoc".equals(applicationSignature) || "pdos".equals(applicationSignature))
			applicationName = AiffUtil.bytesToPascalString(data);
		aiffHeader.addApplicationIdentifier(applicationSignature + ": " + applicationName);

		return true;
	}
}
