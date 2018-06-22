package structUnpack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@SuppressWarnings("unchecked")
public class StructUnpacker {
	
	private int size = 0;
	protected String format = null;
	protected ArrayList<Object> unpacked = new ArrayList<>();
	protected static Map<String, ArrayList<String>> typeKey = new HashMap<>();
	
	static {
	    FileReader jsonReader = null;
	    try {
		jsonReader = new FileReader("classnames.json");
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    }
	    
	    Gson gson = new Gson();
	    try {
		typeKey = gson.fromJson(jsonReader, HashMap.class);
	    } catch (JsonSyntaxException | JsonIOException e) {
		e.printStackTrace();
	    }
	    Collections.unmodifiableMap(typeKey);
	    
	    try {
		jsonReader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) throws NoSuchFieldException {
		this.format = format;
		this.size = calcSize(this.format);
	}

	public int getSize() {
		return this.size;
	}

	public StructUnpacker() {
		
	}
	
	public StructUnpacker(String format) throws NoSuchFieldException {
		this.format = format;
		this.size = calcSize(this.format);
	}
	
	private int calcSize(String format) {

		String[] types = format.split(" ");
		int size = 0;

		for (String type: types) {		
			int count;
			try {
			    count = Integer.parseInt(type.replaceAll("[a-zA-Z]", ""));
			} catch (NumberFormatException e) {
			    count = 1;
			}

			switch (type.replaceAll("[0-9]", "")) {
			case "i":
			case "I":
				size += CTypeSize.INT * count;
				break;

			case "f":
			case "F":
				size += CTypeSize.FLOAT * count;
				break;

			case "d":
			case "D":
				size += CTypeSize.DOUBLE * count;
				break;

			case "c":
			case "C":
				size += CTypeSize.CHAR * count;
				break;

			case "h":
			case "H":
				size += CTypeSize.SHORT * count;
				break;
				
			case "l":
			case "L":
			    	size += CTypeSize.LONG * count;
			    	break;

			case "x":
			case "X":
				size += 1 * count;

			default:
				try {
					throw new NoSuchFieldException();
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
		return size;
	}
	
	public ArrayList<Object> unpack(byte[] msg) {
		
		if (msg.length < this.size)
		    throw new IndexOutOfBoundsException();

		new AllocateMemory().fillUnpacked( msg);
		
		if (unpacked.size() < this.size)
		    throw new BufferUnderflowException();
		
		return unpacked;
		
	}

}
