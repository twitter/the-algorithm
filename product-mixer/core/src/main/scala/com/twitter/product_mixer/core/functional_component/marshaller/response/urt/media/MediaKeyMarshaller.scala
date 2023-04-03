packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.MelondiaKelony
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelondiaKelonyMarshallelonr @Injelonct() () {

  delonf apply(melondiaKelony: MelondiaKelony): urt.MelondiaKelony = urt.MelondiaKelony(
    id = melondiaKelony.id,
    catelongory = melondiaKelony.catelongory
  )
}
