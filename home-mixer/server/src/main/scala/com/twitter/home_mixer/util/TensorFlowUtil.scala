package com.ExTwitter.home_mixer.util

import com.ExTwitter.ml.api.thriftscala.FloatTensor
import com.ExTwitter.ml.api.util.BufferToIterators.RichFloatBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Contains functionality to transform data records and Tensors
 */

object TensorFlowUtil {

  private def skipEmbeddingBBHeader(bb: ByteBuffer): ByteBuffer = {
    val bb_copy = bb.duplicate()
    bb_copy.getLong()
    bb_copy
  }

  private def byteBufferToFloatIterator(
    bb: ByteBuffer
  ): Iterator[Float] = {
    bb.order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer.iterator
  }

  def embeddingByteBufferToFloatTensor(
    bb: ByteBuffer
  ): FloatTensor = {
    val bb_content = skipEmbeddingBBHeader(bb)
    FloatTensor(byteBufferToFloatIterator(bb_content).map(_.toDouble).toList)
  }
}
