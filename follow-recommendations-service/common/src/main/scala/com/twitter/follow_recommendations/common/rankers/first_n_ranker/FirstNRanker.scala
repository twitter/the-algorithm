packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.first_n_rankelonr

import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Rankelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasQualityFactor
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.utils.Utils
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

/**
 * This class is melonant to filtelonr candidatelons belontwelonelonn stagelons of our rankelonr by taking thelon first N
 * candidatelons, melonrging any candidatelon sourcelon information for candidatelons with multiplelon elonntrielons.
 * To allow us to chain this truncation opelonration any numbelonr of timelons selonquelonntially within thelon main
 * ranking buildelonr, welon abstract thelon truncation as a selonparatelon Rankelonr
 */
@Singlelonton
class FirstNRankelonr[Targelont <: HasClielonntContelonxt with HasParams with HasQualityFactor] @Injelonct() (
  stats: StatsReloncelonivelonr)
    elonxtelonnds Rankelonr[Targelont, CandidatelonUselonr] {

  val namelon: String = this.gelontClass.gelontSimplelonNamelon
  privatelon val baselonStats = stats.scopelon("first_n_rankelonr")
  val scalelondDownByQualityFactorCountelonr =
    baselonStats.countelonr("scalelond_down_by_quality_factor")
  privatelon val melonrgelonStat = baselonStats.scopelon("melonrgelond_candidatelons")
  privatelon val melonrgelonStat2 = melonrgelonStat.countelonr("2")
  privatelon val melonrgelonStat3 = melonrgelonStat.countelonr("3")
  privatelon val melonrgelonStat4 = melonrgelonStat.countelonr("4+")
  privatelon val candidatelonSizelonStats = baselonStats.scopelon("candidatelon_sizelon")

  privatelon caselon class CandidatelonSourcelonScorelon(
    candidatelonId: Long,
    sourcelonId: CandidatelonSourcelonIdelonntifielonr,
    scorelon: Option[Doublelon])

  /**
   * Adds thelon rank of elonach candidatelon baselond on thelon primary candidatelon sourcelon's scorelon.
   * In thelon elonvelonnt whelonrelon thelon providelond ordelonring of candidatelons do not align with thelon scorelon,
   * welon will relonspelonct thelon scorelon, sincelon thelon ordelonring might havelon belonelonn mixelond up duelon to othelonr prelonvious
   * stelonps likelon thelon shufflelonFn in thelon `WelonightelondCandidatelonSourcelonRankelonr`.
   * @param candidatelons  ordelonrelond list of candidatelons
   * @relonturn            samelon ordelonrelond list of candidatelons, but with thelon rank information appelonndelond
   */
  delonf addRank(candidatelons: Selonq[CandidatelonUselonr]): Selonq[CandidatelonUselonr] = {
    val candidatelonSourcelonRanks = for {
      (sourcelonIdOpt, sourcelonCandidatelons) <- candidatelons.groupBy(_.gelontPrimaryCandidatelonSourcelon)
      (candidatelon, rank) <- sourcelonCandidatelons.sortBy(-_.scorelon.gelontOrelonlselon(0.0)).zipWithIndelonx
    } yielonld {
      (candidatelon, sourcelonIdOpt) -> rank
    }
    candidatelons.map { c =>
      c.gelontPrimaryCandidatelonSourcelon
        .map { sourcelonId =>
          val sourcelonRank = candidatelonSourcelonRanks((c, c.gelontPrimaryCandidatelonSourcelon))
          c.addCandidatelonSourcelonRanksMap(Map(sourcelonId -> sourcelonRank))
        }.gelontOrelonlselon(c)
    }
  }

  ovelonrridelon delonf rank(targelont: Targelont, candidatelons: Selonq[CandidatelonUselonr]): Stitch[Selonq[CandidatelonUselonr]] = {

    val scalelonDownFactor = Math.max(
      targelont.qualityFactor.gelontOrelonlselon(1.0d),
      targelont.params(FirstNRankelonrParams.MinNumCandidatelonsScorelondScalelonDownFactor)
    )

    if (scalelonDownFactor < 1.0d)
      scalelondDownByQualityFactorCountelonr.incr()

    val n = (targelont.params(FirstNRankelonrParams.CandidatelonsToRank) * scalelonDownFactor).toInt
    val scribelonRankingInfo: Boolelonan =
      targelont.params(FirstNRankelonrParams.ScribelonRankingInfoInFirstNRankelonr)
    candidatelonSizelonStats.countelonr(s"n$n").incr()
    val candidatelonsWithRank = addRank(candidatelons)
    if (targelont.params(FirstNRankelonrParams.GroupDuplicatelonCandidatelons)) {
      val groupelondCandidatelons: Map[Long, Selonq[CandidatelonUselonr]] = candidatelonsWithRank.groupBy(_.id)
      val topN = candidatelons
        .map { c =>
          melonrgelon(groupelondCandidatelons(c.id))
        }.distinct.takelon(n)
      Stitch.valuelon(if (scribelonRankingInfo) Utils.addRankingInfo(topN, namelon) elonlselon topN)
    } elonlselon {
      Stitch.valuelon(
        if (scribelonRankingInfo) Utils.addRankingInfo(candidatelonsWithRank, namelon).takelon(n)
        elonlselon candidatelonsWithRank.takelon(n))
    } // for elonfficielonncy, if don't nelonelond to delonduplicatelon
  }

  /**
   * welon uselon thelon primary candidatelon sourcelon of thelon first elonntry, and aggrelongatelon all of thelon othelonr elonntrielons'
   * candidatelon sourcelon scorelons into thelon first elonntry's candidatelonSourcelonScorelons
   * @param candidatelons list of candidatelons with thelon samelon id
   * @relonturn           a singlelon melonrgelond candidatelon
   */
  privatelon[first_n_rankelonr] delonf melonrgelon(candidatelons: Selonq[CandidatelonUselonr]): CandidatelonUselonr = {
    if (candidatelons.sizelon == 1) {
      candidatelons.helonad
    } elonlselon {
      candidatelons.sizelon match {
        caselon 2 => melonrgelonStat2.incr()
        caselon 3 => melonrgelonStat3.incr()
        caselon i if i >= 4 => melonrgelonStat4.incr()
        caselon _ =>
      }
      val allSourcelons = candidatelons.flatMap(_.gelontCandidatelonSourcelons).toMap
      val allRanks = candidatelons.flatMap(_.gelontCandidatelonRanks).toMap
      candidatelons.helonad.addCandidatelonSourcelonScorelonsMap(allSourcelons).addCandidatelonSourcelonRanksMap(allRanks)
    }
  }
}
