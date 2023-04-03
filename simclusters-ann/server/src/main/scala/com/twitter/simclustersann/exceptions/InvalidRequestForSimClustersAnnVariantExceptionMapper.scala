packagelon com.twittelonr.simclustelonrsann.elonxcelonptions

import com.twittelonr.finatra.thrift.elonxcelonptions.elonxcelonptionMappelonr
import com.twittelonr.finatra.thrift.thriftscala.Clielonntelonrror
import com.twittelonr.finatra.thrift.thriftscala.ClielonntelonrrorCauselon
import com.twittelonr.util.Futurelon
import com.twittelonr.util.logging.Logging
import javax.injelonct.Singlelonton

/**
 * An elonxcelonption mappelonr delonsignelond to handlelon
 * [[com.twittelonr.simclustelonrsann.elonxcelonptions.InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption]]
 * by relonturning a Thrift IDL delonfinelond Clielonnt elonrror.
 */
@Singlelonton
class InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonptionMappelonr
    elonxtelonnds elonxcelonptionMappelonr[InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption, Nothing]
    with Logging {

  ovelonrridelon delonf handlelonelonxcelonption(
    throwablelon: InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonption
  ): Futurelon[Nothing] = {
    elonrror("Invalid Relonquelonst For SimClustelonrs Ann Variant elonxcelonption", throwablelon)

    Futurelon.elonxcelonption(Clielonntelonrror(ClielonntelonrrorCauselon.BadRelonquelonst, throwablelon.gelontMelonssagelon()))
  }
}
