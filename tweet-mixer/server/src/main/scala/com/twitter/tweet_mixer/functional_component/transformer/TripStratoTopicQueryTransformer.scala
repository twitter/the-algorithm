package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.twitter.tweet_mixer.candidate_source.popular_topic_tweets.TripStratoTopicQuery
import com.twitter.tweet_mixer.feature.RequestCountryPlaceIdFeature
import com.twitter.tweet_mixer.feature.UserTopicIdsFeature
import com.twitter.logging.Logger
import com.twitter.common.text.language.LocaleUtil

case class TripStratoTopicQueryTransformer(
  sourceIdsParam: FSParam[Seq[String]],
  maxTweetsPerDomainParam: FSBoundedParam[Int],
  maxTweetsParam: FSBoundedParam[Int],
  popTopicIdsParam: FSParam[Seq[Long]])
    extends CandidatePipelineQueryTransformer[PipelineQuery, TripStratoTopicQuery] {
  private val log = Logger.get(classOf[TripStratoTopicQueryTransformer])

  override def transform(inputQuery: PipelineQuery): TripStratoTopicQuery = {
    val normalizedLanguage: Option[String] =
      inputQuery.getLanguageCode.map { languageCode =>
        LocaleUtil.getLocaleOf(languageCode).getLanguage.toLowerCase
      }
    val domains =
      getDomains(inputQuery = inputQuery, languages = Seq(normalizedLanguage, None).distinct)

    TripStratoTopicQuery(
      domains = domains,
      maxCandidatesPerSource = inputQuery.params(maxTweetsPerDomainParam),
      maxPopTopicCandidates = inputQuery.params(maxTweetsParam)
    )
  }

  private def getDomains(
    inputQuery: PipelineQuery,
    languages: Seq[Option[String]]
  ): Seq[TripDomain] = {
    val candidateSourceIds: Seq[String] = inputQuery.params(sourceIdsParam)

    val countryPlaceId: Option[Long] = getCountryPlaceId(inputQuery)
    val userSelectedTopicIds: Seq[Long] = getUserSelectedTopicIds(inputQuery)
    val topicIds: Seq[Long] = {
      // only use popular topic ids if user did not select any topics
      if (userSelectedTopicIds.isEmpty) {
        val popTopicIds: Seq[Long] = inputQuery.params(popTopicIdsParam)
        popTopicIds
      } else {
        userSelectedTopicIds
      }
    }

    candidateSourceIds.flatMap { candidateSourceId =>
      languages.map { language =>
        topicIds.map { topicId =>
          TripDomain(
            sourceId = candidateSourceId,
            language = language,
            placeId = countryPlaceId,
            topicId = Some(topicId)
          )
        }
      }
    }.flatten
  }

  private def getCountryPlaceId(inputQuery: PipelineQuery): Option[Long] = {
    inputQuery.features.map(_.get(RequestCountryPlaceIdFeature))
  }

  private def getUserSelectedTopicIds(inputQuery: PipelineQuery): Seq[Long] = {
    inputQuery.features.map(_.getOrElse(UserTopicIdsFeature, Seq.empty)).getOrElse(Seq.empty)
  }
}
