package com.twitter.ann.scalding.offline

import com.twitter.ann.common.Distance
import com.twitter.ann.common.Metric
import com.twitter.ann.scalding.offline.KnnHelper.nearestNeighborsToString
import com.twitter.cortetwitter.ml.embeddings.common.EntityKind
import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.scalding.source.TypedTetwittert
import com.twitter.scalding.Args
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.UniqueID
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp

/**
 * This job reads indetwitter embedding data, query embeddings data, and split into indetwitter set, query set and true nearest neigbor set
 * from query to indetwitter.
 */
object KnnTruthSetGenerator etwittertends TwitterEtwitterecutionApp {
  override def job: Etwitterecution[Unit] = Etwitterecution.withId { implicit uniqueId =>
    Etwitterecution.getArgs.flatMap { args: Args =>
      val queryEntityKind = EntityKind.getEntityKind(args("query_entity_kind"))
      val indetwitterEntityKind = EntityKind.getEntityKind(args("indetwitter_entity_kind"))
      val metric = Metric.fromString(args("metric"))
      run(queryEntityKind, indetwitterEntityKind, metric, args)
    }
  }

  private[this] def run[A <: EntityId, B <: EntityId, D <: Distance[D]](
    uncastQueryEntityKind: EntityKind[_],
    uncastIndetwitterSpaceEntityKind: EntityKind[_],
    uncastMetric: Metric[_],
    args: Args
  )(
    implicit uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val queryEntityKind = uncastQueryEntityKind.asInstanceOf[EntityKind[A]]
    val indetwitterEntityKind = uncastIndetwitterSpaceEntityKind.asInstanceOf[EntityKind[B]]
    val metric = uncastMetric.asInstanceOf[Metric[D]]

    val reducers = args.int("reducers")
    val mappers = args.int("mappers")
    val numNeighbors = args.int("neighbors")
    val knnOutputPath = args("truth_set_output_path")
    val querySamplePercent = args.double("query_sample_percent", 100) / 100
    val indetwitterSamplePercent = args.double("indetwitter_sample_percent", 100) / 100

    val queryEmbeddings = queryEntityKind.parser
      .getEmbeddingFormat(args, "query")
      .getEmbeddings
      .sample(querySamplePercent)

    val indetwitterEmbeddings = indetwitterEntityKind.parser
      .getEmbeddingFormat(args, "indetwitter")
      .getEmbeddings
      .sample(indetwitterSamplePercent)

    // calculate and write knn
    val knnEtwitterecution = KnnHelper
      .findNearestNeighbours(
        queryEmbeddings,
        indetwitterEmbeddings,
        metric,
        numNeighbors,
        reducers = reducers,
        mappers = mappers
      )(queryEntityKind.ordering, uniqueID).map(
        nearestNeighborsToString(_, queryEntityKind, indetwitterEntityKind)
      )
      .shard(1)
      .writeEtwitterecution(TypedTetwittert.tsv(knnOutputPath))

    // write query set embeddings
    val querySetEtwitterecution = queryEntityKind.parser
      .getEmbeddingFormat(args, "query_set_output")
      .writeEmbeddings(queryEmbeddings)

    // write indetwitter set embeddings
    val indetwitterSetEtwitterecution = indetwitterEntityKind.parser
      .getEmbeddingFormat(args, "indetwitter_set_output")
      .writeEmbeddings(indetwitterEmbeddings)

    Etwitterecution.zip(knnEtwitterecution, querySetEtwitterecution, indetwitterSetEtwitterecution).unit
  }
}
