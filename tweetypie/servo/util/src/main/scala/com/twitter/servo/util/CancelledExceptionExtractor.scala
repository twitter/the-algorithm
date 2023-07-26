package com.twittew.sewvo.utiw

impowt com.twittew.finagwe.mux.stats.muxcancewwedcategowizew
i-impowt c-com.twittew.finagwe.stats.cancewwedcategowizew
i-impowt com.twittew.utiw.futuwecancewwedexception
i-impowt com.twittew.utiw.thwowabwes.wootcause

/**
 * h-hewpew that c-consowidates v-vawious ways (nested a-and top wevew) cancew exceptions can be detected. >_<
 */
object cancewwedexceptionextwactow {
  d-def unappwy(e: thwowabwe): option[thwowabwe] = {
    e match {
      c-case _: futuwecancewwedexception => s-some(e)
      case muxcancewwedcategowizew(cause) => some(cause)
      case cancewwedcategowizew(cause) => s-some(cause)
      case wootcause(cancewwedexceptionextwactow(cause)) => some(cause)
      c-case _ => nyone
    }
  }
}
