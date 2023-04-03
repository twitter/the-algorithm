packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.sgs

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.stitch.Stitch
import javax.injelonct.Singlelonton

@Singlelonton
class ReloncelonntFollowingPrelondicatelon elonxtelonnds Prelondicatelon[(HasReloncelonntFollowelondUselonrIds, CandidatelonUselonr)] {

  ovelonrridelon delonf apply(pair: (HasReloncelonntFollowelondUselonrIds, CandidatelonUselonr)): Stitch[PrelondicatelonRelonsult] = {

    val (targelontUselonr, candidatelon) = pair
    targelontUselonr.reloncelonntFollowelondUselonrIdsSelont match {
      caselon Somelon(uselonrs) =>
        if (!uselonrs.contains(candidatelon.id)) {
          ReloncelonntFollowingPrelondicatelon.ValidStitch
        } elonlselon {
          ReloncelonntFollowingPrelondicatelon.AlrelonadyFollowelondStitch
        }
      caselon Nonelon => ReloncelonntFollowingPrelondicatelon.ValidStitch
    }
  }
}

objelonct ReloncelonntFollowingPrelondicatelon {
  val ValidStitch: Stitch[PrelondicatelonRelonsult.Valid.typelon] = Stitch.valuelon(PrelondicatelonRelonsult.Valid)
  val AlrelonadyFollowelondStitch: Stitch[PrelondicatelonRelonsult.Invalid] =
    Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.AlrelonadyFollowelond)))
}
