package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.recos.entities.thriftscala.{Entity, Hashtag, SemanticCoreEntity}
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{ModelVersions, SimClustersEmbedding}
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.{EmbeddingUtil, EntityEmbeddingUtil, SimClustersEmbeddingJob}
import com.twitter.simclusters_v2.thriftscala.{SimClustersEmbedding as ThriftSimClustersEmbedding, *}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.EntityType
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}

import java.util.TimeZone

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embeddings_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.EntityToSimClustersEmbeddingAdhocApp \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embeddings_job-adhoc \
  --user recos-platform \
  -- --date 2019-09-09 --model-version 20M_145K_updated --entity-type SemanticCore
 */
object EntityToSimClustersEmbeddingAdhocApp extends AdhocExecutionApp {

  import EmbeddingUtil.*
  import EntityEmbeddingUtil.*
  import EntityToSimClustersEmbeddingsJob.*
  import SimClustersEmbeddingJob.*

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val jobConfig = EntityEmbeddingsJobConfig(args, isAdhoc = true)

    val numReducers = args.getOrElse("m", "1000").toInt

    /*
      Using the ERG daily dataset in the adhoc job for quick prototyping, note that there may be
      issues with scaling the job when productionizing on ERG aggregated dataset.
     */
    val entityRealGraphSource = DataSources.entityRealGraphDailyDataSetSource

    val entityUserMatrix: TypedPipe[(Entity, (UserId, Double))] =
      (jobConfig.entityType match {
        case EntityType.SemanticCore =>
          getEntityUserMatrix(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
        case EntityType.Hashtag =>
          getEntityUserMatrix(entityRealGraphSource, jobConfig.halfLife, EntityType.Hashtag)
        case _ =>
          throw new IllegalArgumentException(
            s"Argument [--entity-type] must be provided. Supported options [${EntityType.SemanticCore.name}, ${EntityType.Hashtag.name}]")
      }).forceToDisk

    val normalizedUserEntityMatrix =
      getNormalizedTransposeInputMatrix(entityUserMatrix, numReducers = Some(numReducers))

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case _ =>
        InterestedInSources.simClustersInterestedInDec11Source(dateRange, timeZone)
    }

    val embeddings = computeEmbeddings(
      simClustersSource,
      normalizedUserEntityMatrix,
      scoreExtractors,
      ModelVersion.Model20m145kUpdated,
      toSimClustersEmbeddingId(jobConfig.modelVersion),
      numReducers = Some(numReducers * 2)
    )

    val topKEmbeddings =
      embeddings.group
        .sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))
        .withReducers(numReducers)

    writeOutput(embeddings, topKEmbeddings, jobConfig)
  }

  def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig
  ): Execution[Unit] = {

    val toSimClusterEmbeddingExec = topKEmbeddings
      .mapValues(SimClustersEmbedding.apply(_).toThrift)
      .writeExecution(
        AdhocKeyValSources.entityToClustersSource(
          EntityToSimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            isReverseIndex = false,
            jobConfig.modelVersion,
            jobConfig.entityType)))

    val fromSimClusterEmbeddingExec =
      toReverseIndexSimClusterEmbedding(embeddings, jobConfig.topK)
        .writeExecution(
          AdhocKeyValSources.clusterToEntitiesSource(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = true,
              isManhattanKeyVal = true,
              isReverseIndex = true,
              jobConfig.modelVersion,
              jobConfig.entityType)))

    Execution.zip(toSimClusterEmbeddingExec, fromSimClusterEmbeddingExec).unit
  }
}



