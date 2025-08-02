package com.twitter.ann.annoy

import com.twitter.ann.annoy.AnnoyCommon._
import com.twitter.ann.common._
import com.twitter.ann.file_store.ReadableIndetwitterIdFileStore
import com.twitter.bijection.Injection
import com.twitter.search.common.file.AbstractFile
import com.twitter.util.FuturePool

private[annoy] object TypedAnnoyQueryIndetwitterWithFile {
  private[annoy] def apply[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: FuturePool,
    directory: AbstractFile
  ): Queryable[T, AnnoyRuntimeParams, D] = {
    val deserializer =
      new TypedAnnoyQueryIndetwitterWithFile(dimension, metric, futurePool, injection)
    deserializer.fromDirectory(directory)
  }
}

private[this] class TypedAnnoyQueryIndetwitterWithFile[T, D <: Distance[D]](
  dimension: Int,
  metric: Metric[D],
  futurePool: FuturePool,
  injection: Injection[T, Array[Byte]])
    etwittertends QueryableDeserialization[
      T,
      AnnoyRuntimeParams,
      D,
      Queryable[T, AnnoyRuntimeParams, D]
    ] {
  override def fromDirectory(directory: AbstractFile): Queryable[T, AnnoyRuntimeParams, D] = {
    val indetwitter = RawAnnoyQueryIndetwitter(dimension, metric, futurePool, directory)

    val indetwitterIdFile = directory.getChild(IndetwitterIdMappingFileName)
    val readableFileStore = ReadableIndetwitterIdFileStore(indetwitterIdFile, injection)
    IndetwitterTransformer.transformQueryable(indetwitter, readableFileStore)
  }
}
