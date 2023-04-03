packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Relonct
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class RelonctMarshallelonr @Injelonct() () {

  delonf apply(relonct: Relonct): urt.Relonct = urt.Relonct(
    lelonft = relonct.lelonft,
    top = relonct.top,
    width = relonct.width,
    helonight = relonct.helonight
  )
}
