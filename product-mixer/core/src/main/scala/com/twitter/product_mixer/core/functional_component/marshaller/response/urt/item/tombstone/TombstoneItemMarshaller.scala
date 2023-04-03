packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tombstonelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonmMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TombstonelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TombstonelonItelonmMarshallelonr @Injelonct() (
  displayTypelonMarshallelonr: TombstonelonDisplayTypelonMarshallelonr,
  tombstonelonInfoMarshallelonr: TombstonelonInfoMarshallelonr,
  twelonelontItelonmMarshallelonr: TwelonelontItelonmMarshallelonr) {

  delonf apply(tombstonelonItelonm: TombstonelonItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.Tombstonelon(
      urt.Tombstonelon(
        displayTypelon = displayTypelonMarshallelonr(tombstonelonItelonm.tombstonelonDisplayTypelon),
        tombstonelonInfo = tombstonelonItelonm.tombstonelonInfo.map(tombstonelonInfoMarshallelonr(_)),
        twelonelont = tombstonelonItelonm.twelonelont.map(twelonelontItelonmMarshallelonr(_).twelonelont)
      )
    )
}
