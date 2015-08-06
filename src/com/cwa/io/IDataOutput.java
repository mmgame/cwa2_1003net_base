package com.cwa.io;

import java.io.IOException;

public interface IDataOutput extends IListDataOutput {
	/**
	 * 往流中写一个boolean值。
	 */
	public void writeBoolean(boolean b) throws IOException;

	/**
	 * 往流中写一个byte值。
	 */
	public void writeByte(byte b) throws IOException;

	/**
	 * 往流中写一个short值。
	 */
	public void writeShort(short s) throws IOException;

	/**
	 * 往流中写一个int值。
	 */
	public void writeInt(int i) throws IOException;

	/**
	 * 往流中写一个long值。
	 */
	public void writeLong(long l) throws IOException;

	/**
	 * 往流中写一个字节数组。
	 */
	public void writeBytes(byte[] bytes) throws IOException;

	public void writeBytes(byte[] bytes, int start, int lenght) throws IOException;

	/**
	 * 往流中写一个字符串。
	 */
	public void writeString(String s) throws IOException;

	/**
	 * 往流中写一个char值。
	 */
	public void writeChar(char c) throws IOException;

	/**
	 * 往流中写一个double值。
	 */
	public void writeDouble(double d) throws IOException;

	/**
	 * 往流中写一个float值。
	 */
	public void writeFloat(float f) throws IOException;
}
