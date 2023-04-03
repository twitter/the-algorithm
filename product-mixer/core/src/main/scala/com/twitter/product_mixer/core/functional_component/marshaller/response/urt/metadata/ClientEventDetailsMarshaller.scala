packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ClielonntelonvelonntDelontailsMarshallelonr @Injelonct() (
  convelonrsationDelontailsMarshallelonr: ConvelonrsationDelontailsMarshallelonr,
  timelonlinelonsDelontailsMarshallelonr: TimelonlinelonsDelontailsMarshallelonr,
  articlelonDelontailsMarshallelonr: ArticlelonDelontailsMarshallelonr,
  livelonelonvelonntDelontailsMarshallelonr: LivelonelonvelonntDelontailsMarshallelonr,
  commelonrcelonDelontailsMarshallelonr: CommelonrcelonDelontailsMarshallelonr) {

  delonf apply(clielonntelonvelonntDelontails: ClielonntelonvelonntDelontails): urt.ClielonntelonvelonntDelontails = {
    urt.ClielonntelonvelonntDelontails(
      convelonrsationDelontails =
        clielonntelonvelonntDelontails.convelonrsationDelontails.map(convelonrsationDelontailsMarshallelonr(_)),
      timelonlinelonsDelontails = clielonntelonvelonntDelontails.timelonlinelonsDelontails.map(timelonlinelonsDelontailsMarshallelonr(_)),
      articlelonDelontails = clielonntelonvelonntDelontails.articlelonDelontails.map(articlelonDelontailsMarshallelonr(_)),
      livelonelonvelonntDelontails = clielonntelonvelonntDelontails.livelonelonvelonntDelontails.map(livelonelonvelonntDelontailsMarshallelonr(_)),
      commelonrcelonDelontails = clielonntelonvelonntDelontails.commelonrcelonDelontails.map(commelonrcelonDelontailsMarshallelonr(_))
    )
  }
}
