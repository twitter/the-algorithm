package com.X.ann.annoy

import com.X.ann.annoy.AnnoyCommon._
import com.X.ann.common._
import com.X.ann.file_store.ReadableIndexIdFileStore
import com.X.bijection.Injection
import com.X.search.common.file.AbstractFile
import com.X.util.FuturePool

private[annoy] object TypedAnnoyQueryIndexWithFile {
  private[annoy] def apply[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: FuturePool,
    directory: AbstractFile
  ): Queryable[T, AnnoyRuntimeParams, D] = {
    val deserializer =
      new TypedAnnoyQueryIndexWithFile(dimension, metric, futurePool, injection)
    deserializer.fromDirectory(directory)
  }
}

private[this] class TypedAnnoyQueryIndexWithFile[T, D <: Distance[D]](
  dimension: Int,
  metric: Metric[D],
  futurePool: FuturePool,
  injection: Injection[T, Array[Byte]])
    extends QueryableDeserialization[
      T,
      AnnoyRuntimeParams,
      D,
      Queryable[T, AnnoyRuntimeParams, D]
    ] {
  override def fromDirectory(directory: AbstractFile): Queryable[T, AnnoyRuntimeParams, D] = {
    val index = RawAnnoyQueryIndex(dimension, metric, futurePool, directory)

    val indexIdFile = directory.getChild(IndexIdMappingFileName)
    val readableFileStore = ReadableIndexIdFileStore(indexIdFile, injection)
    IndexTransformer.transformQueryable(index, readableFileStore)
  }
}
