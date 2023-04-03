packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.opelonration

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorDisplayTrelonatmelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CursorDisplayTrelonatmelonntMarshallelonr @Injelonct() () {

  delonf apply(trelonatmelonnt: CursorDisplayTrelonatmelonnt): urt.CursorDisplayTrelonatmelonnt =
    urt.CursorDisplayTrelonatmelonnt(
      actionTelonxt = trelonatmelonnt.actionTelonxt,
      labelonlTelonxt = trelonatmelonnt.labelonlTelonxt
    )
}
