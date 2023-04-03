packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonOpelonration
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonelonntryContelonntMarshallelonr @Injelonct() (
  timelonlinelonItelonmMarshallelonr: TimelonlinelonItelonmMarshallelonr,
  timelonlinelonModulelonMarshallelonr: TimelonlinelonModulelonMarshallelonr,
  timelonlinelonOpelonrationMarshallelonr: TimelonlinelonOpelonrationMarshallelonr) {

  delonf apply(elonntry: Timelonlinelonelonntry): urt.TimelonlinelonelonntryContelonnt = elonntry match {
    caselon itelonm: TimelonlinelonItelonm =>
      urt.TimelonlinelonelonntryContelonnt.Itelonm(timelonlinelonItelonmMarshallelonr(itelonm))
    caselon modulelon: TimelonlinelonModulelon =>
      urt.TimelonlinelonelonntryContelonnt.TimelonlinelonModulelon(timelonlinelonModulelonMarshallelonr(modulelon))
    caselon opelonration: TimelonlinelonOpelonration =>
      urt.TimelonlinelonelonntryContelonnt.Opelonration(timelonlinelonOpelonrationMarshallelonr(opelonration))
  }
}
