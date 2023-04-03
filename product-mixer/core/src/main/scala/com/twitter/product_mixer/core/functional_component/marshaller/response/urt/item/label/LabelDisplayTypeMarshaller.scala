packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.labelonl

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.labelonl._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class LabelonlDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(labelonlDisplayTypelon: LabelonlDisplayTypelon): urt.LabelonlDisplayTypelon = labelonlDisplayTypelon match {
    caselon InlinelonHelonadelonrLabelonlDisplayTypelon => urt.LabelonlDisplayTypelon.InlinelonHelonadelonr
    caselon OthelonrRelonplielonsSelonctionHelonadelonrLabelonlDisplayTypelon => urt.LabelonlDisplayTypelon.OthelonrRelonplielonsSelonctionHelonadelonr
  }
}
