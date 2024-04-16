package com.tw ter.t etyp e.t ettext
 mport scala.collect on. mmutable

/**
 * An Offset  s a typed  ndex  nto a Str ng.
 */
tra  Offset[T] extends Order ng[T] {
  def to nt(t: T):  nt
  def count(text: Str ng, start: Offset.CodeUn , end: Offset.CodeUn ): T

  def compare(t1: T, t2: T):  nt = to nt(t1).compare(to nt(t2))
  def length( nput: Str ng): T = count( nput, Offset.CodeUn (0), Offset.CodeUn .length( nput))
}

object Offset {

  /**
   * UTF-16 code un  offsets are t  nat ve offsets for Java/Scala
   * Str ngs.
   */
  case class CodeUn (to nt:  nt) extends AnyVal w h Ordered[CodeUn ] {
    def compare(ot r: CodeUn ):  nt = to nt.compare(ot r.to nt)
    def +(ot r: CodeUn ) = CodeUn (to nt + ot r.to nt)
    def -(ot r: CodeUn ) = CodeUn (to nt - ot r.to nt)
    def m n(ot r: CodeUn ): CodeUn  =  f (to nt < ot r.to nt) t  else ot r
    def max(ot r: CodeUn ): CodeUn  =  f (to nt > ot r.to nt) t  else ot r
    def  ncr: CodeUn  = CodeUn (to nt + 1)
    def decr: CodeUn  = CodeUn (to nt - 1)
    def unt l(end: CodeUn ):  mmutable. ndexedSeq[CodeUn ] =
      to nt.unt l(end.to nt).map(CodeUn (_))

    /**
     * Converts t  `CodeUn ` to t  equ valent `CodePo nt` w h n t 
     * g ven text.
     */
    def toCodePo nt(text: Str ng): CodePo nt =
      CodePo nt(text.codePo ntCount(0, to nt))

    def offsetByCodePo nts(text: Str ng, codePo nts: CodePo nt): CodeUn  =
      CodeUn (text.offsetByCodePo nts(to nt, codePo nts.to nt))
  }

   mpl c  object CodeUn  extends Offset[CodeUn ] {
    def to nt(u: CodeUn ):  nt = u.to nt
    overr de def length(text: Str ng): CodeUn  = CodeUn (text.length)
    def count(text: Str ng, start: CodeUn , end: CodeUn ): CodeUn  = end - start
  }

  /**
   * Offsets  n whole Un code code po nts. Any CodePo nt  s a val d
   * offset  nto t  Str ng as long as    s >= 0 and less than t 
   * number of code po nts  n t  str ng.
   */
  case class CodePo nt(to nt:  nt) extends AnyVal w h Ordered[CodePo nt] {
    def toShort: Short = to nt.toShort
    def compare(ot r: CodePo nt):  nt = to nt.compare(ot r.to nt)
    def +(ot r: CodePo nt) = CodePo nt(to nt + ot r.to nt)
    def -(ot r: CodePo nt) = CodePo nt(to nt - ot r.to nt)
    def m n(ot r: CodePo nt): CodePo nt =  f (to nt < ot r.to nt) t  else ot r
    def max(ot r: CodePo nt): CodePo nt =  f (to nt > ot r.to nt) t  else ot r
    def unt l(end: CodePo nt):  mmutable. ndexedSeq[CodePo nt] =
      to nt.unt l(end.to nt).map(CodePo nt(_))

    def toCodeUn (text: Str ng): CodeUn  =
      CodeUn (text.offsetByCodePo nts(0, to nt))
  }

   mpl c  object CodePo nt extends Offset[CodePo nt] {
    def to nt(p: CodePo nt):  nt = p.to nt

    def count(text: Str ng, start: CodeUn , end: CodeUn ): CodePo nt =
      CodePo nt(text.codePo ntCount(start.to nt, end.to nt))
  }

  /**
   * Offsets  nto t  Str ng as  f t  Str ng  re encoded as UTF-8.  
   * cannot use a [[Utf8]] offset to  ndex a Str ng, because not all
   * Utf8  nd ces are val d  nd ces  nto t  Str ng.
   */
  case class Utf8(to nt:  nt) extends AnyVal w h Ordered[Utf8] {
    def compare(ot r: Utf8):  nt = to nt.compare(ot r.to nt)
    def +(ot r: Utf8) = Utf8(to nt + ot r.to nt)
    def -(ot r: Utf8) = Utf8(to nt - ot r.to nt)
    def m n(ot r: Utf8): Utf8 =  f (to nt < ot r.to nt) t  else ot r
    def max(ot r: Utf8): Utf8 =  f (to nt > ot r.to nt) t  else ot r
  }

   mpl c  object Utf8 extends Offset[Utf8] {
    def to nt(u: Utf8):  nt = u.to nt

    /**
     * Count how many bytes t  sect on of text would be w n encoded as
     * UTF-8.
     */
    def count(s: Str ng, start: CodeUn , end: CodeUn ): Utf8 = {
      def go( : CodeUn , byteLength: Utf8): Utf8 =
         f (  < end) {
          val cp = s.codePo ntAt( .to nt)
          go(  + CodeUn (Character.charCount(cp)), byteLength + forCodePo nt(cp))
        } else {
          byteLength
        }

      go(start, Utf8(0))
    }

    /**
     * Unfortunately, t re  s no conven ent AP  for f nd ng out how many
     * bytes a un code code po nt would take  n UTF-8, so   have to
     * expl c ly calculate  .
     *
     * @see http://en.w k ped a.org/w k /UTF-8#Descr pt on
     */
    def forCodePo nt(cp:  nt): Utf8 =
      Utf8 {
        //  f t  code po nt  s an unpa red surrogate,   w ll be converted
        //  nto a 1 byte replace nt character
         f (Character.getType(cp) == Character.SURROGATE) 1
        else {
          cp match {
            case _  f cp < 0x80 => 1
            case _  f cp < 0x800 => 2
            case _  f cp < 0x10000 => 3
            case _ => 4
          }
        }
      }
  }

  /**
   * D splay un s count what   cons der a "character"  n a
   * T et. [[D splayUn ]] offsets are only val d for text that  s
   * NFC-normal zed (See: http://www.un code.org/reports/tr15) and
   * HTML-encoded, though t   nterface cannot enforce that.
   *
   * Currently, a [[D splayUn ]]  s equ valent to a s ngle Un code code
   * po nt comb ned w h treat ng "&lt;", "&gt;", and "&amp;" each as a
   * s ngle character (s nce t y are d splayed as '<', '>', and '&'
   * respect vely). T   mple ntat on  s not d rectly exposed.
   *
   *   should be poss ble to change t  def n  on w hout break ng
   * code that uses t  [[D splayUn ]]  nterface e.g. to count
   * user-perce ved characters (grap  s) rat r than code po nts,
   * though any change has to be made  n concert w h chang ng t 
   * mob le cl ent and  b  mple ntat ons so that t  user exper ence
   * of character count ng rema ns cons stent.
   */
  case class D splayUn (to nt:  nt) extends AnyVal w h Ordered[D splayUn ] {
    def compare(ot r: D splayUn ):  nt = to nt.compare(ot r.to nt)
    def +(ot r: D splayUn ) = D splayUn (to nt + ot r.to nt)
    def -(ot r: D splayUn ) = D splayUn (to nt - ot r.to nt)
    def m n(ot r: D splayUn ): D splayUn  =  f (to nt < ot r.to nt) t  else ot r
    def max(ot r: D splayUn ): D splayUn  =  f (to nt > ot r.to nt) t  else ot r
  }

   mpl c  object D splayUn  extends Offset[D splayUn ] {
    def to nt(d: D splayUn ):  nt = d.to nt

    /**
     * Returns t  number of d splay un s  n t  spec f ed range of t 
     * g ven text.  See [[D splayUn ]] for a descrpt on of what  
     * cons der a d splay un .
     *
     * T   nput str ng should already be NFC normal zed to get
     * cons stent results.   f part ally html encoded,   w ll correctly
     * count html ent  es as a s ngle d splay un .
     *
     * @param text t  str ng conta n ng t  characters to count.
     * @param t   ndex to t  f rst char of t  text range
     * @param t   ndex after t  last char of t  text range.
     */
    def count(text: Str ng, start: CodeUn , end: CodeUn ): D splayUn  = {
      val stop = end.m n(CodeUn .length(text))

      @annotat on.ta lrec
      def go(offset: CodeUn , total: D splayUn ): D splayUn  =
         f (offset >= stop) total
        else go(offset + at(text, offset), total + D splayUn (1))

      go(start, D splayUn (0))
    }

    /**
     * Return t  length of t  d splay un  at t  spec f ed offset  n
     * t  (NFC-normal zed, HTML-encoded) text.
     */
    def at(text: Str ng, offset: CodeUn ): CodeUn  =
      CodeUn  {
        text.codePo ntAt(offset.to nt) match {
          case '&' =>
             f (text.reg onMatc s(offset.to nt, "&amp;", 0, 5)) 5
            else  f (text.reg onMatc s(offset.to nt, "&lt;", 0, 4)) 4
            else  f (text.reg onMatc s(offset.to nt, "&gt;", 0, 4)) 4
            else 1

          case cp => Character.charCount(cp)
        }
      }
  }

  /**
   * Ranges of offsets, useful for avo d ng sl c ng ent  es.
   */
  sealed tra  Ranges[T] {
    def conta ns(t: T): Boolean
  }

  object Ranges {
    pr vate[t ] case class  mpl[T](toSeq: Seq[(T, T)])( mpl c  off: Offset[T])
        extends Ranges[T] {
      def conta ns(t: T): Boolean = toSeq.ex sts { case (lo, h ) => off.gt(t, lo) && off.lt(t, h ) }
    }

    /**
     * Non- nclus ve range of offsets (matc s values that are str ctly
     * bet en `h ` and `lo`)
     */
    def bet en[T](lo: T, h : T)( mpl c  off: Offset[T]): Ranges[T] =
       f (off.to nt(h ) > off.to nt(lo) + 1 && off.to nt(lo) <  nt.MaxValue)  mpl(Seq((lo, h )))
      else  mpl(N l)

    /**
     * T  un on of all of t  spec f ed ranges.
     */
    def all[T](ranges: Seq[Ranges[T]])( mpl c  off: Offset[T]): Ranges[T] =
       mpl(
        // Preprocess t  ranges so that each conta ns c ck  s as c ap
        // as poss ble.
        ranges
          .flatMap { case r:  mpl[T] => r.toSeq }
          .sortBy(_._1)
          .foldLeft(N l: L st[(T, T)]) {
            case ((a, b) :: out, (c, d))  f off.lt(c, b) => (a, d) :: out
            case (out, r) => r :: out
          }
      )

    def Empty[T: Offset]: Ranges[T] =  mpl[T](N l)

    pr vate[t ] val HtmlEscapes = """&(?:amp|lt|gt);""".r

    /**
     * Match [[CodeUn ]]s that would spl  a HTML ent y.
     */
    def htmlEnt  es(s: Str ng): Ranges[CodeUn ] = {
      val   = HtmlEscapes.f ndAll n(s)
      all( .map(_ => bet en(CodeUn ( .start), CodeUn ( .end))).toSeq)
    }

    def fromCodePo ntPa rs(pa rs: Seq[( nt,  nt)]): Ranges[CodePo nt] =
      all(pa rs.map { case (lo, h ) => bet en(CodePo nt(lo), CodePo nt(h )) })
  }
}
