packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.param.deloncidelonr.CrMixelonrDeloncidelonr
import com.twittelonr.cr_mixelonr.sourcelon_signal.FrsStorelon
import com.twittelonr.cr_mixelonr.sourcelon_signal.FrsStorelon.FrsQuelonryRelonsult
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondRelonadablelonStorelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import javax.injelonct.Namelond

objelonct FrsStorelonModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.FrsStorelon)
  delonf providelonsFrsStorelon(
    frsClielonnt: FollowReloncommelonndationsThriftSelonrvicelon.MelonthodPelonrelonndpoint,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    deloncidelonr: CrMixelonrDeloncidelonr
  ): RelonadablelonStorelon[FrsStorelon.Quelonry, Selonq[FrsQuelonryRelonsult]] = {
    ObselonrvelondRelonadablelonStorelon(FrsStorelon(frsClielonnt, statsReloncelonivelonr, deloncidelonr))(
      statsReloncelonivelonr.scopelon("follow_reloncommelonndations_storelon"))
  }
}
