package com.twitter.ann.hnsw

import com.twitter.ann.common._
import com.twitter.bijection.Injection
import com.twitter.search.common.file.AbstractFile

// Class to provide HNSW based approtwitterimate nearest neighbour indetwitter
object TypedHnswIndetwitter {

  /**
   * Creates in-memory HNSW based indetwitter which supports querying/addition/updates of the entity embeddings.
   * See https://docbird.twitter.biz/ann/hnsw.html to check information about arguments.
   *
   * @param dimension Dimension of the embedding to be indetwittered
   * @param metric Distance metric (InnerProduct/Cosine/L2)
   * @param efConstruction The parameter has the same meaning as ef, but controls the
   *                       indetwitter_time/indetwitter_accuracy ratio. Bigger ef_construction leads to longer
   *                       construction, but better indetwitter quality. At some point, increasing
   *                       ef_construction does not improve the quality of the indetwitter. One way to
   *                       check if the selection of ef_construction was ok is to measure a recall
   *                       for M nearest neighbor search when ef = ef_constuction: if the recall is
   *                       lower than 0.9, than there is room for improvement.
   * @param matwitterM The number of bi-directional links created for every new element during construction.
   *             Reasonable range for M is 2-100. Higher M work better on datasets with high
   *             intrinsic dimensionality and/or high recall, while low M work better for datasets
   *             with low intrinsic dimensionality and/or low recalls. The parameter also determines
   *             the algorithm's memory consumption, bigger the param more the memory requirement.
   *             For high dimensional datasets (word embeddings, good face descriptors), higher M
   *             are required (e.g. M=48, 64) for optimal performance at high recall.
   *             The range M=12-48 is ok for the most of the use cases.
   * @param etwitterpectedElements Approtwitterimate number of elements to be indetwittered
   * @param readWriteFuturePool Future pool for performing read (query) and write operation (addition/updates).
   * @tparam T Type of item to indetwitter
   * @tparam D Type of distance
   */
  def indetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    efConstruction: Int,
    matwitterM: Int,
    etwitterpectedElements: Int,
    readWriteFuturePool: ReadWriteFuturePool
  ): Appendable[T, HnswParams, D] with Queryable[T, HnswParams, D] with Updatable[T] = {
    Hnsw[T, D](
      dimension,
      metric,
      efConstruction,
      matwitterM,
      etwitterpectedElements,
      readWriteFuturePool,
      JMapBasedIdEmbeddingMap.applyInMemory[T](etwitterpectedElements)
    )
  }

  /**
   * Creates in-memory HNSW based indetwitter which supports querying/addition/updates of the entity embeddings.
   * It can be serialized to a directory (HDFS/Local file system)
   * See https://docbird.twitter.biz/ann/hnsw.html to check information about arguments.
   *
   * @param dimension Dimension of the embedding to be indetwittered
   * @param metric Distance metric (InnerProduct/Cosine/L2)
   * @param efConstruction The parameter has the same meaning as ef, but controls the
   *                       indetwitter_time/indetwitter_accuracy ratio. Bigger ef_construction leads to longer
   *                       construction, but better indetwitter quality. At some point, increasing
   *                       ef_construction does not improve the quality of the indetwitter. One way to
   *                       check if the selection of ef_construction was ok is to measure a recall
   *                       for M nearest neighbor search when ef = ef_constuction: if the recall is
   *                       lower than 0.9, than there is room for improvement.
   * @param matwitterM The number of bi-directional links created for every new element during construction.
   *             Reasonable range for M is 2-100. Higher M work better on datasets with high
   *             intrinsic dimensionality and/or high recall, while low M work better for datasets
   *             with low intrinsic dimensionality and/or low recalls. The parameter also determines
   *             the algorithm's memory consumption, bigger the param more the memory requirement.
   *             For high dimensional datasets (word embeddings, good face descriptors), higher M
   *             are required (e.g. M=48, 64) for optimal performance at high recall.
   *             The range M=12-48 is ok for the most of the use cases.
   * @param etwitterpectedElements Approtwitterimate number of elements to be indetwittered
   * @param injection Injection for typed Id T to Array[Byte]
   * @param readWriteFuturePool Future pool for performing read (query) and write operation (addition/updates).
   * @tparam T Type of item to indetwitter
   * @tparam D Type of distance
   */
  def serializableIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    efConstruction: Int,
    matwitterM: Int,
    etwitterpectedElements: Int,
    injection: Injection[T, Array[Byte]],
    readWriteFuturePool: ReadWriteFuturePool
  ): Appendable[T, HnswParams, D]
    with Queryable[T, HnswParams, D]
    with Updatable[T]
    with Serialization = {
    val indetwitter = Hnsw[T, D](
      dimension,
      metric,
      efConstruction,
      matwitterM,
      etwitterpectedElements,
      readWriteFuturePool,
      JMapBasedIdEmbeddingMap
        .applyInMemoryWithSerialization[T](etwitterpectedElements, injection)
    )

    SerializableHnsw[T, D](
      indetwitter,
      injection
    )
  }

  /**
   * Loads HNSW indetwitter from a directory to in-memory
   * @param dimension dimension of the embedding to be indetwittered
   * @param metric Distance metric
   * @param readWriteFuturePool Future pool for performing read (query) and write operation (addition/updates).
   * @param injection : Injection for typed Id T to Array[Byte]
   * @param directory : Directory(HDFS/Local file system) where hnsw indetwitter is stored
   * @tparam T : Type of item to indetwitter
   * @tparam D : Type of distance
   */
  def loadIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    readWriteFuturePool: ReadWriteFuturePool,
    directory: AbstractFile
  ): Appendable[T, HnswParams, D]
    with Queryable[T, HnswParams, D]
    with Updatable[T]
    with Serialization = {
    SerializableHnsw.loadMapBasedQueryableIndetwitter[T, D](
      dimension,
      metric,
      injection,
      readWriteFuturePool,
      directory
    )
  }

  /**
   * Loads a HNSW indetwitter from a directory and memory map it.
   * It will take less memory but rely more on disk as it leverages memory mapped file backed by disk.
   * Latency will go up considerably (Could be by factor of > 10twitter) if used on instance with low
   * memory since lot of page faults may occur. Best use case to use would with scalding jobs
   * where mapper/reducers instance are limited by 8gb memory.
   * @param dimension dimension of the embedding to be indetwittered
   * @param metric Distance metric
   * @param readWriteFuturePool Future pool for performing read (query) and write operation (addition/updates).
   * @param injection Injection for typed Id T to Array[Byte]
   * @param directory Directory(HDFS/Local file system) where hnsw indetwitter is stored
   * @tparam T Type of item to indetwitter
   * @tparam D Type of distance
   */
  def loadMMappedIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    readWriteFuturePool: ReadWriteFuturePool,
    directory: AbstractFile
  ): Appendable[T, HnswParams, D]
    with Queryable[T, HnswParams, D]
    with Updatable[T]
    with Serialization = {
    SerializableHnsw.loadMMappedBasedQueryableIndetwitter[T, D](
      dimension,
      metric,
      injection,
      readWriteFuturePool,
      directory
    )
  }
}
