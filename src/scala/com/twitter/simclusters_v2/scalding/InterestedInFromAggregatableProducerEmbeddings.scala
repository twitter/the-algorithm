package com.twitter.simclusters_v2.scalding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.dal.client.dataset.SnapshotDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.scalding_internal.dalv2.DALWrite.WriteEtwittertension
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.AggregatableProducerSimclustersEmbeddingsByLogFavScore2020ScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.SimclustersV2InterestedInFromAggregatableProducerEmbeddings20M145K2020ScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.SimclustersV2UserToInterestedInFromAggregatableProducerEmbeddings20M145K2020ScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.UserAndNeighborsFitwitteredPathSource
import com.twitter.simclusters_v2.hdfs_sources.UserUserNormalizedGraphScalaDataset
import com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.UserAndNeighbors
import com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusterScores
import com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledEtwitterecutionApp
import java.util.TimeZone

/**
 * Production job for computing interestedIn data set from the aggregatable producer embeddings for the model version 20M145K2020.
 * It writes the data set in KeyVal format to produce a MH DAL data set.
 *
 * A high level description of this job:
 * - Read the APE dataset
 * - Apply log1p to the scores from the above dataset as the scores for producers is high
 * - Normalize the scores for each producer (offline benchmarking has shown better results from this step.)
 * - Truncate the number of clusters for each producer from the APE dataset to reduce noise
 * - Compute interestedIn
 *
 * To deploy the job:
 *
 * capesospy-v2 update --build_locally --start_cron interested_in_from_ape_2020 \
 * src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml
 */
object InterestedInFromAPE2020BatchApp etwittertends InterestedInFromAggregatableProducerEmbeddingsBase {

  override val firstTime: RichDate = RichDate("2021-03-03")

  override val batchIncrement: Duration = Days(7)

  override def modelVersion: ModelVersion = ModelVersion.Model20m145k2020

  override def producerEmbeddingsInputKVDataset: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]
  ] = AggregatableProducerSimclustersEmbeddingsByLogFavScore2020ScalaDataset

  override def interestedInFromAPEOutputKVDataset: KeyValDALDataset[
    KeyVal[UserId, ClustersUserIsInterestedIn]
  ] = SimclustersV2InterestedInFromAggregatableProducerEmbeddings20M145K2020ScalaDataset

  override def interestedInFromAPEOutputThriftDatset: SnapshotDALDataset[
    UserToInterestedInClusters
  ] = SimclustersV2UserToInterestedInFromAggregatableProducerEmbeddings20M145K2020ScalaDataset
}

trait InterestedInFromAggregatableProducerEmbeddingsBase etwittertends ScheduledEtwitterecutionApp {
  def modelVersion: ModelVersion

  def interestedInFromAPEOutputKVDataset: KeyValDALDataset[
    KeyVal[UserId, ClustersUserIsInterestedIn]
  ]

  def producerEmbeddingsInputKVDataset: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]
  ]

  def interestedInFromAPEOutputThriftDatset: SnapshotDALDataset[UserToInterestedInClusters]

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    //Input args for the run
    val socialProofThreshold = args.int("socialProofThreshold", 2)
    val matwitterClustersFromProducer = args.int("matwitterClustersPerProducer", 5)
    val matwitterClustersPerUserFinalResult = args.int("matwitterInterestedInClustersPerUser", 200)

    //Path variables
    val interestedInFromProducersPath =
      s"/user/cassowary/manhattan_sequence_files/interested_in_from_ape/" + modelVersion

    val interestedInFromProducersThriftPath =
      s"/user/cassowary/manhattan_sequence_files/interested_in_from_ape_thrift/" + modelVersion

    val userUserGraph: TypedPipe[UserAndNeighbors] =
      DAL
        .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
        .withRemoteReadPolicy(AllowCrossDC)
        .toTypedPipe

    val producerEmbeddings = DAL
      .readMostRecentSnapshotNoOlderThan(
        producerEmbeddingsInputKVDataset,
        Days(30)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe.map {
        case KeyVal(producer, embeddings) => (producer, embeddings)
      }

    val result = InterestedInFromAggregatableProducerEmbeddingsBase.run(
      userUserGraph,
      producerEmbeddings,
      matwitterClustersFromProducer,
      socialProofThreshold,
      matwitterClustersPerUserFinalResult,
      modelVersion)

    val keyValEtwitterec =
      result
        .map { case (userId, clusters) => KeyVal(userId, clusters) }
        .writeDALVersionedKeyValEtwitterecution(
          interestedInFromAPEOutputKVDataset,
          D.Suffitwitter(interestedInFromProducersPath)
        )
    val thriftEtwitterec =
      result
        .map {
          case (userId, clusters) =>
            UserToInterestedInClusters(
              userId,
              ModelVersions.toKnownForModelVersion(modelVersion),
              clusters.clusterIdToScores)
        }
        .writeDALSnapshotEtwitterecution(
          interestedInFromAPEOutputThriftDatset,
          D.Daily,
          D.Suffitwitter(interestedInFromProducersThriftPath),
          D.EBLzo(),
          dateRange.end
        )
    Etwitterecution.zip(keyValEtwitterec, thriftEtwitterec).unit
  }
}

/**
 * Adhoc job to generate the interestedIn from aggregatable producer embeddings for the model version 20M145K2020
 *
 * scalding remote run \
 * --user cassowary \
 * --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
 * --principal service_acoount@TWITTER.BIZ \
 * --cluster bluebird-qus1 \
 * --main-class com.twitter.simclusters_v2.scalding.InterestedInFromAPE2020AdhocApp \
 * --target src/scala/com/twitter/simclusters_v2/scalding:interested_in_from_ape_2020-adhoc \
 * --hadoop-properties "mapreduce.map.memory.mb=8192 mapreduce.map.java.opts='-twittermtwitter7618M' mapreduce.reduce.memory.mb=8192 mapreduce.reduce.java.opts='-twittermtwitter7618M'" \
 * -- --outputDir /gcs/user/cassowary/adhoc/your_ldap/interested_in_from_ape_2020_keyval --date 2021-03-05
 */
object InterestedInFromAPE2020AdhocApp etwittertends AdhocEtwitterecutionApp {
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val outputDir = args("outputDir")
    val socialProofThreshold = args.int("socialProofThreshold", 2)
    val matwitterClustersPerUserFinalResult = args.int("matwitterInterestedInClustersPerUser", 200)
    val matwitterClustersFromProducer = args.int("matwitterClustersFromProducer", 5)
    val inputGraph = args.optional("graphInputDir") match {
      case Some(inputDir) => TypedPipe.from(UserAndNeighborsFitwitteredPathSource(inputDir))
      case None =>
        DAL
          .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
          .withRemoteReadPolicy(AllowCrossClusterSameDC)
          .toTypedPipe
    }

    val producerEmbeddings = DAL
      .readMostRecentSnapshotNoOlderThan(
        AggregatableProducerSimclustersEmbeddingsByLogFavScore2020ScalaDataset,
        Days(30)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe.map {
        case KeyVal(producer, embeddings) => (producer, embeddings)
      }

    val result = InterestedInFromAggregatableProducerEmbeddingsBase.run(
      inputGraph,
      producerEmbeddings,
      matwitterClustersFromProducer,
      socialProofThreshold,
      matwitterClustersPerUserFinalResult,
      ModelVersion.Model20m145k2020)

    result
      .writeEtwitterecution(AdhocKeyValSources.interestedInSource(outputDir))
  }
}

/**
 * Helper functions
 */
object InterestedInFromAggregatableProducerEmbeddingsBase {

  /**
   * Helper function to prune the embeddings
   * @param embeddingsWithScore embeddings
   * @param matwitterClusters number of clusters to keep, per userId
   * @param uniqueId for stats
   * @return
   */
  def getPrunedEmbeddings(
    embeddingsWithScore: TypedPipe[(UserId, Seq[(ClusterId, Float)])],
    matwitterClusters: Int
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, Array[(ClusterId, Float)])] = {
    val numProducerMappings = Stat("num_producer_embeddings_total")
    val numProducersWithLargeClusterMappings = Stat(
      "num_producers_with_more_clusters_than_threshold")
    val numProducersWithSmallClusterMappings = Stat(
      "num_producers_with_clusters_less_than_threshold")
    val totalClustersCoverageProducerEmbeddings = Stat("num_clusters_total_producer_embeddings")
    embeddingsWithScore.map {
      case (producerId, clusterArray) =>
        numProducerMappings.inc()
        val clusterSize = clusterArray.size
        totalClustersCoverageProducerEmbeddings.incBy(clusterSize)
        val prunedList = if (clusterSize > matwitterClusters) {
          numProducersWithLargeClusterMappings.inc()
          clusterArray
            .sortBy {
              case (_, knownForScore) => -knownForScore
            }.take(matwitterClusters)
        } else {
          numProducersWithSmallClusterMappings.inc()
          clusterArray
        }
        (producerId, prunedList.toArray)
    }
  }

  /**
   * helper function to remove all scores etwittercept follow and logFav
   * @param interestedInResult interestedIn clusters for a user
   * @return
   */
  def getInterestedInDiscardScores(
    interestedInResult: TypedPipe[(UserId, List[(ClusterId, UserToInterestedInClusterScores)])]
  ): TypedPipe[(UserId, List[(ClusterId, UserToInterestedInClusterScores)])] = {
    interestedInResult.map {
      case (srcId, fullClusterList) =>
        val fullClusterListWithDiscardedScores = fullClusterList.map {
          case (clusterId, clusterDetails) =>
            val clusterDetailsWithoutSocial = UserToInterestedInClusterScores(
              // We are not planning to use the other scores etwittercept for logFav and Follow.
              // Hence, setting others as None for now, we can add them back when needed
              followScore = clusterDetails.followScore,
              logFavScore = clusterDetails.logFavScore,
              logFavScoreClusterNormalizedOnly = clusterDetails.logFavScoreClusterNormalizedOnly
            )
            (clusterId, clusterDetailsWithoutSocial)
        }
        (srcId, fullClusterListWithDiscardedScores)
    }
  }

  /**
   * Helper function to normalize the embeddings
   * @param embeddings cluster embeddings
   * @return
   */
  def getNormalizedEmbeddings(
    embeddings: TypedPipe[(UserId, Seq[(ClusterId, Float)])]
  ): TypedPipe[(UserId, Seq[(ClusterId, Float)])] = {
    embeddings.map {
      case (userId, clustersWithScores) =>
        val l2norm = math.sqrt(clustersWithScores.map(_._2).map(score => score * score).sum)
        (
          userId,
          clustersWithScores.map {
            case (clusterId, score) => (clusterId, (score / l2norm).toFloat)
          })
    }
  }

  def run(
    userUserGraph: TypedPipe[UserAndNeighbors],
    producerEmbeddings: TypedPipe[(SimClustersEmbeddingId, SimClustersEmbedding)],
    matwitterClustersFromProducer: Int,
    socialProofThreshold: Int,
    matwitterClustersPerUserFinalResult: Int,
    modelVersion: ModelVersion
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    import InterestedInFromKnownFor._

    val producerEmbeddingsWithScore: TypedPipe[(UserId, Seq[(ClusterId, Float)])] =
      producerEmbeddings.map {
        case (
              SimClustersEmbeddingId(embeddingType, modelVersion, InternalId.UserId(producerId)),
              simclusterEmbedding) =>
          (
            producerId,
            simclusterEmbedding.embedding.map { simclusterWithScore =>
              // APE dataset has very high producer scores, hence applying log to smoothen them out before
              // computing interestedIn
              (simclusterWithScore.clusterId, math.log(1.0 + simclusterWithScore.score).toFloat)
            })
      }

    val result = keepOnlyTopClusters(
      getInterestedInDiscardScores(
        attachNormalizedScores(
          userClusterPairsWithoutNormalization(
            userUserGraph,
            getPrunedEmbeddings(
              getNormalizedEmbeddings(producerEmbeddingsWithScore),
              matwitterClustersFromProducer),
            socialProofThreshold,
          ))),
      matwitterClustersPerUserFinalResult,
      ModelVersions.toKnownForModelVersion(modelVersion)
    )
    result
  }
}
