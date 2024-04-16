package com.tw ter.follow_recom ndat ons.common.ut ls

/**
 * Typeclass for any Recom ndat on type that has a   ght
 *
 */
tra    ghted[-Rec] {
  def apply(rec: Rec): Double
}

object   ghted {
   mpl c  object   ghtedTuple extends   ghted[(_, Double)] {
    overr de def apply(rec: (_, Double)): Double = rec._2
  }

  def fromFunct on[Rec](f: Rec => Double):   ghted[Rec] = {
    new   ghted[Rec] {
      overr de def apply(rec: Rec): Double = f(rec)
    }
  }
}
