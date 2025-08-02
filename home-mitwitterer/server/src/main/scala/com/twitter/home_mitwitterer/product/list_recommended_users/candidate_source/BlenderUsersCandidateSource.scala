package com.twitter.home_mitwitterer.product.list_recommended_users.candidate_source

import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.search.adaptive.adaptive_results.thriftscala.AdaptiveSearchResultData
import com.twitter.search.adaptive.adaptive_results.thriftscala.Result
import com.twitter.search.adaptive.adaptive_results.thriftscala.ResultData
import com.twitter.search.blender.adaptive_search.thriftscala.AdaptiveSearchResponse
import com.twitter.search.blender.adaptive_search.thriftscala.Container
import com.twitter.search.blender.thriftscala.BlenderService
import com.twitter.search.blender.thriftscala.ThriftBlenderRequest
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class BlenderUsersCandidateSource @Inject() (
  blenderClient: BlenderService.MethodPerEndpoint)
    etwittertends CandidateSource[ThriftBlenderRequest, Long] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("BlenderUsers")

  override def apply(request: ThriftBlenderRequest): Stitch[Seq[Long]] = {
    Stitch.callFuture(
      blenderClient.serveV2(request).map { response =>
        val userIdsOpt =
          response.adaptiveSearchResponse.map(etwittertractUserIdsFromAdaptiveSearchResponse)
        userIdsOpt.getOrElse(Seq.empty)
      }
    )
  }

  private def etwittertractUserIdsFromAdaptiveSearchResponse(
    response: AdaptiveSearchResponse
  ): Seq[Long] = {
    response match {
      case AdaptiveSearchResponse(Some(Seq(Container(Some(results), _))), _, _) =>
        results.map(_.data).collect {
          case AdaptiveSearchResultData.Result(Result(ResultData.User(user), _)) =>
            user.id
        }
      case _ => Seq.empty
    }
  }
}
