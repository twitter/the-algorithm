package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.unified_counter.service.UuaUserIdentifierClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnifiedUserActionsUserIdentifierFeatureHydrator @Inject() (
  uuaUserIdentifierClientColumn: UuaUserIdentifierClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UnifiedUserActionsUserIdentifier")

  override val features: Set[Feature[_, _]] = Set(
    UuaUserGenderFeature,
    UuaUserStateFeature,
    UuaUserAgeBucketFeature
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(UuaUserGenderFeature, None)
    .add(UuaUserStateFeature, None)
    .add(UuaUserAgeBucketFeature, None)
    .build()

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    uuaUserIdentifierClientColumn.fetcher
      .fetch(query.getRequiredUserId.toString)
      .map { response =>
        response.v match {
          case Some(userInfo) =>
            val gender = userInfo.userGender.map(_.toString)
            val state = userInfo.userState.map(_.value.toLong)
            val ageBucket = userInfo.userAgeBucket.map(_.toString)

            FeatureMapBuilder()
              .add(UuaUserGenderFeature, gender)
              .add(UuaUserStateFeature, state)
              .add(UuaUserAgeBucketFeature, ageBucket)
              .build()

          case _ =>
            DefaultFeatureMap
        }
      }
      .rescue {
        case _: Throwable =>
          Stitch.value(DefaultFeatureMap)
      }
  }
}
