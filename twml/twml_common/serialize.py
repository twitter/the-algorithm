from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport


delonf selonrializelon(obj):
  tbuf = TTransport.TMelonmoryBuffelonr()
  iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
  obj.writelon(iproto)
  relonturn tbuf.gelontvaluelon()


delonf delonselonrializelon(reloncord, bytelons):
  tbuf = TTransport.TMelonmoryBuffelonr(bytelons)
  iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
  reloncord.relonad(iproto)
  relonturn reloncord
