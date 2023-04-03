packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTopicTilelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class VelonrticalGridItelonmContelonntMarshallelonr @Injelonct() (
  velonrticalGridItelonmTopicTilelonMarshallelonr: VelonrticalGridItelonmTopicTilelonMarshallelonr) {

  delonf apply(itelonm: VelonrticalGridItelonm): urt.VelonrticalGridItelonmContelonnt = itelonm match {
    caselon velonrticalGridItelonmTopicTilelon: VelonrticalGridItelonmTopicTilelon =>
      velonrticalGridItelonmTopicTilelonMarshallelonr(velonrticalGridItelonmTopicTilelon)
  }
}
