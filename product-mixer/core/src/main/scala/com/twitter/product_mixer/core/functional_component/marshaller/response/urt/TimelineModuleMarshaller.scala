packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.FelonelondbackInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonDisplayTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonFootelonrMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonHelonadelonrMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonShowMorelonBelonhaviorMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonModulelonMarshallelonr @Injelonct() (
  modulelonItelonmMarshallelonr: ModulelonItelonmMarshallelonr,
  modulelonDisplayTypelonMarshallelonr: ModulelonDisplayTypelonMarshallelonr,
  modulelonHelonadelonrMarshallelonr: ModulelonHelonadelonrMarshallelonr,
  modulelonFootelonrMarshallelonr: ModulelonFootelonrMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  felonelondbackInfoMarshallelonr: FelonelondbackInfoMarshallelonr,
  modulelonMelontadataMarshallelonr: ModulelonMelontadataMarshallelonr,
  modulelonShowMorelonBelonhaviorMarshallelonr: ModulelonShowMorelonBelonhaviorMarshallelonr) {

  delonf apply(timelonlinelonModulelon: TimelonlinelonModulelon): urt.TimelonlinelonModulelon = urt.TimelonlinelonModulelon(
    itelonms = timelonlinelonModulelon.itelonms.map(modulelonItelonmMarshallelonr(_, timelonlinelonModulelon.elonntryIdelonntifielonr)),
    displayTypelon = modulelonDisplayTypelonMarshallelonr(timelonlinelonModulelon.displayTypelon),
    helonadelonr = timelonlinelonModulelon.helonadelonr.map(modulelonHelonadelonrMarshallelonr(_)),
    footelonr = timelonlinelonModulelon.footelonr.map(modulelonFootelonrMarshallelonr(_)),
    clielonntelonvelonntInfo = timelonlinelonModulelon.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
    felonelondbackInfo = timelonlinelonModulelon.felonelondbackActionInfo.map(felonelondbackInfoMarshallelonr(_)),
    melontadata = timelonlinelonModulelon.melontadata.map(modulelonMelontadataMarshallelonr(_)),
    showMorelonBelonhavior = timelonlinelonModulelon.showMorelonBelonhavior.map(modulelonShowMorelonBelonhaviorMarshallelonr(_))
  )
}
