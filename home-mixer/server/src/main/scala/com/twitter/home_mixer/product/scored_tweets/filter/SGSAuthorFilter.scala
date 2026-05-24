package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
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

@Singleton
case class SGSAuthorFilter @Inject() (socialGraph: SocialGraph)
    extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SGSAuthor")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val authorIds =
      candidates.flatMap { candidate => candidate.features.get(AuthorIdFeature) }.distinct

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
          ids.contains(candidate.features.get(AuthorIdFeature).get)
        }
      FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate))
    }
  }
}
