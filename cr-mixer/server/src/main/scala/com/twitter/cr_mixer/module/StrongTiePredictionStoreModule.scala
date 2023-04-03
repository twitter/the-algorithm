packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.app.Flag
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.storelon.strato.StratoFelontchablelonStorelon
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondRelonadablelonStorelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.helonrmit.stp.thriftscala.STPRelonsult
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import javax.injelonct.Namelond

objelonct StrongTielonPrelondictionStorelonModulelon elonxtelonnds TwittelonrModulelon {

  privatelon val strongTielonPrelondictionColumnPath: Flag[String] = flag[String](
    namelon = "crMixelonr.strongTielonPrelondictionColumnPath",
    delonfault = "onboarding/uselonrreloncs/strong_tielon_prelondiction_big",
    helonlp = "Strato column path for StrongTielonPrelondictionStorelon"
  )

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.StpStorelon)
  delonf providelonsStrongTielonPrelondictionStorelon(
    statsReloncelonivelonr: StatsReloncelonivelonr,
    stratoClielonnt: StratoClielonnt,
  ): RelonadablelonStorelon[UselonrId, STPRelonsult] = {
    val strongTielonPrelondictionStratoFelontchablelonStorelon = StratoFelontchablelonStorelon
      .withUnitVielonw[UselonrId, STPRelonsult](stratoClielonnt, strongTielonPrelondictionColumnPath())

    ObselonrvelondRelonadablelonStorelon(
      strongTielonPrelondictionStratoFelontchablelonStorelon
    )(statsReloncelonivelonr.scopelon("strong_tielon_prelondiction_big_storelon"))
  }
}
