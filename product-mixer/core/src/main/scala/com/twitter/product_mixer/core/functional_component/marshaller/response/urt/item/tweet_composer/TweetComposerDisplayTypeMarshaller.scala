packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont_composelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont_composelonr.Relonply
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont_composelonr.TwelonelontComposelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont_composelonr.TwelonelontComposelonrSelonlfThrelonad
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontComposelonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(displayTypelon: TwelonelontComposelonrDisplayTypelon): urt.TwelonelontComposelonrDisplayTypelon =
    displayTypelon match {
      caselon TwelonelontComposelonrSelonlfThrelonad => urt.TwelonelontComposelonrDisplayTypelon.SelonlfThrelonad
      caselon Relonply => urt.TwelonelontComposelonrDisplayTypelon.Relonply
    }
}
