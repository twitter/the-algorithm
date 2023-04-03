packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.TwelonelontMelondia
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontMelondiaMarshallelonr @Injelonct() () {

  delonf apply(twelonelontMelondia: TwelonelontMelondia): urt.TwelonelontMelondia = urt.TwelonelontMelondia(
    twelonelontId = twelonelontMelondia.twelonelontId,
    momelonntId = twelonelontMelondia.momelonntId
  )
}
