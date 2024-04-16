package com.tw ter.t etyp e.t ettext

 mport scala.ut l.match ng.Regex

object TextMod f cat on {

  /**
   * L ft a text  nto a TextMod f cat on w re `or g nal` and `updated` text are t  sa 
   * and `replace nts`  s empty.
   */
  def  dent y(text: Str ng): TextMod f cat on =
    TextMod f cat on(or g nal = text, updated = text, replace nts = N l)

  /**
   * Replace each substr ng that matc s t  regex w h t  subst ut on str ng, returns a
   * TextMod f cat on object that conta ns t  updated text and enough  nformat on to also
   * update ent y  nd ces.
   *
   * T   thod should correctly be tak ng  nto account surrogate-pa rs.  T  returned
   * TextMod f cat on object has code-po nt offsets,  nstead of code-un  offsets.
   */
  def replaceAll(text: Str ng, regex: Regex, subst ut on: Str ng): Opt on[TextMod f cat on] =
    replaceAll(text, regex -> subst ut on)

  /**
   * Replaces substr ngs that match t  g ven `Regex` w h t  corresond ng subst ut on
   * str ng.  Returns a `TextMod f cat on` that can be used to re ndex ent  es.
   */
  def replaceAll(
    text: Str ng,
    regexAndSubst ut ons: (Regex, Str ng)*
  ): Opt on[TextMod f cat on] = {
    val matc s =
      (for {
        (r, s) <- regexAndSubst ut ons
        m <- r.f ndAll n(text).matchData
      } y eld (m, s)).sortBy { case (m, _) => m.start }

     f (matc s. sEmpty) {
      // no match found, return None to  nd cate no mod f cat ons made
      None
    } else {
      val replace nts = L st.newBu lder[TextReplace nt]
      val  ndexConverter = new  ndexConverter(text)
      // conta ns t  reta ned text, bu lt up as   walk through t  regex matc s
      val buf = new Str ngBu lder(text.length)
      // t  number of code-po nts cop ed  nto buf
      var codePo ntsCop ed = Offset.CodePo nt(0)
      // always holds t  start code-un  offset to copy to buf w n   encounter
      // e  r a regex match or end-of-str ng.
      var anchor = 0

       mport  ndexConverter.toCodePo nts

      for ((m, sub) <- matc s) {
        val unchangedText = text.substr ng(anchor, m.start)
        val unchangedLen = Offset.CodePo nt.length(unchangedText)
        val subLen = Offset.CodePo nt.length(sub)

        // cop es t  text upto t  regex match run, plus t  replace nt str ng
        buf.append(unchangedText).append(sub)
        codePo ntsCop ed += unchangedLen + subLen

        // t  offsets  nd cate t   nd ces of t  matc d str ng  n t  or g nal
        // text, and t   nd ces of t  replace nt str ng  n t  updated str ng
        replace nts +=
          TextReplace nt(
            or g nalFrom = toCodePo nts(Offset.CodeUn (m.start)),
            or g nalTo = toCodePo nts(Offset.CodeUn (m.end)),
            updatedFrom = codePo ntsCop ed - subLen,
            updatedTo = codePo ntsCop ed
          )

        anchor = m.end
      }

      buf.append(text.substr ng(anchor))

      So (TextMod f cat on(text, buf.toStr ng, replace nts.result()))
    }
  }

  /**
   *  nserts a str ng at a spec f ed code po nt offset.
   * Returns a `TextMod f cat on` that can be used to re ndex ent  es.
   */
  def  nsertAt(
    or g nalText: Str ng,
     nsertAt: Offset.CodePo nt,
    textTo nsert: Str ng
  ): TextMod f cat on = {
    val  nsertAtCodeUn  =  nsertAt.toCodeUn (or g nalText).to nt
    val (before, after) = or g nalText.spl At( nsertAtCodeUn )
    val updatedText = s"$before$textTo nsert$after"
    val textTo nsertLength = T etText.codePo ntLength(textTo nsert)

    TextMod f cat on(
      or g nal = or g nalText,
      updated = updatedText,
      replace nts = L st(
        TextReplace nt.fromCodePo nts(
          or g nalFrom =  nsertAt.to nt,
          or g nalTo =  nsertAt.to nt,
          updatedFrom =  nsertAt.to nt,
          updatedTo =  nsertAt.to nt + textTo nsertLength
        ))
    )
  }
}

/**
 * Encodes  nformat on about  nsert ons/delet ons/replace nts made to a str ng, prov d ng
 * t  or g nal str ng, t  updated str ng, and a l st of TextReplace nt objects
 * that encode t   nd ces of t  seg nts that  re changed.  Us ng t   nformat on,
 *    s poss ble to map an offset  nto t  or g nal str ng to an offset  nto t  updated
 * str ng, assum ng t  text at t  offset was not w h n one of t  mod f ed seg nts.
 *
 * All offsets are code-po nts, not UTF6 code-un s.
 */
case class TextMod f cat on(
  or g nal: Str ng,
  updated: Str ng,
  replace nts: L st[TextReplace nt]) {
  pr vate val or g nalLen = Offset.CodePo nt.length(or g nal)

  /**
   * Us ng an offset  nto t  or g nal Str ng, computes t  equ valent offset  nto t  updated
   * str ng.   f t  offset falls w h n a seg nt that was removed/replaced, None  s returned.
   */
  def re ndex( ndex: Offset.CodePo nt): Opt on[Offset.CodePo nt] =
    re ndex( ndex, Offset.CodePo nt(0), replace nts)

  /**
   * Re ndexes an ent y of type T.  Returns t  updated ent y, or None  f e  r t  `from ndex`
   * or `to ndex` value  s now out of range.
   */
  def re ndexEnt y[T: TextEnt y](e: T): Opt on[T] =
    for {
      from <- re ndex(Offset.CodePo nt(TextEnt y.from ndex(e)))
      to <- re ndex(Offset.CodePo nt(TextEnt y.to ndex(e) - 1))
    } y eld TextEnt y.move(e, from.toShort, (to.toShort + 1).toShort)

  /**
   * Re ndexes a sequence of ent  es of type T.  So  ent  es could be f ltered
   * out  f t y span a reg on of text that has been removed.
   */
  def re ndexEnt  es[T: TextEnt y](es: Seq[T]): Seq[T] =
    for (e <- es; e2 <- re ndexEnt y(e)) y eld e2

  /**
   * Swaps `or g nal` and `updated` text and  nverts all `TextReplace nt`  nstances.
   */
  def  nverse: TextMod f cat on =
    TextMod f cat on(updated, or g nal, replace nts.map(_. nverse))

  // recurs vely walks through t  l st of TextReplace nt objects comput ng
  // offsets to add/substract from 'sh ft', wh ch accumulates all changes and
  // t n gets added to  ndex at t  end.
  pr vate def re ndex(
     ndex: Offset.CodePo nt,
    sh ft: Offset.CodePo nt,
    reps: L st[TextReplace nt]
  ): Opt on[Offset.CodePo nt] =
    reps match {
      case N l =>
         f ( ndex.to nt >= 0 &&  ndex <= or g nalLen)
          So ( ndex + sh ft)
        else
          None
      case (r @ TextReplace nt(fr, to, _, _)) :: ta l =>
         f ( ndex < fr) So ( ndex + sh ft)
        else  f ( ndex < to) None
        else re ndex( ndex, sh ft + r.lengthDelta, ta l)
    }
}

object TextReplace nt {
  def fromCodePo nts(
    or g nalFrom:  nt,
    or g nalTo:  nt,
    updatedFrom:  nt,
    updatedTo:  nt
  ): TextReplace nt =
    TextReplace nt(
      Offset.CodePo nt(or g nalFrom),
      Offset.CodePo nt(or g nalTo),
      Offset.CodePo nt(updatedFrom),
      Offset.CodePo nt(updatedTo)
    )
}

/**
 * Encodes t   nd ces of a seg nt of text  n one str ng that maps to a replace nt
 * seg nt  n an updated vers on of t  text.  T  replace nt seg nt could be empty
 * (updatedTo == updatedFrom),  nd cat ng t  seg nt was removed.
 *
 * All offsets are code-po nts, not UTF16 code-un s.
 *
 * `or g nalFrom` and `updatedFrom` are  nclus ve.
 * `or g nalTo` and `updatedTo` are exclus ve.
 */
case class TextReplace nt(
  or g nalFrom: Offset.CodePo nt,
  or g nalTo: Offset.CodePo nt,
  updatedFrom: Offset.CodePo nt,
  updatedTo: Offset.CodePo nt) {
  def or g nalLength: Offset.CodePo nt = or g nalTo - or g nalFrom
  def updatedLength: Offset.CodePo nt = updatedTo - updatedFrom
  def lengthDelta: Offset.CodePo nt = updatedLength - or g nalLength

  def sh ftOr g nal(offset: Offset.CodePo nt): TextReplace nt =
    copy(or g nalFrom = or g nalFrom + offset, or g nalTo = or g nalTo + offset)

  def sh ftUpdated(offset: Offset.CodePo nt): TextReplace nt =
    copy(updatedFrom = updatedFrom + offset, updatedTo = updatedTo + offset)

  def sh ft(offset: Offset.CodePo nt): TextReplace nt =
    TextReplace nt(
      or g nalFrom + offset,
      or g nalTo + offset,
      updatedFrom + offset,
      updatedTo + offset
    )

  def  nverse: TextReplace nt =
    TextReplace nt(
      or g nalFrom = updatedFrom,
      or g nalTo = updatedTo,
      updatedFrom = or g nalFrom,
      updatedTo = or g nalTo
    )
}
