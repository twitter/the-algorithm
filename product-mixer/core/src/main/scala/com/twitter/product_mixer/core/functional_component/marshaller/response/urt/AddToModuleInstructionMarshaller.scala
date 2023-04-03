packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddToModulelonTimelonlinelonInstruction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AddToModulelonInstructionMarshallelonr @Injelonct() (modulelonItelonmMarshallelonr: ModulelonItelonmMarshallelonr) {

  delonf apply(instruction: AddToModulelonTimelonlinelonInstruction): urt.AddToModulelon = urt.AddToModulelon(
    modulelonItelonms = instruction.modulelonItelonms.map(modulelonItelonmMarshallelonr(_, instruction.modulelonelonntryId)),
    modulelonelonntryId = instruction.modulelonelonntryId,
    modulelonItelonmelonntryId = instruction.modulelonItelonmelonntryId,
    prelonpelonnd = instruction.prelonpelonnd
  )
}
