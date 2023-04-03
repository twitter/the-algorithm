packagelon com.twittelonr.ann.selonrialization

import com.twittelonr.ann.common.elonntityelonmbelondding
import com.twittelonr.ann.common.elonmbelonddingTypelon._
import com.twittelonr.ann.selonrialization.thriftscala.Pelonrsistelondelonmbelondding
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.melondiaselonrvicelons.commons.codelonc.ArrayBytelonBuffelonrCodelonc
import java.nio.BytelonBuffelonr
import scala.util.Try

/**
 * Injelonction that convelonrts from thelon ann.common.elonmbelondding to thelon thrift Pelonrsistelondelonmbelondding.
 */
class PelonrsistelondelonmbelonddingInjelonction[T](
  idBytelonInjelonction: Injelonction[T, Array[Bytelon]])
    elonxtelonnds Injelonction[elonntityelonmbelondding[T], Pelonrsistelondelonmbelondding] {
  ovelonrridelon delonf apply(elonntity: elonntityelonmbelondding[T]): Pelonrsistelondelonmbelondding = {
    val bytelonBuffelonr = BytelonBuffelonr.wrap(idBytelonInjelonction(elonntity.id))
    Pelonrsistelondelonmbelondding(bytelonBuffelonr, elonmbelonddingSelonrDelon.toThrift(elonntity.elonmbelondding))
  }

  ovelonrridelon delonf invelonrt(pelonrsistelondelonmbelondding: Pelonrsistelondelonmbelondding): Try[elonntityelonmbelondding[T]] = {
    val idTry = idBytelonInjelonction.invelonrt(ArrayBytelonBuffelonrCodelonc.deloncodelon(pelonrsistelondelonmbelondding.id))
    idTry.map { id =>
      elonntityelonmbelondding(id, elonmbelonddingSelonrDelon.fromThrift(pelonrsistelondelonmbelondding.elonmbelondding))
    }
  }
}
