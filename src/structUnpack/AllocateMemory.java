package structUnpack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public final class AllocateMemory extends StructUnpacker {
    
    private String getClassName(String type) {
	String dtype = type.replaceAll("[0-9]", "");		
	for (String className: typeKey.keySet()) {
	    if (typeKey.get(className).contains(dtype))
		return className;
	}
	return null;
    }

    public void fillUnpacked(byte[] msg) {
	
	String[] types = this.format.split(" ");
	ByteBuffer msgBuf = ByteBuffer.wrap(msg).order(ByteOrder.LITTLE_ENDIAN);

	for (String type: types) {

		String className = this.getClassName(type);

		int count = 0;
		try {
			count = Integer.parseInt(type.replaceAll("[a-zA-Z]", ""));
		} catch (NumberFormatException e) {
			count = 1;
		}

		switch (className) {
		case "Integer":
			if (count == 1) {
				this.unpacked.add(msgBuf.getInt());
			} else {
			    int[] array = new int[count];
			    for (int i = 0; count-- != 0; i++)
				array[i] = msgBuf.getInt();
			    this.unpacked.add(array);
			}
			break;
			
		case "Float":
			if (count == 1) {
				this.unpacked.add(msgBuf.getFloat());
			} else {
			    float[] array = new float[count];
			    for (int i = 0; count-- != 0; i++)
				array[i] = msgBuf.getFloat();
			    this.unpacked.add(array);
			}
			break;
			
		case "Double":
			if (count == 1) {
				this.unpacked.add(msgBuf.getDouble());
			} else {
			    double[] array = new double[count];
			    for (int i = 0; count-- != 0; i++)
				array[i] = msgBuf.getDouble();
			    this.unpacked.add(array);
			}
			break;
			
		case "Short":
			if (count == 1) {
				this.unpacked.add(msgBuf.getShort());
			} else {
			    short[] array = new short[count];
			    for (int i = 0; count-- != 0; i++)
				array[i] = msgBuf.getShort();
			    this.unpacked.add(array);
			}
			break;
			
		case "Long":
			if (count == 1) {
				this.unpacked.add(msgBuf.getLong());
			} else {
			    long[] array = new long[count];
			    for (int i = 0; count-- != 0; i++)
				array[i] = msgBuf.getLong();
			    this.unpacked.add(array);
			}
			break;

		case "Character":
			if (count == 1) {
				this.unpacked.add(msgBuf.get());
			} else {
			    byte[] buf = new byte[count];
			    msgBuf.get(buf, 0, count);
			    String str = new String(buf, Charset.forName("utf-8"));
			    this.unpacked.add(str.toString());
			}
			break;
		}
	}
}

}
