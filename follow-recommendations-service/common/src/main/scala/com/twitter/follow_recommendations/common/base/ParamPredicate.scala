packagelon com.twittelonr.follow_reloncommelonndations.common.baselon

import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.ParamRelonason
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Param

caselon class ParamPrelondicatelon[Relonquelonst <: HasParams](param: Param[Boolelonan]) elonxtelonnds Prelondicatelon[Relonquelonst] {

  delonf apply(relonquelonst: Relonquelonst): Stitch[PrelondicatelonRelonsult] = {
    if (relonquelonst.params(param)) {
      Stitch.valuelon(PrelondicatelonRelonsult.Valid)
    } elonlselon {
      Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(ParamRelonason(param.statNamelon))))
    }
  }
}
