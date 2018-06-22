package test;

import java.util.ArrayList;

import readStruct.ReadFromSocket;
import readStruct.StructRecvParams;

import structUnpack.StructUnpacker;

public class Demo {
	
	public static void main(String[] args) {
		
		ReadFromSocket reader = new ReadFromSocket();
		
		StructRecvParams params = new StructRecvParams();
		params.setServerIP("127.0.0.1");
		params.setServerPort(3000);
		params.setRequestMsg("b");
		byte[] recvBytes = reader.recvStruct(params);

		StructUnpacker unpacker = null;
		try {
			unpacker = new StructUnpacker("i i i 100c 400c");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		ArrayList<Object> buf = unpacker.unpack(recvBytes);
		
		System.out.println(buf.get(0));
		System.out.println(buf.get(1));
		System.out.println(buf.get(2));
		
		String s = (String)(buf.get(4));
		char[] recvChars = new char[s.length()];

		int rows = (int)buf.get(2);
		int cols = (int)buf.get(1);
		recvChars = s.toCharArray();
		
		String[] level = new String[rows];
		
		int pos = 0;
		for (int i = 0; i < rows; i++) {
			char[] rowData = new char[cols];
			for (int j = 0; j < cols; j++) {
				rowData[j] = recvChars[j + pos];
				level[i] = String.copyValueOf(rowData);
			}
			pos += 20;
			//System.out.println(level[i]);
		}

	}

}
