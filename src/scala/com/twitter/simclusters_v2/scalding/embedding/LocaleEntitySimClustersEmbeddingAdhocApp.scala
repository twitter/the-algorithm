package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.recos.entities.thriftscala.{Entity, Hashtag, SemanticCoreEntity}
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{ModelVersions, SimClustersEmbedding}
import com.twitter.simclusters_v2.hdfs_sources.{AdhocKeyValSources, EntityEmbeddingsSources, InterestedInSources}
import com.twitter.simclusters_v2.hdfs_sources.presto_hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingsJob.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.EntityEmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.{EmbeddingUtil, ExternalDataSources}
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob.*
import com.twitter.simclusters_v2.thriftscala.{SimClustersEmbedding as ThriftSimClustersEmbedding, *}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.{Edge, EntityType}
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}

import java.util.TimeZone

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_per_language_embeddings_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingAdhocApp \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_per_language_embeddings_job-adhoc \
  --user recos-platform \
  -- --date 2019-12-17 --model-version 20M_145K_updated --entity-type SemanticCore
 */
object LocaleEntitySimClustersEmbeddingAdhocApp extends AdhocExecutionApp {

  // Import implicits

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val jobConfig = EntityEmbeddingsJobConfig(args, isAdhoc = true)

    val numReducers = args.getOrElse("m", "2000").toInt

    /*
      Can use the ERG daily dataset in the adhoc job for quick prototyping, note that there may be
      issues with scaling the job when productionizing on ERG aggregated dataset.
     */
    val userEntityMatrix: TypedPipe[(UserId, (Entity, Double))] =
      getUserEntityMatrix(
        jobConfig,
        DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))),
        Some(ExternalDataSources.uttEntitiesSource())
      ).forceToDisk

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case modelVersion =>
        throw new IllegalArgumentException(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val entityPerLanguage = userEntityMatrix.join(ExternalDataSources.userSource).map {
      case (userId, ((entity, score), (_, language))) =>
        ((entity, language), (userId, score))
    }

    val normalizedUserEntityMatrix =
      getNormalizedTransposeInputMatrix(entityPerLanguage, numReducers = Some(numReducers))

    val embeddings = computeEmbeddings[(Entity, String)](
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
          LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            isReverseIndex = false,
            isLogFav = false,
            jobConfig.modelVersion,
            jobConfig.entityType)))

    val fromSimClusterEmbeddingExec =
      toReverseIndexSimClusterEmbedding(embeddings, jobConfig.topK)
        .writeExecution(
          AdhocKeyValSources.clusterToEntitiesSource(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = true,
              isManhattanKeyVal = true,
              isReverseIndex = true,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType)))

    Execution.zip(toSimClusterEmbeddingExec, fromSimClusterEmbeddingExec).unit
  }
}




