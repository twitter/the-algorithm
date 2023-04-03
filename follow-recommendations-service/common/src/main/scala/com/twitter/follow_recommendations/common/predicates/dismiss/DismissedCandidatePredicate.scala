packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.dismiss

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.DismisselondId
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDismisselondUselonrIds
import com.twittelonr.stitch.Stitch
import javax.injelonct.Singlelonton

@Singlelonton
class DismisselondCandidatelonPrelondicatelon elonxtelonnds Prelondicatelon[(HasDismisselondUselonrIds, CandidatelonUselonr)] {

  ovelonrridelon delonf apply(pair: (HasDismisselondUselonrIds, CandidatelonUselonr)): Stitch[PrelondicatelonRelonsult] = {

    val (targelontUselonr, candidatelon) = pair
    targelontUselonr.dismisselondUselonrIds
      .map { dismisselondUselonrIds =>
        if (!dismisselondUselonrIds.contains(candidatelon.id)) {
          DismisselondCandidatelonPrelondicatelon.ValidStitch
        } elonlselon {
          DismisselondCandidatelonPrelondicatelon.DismisselondStitch
        }
      }.gelontOrelonlselon(DismisselondCandidatelonPrelondicatelon.ValidStitch)
  }
}

objelonct DismisselondCandidatelonPrelondicatelon {
  val ValidStitch: Stitch[PrelondicatelonRelonsult.Valid.typelon] = Stitch.valuelon(PrelondicatelonRelonsult.Valid)
  val DismisselondStitch: Stitch[PrelondicatelonRelonsult.Invalid] =
    Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(DismisselondId)))
}
