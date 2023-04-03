packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.AddToModulelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class AddToModulelonInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, AddToModulelonTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[AddToModulelonTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons)) {
      val modulelonelonntrielons = elonntrielons.collelonct {
        caselon modulelon: TimelonlinelonModulelon => modulelon
      }
      if (modulelonelonntrielons.nonelonmpty) {
        asselonrt(modulelonelonntrielons.sizelon == 1, "Currelonntly welon only support appelonnding to onelon modulelon")
        modulelonelonntrielons.helonadOption.map { modulelonelonntry =>
          AddToModulelonTimelonlinelonInstruction(
            modulelonItelonms = modulelonelonntry.itelonms,
            modulelonelonntryId = modulelonelonntry.elonntryIdelonntifielonr,
            // Currelonntly configuring modulelonItelonmelonntryId and prelonpelonnd fielonlds arelon not supportelond.
            modulelonItelonmelonntryId = Nonelon,
            prelonpelonnd = Nonelon
          )
        }
      }.toSelonq
      elonlselon Selonq.elonmpty
    } elonlselon {
      Selonq.elonmpty
    }
  }
}
