from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport


def serialize(obj):
  tbuf = TTransport.TMemoryBuffer()
  iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
  obj.write(iproto)
  return tbuf.getvalue()


def deserialize(record, bytes):
  tbuf = TTransport.TMemoryBuffer(bytes)
  iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
  record.read(iproto)
  return record
