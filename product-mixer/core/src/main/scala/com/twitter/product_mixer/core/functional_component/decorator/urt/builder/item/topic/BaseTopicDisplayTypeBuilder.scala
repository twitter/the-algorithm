packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.topic

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.topic.TopicDisplayTypelon

trait BaselonTopicDisplayTypelonBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]] {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[TopicDisplayTypelon]
}
