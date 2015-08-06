package com.cwa.io;

import java.io.IOException;

/**
 * 可读写接口
 * 
 * @author Administrator
 * 
 */
public interface IWritable {
	void write(IDataOutput out) throws IOException;

	void readFields(IDataInput in) throws IOException;
}
