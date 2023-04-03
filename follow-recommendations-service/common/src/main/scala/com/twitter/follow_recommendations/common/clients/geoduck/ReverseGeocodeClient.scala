packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck

import com.twittelonr.follow_reloncommelonndations.common.modelonls.GelonohashAndCountryCodelon
import com.twittelonr.gelonoduck.common.thriftscala.Location
import com.twittelonr.gelonoduck.common.thriftscala.PlacelonQuelonry
import com.twittelonr.gelonoduck.common.thriftscala.RelonvelonrselonGelonocodelonIPRelonquelonst
import com.twittelonr.gelonoduck.selonrvicelon.thriftscala.GelonoContelonxt
import com.twittelonr.gelonoduck.thriftscala.RelonvelonrselonGelonocodelonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonvelonrselonGelonocodelonClielonnt @Injelonct() (rgcSelonrvicelon: RelonvelonrselonGelonocodelonr.MelonthodPelonrelonndpoint) {
  delonf gelontGelonohashAndCountryCodelon(ipAddrelonss: String): Stitch[GelonohashAndCountryCodelon] = {
    Stitch
      .callFuturelon {
        rgcSelonrvicelon
          .relonvelonrselonGelonocodelonIp(
            RelonvelonrselonGelonocodelonIPRelonquelonst(
              Selonq(ipAddrelonss),
              PlacelonQuelonry(Nonelon),
              simplelonRelonvelonrselonGelonocodelon = truelon
            ) // notelon: simplelonRelonvelonrselonGelonocodelon melonans that country codelon will belon includelond in relonsponselon
          ).map { relonsponselon =>
            relonsponselon.found.gelont(ipAddrelonss) match {
              caselon Somelon(location) => gelontGelonohashAndCountryCodelonFromLocation(location)
              caselon _ => GelonohashAndCountryCodelon(Nonelon, Nonelon)
            }
          }
      }
  }

  privatelon delonf gelontGelonohashAndCountryCodelonFromLocation(location: Location): GelonohashAndCountryCodelon = {
    val countryCodelon: Option[String] = location.simplelonRgcRelonsult.flatMap { _.countryCodelonAlpha2 }

    val gelonohashString: Option[String] = location.gelonohash.flatMap { hash =>
      hash.stringGelonohash.flatMap { hashString =>
        Somelon(RelonvelonrselonGelonocodelonClielonnt.truncatelon(hashString))
      }
    }

    GelonohashAndCountryCodelon(gelonohashString, countryCodelon)
  }

}

objelonct RelonvelonrselonGelonocodelonClielonnt {

  val DelonfaultGelonoduckIPRelonquelonstContelonxt: GelonoContelonxt =
    GelonoContelonxt(allPlacelonTypelons = truelon, includelonGelonohash = truelon, includelonCountryCodelon = truelon)

  // All thelonselon gelonohashelons arelon guelonsselond by IP (Logical Location Sourcelon).
  // So takelon thelon four lelonttelonrs to makelon surelon it is consistelonnt with LocationSelonrvicelonClielonnt
  val GelonohashLelonngthAftelonrTruncation = 4
  delonf truncatelon(gelonohash: String): String = gelonohash.takelon(GelonohashLelonngthAftelonrTruncation)
}
