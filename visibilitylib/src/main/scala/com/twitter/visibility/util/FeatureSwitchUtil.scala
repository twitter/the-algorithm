package com.tw ter.v s b l y.ut l

 mport com.tw ter.abdec der.ABDec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.v2.bu lder.FeatureSw c sBu lder
 mport com.tw ter.f nagle.stats.StatsRece ver

object FeatureSw chUt l {
  pr vate val L braryFeaturesConf gPath = "/features/v s b l y/ma n"
  pr vate val L m edAct onsFeaturesConf gPath = "/features/v s b l y-l m ed-act ons/ma n"

  def mkV s b l yL braryFeatureSw c s(
    abDec der: ABDec der,
    statsRece ver: StatsRece ver
  ): FeatureSw c s =
    FeatureSw c sBu lder
      .createDefault(L braryFeaturesConf gPath, abDec der, So (statsRece ver)).bu ld()

  def mkL m edAct onsFeatureSw c s(statsRece ver: StatsRece ver): FeatureSw c s =
    FeatureSw c sBu lder
      .createW hNoExper  nts(L m edAct onsFeaturesConf gPath, So (statsRece ver)).bu ld()
}
