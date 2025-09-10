package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.NaviClientConfigFeature
import com.twitter.home_mixer.model.NaviClientConfig
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelIdParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NaviGPUBatchSizeParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ProdModelIdParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseGPUNaviClusterParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseGPUNaviClusterTestUsersParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseRealtimeNaviClusterParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseSecondaryNaviClusterParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecap
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapGPU
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapRealtime
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapSecondary
import com.twitter.product_mixer.component_library.module.TestUserMapper
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaviClientConfigQueryFeatureHydrator @Inject() (
  testUserMapper: TestUserMapper)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("NaviClientConfig")

  override val features: Set[Feature[_, _]] = Set(NaviClientConfigFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    // Use experimental realtime cluster
    val useRealtimeCluster = query.params(UseRealtimeNaviClusterParam)
    // Use secondary navi cluster for handling peak traffic
    val useSecondaryCluster = query.params(UseSecondaryNaviClusterParam)

    val modelId = query.params(ModelIdParam)
    val prodModelId = query.params(ProdModelIdParam)
    val isTestUser = testUserMapper.isTestUser(query.clientContext)
    val useGPUCluster = prodModelId == modelId && (query.params(UseGPUNaviClusterParam) ||
      (isTestUser && query.params(UseGPUNaviClusterTestUsersParam)))

    val gpuBatchSize =
      if (useGPUCluster) Some(query.params(NaviGPUBatchSizeParam).toInt)
      else None

    val clusterClientPriorityList = Seq(
      (useGPUCluster, NaviModelClientHomeRecapGPU),
      (useRealtimeCluster, NaviModelClientHomeRecapRealtime),
      (useSecondaryCluster, NaviModelClientHomeRecapSecondary)
    )

    val clientName = clusterClientPriorityList
      .collectFirst {
        case (true, name) => name
      }.getOrElse(NaviModelClientHomeRecap)

    val clusterStr = clientName match {
      case NaviModelClientHomeRecapGPU => "GPU"
      case NaviModelClientHomeRecapRealtime => "Realtime"
      case _ => ""
    }

    val naviConfig = NaviClientConfig(clientName, gpuBatchSize, clusterStr)
    Stitch.value(FeatureMap(NaviClientConfigFeature, naviConfig))
  }
}
