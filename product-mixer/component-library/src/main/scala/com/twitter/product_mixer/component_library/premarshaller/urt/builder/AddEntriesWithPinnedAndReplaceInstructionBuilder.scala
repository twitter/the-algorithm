packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Itelonratelons ovelonr all thelon [[Timelonlinelonelonntry]] passelond it and crelonatelons `addelonntry` elonntrielons in thelon URT for
 * any elonntrielons which arelon not pinnelond and not relonplacelonablelon(cursors arelon relonplacelonablelon)
 *
 * This is beloncauselon pinnelond elonntrielons always show up in thelon `pinelonntry` selonction, and relonplacelonablelon elonntrielons
 * will show up in thelon `relonplacelonelonntry` selonction.
 */
caselon class AddelonntrielonsWithPinnelondAndRelonplacelonInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val elonntrielonsToAdd = elonntrielons
        .filtelonrNot(_.isPinnelond.gelontOrelonlselon(falselon))
        .filtelonr(_.elonntryIdToRelonplacelon.iselonmpty)
      if (elonntrielonsToAdd.nonelonmpty) Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielonsToAdd))
      elonlselon Selonq.elonmpty
    } elonlselon
      Selonq.elonmpty
  }
}
