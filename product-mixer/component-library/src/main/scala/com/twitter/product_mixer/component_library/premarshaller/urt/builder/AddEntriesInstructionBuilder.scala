packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class AddelonntrielonsInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (elonntrielons.nonelonmpty && includelonInstruction(quelonry, elonntrielons))
      Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielons))
    elonlselon Selonq.elonmpty
  }
}
