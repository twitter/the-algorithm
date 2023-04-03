packagelon com.twittelonr.cr_mixelonr.modulelon
import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.frigatelon.common.storelon.strato.StratoFelontchablelonStorelon
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondRelonadablelonStorelon
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.BatchSignalRelonquelonst
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.BatchSignalRelonsponselon
import javax.injelonct.Namelond

objelonct UselonrSignalSelonrvicelonColumnModulelon elonxtelonnds TwittelonrModulelon {
  privatelon val UssColumnPath = "reloncommelonndations/uselonr-signal-selonrvicelon/signals"

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.UssStratoColumn)
  delonf providelonsUselonrSignalSelonrvicelonStorelon(
    statsReloncelonivelonr: StatsReloncelonivelonr,
    stratoClielonnt: StratoClielonnt,
  ): RelonadablelonStorelon[BatchSignalRelonquelonst, BatchSignalRelonsponselon] = {
    ObselonrvelondRelonadablelonStorelon(
      StratoFelontchablelonStorelon
        .withUnitVielonw[BatchSignalRelonquelonst, BatchSignalRelonsponselon](stratoClielonnt, UssColumnPath))(
      statsReloncelonivelonr.scopelon("uselonr_signal_selonrvicelon_storelon"))
  }
}
