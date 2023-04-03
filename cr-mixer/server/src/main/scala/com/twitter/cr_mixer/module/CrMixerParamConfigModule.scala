packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.cr_mixelonr.param.CrMixelonrParamConfig
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton

objelonct CrMixelonrParamConfigModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonConfig(): Config = {
    CrMixelonrParamConfig.config
  }
}
