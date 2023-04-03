packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.sgs

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasInvalidRelonlationshipUselonrIds
import com.twittelonr.stitch.Stitch
import javax.injelonct.Singlelonton

@Singlelonton
class InvalidRelonlationshipPrelondicatelon
    elonxtelonnds Prelondicatelon[(HasInvalidRelonlationshipUselonrIds, CandidatelonUselonr)] {

  ovelonrridelon delonf apply(
    pair: (HasInvalidRelonlationshipUselonrIds, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {

    val (targelontUselonr, candidatelon) = pair
    targelontUselonr.invalidRelonlationshipUselonrIds match {
      caselon Somelon(uselonrs) =>
        if (!uselonrs.contains(candidatelon.id)) {
          InvalidRelonlationshipPrelondicatelon.ValidStitch
        } elonlselon {
          Stitch.valuelon(InvalidRelonlationshipPrelondicatelon.InvalidRelonlationshipStitch)
        }
      caselon Nonelon => Stitch.valuelon(PrelondicatelonRelonsult.Valid)
    }
  }
}

objelonct InvalidRelonlationshipPrelondicatelon {
  val ValidStitch: Stitch[PrelondicatelonRelonsult.Valid.typelon] = Stitch.valuelon(PrelondicatelonRelonsult.Valid)
  val InvalidRelonlationshipStitch: PrelondicatelonRelonsult.Invalid =
    PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.InvalidRelonlationship))
}
