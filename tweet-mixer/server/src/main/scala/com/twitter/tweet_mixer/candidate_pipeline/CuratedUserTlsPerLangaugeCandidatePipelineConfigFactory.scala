package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.{thriftscala => tls}
import com.twitter.tweet_mixer.candidate_source.curated_user_tls_per_language.CuratedUserTlsPerLanguageCandidateSource
import com.twitter.tweet_mixer.param.CuratedUserTlsPerLanguageParams._
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsQuery
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants

import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class CuratedUserTlsPerLanguageCandidatePipelineConfigFactory @Inject() (
  curatedUserTlsPerLanguageCandidateSource: CuratedUserTlsPerLanguageCandidateSource) {
  def build[Query <: HomeRecommendedTweetsQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): CuratedUserTlsPerLanguageCandidateSourceConfig[Query] = {
    new CuratedUserTlsPerLanguageCandidateSourceConfig(
      identifierPrefix,
      curatedUserTlsPerLanguageCandidateSource)
  }
}

class CuratedUserTlsPerLanguageCandidateSourceConfig[Query <: HomeRecommendedTweetsQuery](
  identifierPrefix: String,
  curatedUserTlsPerLanguageCandidateSource: CuratedUserTlsPerLanguageCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      Seq[tls.TimelineQuery],
      TweetCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.CuratedUserTlsPerLanguage)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    CuratedUserTlsPerLanguageTweetsEnable)

  private val MaxTweetsToFetchPerAuthor = 10

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    Seq[tls.TimelineQuery]
  ] = { query =>
    val authorIds = query.params(CuratedUserTlsPerLanguageTweetsAuthorListParam)

    authorIds.map { authorId =>
      tls.TimelineQuery(
        timelineType = tls.TimelineType.User,
        timelineId = authorId,
        maxCount = MaxTweetsToFetchPerAuthor.toShort,
        options = Some(tls.TimelineQueryOptions(query.clientContext.userId)),
      )
    }
  }

  override def candidateSource: CandidateSource[Seq[tls.TimelineQuery], TweetCandidate] =
    curatedUserTlsPerLanguageCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetCandidate,
    TweetCandidate
  ] = identity
}
