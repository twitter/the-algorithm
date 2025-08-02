package com.twitter.home_mitwitterer.functional_component.filter

import com.twitter.common_internal.analytics.twitter_client_user_agent_parser.UserAgent
import com.twitter.home_mitwitterer.model.HomeFeatures.IsAncestorCandidateFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemitwitterer.injection.store.persistence.TimelinePersistenceUtils
import com.twitter.timelines.util.client_info.ClientPlatform

object PreviouslyServedAncestorsFilter
    etwittertends Filter[PipelineQuery, TweetCandidate]
    with TimelinePersistenceUtils {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedAncestors")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val clientPlatform = ClientPlatform.fromQueryOptions(
      clientAppId = query.clientContetwittert.appId,
      userAgent = query.clientContetwittert.userAgent.flatMap(UserAgent.fromString))
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
