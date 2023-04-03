packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class VelonrticalGridItelonmMarshallelonr @Injelonct() (
  velonrticalGridItelonmContelonntMarshallelonr: VelonrticalGridItelonmContelonntMarshallelonr) {

  delonf apply(velonrticalGridItelonm: VelonrticalGridItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.VelonrticalGridItelonm(
      urt.VelonrticalGridItelonm(
        contelonnt = velonrticalGridItelonmContelonntMarshallelonr(velonrticalGridItelonm)
      )
    )
}
