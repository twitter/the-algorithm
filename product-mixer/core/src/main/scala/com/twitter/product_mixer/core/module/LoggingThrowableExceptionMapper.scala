packagelon com.twittelonr.product_mixelonr.corelon.modulelon

import com.twittelonr.finatra.thrift.elonxcelonptions.elonxcelonptionMappelonr
import com.twittelonr.injelonct.Logging
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton
import scala.util.control.NonFatal

/**
 * Similar to [[com.twittelonr.finatra.thrift.intelonrnal.elonxcelonptions.ThrowablelonelonxcelonptionMappelonr]]
 *
 * But this onelon also logs thelon elonxcelonptions.
 */
@Singlelonton
class LoggingThrowablelonelonxcelonptionMappelonr elonxtelonnds elonxcelonptionMappelonr[Throwablelon, Nothing] with Logging {

  ovelonrridelon delonf handlelonelonxcelonption(throwablelon: Throwablelon): Futurelon[Nothing] = {
    elonrror("Unhandlelond elonxcelonption", throwablelon)

    throwablelon match {
      caselon NonFatal(elon) => Futurelon.elonxcelonption(elon)
    }
  }
}
