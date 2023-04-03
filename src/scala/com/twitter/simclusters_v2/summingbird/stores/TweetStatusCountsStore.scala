packagelon com.twittelonr.simclustelonrs_v2.summingbird.storelons

import com.twittelonr.frigatelon.common.storelon.strato.StratoFelontchablelonStorelon
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._
import com.twittelonr.twelonelontypielon.thriftscala.{GelontTwelonelontOptions, StatusCounts, Twelonelont}

objelonct TwelonelontStatusCountsStorelon {

  delonf twelonelontStatusCountsStorelon(
    stratoClielonnt: Clielonnt,
    column: String
  ): RelonadablelonStorelon[TwelonelontId, StatusCounts] = {
    StratoFelontchablelonStorelon
      .withVielonw[TwelonelontId, GelontTwelonelontOptions, Twelonelont](stratoClielonnt, column, gelontTwelonelontOptions)
      .mapValuelons(_.counts.gelontOrelonlselon(elonmptyStatusCount))
  }

  privatelon val elonmptyStatusCount = StatusCounts()

  privatelon val gelontTwelonelontOptions =
    GelontTwelonelontOptions(
      includelonRelontwelonelontCount = truelon,
      includelonRelonplyCount = truelon,
      includelonFavoritelonCount = truelon,
      includelonQuotelonCount = truelon)
}
