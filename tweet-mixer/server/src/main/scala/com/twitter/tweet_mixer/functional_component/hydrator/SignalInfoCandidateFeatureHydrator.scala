package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.SearcherRealtimeHistorySourceSignalFeature
import com.twitter.tweet_mixer.feature.SignalInfo
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableSignalInfoHydrator

object SignalInfoFeature extends Feature[TweetCandidate, Seq[SignalInfo]]

object SignalInfoCandidateFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SignalType")

  override val features: Set[Feature[_, _]] = Set(SignalInfoFeature)

  override def onlyIf(input: PipelineQuery): Boolean = {
    input.params(EnableSignalInfoHydrator)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val signalInfoFeatureMap = USSFeatures
      .getSignalsWithInfo[Long](
        query,
        USSFeatures.TweetFeatures ++ USSFeatures.ProducerFeatures ++ USSFeatures.ImmersiveVideoTweetFeatures
      )

    val searcherRealtimeHistoryFeatureMap =
      USSFeatures.getSignalsWithInfo[String](query, USSFeatures.SearcherRealtimeHistoryFeatures)

    val userId = query.getRequiredUserId

    Stitch.value(candidates.map { candidate =>
      val sourceSignal = candidate.features.getOrElse(SourceSignalFeature, userId)
      val signalInfo = signalInfoFeatureMap.getOrElse(sourceSignal, Seq.empty)
      val searcherRealtimeHistorySourceSignal =
        candidate.features.getOrElse(SearcherRealtimeHistorySourceSignalFeature, "")
      val searcherRealtimeHistorySignalInfo =
        searcherRealtimeHistoryFeatureMap.getOrElse(searcherRealtimeHistorySourceSignal, Seq.empty)
      createFeatureMap(signalInfo ++ searcherRealtimeHistorySignalInfo)
    })
  }

  private def createFeatureMap(signalInfo: Seq[SignalInfo]): FeatureMap =
    FeatureMap(SignalInfoFeature, signalInfo)
}
