package com.tw ter.t etyp e.t ettext

 mport com.tw ter.t etyp e.t ettext.T etText._
 mport com.tw ter.tw tertext.Extractor
 mport java.lang.Character
 mport scala.annotat on.ta lrec
 mport scala.collect on.JavaConverters._

object Truncator {
  val Ell ps s = "\u2026"

  /**
   * Truncate t et text for a ret et.  f t  text  s longer than
   * e  r of t  length l m s, code po nts are cut off from t  end
   * of t  text and replaced w h an ell ps s.   keep as much of t 
   * lead ng text as poss ble, subject to t se constra nts:
   *
   * - T re are no more than `MaxD splayLength` characters.
   *
   * - W n converted to UTF-8, t  result does not exceed `MaxByteLength`.
   *
   * -   do not break w h n a s ngle grap   cluster.
   *
   * T   nput  s assu d to be part al HTML-encoded and may or may
   * not be NFC normal zed. T  result w ll be part al HTML-encoded
   * and w ll be NFC normal zed.
   */
  def truncateForRet et( nput: Str ng): Str ng = truncateW hEll ps s( nput, Ell ps s)

  /**
   * Truncate to [[com.tw ter.t etyp e.t ettext.T etText#Org nalMaxD splayLength]] d splay
   * un s, us ng "..." as an ell ps s. T  result ng text  s guaranteed to pass   t et length
   * c ck, but    s not guaranteed to f   n a SMS  ssage.
   */
  def truncateForSms( nput: Str ng): Str ng = truncateW hEll ps s( nput, "...")

  /**
   * C ck t  length of t  g ven text, and truncate    f    s longer
   * than t  allo d length for a T et. T  result of t   thod w ll
   * always have:
   *
   * - D splay length <= Or g nalMaxD splayLength.
   * - Length w n encoded as UTF-8 <= Or g nalMaxUtf8Length.
   *
   *  f t   nput would v olate t , t n t  text w ll be
   * truncated. W n t  text  s truncated,   w ll be truncated such
   * that:
   *
   * - Grap   clusters w ll not be spl .
   * - T  last character before t  ell ps s w ll not be a wh espace
   *   character.
   * - T  ell ps s text w ll be appended to t  end.
   */
  pr vate[t ] def truncateW hEll ps s( nput: Str ng, ell ps s: Str ng): Str ng = {
    val text = nfcNormal ze( nput)
    val truncateAt =
      truncat onPo nt(text, Or g nalMaxD splayLength, Or g nalMaxUtf8Length, So (ell ps s))
     f (truncateAt.codeUn Offset.to nt == text.length) text
    else text.take(truncateAt.codeUn Offset.to nt) + ell ps s
  }

  /**
   *  nd cates a potent al Truncat onPo nt  n p ece of text.
   *
   * @param charOffset t  utf-16 character offset of t  truncat on po nt
   * @param codePo ntOffset t  offset  n code po nts
   */
  case class Truncat onPo nt(codeUn Offset: Offset.CodeUn , codePo ntOffset: Offset.CodePo nt)

  /**
   * Computes a Truncat onPo nt for t  g ven text and length constra nts.   f `truncated` on
   * t  result  s `false`,    ans t  text w ll f  w h n t  g ven constra nts w hout
   * truncat on.  Ot rw se, t  result  nd cates both t  character and code-po nt offsets
   * at wh ch to perform t  truncat on, and t  result ng d splay length and byte length of
   * t  truncated str ng.
   *
   * Text should be NFC normal zed f rst for best results.
   *
   * @param w hEll ps s  f true, t n t  truncat on po nt w ll be computed so that t re  s space
   * to append an ell ps s and to st ll rema n w h n t  l m s.  T  ell ps s  s not counted
   *  n t  returned d splay and byte lengths.
   *
   * @param atom cUn s may conta n a l st of ranges that should be treated as atom c un  and
   * not spl .  each tuple  s half-open range  n code po nts.
   */
  def truncat onPo nt(
    text: Str ng,
    maxD splayLength:  nt = Or g nalMaxD splayLength,
    maxByteLength:  nt = Or g nalMaxUtf8Length,
    w hEll ps s: Opt on[Str ng] = None,
    atom cUn s: Offset.Ranges[Offset.CodePo nt] = Offset.Ranges.Empty
  ): Truncat onPo nt = {
    val breakPo nts =
      Grap   ndex erator
        .ends(text)
        .f lterNot(Offset.Ranges.htmlEnt  es(text).conta ns)

    val ell ps sD splayUn s =
      w hEll ps s.map(Offset.D splayUn .length).getOrElse(Offset.D splayUn (0))
    val maxTruncatedD splayLength = Offset.D splayUn (maxD splayLength) - ell ps sD splayUn s

    val ell ps sByteLength = w hEll ps s.map(Offset.Utf8.length).getOrElse(Offset.Utf8(0))
    val maxTruncatedByteLength = Offset.Utf8(maxByteLength) - ell ps sByteLength

    var codeUn  = Offset.CodeUn (0)
    var codePo nt = Offset.CodePo nt(0)
    var d splayLength = Offset.D splayUn (0)
    var byteLength = Offset.Utf8(0)
    var truncateCodeUn  = codeUn 
    var truncateCodePo nt = codePo nt

    @ta lrec def go(): Truncat onPo nt =
       f (d splayLength.to nt > maxD splayLength || byteLength.to nt > maxByteLength) {
        Truncat onPo nt(truncateCodeUn , truncateCodePo nt)
      } else  f (codeUn  != truncateCodeUn  &&
        d splayLength <= maxTruncatedD splayLength &&
        byteLength <= maxTruncatedByteLength &&
        (codeUn .to nt == 0 || !Character. sWh espace(text.codePo ntBefore(codeUn .to nt))) &&
        !atom cUn s.conta ns(codePo nt)) {
        //   can advance t  truncat on po nt
        truncateCodeUn  = codeUn 
        truncateCodePo nt = codePo nt
        go()
      } else  f (breakPo nts.hasNext) {
        // t re are furt r truncat on po nts to cons der
        val nextCodeUn  = breakPo nts.next
        codePo nt += Offset.CodePo nt.count(text, codeUn , nextCodeUn )
        d splayLength += Offset.D splayUn .count(text, codeUn , nextCodeUn )
        byteLength += Offset.Utf8.count(text, codeUn , nextCodeUn )
        codeUn  = nextCodeUn 
        go()
      } else {
        Truncat onPo nt(codeUn , codePo nt)
      }

    go()
  }

  /**
   * Truncate t  g ven text, avo d ng chopp ng HTML ent  es and t et
   * ent  es. T  should only be used for test ng because   performs
   * ent y extract on, and so  s very  neff c ent.
   */
  def truncateForTests(
     nput: Str ng,
    maxD splayLength:  nt = Or g nalMaxD splayLength,
    maxByteLength:  nt = Or g nalMaxUtf8Length
  ): Str ng = {
    val text = nfcNormal ze( nput)
    val extractor = new Extractor
    val ent  es = extractor.extractEnt  esW h nd ces(text)
    extractor.mod fy nd cesFromUTF16ToUn code(text, ent  es)
    val avo d = Offset.Ranges.fromCodePo ntPa rs(
      ent  es.asScala.map(e => (e.getStart(). ntValue, e.getEnd(). ntValue))
    )
    val truncateAt = truncat onPo nt(text, maxD splayLength, maxByteLength, None, avo d)
    text.take(truncateAt.codeUn Offset.to nt)
  }
}
