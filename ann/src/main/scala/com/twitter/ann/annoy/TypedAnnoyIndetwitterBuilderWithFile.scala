package com.twitter.ann.annoy

import com.twitter.ann.annoy.AnnoyCommon.IndetwitterIdMappingFileName
import com.twitter.ann.common._
import com.twitter.ann.file_store.WritableIndetwitterIdFileStore
import com.twitter.bijection.Injection
import com.twitter.search.common.file.AbstractFile
import com.twitter.util.Future
import com.twitter.util.FuturePool
import org.apache.beam.sdk.io.fs.ResourceId

private[annoy] object TypedAnnoyIndetwitterBuilderWithFile {
  private[annoy] def apply[T, D <: Distance[D]](
    dimension: Int,
    numOfTrees: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: FuturePool
  ): Appendable[T, AnnoyRuntimeParams, D] with Serialization = {
    val indetwitter = RawAnnoyIndetwitterBuilder(dimension, numOfTrees, metric, futurePool)
    val writableFileStore = WritableIndetwitterIdFileStore(injection)
    new TypedAnnoyIndetwitterBuilderWithFile[T, D](indetwitter, writableFileStore)
  }
}

private[this] class TypedAnnoyIndetwitterBuilderWithFile[T, D <: Distance[D]](
  indetwitterBuilder: RawAppendable[AnnoyRuntimeParams, D] with Serialization,
  store: WritableIndetwitterIdFileStore[T])
    etwittertends Appendable[T, AnnoyRuntimeParams, D]
    with Serialization {
  private[this] val transformedIndetwitter = IndetwitterTransformer.transformAppendable(indetwitterBuilder, store)

  override def append(entity: EntityEmbedding[T]): Future[Unit] = {
    transformedIndetwitter.append(entity)
  }

  override def toDirectory(directory: ResourceId): Unit = {
    indetwitterBuilder.toDirectory(directory)
    toDirectory(new IndetwitterOutputFile(directory))
  }

  override def toDirectory(directory: AbstractFile): Unit = {
    indetwitterBuilder.toDirectory(directory)
    toDirectory(new IndetwitterOutputFile(directory))
  }

  private def toDirectory(directory: IndetwitterOutputFile): Unit = {
    val indetwitterIdFile = directory.createFile(IndetwitterIdMappingFileName)
    store.save(indetwitterIdFile)
  }

  override def toQueryable: Queryable[T, AnnoyRuntimeParams, D] = {
    transformedIndetwitter.toQueryable
  }
}
