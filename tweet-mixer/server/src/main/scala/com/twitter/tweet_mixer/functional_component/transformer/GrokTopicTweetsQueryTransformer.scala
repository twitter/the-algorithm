package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.common.text.language.LocaleUtil
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.tweet_mixer.candidate_source.popular_grok_topic_tweets.GrokTopicTweetsQuery
import com.twitter.tweet_mixer.feature.RequestCountryPlaceIdFeature

case class GrokTopicTweetsQueryTransformer(maxNumCandidatesParam: FSBoundedParam[Int])
    extends CandidatePipelineQueryTransformer[PipelineQuery, GrokTopicTweetsQuery] {

  override def transform(inputQuery: PipelineQuery): GrokTopicTweetsQuery = {
    val normalizedLanguage: Option[String] =
      inputQuery.getLanguageCode.map { languageCode =>
        LocaleUtil.getLocaleOf(languageCode).getLanguage.toLowerCase
      }

    val countryPlaceId = getCountryPlaceId(inputQuery)
    GrokTopicTweetsQuery(
      inputQuery.getOptionalUserId.getOrElse(0L),
      normalizedLanguage,
      countryPlaceId,
      inputQuery.params(maxNumCandidatesParam)
    )
  }

  private def getCountryPlaceId(inputQuery: PipelineQuery): Option[Long] = {
    inputQuery.features.map(_.get(RequestCountryPlaceIdFeature))
  }
}
