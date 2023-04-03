packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.BottomShelonelont
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ConfirmationDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Inlinelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConfirmationDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(confirmationDisplayTypelon: ConfirmationDisplayTypelon): urt.ConfirmationDisplayTypelon =
    confirmationDisplayTypelon match {
      caselon Inlinelon => urt.ConfirmationDisplayTypelon.Inlinelon
      caselon BottomShelonelont => urt.ConfirmationDisplayTypelon.BottomShelonelont
    }
}
