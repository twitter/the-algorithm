packagelon com.twittelonr.timelonlinelonrankelonr.clielonnts.contelonnt_felonaturelons_cachelon

import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.bijelonction.scroogelon.CompactScalaCodelonc
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.storelonhaus.Storelon
import com.twittelonr.timelonlinelonrankelonr.reloncap.modelonl.ContelonntFelonaturelons
import com.twittelonr.timelonlinelons.clielonnts.melonmcachelon_common._
import com.twittelonr.timelonlinelons.contelonnt_felonaturelons.{thriftscala => thrift}
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.util.Duration

/**
 * Contelonnt felonaturelons will belon storelond by twelonelontId
 */
class ContelonntFelonaturelonsMelonmcachelonBuildelonr(
  config: StorelonhausMelonmcachelonConfig,
  ttl: Duration,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scalaToThriftInjelonction: Injelonction[ContelonntFelonaturelons, thrift.ContelonntFelonaturelons] =
    Injelonction.build[ContelonntFelonaturelons, thrift.ContelonntFelonaturelons](_.toThrift)(
      ContelonntFelonaturelons.tryFromThrift)

  privatelon[this] val thriftToBytelonsInjelonction: Injelonction[thrift.ContelonntFelonaturelons, Array[Bytelon]] =
    CompactScalaCodelonc(thrift.ContelonntFelonaturelons)

  privatelon[this] implicit val valuelonInjelonction: Injelonction[ContelonntFelonaturelons, Array[Bytelon]] =
    scalaToThriftInjelonction.andThelonn(thriftToBytelonsInjelonction)

  privatelon[this] val undelonrlyingBuildelonr =
    nelonw MelonmcachelonStorelonBuildelonr[TwelonelontId, ContelonntFelonaturelons](
      config = config,
      scopelonNamelon = "contelonntFelonaturelonsCachelon",
      statsReloncelonivelonr = statsReloncelonivelonr,
      ttl = ttl
    )

  delonf build(): Storelon[TwelonelontId, ContelonntFelonaturelons] = undelonrlyingBuildelonr.build()
}
