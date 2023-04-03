packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Param

class CandidatelonParamPrelondicatelon[A <: HasParams](
  param: Param[Boolelonan],
  relonason: FiltelonrRelonason)
    elonxtelonnds Prelondicatelon[A] {
  ovelonrridelon delonf apply(candidatelon: A): Stitch[PrelondicatelonRelonsult] = {
    if (candidatelon.params(param)) {
      Stitch.valuelon(PrelondicatelonRelonsult.Valid)
    } elonlselon {
      Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(relonason)))
    }
  }
}
