packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.TimelonlinelonScribelonConfigMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransportMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.Pagelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UrpTransportMarshallelonr @Injelonct() (
  pagelonBodyMarshallelonr: PagelonBodyMarshallelonr,
  timelonlinelonScribelonConfigMarshallelonr: TimelonlinelonScribelonConfigMarshallelonr,
  pagelonHelonadelonrMarshallelonr: PagelonHelonadelonrMarshallelonr,
  pagelonNavBarMarshallelonr: PagelonNavBarMarshallelonr)
    elonxtelonnds TransportMarshallelonr[Pagelon, urp.Pagelon] {

  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr =
    TransportMarshallelonrIdelonntifielonr("UnifielondRichPagelon")

  ovelonrridelon delonf apply(pagelon: Pagelon): urp.Pagelon = urp.Pagelon(
    id = pagelon.id,
    pagelonBody = pagelonBodyMarshallelonr(pagelon.pagelonBody),
    scribelonConfig = pagelon.scribelonConfig.map(timelonlinelonScribelonConfigMarshallelonr(_)),
    pagelonHelonadelonr = pagelon.pagelonHelonadelonr.map(pagelonHelonadelonrMarshallelonr(_)),
    pagelonNavBar = pagelon.pagelonNavBar.map(pagelonNavBarMarshallelonr(_))
  )
}
