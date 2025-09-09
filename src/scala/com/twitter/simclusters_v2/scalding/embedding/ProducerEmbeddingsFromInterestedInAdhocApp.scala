package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob
import com.twitter.simclusters_v2.thriftscala.*
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}

import java.util.TimeZone


/**
 * Adhoc job to calculate producer's simcluster embeddings, which essentially assigns interestedIn
 * SimClusters to each producer, regardless of whether the producer has a knownFor assignment.
 *
$ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:producer_embeddings_from_interested_in-adhoc

 $ scalding remote run \
 --main-class com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInAdhocApp \
 --target src/scala/com/twitter/simclusters_v2/scalding/embedding:producer_embeddings_from_interested_in-adhoc \
 --user cassowary --cluster bluebird-qus1 \
 --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
 --principal service_acoount@TWITTER.BIZ \
 -- --date 2020-08-25 --model_version 20M_145K_updated \
 --outputDir /gcs/user/cassowary/adhoc/producerEmbeddings/

 */
object ProducerEmbeddingsFromInterestedInAdhocApp extends AdhocExecutionApp {

  import ProducerEmbeddingsFromInterestedIn.*

  private val numReducersForMatrixMultiplication = 12000

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val outputDir = args("outputDir")

    val modelVersion =
      ModelVersions.toModelVersion(args.required("model_version"))

    val interestedInClusters = modelVersion match {
      case ModelVersion.Model20m145k2020 =>
        InterestedInSources.simClustersInterestedIn2020Source(dateRange, timeZone).forceToDisk
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone).forceToDisk
      case _ =>
        InterestedInSources.simClustersInterestedInDec11Source(dateRange, timeZone).forceToDisk
    }

    Execution
      .zip(
        runFavScore(
          interestedInClusters,
          DataSources.userUserNormalizedGraphSource,
          DataSources.userNormsAndCounts,
          modelVersion,
          outputDir
        ),
        runFollowScore(
          interestedInClusters,
          DataSources.userUserNormalizedGraphSource,
          DataSources.userNormsAndCounts,
          modelVersion,
          outputDir
        )
      ).unit
  }

  // Calculate the embeddings using follow scores
  private def runFollowScore(
    interestedInClusters: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    userUserNormalGraph: TypedPipe[UserAndNeighbors],
    userNormsAndCounts: TypedPipe[NormsAndCounts],
    modelVersion: ModelVersion,
    outputDir: String
  )(
    implicit uniqueID: UniqueID
  ): Execution[Unit] = {
    val keyByClusterSinkPath = outputDir + "keyedByCluster/byFollowScore_" + modelVersion
    val keyByProducerSinkPath = outputDir + "keyedByProducer/byFollowScore_" + modelVersion

    runAdhocByScore(
      interestedInClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      keyedByProducerSinkPath = keyByProducerSinkPath,
      keyedByClusterSinkPath = keyByClusterSinkPath,
      userToProducerScoringFn = userToProducerFollowScore,
      userToClusterScoringFn = userToClusterFollowScore,
      _.followerCount.exists(_ > minNumFollowersForProducer),
      modelVersion
    )
  }

  // Calculate the embeddings using fav scores
  private def runFavScore(
    interestedInClusters: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    userUserNormalGraph: TypedPipe[UserAndNeighbors],
    userNormsAndCounts: TypedPipe[NormsAndCounts],
    modelVersion: ModelVersion,
    outputDir: String
  )(
    implicit uniqueID: UniqueID
  ): Execution[Unit] = {
    val keyByClusterSinkPath = outputDir + "keyedByCluster/byFavScore_" + modelVersion
    val keyByProducerSinkPath = outputDir + "keyedByProducer/byFavScore_" + modelVersion

    runAdhocByScore(
      interestedInClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      keyedByProducerSinkPath = keyByProducerSinkPath,
      keyedByClusterSinkPath = keyByClusterSinkPath,
      userToProducerScoringFn = userToProducerFavScore,
      userToClusterScoringFn = userToClusterFavScore,
      _.faverCount.exists(_ > minNumFaversForProducer),
      modelVersion
    )
  }

  /**
   * Calculate the embedding and writes the results keyed by producers and clusters separately into
   * individual locations
   */
  private def runAdhocByScore(
    interestedInClusters: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    userUserNormalGraph: TypedPipe[UserAndNeighbors],
    userNormsAndCounts: TypedPipe[NormsAndCounts],
    keyedByProducerSinkPath: String,
    keyedByClusterSinkPath: String,
    userToProducerScoringFn: NeighborWithWeights => Double,
    userToClusterScoringFn: UserToInterestedInClusterScores => Double,
    userFilter: NormsAndCounts => Boolean,
    modelVersion: ModelVersion
  )(
    implicit uniqueID: UniqueID
  ): Execution[Unit] = {

    val producerClusterEmbedding = getProducerClusterEmbedding(
      interestedInClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      userToProducerScoringFn,
      userToClusterScoringFn,
      userFilter,
      numReducersForMatrixMultiplication,
      modelVersion,
      cosineSimilarityThreshold
    ).forceToDisk

    val keyByProducerExec =
      toSimClusterEmbedding(producerClusterEmbedding, topKClustersToKeep, modelVersion)
        .writeExecution(
          AdhocKeyValSources.topProducerToClusterEmbeddingsSource(keyedByProducerSinkPath))

    val keyByClusterExec =
      fromSimClusterEmbedding(producerClusterEmbedding, topKUsersToKeep, modelVersion)
        .map { case (clusterId, topProducers) => (clusterId, topProducersToThrift(topProducers)) }
        .writeExecution(
          AdhocKeyValSources.topClusterEmbeddingsToProducerSource(keyedByClusterSinkPath))

    Execution.zip(keyByProducerExec, keyByClusterExec).unit
  }
}


