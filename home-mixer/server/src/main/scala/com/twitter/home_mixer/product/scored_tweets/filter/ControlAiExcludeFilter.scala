package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ControlAiEmbeddingSimilarityThresholdParam
import com.twitter.home_mixer.product.scored_tweets.util.ControlAiUtil
import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.ControlAiTopicEmbeddingMapFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.UserControlAiFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.control_ai.control.{thriftscala => ci}

object ControlAiExcludeFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("ControlAiExclude")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val actions = query.features
      .flatMap(_.getOrElse(UserControlAiFeature, None))
      .map(_.actions).getOrElse(Seq.empty).filter(_.actionType == ci.ActionType.Exclude)

    val (removed, kept) = candidates.partition { candidate =>
      actions.exists(
        ControlAiUtil.conditionMatch(
          _,
          candidate,
          query.features
            .map(_.get(ControlAiTopicEmbeddingMapFeature)).getOrElse(Map.empty),
          threshold = query.params(ControlAiEmbeddingSimilarityThresholdParam)
        ))
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
