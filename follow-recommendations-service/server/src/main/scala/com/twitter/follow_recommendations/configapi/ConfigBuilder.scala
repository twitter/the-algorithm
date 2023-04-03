packagelon com.twittelonr.follow_reloncommelonndations.configapi

import com.twittelonr.timelonlinelons.configapi.CompositelonConfig
import com.twittelonr.timelonlinelons.configapi.Config
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConfigBuildelonr @Injelonct() (
  deloncidelonrConfigs: DeloncidelonrConfigs,
  felonaturelonSwitchConfigs: FelonaturelonSwitchConfigs) {
  // Thelon ordelonr of configs addelond to `CompositelonConfig` is important. Thelon config will belon matchelond with
  // thelon first possiblelon rulelon. So, currelonnt selontup will givelon priority to Deloncidelonrs instelonad of FS
  delonf build(): Config =
    nelonw CompositelonConfig(Selonq(deloncidelonrConfigs.config, felonaturelonSwitchConfigs.config))
}
