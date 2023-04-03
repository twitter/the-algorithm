packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.topic

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.topic.TopicCandidatelonUrtItelonmBuildelonr.TopicClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TopicCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonUrlBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTilelonStylelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTopicTilelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class VelonrticalGridTopicCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, TopicCandidatelon],
  velonrticalGridItelonmTopicFunctionalityTypelon: VelonrticalGridItelonmTopicFunctionalityTypelon,
  velonrticalGridItelonmTilelonStylelon: VelonrticalGridItelonmTilelonStylelon,
  urlBuildelonr: Option[BaselonUrlBuildelonr[Quelonry, TopicCandidatelon]] = Nonelon,
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, TopicCandidatelon]
  ] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, TopicCandidatelon, VelonrticalGridItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    topicCandidatelon: TopicCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): VelonrticalGridItelonm = {
    VelonrticalGridItelonmTopicTilelon(
      id = topicCandidatelon.id,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        topicCandidatelon,
        candidatelonFelonaturelons,
        Somelon(TopicClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, topicCandidatelon, candidatelonFelonaturelons)),
      stylelon = Somelon(velonrticalGridItelonmTilelonStylelon),
      functionalityTypelon = Somelon(velonrticalGridItelonmTopicFunctionalityTypelon),
      url = urlBuildelonr.map(_.apply(quelonry, topicCandidatelon, candidatelonFelonaturelons))
    )
  }
}
