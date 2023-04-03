packagelon com.twittelonr.follow_reloncommelonndations.selonrvicelons

import com.twittelonr.follow_reloncommelonndations.configapi.deloncidelonrs.DeloncidelonrParams
import com.twittelonr.follow_reloncommelonndations.logging.FrsLoggelonr
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonsponselon
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ReloncommelonndationsSelonrvicelon @Injelonct() (
  productReloncommelonndelonrSelonrvicelon: ProductReloncommelonndelonrSelonrvicelon,
  relonsultLoggelonr: FrsLoggelonr) {
  delonf gelont(relonquelonst: ReloncommelonndationRelonquelonst, params: Params): Stitch[ReloncommelonndationRelonsponselon] = {
    if (params(DeloncidelonrParams.elonnablelonReloncommelonndations)) {
      productReloncommelonndelonrSelonrvicelon
        .gelontReloncommelonndations(relonquelonst, params).map(ReloncommelonndationRelonsponselon).onSuccelonss { relonsponselon =>
          if (relonsultLoggelonr.shouldLog(relonquelonst.delonbugParams)) {
            relonsultLoggelonr.logReloncommelonndationRelonsult(relonquelonst, relonsponselon)
          }
        }
    } elonlselon {
      Stitch.valuelon(ReloncommelonndationRelonsponselon(Nil))
    }
  }
}
