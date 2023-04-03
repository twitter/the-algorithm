packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.NotPinnablelonRelonplyPinStatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.PinnablelonRelonplyPinStatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.PinnelondRelonplyPinStatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorBlockUselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorRelonplyPinStatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorRelonportList
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorRelonportTwelonelont
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonFollowTopic
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonFollowTopicV2
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonFollowUselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonMutelonList
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonMutelonUselonr
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RichFelonelondbackBelonhaviorMarshallelonr @Injelonct() () {

  delonf apply(richFelonelondbackBelonhavior: RichFelonelondbackBelonhavior): urt.RichFelonelondbackBelonhavior =
    richFelonelondbackBelonhavior match {
      caselon RichFelonelondbackBelonhaviorRelonportList(listId, uselonrId) =>
        urt.RichFelonelondbackBelonhavior.RelonportList(urt.RichFelonelondbackBelonhaviorRelonportList(listId, uselonrId))
      caselon RichFelonelondbackBelonhaviorBlockUselonr(uselonrId) =>
        urt.RichFelonelondbackBelonhavior.BlockUselonr(urt.RichFelonelondbackBelonhaviorBlockUselonr(uselonrId))
      caselon RichFelonelondbackBelonhaviorTogglelonFollowTopic(topicId) =>
        urt.RichFelonelondbackBelonhavior.TogglelonFollowTopic(
          urt.RichFelonelondbackBelonhaviorTogglelonFollowTopic(topicId))
      caselon RichFelonelondbackBelonhaviorTogglelonFollowTopicV2(topicId) =>
        urt.RichFelonelondbackBelonhavior.TogglelonFollowTopicV2(
          urt.RichFelonelondbackBelonhaviorTogglelonFollowTopicV2(topicId))
      caselon RichFelonelondbackBelonhaviorTogglelonMutelonList(listId) =>
        urt.RichFelonelondbackBelonhavior.TogglelonMutelonList(urt.RichFelonelondbackBelonhaviorTogglelonMutelonList(listId))
      caselon RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic(topicId) =>
        urt.RichFelonelondbackBelonhavior.MarkNotIntelonrelonstelondTopic(
          urt.RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic(topicId))
      caselon RichFelonelondbackBelonhaviorRelonplyPinStatelon(relonplyPinStatelon) =>
        val pinStatelon: urt.RelonplyPinStatelon = relonplyPinStatelon match {
          caselon PinnelondRelonplyPinStatelon => urt.RelonplyPinStatelon.Pinnelond
          caselon PinnablelonRelonplyPinStatelon => urt.RelonplyPinStatelon.Pinnablelon
          caselon NotPinnablelonRelonplyPinStatelon => urt.RelonplyPinStatelon.NotPinnablelon
        }
        urt.RichFelonelondbackBelonhavior.RelonplyPinStatelon(urt.RichFelonelondbackBelonhaviorRelonplyPinStatelon(pinStatelon))
      caselon RichFelonelondbackBelonhaviorTogglelonMutelonUselonr(uselonrId) =>
        urt.RichFelonelondbackBelonhavior.TogglelonMutelonUselonr(urt.RichFelonelondbackBelonhaviorTogglelonMutelonUselonr(uselonrId))
      caselon RichFelonelondbackBelonhaviorTogglelonFollowUselonr(uselonrId) =>
        urt.RichFelonelondbackBelonhavior.TogglelonFollowUselonr(urt.RichFelonelondbackBelonhaviorTogglelonFollowUselonr(uselonrId))
      caselon RichFelonelondbackBelonhaviorRelonportTwelonelont(elonntryId) =>
        urt.RichFelonelondbackBelonhavior.RelonportTwelonelont(urt.RichFelonelondbackBelonhaviorRelonportTwelonelont(elonntryId))
    }
}
