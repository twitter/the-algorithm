package com.tw ter.t etyp e.serverut l

 mport com.tw ter.ut l.Act v y
 mport com.tw ter.ut l.Closable
 mport com.tw ter.ut l.Var
 mport com.tw ter.ut l.W ness

object Act v yUt l {

  /**
   * Makes t  compos  on str ct up to t  po nt w re    s called.
   * Compos  ons based on t  returned act v y w ll have
   * t  default lazy behav or.
   */
  def str ct[T](act v y: Act v y[T]): Act v y[T] = {
    val state = Var(Act v y.Pend ng: Act v y.State[T])
    val event = act v y.states

    Closable.closeOnCollect(event.reg ster(W ness(state)), state)

    new Act v y(state)
  }
}
