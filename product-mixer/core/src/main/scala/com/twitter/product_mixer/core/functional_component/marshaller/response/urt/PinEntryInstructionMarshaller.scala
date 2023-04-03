packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.PinelonntryTimelonlinelonInstruction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PinelonntryInstructionMarshallelonr @Injelonct() (
  timelonlinelonelonntryMarshallelonr: TimelonlinelonelonntryMarshallelonr) {

  delonf apply(instruction: PinelonntryTimelonlinelonInstruction): urt.Pinelonntry = {
    urt.Pinelonntry(elonntry = timelonlinelonelonntryMarshallelonr(instruction.elonntry))
  }
}
