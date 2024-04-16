package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.tw ter.d scovery.common.conf gap .FeatureContextBu lder
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object FeatureContextBu lderModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desFeatureContextBu lder(featureSw c s: FeatureSw c s): FeatureContextBu lder = {
    FeatureContextBu lder(featureSw c s)
  }
}
