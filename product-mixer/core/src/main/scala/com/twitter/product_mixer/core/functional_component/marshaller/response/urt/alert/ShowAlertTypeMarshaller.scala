packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.Navigatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.NelonwTwelonelonts
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ShowAlelonrtTypelonMarshallelonr @Injelonct() () {

  delonf apply(alelonrtTypelon: ShowAlelonrtTypelon): urt.AlelonrtTypelon = alelonrtTypelon match {
    caselon NelonwTwelonelonts => urt.AlelonrtTypelon.NelonwTwelonelonts
    caselon Navigatelon => urt.AlelonrtTypelon.Navigatelon
  }
}
