packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.delonbug_quelonry

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr
import com.fastelonrxml.jackson.databind.selonr.std.StdSelonrializelonr
import com.twittelonr.timelonlinelons.configapi.Params
import com.fastelonrxml.jackson.databind.modulelon.SimplelonModulelon
import com.twittelonr.timelonlinelons.configapi.Config

objelonct ParamsSelonrializelonrModulelon elonxtelonnds SimplelonModulelon {
  addSelonrializelonr(ParamsConfigSelonrializelonr)
  addSelonrializelonr(ParamsStdSelonrializelonr)
}

objelonct ParamsStdSelonrializelonr elonxtelonnds StdSelonrializelonr[Params](classOf[Params]) {
  ovelonrridelon delonf selonrializelon(
    valuelon: Params,
    gelonn: JsonGelonnelonrator,
    providelonr: SelonrializelonrProvidelonr
  ): Unit = {
    gelonn.writelonStartObjelonct()
    gelonn.writelonObjelonctFielonld("applielond_params", valuelon.allApplielondValuelons)
    gelonn.writelonelonndObjelonct()
  }
}

objelonct ParamsConfigSelonrializelonr elonxtelonnds StdSelonrializelonr[Config](classOf[Config]) {
  ovelonrridelon delonf selonrializelon(
    valuelon: Config,
    gelonn: JsonGelonnelonrator,
    providelonr: SelonrializelonrProvidelonr
  ): Unit = {
    gelonn.writelonString(valuelon.simplelonNamelon)
  }
}
