packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.highlight

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.highlight.HighlightelondSelonction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HighlightelondSelonctionMarshallelonr @Injelonct() () {

  delonf apply(highlightelondSelonction: HighlightelondSelonction): urt.HighlightelondSelonction =
    urt.HighlightelondSelonction(
      startIndelonx = highlightelondSelonction.startIndelonx,
      elonndIndelonx = highlightelondSelonction.elonndIndelonx
    )
}
