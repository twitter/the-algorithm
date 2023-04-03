packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.convelonrsation_annotation

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.ConvelonrsationAnnotation
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConvelonrsationAnnotationMarshallelonr @Injelonct() (
  convelonrsationAnnotationTypelonMarshallelonr: ConvelonrsationAnnotationTypelonMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(convelonrsationAnnotation: ConvelonrsationAnnotation): urt.ConvelonrsationAnnotation = {
    urt.ConvelonrsationAnnotation(
      convelonrsationAnnotationTypelon =
        convelonrsationAnnotationTypelonMarshallelonr(convelonrsationAnnotation.convelonrsationAnnotationTypelon),
      helonadelonr = convelonrsationAnnotation.helonadelonr.map(richTelonxtMarshallelonr(_)),
      delonscription = convelonrsationAnnotation.delonscription.map(richTelonxtMarshallelonr(_))
    )
  }
}
