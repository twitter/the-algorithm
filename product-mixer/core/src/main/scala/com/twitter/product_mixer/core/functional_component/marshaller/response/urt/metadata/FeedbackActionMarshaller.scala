packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.FelonelondbackActionMarshallelonr.gelonnelonratelonKelony
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct FelonelondbackActionMarshallelonr {
  delonf gelonnelonratelonKelony(felonelondbackAction: urt.FelonelondbackAction): String = {
    felonelondbackAction.hashCodelon.toString
  }
}

@Singlelonton
class FelonelondbackActionMarshallelonr @Injelonct() (
  childFelonelondbackActionMarshallelonr: ChildFelonelondbackActionMarshallelonr,
  felonelondbackTypelonMarshallelonr: FelonelondbackTypelonMarshallelonr,
  confirmationDisplayTypelonMarshallelonr: ConfirmationDisplayTypelonMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  horizonIconMarshallelonr: HorizonIconMarshallelonr,
  richFelonelondbackBelonhaviorMarshallelonr: RichFelonelondbackBelonhaviorMarshallelonr) {

  delonf apply(felonelondbackAction: FelonelondbackAction): urt.FelonelondbackAction = {
    val childKelonys = felonelondbackAction.childFelonelondbackActions
      .map(_.map { childFelonelondbackAction =>
        val urtChildFelonelondbackAction = childFelonelondbackActionMarshallelonr(childFelonelondbackAction)
        gelonnelonratelonKelony(urtChildFelonelondbackAction)
      })

    urt.FelonelondbackAction(
      felonelondbackTypelon = felonelondbackTypelonMarshallelonr(felonelondbackAction.felonelondbackTypelon),
      prompt = felonelondbackAction.prompt,
      confirmation = felonelondbackAction.confirmation,
      childKelonys = childKelonys,
      felonelondbackUrl = felonelondbackAction.felonelondbackUrl,
      hasUndoAction = felonelondbackAction.hasUndoAction,
      confirmationDisplayTypelon =
        felonelondbackAction.confirmationDisplayTypelon.map(confirmationDisplayTypelonMarshallelonr(_)),
      clielonntelonvelonntInfo = felonelondbackAction.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
      icon = felonelondbackAction.icon.map(horizonIconMarshallelonr(_)),
      richBelonhavior = felonelondbackAction.richBelonhavior.map(richFelonelondbackBelonhaviorMarshallelonr(_)),
      subprompt = felonelondbackAction.subprompt,
      elonncodelondFelonelondbackRelonquelonst = felonelondbackAction.elonncodelondFelonelondbackRelonquelonst
    )
  }
}
