packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.DismissInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.HalfCovelonrContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HalfCovelonrContelonntMarshallelonr @Injelonct() (
  halfCovelonrDisplayTypelonMarshallelonr: HalfCovelonrDisplayTypelonMarshallelonr,
  covelonrCtaMarshallelonr: CovelonrCtaMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  covelonrImagelonMarshallelonr: CovelonrImagelonMarshallelonr,
  dismissInfoMarshallelonr: DismissInfoMarshallelonr,
  callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(halfCovelonr: HalfCovelonrContelonnt): urt.Covelonr =
    urt.Covelonr.HalfCovelonr(
      urt.HalfCovelonr(
        displayTypelon = halfCovelonrDisplayTypelonMarshallelonr(halfCovelonr.displayTypelon),
        primaryTelonxt = richTelonxtMarshallelonr(halfCovelonr.primaryTelonxt),
        primaryCovelonrCta = covelonrCtaMarshallelonr(halfCovelonr.primaryCovelonrCta),
        seloncondaryCovelonrCta = halfCovelonr.seloncondaryCovelonrCta.map(covelonrCtaMarshallelonr(_)),
        seloncondaryTelonxt = halfCovelonr.seloncondaryTelonxt.map(richTelonxtMarshallelonr(_)),
        imprelonssionCallbacks = halfCovelonr.imprelonssionCallbacks.map(_.map(callbackMarshallelonr(_))),
        dismissiblelon = halfCovelonr.dismissiblelon,
        covelonrImagelon = halfCovelonr.covelonrImagelon.map(covelonrImagelonMarshallelonr(_)),
        dismissInfo = halfCovelonr.dismissInfo.map(dismissInfoMarshallelonr(_))
      ))
}
