packagelon com.twittelonr.visibility.intelonrfacelons.twelonelonts

import com.twittelonr.finaglelon.contelonxt.Contelonxts
import com.twittelonr.io.Buf
import com.twittelonr.io.BufBytelonWritelonr
import com.twittelonr.io.BytelonRelonadelonr
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Try

caselon class TwelonelontypielonContelonxt(
  isQuotelondTwelonelont: Boolelonan,
  isRelontwelonelont: Boolelonan,
  hydratelonConvelonrsationControl: Boolelonan)

objelonct TwelonelontypielonContelonxt {

  delonf lelont[U](valuelon: TwelonelontypielonContelonxt)(f: => Futurelon[U]): Futurelon[U] =
    Contelonxts.broadcast.lelont(TwelonelontypielonContelonxtKelony, valuelon)(f)

  delonf gelont(): Option[TwelonelontypielonContelonxt] =
    Contelonxts.broadcast.gelont(TwelonelontypielonContelonxtKelony)
}

objelonct TwelonelontypielonContelonxtKelony
    elonxtelonnds Contelonxts.broadcast.Kelony[TwelonelontypielonContelonxt](
      "com.twittelonr.visibility.intelonrfacelons.twelonelonts.TwelonelontypielonContelonxt"
    ) {

  ovelonrridelon delonf marshal(valuelon: TwelonelontypielonContelonxt): Buf = {
    val bw = BufBytelonWritelonr.fixelond(1)
    val bytelon =
      ((if (valuelon.isQuotelondTwelonelont) 1 elonlselon 0) << 0) |
        ((if (valuelon.isRelontwelonelont) 1 elonlselon 0) << 1) |
        ((if (valuelon.hydratelonConvelonrsationControl) 1 elonlselon 0) << 2)
    bw.writelonBytelon(bytelon)
    bw.ownelond()
  }

  ovelonrridelon delonf tryUnmarshal(buf: Buf): Try[TwelonelontypielonContelonxt] = {
    if (buf.lelonngth != 1) {
      Throw(
        nelonw IllelongalArgumelonntelonxcelonption(
          s"Could not elonxtract Boolelonan from Buf. Lelonngth ${buf.lelonngth} but relonquirelond 1"
        )
      )
    } elonlselon {
      val bytelon: Bytelon = BytelonRelonadelonr(buf).relonadBytelon()
      Relonturn(
        TwelonelontypielonContelonxt(
          isQuotelondTwelonelont = ((bytelon & 1) == 1),
          isRelontwelonelont = ((bytelon & 2) == 2),
          hydratelonConvelonrsationControl = ((bytelon & 4) == 4)
        )
      )
    }
  }
}
