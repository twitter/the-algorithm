package com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts

 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsParams.AccountsF lter ngAndRank ngLog cs
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsParams.Cand dateS ceEnabled
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasGeohashAndCountryCode
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.onboard ng.relevance.crowd_search_accounts.thr ftscala.CrowdSearchAccounts
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.CrowdSearchAccountsCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

object AccountsF lter ngAndRank ngLog c d extends Enu rat on {
  type AccountsF lter ngAndRank ngLog c d = Value

  val NewSearc sDa ly: AccountsF lter ngAndRank ngLog c d = Value("new_searc s_da ly")
  val NewSearc s ekly: AccountsF lter ngAndRank ngLog c d = Value("new_searc s_ ekly")
  val Searc sDa ly: AccountsF lter ngAndRank ngLog c d = Value("searc s_da ly")
  val Searc s ekly: AccountsF lter ngAndRank ngLog c d = Value("searc s_ ekly")
}

object CrowdSearchAccountsS ce {
  val MaxCac S ze = 500
  val Cac TTL: Durat on = Durat on.fromH s(24)

  type Target = HasParams w h HasCl entContext w h HasGeohashAndCountryCode

  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.CrowdSearchAccounts.toStr ng)
}

@S ngleton
class CrowdSearchAccountsS ce @ nject() (
  crowdSearchAccountsCl entColumn: CrowdSearchAccountsCl entColumn,
  statsRece ver: StatsRece ver,
) extends Cand dateS ce[CrowdSearchAccountsS ce.Target, Cand dateUser]
    w h Logg ng {

  /** @see [[Cand dateS ce dent f er]] */
  overr de val  dent f er: Cand dateS ce dent f er =
    CrowdSearchAccountsS ce. dent f er

  pr vate val stats = statsRece ver.scope( dent f er.na )
  pr vate val requestsStats = stats.counter("requests")
  pr vate val noCountryCodeStats = stats.counter("no_country_code")
  pr vate val successStats = stats.counter("success")
  pr vate val errorStats = stats.counter("error")

  pr vate val cac  = St chCac [Str ng, Opt on[CrowdSearchAccounts]](
    maxCac S ze = CrowdSearchAccountsS ce.MaxCac S ze,
    ttl = CrowdSearchAccountsS ce.Cac TTL,
    statsRece ver = statsRece ver.scope( dent f er.na , "cac "),
    underly ngCall = (k: Str ng) => {
      crowdSearchAccountsCl entColumn.fetc r
        .fetch(k)
        .map { result => result.v }
    }
  )

  /** returns a Seq of ''potent al'' content */
  overr de def apply(
    target: CrowdSearchAccountsS ce.Target
  ): St ch[Seq[Cand dateUser]] = {
     f (!target.params(Cand dateS ceEnabled)) {
      return St ch.value(Seq[Cand dateUser]())
    }
    requestsStats. ncr()
    target.getCountryCode
      .orElse(target.geohashAndCountryCode.flatMap(_.countryCode)).map { countryCode =>
        St ch
          .collect(target
            .params(AccountsF lter ngAndRank ngLog cs).map(log c =>
              cac .readThrough(countryCode.toUpperCase() + "-" + log c)))
          .onSuccess(_ => {
            successStats. ncr()
          })
          .onFa lure(t => {
            debug("cand date s ce fa led  dent f er = %s".format( dent f er), t)
            errorStats. ncr()
          })
          .map(transformCrowdSearchAccountsToCand dateS ce)
      }.getOrElse {
        noCountryCodeStats. ncr()
        St ch.value(Seq[Cand dateUser]())
      }
  }

  pr vate def transformCrowdSearchAccountsToCand dateS ce(
    crowdSearchAccounts: Seq[Opt on[CrowdSearchAccounts]]
  ): Seq[Cand dateUser] = {
    crowdSearchAccounts
      .flatMap(opt =>
        opt
          .map(accounts =>
            accounts.accounts.map(account =>
              Cand dateUser(
                 d = account.account d,
                score = So (account.searchAct v yScore),
              ).w hCand dateS ce( dent f er)))
          .getOrElse(Seq[Cand dateUser]()))
  }
}
