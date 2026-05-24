package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.candidateservice.core_io.MatchingProfile
import com.twitter.home_mixer.model.HomeFeatures.ViewerHasJobRecommendationsEnabled
import com.twitter.home_mixer.model.HomeFeatures.ViewerHasRecruitingOrganizationRecommendationsEnabled
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithDefaultTimeout
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableViewerHasJobRecommendationsFeatureParam
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoErrCategorizer
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.recruiting.api.user.MatchingProfileOnUserClientColumn

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ViewerHasJobRecommendationsFeatureHydrator @Inject() (
  @Named(BatchedStratoClientWithDefaultTimeout) stratoClient: Client)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ViewerHasJobRecommendations")

  override val features: Set[Feature[_, _]] =
    Set(ViewerHasJobRecommendationsEnabled, ViewerHasRecruitingOrganizationRecommendationsEnabled)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableViewerHasJobRecommendationsFeatureParam)

  private val fetcher = stratoClient.fetcher[
    Long,
    Unit,
    MatchingProfile
  ](MatchingProfileOnUserClientColumn.Path)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    fetcher
      .fetch(userId, ())
      .map { result =>
        val (orgsEnabled, jobsEnabled) = result.v match {
          case None =>
            // When no matching profile exists, the user cannot have consented
            // to see job recommendations. Just show org recommendations.
            (true, false)
          case Some(profile) =>
            // When a profile does exist, check for consent. Consent cannot be withdrawn.
            // It indicates that at some point in the past, the user has opted in to see
            // job recommendations. They can later toggle off recommendations. Possible
            // states:
            //
            // 1. User consented and recommendations enabled
            //    (a) there are recommendations --> show job recommendations
            //    (b) there are no recommendations --> show org recommendations
            // 2. User toggled off recommendations --> show nothing
            // 3. User has not consented --> show org recommendations
            if (!profile.recommendationsEnabled) {
              (false, false)
            } else {
              val showJobRecommendations =
                profile.consentedAt.nonEmpty && profile.hasJobRecommendations
              (!showJobRecommendations, showJobRecommendations)
            }
        }

        FeatureMapBuilder()
          .add(ViewerHasRecruitingOrganizationRecommendationsEnabled, orgsEnabled)
          .add(ViewerHasJobRecommendationsEnabled, jobsEnabled)
          .build()
      }
      .rescue(StratoErrCategorizer.CategorizeStratoException)
  }
}
