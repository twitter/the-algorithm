packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.UselonrMelontadataManhattanelonndpoint
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.UselonrLanguagelonsStorelon
import com.twittelonr.homelon_mixelonr.storelon.UselonrLanguagelonsStorelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.selonarch.common.constants.{thriftscala => scc}
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVelonndpoint
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import javax.injelonct.Singlelonton

objelonct UselonrMelontadataStorelonModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(UselonrLanguagelonsStorelon)
  delonf providelonsUselonrLanguagelonsFelonaturelonsStorelon(
    @Namelond(UselonrMelontadataManhattanelonndpoint) UselonrMelontadataManhattanKVelonndpoint: ManhattanKVelonndpoint,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): RelonadablelonStorelon[Long, Selonq[scc.ThriftLanguagelon]] = {
    nelonw UselonrLanguagelonsStorelon(UselonrMelontadataManhattanKVelonndpoint, statsReloncelonivelonr)
  }
}
