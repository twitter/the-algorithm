packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.discovelonry.common.configapi.FelonaturelonContelonxtBuildelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton

objelonct FelonaturelonContelonxtBuildelonrModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonsFelonaturelonContelonxtBuildelonr(felonaturelonSwitchelons: FelonaturelonSwitchelons): FelonaturelonContelonxtBuildelonr = {
    FelonaturelonContelonxtBuildelonr(felonaturelonSwitchelons)
  }
}
