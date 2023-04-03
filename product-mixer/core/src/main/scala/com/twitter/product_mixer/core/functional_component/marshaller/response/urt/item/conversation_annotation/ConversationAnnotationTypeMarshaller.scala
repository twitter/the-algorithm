packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.convelonrsation_annotation

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.ConvelonrsationAnnotationTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.Largelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.Political

import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConvelonrsationAnnotationTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    convelonrsationAnnotationTypelon: ConvelonrsationAnnotationTypelon
  ): urt.ConvelonrsationAnnotationTypelon = convelonrsationAnnotationTypelon match {
    caselon Largelon => urt.ConvelonrsationAnnotationTypelon.Largelon
    caselon Political => urt.ConvelonrsationAnnotationTypelon.Political
  }
}
