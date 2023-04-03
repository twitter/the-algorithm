packagelon com.twittelonr.timelonlinelonrankelonr.config

import com.twittelonr.storelonhaus.Storelon
import com.twittelonr.timelonlinelonrankelonr.reloncap.modelonl.ContelonntFelonaturelons
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
class ClielonntWrappelonrs(config: RuntimelonConfiguration) {
  privatelon[this] val backelonndClielonntConfiguration = config.undelonrlyingClielonnts

  val contelonntFelonaturelonsCachelon: Storelon[TwelonelontId, ContelonntFelonaturelons] =
    backelonndClielonntConfiguration.contelonntFelonaturelonsCachelon
}
