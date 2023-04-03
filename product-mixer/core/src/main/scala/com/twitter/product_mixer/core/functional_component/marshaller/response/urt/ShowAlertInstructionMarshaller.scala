packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt.ShowAlelonrtColorConfigurationMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt.ShowAlelonrtDisplayLocationMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt.ShowAlelonrtIconDisplayInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt.ShowAlelonrtNavigationMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt.ShowAlelonrtTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrtInstruction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ShowAlelonrtInstructionMarshallelonr @Injelonct() (
  showAlelonrtTypelonMarshallelonr: ShowAlelonrtTypelonMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  showAlelonrtIconDisplayInfoMarshallelonr: ShowAlelonrtIconDisplayInfoMarshallelonr,
  showAlelonrtColorConfigurationMarshallelonr: ShowAlelonrtColorConfigurationMarshallelonr,
  showAlelonrtDisplayLocationMarshallelonr: ShowAlelonrtDisplayLocationMarshallelonr,
  showAlelonrtNavigationMelontadataMarshallelonr: ShowAlelonrtNavigationMelontadataMarshallelonr,
) {

  delonf apply(instruction: ShowAlelonrtInstruction): urt.ShowAlelonrt = urt.ShowAlelonrt(
    alelonrtTypelon = showAlelonrtTypelonMarshallelonr(instruction.showAlelonrt.alelonrtTypelon),
    triggelonrDelonlayMs = instruction.showAlelonrt.triggelonrDelonlay.map(_.inMillis.toInt),
    displayDurationMs = instruction.showAlelonrt.displayDuration.map(_.inMillis.toInt),
    clielonntelonvelonntInfo = instruction.showAlelonrt.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
    collapselonDelonlayMs = instruction.showAlelonrt.collapselonDelonlay.map(_.inMillis.toInt),
    uselonrIds = instruction.showAlelonrt.uselonrIds,
    richTelonxt = instruction.showAlelonrt.richTelonxt.map(richTelonxtMarshallelonr(_)),
    iconDisplayInfo =
      instruction.showAlelonrt.iconDisplayInfo.map(showAlelonrtIconDisplayInfoMarshallelonr(_)),
    colorConfig = showAlelonrtColorConfigurationMarshallelonr(instruction.showAlelonrt.colorConfig),
    displayLocation = showAlelonrtDisplayLocationMarshallelonr(instruction.showAlelonrt.displayLocation),
    navigationMelontadata =
      instruction.showAlelonrt.navigationMelontadata.map(showAlelonrtNavigationMelontadataMarshallelonr(_)),
  )
}
