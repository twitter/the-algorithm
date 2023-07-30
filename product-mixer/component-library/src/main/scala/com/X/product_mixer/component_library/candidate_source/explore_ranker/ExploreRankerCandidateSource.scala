package com.X.product_mixer.component_library.candidate_source.explore_ranker

import com.X.explore_ranker.{thriftscala => t}
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreRankerCandidateSource @Inject() (
  exploreRankerService: t.ExploreRanker.MethodPerEndpoint)
    extends CandidateSource[t.ExploreRankerRequest, t.ImmersiveRecsResult] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("ExploreRanker")

  override def apply(
    request: t.ExploreRankerRequest
  ): Stitch[Seq[t.ImmersiveRecsResult]] = {
    Stitch
      .callFuture(exploreRankerService.getRankedResults(request))
      .map {
        case t.ExploreRankerResponse(
              t.ExploreRankerProductResponse
                .ImmersiveRecsResponse(t.ImmersiveRecsResponse(immersiveRecsResults))) =>
          immersiveRecsResults
        case response =>
          throw new UnsupportedOperationException(s"Unknown response type: $response")
      }
  }
}
