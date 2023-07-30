package com.X.product_mixer.component_library.candidate_source.people_discovery

import com.X.peoplediscovery.api.{thriftscala => t}
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSourceWithExtractedFeatures
import com.X.product_mixer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.product_mixer.core.pipeline.pipeline_failure.UnexpectedCandidateResult
import com.X.stitch.Stitch
import com.X.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

object WhoToFollowModuleHeaderFeature extends Feature[UserCandidate, t.Header]
object WhoToFollowModuleDisplayOptionsFeature
    extends Feature[UserCandidate, Option[t.DisplayOptions]]
object WhoToFollowModuleShowMoreFeature extends Feature[UserCandidate, Option[t.ShowMore]]

@Singleton
class PeopleDiscoveryCandidateSource @Inject() (
  peopleDiscoveryService: t.ThriftPeopleDiscoveryService.MethodPerEndpoint)
    extends CandidateSourceWithExtractedFeatures[t.GetModuleRequest, t.RecommendedUser]
    with Logging {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "PeopleDiscovery")

  override def apply(
    request: t.GetModuleRequest
  ): Stitch[CandidatesWithSourceFeatures[t.RecommendedUser]] = {
    Stitch
      .callFuture(peopleDiscoveryService.getModules(request))
      .map { response: t.GetModuleResponse =>
        // under the assumption getModules returns a maximum of one module
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
          }.getOrElse(throw PipelineFailure(UnexpectedCandidateResult, "unexpected missing module"))
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
