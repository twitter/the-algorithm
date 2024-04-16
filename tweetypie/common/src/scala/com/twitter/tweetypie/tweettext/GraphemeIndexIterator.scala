package com.tw ter.t etyp e.t ettext

 mport com. bm. cu.text.Break erator

/**
 * Adapt t  [[Break erator]]  nterface to a scala [[ erator]]
 * over t  offsets of user-perce ved characters  n a Str ng.
 */
object Grap   ndex erator {

  /**
   * Produce an  erator over  nd ces  n t  str ng that mark t  end
   * of a user-perce ved character (grap  )
   */
  def ends(s: Str ng):  erator[Offset.CodeUn ] =
    // T  start of every grap   but t  f rst  s also a grap  
    // end. T  last grap   ends at t  end of t  str ng.
    starts(s).drop(1) ++  erator(Offset.CodeUn .length(s))

  /**
   * Produce an  erator over  nd ces  n t  str ng that mark t  start
   * of a user-perce ved character (grap  )
   */
  def starts(s: Str ng):  erator[Offset.CodeUn ] =
    new  erator[Offset.CodeUn ] {
      pr vate[t ] val   = Break erator.getCharacter nstance()

       .setText(s)

      overr de def hasNext: Boolean =  .current < s.length

      overr de def next: Offset.CodeUn  = {
         f (!hasNext) throw new  llegalArgu ntExcept on(s"${ .current()}, ${s.length}")

        // No matter what,   w ll be return ng t  value of `current`,
        // wh ch  s t   ndex of t  start of t  next grap  .
        val result =  .current()

         .next()

        Offset.CodeUn (result)
      }
    }
}
