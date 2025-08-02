package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.inject.TwitterModule
import com.twitter.simclustersann.thriftscala.SimClustersANNService
import javatwitter.inject.Named

object SimClustersANNServiceNameToClientMapper etwittertends TwitterModule {

  @Provides
  @Singleton
  def providesSimClustersANNServiceNameToClientMapping(
    @Named(ModuleNames.ProdSimClustersANNServiceClientName) simClustersANNServiceProd: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.EtwitterperimentalSimClustersANNServiceClientName) simClustersANNServiceEtwitterperimental: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersANNServiceClientName1) simClustersANNService1: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersANNServiceClientName2) simClustersANNService2: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersANNServiceClientName3) simClustersANNService3: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersANNServiceClientName5) simClustersANNService5: SimClustersANNService.MethodPerEndpoint,
    @Named(ModuleNames.SimClustersANNServiceClientName4) simClustersANNService4: SimClustersANNService.MethodPerEndpoint
  ): Map[String, SimClustersANNService.MethodPerEndpoint] = {
    Map[String, SimClustersANNService.MethodPerEndpoint](
      "simclusters-ann" -> simClustersANNServiceProd,
      "simclusters-ann-etwitterperimental" -> simClustersANNServiceEtwitterperimental,
      "simclusters-ann-1" -> simClustersANNService1,
      "simclusters-ann-2" -> simClustersANNService2,
      "simclusters-ann-3" -> simClustersANNService3,
      "simclusters-ann-5" -> simClustersANNService5,
      "simclusters-ann-4" -> simClustersANNService4
    )
  }
}
