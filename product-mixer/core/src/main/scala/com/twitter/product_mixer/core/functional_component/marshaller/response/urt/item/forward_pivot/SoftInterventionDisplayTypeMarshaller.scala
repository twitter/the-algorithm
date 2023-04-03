packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.forward_pivot

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.GelontThelonLatelonst
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.GovelonrnmelonntRelonquelonstelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.Mislelonading
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.SoftIntelonrvelonntionDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.StayInformelond
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SoftIntelonrvelonntionDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    softIntelonrvelonntionDisplayTypelon: SoftIntelonrvelonntionDisplayTypelon
  ): urt.SoftIntelonrvelonntionDisplayTypelon =
    softIntelonrvelonntionDisplayTypelon match {
      caselon GelontThelonLatelonst => urt.SoftIntelonrvelonntionDisplayTypelon.GelontThelonLatelonst
      caselon StayInformelond => urt.SoftIntelonrvelonntionDisplayTypelon.StayInformelond
      caselon Mislelonading => urt.SoftIntelonrvelonntionDisplayTypelon.Mislelonading
      caselon GovelonrnmelonntRelonquelonstelond => urt.SoftIntelonrvelonntionDisplayTypelon.GovelonrnmelonntRelonquelonstelond
    }
}
