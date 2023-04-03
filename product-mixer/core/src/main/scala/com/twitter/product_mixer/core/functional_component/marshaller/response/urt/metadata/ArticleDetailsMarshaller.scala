packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ArticlelonDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ArticlelonDelontailsMarshallelonr @Injelonct() () {

  delonf apply(articlelonDelontails: ArticlelonDelontails): urt.ArticlelonDelontails = urt.ArticlelonDelontails(
    articlelonPosition = articlelonDelontails.articlelonPosition,
    sharelonCount = articlelonDelontails.sharelonCount
  )
}
