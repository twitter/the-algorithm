package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.S ngleton
 mport com.tw ter.s mclustersann.cand date_s ce.Approx mateCos neS m lar y
 mport com.tw ter.s mclustersann.cand date_s ce.Exper  ntalApprox mateCos neS m lar y
 mport com.tw ter.s mclustersann.cand date_s ce.Opt m zedApprox mateCos neS m lar y
 mport com.tw ter.s mclustersann.cand date_s ce.S mClustersANNCand dateS ce

object S mClustersANNCand dateS ceModule extends Tw terModule {

  val acsFlag = flag[Str ng](
    na  = "approx mate_cos ne_s m lar y",
    default = "or g nal",
     lp =
      "Select d fferent  mple ntat ons of t  approx mate cos ne s m lar y algor hm, for test ng opt m zat ons",
  )
  @S ngleton
  @Prov des
  def prov des(
    embedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng],
    cac dClusterT et ndexStore: ReadableStore[Cluster d, Seq[(T et d, Double)]],
    statsRece ver: StatsRece ver
  ): S mClustersANNCand dateS ce = {

    val approx mateCos neS m lar y = acsFlag() match {
      case "or g nal" => Approx mateCos neS m lar y
      case "opt m zed" => Opt m zedApprox mateCos neS m lar y
      case "exper  ntal" => Exper  ntalApprox mateCos neS m lar y
      case _ => Approx mateCos neS m lar y
    }

    new S mClustersANNCand dateS ce(
      approx mateCos neS m lar y = approx mateCos neS m lar y,
      clusterT etCand datesStore = cac dClusterT et ndexStore,
      s mClustersEmbedd ngStore = embedd ngStore,
      statsRece ver = statsRece ver.scope("s mClustersANNCand dateS ce")
    )
  }
}
