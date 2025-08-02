package com.twitter.ann.annoy

import com.twitter.ann.common._
import com.twitter.bijection.Injection
import com.twitter.search.common.file.AbstractFile
import com.twitter.util.FuturePool

// Class to provide Annoy based ann indetwitter.
object TypedAnnoyIndetwitter {

  /**
   * Create Annoy based typed indetwitter builder that serializes indetwitter to a directory (HDFS/Local file system).
   * It cannot be used in scalding as it leverage C/C++ jni bindings, whose build conflicts with version of some libs installed on hadoop.
   * You can use it on aurora or with IndetwitterBuilding job which triggers scalding job but then streams data to aurora machine for building indetwitter.
   * @param dimension dimension of embedding
   * @param numOfTrees builds a forest of numOfTrees trees.
   *                   More trees gives higher precision when querying at the cost of increased memory and disk storage requirement at the build time.
   *                   At runtime the indetwitter will be memory mapped, so memory wont be an issue but disk storage would be needed.
   * @param metric     distance metric for nearest neighbour search
   * @param injection Injection to convert bytes to Id.
   * @tparam T Type of Id for embedding
   * @tparam D Typed Distance
   * @return Serializable AnnoyIndetwitter
   */
  def indetwitterBuilder[T, D <: Distance[D]](
    dimension: Int,
    numOfTrees: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: FuturePool
  ): Appendable[T, AnnoyRuntimeParams, D] with Serialization = {
    TypedAnnoyIndetwitterBuilderWithFile(dimension, numOfTrees, metric, injection, futurePool)
  }

  /**
   * Load Annoy based queryable indetwitter from a directory
   * @param dimension dimension of embedding
   * @param metric distance metric for nearest neighbour search
   * @param injection Injection to convert bytes to Id.
   * @param futurePool FuturePool
   * @param directory Directory (HDFS/Local file system) where serialized indetwitter is stored.
   * @tparam T Type of Id for embedding
   * @tparam D Typed Distance
   * @return Typed Queryable AnnoyIndetwitter
   */
  def loadQueryableIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    injection: Injection[T, Array[Byte]],
    futurePool: FuturePool,
    directory: AbstractFile
  ): Queryable[T, AnnoyRuntimeParams, D] = {
    TypedAnnoyQueryIndetwitterWithFile(dimension, metric, injection, futurePool, directory)
  }
}
