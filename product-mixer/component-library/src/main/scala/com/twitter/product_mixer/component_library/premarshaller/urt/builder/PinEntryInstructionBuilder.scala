packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.PinelonntryTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Pinnablelonelonntry

caselon class PinelonntryInstructionBuildelonr()
    elonxtelonnds UrtInstructionBuildelonr[PipelonlinelonQuelonry, PinelonntryTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: PipelonlinelonQuelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[PinelonntryTimelonlinelonInstruction] = {
    // Only onelon elonntry can belon pinnelond and thelon delonsirablelon belonhavior is to pick thelon elonntry with thelon highelonst
    // sort indelonx in thelon elonvelonnt that multiplelon pinnelond itelonms elonxist. Sincelon thelon elonntrielons arelon alrelonady
    // sortelond welon can accomplish this by picking thelon first onelon.
    elonntrielons.collelonctFirst {
      caselon elonntry: Pinnablelonelonntry if elonntry.isPinnelond.gelontOrelonlselon(falselon) =>
        PinelonntryTimelonlinelonInstruction(elonntry)
    }.toSelonq
  }
}
