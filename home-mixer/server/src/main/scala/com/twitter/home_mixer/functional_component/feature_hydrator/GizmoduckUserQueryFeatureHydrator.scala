package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.model.HomeFeatures.SignupCountryFeature
import com.twitter.home_mixer.model.HomeFeatures.SignupSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.UserFollowersCountFeature
import com.twitter.home_mixer.model.HomeFeatures.UserFollowingCountFeature
import com.twitter.home_mixer.model.HomeFeatures.UserScreenNameFeature
import com.twitter.home_mixer.model.HomeFeatures.UserTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerAllowsAdsPersonalizationFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerAllowsDataSharingFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerAllowsForYouRecommendationsFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerHasPremiumTier
import com.twitter.home_mixer.model.HomeFeatures.ViewerSafetyLabels
import com.twitter.home_mixer.model.signup.MarchMadness
import com.twitter.home_mixer.model.signup.Onboard
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.gizmoduck.Gizmoduck

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class GizmoduckUserQueryFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GizmoduckUser")

  override val features: Set[Feature[_, _]] = Set(
    UserFollowingCountFeature,
    UserFollowersCountFeature,
    UserTypeFeature,
    UserScreenNameFeature,
    ViewerHasPremiumTier,
    SignupCountryFeature,
    SignupSourceFeature,
    ViewerAllowsForYouRecommendationsFeature,
    ViewerAllowsDataSharingFeature,
    ViewerAllowsAdsPersonalizationFeature,
    ViewerSafetyLabels
  )

  private val queryFields: Set[gt.QueryFields] = Set(
    gt.QueryFields.Counts,
    gt.QueryFields.Safety,
    gt.QueryFields.Profile,
    gt.QueryFields.Account,
    gt.QueryFields.Labels
  )

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    gizmoduck
      .getUserById(
        userId = userId,
        queryFields = queryFields,
        context = gt.LookupContext(forUserId = Some(userId), includeSoftUsers = true))
      .map { user =>
        val premiumTier = user.safety
          .map { safety =>
            safety.skipRateLimit.contains(true) ||
            safety.isBlueVerified.contains(true) ||
            safety.verifiedType.contains(gt.VerifiedType.Business) ||
            safety.verifiedType.contains(gt.VerifiedType.Government) ||
            safety.verifiedOrganizationDetails.exists(_.isVerifiedOrganization.getOrElse(false)) ||
            safety.verifiedOrganizationDetails
              .exists(_.isVerifiedOrganizationAffiliate.contains(true))
          }.getOrElse(false)

        val signupSource = user.safety.flatMap(_.signupCreationSource).flatMap {
          case gt.SignupCreationSource.MarchMadness => Some(MarchMadness)
          case gt.SignupCreationSource.Onboard => Some(Onboard)
          case _ => None
        }

        FeatureMapBuilder()
          .add(UserFollowingCountFeature, user.counts.map(_.following.toInt))
          .add(UserFollowersCountFeature, user.counts.map(_.followers.toInt))
          .add(UserTypeFeature, Some(user.userType))
          .add(UserScreenNameFeature, user.profile.map(_.screenName))
          .add(ViewerHasPremiumTier, premiumTier)
          .add(SignupCountryFeature, user.safety.flatMap(_.signupCountryCode))
          .add(SignupSourceFeature, signupSource)
          .add(
            ViewerAllowsForYouRecommendationsFeature,
            user.account.flatMap(_.allowForYouRecommendations)
          )
          .add(
            ViewerAllowsDataSharingFeature,
            user.account.map(_.allowSharingDataForThirdPartyPersonalization)
          ).add(
            ViewerAllowsAdsPersonalizationFeature,
            user.account.map(_.allowAdsPersonalization)
          ).add(
            ViewerSafetyLabels,
            user.labels.map(_.labels.map(_.labelValue.name))
          )
          .build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}
