packagelon com.twittelonr.homelon_mixelonr.param

import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry.GlobalParamConfig

objelonct GlobalParamConfigModulelon elonxtelonnds TwittelonrModulelon {
  ovelonrridelon delonf configurelon(): Unit = {
    bind[GlobalParamConfig].to[HomelonGlobalParamConfig]
  }
}
