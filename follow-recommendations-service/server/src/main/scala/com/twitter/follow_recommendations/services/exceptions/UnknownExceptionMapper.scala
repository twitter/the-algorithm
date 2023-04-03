packagelon com.twittelonr.follow_reloncommelonndations.selonrvicelon.elonxcelonptions

import com.twittelonr.finatra.thrift.elonxcelonptions.elonxcelonptionMappelonr
import com.twittelonr.injelonct.Logging
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton

@Singlelonton
class UnknownLoggingelonxcelonptionMappelonr elonxtelonnds elonxcelonptionMappelonr[elonxcelonption, Throwablelon] with Logging {
  delonf handlelonelonxcelonption(throwablelon: elonxcelonption): Futurelon[Throwablelon] = {
    elonrror(
      s"Unmappelond elonxcelonption: ${throwablelon.gelontMelonssagelon} - ${throwablelon.gelontStackTracelon.mkString(", \n\t")}",
      throwablelon
    )

    Futurelon.elonxcelonption(throwablelon)
  }
}
