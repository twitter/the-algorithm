package com.twitter.tsp.utils

import com.twitter.bijection.Injection
import scala.util.Try
import net.jpountz.lz4.LZ4CompressorWithLength
import net.jpountz.lz4.LZ4DecompressorWithLength
import net.jpountz.lz4.LZ4Factory

object LZ4Injection extends Injection[Array[Byte], Array[Byte]] {
  private val lz4Factory = LZ4Factory.fastestInstance()
  private val fastCompressor = new LZ4CompressorWithLength(lz4Factory.fastCompressor())
  private val decompressor = new LZ4DecompressorWithLength(lz4Factory.fastDecompressor())

  override def apply(a: Array[Byte]): Array[Byte] = LZ4Injection.fastCompressor.compress(a)

  override def invert(b: Array[Byte]): Try[Array[Byte]] = Try {
    LZ4Injection.decompressor.decompress(b)
  }
}
