package com.twitter.tweet_mixer.utils

import com.google.common.primitives.Longs
import com.twitter.io.Buf
import com.twitter.servo.cache.SeqSerializer
import com.twitter.servo.cache.Serializers.ArrayByteBuf
import com.twitter.servo.util.Transformer

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import scala.Array.emptyByteArray

object Transformers {

  def serializeIntLongOption(value: Option[(Int, Long)]): Buf = {
    val byteArray = value match {
      case Some((int, long)) =>
        val byteStream = new ByteArrayOutputStream()
        val objStream = new ObjectOutputStream(byteStream)
        objStream.writeObject((int, long))
        objStream.close()
        byteStream.toByteArray
      case None => emptyByteArray
    }
    Buf.ByteArray.Shared.apply(byteArray)
  }

  def serializeTpCacheOption(value: Option[(Int, Long, Long)]): Buf = {
    val byteArray = value match {
      case Some((int, long, mediaId)) =>
        val byteStream = new ByteArrayOutputStream()
        val objStream = new ObjectOutputStream(byteStream)
        objStream.writeObject((int, long, mediaId))
        objStream.close()
        byteStream.toByteArray
      case None => emptyByteArray
    }
    Buf.ByteArray.Shared.apply(byteArray)
  }

  def serializeMediaMetadataCacheOption(value: Option[Long]): Buf = {
    val byteArray = value match {
      case Some(mediaClusterId) =>
        val byteStream = new ByteArrayOutputStream()
        val objStream = new ObjectOutputStream(byteStream)
        objStream.writeObject(mediaClusterId)
        objStream.close()
        byteStream.toByteArray
      case None => emptyByteArray
    }
    Buf.ByteArray.Shared.apply(byteArray)
  }

  def deserializeIntLongOption(buffer: Buf): Option[(Int, Long)] = {
    val bytes = Buf.ByteArray.Shared.extract(buffer)
    if (bytes.isEmpty) None
    else {
      val byteStream = new ByteArrayInputStream(bytes)
      val objStream = new ObjectInputStream(byteStream)
      val value = objStream.readObject().asInstanceOf[(Int, Long)]
      objStream.close()
      Some(value)
    }
  }

  def deserializeTpCacheOption(buffer: Buf): Option[(Int, Long, Long)] = {
    val bytes = Buf.ByteArray.Shared.extract(buffer)
    if (bytes.isEmpty) None
    else {
      val byteStream = new ByteArrayInputStream(bytes)
      val objStream = new ObjectInputStream(byteStream)
      val value = objStream.readObject().asInstanceOf[(Int, Long, Long)]
      objStream.close()
      Some(value)
    }
  }

  def deserializeMediaMetadataCacheOption(buffer: Buf): Option[Long] = {
    val bytes = Buf.ByteArray.Shared.extract(buffer)
    if (bytes.isEmpty) None
    else {
      val byteStream = new ByteArrayInputStream(bytes)
      val objStream = new ObjectInputStream(byteStream)
      val value = objStream.readObject().asInstanceOf[Long]
      objStream.close()
      Some(value)
    }
  }

  def longDoubleSerializer(input: (Long, Double)): Array[Byte] = {
    val buffer = ByteBuffer.allocate(Longs.BYTES + java.lang.Double.BYTES)
    buffer.putLong(input._1)
    buffer.putDouble(input._2)
    buffer.array()
  }

  def longDoubleDeserializer(input: Array[Byte]): (Long, Double) = {
    val buffer = ByteBuffer.wrap(input)
    val longValue = buffer.getLong()
    val doubleValue = buffer.getDouble()
    (longValue, doubleValue)
  }

  private val longDoubleTransformer =
    Transformer[(Long, Double), Array[Byte]](longDoubleSerializer, longDoubleDeserializer)

  private val longDoubleSeqSerializer = new SeqSerializer(longDoubleTransformer, 16)

  val longDoubleSeqBufTransformer = longDoubleSeqSerializer.andThen(ArrayByteBuf)
  
  def serializeFilterBoolean(value: Boolean): Buf = {
    Buf.ByteArray.Shared(Array[Byte](if (value) 0x01 else 0x00))
  }

  def deserializeFilterBoolean(buffer: Buf): Boolean = {
    val bytes = Buf.ByteArray.Shared.extract(buffer)
    if (bytes.isEmpty) false
    else bytes(0) == 0x01
  }
}
