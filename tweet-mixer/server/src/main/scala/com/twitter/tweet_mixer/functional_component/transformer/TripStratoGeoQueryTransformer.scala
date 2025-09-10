package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.twitter.tweet_mixer.candidate_source.popular_geo_tweets.TripStratoGeoQuery
import com.twitter.tweet_mixer.feature.RequestCountryPlaceIdFeature
import com.twitter.common.text.language.LocaleUtil

import com.twitter.logging.Logger

case class TripStratoGeoQueryTransformer(
  geoSourceIdsParam: FSParam[Seq[String]],
  maxTweetsPerDomainParam: FSBoundedParam[Int],
  maxPopGeoTweetsParam: FSBoundedParam[Int])
    extends CandidatePipelineQueryTransformer[PipelineQuery, TripStratoGeoQuery] {
  private val log = Logger.get(classOf[TripStratoGeoQueryTransformer])

  override def transform(inputQuery: PipelineQuery): TripStratoGeoQuery = {
    val normalizedLanguage: Option[String] =
      inputQuery.getLanguageCode.map { languageCode =>
        LocaleUtil.getLocaleOf(languageCode).getLanguage.toLowerCase
      }
    val domains = getDomains(
      inputQuery = inputQuery,
      languages = Seq(normalizedLanguage, None)
    )

    TripStratoGeoQuery(
      domains = domains,
      maxCandidatesPerSource = inputQuery.params(maxTweetsPerDomainParam),
      maxPopGeoCandidates = inputQuery.params(maxPopGeoTweetsParam)
    )
  }

  private def getDomains(
    inputQuery: PipelineQuery,
    languages: Seq[Option[String]]
  ): Seq[TripDomain] = {
    val candidateSourceIds: Seq[String] = inputQuery.params(geoSourceIdsParam)

    val countryPlaceId: Option[Long] = getCountryPlaceId(inputQuery)

    candidateSourceIds.flatMap { candidateSourceId =>
      languages.map { language =>
        TripDomain(sourceId = candidateSourceId, language = language, placeId = countryPlaceId)
      }
    }
  }

  private def getCountryPlaceId(inputQuery: PipelineQuery): Option[Long] = {
    inputQuery.features.map(_.get(RequestCountryPlaceIdFeature))
  }
}
