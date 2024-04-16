package com.tw ter.t etyp e.match ng

object TokenSequence {

  /**
   *  s `suff x` a suff x of `s`, start ng at `offset`  n `s`?
   */
  def hasSuff xAt(s: CharSequence, suff x: CharSequence, offset:  nt): Boolean =
     f (offset == 0 && (s.eq(suff x) || s == suff x)) {
      true
    } else  f (suff x.length != (s.length - offset)) {
      false
    } else {
      @annotat on.ta lrec
      def go( :  nt): Boolean =
         f (  == suff x.length) true
        else  f (suff x.charAt( ) == s.charAt(offset +  )) go(  + 1)
        else false

      go(0)
    }

  /**
   * Do two [[CharSequence]]s conta n t  sa  characters?
   *
   * [[CharSequence]] equal y  s not suff c ent because
   * [[CharSequence]]s of d fferent types may not cons der ot r
   * [[CharSequence]]s conta n ng t  sa  characters equ valent.
   */
  def sa Characters(s1: CharSequence, s2: CharSequence): Boolean =
    hasSuff xAt(s1, s2, 0)

  /**
   * T   thod  mple nts t  product def n  on of a token match ng a
   * keyword. That def n  on  s:
   *
   * - T  token conta ns t  sa  characters as t  keyword.
   * - T  token conta ns t  sa  characters as t  keyword after
   *   dropp ng a lead ng '#' or '@' from t  token.
   *
   * T   ntent on  s that a keyword matc s an  dent cal hashtag, but
   *  f t  keyword  self  s a hashtag,   only matc s t  hashtag
   * form.
   *
   * T  token zat on process should rule out tokens or keywords that
   * start w h mult ple '#' characters, even though t   mple ntat on
   * allows for e.g. token "##a" to match "#a".
   */
  def tokenMatc s(token: CharSequence, keyword: CharSequence): Boolean =
     f (sa Characters(token, keyword)) true
    else  f (token.length == 0) false
    else {
      val tokenStart = token.charAt(0)
      (tokenStart == '#' || tokenStart == '@') && hasSuff xAt(token, keyword, 1)
    }
}

/**
 * A sequence of normal zed tokens. T  sequence depends on t  locale
 *  n wh ch t  text was parsed and t  vers on of t  pengu n l brary
 * that was used at token zat on t  .
 */
case class TokenSequence pr vate[match ng] (to ndexedSeq:  ndexedSeq[CharSequence]) {
   mport TokenSequence.tokenMatc s

  pr vate def apply( :  nt): CharSequence = to ndexedSeq( )

  def  sEmpty: Boolean = to ndexedSeq. sEmpty
  def nonEmpty: Boolean = to ndexedSeq.nonEmpty

  /**
   * Does t  suppl ed sequence of keywords match a consecut ve sequence
   * of tokens w h n t  sequence?
   */
  def conta nsKeywordSequence(keywords: TokenSequence): Boolean = {
    val f nal ndex = to ndexedSeq.length - keywords.to ndexedSeq.length

    @annotat on.ta lrec
    def matc sAt(offset:  nt,  :  nt): Boolean =
       f (  >= keywords.to ndexedSeq.length) true
      else  f (tokenMatc s(t (  + offset), keywords( ))) matc sAt(offset,   + 1)
      else false

    @annotat on.ta lrec
    def search(offset:  nt): Boolean =
       f (offset > f nal ndex) false
      else  f (matc sAt(offset, 0)) true
      else search(offset + 1)

    search(0)
  }
}
