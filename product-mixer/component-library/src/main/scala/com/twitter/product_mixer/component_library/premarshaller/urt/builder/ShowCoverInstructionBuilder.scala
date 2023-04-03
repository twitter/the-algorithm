packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowCovelonrInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Covelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class ShowCovelonrInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, ShowCovelonrInstruction] {
  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[ShowCovelonrInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      // Currelonntly only onelon covelonr is supportelond pelonr relonsponselon
      elonntrielons.collelonctFirst {
        caselon covelonrelonntry: Covelonr => ShowCovelonrInstruction(covelonrelonntry)
      }.toSelonq
    } elonlselon {
      Selonq.elonmpty
    }
  }
}
