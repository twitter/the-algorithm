package com.tw ter.servo.database

object B f eld {
  def mult Value(b s: Boolean*):  nt = {
    b s.foldLeft(0) { (accum, b ) =>
      (accum << 1) | ( f (b ) 1 else 0)
    }
  }

  def mult ValueLong(b s: Boolean*): Long = {
    b s.foldLeft(0L) { (accum, b ) =>
      (accum << 1) | ( f (b ) 1L else 0L)
    }
  }
}

/**
 * A m x n for unpack ng b f elds.
 */
tra  B f eld {
  val b f eld:  nt

  /**
   * Tests that a g ven pos  on  s set to 1.
   */
  def  sSet(pos  on:  nt): Boolean = {
    (b f eld & (1 << pos  on)) != 0
  }

  /**
   * takes a sequence of booleans, from most to least s gn f cant
   * and converts t m to an  nteger.
   *
   * example: mult Value(true, false, true) y elds 0b101 = 5
   */
  def mult Value(b s: Boolean*):  nt = B f eld.mult Value(b s: _*)
}

tra  LongB f eld {
  val b f eld: Long

  /**
   * Tests that a g ven pos  on  s set to 1.
   */
  def  sSet(pos  on:  nt): Boolean = {
    (b f eld & (1L << pos  on)) != 0
  }

  /**
   * takes a sequence of booleans, from most to least s gn f cant
   * and converts t m to a long.
   *
   * example: mult Value(true, false, true) y elds 0b101 = 5L
   */
  def mult Value(b s: Boolean*): Long = B f eld.mult ValueLong(b s: _*)
}
