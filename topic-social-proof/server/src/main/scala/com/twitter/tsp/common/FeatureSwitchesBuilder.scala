package com.tw ter.tsp.common

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.v2.bu lder.{FeatureSw c sBu lder => FsBu lder}
 mport com.tw ter.featuresw c s.v2.exper  ntat on.NullBucket mpressor
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Durat on

case class FeatureSw c sBu lder(
  statsRece ver: StatsRece ver,
  abDec der: Logg ngABDec der,
  featuresD rectory: Str ng,
  addServ ceDeta lsFromAurora: Boolean,
  conf gRepoD rectory: Str ng = "/usr/local/conf g",
  fastRefresh: Boolean = false,
   mpressExper  nts: Boolean = true) {

  def bu ld(): FeatureSw c s = {
    val featureSw c s = FsBu lder()
      .abDec der(abDec der)
      .statsRece ver(statsRece ver)
      .conf gRepoAbsPath(conf gRepoD rectory)
      .featuresD rectory(featuresD rectory)
      .l m ToReferencedExper  nts(shouldL m  = true)
      .exper  nt mpress onStatsEnabled(true)

     f (! mpressExper  nts) featureSw c s.exper  ntBucket mpressor(NullBucket mpressor)
     f (addServ ceDeta lsFromAurora) featureSw c s.serv ceDeta lsFromAurora()
     f (fastRefresh) featureSw c s.refreshPer od(Durat on.fromSeconds(10))

    featureSw c s.bu ld()
  }
}
