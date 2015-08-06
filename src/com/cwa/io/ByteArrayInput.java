package com.cwa.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cwa.Constant;


public class ByteArrayInput implements IDataInput {
	private DataInputStream in;

	public ByteArrayInput(byte[] data) {
		in = new DataInputStream(new ByteArrayInputStream(data));
	}

	@Override
	public int remaining() throws IOException {
		return in.available();
	}

	@Override
	public boolean readBoolean() throws IOException {
		return in.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return in.readByte();
	}

	@Override
	public short readShort() throws IOException {
		return in.readShort();
	}

	@Override
	public int readInt() throws IOException {
		return in.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return in.readLong();
	}

	@Override
	public char readChar() throws IOException {
		return in.readChar();
	}

	@Override
	public byte[] readBytes() throws IOException {
		short length = in.readShort();
		byte[] bytes = new byte[length];
		in.read(bytes);
		return bytes;
	}

	@Override
	public String readString() throws IOException {
		short length = in.readShort();
		byte[] data = new byte[length];
		in.readFully(data);
		return new String(data, Constant.CHARSET_UTF_8);
	}

	@Override
	public List<Byte> readByteList() throws IOException {
		int length = in.readInt();
		List<Byte> list = new ArrayList<Byte>(length);
		for (int i = 0; i < length; i++) {
			list.add(in.readByte());
		}
		return list;
	}

	@Override
	public List<String> readStringList() throws IOException {
		int length = in.readInt();
		List<String> list = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			list.add(readString());
		}
		return list;
	}

	@Override
	public List<Integer> readIntList() throws IOException {
		int length = in.readInt();
		List<Integer> list = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++) {
			list.add(in.readInt());
		}
		return list;
	}

	@Override
	public List<Long> readLongList() throws IOException {
		int length = in.readInt();
		List<Long> list = new ArrayList<Long>(length);
		for (int i = 0; i < length; i++) {
			list.add(in.readLong());
		}
		return list;
	}

	@Override
	public double readDouble() throws IOException {
		return in.readDouble();
	}

	@Override
	public float readFloat() throws IOException {
		return in.readFloat();
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		in.readFully(b);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		in.readFully(b, off, len);
	}

	@Override
	public int skipBytes(int n) throws IOException {
		return in.skipBytes(n);
	}
}
