packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasPrelonviousReloncommelonndationsContelonxt
import com.twittelonr.stitch.Stitch
import javax.injelonct.Singlelonton

@Singlelonton
class PrelonviouslyReloncommelonndelondUselonrIdsPrelondicatelon
    elonxtelonnds Prelondicatelon[(HasPrelonviousReloncommelonndationsContelonxt, CandidatelonUselonr)] {
  ovelonrridelon delonf apply(
    pair: (HasPrelonviousReloncommelonndationsContelonxt, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {

    val (targelontUselonr, candidatelon) = pair

    val prelonviouslyReloncommelonndelondUselonrIDs = targelontUselonr.prelonviouslyReloncommelonndelondUselonrIDs

    if (!prelonviouslyReloncommelonndelondUselonrIDs.contains(candidatelon.id)) {
      PrelonviouslyReloncommelonndelondUselonrIdsPrelondicatelon.ValidStitch
    } elonlselon {
      PrelonviouslyReloncommelonndelondUselonrIdsPrelondicatelon.AlrelonadyReloncommelonndelondStitch
    }
  }
}

objelonct PrelonviouslyReloncommelonndelondUselonrIdsPrelondicatelon {
  val ValidStitch: Stitch[PrelondicatelonRelonsult.Valid.typelon] = Stitch.valuelon(PrelondicatelonRelonsult.Valid)
  val AlrelonadyReloncommelonndelondStitch: Stitch[PrelondicatelonRelonsult.Invalid] =
    Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.AlrelonadyReloncommelonndelond)))
}
