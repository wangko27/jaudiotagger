package org.jaudiotagger.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jaudiotagger.audio.mp3.MP3File;

/**
 * Simple class that will attempt to recursively read all files within a directory, flags errors that occur.
 */
public class ExtractID3TagFromFile {

	public static void main(final String[] args) {
		final ExtractID3TagFromFile test = new ExtractID3TagFromFile();

		if (args.length != 2) {
			System.err.println("usage ExtractID3TagFromFile Filename FilenameOut");
			System.err.println("      You must enter the file to extract the tag from and where to extract to");
			System.exit(1);
		}

		final Path file = Paths.get(args[0]);
		final Path outFile = Paths.get(args[1]);
		if (!Files.isRegularFile(file)) {
			System.err.println("usage ExtractID3TagFromFile Filename FilenameOut");
			System.err.println("      File " + args[0] + " could not be found");
			System.exit(1);
		}

		try {
			final MP3File tmpMP3 = new MP3File(file);
			tmpMP3.extractID3v2TagDataIntoFile(outFile);
		} catch (final Exception e) {
			System.err.println("Unable to extract tag");
			System.exit(1);
		}
	}

	final class MP3FileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter {

		/**
		 * allows Directories
		 */
		private final boolean allowDirectories;

		/**
		 * Create a default MP3FileFilter. The allowDirectories field will default to false.
		 */
		public MP3FileFilter() {
			this(false);
		}

		/**
		 * Create an MP3FileFilter. If allowDirectories is true, then this filter will accept directories as well as mp3
		 * files. If it is false then only mp3 files will be accepted.
		 * 
		 * @param allowDirectories
		 *            whether or not to accept directories
		 */
		private MP3FileFilter(final boolean allowDirectories) {
			this.allowDirectories = allowDirectories;
		}

		/**
		 * Determines whether or not the file is an mp3 file. If the file is a directory, whether or not is accepted
		 * depends upon the allowDirectories flag passed to the constructor.
		 * 
		 * @param file
		 *            the file to test
		 * @return true if this file or directory should be accepted
		 */
		@Override
		public final boolean accept(final File file) {
			return (((file.getName()).toLowerCase().endsWith(".mp3")) || (file.isDirectory() && (this.allowDirectories)));
		}

		/**
		 * Returns the Name of the Filter for use in the Chooser Dialog
		 * 
		 * @return The Description of the Filter
		 */
		@Override
		public final String getDescription() {
			return ".mp3 Files";
		}
	}

	public static final String IDENT = "$Id$";

}
