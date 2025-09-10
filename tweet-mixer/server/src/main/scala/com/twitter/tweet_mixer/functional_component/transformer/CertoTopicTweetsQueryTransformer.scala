package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.common.text.language.LocaleUtil
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.topic_tweets.CertoTopicTweetsQuery
import com.twitter.tweet_mixer.feature.UserTopicIdsFeature

case class CertoTopicTweetsQueryTransformer(
  maxTweetsPerTopicParam: FSBoundedParam[Int],
  maxTweetsParam: FSBoundedParam[Int],
  minCertoScoreParam: FSBoundedParam[Double],
  minCertoFavCountParam: FSBoundedParam[Int],
  userInferredTopicIdsEnabled: FSParam[Boolean],
  signalsFn: PipelineQuery => Seq[Long])
    extends CandidatePipelineQueryTransformer[PipelineQuery, CertoTopicTweetsQuery] {

  override def transform(inputQuery: PipelineQuery): CertoTopicTweetsQuery = {
    val normalizedLanguage: Option[String] =
      inputQuery.getLanguageCode.map { languageCode =>
        LocaleUtil.getLocaleOf(languageCode).getLanguage.toLowerCase
      }

    val productContextTopicIds = signalsFn(inputQuery)
    val topics: Seq[TopicId] =
      if (productContextTopicIds.isEmpty && inputQuery.params(userInferredTopicIdsEnabled)) {
        getUserSelectedTopics(inputQuery, language = normalizedLanguage, countryCode = None)
      } else {
        productContextTopicIds.map { topicId: Long =>
          TopicId(entityId = topicId, language = normalizedLanguage, country = None)
        }
      }

    CertoTopicTweetsQuery(
      topicIds = topics,
      maxCandidatesPerTopic = inputQuery.params(maxTweetsPerTopicParam),
      maxCandidates = inputQuery.params(maxTweetsParam),
      minCertoScore = inputQuery.params(minCertoScoreParam),
      minFavCount = inputQuery.params(minCertoFavCountParam)
    )
  }

  private def getUserSelectedTopics(
    inputQuery: PipelineQuery,
    language: Option[String],
    countryCode: Option[String]
  ): Seq[TopicId] = {
    val topicIds = inputQuery.features.map(_.get(UserTopicIdsFeature)).getOrElse(Seq.empty)
    topicIds.map { topicId: Long =>
      TopicId(entityId = topicId, language = language, country = countryCode)
    }
  }
}
