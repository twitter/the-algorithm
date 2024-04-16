package com.tw ter.t etyp e. d a

 mport com.tw ter. d aserv ces.commons.thr ftscala._
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y

object  d aKeyUt l {

  def get( d aEnt y:  d aEnt y):  d aKey =
     d aEnt y. d aKey.getOrElse {
      throw new  llegalStateExcept on(""" d a key undef ned. T  state  s unexpected, t   d a
          |key should be set by t  t et creat on for new t ets
          |and by ` d aKeyHydrator` for legacy t ets.""".str pMarg n)
    }

  def contentType( d aKey:  d aKey):  d aContentType =
     d aKey. d aCategory match {
      case  d aCategory.T et mage =>  d aContentType. mageJpeg
      case  d aCategory.T etG f =>  d aContentType.V deoMp4
      case  d aCategory.T etV deo =>  d aContentType.V deoGener c
      case  d aCategory.Ampl fyV deo =>  d aContentType.V deoGener c
      case  d aCats => throw new Not mple ntedError( d aCats.toStr ng)
    }
}
