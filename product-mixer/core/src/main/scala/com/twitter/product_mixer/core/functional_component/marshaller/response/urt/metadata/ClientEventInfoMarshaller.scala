packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ClielonntelonvelonntInfoMarshallelonr @Injelonct() (
  clielonntelonvelonntDelontailsMarshallelonr: ClielonntelonvelonntDelontailsMarshallelonr) {

  delonf apply(clielonntelonvelonntInfo: ClielonntelonvelonntInfo): urt.ClielonntelonvelonntInfo = {
    urt.ClielonntelonvelonntInfo(
      componelonnt = clielonntelonvelonntInfo.componelonnt,
      elonlelonmelonnt = clielonntelonvelonntInfo.elonlelonmelonnt,
      delontails = clielonntelonvelonntInfo.delontails.map(clielonntelonvelonntDelontailsMarshallelonr(_)),
      action = clielonntelonvelonntInfo.action,
      elonntityTokelonn = clielonntelonvelonntInfo.elonntityTokelonn
    )
  }
}
