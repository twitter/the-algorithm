package com.tw ter.recos njector.conf g

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.cl ent.Cl entReg stry
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.T etCreat onT  MHStore
 mport com.tw ter.fr gate.common.ut l.F nagle._
 mport com.tw ter.fr gate.common.ut l.{Url nfo, Url nfo nject on, UrlResolver}
 mport com.tw ter.g zmoduck.thr ftscala.{LookupContext, QueryF elds, User, UserServ ce}
 mport com.tw ter. rm .store.common.{ObservedCac dReadableStore, Observed mcac dReadableStore}
 mport com.tw ter. rm .store.g zmoduck.G zmoduckUserStore
 mport com.tw ter. rm .store.t etyp e.T etyP eStore
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.p nk_floyd.thr ftscala.{Cl ent dent f er, Storer}
 mport com.tw ter.soc algraph.thr ftscala.{ dsRequest, Soc alGraphServ ce}
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport com.tw ter.st ch.storehaus.ReadableStoreOfSt ch
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storage.cl ent.manhattan.kv.{
  ManhattanKVCl ent,
  ManhattanKVCl entMtlsParams,
  ManhattanKVEndpo ntBu lder
}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t etyp e.thr ftscala.{GetT etOpt ons, T etServ ce}
 mport com.tw ter.ut l.Future

/*
 * Any f nagle cl ents should not be def ned as lazy.  f def ned lazy,
 * Cl entReg stry.expAllReg steredCl entsResolved() call  n  n  w ll not ensure that t  cl ents
 * are act ve before thr ft endpo nt  s act ve.   want t  cl ents to be act ve, because zookeeper
 * resolut on tr ggered by f rst request(s) m ght result  n t  request(s) fa l ng.
 */
tra  DeployConf g extends Conf g w h Cac Conf g {
   mpl c  def statsRece ver: StatsRece ver

  def log: Logger

  // Cl ents
  val g zmoduckCl ent = new UserServ ce.F nagledCl ent(
    readOnlyThr ftServ ce(
      "g zmoduck",
      "/s/g zmoduck/g zmoduck",
      statsRece ver,
      recos njectorThr ftCl ent d,
      requestT  out = 450.m ll seconds,
      mTLSServ ce dent f er = So (serv ce dent f er)
    )
  )
  val t etyP eCl ent = new T etServ ce.F nagledCl ent(
    readOnlyThr ftServ ce(
      "t etyp e",
      "/s/t etyp e/t etyp e",
      statsRece ver,
      recos njectorThr ftCl ent d,
      requestT  out = 450.m ll seconds,
      mTLSServ ce dent f er = So (serv ce dent f er)
    )
  )

  val sgsCl ent = new Soc alGraphServ ce.F nagledCl ent(
    readOnlyThr ftServ ce(
      "soc algraph",
      "/s/soc algraph/soc algraph",
      statsRece ver,
      recos njectorThr ftCl ent d,
      requestT  out = 450.m ll seconds,
      mTLSServ ce dent f er = So (serv ce dent f er)
    )
  )

  val p nkStoreCl ent = new Storer.F nagledCl ent(
    readOnlyThr ftServ ce(
      "p nk_store",
      "/s/sp derduck/p nk-store",
      statsRece ver,
      recos njectorThr ftCl ent d,
      requestT  out = 450.m ll seconds,
      mTLSServ ce dent f er = So (serv ce dent f er)
    )
  )

  // Stores
  pr vate val _g zmoduckStore = {
    val queryF elds: Set[QueryF elds] = Set(
      QueryF elds.D scoverab l y,
      QueryF elds.Labels,
      QueryF elds.Safety
    )
    val context: LookupContext = LookupContext(
       ncludeDeact vated = true,
      safetyLevel = So (SafetyLevel.Recom ndat ons)
    )

    G zmoduckUserStore(
      cl ent = g zmoduckCl ent,
      queryF elds = queryF elds,
      context = context,
      statsRece ver = statsRece ver
    )
  }

  overr de val userStore: ReadableStore[Long, User] = {
    //  mcac  based cac 
    Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = _g zmoduckStore,
      cac Cl ent = recos njectorCoreSvcsCac Cl ent,
      ttl = 2.h s
    )(
      value nject on = B naryScalaCodec(User),
      statsRece ver = statsRece ver.scope("UserStore"),
      keyToStr ng = { k: Long =>
        s"usr /$k"
      }
    )
  }

  /**
   * T etyP e store, used to fetch t et objects w n unava lable, and also as a s ce of
   * t et SafetyLevel f lter ng.
   * Note:   do NOT cac  T etyP e calls, as   makes t et SafetyLevel f lter ng less accurate.
   * T etyP e QPS  s < 20K/cluster.
   * More  nfo  s  re:
   * https://cg .tw ter.b z/s ce/tree/src/thr ft/com/tw ter/spam/rtf/safety_level.thr ft
   */
  overr de val t etyP eStore: ReadableStore[Long, T etyP eResult] = {
    val getT etOpt ons = So (
      GetT etOpt ons(
         ncludeCards = true,
        safetyLevel = So (SafetyLevel.RecosWr ePath)
      )
    )
    T etyP eStore(
      t etyP eCl ent,
      getT etOpt ons,
      convertExcept onsToNotFound = false // Do not suppress T etyP e errors. Leave   to caller
    )
  }

  pr vate val _url nfoStore = {
    // n  al ze p nk store cl ent, for pars ng url
    UrlResolver(
      p nkStoreCl ent,
      statsRece ver.scope("urlFetc r"),
      cl ent d = Cl ent dent f er.Recoshose
    )
  }

  overr de val url nfoStore: ReadableStore[Str ng, Url nfo] = {
    //  mcac  based cac 
    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = _url nfoStore,
      cac Cl ent = recos njectorCoreSvcsCac Cl ent,
      ttl = 2.h s
    )(
      value nject on = Url nfo nject on,
      statsRece ver = statsRece ver.scope("Url nfoStore"),
      keyToStr ng = { k: Str ng =>
        s"u sr /$k"
      }
    )

    ObservedCac dReadableStore.from(
       mcac dStore,
      ttl = 1.m nutes,
      maxKeys = 1e5.to nt,
      w ndowS ze = 10000L,
      cac Na  = "url_store_ n_proc_cac "
    )(statsRece ver.scope("url_store_ n_proc_cac "))
  }

  overr de val soc alGraph dStore = ReadableStoreOfSt ch {  dsRequest:  dsRequest =>
    Soc alGraph(sgsCl ent). ds( dsRequest)
  }

  /**
   * MH Store for updat ng t  last t   user created a t et
   */
  val t etCreat onStore: T etCreat onT  MHStore = {
    val cl ent = ManhattanKVCl ent(
      app d = "recos_t et_creat on_ nfo",
      dest = "/s/manhattan/o ga.nat ve-thr ft",
      mtlsParams = ManhattanKVCl entMtlsParams(serv ce dent f er)
    )

    val endpo nt = ManhattanKVEndpo ntBu lder(cl ent)
      .defaultMaxT  out(700.m ll seconds)
      .statsRece ver(
        statsRece ver
          .scope(serv ce dent f er.zone)
          .scope(serv ce dent f er.env ron nt)
          .scope("recos_ njector_t et_creat on_ nfo_store")
      )
      .bu ld()

    val dataset =  f (serv ce dent f er.env ron nt == "prod") {
      "recos_ njector_t et_creat on_ nfo"
    } else {
      "recos_ njector_t et_creat on_ nfo_stag ng"
    }

    new T etCreat onT  MHStore(
      cluster = serv ce dent f er.zone,
      endpo nt = endpo nt,
      dataset = dataset,
      wr eTtl = So (14.days),
      statsRece ver.scope("recos_ njector_t et_creat on_ nfo_store")
    )
  }

  // wa  for all serversets to populate
  overr de def  n (): Future[Un ] = Cl entReg stry.expAllReg steredCl entsResolved().un 
}
