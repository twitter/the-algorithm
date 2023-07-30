package com.X.home_mixer.product.list_recommended_users.candidate_source

import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.search.adaptive.adaptive_results.thriftscala.AdaptiveSearchResultData
import com.X.search.adaptive.adaptive_results.thriftscala.Result
import com.X.search.adaptive.adaptive_results.thriftscala.ResultData
import com.X.search.blender.adaptive_search.thriftscala.AdaptiveSearchResponse
import com.X.search.blender.adaptive_search.thriftscala.Container
import com.X.search.blender.thriftscala.BlenderService
import com.X.search.blender.thriftscala.ThriftBlenderRequest
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlenderUsersCandidateSource @Inject() (
  blenderClient: BlenderService.MethodPerEndpoint)
    extends CandidateSource[ThriftBlenderRequest, Long] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("BlenderUsers")

  override def apply(request: ThriftBlenderRequest): Stitch[Seq[Long]] = {
    Stitch.callFuture(
      blenderClient.serveV2(request).map { response =>
        val userIdsOpt =
          response.adaptiveSearchResponse.map(extractUserIdsFromAdaptiveSearchResponse)
        userIdsOpt.getOrElse(Seq.empty)
      }
    )
  }

  private def extractUserIdsFromAdaptiveSearchResponse(
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
