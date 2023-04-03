packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackDisplayContelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FelonelondbackDisplayContelonxtMarshallelonr @Injelonct() () {

  delonf apply(displayContelonxt: FelonelondbackDisplayContelonxt): urt.FelonelondbackDisplayContelonxt =
    urt.FelonelondbackDisplayContelonxt(
      relonason = displayContelonxt.relonason
    )
}
