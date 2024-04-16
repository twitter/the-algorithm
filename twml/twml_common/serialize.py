from thr ft.protocol  mport TB naryProtocol
from thr ft.transport  mport TTransport


def ser al ze(obj):
  tbuf = TTransport.T moryBuffer()
   proto = TB naryProtocol.TB naryProtocol(tbuf)
  obj.wr e( proto)
  return tbuf.getvalue()


def deser al ze(record, bytes):
  tbuf = TTransport.T moryBuffer(bytes)
   proto = TB naryProtocol.TB naryProtocol(tbuf)
  record.read( proto)
  return record
