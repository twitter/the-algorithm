package com.twitter.ann.hnsw

import com.google.common.annotations.VisibleForTesting
import com.twitter.ann.common.EmbeddingType.EmbeddingVector
import com.twitter.ann.common._
import com.twitter.ann.common.thriftscala.HnswIndetwitterMetadata
import com.twitter.ann.hnsw.HnswCommon._
import com.twitter.ann.hnsw.HnswIndetwitter.RandomProvider
import com.twitter.bijection.Injection
import com.twitter.search.common.file.AbstractFile
import com.twitter.search.common.file.FileUtils
import com.twitter.util.Future
import java.io.IOEtwitterception
import java.util.concurrent.ThreadLocalRandom
import java.util.Random
import org.apache.beam.sdk.io.fs.ResourceId

private[hnsw] object SerializableHnsw {
  private[hnsw] def apply[T, D <: Distance[D]](
    indetwitter: Hnsw[T, D],
    injection: Injection[T, Array[Byte]]
  ): SerializableHnsw[T, D] = {
    new SerializableHnsw[T, D](
      indetwitter,
      injection
    )
  }

  private[hnsw] def loadMapBasedQueryableIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: ReadWriteFuturePool,
    directory: AbstractFile
  ): SerializableHnsw[T, D] = {
    val metadata = HnswIOUtil.loadIndetwitterMetadata(directory.getChild(MetaDataFileName))
    validateMetadata(dimension, metric, metadata)
    val idEmbeddingMap = JMapBasedIdEmbeddingMap.loadInMemory(
      directory.getChild(EmbeddingMappingFileName),
      injection,
      Some(metadata.numElements)
    )
    loadIndetwitter(
      dimension,
      metric,
      injection,
      futurePool,
      directory,
      idEmbeddingMap,
      metadata
    )
  }

  private[hnsw] def loadMMappedBasedQueryableIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: ReadWriteFuturePool,
    directory: AbstractFile
  ): SerializableHnsw[T, D] = {
    val metadata = HnswIOUtil.loadIndetwitterMetadata(directory.getChild(MetaDataFileName))
    validateMetadata(dimension, metric, metadata)
    loadIndetwitter(
      dimension,
      metric,
      injection,
      futurePool,
      directory,
      MapDbBasedIdEmbeddingMap
        .loadAsReadonly(directory.getChild(EmbeddingMappingFileName), injection),
      metadata
    )
  }

  private[hnsw] def loadIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: ReadWriteFuturePool,
    directory: AbstractFile,
    idEmbeddingMap: IdEmbeddingMap[T],
    metadata: HnswIndetwitterMetadata
  ): SerializableHnsw[T, D] = {
    val distFn =
      DistanceFunctionGenerator(metric, (key: T) => idEmbeddingMap.get(key))
    val randomProvider = new RandomProvider {
      override def get(): Random = ThreadLocalRandom.current()
    }
    val internalIndetwitter = HnswIndetwitter.loadHnswIndetwitter[T, EmbeddingVector](
      distFn.indetwitter,
      distFn.query,
      directory.getChild(InternalIndetwitterDir),
      injection,
      randomProvider
    )

    val indetwitter = new Hnsw[T, D](
      dimension,
      metric,
      internalIndetwitter,
      futurePool,
      idEmbeddingMap,
      distFn.shouldNormalize,
      LockedAccess.apply(metadata.numElements)
    )

    new SerializableHnsw(indetwitter, injection)
  }

  private[this] def validateMetadata[D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    etwitteristingMetadata: HnswIndetwitterMetadata
  ): Unit = {
    assert(
      etwitteristingMetadata.dimension == dimension,
      s"Dimensions do not match. requested: $dimension etwitteristing: ${etwitteristingMetadata.dimension}"
    )

    val etwitteristingMetric = Metric.fromThrift(etwitteristingMetadata.distanceMetric)
    assert(
      etwitteristingMetric == metric,
      s"DistanceMetric do not match. requested: $metric etwitteristing: $etwitteristingMetric"
    )
  }
}

@VisibleForTesting
private[hnsw] class SerializableHnsw[T, D <: Distance[D]](
  indetwitter: Hnsw[T, D],
  injection: Injection[T, Array[Byte]])
    etwittertends Appendable[T, HnswParams, D]
    with Queryable[T, HnswParams, D]
    with Serialization
    with Updatable[T] {
  override def append(entity: EntityEmbedding[T]) = indetwitter.append(entity)

  override def toQueryable: Queryable[T, HnswParams, D] = indetwitter.toQueryable

  override def query(
    embedding: EmbeddingVector,
    numOfNeighbours: Int,
    runtimeParams: HnswParams
  ) = indetwitter.query(embedding, numOfNeighbours, runtimeParams)

  override def queryWithDistance(
    embedding: EmbeddingVector,
    numOfNeighbours: Int,
    runtimeParams: HnswParams
  ) = indetwitter.queryWithDistance(embedding, numOfNeighbours, runtimeParams)

  def toDirectory(directory: ResourceId): Unit = {
    toDirectory(new IndetwitterOutputFile(directory))
  }

  def toDirectory(directory: AbstractFile): Unit = {
    // Create a temp dir with time prefitwitter, and then do a rename after serialization
    val tmpDir = FileUtils.getTmpFileHandle(directory)
    if (!tmpDir.etwitterists()) {
      tmpDir.mkdirs()
    }

    toDirectory(new IndetwitterOutputFile(tmpDir))

    // Rename tmp dir to original directory supplied
    if (!tmpDir.rename(directory)) {
      throw new IOEtwitterception(s"Failed to rename ${tmpDir.getPath} to ${directory.getPath}")
    }
  }

  private def toDirectory(indetwitterFile: IndetwitterOutputFile): Unit = {
    // Save java based hnsw indetwitter
    indetwitter.getIndetwitter.toDirectory(indetwitterFile.createDirectory(InternalIndetwitterDir), injection)

    // Save indetwitter metadata
    HnswIOUtil.saveIndetwitterMetadata(
      indetwitter.getDimen,
      indetwitter.getMetric,
      indetwitter.getIdEmbeddingMap.size(),
      indetwitterFile.createFile(MetaDataFileName).getOutputStream()
    )

    // Save embedding mapping
    indetwitter.getIdEmbeddingMap.toDirectory(
      indetwitterFile.createFile(EmbeddingMappingFileName).getOutputStream())

    // Create _SUCCESS file
    indetwitterFile.createSuccessFile()
  }

  override def update(
    entity: EntityEmbedding[T]
  ): Future[Unit] = {
    indetwitter.update(entity)
  }
}
