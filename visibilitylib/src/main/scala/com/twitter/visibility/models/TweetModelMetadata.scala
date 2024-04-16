package com.tw ter.v s b l y.models

 mport com.tw ter.spam.rtf.{thr ftscala => s}

case class T etModel tadata(
  vers on: Opt on[ nt] = None,
  cal bratedLanguage: Opt on[Str ng] = None)

object T etModel tadata {

  def fromThr ft( tadata: s.Model tadata): Opt on[T etModel tadata] = {
     tadata match {
      case s.Model tadata.Model tadataV1(s.Model tadataV1(vers on, cal bratedLanguage)) =>
        So (T etModel tadata(vers on, cal bratedLanguage))
      case _ => None
    }
  }

  def toThr ft( tadata: T etModel tadata): s.Model tadata = {
    s.Model tadata.Model tadataV1(
      s.Model tadataV1( tadata.vers on,  tadata.cal bratedLanguage))
  }
}
