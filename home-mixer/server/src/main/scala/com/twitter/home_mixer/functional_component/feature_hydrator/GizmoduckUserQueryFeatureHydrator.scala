package com.ExTwitter.home_mixer.functional_component.feature_hydrator

import com.ExTwitter.gizmoduck.{thriftscala => gt}
import com.ExTwitter.home_mixer.model.HomeFeatures.UserFollowingCountFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.UserScreenNameFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.UserTypeFeature
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.gizmoduck.Gizmoduck
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class GizmoduckUserQueryFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GizmoduckUser")

  override val features: Set[Feature[_, _]] =
    Set(UserFollowingCountFeature, UserTypeFeature, UserScreenNameFeature)

  private val queryFields: Set[gt.QueryFields] =
    Set(gt.QueryFields.Counts, gt.QueryFields.Safety, gt.QueryFields.Profile)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    gizmoduck
      .getUserById(
        userId = userId,
        queryFields = queryFields,
        context = gt.LookupContext(forUserId = Some(userId), includeSoftUsers = true))
      .map { user =>
        FeatureMapBuilder()
          .add(UserFollowingCountFeature, user.counts.map(_.following.toInt))
          .add(UserTypeFeature, Some(user.userType))
          .add(UserScreenNameFeature, user.profile.map(_.screenName))
          .build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}
