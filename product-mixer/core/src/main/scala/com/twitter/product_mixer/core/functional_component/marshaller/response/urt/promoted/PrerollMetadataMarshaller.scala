packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PrelonrollMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PrelonrollMelontadataMarshallelonr @Injelonct() (
  prelonrollMarshallelonr: PrelonrollMarshallelonr) {
  delonf apply(prelonrollMelontadata: PrelonrollMelontadata): urt.PrelonrollMelontadata =
    urt.PrelonrollMelontadata(
      prelonroll = prelonrollMelontadata.prelonroll.map(prelonrollMarshallelonr(_)),
      videlonoAnalyticsScribelonPassthrough = prelonrollMelontadata.videlonoAnalyticsScribelonPassthrough
    )
}
