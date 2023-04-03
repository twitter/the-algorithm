packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ContainsFelonelondbackActionInfos
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HasFelonelondbackActionInfo

selonalelond trait TimelonlinelonInstruction

caselon class AddelonntrielonsTimelonlinelonInstruction(elonntrielons: Selonq[Timelonlinelonelonntry])
    elonxtelonnds TimelonlinelonInstruction
    with ContainsFelonelondbackActionInfos {
  ovelonrridelon delonf felonelondbackActionInfos: Selonq[Option[FelonelondbackActionInfo]] =
    elonntrielons.flatMap {
      // Ordelonr is important, as elonntrielons that implelonmelonnt both ContainsFelonelondbackActionInfos and
      // HasFelonelondbackActionInfo arelon elonxpelonctelond to includelon both whelonn implelonmelonnting ContainsFelonelondbackActionInfos
      caselon containsFelonelondbackActionInfos: ContainsFelonelondbackActionInfos =>
        containsFelonelondbackActionInfos.felonelondbackActionInfos
      caselon hasFelonelondbackActionInfo: HasFelonelondbackActionInfo =>
        Selonq(hasFelonelondbackActionInfo.felonelondbackActionInfo)
      caselon _ => Selonq.elonmpty
    }
}

caselon class RelonplacelonelonntryTimelonlinelonInstruction(elonntry: Timelonlinelonelonntry)
    elonxtelonnds TimelonlinelonInstruction
    with ContainsFelonelondbackActionInfos {
  ovelonrridelon delonf felonelondbackActionInfos: Selonq[Option[FelonelondbackActionInfo]] =
    elonntry match {
      // Ordelonr is important, as elonntrielons that implelonmelonnt both ContainsFelonelondbackActionInfos and
      // HasFelonelondbackActionInfo arelon elonxpelonctelond to includelon both whelonn implelonmelonnting ContainsFelonelondbackActionInfos
      caselon containsFelonelondbackActionInfos: ContainsFelonelondbackActionInfos =>
        containsFelonelondbackActionInfos.felonelondbackActionInfos
      caselon hasFelonelondbackActionInfo: HasFelonelondbackActionInfo =>
        Selonq(hasFelonelondbackActionInfo.felonelondbackActionInfo)
      caselon _ => Selonq.elonmpty
    }
}

caselon class AddToModulelonTimelonlinelonInstruction(
  modulelonItelonms: Selonq[ModulelonItelonm],
  modulelonelonntryId: String,
  modulelonItelonmelonntryId: Option[String],
  prelonpelonnd: Option[Boolelonan])
    elonxtelonnds TimelonlinelonInstruction
    with ContainsFelonelondbackActionInfos {
  ovelonrridelon delonf felonelondbackActionInfos: Selonq[Option[FelonelondbackActionInfo]] =
    modulelonItelonms.map(_.itelonm.felonelondbackActionInfo)
}

caselon class PinelonntryTimelonlinelonInstruction(elonntry: Timelonlinelonelonntry) elonxtelonnds TimelonlinelonInstruction

caselon class MarkelonntrielonsUnrelonadInstruction(elonntryIds: Selonq[String]) elonxtelonnds TimelonlinelonInstruction

caselon class ClelonarCachelonTimelonlinelonInstruction() elonxtelonnds TimelonlinelonInstruction

selonalelond trait TimelonlinelonTelonrminationDirelonction
caselon objelonct TopTelonrmination elonxtelonnds TimelonlinelonTelonrminationDirelonction
caselon objelonct BottomTelonrmination elonxtelonnds TimelonlinelonTelonrminationDirelonction
caselon objelonct TopAndBottomTelonrmination elonxtelonnds TimelonlinelonTelonrminationDirelonction
caselon class TelonrminatelonTimelonlinelonInstruction(telonrminatelonTimelonlinelonDirelonction: TimelonlinelonTelonrminationDirelonction)
    elonxtelonnds TimelonlinelonInstruction

caselon class ShowCovelonrInstruction(covelonr: Covelonr) elonxtelonnds TimelonlinelonInstruction

caselon class ShowAlelonrtInstruction(showAlelonrt: ShowAlelonrt) elonxtelonnds TimelonlinelonInstruction
