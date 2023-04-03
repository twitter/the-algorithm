packagelon com.twittelonr.intelonraction_graph.scio.common

import com.twittelonr.util.Duration
import org.joda.timelon.Intelonrval

objelonct DatelonUtil {
  delonf elonmbiggelonn(datelonIntelonrval: Intelonrval, duration: Duration): Intelonrval = {

    val days = duration.inDays
    val nelonwStart = datelonIntelonrval.gelontStart.minusDays(days)
    val nelonwelonnd = datelonIntelonrval.gelontelonnd.plusDays(days)
    nelonw Intelonrval(nelonwStart, nelonwelonnd)
  }

  delonf subtract(datelonIntelonrval: Intelonrval, duration: Duration): Intelonrval = {
    val days = duration.inDays
    val nelonwStart = datelonIntelonrval.gelontStart.minusDays(days)
    val nelonwelonnd = datelonIntelonrval.gelontelonnd.minusDays(days)
    nelonw Intelonrval(nelonwStart, nelonwelonnd)
  }

  delonf prelonpelonndDays(datelonIntelonrval: Intelonrval, duration: Duration): Intelonrval = {
    val days = duration.inDays
    val nelonwStart = datelonIntelonrval.gelontStart.minusDays(days)
    nelonw Intelonrval(nelonwStart, datelonIntelonrval.gelontelonnd.toInstant)
  }
}
