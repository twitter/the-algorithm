packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.ppmi_localelon_follow

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.ppmi_localelon_follow.PPMILocalelonFollowSourcelonParams.CandidatelonSourcelonelonnablelond
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.ppmi_localelon_follow.PPMILocalelonFollowSourcelonParams.LocalelonToelonxcludelonFromReloncommelonndation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.UselonrPrelonfelonrrelondLanguagelonsOnUselonrClielonntColumn
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.LocalelonFollowPpmiClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams

/**
 * Felontchelons candidatelons baselond on thelon Positivelon Pointwiselon Mutual Information (PPMI) statistic
 * for a selont of localelons
 * */
@Singlelonton
class PPMILocalelonFollowSourcelon @Injelonct() (
  uselonrPrelonfelonrrelondLanguagelonsOnUselonrClielonntColumn: UselonrPrelonfelonrrelondLanguagelonsOnUselonrClielonntColumn,
  localelonFollowPpmiClielonntColumn: LocalelonFollowPpmiClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasClielonntContelonxt with HasParams, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = PPMILocalelonFollowSourcelon.Idelonntifielonr
  privatelon val stats = statsReloncelonivelonr.scopelon("PPMILocalelonFollowSourcelon")

  ovelonrridelon delonf apply(targelont: HasClielonntContelonxt with HasParams): Stitch[Selonq[CandidatelonUselonr]] = {
    (for {
      countryCodelon <- targelont.gelontCountryCodelon
      uselonrId <- targelont.gelontOptionalUselonrId
    } yielonld {
      gelontPrelonfelonrrelondLocalelons(uselonrId, countryCodelon.toLowelonrCaselon())
        .flatMap { localelon =>
          stats.addGaugelon("allLocalelon") {
            localelon.lelonngth
          }
          val filtelonrelondLocalelon =
            localelon.filtelonr(!targelont.params(LocalelonToelonxcludelonFromReloncommelonndation).contains(_))
          stats.addGaugelon("postFiltelonrLocalelon") {
            filtelonrelondLocalelon.lelonngth
          }
          if (targelont.params(CandidatelonSourcelonelonnablelond)) {
            gelontPPMILocalelonFollowCandidatelons(filtelonrelondLocalelon)
          } elonlselon Stitch(Selonq.elonmpty)
        }
        .map(_.sortBy(_.scorelon)(Ordelonring[Option[Doublelon]].relonvelonrselon)
          .takelon(PPMILocalelonFollowSourcelon.DelonfaultMaxCandidatelonsToRelonturn))
    }).gelontOrelonlselon(Stitch.Nil)
  }

  privatelon delonf gelontPPMILocalelonFollowCandidatelons(
    localelons: Selonq[String]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    Stitch
      .travelonrselon(localelons) { localelon =>
        // Gelont PPMI candidatelons for elonach localelon
        localelonFollowPpmiClielonntColumn.felontchelonr
          .felontch(localelon)
          .map(_.v
            .map(_.candidatelons).gelontOrelonlselon(Nil).map { candidatelon =>
              CandidatelonUselonr(id = candidatelon.uselonrId, scorelon = Somelon(candidatelon.scorelon))
            }.map(_.withCandidatelonSourcelon(idelonntifielonr)))
      }.map(_.flattelonn)
  }

  privatelon delonf gelontPrelonfelonrrelondLocalelons(uselonrId: Long, countryCodelon: String): Stitch[Selonq[String]] = {
    uselonrPrelonfelonrrelondLanguagelonsOnUselonrClielonntColumn.felontchelonr
      .felontch(uselonrId)
      .map(_.v.map(_.languagelons).gelontOrelonlselon(Nil).map { lang =>
        s"$countryCodelon-$lang".toLowelonrCaselon
      })
  }
}

objelonct PPMILocalelonFollowSourcelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.PPMILocalelonFollow.toString)
  val DelonfaultMaxCandidatelonsToRelonturn = 100
}
