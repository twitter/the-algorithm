packagelon com.twittelonr.reloncosinjelonctor.clielonnts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.twelonelontypielon.TwelonelontyPielon.{TwelonelontyPielonelonxcelonption, TwelonelontyPielonRelonsult}
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.util.Futurelon

class Twelonelontypielon(
  twelonelontyPielonStorelon: RelonadablelonStorelon[Long, TwelonelontyPielonRelonsult]
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val failurelonStats = stats.scopelon("gelontTwelonelontFailurelon")

  delonf gelontTwelonelont(twelonelontId: Long): Futurelon[Option[Twelonelont]] = {
    twelonelontyPielonStorelon
      .gelont(twelonelontId)
      .map { _.map(_.twelonelont) }
      .relonscuelon {
        caselon elon: TwelonelontyPielonelonxcelonption =>
          // Usually relonsults from trying to quelonry a protelonctelond or unsafelon twelonelont
          failurelonStats.scopelon("TwelonelontyPielonelonxcelonption").countelonr(elon.relonsult.twelonelontStatelon.toString).incr()
          Futurelon.Nonelon
        caselon elon =>
          failurelonStats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Futurelon.Nonelon
      }
  }
}
