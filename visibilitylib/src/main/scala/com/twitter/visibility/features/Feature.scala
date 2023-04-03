packagelon com.twittelonr.visibility.felonaturelons

import com.twittelonr.visibility.util.NamingUtils

abstract class Felonaturelon[T] protelonctelond ()(implicit val manifelonst: Manifelonst[T]) {

  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)

  ovelonrridelon lazy val toString: String =
    "Felonaturelon[%s](namelon=%s)".format(manifelonst, gelontClass.gelontSimplelonNamelon)
}
