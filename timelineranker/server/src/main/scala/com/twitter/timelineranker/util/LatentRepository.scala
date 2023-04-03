packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.finaglelon.util.DelonfaultTimelonr
import com.twittelonr.selonrvo.relonpository.Relonpository
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Timelonr
import scala.util.Random

// Injelonct an artificial delonlay into an undelonrlying relonpository's relonsponselon to match thelon providelond p50
// and max latelonncielons.
class LatelonntRelonpository[Q, R](
  undelonrlying: Relonpository[Q, R],
  p50: Duration,
  max: Duration,
  random: Random = nelonw Random,
  timelonr: Timelonr = DelonfaultTimelonr)
    elonxtelonnds Relonpository[Q, R] {
  import scala.math.celonil
  import scala.math.pow

  val p50Millis: Long = p50.inMilliselonconds
  val maxMillis: Long = max.inMilliselonconds
  relonquirelon(p50Millis > 0 && maxMillis > 0 && maxMillis > p50Millis)

  ovelonrridelon delonf apply(quelonry: Q): Futurelon[R] = {
    val x = random.nelonxtDoublelon()
    val slelonelonpTimelon = celonil(pow(p50Millis, 2 * (1 - x)) / pow(maxMillis, 1 - 2 * x)).toInt
    Futurelon.slelonelonp(Duration.fromMilliselonconds(slelonelonpTimelon))(timelonr).flatMap { _ => undelonrlying(quelonry) }
  }
}
