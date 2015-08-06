package com.cwa.io;

import java.io.IOException;
import java.util.List;

public interface IListDataOutput {
	void writeByteList(List<Byte> list) throws IOException;

	void writeStringList(List<String> list) throws IOException;

	void writeIntList(List<Integer> list) throws IOException;

	void writeLongList(List<Long> list) throws IOException;
}
