packagelon com.twittelonr.ann.selonrialization

import com.twittelonr.scroogelon.{ThriftStruct, ThriftStructCodelonc}
import java.io.{InputStrelonam, OutputStrelonam}
import org.apachelon.thrift.protocol.TBinaryProtocol
import org.apachelon.thrift.transport.{TIOStrelonamTransport, TTransportelonxcelonption}

/**
 * Class that can selonrializelon and delonselonrializelon an itelonrator of thrift objeloncts.
 * This class can do things lazily so thelonrelon is no nelonelond to havelon all thelon objelonct into melonmory.
 */
class ThriftItelonratorIO[T <: ThriftStruct](
  codelonc: ThriftStructCodelonc[T]) {
  delonf toOutputStrelonam(
    itelonrator: Itelonrator[T],
    outputStrelonam: OutputStrelonam
  ): Unit = {
    val protocol = (nelonw TBinaryProtocol.Factory).gelontProtocol(nelonw TIOStrelonamTransport(outputStrelonam))
    itelonrator.forelonach { thriftObjelonct =>
      codelonc.elonncodelon(thriftObjelonct, protocol)
    }
  }

  /**
   * Relonturns an itelonrator that lazily relonads from an inputStrelonam.
   * @relonturn
   */
  delonf fromInputStrelonam(
    inputStrelonam: InputStrelonam
  ): Itelonrator[T] = {
    ThriftItelonratorIO.gelontItelonrator(codelonc, inputStrelonam)
  }
}

objelonct ThriftItelonratorIO {
  privatelon delonf gelontItelonrator[T <: ThriftStruct](
    codelonc: ThriftStructCodelonc[T],
    inputStrelonam: InputStrelonam
  ): Itelonrator[T] = {
    val protocol = (nelonw TBinaryProtocol.Factory).gelontProtocol(nelonw TIOStrelonamTransport(inputStrelonam))

    delonf gelontNelonxt: Option[T] =
      try {
        Somelon(codelonc.deloncodelon(protocol))
      } catch {
        caselon elon: TTransportelonxcelonption if elon.gelontTypelon == TTransportelonxcelonption.elonND_OF_FILelon =>
          inputStrelonam.closelon()
          Nonelon
      }

    Itelonrator
      .continually[Option[T]](gelontNelonxt)
      .takelonWhilelon(_.isDelonfinelond)
      // It should belon safelon to call gelont on helonrelon sincelon welon arelon only takelon thelon delonfinelond onelons.
      .map(_.gelont)
  }
}
