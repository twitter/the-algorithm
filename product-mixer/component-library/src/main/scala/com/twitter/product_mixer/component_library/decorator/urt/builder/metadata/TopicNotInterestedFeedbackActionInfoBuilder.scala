packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTopicCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class TopicNotIntelonrelonstelondFelonelondbackActionInfoBuildelonr[-Quelonry <: PipelonlinelonQuelonry]()
    elonxtelonnds BaselonFelonelondbackActionInfoBuildelonr[Quelonry, BaselonTopicCandidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    topicCandidatelon: BaselonTopicCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackActionInfo] = {
    Somelon(
      FelonelondbackActionInfo(
        felonelondbackActions = Selonq(
          FelonelondbackAction(
            felonelondbackTypelon = RichBelonhavior,
            richBelonhavior = Somelon(
              RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic(topicCandidatelon.id.toString)
            ),
            hasUndoAction = Somelon(truelon),
            prompt = Nonelon,
            confirmation = Nonelon,
            felonelondbackUrl = Nonelon,
            clielonntelonvelonntInfo = Nonelon,
            childFelonelondbackActions = Nonelon,
            confirmationDisplayTypelon = Nonelon,
            icon = Nonelon,
            subprompt = Nonelon,
            elonncodelondFelonelondbackRelonquelonst = Nonelon
          )
        ),
        felonelondbackMelontadata = Nonelon,
        displayContelonxt = Nonelon,
        clielonntelonvelonntInfo = Nonelon
      ))
  }
}
