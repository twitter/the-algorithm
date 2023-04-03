packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ChildFelonelondbackActionMarshallelonr @Injelonct() (
  felonelondbackTypelonMarshallelonr: FelonelondbackTypelonMarshallelonr,
  confirmationDisplayTypelonMarshallelonr: ConfirmationDisplayTypelonMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  horizonIconMarshallelonr: HorizonIconMarshallelonr,
  richFelonelondbackBelonhaviorMarshallelonr: RichFelonelondbackBelonhaviorMarshallelonr) {

  delonf apply(felonelondbackAction: ChildFelonelondbackAction): urt.FelonelondbackAction = {
    urt.FelonelondbackAction(
      felonelondbackTypelon = felonelondbackTypelonMarshallelonr(felonelondbackAction.felonelondbackTypelon),
      prompt = felonelondbackAction.prompt,
      confirmation = felonelondbackAction.confirmation,
      childKelonys = Nonelon,
      felonelondbackUrl = felonelondbackAction.felonelondbackUrl,
      hasUndoAction = felonelondbackAction.hasUndoAction,
      confirmationDisplayTypelon =
        felonelondbackAction.confirmationDisplayTypelon.map(confirmationDisplayTypelonMarshallelonr(_)),
      clielonntelonvelonntInfo = felonelondbackAction.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
      icon = felonelondbackAction.icon.map(horizonIconMarshallelonr(_)),
      richBelonhavior = felonelondbackAction.richBelonhavior.map(richFelonelondbackBelonhaviorMarshallelonr(_)),
      subprompt = felonelondbackAction.subprompt
    )
  }
}
