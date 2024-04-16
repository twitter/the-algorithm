package com.tw ter.tsp.ut ls

 mport com.tw ter.b ject on. nject on
 mport scala.ut l.Try
 mport net.jpountz.lz4.LZ4CompressorW hLength
 mport net.jpountz.lz4.LZ4DecompressorW hLength
 mport net.jpountz.lz4.LZ4Factory

object LZ4 nject on extends  nject on[Array[Byte], Array[Byte]] {
  pr vate val lz4Factory = LZ4Factory.fastest nstance()
  pr vate val fastCompressor = new LZ4CompressorW hLength(lz4Factory.fastCompressor())
  pr vate val decompressor = new LZ4DecompressorW hLength(lz4Factory.fastDecompressor())

  overr de def apply(a: Array[Byte]): Array[Byte] = LZ4 nject on.fastCompressor.compress(a)

  overr de def  nvert(b: Array[Byte]): Try[Array[Byte]] = Try {
    LZ4 nject on.decompressor.decompress(b)
  }
}
