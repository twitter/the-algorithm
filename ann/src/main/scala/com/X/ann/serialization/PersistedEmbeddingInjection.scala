package com.X.ann.serialization

import com.X.ann.common.EntityEmbedding
import com.X.ann.common.EmbeddingType._
import com.X.ann.serialization.thriftscala.PersistedEmbedding
import com.X.bijection.Injection
import com.X.mediaservices.commons.codec.ArrayByteBufferCodec
import java.nio.ByteBuffer
import scala.util.Try

/**
 * Injection that converts from the ann.common.Embedding to the thrift PersistedEmbedding.
 */
class PersistedEmbeddingInjection[T](
  idByteInjection: Injection[T, Array[Byte]])
    extends Injection[EntityEmbedding[T], PersistedEmbedding] {
  override def apply(entity: EntityEmbedding[T]): PersistedEmbedding = {
    val byteBuffer = ByteBuffer.wrap(idByteInjection(entity.id))
    PersistedEmbedding(byteBuffer, embeddingSerDe.toThrift(entity.embedding))
  }

  override def invert(persistedEmbedding: PersistedEmbedding): Try[EntityEmbedding[T]] = {
    val idTry = idByteInjection.invert(ArrayByteBufferCodec.decode(persistedEmbedding.id))
    idTry.map { id =>
      EntityEmbedding(id, embeddingSerDe.fromThrift(persistedEmbedding.embedding))
    }
  }
}
