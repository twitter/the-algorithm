packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddelonntrielonsTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Build thelon Addelonntrielons instruction with speloncial handling for AddToModulelon elonntrielons.
 *
 * elonntrielons which arelon going to belon addelond to a modulelon arelon going to belon addelond via
 * AddToModulelonInstructionBuildelonr, for othelonr elonntrielons in thelon samelon relonsponselon (likelon cursor elonntrielons) welon
 * still nelonelond an AddelonntrielonsTimelonlinelonInstruction which is going to belon crelonatelond by this buildelonr.
 */
caselon class AddelonntrielonsWithAddToModulelonInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddelonntrielonsTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddelonntrielonsTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val elonntrielonsToAdd = elonntrielons.filtelonr {
        caselon _: TimelonlinelonModulelon => falselon
        caselon _ => truelon
      }
      if (elonntrielonsToAdd.nonelonmpty) Selonq(AddelonntrielonsTimelonlinelonInstruction(elonntrielonsToAdd))
      elonlselon Selonq.elonmpty
    } elonlselon
      Selonq.elonmpty
  }
}
