packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.topic

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.topic.TopicCandidatelonUrtItelonmBuildelonr.TopicClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTopicCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.topic.BaselonTopicDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.topic.BaselonTopicFunctionalityTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct TopicCandidatelonUrtItelonmBuildelonr {
  val TopicClielonntelonvelonntInfoelonlelonmelonnt: String = "topic"
}

caselon class TopicCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTopicCandidatelon](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon],
  topicFunctionalityTypelonBuildelonr: Option[BaselonTopicFunctionalityTypelonBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  topicDisplayTypelonBuildelonr: Option[BaselonTopicDisplayTypelonBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon]
  ] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, Candidatelon, TopicItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    topicCandidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): TopicItelonm =
    TopicItelonm(
      id = topicCandidatelon.id,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        topicCandidatelon,
        candidatelonFelonaturelons,
        Somelon(TopicClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, topicCandidatelon, candidatelonFelonaturelons)),
      topicFunctionalityTypelon =
        topicFunctionalityTypelonBuildelonr.flatMap(_.apply(quelonry, topicCandidatelon, candidatelonFelonaturelons)),
      topicDisplayTypelon =
        topicDisplayTypelonBuildelonr.flatMap(_.apply(quelonry, topicCandidatelon, candidatelonFelonaturelons))
    )
}
