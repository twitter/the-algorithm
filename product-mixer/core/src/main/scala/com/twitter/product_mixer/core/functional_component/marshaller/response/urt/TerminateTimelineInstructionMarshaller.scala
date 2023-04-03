packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.BottomTelonrmination
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TelonrminatelonTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TopTelonrmination
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TopAndBottomTelonrmination
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TelonrminatelonTimelonlinelonInstructionMarshallelonr @Injelonct() () {

  delonf apply(instruction: TelonrminatelonTimelonlinelonInstruction): urt.TelonrminatelonTimelonlinelon =
    urt.TelonrminatelonTimelonlinelon(
      direlonction = instruction.telonrminatelonTimelonlinelonDirelonction match {
        caselon TopTelonrmination => urt.TimelonlinelonTelonrminationDirelonction.Top
        caselon BottomTelonrmination => urt.TimelonlinelonTelonrminationDirelonction.Bottom
        caselon TopAndBottomTelonrmination => urt.TimelonlinelonTelonrminationDirelonction.TopAndBottom
      }
    )
}
