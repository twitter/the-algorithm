package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etV deoWatch
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAct on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo

abstract class BaseV deoAdsCallbackEngage nt(act onType: Act onType)
    extends BaseAdsCallbackEngage nt(act onType = act onType) {

  overr de def get em( nput: SpendServerEvent): Opt on[ em] = {
     nput.engage ntEvent.flatMap { e =>
      e. mpress onData.flatMap {   =>
        getT et nfo( .promotedT et d,  .organ cT et d,  .advert ser d,  nput)
      }
    }
  }

  pr vate def getT et nfo(
    promotedT et d: Opt on[Long],
    organ cT et d: Opt on[Long],
    advert ser d: Long,
     nput: SpendServerEvent
  ): Opt on[ em] = {
    val act onedT et dOpt: Opt on[Long] =
       f (promotedT et d. sEmpty) organ cT et d else promotedT et d
    act onedT et dOpt.map { act onT et d =>
       em.T et nfo(
        T et nfo(
          act onT et d = act onT et d,
          act onT etAuthor nfo = So (Author nfo(author d = So (advert ser d))),
          t etAct on nfo = So (
            T etAct on nfo.T etV deoWatch(
              T etV deoWatch(
                 sMonet zable = So (true),
                v deoOwner d =  nput.engage ntEvent
                  .flatMap(e => e.cardEngage nt).flatMap(_.ampl fyDeta ls).flatMap(_.v deoOwner d),
                v deoUu d =  nput.engage ntEvent
                  .flatMap(_.cardEngage nt).flatMap(_.ampl fyDeta ls).flatMap(_.v deoUu d),
                prerollOwner d =  nput.engage ntEvent
                  .flatMap(e => e.cardEngage nt).flatMap(_.ampl fyDeta ls).flatMap(
                    _.prerollOwner d),
                prerollUu d =  nput.engage ntEvent
                  .flatMap(_.cardEngage nt).flatMap(_.ampl fyDeta ls).flatMap(_.prerollUu d)
              ))
          )
        ),
      )
    }
  }
}
