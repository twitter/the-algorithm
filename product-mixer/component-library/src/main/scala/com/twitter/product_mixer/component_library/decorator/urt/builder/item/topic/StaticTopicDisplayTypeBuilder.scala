packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTopicCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.topic.BaselonTopicDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicDisplayTypelon

caselon class StaticTopicDisplayTypelonBuildelonr(
  displayTypelon: TopicDisplayTypelon)
    elonxtelonnds BaselonTopicDisplayTypelonBuildelonr[PipelonlinelonQuelonry, BaselonTopicCandidatelon] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: BaselonTopicCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[TopicDisplayTypelon] = Somelon(displayTypelon)
}
