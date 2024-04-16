package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.B naryCompactScala nject on
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.Long nject on
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl ent
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo ntBu lder
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Component
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Component0
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.KeyDescr ptor
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.strato.generated.cl ent.ml.featureStore.McUserCount ngOnUserCl entColumn
 mport com.tw ter.strato.generated.cl ent.ml.featureStore.onboard ng.T  l nesAuthorFeaturesOnUserCl entColumn
 mport com.tw ter.t  l nes.author_features.v1.thr ftscala.AuthorFeatures
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.onboard ng.relevance.features.thr ftscala.MCUserCount ngFeatures
 mport java.lang.{Long => JLong}
 mport scala.ut l.Random

object Hydrat onS cesModule extends Tw terModule {

  val readFromManhattan = flag(
    "feature_hydrat on_enable_read ng_from_manhattan",
    false,
    "W t r to read t  data from Manhattan or Strato")

  val manhattanApp d =
    flag("frs_readonly.app d", "ml_features_at na", "RO App  d used by t  RO FRS serv ce")
  val manhattanDestNa  = flag(
    "frs_readonly.destNa ",
    "/s/manhattan/at na.nat ve-thr ft",
    "manhattan Dest Na  used by t  RO FRS serv ce")

  @Prov des
  @S ngleton
  def prov desAt naManhattanCl ent(
    serv ce dent f er: Serv ce dent f er
  ): ManhattanKVEndpo nt = {
    val cl ent = ManhattanKVCl ent(
      manhattanApp d(),
      manhattanDestNa (),
      ManhattanKVCl entMtlsParams(serv ce dent f er)
    )
    ManhattanKVEndpo ntBu lder(cl ent)
      .defaultGuarantee(Guarantee. ak)
      .bu ld()
  }

  val manhattanAuthorDataset = "t  l nes_author_features"
  pr vate val defaultCac MaxKeys = 60000
  pr vate val cac TTL = 12.h s
  pr vate val earlyExp rat on = 0.2

  val authorKeyDesc = KeyDescr ptor(Component(Long nject on), Component0)
  val authorDatasetKey = authorKeyDesc.w hDataset(manhattanAuthorDataset)
  val authorValDesc = ValueDescr ptor(B naryCompactScala nject on(AuthorFeatures))

  @Prov des
  @S ngleton
  def t  l nesAuthorSt chCac (
    manhattanReadOnlyEndpo nt: ManhattanKVEndpo nt,
    t  l nesAuthorFeaturesColumn: T  l nesAuthorFeaturesOnUserCl entColumn,
    stats: StatsRece ver
  ): St chCac [JLong, Opt on[AuthorFeatures]] = {

    val st chCac Stats =
      stats
        .scope("d rect_ds_s ce_feature_hydrat on_module").scope("t  l nes_author")

    val stStat = st chCac Stats.counter("readFromStrato-each")
    val mhtStat = st chCac Stats.counter("readFromManhattan-each")

    val t  l nesAuthorUnderly ngCall =  f (readFromManhattan()) {
      st chCac Stats.counter("readFromManhattan"). ncr()
      val authorCac Underly ngManhattanCall: JLong => St ch[Opt on[AuthorFeatures]] =  d => {
        mhtStat. ncr()
        val key = authorDatasetKey.w hPkey( d)
        manhattanReadOnlyEndpo nt
          .get(key = key, valueDesc = authorValDesc).map(_.map(value =>
            clearUnsedF eldsForAuthorFeature(value.contents)))
      }
      authorCac Underly ngManhattanCall
    } else {
      st chCac Stats.counter("readFromStrato"). ncr()
      val authorCac Underly ngStratoCall: JLong => St ch[Opt on[AuthorFeatures]] =  d => {
        stStat. ncr()
        val t  l nesAuthorFeaturesFetc r = t  l nesAuthorFeaturesColumn.fetc r
        t  l nesAuthorFeaturesFetc r
          .fetch( d).map(result => result.v.map(clearUnsedF eldsForAuthorFeature))
      }
      authorCac Underly ngStratoCall
    }

    St chCac [JLong, Opt on[AuthorFeatures]](
      underly ngCall = t  l nesAuthorUnderly ngCall,
      maxCac S ze = defaultCac MaxKeys,
      ttl = random zedTTL(cac TTL. nSeconds).seconds,
      statsRece ver = st chCac Stats
    )

  }

  // Not add ng manhattan s nce   d dn't seem useful for Author Data,   can add  n anot r phab
  //  f dee d  lpful
  @Prov des
  @S ngleton
  def  tr cCenterUserCount ngSt chCac (
    mcUserCount ngFeaturesColumn: McUserCount ngOnUserCl entColumn,
    stats: StatsRece ver
  ): St chCac [JLong, Opt on[MCUserCount ngFeatures]] = {

    val st chCac Stats =
      stats
        .scope("d rect_ds_s ce_feature_hydrat on_module").scope("mc_user_count ng")

    val stStat = st chCac Stats.counter("readFromStrato-each")
    st chCac Stats.counter("readFromStrato"). ncr()

    val mcUserCount ngCac Underly ngCall: JLong => St ch[Opt on[MCUserCount ngFeatures]] =  d => {
      stStat. ncr()
      val mcUserCount ngFeaturesFetc r = mcUserCount ngFeaturesColumn.fetc r
      mcUserCount ngFeaturesFetc r.fetch( d).map(_.v)
    }

    St chCac [JLong, Opt on[MCUserCount ngFeatures]](
      underly ngCall = mcUserCount ngCac Underly ngCall,
      maxCac S ze = defaultCac MaxKeys,
      ttl = random zedTTL(cac TTL. nSeconds).seconds,
      statsRece ver = st chCac Stats
    )

  }

  // clear out f elds   don't need to save cac  space
  pr vate def clearUnsedF eldsForAuthorFeature(entry: AuthorFeatures): AuthorFeatures = {
    entry.unsetUserTop cs.unsetUser alth.unsetAuthorCountryCodeAggregates.unsetOr g nalAuthorCountryCodeAggregates
  }

  // To avo d a cac  stampede. See https://en.w k ped a.org/w k /Cac _stampede
  pr vate def random zedTTL(ttl: Long): Long = {
    (ttl - ttl * earlyExp rat on * Random.nextDouble()).toLong
  }
}
