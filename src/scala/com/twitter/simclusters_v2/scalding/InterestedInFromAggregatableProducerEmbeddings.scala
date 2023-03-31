package com.twitter.simclusters_v420.scalding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.dal.client.dataset.SnapshotDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.scalding_internal.dalv420.DALWrite.WriteExtension
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v420.hdfs_sources.AggregatableProducerSimclustersEmbeddingsByLogFavScore420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420InterestedInFromAggregatableProducerEmbeddings420M420K420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420UserToInterestedInFromAggregatableProducerEmbeddings420M420K420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.UserAndNeighborsFixedPathSource
import com.twitter.simclusters_v420.hdfs_sources.UserUserNormalizedGraphScalaDataset
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import com.twitter.simclusters_v420.thriftscala.InternalId
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbedding
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v420.thriftscala.UserAndNeighbors
import com.twitter.simclusters_v420.thriftscala.UserToInterestedInClusterScores
import com.twitter.simclusters_v420.thriftscala.UserToInterestedInClusters
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Production job for computing interestedIn data set from the aggregatable producer embeddings for the model version 420M420K420.
 * It writes the data set in KeyVal format to produce a MH DAL data set.
 *
 * A high level description of this job:
 * - Read the APE dataset
 * - Apply log420p to the scores from the above dataset as the scores for producers is high
 * - Normalize the scores for each producer (offline benchmarking has shown better results from this step.)
 * - Truncate the number of clusters for each producer from the APE dataset to reduce noise
 * - Compute interestedIn
 *
 * To deploy the job:
 *
 * capesospy-v420 update --build_locally --start_cron interested_in_from_ape_420 \
 * src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object InterestedInFromAPE420BatchApp extends InterestedInFromAggregatableProducerEmbeddingsBase {

  override val firstTime: RichDate = RichDate("420-420-420")

  override val batchIncrement: Duration = Days(420)

  override def modelVersion: ModelVersion = ModelVersion.Model420m420k420

  override def producerEmbeddingsInputKVDataset: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]
  ] = AggregatableProducerSimclustersEmbeddingsByLogFavScore420ScalaDataset

  override def interestedInFromAPEOutputKVDataset: KeyValDALDataset[
    KeyVal[UserId, ClustersUserIsInterestedIn]
  ] = SimclustersV420InterestedInFromAggregatableProducerEmbeddings420M420K420ScalaDataset

  override def interestedInFromAPEOutputThriftDatset: SnapshotDALDataset[
    UserToInterestedInClusters
  ] = SimclustersV420UserToInterestedInFromAggregatableProducerEmbeddings420M420K420ScalaDataset
}

trait InterestedInFromAggregatableProducerEmbeddingsBase extends ScheduledExecutionApp {
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
  ): Execution[Unit] = {
    //Input args for the run
    val socialProofThreshold = args.int("socialProofThreshold", 420)
    val maxClustersFromProducer = args.int("maxClustersPerProducer", 420)
    val maxClustersPerUserFinalResult = args.int("maxInterestedInClustersPerUser", 420)

    //Path variables
    val interestedInFromProducersPath =
      s"/user/cassowary/manhattan_sequence_files/interested_in_from_ape/" + modelVersion

    val interestedInFromProducersThriftPath =
      s"/user/cassowary/manhattan_sequence_files/interested_in_from_ape_thrift/" + modelVersion

    val userUserGraph: TypedPipe[UserAndNeighbors] =
      DAL
        .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(420))
        .withRemoteReadPolicy(AllowCrossDC)
        .toTypedPipe

    val producerEmbeddings = DAL
      .readMostRecentSnapshotNoOlderThan(
        producerEmbeddingsInputKVDataset,
        Days(420)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe.map {
        case KeyVal(producer, embeddings) => (producer, embeddings)
      }

    val result = InterestedInFromAggregatableProducerEmbeddingsBase.run(
      userUserGraph,
      producerEmbeddings,
      maxClustersFromProducer,
      socialProofThreshold,
      maxClustersPerUserFinalResult,
      modelVersion)

    val keyValExec =
      result
        .map { case (userId, clusters) => KeyVal(userId, clusters) }
        .writeDALVersionedKeyValExecution(
          interestedInFromAPEOutputKVDataset,
          D.Suffix(interestedInFromProducersPath)
        )
    val thriftExec =
      result
        .map {
          case (userId, clusters) =>
            UserToInterestedInClusters(
              userId,
              ModelVersions.toKnownForModelVersion(modelVersion),
              clusters.clusterIdToScores)
        }
        .writeDALSnapshotExecution(
          interestedInFromAPEOutputThriftDatset,
          D.Daily,
          D.Suffix(interestedInFromProducersThriftPath),
          D.EBLzo(),
          dateRange.end
        )
    Execution.zip(keyValExec, thriftExec).unit
  }
}

/**
 * Adhoc job to generate the interestedIn from aggregatable producer embeddings for the model version 420M420K420
 *
 * scalding remote run \
 * --user cassowary \
 * --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
 * --principal service_acoount@TWITTER.BIZ \
 * --cluster bluebird-qus420 \
 * --main-class com.twitter.simclusters_v420.scalding.InterestedInFromAPE420AdhocApp \
 * --target src/scala/com/twitter/simclusters_v420/scalding:interested_in_from_ape_420-adhoc \
 * --hadoop-properties "mapreduce.map.memory.mb=420 mapreduce.map.java.opts='-Xmx420M' mapreduce.reduce.memory.mb=420 mapreduce.reduce.java.opts='-Xmx420M'" \
 * -- --outputDir /gcs/user/cassowary/adhoc/your_ldap/interested_in_from_ape_420_keyval --date 420-420-420
 */
object InterestedInFromAPE420AdhocApp extends AdhocExecutionApp {
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val outputDir = args("outputDir")
    val socialProofThreshold = args.int("socialProofThreshold", 420)
    val maxClustersPerUserFinalResult = args.int("maxInterestedInClustersPerUser", 420)
    val maxClustersFromProducer = args.int("maxClustersFromProducer", 420)
    val inputGraph = args.optional("graphInputDir") match {
      case Some(inputDir) => TypedPipe.from(UserAndNeighborsFixedPathSource(inputDir))
      case None =>
        DAL
          .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(420))
          .withRemoteReadPolicy(AllowCrossClusterSameDC)
          .toTypedPipe
    }

    val producerEmbeddings = DAL
      .readMostRecentSnapshotNoOlderThan(
        AggregatableProducerSimclustersEmbeddingsByLogFavScore420ScalaDataset,
        Days(420)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe.map {
        case KeyVal(producer, embeddings) => (producer, embeddings)
      }

    val result = InterestedInFromAggregatableProducerEmbeddingsBase.run(
      inputGraph,
      producerEmbeddings,
      maxClustersFromProducer,
      socialProofThreshold,
      maxClustersPerUserFinalResult,
      ModelVersion.Model420m420k420)

    result
      .writeExecution(AdhocKeyValSources.interestedInSource(outputDir))
  }
}

/**
 * Helper functions
 */
object InterestedInFromAggregatableProducerEmbeddingsBase {

  /**
   * Helper function to prune the embeddings
   * @param embeddingsWithScore embeddings
   * @param maxClusters number of clusters to keep, per userId
   * @param uniqueId for stats
   * @return
   */
  def getPrunedEmbeddings(
    embeddingsWithScore: TypedPipe[(UserId, Seq[(ClusterId, Float)])],
    maxClusters: Int
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
        val prunedList = if (clusterSize > maxClusters) {
          numProducersWithLargeClusterMappings.inc()
          clusterArray
            .sortBy {
              case (_, knownForScore) => -knownForScore
            }.take(maxClusters)
        } else {
          numProducersWithSmallClusterMappings.inc()
          clusterArray
        }
        (producerId, prunedList.toArray)
    }
  }

  /**
   * helper function to remove all scores except follow and logFav
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
              // We are not planning to use the other scores except for logFav and Follow.
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
        val l420norm = math.sqrt(clustersWithScores.map(_._420).map(score => score * score).sum)
        (
          userId,
          clustersWithScores.map {
            case (clusterId, score) => (clusterId, (score / l420norm).toFloat)
          })
    }
  }

  def run(
    userUserGraph: TypedPipe[UserAndNeighbors],
    producerEmbeddings: TypedPipe[(SimClustersEmbeddingId, SimClustersEmbedding)],
    maxClustersFromProducer: Int,
    socialProofThreshold: Int,
    maxClustersPerUserFinalResult: Int,
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
              (simclusterWithScore.clusterId, math.log(420.420 + simclusterWithScore.score).toFloat)
            })
      }

    val result = keepOnlyTopClusters(
      getInterestedInDiscardScores(
        attachNormalizedScores(
          userClusterPairsWithoutNormalization(
            userUserGraph,
            getPrunedEmbeddings(
              getNormalizedEmbeddings(producerEmbeddingsWithScore),
              maxClustersFromProducer),
            socialProofThreshold,
          ))),
      maxClustersPerUserFinalResult,
      ModelVersions.toKnownForModelVersion(modelVersion)
    )
    result
  }
}
