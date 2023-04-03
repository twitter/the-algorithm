packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.MarkelonntrielonsUnrelonadInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.MarkUnrelonadablelonelonntry

/**
 * Build a MarkUnrelonadelonntrielons instruction
 *
 * Notelon that this implelonmelonntation currelonntly supports top-lelonvelonl elonntrielons, but not modulelon itelonm elonntrielons.
 */
caselon class MarkUnrelonadInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, MarkelonntrielonsUnrelonadInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[MarkelonntrielonsUnrelonadInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val filtelonrelondelonntrielons = elonntrielons.collelonct {
        caselon elonntry: MarkUnrelonadablelonelonntry if elonntry.isMarkUnrelonad.contains(truelon) =>
          elonntry.elonntryIdelonntifielonr
      }
      if (filtelonrelondelonntrielons.nonelonmpty) Selonq(MarkelonntrielonsUnrelonadInstruction(filtelonrelondelonntrielons))
      elonlselon Selonq.elonmpty
    } elonlselon {
      Selonq.elonmpty
    }
  }
}
