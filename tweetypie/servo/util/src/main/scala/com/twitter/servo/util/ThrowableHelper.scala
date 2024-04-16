package com.tw ter.servo.ut l

 mport com.tw ter.ut l.Throwables

/**
 * An object w h so   lper  thods for deal ng w h except ons
 * (currently just classna  cleanup)
 */
object Throwable lper {

  /**
   * Returns a san  zed sequence of classna  for t  g ven Throwable
   *  nclud ng root causes.
   */
  def san  zeClassna Cha n(t: Throwable): Seq[Str ng] =
    Throwables.mkStr ng(t).map(classna Transform(_))

  /**
   * Returns a san  zed classna  for t  g ven Throwable.
   */
  def san  zeClassna (t: Throwable): Str ng =
    classna Transform(t.getClass.getNa )

  /**
   * A funct on that appl es a bunch of cleanup transformat ons to except on classna s
   * (currently just 1, but t re w ll l kely be more!).
   */
  pr vate val classna Transform: Str ng => Str ng =
     mo ze { str pSuff x("$ mmutable").andT n(str pSuff x("$")) }

  /**
   * Generates a funct on that str ps off t  spec f ed suff x from str ngs,  f found.
   */
  pr vate def str pSuff x(suff x: Str ng): Str ng => Str ng =
    s => {
       f (s.endsW h(suff x))
        s.substr ng(0, s.length - suff x.length)
      else
        s
    }
}
