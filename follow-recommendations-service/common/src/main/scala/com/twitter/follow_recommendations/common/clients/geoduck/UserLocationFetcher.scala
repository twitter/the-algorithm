packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.GelonohashAndCountryCodelon
import com.twittelonr.stitch.Stitch

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrLocationFelontchelonr @Injelonct() (
  locationSelonrvicelonClielonnt: LocationSelonrvicelonClielonnt,
  relonvelonrselonGelonocodelonClielonnt: RelonvelonrselonGelonocodelonClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("uselonr_location_felontchelonr")
  privatelon val totalRelonquelonstsCountelonr = stats.countelonr("relonquelonsts")
  privatelon val elonmptyRelonsponselonsCountelonr = stats.countelonr("elonmpty")
  privatelon val locationSelonrvicelonelonxcelonptionCountelonr = stats.countelonr("location_selonrvicelon_elonxcelonption")
  privatelon val relonvelonrselonGelonocodelonelonxcelonptionCountelonr = stats.countelonr("relonvelonrselon_gelonocodelon_elonxcelonption")

  delonf gelontGelonohashAndCountryCodelon(
    uselonrId: Option[Long],
    ipAddrelonss: Option[String]
  ): Stitch[Option[GelonohashAndCountryCodelon]] = {
    totalRelonquelonstsCountelonr.incr()
    val lscLocationStitch = Stitch
      .collelonct {
        uselonrId.map(locationSelonrvicelonClielonnt.gelontGelonohashAndCountryCodelon)
      }.relonscuelon {
        caselon _: elonxcelonption =>
          locationSelonrvicelonelonxcelonptionCountelonr.incr()
          Stitch.Nonelon
      }

    val ipLocationStitch = Stitch
      .collelonct {
        ipAddrelonss.map(relonvelonrselonGelonocodelonClielonnt.gelontGelonohashAndCountryCodelon)
      }.relonscuelon {
        caselon _: elonxcelonption =>
          relonvelonrselonGelonocodelonelonxcelonptionCountelonr.incr()
          Stitch.Nonelon
      }

    Stitch.join(lscLocationStitch, ipLocationStitch).map {
      caselon (lscLocation, ipLocation) => {
        val gelonohash = lscLocation.flatMap(_.gelonohash).orelonlselon(ipLocation.flatMap(_.gelonohash))
        val countryCodelon =
          lscLocation.flatMap(_.countryCodelon).orelonlselon(ipLocation.flatMap(_.countryCodelon))
        (gelonohash, countryCodelon) match {
          caselon (Nonelon, Nonelon) =>
            elonmptyRelonsponselonsCountelonr.incr()
            Nonelon
          caselon _ => Somelon(GelonohashAndCountryCodelon(gelonohash, countryCodelon))
        }
      }
    }
  }
}
