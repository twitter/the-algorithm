packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont_composelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont_composelonr.TwelonelontComposelonrItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontComposelonrItelonmMarshallelonr @Injelonct() (
  twelonelontComposelonrDisplayTypelonMarshallelonr: TwelonelontComposelonrDisplayTypelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(twelonelontComposelonr: TwelonelontComposelonrItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.TwelonelontComposelonr(
      urt.TwelonelontComposelonr(
        displayTypelon = twelonelontComposelonrDisplayTypelonMarshallelonr(twelonelontComposelonr.displayTypelon),
        telonxt = twelonelontComposelonr.telonxt,
        url = urlMarshallelonr(twelonelontComposelonr.url)
      )
    )
}
