packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.suggelonstion

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion.SpelonllingItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SpelonllingItelonmMarshallelonr @Injelonct() (
  telonxtRelonsultMarshallelonr: TelonxtRelonsultMarshallelonr,
  spelonllingActionTypelonMarshallelonr: SpelonllingActionTypelonMarshallelonr) {

  delonf apply(spelonllingItelonm: SpelonllingItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.Spelonlling(
      urt.Spelonlling(
        spelonllingRelonsult = telonxtRelonsultMarshallelonr(spelonllingItelonm.telonxtRelonsult),
        spelonllingAction = spelonllingItelonm.spelonllingActionTypelon.map(spelonllingActionTypelonMarshallelonr(_)),
        originalQuelonry = spelonllingItelonm.originalQuelonry
      )
    )
  }
}
