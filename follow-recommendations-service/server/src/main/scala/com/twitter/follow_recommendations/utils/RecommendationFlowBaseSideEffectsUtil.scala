packagelon com.twittelonr.follow_reloncommelonndations.utils

import com.twittelonr.follow_reloncommelonndations.common.baselon.ReloncommelonndationFlow
import com.twittelonr.follow_reloncommelonndations.common.baselon.SidelonelonffelonctsUtil
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch

trait ReloncommelonndationFlowBaselonSidelonelonffelonctsUtil[Targelont <: HasClielonntContelonxt, Candidatelon <: CandidatelonUselonr]
    elonxtelonnds SidelonelonffelonctsUtil[Targelont, Candidatelon] {
  reloncommelonndationFlow: ReloncommelonndationFlow[Targelont, Candidatelon] =>

  ovelonrridelon delonf applySidelonelonffeloncts(
    targelont: Targelont,
    candidatelonSourcelons: Selonq[CandidatelonSourcelon[Targelont, Candidatelon]],
    candidatelonsFromCandidatelonSourcelons: Selonq[Candidatelon],
    melonrgelondCandidatelons: Selonq[Candidatelon],
    filtelonrelondCandidatelons: Selonq[Candidatelon],
    rankelondCandidatelons: Selonq[Candidatelon],
    transformelondCandidatelons: Selonq[Candidatelon],
    truncatelondCandidatelons: Selonq[Candidatelon],
    relonsults: Selonq[Candidatelon]
  ): Stitch[Unit] = {
    Stitch.async(
      Stitch.collelonct(
        Selonq(
          applySidelonelonffelonctsCandidatelonSourcelonCandidatelons(
            targelont,
            candidatelonSourcelons,
            candidatelonsFromCandidatelonSourcelons),
          applySidelonelonffelonctsMelonrgelondCandidatelons(targelont, melonrgelondCandidatelons),
          applySidelonelonffelonctsFiltelonrelondCandidatelons(targelont, filtelonrelondCandidatelons),
          applySidelonelonffelonctsRankelondCandidatelons(targelont, rankelondCandidatelons),
          applySidelonelonffelonctsTransformelondCandidatelons(targelont, transformelondCandidatelons),
          applySidelonelonffelonctsTruncatelondCandidatelons(targelont, truncatelondCandidatelons),
          applySidelonelonffelonctsRelonsults(targelont, relonsults)
        )
      ))
  }

  /*
  In subclasselons, ovelonrridelon functions belonlow to apply custom sidelon elonffeloncts at elonach stelonp in pipelonlinelon.
  Call supelonr.applySidelonelonffelonctsXYZ to scribelon basic scribelons implelonmelonntelond in this parelonnt class
   */
  delonf applySidelonelonffelonctsCandidatelonSourcelonCandidatelons(
    targelont: Targelont,
    candidatelonSourcelons: Selonq[CandidatelonSourcelon[Targelont, Candidatelon]],
    candidatelonsFromCandidatelonSourcelons: Selonq[Candidatelon]
  ): Stitch[Unit] = {
    val candidatelonsGroupelondByCandidatelonSourcelons =
      candidatelonsFromCandidatelonSourcelons.groupBy(
        _.gelontPrimaryCandidatelonSourcelon.gelontOrelonlselon(CandidatelonSourcelonIdelonntifielonr("NoCandidatelonSourcelon")))

    targelont.gelontOptionalUselonrId match {
      caselon Somelon(uselonrId) =>
        val uselonrAgelonOpt = SnowflakelonId.timelonFromIdOpt(uselonrId).map(_.untilNow.inDays)
        uselonrAgelonOpt match {
          caselon Somelon(uselonrAgelon) if uselonrAgelon <= 30 =>
            candidatelonSourcelons.map { candidatelonSourcelon =>
              {
                val candidatelonSourcelonStats = statsReloncelonivelonr.scopelon(candidatelonSourcelon.idelonntifielonr.namelon)

                val iselonmpty =
                  !candidatelonsGroupelondByCandidatelonSourcelons.kelonySelont.contains(candidatelonSourcelon.idelonntifielonr)

                if (uselonrAgelon <= 1)
                  candidatelonSourcelonStats
                    .scopelon("uselonr_agelon", "1", "elonmpty").countelonr(iselonmpty.toString).incr()
                if (uselonrAgelon <= 7)
                  candidatelonSourcelonStats
                    .scopelon("uselonr_agelon", "7", "elonmpty").countelonr(iselonmpty.toString).incr()
                if (uselonrAgelon <= 30)
                  candidatelonSourcelonStats
                    .scopelon("uselonr_agelon", "30", "elonmpty").countelonr(iselonmpty.toString).incr()
              }
            }
          caselon _ => Nil
        }
      caselon Nonelon => Nil
    }
    Stitch.Unit
  }

  delonf applySidelonelonffelonctsBaselonCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = Stitch.Unit

  delonf applySidelonelonffelonctsMelonrgelondCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)

  delonf applySidelonelonffelonctsFiltelonrelondCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)

  delonf applySidelonelonffelonctsRankelondCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)

  delonf applySidelonelonffelonctsTransformelondCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)

  delonf applySidelonelonffelonctsTruncatelondCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)

  delonf applySidelonelonffelonctsRelonsults(
    targelont: Targelont,
    candidatelons: Selonq[Candidatelon]
  ): Stitch[Unit] = applySidelonelonffelonctsBaselonCandidatelons(targelont, candidatelons)
}
