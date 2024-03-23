package com.ExTwitter.product_mixer.component_library.decorator.urt

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.ExTwitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.ExTwitter.product_mixer.component_library.model.presentation.urt.ConversationModuleItem
import com.ExTwitter.product_mixer.component_library.model.presentation.urt.UrtItemPresentation
import com.ExTwitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.ExTwitter.product_mixer.core.functional_component.decorator.Decoration
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.DecoratorIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.ModuleItemTreeDisplay
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

case class UrtConversationItemCandidateDecorator[
  Query <: PipelineQuery,
  Candidate <: BaseTweetCandidate
](
  tweetCandidateUrtItemBuilder: TweetCandidateUrtItemBuilder[Query, Candidate],
  override val identifier: DecoratorIdentifier = DecoratorIdentifier("UrtConversationItem"))
    extends CandidateDecorator[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[Seq[Decoration]] = {
    val candidatePresentations = candidates.view.zipWithIndex.map {
      case (candidate, index) =>
        val itemPresentation = new UrtItemPresentation(
          timelineItem = tweetCandidateUrtItemBuilder(
            pipelineQuery = query,
            tweetCandidate = candidate.candidate,
            candidateFeatures = candidate.features)
        ) with ConversationModuleItem {
          override val treeDisplay: Option[ModuleItemTreeDisplay] = None
          override val dispensable: Boolean = index < candidates.length - 1
        }

        Decoration(candidate.candidate, itemPresentation)
    }

    Stitch.value(candidatePresentations)
  }
}
