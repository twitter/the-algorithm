package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAct on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

abstract class BaseAdsCallbackEngage nt(act onType: Act onType) {

  protected def get em( nput: SpendServerEvent): Opt on[ em] = {
     nput.engage ntEvent.flatMap { e =>
      e. mpress onData.flatMap {   =>
        getPromotedT et nfo( .promotedT et d,  .advert ser d)
      }
    }
  }

  protected def getPromotedT et nfo(
    promotedT et dOpt: Opt on[Long],
    advert ser d: Long,
    t etAct on nfoOpt: Opt on[T etAct on nfo] = None
  ): Opt on[ em] = {
    promotedT et dOpt.map { promotedT et d =>
       em.T et nfo(
        T et nfo(
          act onT et d = promotedT et d,
          act onT etAuthor nfo = So (Author nfo(author d = So (advert ser d))),
          t etAct on nfo = t etAct on nfoOpt)
      )
    }
  }

  def getUUA( nput: SpendServerEvent): Opt on[Un f edUserAct on] = {
    val user dent f er: User dent f er =
      User dent f er(
        user d =  nput.engage ntEvent.flatMap(e => e.cl ent nfo.flatMap(_.user d64)),
        guest dMarket ng =  nput.engage ntEvent.flatMap(e => e.cl ent nfo.flatMap(_.guest d)),
      )

    get em( nput).map {  em =>
      Un f edUserAct on(
        user dent f er = user dent f er,
         em =  em,
        act onType = act onType,
        event tadata = getEvent tadata( nput),
      )
    }
  }

  protected def getEvent tadata( nput: SpendServerEvent): Event tadata =
    Event tadata(
      s ceT  stampMs =  nput.engage ntEvent
        .map { e => e.engage ntEpochT  M ll Sec }.getOrElse(AdapterUt ls.currentT  stampMs),
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerAdsCallbackEngage nts,
      language =  nput.engage ntEvent.flatMap { e => e.cl ent nfo.flatMap(_.languageCode) },
      countryCode =  nput.engage ntEvent.flatMap { e => e.cl ent nfo.flatMap(_.countryCode) },
      cl entApp d =
         nput.engage ntEvent.flatMap { e => e.cl ent nfo.flatMap(_.cl ent d) }.map { _.toLong },
    )
}
