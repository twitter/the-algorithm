packagelon com.twittelonr.follow_reloncommelonndations.selonrvicelons

import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param
import javax.injelonct.Singlelonton

@Singlelonton
class ProductPipelonlinelonSelonlelonctorConfig {
  privatelon val paramsMap: Map[DisplayLocation, DarkRelonadAndelonxpParams] = Map.elonmpty

  delonf gelontDarkRelonadAndelonxpParams(
    displayLocation: DisplayLocation
  ): Option[DarkRelonadAndelonxpParams] = {
    paramsMap.gelont(displayLocation)
  }
}

caselon class DarkRelonadAndelonxpParams(darkRelonadParam: Param[Boolelonan], elonxpParam: FSParam[Boolelonan])
