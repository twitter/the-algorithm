packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.Classic
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ClassicNoDividelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.Contelonxtelonmphasis
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonHelonadelonrDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonHelonadelonrDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(displayTypelon: ModulelonHelonadelonrDisplayTypelon): urt.ModulelonHelonadelonrDisplayTypelon =
    displayTypelon match {
      caselon Classic => urt.ModulelonHelonadelonrDisplayTypelon.Classic
      caselon Contelonxtelonmphasis => urt.ModulelonHelonadelonrDisplayTypelon.Contelonxtelonmphasis
      caselon ClassicNoDividelonr => urt.ModulelonHelonadelonrDisplayTypelon.ClassicNoDividelonr
    }

}
