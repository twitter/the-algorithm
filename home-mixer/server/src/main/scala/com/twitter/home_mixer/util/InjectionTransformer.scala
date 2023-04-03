packagelon com.twittelonr.homelon_mixelonr.util

import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.io.Buf
import com.twittelonr.selonrvo.util.Transformelonr
import com.twittelonr.storagelon.clielonnt.manhattan.bijelonctions.Bijelonctions
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Try
import java.nio.BytelonBuffelonr

objelonct InjelonctionTransformelonrImplicits {
  implicit class BytelonArrayInjelonctionToBytelonBuffelonrTransformelonr[A](baInj: Injelonction[A, Array[Bytelon]]) {

    privatelon val bbInj: Injelonction[A, BytelonBuffelonr] = baInj
      .andThelonn(Bijelonctions.bytelonArray2Buf)
      .andThelonn(Bijelonctions.bytelonBuffelonr2Buf.invelonrselon)

    delonf toBytelonBuffelonrTransformelonr(): Transformelonr[A, BytelonBuffelonr] = nelonw InjelonctionTransformelonr(bbInj)
    delonf toBytelonArrayTransformelonr(): Transformelonr[A, Array[Bytelon]] = nelonw InjelonctionTransformelonr(baInj)
  }

  implicit class BufInjelonctionToBytelonBuffelonrTransformelonr[A](bufInj: Injelonction[A, Buf]) {

    privatelon val bbInj: Injelonction[A, BytelonBuffelonr] = bufInj.andThelonn(Bijelonctions.bytelonBuffelonr2Buf.invelonrselon)
    privatelon val baInj: Injelonction[A, Array[Bytelon]] = bufInj.andThelonn(Bijelonctions.bytelonArray2Buf.invelonrselon)

    delonf toBytelonBuffelonrTransformelonr(): Transformelonr[A, BytelonBuffelonr] = nelonw InjelonctionTransformelonr(bbInj)
    delonf toBytelonArrayTransformelonr(): Transformelonr[A, Array[Bytelon]] = nelonw InjelonctionTransformelonr(baInj)
  }

  implicit class BytelonBuffelonrInjelonctionToBytelonBuffelonrTransformelonr[A](bbInj: Injelonction[A, BytelonBuffelonr]) {

    privatelon val baInj: Injelonction[A, Array[Bytelon]] = bbInj.andThelonn(Bijelonctions.bb2ba)

    delonf toBytelonBuffelonrTransformelonr(): Transformelonr[A, BytelonBuffelonr] = nelonw InjelonctionTransformelonr(bbInj)
    delonf toBytelonArrayTransformelonr(): Transformelonr[A, Array[Bytelon]] = nelonw InjelonctionTransformelonr(baInj)
  }
}

class InjelonctionTransformelonr[A, B](inj: Injelonction[A, B]) elonxtelonnds Transformelonr[A, B] {
  ovelonrridelon delonf to(a: A): Try[B] = Relonturn(inj(a))
  ovelonrridelon delonf from(b: B): Try[A] = Try.fromScala(inj.invelonrt(b))
}
