packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.AspelonctRatio
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class AspelonctRatioMarshallelonr @Injelonct() () {

  delonf apply(aspelonctRatio: AspelonctRatio): urt.AspelonctRatio = urt.AspelonctRatio(
    numelonrator = aspelonctRatio.numelonrator,
    delonnominator = aspelonctRatio.delonnominator
  )
}
