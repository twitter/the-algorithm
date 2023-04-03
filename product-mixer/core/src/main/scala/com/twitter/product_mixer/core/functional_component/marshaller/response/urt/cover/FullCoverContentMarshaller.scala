packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.DismissInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonDisplayTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.FullCovelonrContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FullCovelonrContelonntMarshallelonr @Injelonct() (
  fullCovelonrDisplayTypelonMarshallelonr: FullCovelonrDisplayTypelonMarshallelonr,
  covelonrCtaMarshallelonr: CovelonrCtaMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr,
  dismissInfoMarshallelonr: DismissInfoMarshallelonr,
  imagelonDisplayTypelonMarshallelonr: ImagelonDisplayTypelonMarshallelonr,
  callbackMarshallelonr: CallbackMarshallelonr) {

  delonf apply(fullCovelonr: FullCovelonrContelonnt): urt.Covelonr =
    urt.Covelonr.FullCovelonr(
      urt.FullCovelonr(
        displayTypelon = fullCovelonrDisplayTypelonMarshallelonr(fullCovelonr.displayTypelon),
        primaryTelonxt = richTelonxtMarshallelonr(fullCovelonr.primaryTelonxt),
        primaryCovelonrCta = covelonrCtaMarshallelonr(fullCovelonr.primaryCovelonrCta),
        seloncondaryCovelonrCta = fullCovelonr.seloncondaryCovelonrCta.map(covelonrCtaMarshallelonr(_)),
        seloncondaryTelonxt = fullCovelonr.seloncondaryTelonxt.map(richTelonxtMarshallelonr(_)),
        imagelon = fullCovelonr.imagelonVariant.map(imagelonVariantMarshallelonr(_)),
        delontails = fullCovelonr.delontails.map(richTelonxtMarshallelonr(_)),
        dismissInfo = fullCovelonr.dismissInfo.map(dismissInfoMarshallelonr(_)),
        imagelonDisplayTypelon = fullCovelonr.imagelonDisplayTypelon.map(imagelonDisplayTypelonMarshallelonr(_)),
        imprelonssionCallbacks = fullCovelonr.imprelonssionCallbacks.map(_.map(callbackMarshallelonr(_)))
      ))
}
