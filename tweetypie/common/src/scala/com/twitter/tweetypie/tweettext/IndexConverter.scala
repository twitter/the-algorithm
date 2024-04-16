package com.tw ter.t etyp e.t ettext

/**
 * An eff c ent converter of  nd ces bet en code po nts and code un s.
 */
class  ndexConverter(text: Str ng) {
  // Keep track of a s ngle correspond ng pa r of code un  and code po nt
  // offsets so that   can re-use count ng work  f t  next requested
  // ent y  s near t  most recent ent y.
  pr vate var codePo nt ndex = 0
  // T  code un   ndex should never spl  a surrogate pa r.
  pr vate var char ndex = 0

  /**
   * @param offset  ndex  nto t  str ng  asured  n code un s.
   * @return T  code po nt  ndex that corresponds to t  spec f ed character  ndex.
   */
  def toCodePo nts(offset: Offset.CodeUn ): Offset.CodePo nt =
    Offset.CodePo nt(codeUn sToCodePo nts(offset.to nt))

  /**
   * @param char ndex  ndex  nto t  str ng  asured  n code un s.
   * @return T  code po nt  ndex that corresponds to t  spec f ed character  ndex.
   */
  def codeUn sToCodePo nts(char ndex:  nt):  nt = {
     f (char ndex < t .char ndex) {
      t .codePo nt ndex -= text.codePo ntCount(char ndex, t .char ndex)
    } else {
      t .codePo nt ndex += text.codePo ntCount(t .char ndex, char ndex)
    }
    t .char ndex = char ndex

    // Make sure that char ndex never po nts to t  second code un  of a
    // surrogate pa r.
     f (char ndex > 0 && Character. sSupple ntaryCodePo nt(text.codePo ntAt(char ndex - 1))) {
      t .char ndex -= 1
      t .codePo nt ndex -= 1
    }

    t .codePo nt ndex
  }

  /**
   * @param offset  ndex  nto t  str ng  asured  n code po nts.
   * @return t  correspond ng code un   ndex
   */
  def toCodeUn s(offset: Offset.CodePo nt): Offset.CodeUn  = {
    t .char ndex = text.offsetByCodePo nts(char ndex, offset.to nt - t .codePo nt ndex)
    t .codePo nt ndex = offset.to nt
    Offset.CodeUn (t .char ndex)
  }

  /**
   * @param codePo nt ndex  ndex  nto t  str ng  asured  n code po nts.
   * @return t  correspond ng code un   ndex
   */
  def codePo ntsToCodeUn s(codePo nt ndex:  nt):  nt =
    toCodeUn s(Offset.CodePo nt(codePo nt ndex)).to nt

  /**
   * Returns a substr ng wh ch beg ns at t  spec f ed code po nt `from` and extends to t 
   * code po nt `to`. S nce Str ng.substr ng only works w h character, t   thod f rst
   * converts code po nt offset to code un  offset.
   */
  def substr ng(from: Offset.CodePo nt, to: Offset.CodePo nt): Str ng =
    text.substr ng(toCodeUn s(from).to nt, toCodeUn s(to).to nt)

  /**
   * Returns a substr ng wh ch beg ns at t  spec f ed code po nt `from` and extends to t 
   * code po nt `to`. S nce Str ng.substr ng only works w h character, t   thod f rst
   * converts code po nt offset to code un  offset.
   */
  def substr ngByCodePo nts(from:  nt, to:  nt): Str ng =
    substr ng(Offset.CodePo nt(from), Offset.CodePo nt(to))

  /**
   * Returns a substr ng wh ch beg ns at t  spec f ed code po nt `from` and extends to t 
   * end of t  str ng. S nce Str ng.substr ng only works w h character, t   thod f rst
   * converts code po nt offset to code un  offset.
   */
  def substr ngByCodePo nts(from:  nt): Str ng = {
    val charFrom = codePo ntsToCodeUn s(from)
    text.substr ng(charFrom)
  }
}
