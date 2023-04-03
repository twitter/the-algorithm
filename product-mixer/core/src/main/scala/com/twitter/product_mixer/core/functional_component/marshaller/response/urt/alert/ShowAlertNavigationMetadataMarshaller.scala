packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtNavigationMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ShowAlelonrtNavigationMelontadataMarshallelonr @Injelonct() () {

  delonf apply(alelonrtNavigationMelontadata: ShowAlelonrtNavigationMelontadata): urt.ShowAlelonrtNavigationMelontadata =
    urt.ShowAlelonrtNavigationMelontadata(navigatelonToelonntryId =
      Somelon(alelonrtNavigationMelontadata.navigatelonToelonntryId))
}
