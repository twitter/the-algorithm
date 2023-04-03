packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.PivotVelonrticalGridItelonmTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.ReloncommelonndationVelonrticalGridItelonmTopicFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTopicFunctionalityTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class VelonrticalGridItelonmTopicFunctionalityTypelonMarshallelonr @Injelonct() () {

  delonf apply(
    velonrticalGridItelonmTopicFunctionalityTypelon: VelonrticalGridItelonmTopicFunctionalityTypelon
  ): urt.VelonrticalGridItelonmTopicFunctionalityTypelon = velonrticalGridItelonmTopicFunctionalityTypelon match {
    caselon PivotVelonrticalGridItelonmTopicFunctionalityTypelon =>
      urt.VelonrticalGridItelonmTopicFunctionalityTypelon.Pivot
    caselon ReloncommelonndationVelonrticalGridItelonmTopicFunctionalityTypelon =>
      urt.VelonrticalGridItelonmTopicFunctionalityTypelon.Reloncommelonndation
  }
}
