package com.twitter.simclusters_v2.scalding.embedding.tfg

import com.twitter.bijection.{Bufferable, Injection}
import com.twitter.dal.client.dataset.{KeyValDALDataset, SnapshotDALDatasetBase}
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite.{D, _}
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{Language, SimClustersEmbedding, TopicId}
import com.twitter.simclusters_v2.hdfs_sources.InterestedInSources
import com.twitter.simclusters_v2.scalding.common.matritwitter.{SparseMatritwitter, SparseRowMatritwitter}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.{UserId, _}
import com.twitter.simclusters_v2.scalding.embedding.common.{
  EmbeddingUtil,
  EtwitterternalDataSources,
  SimClustersEmbeddingBaseJob
}
import com.twitter.simclusters_v2.thriftscala.{
  ClustersScore,
  EmbeddingType,
  TfgTopicEmbeddings,
  InternalId,
  LocaleEntityId,
  ModelVersion,
  SimClustersEmbeddingId,
  UserToInterestedInClusterScores,
  SimClustersEmbedding => ThriftSimClustersEmbedding,
  TopicId => TID
}
import com.twitter.wtf.scalding.jobs.common.DateRangeEtwitterecutionApp

import java.util.TimeZone

/**
 * Base app for the Topic-Follow-Graph (TFG) topic embeddings
 * A topic's TFG embedding is represented by the sum of all the users who followed the topic
 */
trait TfgBasedTopicEmbeddingsBaseApp
    etwittertends SimClustersEmbeddingBaseJob[(TopicId, Language)]
    with DateRangeEtwitterecutionApp {

  val isAdhoc: Boolean
  val embeddingType: EmbeddingType
  val embeddingSource: KeyValDALDataset[KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]]
  val pathSuffitwitter: String
  val modelVersion: ModelVersion
  val parquetDataSource: SnapshotDALDatasetBase[TfgTopicEmbeddings]
  def scoreEtwittertractor: UserToInterestedInClusterScores => Double

  override def numClustersPerNoun: Int = 50
  override def numNounsPerClusters: Int = 1 // not used for now. Set to an arbitrary number
  override def thresholdForEmbeddingScores: Double = 0.001

  val minNumFollowers = 100

  override def prepareNounToUserMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseMatritwitter[(TopicId, Language), UserId, Double] = {
    implicit val inj: Injection[(TopicId, Language), Array[Byte]] =
      Bufferable.injectionOf[(TopicId, Language)]

    val topicLangUsers = EtwitterternalDataSources.topicFollowGraphSource
      .map { case (topic, user) => (user, topic) }
      .join(EtwitterternalDataSources.userSource)
      .map {
        case (user, (topic, (_, language))) =>
          ((topic, language), user, 1.0)
      }
      .forceToDisk

    val validTopicLang =
      SparseMatritwitter(topicLangUsers).rowNnz.filter {
        case (_, nzCount) => nzCount >= minNumFollowers
      }.keys

    SparseMatritwitter[(TopicId, Language), UserId, Double](topicLangUsers).filterRows(validTopicLang)
  }

  override def prepareUserToClusterMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseRowMatritwitter[UserId, ClusterId, Double] =
    SparseRowMatritwitter(
      InterestedInSources
        .simClustersInterestedInSource(modelVersion, dateRange, timeZone)
        .map {
          case (userId, clustersUserIsInterestedIn) =>
            userId -> clustersUserIsInterestedIn.clusterIdToScores
              .map {
                case (clusterId, scores) =>
                  clusterId -> scoreEtwittertractor(scores)
              }
              .filter(_._2 > 0.0)
              .toMap
        },
      isSkinnyMatritwitter = true
    )

  override def writeNounToClustersIndetwitter(
    output: TypedPipe[((TopicId, Language), Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val topicEmbeddingCount = Stat(s"topic_embedding_count")
    val user = System.getenv("USER")
    val parquetEtwitterec = output
      .map {
        case ((entityId, language), clustersWithScores) =>
          TfgTopicEmbeddings(
            TID(
              entityId = entityId,
              language = Some(language),
            ),
            clusterScore = clustersWithScores.map {
              case (clusterId, score) => ClustersScore(clusterId, score)
            }
          )
      }
      .writeDALSnapshotEtwitterecution(
        parquetDataSource,
        D.Daily,
        D.Suffitwitter(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = isAdhoc,
            isManhattanKeyVal = false,
            modelVersion,
            pathSuffitwitter + "/snapshot")),
        D.Parquet,
        dateRange.end
      )

    val tsvEtwitterec =
      output
        .map {
          case ((entityId, language), clustersWithScores) =>
            (entityId, language, clustersWithScores.mkString(";"))
        }
        .shard(10)
        .writeEtwitterecution(TypedTsv[(TopicId, Language, String)](
          s"/user/$user/adhoc/topic_embedding/$pathSuffitwitter/$ModelVersionPathMap($modelVersion)"))

    val keyValEtwitterec = output
      .flatMap {
        case ((entityId, lang), clustersWithScores) =>
          topicEmbeddingCount.inc()
          val embedding = SimClustersEmbedding(clustersWithScores).toThrift
          Seq(
            KeyVal(
              SimClustersEmbeddingId(
                embeddingType,
                modelVersion,
                InternalId.LocaleEntityId(LocaleEntityId(entityId, lang))
              ),
              embedding
            ),
            KeyVal(
              SimClustersEmbeddingId(
                embeddingType,
                modelVersion,
                InternalId.TopicId(TID(entityId, Some(lang), country = None))
              ),
              embedding
            ),
          )
      }
      .writeDALVersionedKeyValEtwitterecution(
        embeddingSource,
        D.Suffitwitter(
          EmbeddingUtil
            .getHdfsPath(isAdhoc = isAdhoc, isManhattanKeyVal = true, modelVersion, pathSuffitwitter))
      )
    if (isAdhoc)
      Etwitterecution.zip(tsvEtwitterec, keyValEtwitterec, parquetEtwitterec).unit
    else
      Etwitterecution.zip(keyValEtwitterec, parquetEtwitterec).unit
  }

  override def writeClusterToNounsIndetwitter(
    output: TypedPipe[(ClusterId, Seq[((TopicId, Language), Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    Etwitterecution.unit // do not need this
  }
}
