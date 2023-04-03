packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ConvelonrsationDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConvelonrsationDelontailsMarshallelonr @Injelonct() (selonctionMarshallelonr: ConvelonrsationSelonctionMarshallelonr) {

  delonf apply(convelonrsationDelontails: ConvelonrsationDelontails): urt.ConvelonrsationDelontails =
    urt.ConvelonrsationDelontails(
      convelonrsationSelonction = convelonrsationDelontails.convelonrsationSelonction.map(selonctionMarshallelonr(_))
    )
}
