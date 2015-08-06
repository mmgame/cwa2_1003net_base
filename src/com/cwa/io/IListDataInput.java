package com.cwa.io;

import java.io.IOException;
import java.util.List;

public interface IListDataInput {
	List<Byte> readByteList() throws IOException;

	List<String> readStringList() throws IOException;

	List<Integer> readIntList() throws IOException;

	List<Long> readLongList() throws IOException;
}
