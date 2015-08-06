/**
 * 
 */
package com.cwa.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.cwa.Constant;



/**
 * @author Administrator
 * 
 */
public class ByteArrayOutput implements IDataOutput {
	private ByteArrayOutputStream baos;
	private DataOutputStream out;

	public ByteArrayOutput() {
		baos = new ByteArrayOutputStream();
		out = new DataOutputStream(baos);
	}

	/**
	 * 返回内部的字节数组。
	 */
	public byte[] toByteArray() {
		return baos.toByteArray();
	}

	@Override
	public void writeBoolean(boolean b) throws IOException {
		out.writeBoolean(b);
	}

	@Override
	public void writeByte(byte b) throws IOException {
		out.writeByte(b);
	}

	@Override
	public void writeShort(short s) throws IOException {
		out.writeShort(s);
	}

	@Override
	public void writeInt(int i) throws IOException {
		out.writeInt(i);
	}

	@Override
	public void writeLong(long l) throws IOException {
		out.writeLong(l);
	}

	@Override
	public void writeChar(char c) throws IOException {
		out.writeChar(c);
	}

	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		out.writeShort(bytes.length);
		out.write(bytes);
	}

	@Override
	public void writeString(String s) throws IOException {
		if (s == null) {
			s = "";
		}
		byte[] data = s.getBytes(Constant.CHARSET_UTF_8);
		out.writeShort((short) data.length);
		out.write(data);
	}

	@Override
	public void writeByteList(List<Byte> list) throws IOException {
		if (list == null) {
			list = new LinkedList<Byte>();
		}
		out.writeInt(list.size());
		for (Byte l : list) {
			out.writeByte(l);
		}
	}

	@Override
	public void writeStringList(List<String> list) throws IOException {
		if (list == null) {
			list = new LinkedList<String>();
		}
		out.writeInt(list.size());
		for (String l : list) {
			writeString(l);
		}
	}

	@Override
	public void writeIntList(List<Integer> list) throws IOException {
		if (list == null) {
			list = new LinkedList<Integer>();
		}
		out.writeInt(list.size());
		for (int l : list) {
			out.writeInt(l);
		}
	}

	@Override
	public void writeLongList(List<Long> list) throws IOException {
		if (list == null) {
			list = new LinkedList<Long>();
		}
		out.writeInt(list.size());
		for (long l : list) {
			out.writeLong(l);
		}
	}

	@Override
	public void writeBytes(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
	public void writeDouble(double d) throws IOException {
		out.writeDouble(d);
	}

	@Override
	public void writeFloat(float f) throws IOException {
		out.writeFloat(f);
	}
}
