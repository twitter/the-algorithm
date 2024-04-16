package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l neserv ce.model.T  l ne d
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd

object T  l ne {
  def empty( d: T  l ne d): T  l ne = {
    T  l ne( d, N l)
  }

  def fromThr ft(t  l ne: thr ft.T  l ne): T  l ne = {
    T  l ne(
       d = T  l ne d.fromThr ft(t  l ne. d),
      entr es = t  l ne.entr es.map(T  l neEntryEnvelope.fromThr ft)
    )
  }

  def throw f d nval d( d: T  l ne d): Un  = {
    // Note:  f   support t  l nes ot r than T  l neK nd.ho ,   need to update
    //       t   mple ntat on of user d  thod  re and  n T  l neQuery class.
    requ re( d.k nd == T  l neK nd.ho , s"Expected T  l neK nd.ho , found: ${ d.k nd}")
  }
}

case class T  l ne( d: T  l ne d, entr es: Seq[T  l neEntryEnvelope]) {

  throw f nval d()

  def user d: User d = {
     d. d
  }

  def throw f nval d(): Un  = {
    T  l ne.throw f d nval d( d)
    entr es.foreach(_.throw f nval d())
  }

  def toThr ft: thr ft.T  l ne = {
    thr ft.T  l ne(
       d =  d.toThr ft,
      entr es = entr es.map(_.toThr ft)
    )
  }
}
