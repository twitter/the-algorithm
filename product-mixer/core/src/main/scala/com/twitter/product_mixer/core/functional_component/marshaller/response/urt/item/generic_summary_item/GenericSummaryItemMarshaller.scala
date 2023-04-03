packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.gelonnelonric_summary_itelonm

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia.MelondiaMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond.PromotelondMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonricSummaryItelonmMarshallelonr @Injelonct() (
  gelonnelonricSummaryDisplayTypelonMarshallelonr: GelonnelonricSummaryDisplayTypelonMarshallelonr,
  gelonnelonricSummaryContelonxtMarshallelonr: GelonnelonricSummaryContelonxtMarshallelonr,
  gelonnelonricSummaryActionMarshallelonr: GelonnelonricSummaryActionMarshallelonr,
  melondiaMarshallelonr: MelondiaMarshallelonr,
  promotelondMelontadataMarshallelonr: PromotelondMelontadataMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(gelonnelonricSummaryItelonm: GelonnelonricSummaryItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.GelonnelonricSummary(
      urt.GelonnelonricSummary(
        helonadlinelon = richTelonxtMarshallelonr(gelonnelonricSummaryItelonm.helonadlinelon),
        displayTypelon = gelonnelonricSummaryDisplayTypelonMarshallelonr(gelonnelonricSummaryItelonm.displayTypelon),
        uselonrAttributionIds = gelonnelonricSummaryItelonm.uselonrAttributionIds,
        melondia = gelonnelonricSummaryItelonm.melondia.map(melondiaMarshallelonr(_)),
        contelonxt = gelonnelonricSummaryItelonm.contelonxt.map(gelonnelonricSummaryContelonxtMarshallelonr(_)),
        timelonstamp = gelonnelonricSummaryItelonm.timelonstamp.map(_.inMilliselonconds),
        onClickAction = gelonnelonricSummaryItelonm.onClickAction.map(gelonnelonricSummaryActionMarshallelonr(_)),
        promotelondMelontadata = gelonnelonricSummaryItelonm.promotelondMelontadata.map(promotelondMelontadataMarshallelonr(_))
      )
    )
}
