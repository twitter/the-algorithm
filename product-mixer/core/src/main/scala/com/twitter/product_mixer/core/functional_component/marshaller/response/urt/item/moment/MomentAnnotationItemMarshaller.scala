packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.momelonnt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.momelonnt.MomelonntAnnotationItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MomelonntAnnotationItelonmMarshallelonr @Injelonct() (richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {
  delonf apply(momelonntAnnotationItelonm: MomelonntAnnotationItelonm): urt.TimelonlinelonItelonmContelonnt = {
    urt.TimelonlinelonItelonmContelonnt.MomelonntAnnotation(
      urt.MomelonntAnnotation(
        annotationId = momelonntAnnotationItelonm.id,
        telonxt = momelonntAnnotationItelonm.telonxt.map(richTelonxtMarshallelonr(_)),
        helonadelonr = momelonntAnnotationItelonm.helonadelonr.map(richTelonxtMarshallelonr(_)),
      )
    )
  }
}
