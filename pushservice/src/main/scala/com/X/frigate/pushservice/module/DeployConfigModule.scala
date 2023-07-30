package com.X.frigate.pushservice.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.abdecider.LoggingABDecider
import com.X.decider.Decider
import com.X.featureswitches.v2.FeatureSwitches
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.tunable.StandardTunableMap
import com.X.frigate.pushservice.config.DeployConfig
import com.X.frigate.pushservice.config.ProdConfig
import com.X.frigate.pushservice.config.StagingConfig
import com.X.frigate.pushservice.params.ShardParams
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ConfigRepoLocalPath
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal

object DeployConfigModule extends XModule {

  @Provides
  @Singleton
  def providesDeployConfig(
    @Flag(FlagName.numShards) numShards: Int,
    @Flag(FlagName.shardId) shardId: Int,
    @Flag(FlagName.isInMemCacheOff) inMemCacheOff: Boolean,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    @Flag(ConfigRepoLocalPath) localConfigRepoPath: String,
    serviceIdentifier: ServiceIdentifier,
    decider: Decider,
    abDecider: LoggingABDecider,
    featureSwitches: FeatureSwitches,
    statsReceiver: StatsReceiver
  ): DeployConfig = {
    val tunableMap = if (serviceIdentifier.service.contains("canary")) {
      StandardTunableMap(id = "frigate-pushservice-canary")
    } else { StandardTunableMap(id = serviceIdentifier.service) }
    val shardParams = ShardParams(numShards, shardId)
    serviceIdentifier.environment match {
      case "devel" | "staging" =>
        StagingConfig(
          isServiceLocal = isServiceLocal,
          localConfigRepoPath = localConfigRepoPath,
          inMemCacheOff = inMemCacheOff,
          decider = decider,
          abDecider = abDecider,
          featureSwitches = featureSwitches,
          serviceIdentifier = serviceIdentifier,
          tunableMap = tunableMap,
          shardParams = shardParams
        )(statsReceiver)
      case "prod" =>
        ProdConfig(
          isServiceLocal = isServiceLocal,
          localConfigRepoPath = localConfigRepoPath,
          inMemCacheOff = inMemCacheOff,
          decider = decider,
          abDecider = abDecider,
          featureSwitches = featureSwitches,
          serviceIdentifier = serviceIdentifier,
          tunableMap = tunableMap,
          shardParams = shardParams
        )(statsReceiver)
      case env => throw new Exception(s"Unknown environment $env")
    }
  }
}
