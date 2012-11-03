/**
 *  @author : Paul Taylor
 *  @author : Eric Farng
 *
 *  Version @version:$Id$
 *
 *  MusicTag Copyright (C)2003,2004
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 *  or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 *  you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Description:
 *
 */
package org.jaudiotagger.tag.datatype;

import org.jaudiotagger.audio.generic.Utils;
import org.jaudiotagger.tag.InvalidDataTypeException;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;

public class Lyrics3TimeStamp extends AbstractDataType {
	/**
     *
     */
	private long minute = 0;

	/**
     *
     */
	private long second = 0;

	/**
	 * Todo this is wrong
	 * 
	 * @param s
	 */
	public void readString(final String s) {}

	/**
	 * Creates a new ObjectLyrics3TimeStamp datatype.
	 * 
	 * @param identifier
	 * @param frameBody
	 */
	public Lyrics3TimeStamp(final String identifier, final AbstractTagFrameBody frameBody) {
		super(identifier, frameBody);
	}

	public Lyrics3TimeStamp(final String identifier) {
		super(identifier, null);
	}

	public Lyrics3TimeStamp(final Lyrics3TimeStamp copy) {
		super(copy);
		this.minute = copy.minute;
		this.second = copy.second;
	}

	public void setMinute(final long minute) {
		this.minute = minute;
	}

	/**
	 * @return
	 */
	public long getMinute() {
		return minute;
	}

	public void setSecond(final long second) {
		this.second = second;
	}

	/**
	 * @return
	 */
	public long getSecond() {
		return second;
	}

	/**
	 * @return
	 */
	@Override
	public int getSize() {
		return 7;
	}

	/**
	 * Creates a new ObjectLyrics3TimeStamp datatype.
	 * 
	 * @param timeStamp
	 * @param timeStampFormat
	 */
	public void setTimeStamp(long timeStamp, final byte timeStampFormat) {
		/**
		 * @todo convert both types of formats
		 */
		timeStamp = timeStamp / 1000;
		minute = timeStamp / 60;
		second = timeStamp % 60;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Lyrics3TimeStamp))
			return false;

		final Lyrics3TimeStamp object = (Lyrics3TimeStamp) obj;

		if (this.minute != object.minute)
			return false;

		return this.second == object.second && super.equals(obj);

	}

	/**
	 * @param timeStamp
	 * @param offset
	 * @throws NullPointerException
	 * @throws IndexOutOfBoundsException
	 */
	public void readString(String timeStamp, final int offset) {
		if (timeStamp == null)
			throw new NullPointerException("Image is null");

		if ((offset < 0) || (offset >= timeStamp.length()))
			throw new IndexOutOfBoundsException("Offset to timeStamp is out of bounds: offset = " + offset + ", timeStamp.length()" + timeStamp.length());

		timeStamp = timeStamp.substring(offset);

		if (timeStamp.length() == 7) {
			minute = Integer.parseInt(timeStamp.substring(1, 3));
			second = Integer.parseInt(timeStamp.substring(4, 6));
		} else {
			minute = 0;
			second = 0;
		}
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return writeString();
	}

	/**
	 * @return
	 */
	public String writeString() {
		String str;
		str = "[";

		if (minute < 0)
			str += "00";
		else {
			if (minute < 10)
				str += '0';

			str += Long.toString(minute);
		}

		str += ':';

		if (second < 0)
			str += "00";
		else {
			if (second < 10)
				str += '0';

			str += Long.toString(second);
		}

		str += ']';

		return str;
	}

	@Override
	public void readByteArray(final byte[] arr, final int offset) throws InvalidDataTypeException {
		readString(arr.toString(), offset);
	}

	@Override
	public byte[] writeByteArray() {
		return Utils.getDefaultBytes(writeString(), "ISO8859-1");
	}

}
