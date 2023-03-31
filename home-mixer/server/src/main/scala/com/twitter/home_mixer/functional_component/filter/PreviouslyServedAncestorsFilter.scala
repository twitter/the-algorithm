package com.twitter.home_mixer.functional_component.filter

import com.twitter.common_internal.analytics.twitter_client_user_agent_parser.UserAgent
import com.twitter.home_mixer.model.HomeFeatures.IsAncestorCandidateFeature
import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.twitter.timelines.util.client_info.ClientPlatform

object PreviouslyServedAncestorsFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with TimelinePersistenceUtils {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedAncestors")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val clientPlatform = ClientPlatform.fromQueryOptions(
      clientAppId = query.clientContext.appId,
      userAgent = query.clientContext.userAgent.flatMap(UserAgent.fromString))
    val entries =
      query.features.map(_.getOrElse(PersistenceEntriesFeature, Seq.empty)).toSeq.flatten
    val tweetIds = applicableResponses(clientPlatform, entries)
      .flatMap(_.entries.flatMap(_.tweetIds(includeSourceTweets = true))).toSet
    val ancestorIds =
      candidates
        .filter(_.features.getOrElse(IsAncestorCandidateFeature, false)).map(_.candidate.id).toSet

    val (removed, kept) =
      candidates
        .map(_.candidate).partition(candidate =>
          tweetIds.contains(candidate.id) && ancestorIds.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
