packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DismissInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class DismissInfoMarshallelonr @Injelonct() (callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(dismissInfo: DismissInfo): urt.DismissInfo =
    urt.DismissInfo(dismissInfo.callbacks.map(_.map(callbackMarshallelonr(_))))
}
