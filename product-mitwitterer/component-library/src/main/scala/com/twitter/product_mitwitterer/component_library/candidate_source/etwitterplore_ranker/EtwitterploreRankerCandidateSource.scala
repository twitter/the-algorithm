package com.twitter.product_mitwitterer.component_library.candidate_source.etwitterplore_ranker

import com.twitter.etwitterplore_ranker.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class EtwitterploreRankerCandidateSource @Inject() (
  etwitterploreRankerService: t.EtwitterploreRanker.MethodPerEndpoint)
    etwittertends CandidateSource[t.EtwitterploreRankerRequest, t.ImmersiveRecsResult] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("EtwitterploreRanker")

  override def apply(
    request: t.EtwitterploreRankerRequest
  ): Stitch[Seq[t.ImmersiveRecsResult]] = {
    Stitch
      .callFuture(etwitterploreRankerService.getRankedResults(request))
      .map {
        case t.EtwitterploreRankerResponse(
              t.EtwitterploreRankerProductResponse
                .ImmersiveRecsResponse(t.ImmersiveRecsResponse(immersiveRecsResults))) =>
          immersiveRecsResults
        case response =>
          throw new UnsupportedOperationEtwitterception(s"Unknown response type: $response")
      }
  }
}
