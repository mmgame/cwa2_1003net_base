package com.cwa.io;

import java.io.IOException;

public interface IDataInput extends IListDataInput {
	/**
	 * 返回剩余字节数。
	 */
	public int remaining() throws IOException;

	/**
	 * 从流中读取一个boolean值。
	 */
	public boolean readBoolean() throws IOException;

	/**
	 * 从流中读取一个byte值。
	 */
	public byte readByte() throws IOException;

	/**
	 * 从流中读取一个short值。
	 */
	public short readShort() throws IOException;

	/**
	 * 从流中读取一个int值。
	 */
	public int readInt() throws IOException;

	/**
	 * 从流中读取一个long值。
	 */
	public long readLong() throws IOException;

	/**
	 * 从流中读取一个字节数组。
	 */
	public byte[] readBytes() throws IOException;

	/**
	 * 从流中读取一个字符串。
	 */
	public String readString() throws IOException;

	/**
	 * 从流中读取一个char值。
	 */
	public char readChar() throws IOException;

	/**
	 * 从流中读取一个double值。
	 */
	public double readDouble() throws IOException;

	/**
	 * 从流中读取一个float值。
	 */
	public float readFloat() throws IOException;

	public void readFully(byte[] buffer) throws IOException;

	public void readFully(byte[] buffer, int start, int length) throws IOException;

	public int skipBytes(int n) throws IOException;
}
