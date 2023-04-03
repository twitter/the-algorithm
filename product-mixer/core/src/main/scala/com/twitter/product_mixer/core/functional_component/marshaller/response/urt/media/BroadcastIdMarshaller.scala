packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.BroadcastId
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class BroadcastIdMarshallelonr @Injelonct() () {

  delonf apply(broadcastId: BroadcastId): urt.BroadcastId = urt.BroadcastId(
    id = broadcastId.id
  )
}
