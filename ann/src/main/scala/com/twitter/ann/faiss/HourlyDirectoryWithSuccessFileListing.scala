package com.tw ter.ann.fa ss

 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.search.common.f le.AbstractF le
 mport com.tw ter.search.common.f le.F leUt ls
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport java.ut l.Locale

object H lyD rectoryW hSuccessF leL st ng extends Logg ng {
  pr vate val SUCCESS_F LE_NAME = "_SUCCESS"

  def l stH ly ndexD rector es(
    root: AbstractF le,
    start ngFrom: T  ,
    count:  nt,
    lookback nterval:  nt
  ): Seq[AbstractF le] = l st ngStep(root, start ngFrom, count, lookback nterval)

  pr vate def l st ngStep(
    root: AbstractF le,
    start ngFrom: T  ,
    rema n ngD rector esToF nd:  nt,
    rema n ngAttempts:  nt
  ): L st[AbstractF le] = {
     f (rema n ngD rector esToF nd == 0 || rema n ngAttempts == 0) {
      return L st.empty
    }

    val  ad = getSuccessfulD rectoryForDate(root, start ngFrom)

    val prev ousH  = start ngFrom - 1.h 

     ad match {
      case Throw(e) =>
        l st ngStep(root, prev ousH , rema n ngD rector esToF nd, rema n ngAttempts - 1)
      case Return(d rectory) =>
        d rectory ::
          l st ngStep(root, prev ousH , rema n ngD rector esToF nd - 1, rema n ngAttempts - 1)
    }
  }

  pr vate def getSuccessfulD rectoryForDate(
    root: AbstractF le,
    date: T  
  ): Try[AbstractF le] = {
    val folder = root.getPath + "/" + date.format("yyyy/MM/dd/HH", Locale.ROOT)
    val successPath =
      folder + "/" + SUCCESS_F LE_NAME

    debug(s"C ck ng ${successPath}")

    Try(F leUt ls.getF leHandle(successPath)).flatMap { f le =>
       f (f le.canRead) {
        Try(F leUt ls.getF leHandle(folder))
      } else {
        Throw(new  llegalArgu ntExcept on(s"Found ${f le.toStr ng} but can't read  "))
      }
    }
  }
}
