packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.ButtonStylelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Delonfault
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Primary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Seloncondary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Telonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Delonstructivelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Nelonutral
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.DelonstructivelonSeloncondary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.DelonstructivelonTelonxt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ButtonStylelonMarshallelonr @Injelonct() () {
  delonf apply(buttonStylelon: ButtonStylelon): urt.ButtonStylelon =
    buttonStylelon match {
      caselon Delonfault => urt.ButtonStylelon.Delonfault
      caselon Primary => urt.ButtonStylelon.Primary
      caselon Seloncondary => urt.ButtonStylelon.Seloncondary
      caselon Telonxt => urt.ButtonStylelon.Telonxt
      caselon Delonstructivelon => urt.ButtonStylelon.Delonstructivelon
      caselon Nelonutral => urt.ButtonStylelon.Nelonutral
      caselon DelonstructivelonSeloncondary => urt.ButtonStylelon.DelonstructivelonSeloncondary
      caselon DelonstructivelonTelonxt => urt.ButtonStylelon.DelonstructivelonTelonxt
    }
}
