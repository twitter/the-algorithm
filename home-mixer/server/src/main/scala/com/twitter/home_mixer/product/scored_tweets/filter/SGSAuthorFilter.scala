package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Drop tweets when the viewer mutes, blocks, or is blocked by a user on the card.
 *
 * AuthorId is the poster (the retweeter on a retweet). Mute and block must also
 * apply to the original author and the quoted author. FeedbackFatigueFilter already
 * uses the original author for "see fewer" on tweets.
 */
@Singleton
case class SGSAuthorFilter @Inject() (socialGraph: SocialGraph)
    extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SGSAuthor")

  private def userIdsForSgs(
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Seq[Long] =
    (candidate.features.getOrElse(AuthorIdFeature, None) ++
      candidate.features.getOrElse(SourceUserIdFeature, None) ++
      candidate.features.getOrElse(QuotedUserIdFeature, None)).toSeq.distinct

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val authorIds = candidates.flatMap(userIdsForSgs).distinct

    if (authorIds.isEmpty) {
      Stitch.value(FilterResult(kept = candidates.map(_.candidate), removed = Seq.empty))
    } else {
      val request = sg.IdsRequest(
        relationships = Seq(
          sg.SrcRelationship(
            source = query.getRequiredUserId,
            relationshipType = sg.RelationshipType.Blocking,
            hasRelationship = true,
            targets = Some(authorIds)),
          sg.SrcRelationship(
            source = query.getRequiredUserId,
            relationshipType = sg.RelationshipType.BlockedBy,
            hasRelationship = true,
            targets = Some(authorIds)),
          sg.SrcRelationship(
            source = query.getRequiredUserId,
            relationshipType = sg.RelationshipType.Muting,
            hasRelationship = true,
            targets = Some(authorIds))
        ),
        pageRequest = Some(sg.PageRequest(selectAll = Some(true))),
        context = Some(sg.LookupContext(performUnion = Some(true)))
      )

      socialGraph.ids(request).map { result =>
        val ids = result.ids.toSet
        val (removed, kept) =
          candidates.partition { candidate =>
            userIdsForSgs(candidate).exists(ids.contains)
          }
        FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate))
      }
    }
  }
}
