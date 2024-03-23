package com.ExTwitter.home_mixer.functional_component.filter

import com.ExTwitter.common_internal.analytics.ExTwitter_client_user_agent_parser.UserAgent
import com.ExTwitter.home_mixer.model.HomeFeatures.IsAncestorCandidateFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.ExTwitter.timelines.util.client_info.ClientPlatform

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
