packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.articlelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonSelonelondTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.FollowingListSelonelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.FrielonndsOfFrielonndsSelonelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ListIdSelonelond
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ArticlelonSelonelondTypelonMarshallelonr @Injelonct() () {

  delonf apply(articlelonSelonelondTypelon: ArticlelonSelonelondTypelon): urt.ArticlelonSelonelondTypelon =
    articlelonSelonelondTypelon match {
      caselon FollowingListSelonelond => urt.ArticlelonSelonelondTypelon.FollowingList
      caselon FrielonndsOfFrielonndsSelonelond => urt.ArticlelonSelonelondTypelon.FrielonndsOfFrielonnds
      caselon ListIdSelonelond => urt.ArticlelonSelonelondTypelon.ListId
    }
}
