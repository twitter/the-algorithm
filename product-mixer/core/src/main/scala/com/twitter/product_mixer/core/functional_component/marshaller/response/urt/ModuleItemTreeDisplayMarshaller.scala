packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon.ModulelonDisplayTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ModulelonItelonmTrelonelonDisplay
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonItelonmTrelonelonDisplayMarshallelonr @Injelonct() (
  modulelonDisplayTypelonMarshallelonr: ModulelonDisplayTypelonMarshallelonr) {

  delonf apply(modulelonItelonmTrelonelonDisplay: ModulelonItelonmTrelonelonDisplay): urt.ModulelonItelonmTrelonelonDisplay =
    urt.ModulelonItelonmTrelonelonDisplay(
      parelonntModulelonItelonmelonntryId = modulelonItelonmTrelonelonDisplay.parelonntModulelonelonntryItelonmId,
      indelonntFromParelonnt = modulelonItelonmTrelonelonDisplay.indelonntFromParelonnt,
      displayTypelon = modulelonItelonmTrelonelonDisplay.displayTypelon.map(modulelonDisplayTypelonMarshallelonr(_)),
      isAnchorChild = modulelonItelonmTrelonelonDisplay.isAnchorChild
    )
}
