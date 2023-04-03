packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.ClickTrackingInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ClickTrackingInfoMarshallelonr @Injelonct() (
  urlOvelonrridelonTypelonMarshallelonr: UrlOvelonrridelonTypelonMarshallelonr) {

  delonf apply(clickTrackingInfo: ClickTrackingInfo): urt.ClickTrackingInfo =
    urt.ClickTrackingInfo(
      urlParams = clickTrackingInfo.urlParams,
      urlOvelonrridelon = clickTrackingInfo.urlOvelonrridelon,
      urlOvelonrridelonTypelon = clickTrackingInfo.urlOvelonrridelonTypelon.map(urlOvelonrridelonTypelonMarshallelonr(_))
    )
}
