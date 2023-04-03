packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrCtaBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrBelonhaviorDismiss
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrBelonhaviorNavigatelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr

@Singlelonton
class CovelonrCtaBelonhaviorMarshallelonr @Injelonct() (
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(covelonrCtaBelonhavior: CovelonrCtaBelonhavior): urt.CovelonrCtaBelonhavior =
    covelonrCtaBelonhavior match {
      caselon dismiss: CovelonrBelonhaviorDismiss =>
        urt.CovelonrCtaBelonhavior.Dismiss(
          urt.CovelonrBelonhaviorDismiss(dismiss.felonelondbackMelonssagelon.map(richTelonxtMarshallelonr(_))))
      caselon nav: CovelonrBelonhaviorNavigatelon =>
        urt.CovelonrCtaBelonhavior.Navigatelon(urt.CovelonrBelonhaviorNavigatelon(urlMarshallelonr(nav.url)))
    }
}
