packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twittelonr_list

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.List
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.ListTilelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.ListWithPin
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.ListWithSubscribelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twittelonr_list.TwittelonrListDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwittelonrListDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(twittelonrListDisplayTypelon: TwittelonrListDisplayTypelon): urt.TwittelonrListDisplayTypelon =
    twittelonrListDisplayTypelon match {
      caselon List => urt.TwittelonrListDisplayTypelon.List
      caselon ListTilelon => urt.TwittelonrListDisplayTypelon.ListTilelon
      caselon ListWithPin => urt.TwittelonrListDisplayTypelon.ListWithPin
      caselon ListWithSubscribelon => urt.TwittelonrListDisplayTypelon.ListWithSubscribelon
    }
}
