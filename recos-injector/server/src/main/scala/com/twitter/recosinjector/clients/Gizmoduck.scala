packagelon com.twittelonr.reloncosinjelonctor.clielonnts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon

class Gizmoduck(
  uselonrStorelon: RelonadablelonStorelon[Long, Uselonr]
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val log = Loggelonr()
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  delonf gelontUselonr(uselonrId: Long): Futurelon[Option[Uselonr]] = {
    uselonrStorelon
      .gelont(uselonrId)
      .relonscuelon {
        caselon elon =>
          stats.scopelon("gelontUselonrFailurelon").countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          log.elonrror(s"Failelond with melonssagelon ${elon.toString}")
          Futurelon.Nonelon
      }
  }
}
