package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.inject.TwitterModule
import com.twitter.simclustersann.thriftscala.SimClustersANNService
import com.twitter.tweet_mixer.model.ModuleNames
import javax.inject.Named

object SimClustersANNServiceNameToClientMapper extends TwitterModule {

  @Provides
  @Singleton
  def providesSimClustersANNServiceNameToClientMapping(
    @Named(ModuleNames.ProdSimClustersANNServiceClientName) simClustersANNServiceProd: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersVideoANNServiceClientName) simClustersVideoANNServiceProd: SimClustersANNService.MethodPerEndpoint
  ): Map[String, SimClustersANNService.MethodPerEndpoint] = {
    Map[String, SimClustersANNService.MethodPerEndpoint](
      "simclusters-ann" -> simClustersANNServiceProd,
      "simclusters-ann-experimental" -> simClustersVideoANNServiceProd
    )
  }
}
