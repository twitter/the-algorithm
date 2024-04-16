package com.tw ter.representat onscorer.tw stlyfeatures

 mport com.g hub.benmanes.caffe ne.cac .Caffe ne
 mport com.tw ter.st ch.cac .Ev ct ngCac 
 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.representat onscorer.common.Representat onScorerDec der
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.cac .ConcurrentMapCac 
 mport com.tw ter.st ch.cac . mo zeQuery
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.generated.cl ent.recom ndat ons.user_s gnal_serv ce.S gnalsCl entColumn
 mport java.ut l.concurrent.ConcurrentMap
 mport java.ut l.concurrent.T  Un 
 mport javax. nject.S ngleton

object UserS gnalServ ceRecentEngage ntsCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov de(
    cl ent: Cl ent,
    dec der: Representat onScorerDec der,
    statsRece ver: StatsRece ver
  ): Long => St ch[Engage nts] = {
    val stratoCl ent = new S gnalsCl entColumn(cl ent)

    /*
    T  cac  holds a users recent engage nts for a short per od of t  , such that batc d requests
    for mult ple (user d, t et d) pa rs don't all need to fetch t m.

    [1] Caffe ne cac  keys/values must be objects, so   cannot use t  `Long` pr m  ve d rectly.
    T  boxed java.lang.Long works as a key, s nce    s an object.  n most s uat ons t  comp ler
    can see w re auto(un)box ng can occur. Ho ver,  re   seem to need so  wrapper funct ons
    w h expl c  types to allow t  box ng to happen.
     */
    val mapCac : ConcurrentMap[java.lang.Long, St ch[Engage nts]] =
      Caffe ne
        .newBu lder()
        .exp reAfterWr e(5, T  Un .SECONDS)
        .max mumS ze(
          1000 //   est mate 5M un que users  n a 5m per od - w h 2k RSX  nstances, assu  that one w ll see < 1k  n a 5s per od
        )
        .bu ld[java.lang.Long, St ch[Engage nts]]
        .asMap

    statsRece ver.prov deGauge("ussRecentEngage ntsCl ent", "cac _s ze") { mapCac .s ze.toFloat }

    val engage ntsCl ent =
      new UserS gnalServ ceRecentEngage ntsCl ent(stratoCl ent, dec der, statsRece ver)

    val f = (l: java.lang.Long) => engage ntsCl ent.get(l) // See note [1] above
    val cac dCall =  mo zeQuery(f, Ev ct ngCac .laz ly(new ConcurrentMapCac (mapCac )))
    (l: Long) => cac dCall(l) // see note [1] above
  }
}
