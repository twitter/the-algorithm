package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.contentrecommender.thriftscala.AlgorithmType
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.timelines.configapi.FSParam
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.topic_recos.thriftscala.TopicTweetPartitionFlatKey
import com.twitter.tweet_mixer.candidate_source.topic_tweets.SkitTimedTopicKeys
import com.twitter.tweet_mixer.candidate_source.topic_tweets.SkitTopicTweetsQuery
import com.twitter.tweet_mixer.feature.UserTopicIdsFeature
import com.twitter.tweet_mixer.functional_component.transformer.SkitTopicTweetsQueryTransformer._
import com.twitter.util.Duration

case class SkitTopicTweetsQueryTransformer(
  maxTweetsPerTopicParam: FSBoundedParam[Int],
  maxTweetsParam: FSBoundedParam[Int],
  minSkitScoreParam: FSBoundedParam[Double],
  minSkitFavCountParam: FSBoundedParam[Int],
  userInferredTopicIdsEnabled: FSParam[Boolean],
  maxTweetAgeInHours: FSBoundedParam[Duration],
  semanticCoreVersionIdParam: FSBoundedParam[Long],
  algorithmType: Option[AlgorithmType],
  simclustersModelVersion: Option[ModelVersion],
  signalsFn: PipelineQuery => Seq[Long])
    extends CandidatePipelineQueryTransformer[PipelineQuery, SkitTopicTweetsQuery] {

  assert(algorithmType.exists(algorithm => AllowedAlgorithmTypes.contains(algorithm)))

  override def transform(inputQuery: PipelineQuery): SkitTopicTweetsQuery = {

    val productContextTopicIds = signalsFn(inputQuery)

    val topics: Seq[Long] =
      if (productContextTopicIds.isEmpty && inputQuery.params(userInferredTopicIdsEnabled)) {
        inputQuery.features.map(_.get(UserTopicIdsFeature)).getOrElse(Seq.empty)
      } else {
        productContextTopicIds
      }

    val latestTweetTimeInHour = inputQuery.queryTime.inHours.toLong

    val earliestTweetTimeInHour = latestTweetTimeInHour -
      math.min(MaxTweetAgeInHours, inputQuery.params(maxTweetAgeInHours).inHours).toLong

    val skitTimedTopicKeys = topics.map { topicId =>
      val timedTopicKeys =
        for (timePartition <- earliestTweetTimeInHour to latestTweetTimeInHour) yield {

          TopicTweetPartitionFlatKey(
            entityId = topicId,
            timePartition = timePartition,
            algorithmType = algorithmType,
            tweetEmbeddingType = Some(EmbeddingType.LogFavBasedTweet),
            language = inputQuery.getLanguageCode.getOrElse("").toLowerCase,
            country = None, // Disable country. It is not used.
            semanticCoreAnnotationVersionId = Some(inputQuery.params(semanticCoreVersionIdParam)),
            simclustersModelVersion = simclustersModelVersion,
          )
        }

      SkitTimedTopicKeys(keys = timedTopicKeys, topicId = topicId)
    }

    SkitTopicTweetsQuery(
      topicKeys = skitTimedTopicKeys,
      maxCandidatesPerTopic = inputQuery.params(maxTweetsPerTopicParam),
      maxCandidates = inputQuery.params(maxTweetsParam),
      minScore = inputQuery.params(minSkitScoreParam),
      minFavCount = inputQuery.params(minSkitFavCountParam)
    )
  }
}

object SkitTopicTweetsQueryTransformer {
  val MaxTweetAgeInHours: Int = 7.days.inHours // Simple guard to prevent overloading

  val AllowedAlgorithmTypes: Set[AlgorithmType] =
    Set(AlgorithmType.TfgTweet, AlgorithmType.SemanticCoreTweet)
}
