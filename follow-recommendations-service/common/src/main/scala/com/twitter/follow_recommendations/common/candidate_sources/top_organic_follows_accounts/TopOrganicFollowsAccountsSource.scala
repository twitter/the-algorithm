packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.top_organic_follows_accounts

import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.AccountsFiltelonringAndRankingLogics
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.CandidatelonSourcelonelonnablelond
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasGelonohashAndCountryCodelon
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.onboarding.relonlelonvancelon.organic_follows_accounts.thriftscala.OrganicFollowsAccounts
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.OrganicFollowsAccountsClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Duration
import com.twittelonr.util.logging.Logging
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct AccountsFiltelonringAndRankingLogicId elonxtelonnds elonnumelonration {
  typelon AccountsFiltelonringAndRankingLogicId = Valuelon

  val NelonwOrganicFollows: AccountsFiltelonringAndRankingLogicId = Valuelon("nelonw_organic_follows")
  val NonNelonwOrganicFollows: AccountsFiltelonringAndRankingLogicId = Valuelon("non_nelonw_organic_follows")
  val OrganicFollows: AccountsFiltelonringAndRankingLogicId = Valuelon("organic_follows")
}

objelonct TopOrganicFollowsAccountsSourcelon {
  val MaxCachelonSizelon = 500
  val CachelonTTL: Duration = Duration.fromHours(24)

  typelon Targelont = HasParams with HasClielonntContelonxt with HasGelonohashAndCountryCodelon

  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.OrganicFollowAccounts.toString)
}

@Singlelonton
class TopOrganicFollowsAccountsSourcelon @Injelonct() (
  organicFollowsAccountsClielonntColumn: OrganicFollowsAccountsClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr,
) elonxtelonnds CandidatelonSourcelon[TopOrganicFollowsAccountsSourcelon.Targelont, CandidatelonUselonr]
    with Logging {

  /** @selonelon [[CandidatelonSourcelonIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    TopOrganicFollowsAccountsSourcelon.Idelonntifielonr

  privatelon val stats = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon)
  privatelon val relonquelonstsStats = stats.countelonr("relonquelonsts")
  privatelon val noCountryCodelonStats = stats.countelonr("no_country_codelon")
  privatelon val succelonssStats = stats.countelonr("succelonss")
  privatelon val elonrrorStats = stats.countelonr("elonrror")

  privatelon val cachelon = StitchCachelon[String, Option[OrganicFollowsAccounts]](
    maxCachelonSizelon = TopOrganicFollowsAccountsSourcelon.MaxCachelonSizelon,
    ttl = TopOrganicFollowsAccountsSourcelon.CachelonTTL,
    statsReloncelonivelonr = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon, "cachelon"),
    undelonrlyingCall = (k: String) => {
      organicFollowsAccountsClielonntColumn.felontchelonr
        .felontch(k)
        .map { relonsult => relonsult.v }
    }
  )

  /** relonturns a Selonq of ''potelonntial'' contelonnt */
  ovelonrridelon delonf apply(
    targelont: TopOrganicFollowsAccountsSourcelon.Targelont
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
          .map(transformOrganicFollowAccountssToCandidatelonSourcelon)
      }.gelontOrelonlselon {
        noCountryCodelonStats.incr()
        Stitch.valuelon(Selonq[CandidatelonUselonr]())
      }
  }

  privatelon delonf transformOrganicFollowAccountssToCandidatelonSourcelon(
    organicFollowsAccounts: Selonq[Option[OrganicFollowsAccounts]]
  ): Selonq[CandidatelonUselonr] = {
    organicFollowsAccounts
      .flatMap(opt =>
        opt
          .map(accounts =>
            accounts.accounts.map(account =>
              CandidatelonUselonr(
                id = account.accountId,
                scorelon = Somelon(account.followelondCountScorelon),
              ).withCandidatelonSourcelon(idelonntifielonr)))
          .gelontOrelonlselon(Selonq[CandidatelonUselonr]()))
  }
}
