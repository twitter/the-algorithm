package com.twitter.simclusters_v2.scalding.topic_recommendations

import com.twitter.bijection.Bufferable
import com.twitter.bijection.Injection
import com.twitter.recos.entities.thriftscala._
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.Country
import com.twitter.simclusters_v2.common.Language
import com.twitter.simclusters_v2.common.TopicId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.hdfs_sources.DataSources
import com.twitter.simclusters_v2.hdfs_sources.TopProducersForLocaleTopicsFromTopicFollowGraphScalaDataset
import com.twitter.simclusters_v2.scalding.common.matritwitter.SparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.ProducerId
import com.twitter.simclusters_v2.scalding.embedding.common.EtwitterternalDataSources
import com.twitter.simclusters_v2.thriftscala.UserAndNeighbors
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledEtwitterecutionApp
import java.util.TimeZone

/**
 * In this file, we compute the top producers for a topic from the Topic Follow Graph
 *
 * It works as follows:
 *
 *  1. Producer embedding: List of users who follow the producer's profile and follow atleast one topic
 *
 *  2. Topic embedding: List of users who follow the topic
 *
 *  3. Score(producer, topic) = cosine similarity of the producer and topic embedding as defined above
 *
 *  4. Please note that we compute the top producers for each topic locale.
 */

/**
scalding remote run --user cassowary \
 --target src/scala/com/twitter/simclusters_v2/scalding/topic_recommendations:top_producers_for_topics_from_topic_follow_graph-adhoc \
 --main-class com.twitter.simclusters_v2.scalding.topic_recommendations.ProducersForTopicsFromTopicFollowGraphAdhocApp \
 --submitter  hadoopnest1.atla.twitter.com  \
 --  --date 2021-01-06 --minActiveFollowers 400 --matwitterProducersPerTopic 50 \
 --output_dir_producers_per_topic /user/cassowary/adhoc/ldap/ttf_profile_pages_topics_to_producers
 */

object ProducersForTopicsFromTopicFollowGraphAdhocApp etwittertends AdhocEtwitterecutionApp {

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    import ProducersForTopicsFromTopicFollowGraph._
    val outputDirProducersPerTopic = args("output_dir_producers_per_topic")
    val minActiveFollowersForProducer = args.int("minActiveFollowers", 400)
    val matwitterProducersPerTopicPerLocale = args.int("matwitterProducersPerTopic", 50)
    val minTopicFollows = args.int("minTopicFollows", 100)

    val topicsFollowedByProducersFollowers = getTopicsFromProducersFollowers(
      DataSources
        .userUserNormalizedGraphSource(dateRange.prepend(Days(7))),
      EtwitterternalDataSources.topicFollowGraphSource,
      EtwitterternalDataSources.userSource,
      EtwitterternalDataSources.inferredUserConsumedLanguageSource,
      minActiveFollowersForProducer,
      minTopicFollows
    )

    sortAndGetTopProducersPerLocaleTopic(
      topicsFollowedByProducersFollowers,
      matwitterProducersPerTopicPerLocale).writeEtwitterecution(TypedTsv(outputDirProducersPerTopic))

  }
}

/**
capesospy-v2 update --build_locally \
 --start_cron top_producers_for_topics_from_topic_follow_graph \
 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */

object ProducersForTopicsFromTopicFollowGraphBatchApp etwittertends ScheduledEtwitterecutionApp {
  override val firstTime: RichDate = RichDate("2020-10-01")

  override val batchIncrement: Duration = Days(1)

  private val topProducersForLocaleTopicsPath: String =
    "/user/cassowary/manhattan_sequence_files/top_producers_for_topics_from_topic_follow_graph"

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    import ProducersForTopicsFromTopicFollowGraph._
    val minActiveFollowersForProducer = args.int("minActiveFollowers", 400)
    val matwitterProducersPerTopicPerLocale = args.int("matwitterProducersPerTopic", 50)
    val minTopicFollows = args.int("minTopicFollows", 100)

    val topicsFollowedByProducersFollowers = getTopicsFromProducersFollowers(
      DataSources
        .userUserNormalizedGraphSource(dateRange.prepend(Days(7))),
      EtwitterternalDataSources.topicFollowGraphSource,
      EtwitterternalDataSources.userSource,
      EtwitterternalDataSources.inferredUserConsumedLanguageSource,
      minActiveFollowersForProducer,
      minTopicFollows
    )

    sortAndGetTopProducersPerLocaleTopic(
      topicsFollowedByProducersFollowers,
      matwitterProducersPerTopicPerLocale)
      .map {
        case ((topicId, languageOpt, countryOpt), producersWithScores) =>
          KeyVal(
            SemanticCoreEntityWithLocale(
              entityId = topicId,
              contetwittert = Locale(language = languageOpt, country = countryOpt)),
            UserScoreList(producersWithScores.map {
              case (producerId, producerScore) =>
                UserWithScore(userId = producerId, score = producerScore)
            })
          )
      }.writeDALVersionedKeyValEtwitterecution(
        TopProducersForLocaleTopicsFromTopicFollowGraphScalaDataset,
        D.Suffitwitter(topProducersForLocaleTopicsPath),
        version = EtwitterplicitEndTime(dateRange.end)
      )
  }
}

object ProducersForTopicsFromTopicFollowGraph {

  implicit val sparseMatritwitterInj: Injection[
    (ProducerId, Option[Language], Option[Country]),
    Array[Byte]
  ] =
    Bufferable.injectionOf[(ProducerId, Option[Language], Option[Country])]

  // This function takes the producer to topics map and generates the sorted and
  // truncated top producers ranked list for each locale topic
  def sortAndGetTopProducersPerLocaleTopic(
    producerToTopics: TypedPipe[(ProducerId, (TopicId, Option[Language], Option[Country]), Double)],
    matwitterProducersPerLocaleTopic: Int
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[((TopicId, Option[Language], Option[Country]), List[(ProducerId, Double)])] = {
    val numTopicsWithLocales = Stat("num_topics_with_locales")
    producerToTopics
      .map {
        case (producerId, (topicId, languageOpt, countryOpt), score) =>
          ((topicId, languageOpt, countryOpt), Seq((producerId, score)))
      }
      .sumByKey.mapValues { producersList =>
        numTopicsWithLocales.inc()
        producersList.sortBy(-_._2).take(matwitterProducersPerLocaleTopic).toList
      }.toTypedPipe
  }

  def getTopicsFromProducersFollowers(
    userUserGraph: TypedPipe[UserAndNeighbors],
    followedTopicsToUsers: TypedPipe[(TopicId, UserId)],
    userSource: TypedPipe[(UserId, (Country, Language))],
    userLanguages: TypedPipe[(UserId, Seq[(Language, Double)])],
    minActiveFollowersForProducer: Int,
    minTopicFollows: Int
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): TypedPipe[(ProducerId, (TopicId, Option[Language], Option[Country]), Double)] = {

    val usersFollowingTopics: TypedPipe[UserId] = followedTopicsToUsers.map(_._2).distinct
    val producerToUsersSparseMatritwitter: SparseMatritwitter[ProducerId, UserId, Double] =
      TopicsForProducersUtils
        .getProducersToFollowedByUsersSparseMatritwitter(
          userUserGraph,
          minActiveFollowersForProducer).filterCols(usersFollowingTopics).rowL2Normalize

    val userToTopicsSparseSkinnyMatritwitter: SparseMatritwitter[
      UserId,
      (TopicId, Option[Language], Option[Country]),
      Double
    ] =
      TopicsForProducersUtils
        .getFollowedTopicsToUserSparseMatritwitter(
          followedTopicsToUsers,
          userSource,
          userLanguages,
          minTopicFollows).rowL2Normalize.transpose

    // Obtain the Producer to Locale Topics Matritwitter
    val producersToLocaleTopicsMatritwitter: SparseMatritwitter[
      ProducerId,
      (TopicId, Option[Language], Option[Country]),
      Double
    ] =
      producerToUsersSparseMatritwitter.multiplySparseMatritwitter(userToTopicsSparseSkinnyMatritwitter)

    producersToLocaleTopicsMatritwitter.toTypedPipe
  }
}
