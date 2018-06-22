package readStruct;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReadFromSocket extends ReadStruct {

	@Override
	public byte[] recvStruct(StructRecvParams params) {
		
		byte[] recvBytes = new byte[512];

		try {
			DatagramSocket client = new DatagramSocket();

			InetAddress serverAddress = InetAddress.getByName(params.getServerIP());

			DatagramPacket sendPacket = new DatagramPacket(params.getRequestMsg().getBytes(), params.getRequestMsg().length(), serverAddress, params.getServerPort());
			client.send(sendPacket);

			DatagramPacket recvPacket = new DatagramPacket(recvBytes, 512, serverAddress, params.getServerPort());
			client.receive(recvPacket);

			client.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return recvBytes;
	}

	@Override
	protected void validateStructParams(StructRecvParams params) {
	    
	}


}
