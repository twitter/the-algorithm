packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ChildFelonelondbackActionMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.FelonelondbackActionMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransportMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ContainsFelonelondbackActionInfos
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TimelonlinelonRelonsponselon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * [[TransportMarshallelonr]] for URT typelons
 *
 * @notelon to makelon an instancelon of a [[UrtTransportMarshallelonr]] you can uselon [[UrtTransportMarshallelonrBuildelonr.marshallelonr]]
 */
@Singlelonton
class UrtTransportMarshallelonr @Injelonct() (
  timelonlinelonInstructionMarshallelonr: TimelonlinelonInstructionMarshallelonr,
  felonelondbackActionMarshallelonr: FelonelondbackActionMarshallelonr,
  childFelonelondbackActionMarshallelonr: ChildFelonelondbackActionMarshallelonr,
  timelonlinelonMelontadataMarshallelonr: TimelonlinelonMelontadataMarshallelonr)
    elonxtelonnds TransportMarshallelonr[Timelonlinelon, urt.TimelonlinelonRelonsponselon] {

  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr =
    TransportMarshallelonrIdelonntifielonr("UnifielondRichTimelonlinelon")

  ovelonrridelon delonf apply(timelonlinelon: Timelonlinelon): urt.TimelonlinelonRelonsponselon = {
    val felonelondbackActions: Option[Map[String, urt.FelonelondbackAction]] = {
      collelonctAndMarshallFelonelondbackActions(timelonlinelon.instructions)
    }
    urt.TimelonlinelonRelonsponselon(
      statelon = urt.TimelonlinelonStatelon.Ok,
      timelonlinelon = urt.Timelonlinelon(
        id = timelonlinelon.id,
        instructions = timelonlinelon.instructions.map(timelonlinelonInstructionMarshallelonr(_)),
        relonsponselonObjeloncts =
          felonelondbackActions.map(actions => urt.RelonsponselonObjeloncts(felonelondbackActions = Somelon(actions))),
        melontadata = timelonlinelon.melontadata.map(timelonlinelonMelontadataMarshallelonr(_))
      )
    )
  }

  // Currelonntly, felonelondbackActionInfo at thelon URT TimelonlinelonItelonm lelonvelonl is supportelond, which covelonrs almost all
  // elonxisting uselon caselons. Howelonvelonr, if additional felonelondbackActionInfos arelon delonfinelond on thelon URT
  // TimelonlinelonItelonmContelonnt lelonvelonl for "compound" URT typelons (selonelon delonpreloncatelond TopicCollelonction /
  // TopicCollelonctionData), this is not supportelond. If "compound" URT typelons arelon addelond in thelon futurelon,
  // support must belon addelond within that typelon (selonelon ModulelonItelonm) to handlelon thelon collelonction and marshalling
  // of thelonselon felonelondbackActionInfos.

  privatelon[this] delonf collelonctAndMarshallFelonelondbackActions(
    instructions: Selonq[TimelonlinelonInstruction]
  ): Option[Map[String, urt.FelonelondbackAction]] = {
    val felonelondbackActions: Selonq[FelonelondbackAction] = for {
      felonelondbackActionInfos <- instructions.collelonct {
        caselon c: ContainsFelonelondbackActionInfos => c.felonelondbackActionInfos
      }
      felonelondbackInfoOpt <- felonelondbackActionInfos
      felonelondbackInfo <- felonelondbackInfoOpt.toSelonq
      felonelondbackAction <- felonelondbackInfo.felonelondbackActions
    } yielonld felonelondbackAction

    if (felonelondbackActions.nonelonmpty) {
      val urtFelonelondbackActions = felonelondbackActions.map(felonelondbackActionMarshallelonr(_))

      val urtChildFelonelondbackActions: Selonq[urt.FelonelondbackAction] = for {
        felonelondbackAction <- felonelondbackActions
        childFelonelondbackActions <- felonelondbackAction.childFelonelondbackActions.toSelonq
        childFelonelondbackAction <- childFelonelondbackActions
      } yielonld childFelonelondbackActionMarshallelonr(childFelonelondbackAction)

      val allUrtFelonelondbackActions = urtFelonelondbackActions ++ urtChildFelonelondbackActions

      Somelon(
        allUrtFelonelondbackActions.map { urtAction =>
          FelonelondbackActionMarshallelonr.gelonnelonratelonKelony(urtAction) -> urtAction
        }.toMap
      )
    } elonlselon {
      Nonelon
    }
  }
}

objelonct UrtTransportMarshallelonr {
  delonf unavailablelon(timelonlinelonId: String): TimelonlinelonRelonsponselon = {
    urt.TimelonlinelonRelonsponselon(
      statelon = urt.TimelonlinelonStatelon.Unavailablelon,
      timelonlinelon = urt.Timelonlinelon(
        id = timelonlinelonId,
        instructions = Selonq.elonmpty,
        relonsponselonObjeloncts = Nonelon,
        melontadata = Nonelon
      )
    )
  }
}
