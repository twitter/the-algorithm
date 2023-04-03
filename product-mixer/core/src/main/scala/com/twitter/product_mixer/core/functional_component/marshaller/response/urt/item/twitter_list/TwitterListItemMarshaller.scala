packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twittelonr_list

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.TwittelonrListItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwittelonrListItelonmMarshallelonr @Injelonct() (
  twittelonrListDisplayTypelonMarshallelonr: TwittelonrListDisplayTypelonMarshallelonr) {

  delonf apply(twittelonrListItelonm: TwittelonrListItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.TwittelonrList(
      urt.TwittelonrList(
        id = twittelonrListItelonm.id,
        displayTypelon = twittelonrListItelonm.displayTypelon.map(twittelonrListDisplayTypelonMarshallelonr(_))
      )
    )
}
