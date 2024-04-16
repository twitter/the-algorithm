package com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts

 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsParams.AccountsF lter ngAndRank ngLog cs
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsParams.Cand dateS ceEnabled
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasGeohashAndCountryCode
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.onboard ng.relevance.organ c_follows_accounts.thr ftscala.Organ cFollowsAccounts
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.Organ cFollowsAccountsCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

object AccountsF lter ngAndRank ngLog c d extends Enu rat on {
  type AccountsF lter ngAndRank ngLog c d = Value

  val NewOrgan cFollows: AccountsF lter ngAndRank ngLog c d = Value("new_organ c_follows")
  val NonNewOrgan cFollows: AccountsF lter ngAndRank ngLog c d = Value("non_new_organ c_follows")
  val Organ cFollows: AccountsF lter ngAndRank ngLog c d = Value("organ c_follows")
}

object TopOrgan cFollowsAccountsS ce {
  val MaxCac S ze = 500
  val Cac TTL: Durat on = Durat on.fromH s(24)

  type Target = HasParams w h HasCl entContext w h HasGeohashAndCountryCode

  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.Organ cFollowAccounts.toStr ng)
}

@S ngleton
class TopOrgan cFollowsAccountsS ce @ nject() (
  organ cFollowsAccountsCl entColumn: Organ cFollowsAccountsCl entColumn,
  statsRece ver: StatsRece ver,
) extends Cand dateS ce[TopOrgan cFollowsAccountsS ce.Target, Cand dateUser]
    w h Logg ng {

  /** @see [[Cand dateS ce dent f er]] */
  overr de val  dent f er: Cand dateS ce dent f er =
    TopOrgan cFollowsAccountsS ce. dent f er

  pr vate val stats = statsRece ver.scope( dent f er.na )
  pr vate val requestsStats = stats.counter("requests")
  pr vate val noCountryCodeStats = stats.counter("no_country_code")
  pr vate val successStats = stats.counter("success")
  pr vate val errorStats = stats.counter("error")

  pr vate val cac  = St chCac [Str ng, Opt on[Organ cFollowsAccounts]](
    maxCac S ze = TopOrgan cFollowsAccountsS ce.MaxCac S ze,
    ttl = TopOrgan cFollowsAccountsS ce.Cac TTL,
    statsRece ver = statsRece ver.scope( dent f er.na , "cac "),
    underly ngCall = (k: Str ng) => {
      organ cFollowsAccountsCl entColumn.fetc r
        .fetch(k)
        .map { result => result.v }
    }
  )

  /** returns a Seq of ''potent al'' content */
  overr de def apply(
    target: TopOrgan cFollowsAccountsS ce.Target
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
          .map(transformOrgan cFollowAccountssToCand dateS ce)
      }.getOrElse {
        noCountryCodeStats. ncr()
        St ch.value(Seq[Cand dateUser]())
      }
  }

  pr vate def transformOrgan cFollowAccountssToCand dateS ce(
    organ cFollowsAccounts: Seq[Opt on[Organ cFollowsAccounts]]
  ): Seq[Cand dateUser] = {
    organ cFollowsAccounts
      .flatMap(opt =>
        opt
          .map(accounts =>
            accounts.accounts.map(account =>
              Cand dateUser(
                 d = account.account d,
                score = So (account.follo dCountScore),
              ).w hCand dateS ce( dent f er)))
          .getOrElse(Seq[Cand dateUser]()))
  }
}
