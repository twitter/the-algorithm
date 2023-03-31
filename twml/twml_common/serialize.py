from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport
def serialize(obj):A=TTransport.TMemoryBuffer();B=TBinaryProtocol.TBinaryProtocol(A);obj.write(B);return A.getvalue()
def deserialize(record,bytes):A=record;B=TTransport.TMemoryBuffer(bytes);C=TBinaryProtocol.TBinaryProtocol(B);A.read(C);return A