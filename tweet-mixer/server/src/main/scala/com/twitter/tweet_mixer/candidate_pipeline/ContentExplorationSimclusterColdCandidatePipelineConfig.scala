package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SimclusterColdPostsCandidateSource
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SimclusterColdPostsQuery
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.CoreProductGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.BusinessHours
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationSimclusterColdPostsEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationSimclusterColdPostsMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationSimclusterColdPostsPostsPerSimcluster
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentExplorationSimclusterColdCandidatePipelineConfig @Inject() (
  simclusterColdPostsCandidateSource: SimclusterColdPostsCandidateSource,
  identifierPrefix: String)
    extends CandidatePipelineConfig[
      PipelineQuery,
      SimclusterColdPostsQuery,
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationSimclusterColdPosts)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "ContentExplorationSimclusterColdPosts",
      param = ContentExplorationSimclusterColdPostsEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    SimclusterColdPostsQuery
  ] = { query =>
    SimclusterColdPostsQuery(
      userId = query.getRequiredUserId,
      postsPerSimcluster = query.params(ContentExplorationSimclusterColdPostsPostsPerSimcluster),
      maxCandidates = query.params(ContentExplorationSimclusterColdPostsMaxCandidates)
    )
  }

  override def candidateSource: CandidateSource[
    SimclusterColdPostsQuery,
    Long
  ] = simclusterColdPostsCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    TweetCandidate
  ] = { tweetId =>
    TweetCandidate(id = tweetId)
  }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(threshold = 95, notificationType = BusinessHours)(CoreProductGroupMap),
    defaultEmptyResponseRateAlert()(CoreProductGroupMap)
  )
}
