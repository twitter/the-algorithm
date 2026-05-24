package com.twitter.product_mixer.component_library.candidate_source.ann

import com.twitter.ann.common._

/**
 * A [[AnnIdQuery]] is a query class which defines the ann entities with runtime params and number of neighbors requested
 *
 * @param ids Sequence of queries
 * @param numOfNeighbors Number of neighbors requested
 * @param runtimeParams ANN Runtime Params
 * @param batchSize Batch size to the stitch client
 * @tparam T type of  query.
 * @tparam P  runtime parameters supported by the index.
 */
case class AnnIdQuery[T, P <: RuntimeParams](
  ids: Seq[T],
  numOfNeighbors: Int,
  runtimeParams: P)
