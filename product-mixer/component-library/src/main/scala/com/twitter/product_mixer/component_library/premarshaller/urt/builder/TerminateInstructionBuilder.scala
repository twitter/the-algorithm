packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.BottomTelonrmination
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TelonrminatelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonTelonrminationDirelonction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TopAndBottomTelonrmination
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TopTelonrmination
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

selonalelond trait TelonrminatelonInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry]
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, TelonrminatelonTimelonlinelonInstruction] {

  delonf direlonction: TimelonlinelonTelonrminationDirelonction

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[TelonrminatelonTimelonlinelonInstruction] =
    if (includelonInstruction(quelonry, elonntrielons))
      Selonq(TelonrminatelonTimelonlinelonInstruction(telonrminatelonTimelonlinelonDirelonction = direlonction))
    elonlselon Selonq.elonmpty
}

caselon class TelonrminatelonTopInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds TelonrminatelonInstructionBuildelonr[Quelonry] {

  ovelonrridelon val direlonction = TopTelonrmination
}

caselon class TelonrminatelonBottomInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds TelonrminatelonInstructionBuildelonr[Quelonry] {

  ovelonrridelon val direlonction = BottomTelonrmination
}

caselon class TelonrminatelonTopAndBottomInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds TelonrminatelonInstructionBuildelonr[Quelonry] {

  ovelonrridelon val direlonction = TopAndBottomTelonrmination
}
