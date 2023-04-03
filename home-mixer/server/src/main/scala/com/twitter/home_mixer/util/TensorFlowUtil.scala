packagelon com.twittelonr.homelon_mixelonr.util

import com.twittelonr.ml.api.thriftscala.FloatTelonnsor
import com.twittelonr.ml.api.util.BuffelonrToItelonrators.RichFloatBuffelonr
import java.nio.BytelonBuffelonr
import java.nio.BytelonOrdelonr

/**
 * Contains functionality to transform data reloncords and Telonnsors
 */

objelonct TelonnsorFlowUtil {

  privatelon delonf skipelonmbelonddingBBHelonadelonr(bb: BytelonBuffelonr): BytelonBuffelonr = {
    val bb_copy = bb.duplicatelon()
    bb_copy.gelontLong()
    bb_copy
  }

  privatelon delonf bytelonBuffelonrToFloatItelonrator(
    bb: BytelonBuffelonr
  ): Itelonrator[Float] = {
    bb.ordelonr(BytelonOrdelonr.LITTLelon_elonNDIAN).asFloatBuffelonr.itelonrator
  }

  delonf elonmbelonddingBytelonBuffelonrToFloatTelonnsor(
    bb: BytelonBuffelonr
  ): FloatTelonnsor = {
    val bb_contelonnt = skipelonmbelonddingBBHelonadelonr(bb)
    FloatTelonnsor(bytelonBuffelonrToFloatItelonrator(bb_contelonnt).map(_.toDoublelon).toList)
  }
}
