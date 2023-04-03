packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.SinglelonStatelonDelonfaultVelonrticalGridItelonmTilelonStylelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.DoublelonStatelonDelonfaultVelonrticalGridItelonmTilelonStylelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTilelonStylelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class VelonrticalGridItelonmTilelonStylelonMarshallelonr @Injelonct() () {

  delonf apply(velonrticalGridItelonmTilelonStylelon: VelonrticalGridItelonmTilelonStylelon): urt.VelonrticalGridItelonmTilelonStylelon =
    velonrticalGridItelonmTilelonStylelon match {
      caselon SinglelonStatelonDelonfaultVelonrticalGridItelonmTilelonStylelon =>
        urt.VelonrticalGridItelonmTilelonStylelon.SinglelonStatelonDelonfault
      caselon DoublelonStatelonDelonfaultVelonrticalGridItelonmTilelonStylelon =>
        urt.VelonrticalGridItelonmTilelonStylelon.DoublelonStatelonDelonfault
    }
}
