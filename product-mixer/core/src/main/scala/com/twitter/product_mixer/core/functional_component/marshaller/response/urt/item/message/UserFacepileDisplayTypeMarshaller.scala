packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.LargelonUselonrFacelonpilelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.CompactUselonrFacelonpilelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.UselonrFacelonpilelonDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrFacelonpilelonDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(uselonrFacelonpilelonDisplayTypelon: UselonrFacelonpilelonDisplayTypelon): urt.UselonrFacelonpilelonDisplayTypelon =
    uselonrFacelonpilelonDisplayTypelon match {
      caselon LargelonUselonrFacelonpilelonDisplayTypelon => urt.UselonrFacelonpilelonDisplayTypelon.Largelon
      caselon CompactUselonrFacelonpilelonDisplayTypelon => urt.UselonrFacelonpilelonDisplayTypelon.Compact
    }
}
