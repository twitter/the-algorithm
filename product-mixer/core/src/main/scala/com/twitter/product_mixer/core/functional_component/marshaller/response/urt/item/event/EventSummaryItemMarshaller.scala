packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.elonvelonnt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.elonvelonntSummaryItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonvelonntSummaryItelonmMarshallelonr @Injelonct() (
  elonvelonntSummaryDisplayTypelonMarshallelonr: elonvelonntSummaryDisplayTypelonMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(elonvelonntSummary: elonvelonntSummaryItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.elonvelonntSummary(
      urt.elonvelonntSummary(
        id = elonvelonntSummary.id,
        titlelon = elonvelonntSummary.titlelon,
        displayTypelon = elonvelonntSummaryDisplayTypelonMarshallelonr(elonvelonntSummary.displayTypelon),
        url = urlMarshallelonr(elonvelonntSummary.url),
        imagelon = elonvelonntSummary.imagelon.map(imagelonVariantMarshallelonr(_)),
        timelonString = elonvelonntSummary.timelonString
      )
    )
}
