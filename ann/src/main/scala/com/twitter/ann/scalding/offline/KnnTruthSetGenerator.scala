package com.ExTwitter.ann.scalding.offline

import com.ExTwitter.ann.common.Distance
import com.ExTwitter.ann.common.Metric
import com.ExTwitter.ann.scalding.offline.KnnHelper.nearestNeighborsToString
import com.ExTwitter.cortex.ml.embeddings.common.EntityKind
import com.ExTwitter.ml.featurestore.lib.EntityId
import com.ExTwitter.scalding.source.TypedText
import com.ExTwitter.scalding.Args
import com.ExTwitter.scalding.Execution
import com.ExTwitter.scalding.UniqueID
import com.ExTwitter.scalding_internal.job.ExTwitterExecutionApp

/**
 * This job reads index embedding data, query embeddings data, and split into index set, query set and true nearest neigbor set
 * from query to index.
 */
object KnnTruthSetGenerator extends ExTwitterExecutionApp {
  override def job: Execution[Unit] = Execution.withId { implicit uniqueId =>
    Execution.getArgs.flatMap { args: Args =>
      val queryEntityKind = EntityKind.getEntityKind(args("query_entity_kind"))
      val indexEntityKind = EntityKind.getEntityKind(args("index_entity_kind"))
      val metric = Metric.fromString(args("metric"))
      run(queryEntityKind, indexEntityKind, metric, args)
    }
  }

  private[this] def run[A <: EntityId, B <: EntityId, D <: Distance[D]](
    uncastQueryEntityKind: EntityKind[_],
    uncastIndexSpaceEntityKind: EntityKind[_],
    uncastMetric: Metric[_],
    args: Args
  )(
    implicit uniqueID: UniqueID
  ): Execution[Unit] = {
    val queryEntityKind = uncastQueryEntityKind.asInstanceOf[EntityKind[A]]
    val indexEntityKind = uncastIndexSpaceEntityKind.asInstanceOf[EntityKind[B]]
    val metric = uncastMetric.asInstanceOf[Metric[D]]

    val reducers = args.int("reducers")
    val mappers = args.int("mappers")
    val numNeighbors = args.int("neighbors")
    val knnOutputPath = args("truth_set_output_path")
    val querySamplePercent = args.double("query_sample_percent", 100) / 100
    val indexSamplePercent = args.double("index_sample_percent", 100) / 100

    val queryEmbeddings = queryEntityKind.parser
      .getEmbeddingFormat(args, "query")
      .getEmbeddings
      .sample(querySamplePercent)

    val indexEmbeddings = indexEntityKind.parser
      .getEmbeddingFormat(args, "index")
      .getEmbeddings
      .sample(indexSamplePercent)

    // calculate and write knn
    val knnExecution = KnnHelper
      .findNearestNeighbours(
        queryEmbeddings,
        indexEmbeddings,
        metric,
        numNeighbors,
        reducers = reducers,
        mappers = mappers
      )(queryEntityKind.ordering, uniqueID).map(
        nearestNeighborsToString(_, queryEntityKind, indexEntityKind)
      )
      .shard(1)
      .writeExecution(TypedText.tsv(knnOutputPath))

    // write query set embeddings
    val querySetExecution = queryEntityKind.parser
      .getEmbeddingFormat(args, "query_set_output")
      .writeEmbeddings(queryEmbeddings)

    // write index set embeddings
    val indexSetExecution = indexEntityKind.parser
      .getEmbeddingFormat(args, "index_set_output")
      .writeEmbeddings(indexEmbeddings)

    Execution.zip(knnExecution, querySetExecution, indexSetExecution).unit
  }
}
