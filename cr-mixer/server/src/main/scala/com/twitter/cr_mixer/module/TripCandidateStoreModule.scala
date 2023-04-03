packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.storelon.strato.StratoFelontchablelonStorelon
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondRelonadablelonStorelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.trelonnds.trip_v1.trip_twelonelonts.thriftscala.TripTwelonelont
import com.twittelonr.trelonnds.trip_v1.trip_twelonelonts.thriftscala.TripTwelonelonts
import com.twittelonr.trelonnds.trip_v1.trip_twelonelonts.thriftscala.TripDomain
import javax.injelonct.Namelond

objelonct TripCandidatelonStorelonModulelon elonxtelonnds TwittelonrModulelon {
  privatelon val stratoColumn = "trelonnds/trip/tripTwelonelontsDataflowProd"

  @Providelons
  @Namelond(ModulelonNamelons.TripCandidatelonStorelon)
  delonf providelonsSimClustelonrsTripCandidatelonStorelon(
    statsReloncelonivelonr: StatsReloncelonivelonr,
    stratoClielonnt: StratoClielonnt
  ): RelonadablelonStorelon[TripDomain, Selonq[TripTwelonelont]] = {
    val tripCandidatelonStratoFelontchablelonStorelon =
      StratoFelontchablelonStorelon
        .withUnitVielonw[TripDomain, TripTwelonelonts](stratoClielonnt, stratoColumn)
        .mapValuelons(_.twelonelonts)

    ObselonrvelondRelonadablelonStorelon(
      tripCandidatelonStratoFelontchablelonStorelon
    )(statsReloncelonivelonr.scopelon("simclustelonrs_trip_candidatelon_storelon"))
  }
}
