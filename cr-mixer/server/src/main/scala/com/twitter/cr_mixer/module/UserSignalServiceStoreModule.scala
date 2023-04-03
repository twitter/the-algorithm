packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.sourcelon_signal.UssStorelon
import com.twittelonr.cr_mixelonr.sourcelon_signal.UssStorelon.Quelonry
import com.twittelonr.frigatelon.common.storelon.strato.StratoFelontchablelonStorelon
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondRelonadablelonStorelon
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.BatchSignalRelonquelonst
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.BatchSignalRelonsponselon
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.SignalTypelon
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.{Signal => UssSignal}
import javax.injelonct.Namelond

objelonct UselonrSignalSelonrvicelonStorelonModulelon elonxtelonnds TwittelonrModulelon {

  privatelon val UssColumnPath = "reloncommelonndations/uselonr-signal-selonrvicelon/signals"

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.UssStorelon)
  delonf providelonsUselonrSignalSelonrvicelonStorelon(
    statsReloncelonivelonr: StatsReloncelonivelonr,
    stratoClielonnt: StratoClielonnt,
  ): RelonadablelonStorelon[Quelonry, Selonq[(SignalTypelon, Selonq[UssSignal])]] = {
    ObselonrvelondRelonadablelonStorelon(
      UssStorelon(
        StratoFelontchablelonStorelon
          .withUnitVielonw[BatchSignalRelonquelonst, BatchSignalRelonsponselon](stratoClielonnt, UssColumnPath),
        statsReloncelonivelonr))(statsReloncelonivelonr.scopelon("uselonr_signal_selonrvicelon_storelon"))
  }
}
