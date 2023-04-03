packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AddelonntrielonsInstructionMarshallelonr @Injelonct() (
  timelonlinelonelonntryMarshallelonr: TimelonlinelonelonntryMarshallelonr) {

  delonf apply(instruction: AddelonntrielonsTimelonlinelonInstruction): urt.Addelonntrielons = urt.Addelonntrielons(
    elonntrielons = instruction.elonntrielons.map(timelonlinelonelonntryMarshallelonr(_))
  )
}
