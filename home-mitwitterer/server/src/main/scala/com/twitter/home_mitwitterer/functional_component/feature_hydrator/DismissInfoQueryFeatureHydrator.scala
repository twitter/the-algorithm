package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.timelinemitwitterer.clients.manhattan.InjectionHistoryClient
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemitwitterer.clients.manhattan.DismissInfo
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object DismissInfoQueryFeatureHydrator {
  val DismissInfoSuggestTypes = Seq(SuggestType.WhoToFollow)
}

@Singleton
case class DismissInfoQueryFeatureHydrator @Inject() (
  dismissInfoClient: InjectionHistoryClient)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8, 50, 60, 60)
  )
}
