package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ml.ap .thr ftscala.FloatTensor
 mport com.tw ter.ml.ap .ut l.BufferTo erators.R chFloatBuffer
 mport java.n o.ByteBuffer
 mport java.n o.ByteOrder

/**
 * Conta ns funct onal y to transform data records and Tensors
 */

object TensorFlowUt l {

  pr vate def sk pEmbedd ngBB ader(bb: ByteBuffer): ByteBuffer = {
    val bb_copy = bb.dupl cate()
    bb_copy.getLong()
    bb_copy
  }

  pr vate def byteBufferToFloat erator(
    bb: ByteBuffer
  ):  erator[Float] = {
    bb.order(ByteOrder.L TTLE_END AN).asFloatBuffer. erator
  }

  def embedd ngByteBufferToFloatTensor(
    bb: ByteBuffer
  ): FloatTensor = {
    val bb_content = sk pEmbedd ngBB ader(bb)
    FloatTensor(byteBufferToFloat erator(bb_content).map(_.toDouble).toL st)
  }
}
