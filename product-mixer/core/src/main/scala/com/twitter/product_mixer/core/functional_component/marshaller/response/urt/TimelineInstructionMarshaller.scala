packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddToModulelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ClelonarCachelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.MarkelonntrielonsUnrelonadInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.PinelonntryTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.RelonplacelonelonntryTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrtInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowCovelonrInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TelonrminatelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonInstruction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonInstructionMarshallelonr @Injelonct() (
  addelonntrielonsInstructionMarshallelonr: AddelonntrielonsInstructionMarshallelonr,
  addToModulelonInstructionMarshallelonr: AddToModulelonInstructionMarshallelonr,
  markelonntrielonsUnrelonadInstructionMarshallelonr: MarkelonntrielonsUnrelonadInstructionMarshallelonr,
  pinelonntryInstructionMarshallelonr: PinelonntryInstructionMarshallelonr,
  relonplacelonelonntryInstructionMarshallelonr: RelonplacelonelonntryInstructionMarshallelonr,
  showAlelonrtInstructionMarshallelonr: ShowAlelonrtInstructionMarshallelonr,
  telonrminatelonTimelonlinelonInstructionMarshallelonr: TelonrminatelonTimelonlinelonInstructionMarshallelonr,
  covelonrMarshallelonr: CovelonrMarshallelonr) {

  delonf apply(instruction: TimelonlinelonInstruction): urt.TimelonlinelonInstruction = instruction match {
    caselon instruction: AddelonntrielonsTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.Addelonntrielons(addelonntrielonsInstructionMarshallelonr(instruction))
    caselon instruction: AddToModulelonTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.AddToModulelon(addToModulelonInstructionMarshallelonr(instruction))
    caselon _: ClelonarCachelonTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.ClelonarCachelon(urt.ClelonarCachelon())
    caselon instruction: MarkelonntrielonsUnrelonadInstruction =>
      urt.TimelonlinelonInstruction.MarkelonntrielonsUnrelonad(
        markelonntrielonsUnrelonadInstructionMarshallelonr(instruction)
      )
    caselon instruction: PinelonntryTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.Pinelonntry(pinelonntryInstructionMarshallelonr(instruction))
    caselon instruction: RelonplacelonelonntryTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.Relonplacelonelonntry(relonplacelonelonntryInstructionMarshallelonr(instruction))
    caselon instruction: ShowCovelonrInstruction =>
      urt.TimelonlinelonInstruction.ShowCovelonr(covelonrMarshallelonr(instruction.covelonr))
    caselon instruction: ShowAlelonrtInstruction =>
      urt.TimelonlinelonInstruction.ShowAlelonrt(showAlelonrtInstructionMarshallelonr(instruction))
    caselon instruction: TelonrminatelonTimelonlinelonInstruction =>
      urt.TimelonlinelonInstruction.TelonrminatelonTimelonlinelon(telonrminatelonTimelonlinelonInstructionMarshallelonr(instruction))
  }
}
