packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.utils

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Scorelon
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId.RankelonrId

objelonct Utils {

  /**
   * Add thelon ranking and scoring info for a list of candidatelons on a givelonn ranking stagelon.
   * @param candidatelons A list of CandidatelonUselonr
   * @param rankingStagelon Should uselon `Rankelonr.namelon` as thelon ranking stagelon.
   * @relonturn Thelon list of CandidatelonUselonr with ranking/scoring info addelond.
   */
  delonf addRankingInfo(candidatelons: Selonq[CandidatelonUselonr], rankingStagelon: String): Selonq[CandidatelonUselonr] = {
    candidatelons.zipWithIndelonx.map {
      caselon (candidatelon, rank) =>
        // 1-baselond ranking for belonttelonr relonadability
        candidatelon.addInfoPelonrRankingStagelon(rankingStagelon, candidatelon.scorelons, rank + 1)
    }
  }

  delonf gelontCandidatelonScorelonByRankelonrId(candidatelon: CandidatelonUselonr, rankelonrId: RankelonrId): Option[Scorelon] =
    candidatelon.scorelons.flatMap { ss => ss.scorelons.find(_.rankelonrId.contains(rankelonrId)) }

  delonf gelontAllRankelonrIds(candidatelons: Selonq[CandidatelonUselonr]): Selonq[RankelonrId] =
    candidatelons.flatMap(_.scorelons.map(_.scorelons.flatMap(_.rankelonrId))).flattelonn.distinct
}
