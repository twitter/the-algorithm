packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrtInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class ShowAlelonrtInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, ShowAlelonrtInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[ShowAlelonrtInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      // Currelonntly only onelon Alelonrt is supportelond pelonr relonsponselon
      elonntrielons.collelonctFirst {
        caselon alelonrtelonntry: ShowAlelonrt => ShowAlelonrtInstruction(alelonrtelonntry)
      }.toSelonq
    } elonlselon Selonq.elonmpty
  }
}
