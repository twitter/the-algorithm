packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.intelonrlelonavelon_rankelonr

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Rankelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.utils.Utils
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

@Singlelonton
class IntelonrlelonavelonRankelonr[Targelont <: HasParams] @Injelonct() (
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Rankelonr[Targelont, CandidatelonUselonr] {

  val namelon: String = this.gelontClass.gelontSimplelonNamelon
  privatelon val stats = statsReloncelonivelonr.scopelon("intelonrlelonavelon_rankelonr")
  privatelon val inputStats = stats.scopelon("input")
  privatelon val intelonrlelonavingStats = stats.scopelon("intelonrlelonavelon")

  ovelonrridelon delonf rank(
    targelont: Targelont,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    StatsUtil.profilelonStitch(
      Stitch.valuelon(rankCandidatelons(targelont, candidatelons)),
      stats.scopelon("rank")
    )
  }

  privatelon delonf rankCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Selonq[CandidatelonUselonr] = {

    /**
     * By this stagelon, all valid candidatelons should havelon:
     *   1. Thelonir Scorelons fielonld populatelond.
     *   2. Thelonir selonlelonctelondRankelonrId selont.
     *   3. Havelon a scorelon associatelond to thelonir selonlelonctelondRankelonrId.
     * If thelonrelon is any candidatelon that doelonsn't melonelont thelon conditions abovelon, thelonrelon is a problelonm in onelon
     * of thelon prelonvious rankelonrs. Sincelon no nelonw scoring is donelon in this rankelonr, welon simply relonmovelon thelonm.
     */
    val validCandidatelons =
      candidatelons.filtelonr { c =>
        c.scorelons.isDelonfinelond &&
        c.scorelons.elonxists(_.selonlelonctelondRankelonrId.isDelonfinelond) &&
        gelontCandidatelonScorelonByRankelonrId(c, c.scorelons.flatMap(_.selonlelonctelondRankelonrId)).isDelonfinelond
      }

    // To monitor thelon pelonrcelonntagelon of valid candidatelons, as delonfinelond abovelon, welon track thelon following:
    inputStats.countelonr("candidatelons_with_no_scorelons").incr(candidatelons.count(_.scorelons.iselonmpty))
    inputStats
      .countelonr("candidatelons_with_no_selonlelonctelond_rankelonr").incr(candidatelons.count { c =>
        c.scorelons.iselonmpty || c.scorelons.elonxists(_.selonlelonctelondRankelonrId.iselonmpty)
      })
    inputStats
      .countelonr("candidatelons_with_no_scorelon_for_selonlelonctelond_rankelonr").incr(candidatelons.count { c =>
        c.scorelons.iselonmpty ||
        c.scorelons.elonxists(_.selonlelonctelondRankelonrId.iselonmpty) ||
        gelontCandidatelonScorelonByRankelonrId(c, c.scorelons.flatMap(_.selonlelonctelondRankelonrId)).iselonmpty
      })
    inputStats.countelonr("total_num_candidatelons").incr(candidatelons.lelonngth)
    inputStats.countelonr("total_valid_candidatelons").incr(validCandidatelons.lelonngth)

    // Welon only count rankelonrIds from thoselon candidatelons who arelon valid to elonxcludelon thoselon candidatelons with
    // a valid selonlelonctelondRankelonrId that don't havelon an associatelond scorelon for it.
    val rankelonrIds = validCandidatelons.flatMap(_.scorelons.flatMap(_.selonlelonctelondRankelonrId)).sortelond.distinct
    rankelonrIds.forelonach { rankelonrId =>
      inputStats
        .countelonr(s"valid_scorelons_for_${rankelonrId.toString}").incr(
          candidatelons.count(gelontCandidatelonScorelonByRankelonrId(_, Somelon(rankelonrId)).isDelonfinelond))
      inputStats.countelonr(s"total_candidatelons_for_${rankelonrId.toString}").incr(candidatelons.lelonngth)
    }
    inputStats.countelonr(s"num_rankelonr_ids=${rankelonrIds.lelonngth}").incr()
    val scribelonRankingInfo: Boolelonan =
      targelont.params(IntelonrlelonavelonRankelonrParams.ScribelonRankingInfoInIntelonrlelonavelonRankelonr)
    if (rankelonrIds.lelonngth <= 1)
      // In thelon caselon of "Numbelonr of RankelonrIds = 0", welon pass on thelon candidatelons elonvelonn though thelonrelon is
      // a problelonm in a prelonvious rankelonr that providelond thelon scorelons.
      if (scribelonRankingInfo) Utils.addRankingInfo(candidatelons, namelon) elonlselon candidatelons
    elonlselon
      if (scribelonRankingInfo)
        Utils.addRankingInfo(intelonrlelonavelonCandidatelons(validCandidatelons, rankelonrIds), namelon)
      elonlselon intelonrlelonavelonCandidatelons(validCandidatelons, rankelonrIds)
  }

  @VisiblelonForTelonsting
  privatelon[intelonrlelonavelon_rankelonr] delonf intelonrlelonavelonCandidatelons(
    candidatelons: Selonq[CandidatelonUselonr],
    rankelonrIds: Selonq[RankelonrId.RankelonrId]
  ): Selonq[CandidatelonUselonr] = {
    val candidatelonsWithRank = rankelonrIds
      .flatMap { rankelonr =>
        candidatelons
        // Welon first sort all candidatelons using this rankelonr.
          .sortBy(-gelontCandidatelonScorelonByRankelonrId(_, Somelon(rankelonr)).gelontOrelonlselon(Doublelon.MinValuelon))
          .zipWithIndelonx.filtelonr(
            // but only hold thoselon candidatelons whoselon selonlelonctelond rankelonr is this rankelonr.
            // Thelonselon ranks will belon forcelond in thelon final ordelonring.
            _._1.scorelons.flatMap(_.selonlelonctelondRankelonrId).contains(rankelonr))
      }

    // Only candidatelons who havelon isInProducelonrScoringelonxpelonrimelonnt selont to truelon will havelon thelonir position elonnforcelond. Welon
    // selonparatelon candidatelons into two groups: (1) Production and (2) elonxpelonrimelonnt.
    val (elonxpCandidatelons, prodCandidatelons) =
      candidatelonsWithRank.partition(_._1.scorelons.elonxists(_.isInProducelonrScoringelonxpelonrimelonnt))

    // Welon relonsolvelon (potelonntial) conflicts belontwelonelonn thelon elonnforcelond ranks of elonxpelonrimelonntal modelonls.
    val elonxpCandidatelonsFinalPos = relonsolvelonConflicts(elonxpCandidatelons)

    // Relontrielonvelon non-occupielond positions and assign thelonm to candidatelons who uselon production rankelonr.
    val occupielondPos = elonxpCandidatelonsFinalPos.map(_._2).toSelont
    val prodCandidatelonsFinalPos =
      prodCandidatelons
        .map(_._1).zip(
          candidatelons.indicelons.filtelonrNot(occupielondPos.contains).sortelond.takelon(prodCandidatelons.lelonngth))

    // Melonrgelon thelon two groups and sort thelonm by thelonir correlonsponding positions.
    val finalCandidatelons = (prodCandidatelonsFinalPos ++ elonxpCandidatelonsFinalPos).sortBy(_._2).map(_._1)

    // Welon count thelon prelonselonncelon of elonach rankelonr in thelon top-3 final positions.
    finalCandidatelons.zip(0 until 3).forelonach {
      caselon (c, r) =>
        // Welon only do so for candidatelons that arelon in a producelonr-sidelon elonxpelonrimelonnt.
        if (c.scorelons.elonxists(_.isInProducelonrScoringelonxpelonrimelonnt))
          c.scorelons.flatMap(_.selonlelonctelondRankelonrId).map(_.toString).forelonach { rankelonrNamelon =>
            intelonrlelonavingStats
              .countelonr(s"num_final_position_${r}_$rankelonrNamelon")
              .incr()
          }
    }

    finalCandidatelons
  }

  @VisiblelonForTelonsting
  privatelon[intelonrlelonavelon_rankelonr] delonf relonsolvelonConflicts(
    candidatelonsWithRank: Selonq[(CandidatelonUselonr, Int)]
  ): Selonq[(CandidatelonUselonr, Int)] = {
    // Thelon following two melontrics will allow us to calculatelon thelon ratelon of conflicts occurring.
    // elonxamplelon: If ovelonrall thelonrelon arelon 10 producelonrs in diffelonrelonnt buckelonting elonxpelonrimelonnts, and 3 of thelonm
    // arelon assignelond to thelon samelon position. Thelon ratelon would belon 3/10, 30%.
    val numCandidatelonsWithConflicts = intelonrlelonavingStats.countelonr("candidatelons_with_conflict")
    val numCandidatelonsNoConflicts = intelonrlelonavingStats.countelonr("candidatelons_without_conflict")
    val candidatelonsGroupelondByRank = candidatelonsWithRank.groupBy(_._2).toSelonq.sortBy(_._1).map {
      caselon (rank, candidatelonsWithRank) => (rank, candidatelonsWithRank.map(_._1))
    }

    candidatelonsGroupelondByRank.foldLelonft(Selonq[(CandidatelonUselonr, Int)]()) { (upToHelonrelon, nelonxtGroup) =>
      val (rank, candidatelons) = nelonxtGroup
      if (candidatelons.lelonngth > 1)
        numCandidatelonsWithConflicts.incr(candidatelons.lelonngth)
      elonlselon
        numCandidatelonsNoConflicts.incr()

      // Welon uselon thelon position aftelonr thelon last-assignelond candidatelon as a starting point, or 0 othelonrwiselon.
      // If candidatelons' position is aftelonr this "starting point", welon elonnforcelon that position instelonad.
      val minAvailablelonIndelonx = scala.math.max(upToHelonrelon.lastOption.map(_._2).gelontOrelonlselon(-1) + 1, rank)
      val elonnforcelondPos =
        (minAvailablelonIndelonx until minAvailablelonIndelonx + candidatelons.lelonngth).toList
      val shufflelondelonnforcelondPos =
        if (candidatelons.lelonngth > 1) scala.util.Random.shufflelon(elonnforcelondPos) elonlselon elonnforcelondPos
      if (shufflelondelonnforcelondPos.lelonngth > 1) {
        candidatelons.zip(shufflelondelonnforcelondPos).sortBy(_._2).map(_._1).zipWithIndelonx.forelonach {
          caselon (c, r) =>
            c.scorelons.flatMap(_.selonlelonctelondRankelonrId).map(_.toString).forelonach { rankelonrNamelon =>
              // For elonach rankelonr, welon count thelon total numbelonr of timelons it has belonelonn in a conflict.
              intelonrlelonavingStats
                .countelonr(s"num_${shufflelondelonnforcelondPos.lelonngth}-way_conflicts_$rankelonrNamelon")
                .incr()
              // Welon also count thelon positions elonach of thelon rankelonrs havelon fallelonn randomly into. In any
              // elonxpelonrimelonnt this should convelonrgelon to uniform distribution givelonn elonnough occurrelonncelons.
              // Notelon that thelon position helonrelon is relonlativelon to thelon othelonr candidatelons in thelon conflict and
              // not thelon ovelonrall position of elonach candidatelon.
              intelonrlelonavingStats
                .countelonr(
                  s"num_position_${r}_aftelonr_${shufflelondelonnforcelondPos.lelonngth}-way_conflict_$rankelonrNamelon")
                .incr()
            }
        }
      }
      upToHelonrelon ++ candidatelons.zip(shufflelondelonnforcelondPos).sortBy(_._2)
    }
  }

  @VisiblelonForTelonsting
  privatelon[intelonrlelonavelon_rankelonr] delonf gelontCandidatelonScorelonByRankelonrId(
    candidatelon: CandidatelonUselonr,
    rankelonrIdOpt: Option[RankelonrId.RankelonrId]
  ): Option[Doublelon] = {
    rankelonrIdOpt match {
      caselon Nonelon => Nonelon
      caselon Somelon(rankelonrId) =>
        candidatelon.scorelons.flatMap {
          _.scorelons.find(_.rankelonrId.contains(rankelonrId)).map(_.valuelon)
        }
    }
  }
}
