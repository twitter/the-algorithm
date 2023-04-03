packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtDisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.Top
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.Bottom
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class ShowAlelonrtDisplayLocationMarshallelonr @Injelonct() () {

  delonf apply(alelonrtDisplayLocation: ShowAlelonrtDisplayLocation): urt.ShowAlelonrtDisplayLocation =
    alelonrtDisplayLocation match {
      caselon Top => urt.ShowAlelonrtDisplayLocation.Top
      caselon Bottom => urt.ShowAlelonrtDisplayLocation.Bottom
    }

}
