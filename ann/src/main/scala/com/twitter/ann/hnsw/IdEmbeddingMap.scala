package com.twitter.ann.hnsw

import com.twitter.ann.common.EmbeddingType._
import java.io.OutputStream

trait IdEmbeddingMap[T] {
  def putIfAbsent(id: T, embedding: EmbeddingVector): EmbeddingVector
  def put(id: T, embedding: EmbeddingVector): EmbeddingVector
  def get(id: T): EmbeddingVector
  def iter(): Iterator[(T, EmbeddingVector)]
  def size(): Int
  def toDirectory(embeddingFileOutputStream: OutputStream): Unit
}
