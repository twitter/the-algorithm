packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.elonxcludelondId
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HaselonxcludelondUselonrIds
import com.twittelonr.stitch.Stitch

objelonct elonxcludelondUselonrIdPrelondicatelon elonxtelonnds Prelondicatelon[(HaselonxcludelondUselonrIds, CandidatelonUselonr)] {

  val ValidStitch: Stitch[PrelondicatelonRelonsult.Valid.typelon] = Stitch.valuelon(PrelondicatelonRelonsult.Valid)
  val elonxcludelondStitch: Stitch[PrelondicatelonRelonsult.Invalid] =
    Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(elonxcludelondId)))

  ovelonrridelon delonf apply(pair: (HaselonxcludelondUselonrIds, CandidatelonUselonr)): Stitch[PrelondicatelonRelonsult] = {
    val (elonxcludelondUselonrIds, candidatelon) = pair
    if (elonxcludelondUselonrIds.elonxcludelondUselonrIds.contains(candidatelon.id)) {
      elonxcludelondStitch
    } elonlselon {
      ValidStitch
    }
  }
}
