packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Covelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Build Addelonntrielons instruction with speloncial handling for Covelonrs.
 *
 * Covelonr elonntrielons should belon collelonctelond and transformelond into ShowCovelonr instructions. Thelonselon should belon
 * filtelonrelond out of thelon Addelonntrielons instruction. Welon avoid doing this as part of thelon relongular
 * AddelonntrielonsInstructionBuildelonr beloncauselon covelonrs arelon uselond only uselond whelonn using a Flip Pipelonlinelon and
 * delonteloncting covelonr elonntrielons takelons linelonar timelon.
 */
caselon class AddelonntrielonsWithShowCovelonrInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {
  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val elonntrielonsToAdd = elonntrielons.filtelonrNot(_.isInstancelonOf[Covelonr])
      if (elonntrielonsToAdd.nonelonmpty) Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielonsToAdd)) elonlselon Selonq.elonmpty
    } elonlselon
      Selonq.elonmpty
  }
}
