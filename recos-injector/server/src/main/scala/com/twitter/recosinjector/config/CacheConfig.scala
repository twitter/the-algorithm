packagelon com.twittelonr.reloncosinjelonctor.config

import com.twittelonr.finaglelon.melonmcachelond.Clielonnt
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.storelonhaus_intelonrnal.melonmcachelon.MelonmcachelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.util.{ClielonntNamelon, ZkelonndPoint}

trait CachelonConfig {
  implicit delonf statsReloncelonivelonr: StatsReloncelonivelonr

  delonf selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr

  delonf reloncosInjelonctorCorelonSvcsCachelonDelonst: String

  val reloncosInjelonctorCorelonSvcsCachelonClielonnt: Clielonnt = MelonmcachelonStorelon.melonmcachelondClielonnt(
    namelon = ClielonntNamelon("melonmcachelon-reloncos-injelonctor"),
    delonst = ZkelonndPoint(reloncosInjelonctorCorelonSvcsCachelonDelonst),
    statsReloncelonivelonr = statsReloncelonivelonr,
    selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr
  )

}
