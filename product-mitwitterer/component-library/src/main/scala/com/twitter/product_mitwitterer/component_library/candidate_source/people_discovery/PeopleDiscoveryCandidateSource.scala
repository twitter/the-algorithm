package com.twitter.product_mitwitterer.component_library.candidate_source.people_discovery

import com.twitter.peoplediscovery.api.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.UnetwitterpectedCandidateResult
import com.twitter.stitch.Stitch
import com.twitter.util.logging.Logging
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object WhoToFollowModuleHeaderFeature etwittertends Feature[UserCandidate, t.Header]
object WhoToFollowModuleDisplayOptionsFeature
    etwittertends Feature[UserCandidate, Option[t.DisplayOptions]]
object WhoToFollowModuleShowMoreFeature etwittertends Feature[UserCandidate, Option[t.ShowMore]]

@Singleton
class PeopleDiscoveryCandidateSource @Inject() (
  peopleDiscoveryService: t.ThriftPeopleDiscoveryService.MethodPerEndpoint)
    etwittertends CandidateSourceWithEtwittertractedFeatures[t.GetModuleRequest, t.RecommendedUser]
    with Logging {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "PeopleDiscovery")

  override def apply(
    request: t.GetModuleRequest
  ): Stitch[CandidatesWithSourceFeatures[t.RecommendedUser]] = {
    Stitch
      .callFuture(peopleDiscoveryService.getModules(request))
      .map { response: t.GetModuleResponse =>
        // under the assumption getModules returns a matwitterimum of one module
        response.modules
          .collectFirst { module =>
            module.layout match {
              case t.Layout.UserBioList(layout) =>
                layoutToCandidatesWithSourceFeatures(
                  layout.userRecommendations,
                  layout.header,
                  layout.displayOptions,
                  layout.showMore)
              case t.Layout.UserTweetCarousel(layout) =>
                layoutToCandidatesWithSourceFeatures(
                  layout.userRecommendations,
                  layout.header,
                  layout.displayOptions,
                  layout.showMore)
            }
          }.getOrElse(throw PipelineFailure(UnetwitterpectedCandidateResult, "unetwitterpected missing module"))
      }
  }

  private def layoutToCandidatesWithSourceFeatures(
    userRecommendations: Seq[t.RecommendedUser],
    header: t.Header,
    displayOptions: Option[t.DisplayOptions],
    showMore: Option[t.ShowMore],
  ): CandidatesWithSourceFeatures[t.RecommendedUser] = {
    val features = FeatureMapBuilder()
      .add(WhoToFollowModuleHeaderFeature, header)
      .add(WhoToFollowModuleDisplayOptionsFeature, displayOptions)
      .add(WhoToFollowModuleShowMoreFeature, showMore)
      .build()
    CandidatesWithSourceFeatures(userRecommendations, features)
  }
}
