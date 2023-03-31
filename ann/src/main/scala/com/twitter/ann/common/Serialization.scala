package com.twitter.ann.common

import com.twitter.search.common.file.AbstractFile
import org.apache.beam.sdk.io.fs.ResourceId

/**
 * Interface for writing an Appendable to a directory.
 */
trait Serialization {
  def toDirectory(
    serializationDirectory: AbstractFile
  ): Unit

  def toDirectory(
    serializationDirectory: ResourceId
  ): Unit
}

/**
 * Interface for reading a Queryable from a directory
 * @tparam T the id of the embeddings
 * @tparam Q type of the Queryable that is deserialized.
 */
trait QueryableDeserialization[T, P <: RuntimeParams, D <: Distance[D], Q <: Queryable[T, P, D]] {
  def fromDirectory(
    serializationDirectory: AbstractFile
  ): Q
}
