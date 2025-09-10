package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.NaviClientConfigFeature
import com.twitter.home_mixer.model.NaviClientConfig
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring._
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapVideo
import com.twitter.product_mixer.component_library.module.TestUserMapper
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaviVideoClientConfigQueryFeatureHydrator @Inject() (
  testUserMapper: TestUserMapper)
    extends NaviClientConfigQueryFeatureHydrator(testUserMapper) {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("NaviVideoClientConfig")

  override val features: Set[Feature[_, _]] = Set(NaviClientConfigFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    query.params(UseVideoNaviClusterParam) match {
      case true =>
        val config = NaviClientConfig(
          clientName = NaviModelClientHomeRecapVideo,
          customizedBatchSize = None,
          clusterStr = "Video"
        )
        Stitch.value(FeatureMap(NaviClientConfigFeature, config))
      case _ => super.hydrate(query)
    }
  }
}
