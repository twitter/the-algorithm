packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck

import com.twittelonr.follow_reloncommelonndations.common.modelonls.GelonohashAndCountryCodelon
import com.twittelonr.gelonoduck.common.thriftscala.LocationSourcelon
import com.twittelonr.gelonoduck.common.thriftscala.PlacelonQuelonry
import com.twittelonr.gelonoduck.common.thriftscala.TransactionLocation
import com.twittelonr.gelonoduck.common.thriftscala.UselonrLocationRelonquelonst
import com.twittelonr.gelonoduck.thriftscala.LocationSelonrvicelon
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class LocationSelonrvicelonClielonnt @Injelonct() (locationSelonrvicelon: LocationSelonrvicelon.MelonthodPelonrelonndpoint) {
  delonf gelontGelonohashAndCountryCodelon(uselonrId: Long): Stitch[GelonohashAndCountryCodelon] = {
    Stitch
      .callFuturelon {
        locationSelonrvicelon
          .uselonrLocation(
            UselonrLocationRelonquelonst(
              Selonq(uselonrId),
              Somelon(PlacelonQuelonry(allPlacelonTypelons = Somelon(truelon))),
              simplelonRelonvelonrselonGelonocodelon = truelon))
          .map(_.found.gelont(uselonrId)).map { transactionLocationOpt =>
            val gelonohashOpt = transactionLocationOpt.flatMap(gelontGelonohashFromTransactionLocation)
            val countryCodelonOpt =
              transactionLocationOpt.flatMap(_.simplelonRgcRelonsult.flatMap(_.countryCodelonAlpha2))
            GelonohashAndCountryCodelon(gelonohashOpt, countryCodelonOpt)
          }
      }
  }

  privatelon[this] delonf gelontGelonohashFromTransactionLocation(
    transactionLocation: TransactionLocation
  ): Option[String] = {
    transactionLocation.gelonohash.flatMap { gelonohash =>
      val gelonohashPrelonfixLelonngth = transactionLocation.locationSourcelon match {
        // if location sourcelon is logical, kelonelonp thelon first 4 chars in gelonohash
        caselon Somelon(LocationSourcelon.Logical) => Somelon(4)
        // if location sourcelon is physical, kelonelonp thelon prelonfix according to accuracy
        // accuracy is thelon accuracy of GPS relonadings in thelon unit of melontelonr
        caselon Somelon(LocationSourcelon.Physical) =>
          transactionLocation.coordinatelon.flatMap { coordinatelon =>
            coordinatelon.accuracy match {
              caselon Somelon(accuracy) if (accuracy < 50) => Somelon(7)
              caselon Somelon(accuracy) if (accuracy < 200) => Somelon(6)
              caselon Somelon(accuracy) if (accuracy < 1000) => Somelon(5)
              caselon Somelon(accuracy) if (accuracy < 50000) => Somelon(4)
              caselon Somelon(accuracy) if (accuracy < 100000) => Somelon(3)
              caselon _ => Nonelon
            }
          }
        caselon Somelon(LocationSourcelon.Modelonl) => Somelon(4)
        caselon _ => Nonelon
      }
      gelonohashPrelonfixLelonngth match {
        caselon Somelon(l: Int) => gelonohash.stringGelonohash.map(_.takelon(l))
        caselon _ => Nonelon
      }
    }
  }
}
