package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.ml.featurestore.lib.dataset.DatasetParams
import com.twitter.ml.featurestore.lib.dynamic.BaseDynamicHydrationConfig
import com.twitter.ml.featurestore.lib.dynamic.BaseGatedFeatures
import com.twitter.ml.featurestore.lib.dynamic.ClientConfig
import com.twitter.ml.featurestore.lib.dynamic.DynamicFeatureStoreClient
import com.twitter.ml.featurestore.lib.dynamic.FeatureStoreParamsConfig
import com.twitter.ml.featurestore.lib.params.FeatureStoreParams
import com.twitter.product_mixer.core.functional_component.feature_hydrator.featurestorev1.FeatureStoreV1DynamicClientBuilder
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.strato.opcontext.Attribution.ManhattanAppId
import javax.inject.Singleton

object SampleFeatureStoreV1DynamicClientBuilderModule extends TwitterModule {

  @Provides
  @Singleton
  def provideFeatureStoreV1DynamicClientBuilder(
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier
  ): FeatureStoreV1DynamicClientBuilder = {
    val defaultFeatureStoreParams = FeatureStoreParams(global = DatasetParams(
      attributions = Seq(ManhattanAppId("athena", "explore_mixer_features_athena")),
      logHydrationErrors = true))

    new FeatureStoreV1DynamicClientBuilder {
      override def build[Query <: PipelineQuery](
        dynamicHydrationConfig: BaseDynamicHydrationConfig[Query, _ <: BaseGatedFeatures[Query]]
      ): DynamicFeatureStoreClient[Query] =
        DynamicFeatureStoreClient(
          clientConfig = ClientConfig(
            dynamicHydrationConfig = dynamicHydrationConfig,
            featureStoreParamsConfig =
              FeatureStoreParamsConfig(defaultFeatureStoreParams = defaultFeatureStoreParams),
            timeoutProvider = _ => 350.milliseconds,
            timeoutPerRequestProvider = None,
            stratoMaxBatchSizeProvider = None,
            serviceIdentifier = serviceIdentifier
          ),
          statsReceiver = statsReceiver
        )
    }
  }
}
