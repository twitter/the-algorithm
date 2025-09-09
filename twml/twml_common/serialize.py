from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport


def serialize(obj: TBinaryProtocol.TBinaryProtocol) -> bytes:
    """
    Serialize a thrift object into a byte string

    Args:
        obj: the thrift object to serialize

    Returns:
        The serialized thrift object
    """
    tbuf = TTransport.TMemoryBuffer()
    iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
    obj.write(iproto)
    return tbuf.getvalue()


def deserialize(
    record: TBinaryProtocol.TBinaryProtocol, bytes: bytes
) -> TBinaryProtocol.TBinaryProtocol:
    """
    Deserialize a thrift object from a byte string

    Args:
        record: the thrift object to deserialize into
        bytes: the byte string to deserialize from

    Returns:
        The deserialized thrift object
    """
    tbuf = TTransport.TMemoryBuffer(bytes)
    iproto = TBinaryProtocol.TBinaryProtocol(tbuf)
    record.read(iproto)
    return record
