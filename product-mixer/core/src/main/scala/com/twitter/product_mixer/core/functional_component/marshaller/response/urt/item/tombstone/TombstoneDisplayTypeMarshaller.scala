packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tombstonelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.DisconnelonctelondRelonplielonsAncelonstor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.DisconnelonctelondRelonplielonsDelonscelonndant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.Inlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.NonCompliant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TombstonelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TwelonelontUnavailablelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TombstonelonDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(tombstonelonDisplayTypelon: TombstonelonDisplayTypelon): urt.TombstonelonDisplayTypelon =
    tombstonelonDisplayTypelon match {
      caselon TwelonelontUnavailablelon => urt.TombstonelonDisplayTypelon.TwelonelontUnavailablelon
      caselon DisconnelonctelondRelonplielonsAncelonstor => urt.TombstonelonDisplayTypelon.DisconnelonctelondRelonplielonsAncelonstor
      caselon DisconnelonctelondRelonplielonsDelonscelonndant => urt.TombstonelonDisplayTypelon.DisconnelonctelondRelonplielonsDelonscelonndant
      caselon Inlinelon => urt.TombstonelonDisplayTypelon.Inlinelon
      caselon NonCompliant => urt.TombstonelonDisplayTypelon.NonCompliant
    }
}
