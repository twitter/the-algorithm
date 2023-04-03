packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.CallToAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Singlelonton

@Singlelonton
class CallToActionMarshallelonr {
  delonf apply(callToAction: CallToAction): urt.CallToAction = {
    urt.CallToAction(
      callToActionTypelon = callToAction.callToActionTypelon,
      url = callToAction.url
    )
  }
}
