package com.ExTwitter.home_mixer.product.list_recommended_users.candidate_source

import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.search.adaptive.adaptive_results.thriftscala.AdaptiveSearchResultData
import com.ExTwitter.search.adaptive.adaptive_results.thriftscala.Result
import com.ExTwitter.search.adaptive.adaptive_results.thriftscala.ResultData
import com.ExTwitter.search.blender.adaptive_search.thriftscala.AdaptiveSearchResponse
import com.ExTwitter.search.blender.adaptive_search.thriftscala.Container
import com.ExTwitter.search.blender.thriftscala.BlenderService
import com.ExTwitter.search.blender.thriftscala.ThriftBlenderRequest
import com.ExTwitter.stitch.Stitch
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
