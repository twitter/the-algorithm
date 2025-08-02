package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mitwitterer.model.HomeFeatures.UserFollowingCountFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.UserScreenNameFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.UserTypeFeature
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.gizmoduck.Gizmoduck
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class GizmoduckUserQueryFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

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
        contetwittert = gt.LookupContetwittert(forUserId = Some(userId), includeSoftUsers = true))
      .map { user =>
        FeatureMapBuilder()
          .add(UserFollowingCountFeature, user.counts.map(_.following.toInt))
          .add(UserTypeFeature, Some(user.userType))
          .add(UserScreenNameFeature, user.profile.map(_.screenName))
          .build()
      }
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}
