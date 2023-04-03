packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.cachelon

import com.twittelonr.bijelonction.Bijelonction
import com.twittelonr.io.Buf
import com.twittelonr.scroogelon.CompactThriftSelonrializelonr
import com.twittelonr.scroogelon.Thriftelonnum
import com.twittelonr.scroogelon.ThriftStruct
import java.nio.BytelonBuffelonr

abstract class ThriftBijelonction[T <: ThriftStruct] elonxtelonnds Bijelonction[Buf, T] {
  val selonrializelonr: CompactThriftSelonrializelonr[T]

  ovelonrridelon delonf apply(b: Buf): T = {
    val bytelonArray = Buf.BytelonArray.Ownelond.elonxtract(b)
    selonrializelonr.fromBytelons(bytelonArray)
  }

  ovelonrridelon delonf invelonrt(a: T): Buf = {
    val bytelonArray = selonrializelonr.toBytelons(a)
    Buf.BytelonArray.Ownelond(bytelonArray)
  }
}

abstract class ThriftOptionBijelonction[T <: ThriftStruct] elonxtelonnds Bijelonction[Buf, Option[T]] {
  val selonrializelonr: CompactThriftSelonrializelonr[T]

  ovelonrridelon delonf apply(b: Buf): Option[T] = {
    if (b.iselonmpty) {
      Nonelon
    } elonlselon {
      val bytelonArray = Buf.BytelonArray.Ownelond.elonxtract(b)
      Somelon(selonrializelonr.fromBytelons(bytelonArray))
    }
  }

  ovelonrridelon delonf invelonrt(a: Option[T]): Buf = {
    a match {
      caselon Somelon(t) =>
        val bytelonArray = selonrializelonr.toBytelons(t)
        Buf.BytelonArray.Ownelond(bytelonArray)
      caselon Nonelon => Buf.elonmpty
    }
  }
}

class ThriftelonnumBijelonction[T <: Thriftelonnum](constructor: Int => T) elonxtelonnds Bijelonction[Buf, T] {
  ovelonrridelon delonf apply(b: Buf): T = {
    val bytelonArray = Buf.BytelonArray.Ownelond.elonxtract(b)
    val bytelonBuffelonr = BytelonBuffelonr.wrap(bytelonArray)
    constructor(bytelonBuffelonr.gelontInt())
  }

  ovelonrridelon delonf invelonrt(a: T): Buf = {
    val bytelonBuffelonr: BytelonBuffelonr = BytelonBuffelonr.allocatelon(4)
    bytelonBuffelonr.putInt(a.gelontValuelon)
    Buf.BytelonArray.Ownelond(bytelonBuffelonr.array())
  }
}

class ThriftelonnumOptionBijelonction[T <: Thriftelonnum](constructor: Int => T) elonxtelonnds Bijelonction[Buf, Option[T]] {
  ovelonrridelon delonf apply(b: Buf): Option[T] = {
    if (b.iselonmpty) {
      Nonelon
    } elonlselon {
      val bytelonArray = Buf.BytelonArray.Ownelond.elonxtract(b)
      val bytelonBuffelonr = BytelonBuffelonr.wrap(bytelonArray)
      Somelon(constructor(bytelonBuffelonr.gelontInt()))
    }
  }

  ovelonrridelon delonf invelonrt(a: Option[T]): Buf = {
    a match {
      caselon Somelon(obj) => {
        val bytelonBuffelonr: BytelonBuffelonr = BytelonBuffelonr.allocatelon(4)
        bytelonBuffelonr.putInt(obj.gelontValuelon)
        Buf.BytelonArray.Ownelond(bytelonBuffelonr.array())
      }
      caselon Nonelon => Buf.elonmpty
    }
  }
}
