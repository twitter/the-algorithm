packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Build thelon Addelonntrielons instruction with speloncial handling for relonplacelonablelon elonntrielons.
 *
 * elonntrielons (though almost always a singlelon elonntry) with a non-elonmpty elonntryIdToRelonplacelon fielonld should belon
 * collelonctelond and transformelond into Relonplacelonelonntry instructions. Thelonselon should belon filtelonrelond out of thelon
 * Addelonntrielons instruction. Welon avoid doing this as part of thelon relongular AddelonntrielonsInstructionBuildelonr
 * beloncauselon relonplacelonmelonnt is rarelon and delonteloncting relonplacelonablelon elonntrielons takelons linelonar timelon.
 */
caselon class AddelonntrielonsWithRelonplacelonInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val elonntrielonsToAdd = elonntrielons.filtelonr(_.elonntryIdToRelonplacelon.iselonmpty)
      if (elonntrielonsToAdd.nonelonmpty) Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielonsToAdd))
      elonlselon Selonq.elonmpty
    } elonlselon {
      Selonq.elonmpty
    }
  }
}
