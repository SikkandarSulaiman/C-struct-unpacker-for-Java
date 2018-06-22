package readStruct;

public abstract class ReadStruct {
	
	public abstract byte[] recvStruct(StructRecvParams params);
	protected abstract void validateStructParams(StructRecvParams params);

}
