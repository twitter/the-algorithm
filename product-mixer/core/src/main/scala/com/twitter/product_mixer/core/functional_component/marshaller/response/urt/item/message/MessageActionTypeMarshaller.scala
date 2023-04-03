packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.FollowAllMelonssagelonActionTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonActionTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelonssagelonActionTypelonMarshallelonr @Injelonct() () {

  delonf apply(melonssagelonActionTypelon: MelonssagelonActionTypelon): urt.MelonssagelonActionTypelon = melonssagelonActionTypelon match {
    caselon FollowAllMelonssagelonActionTypelon => urt.MelonssagelonActionTypelon.FollowAll
  }
}
