packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.crowd_selonarch_accounts

import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.crowd_selonarch_accounts.CrowdSelonarchAccountsParams.AccountsFiltelonringAndRankingLogics
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.crowd_selonarch_accounts.CrowdSelonarchAccountsParams.CandidatelonSourcelonelonnablelond
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasGelonohashAndCountryCodelon
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.onboarding.relonlelonvancelon.crowd_selonarch_accounts.thriftscala.CrowdSelonarchAccounts
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.CrowdSelonarchAccountsClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Duration
import com.twittelonr.util.logging.Logging
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct AccountsFiltelonringAndRankingLogicId elonxtelonnds elonnumelonration {
  typelon AccountsFiltelonringAndRankingLogicId = Valuelon

  val NelonwSelonarchelonsDaily: AccountsFiltelonringAndRankingLogicId = Valuelon("nelonw_selonarchelons_daily")
  val NelonwSelonarchelonsWelonelonkly: AccountsFiltelonringAndRankingLogicId = Valuelon("nelonw_selonarchelons_welonelonkly")
  val SelonarchelonsDaily: AccountsFiltelonringAndRankingLogicId = Valuelon("selonarchelons_daily")
  val SelonarchelonsWelonelonkly: AccountsFiltelonringAndRankingLogicId = Valuelon("selonarchelons_welonelonkly")
}

objelonct CrowdSelonarchAccountsSourcelon {
  val MaxCachelonSizelon = 500
  val CachelonTTL: Duration = Duration.fromHours(24)

  typelon Targelont = HasParams with HasClielonntContelonxt with HasGelonohashAndCountryCodelon

  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.CrowdSelonarchAccounts.toString)
}

@Singlelonton
class CrowdSelonarchAccountsSourcelon @Injelonct() (
  crowdSelonarchAccountsClielonntColumn: CrowdSelonarchAccountsClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr,
) elonxtelonnds CandidatelonSourcelon[CrowdSelonarchAccountsSourcelon.Targelont, CandidatelonUselonr]
    with Logging {

  /** @selonelon [[CandidatelonSourcelonIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CrowdSelonarchAccountsSourcelon.Idelonntifielonr

  privatelon val stats = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon)
  privatelon val relonquelonstsStats = stats.countelonr("relonquelonsts")
  privatelon val noCountryCodelonStats = stats.countelonr("no_country_codelon")
  privatelon val succelonssStats = stats.countelonr("succelonss")
  privatelon val elonrrorStats = stats.countelonr("elonrror")

  privatelon val cachelon = StitchCachelon[String, Option[CrowdSelonarchAccounts]](
    maxCachelonSizelon = CrowdSelonarchAccountsSourcelon.MaxCachelonSizelon,
    ttl = CrowdSelonarchAccountsSourcelon.CachelonTTL,
    statsReloncelonivelonr = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon, "cachelon"),
    undelonrlyingCall = (k: String) => {
      crowdSelonarchAccountsClielonntColumn.felontchelonr
        .felontch(k)
        .map { relonsult => relonsult.v }
    }
  )

  /** relonturns a Selonq of ''potelonntial'' contelonnt */
  ovelonrridelon delonf apply(
    targelont: CrowdSelonarchAccountsSourcelon.Targelont
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    if (!targelont.params(CandidatelonSourcelonelonnablelond)) {
      relonturn Stitch.valuelon(Selonq[CandidatelonUselonr]())
    }
    relonquelonstsStats.incr()
    targelont.gelontCountryCodelon
      .orelonlselon(targelont.gelonohashAndCountryCodelon.flatMap(_.countryCodelon)).map { countryCodelon =>
        Stitch
          .collelonct(targelont
            .params(AccountsFiltelonringAndRankingLogics).map(logic =>
              cachelon.relonadThrough(countryCodelon.toUppelonrCaselon() + "-" + logic)))
          .onSuccelonss(_ => {
            succelonssStats.incr()
          })
          .onFailurelon(t => {
            delonbug("candidatelon sourcelon failelond idelonntifielonr = %s".format(idelonntifielonr), t)
            elonrrorStats.incr()
          })
          .map(transformCrowdSelonarchAccountsToCandidatelonSourcelon)
      }.gelontOrelonlselon {
        noCountryCodelonStats.incr()
        Stitch.valuelon(Selonq[CandidatelonUselonr]())
      }
  }

  privatelon delonf transformCrowdSelonarchAccountsToCandidatelonSourcelon(
    crowdSelonarchAccounts: Selonq[Option[CrowdSelonarchAccounts]]
  ): Selonq[CandidatelonUselonr] = {
    crowdSelonarchAccounts
      .flatMap(opt =>
        opt
          .map(accounts =>
            accounts.accounts.map(account =>
              CandidatelonUselonr(
                id = account.accountId,
                scorelon = Somelon(account.selonarchActivityScorelon),
              ).withCandidatelonSourcelon(idelonntifielonr)))
          .gelontOrelonlselon(Selonq[CandidatelonUselonr]()))
  }
}
