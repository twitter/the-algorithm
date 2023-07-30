package com.X.home_mixer.functional_component.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.timelinemixer.clients.manhattan.InjectionHistoryClient
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelinemixer.clients.manhattan.DismissInfo
import com.X.timelineservice.suggests.thriftscala.SuggestType
import javax.inject.Inject
import javax.inject.Singleton

object DismissInfoQueryFeatureHydrator {
  val DismissInfoSuggestTypes = Seq(SuggestType.WhoToFollow)
}

@Singleton
case class DismissInfoQueryFeatureHydrator @Inject() (
  dismissInfoClient: InjectionHistoryClient)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("DismissInfo")

  override val features: Set[Feature[_, _]] = Set(DismissInfoFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] =
    Stitch.callFuture {
      dismissInfoClient
        .readDismissInfoEntries(
          query.getRequiredUserId,
          DismissInfoQueryFeatureHydrator.DismissInfoSuggestTypes).map { response =>
          val dismissInfoMap = response.mapValues(DismissInfo.fromThrift)
          FeatureMapBuilder().add(DismissInfoFeature, dismissInfoMap).build()
        }
    }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8, 50, 60, 60)
  )
}
