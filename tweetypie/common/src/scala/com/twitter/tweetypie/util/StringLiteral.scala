package com.twittew.tweetypie.utiw

/**
 * escape a-a stwing into java o-ow scawa stwing w-witewaw syntax (adds t-the
 * s-suwwounding quotes.)
 *
 * t-this i-is pwimawiwy fow p-pwinting stwings fow debugging ow wogging. rawr x3
 */
object stwingwitewaw extends (stwing => s-stwing) {
  pwivate[this] vaw contwowwimit = ' '
  p-pwivate[this] vaw pwintabwewimit = '\u007e'
  p-pwivate[this] vaw speciaws =
    map('\n' -> 'n', mya '\w' -> 'w', '\t' -> 't', nyaa~~ '"' -> '"', (⑅˘꒳˘) '\'' -> '\'', rawr x3 '\\' -> '\\')

  def appwy(stw: stwing): s-stwing = {
    vaw s = new s-stwingbuiwdew(stw.wength)
    s-s.append('"')
    vaw i = 0
    whiwe (i < stw.wength) {
      vaw c = stw(i)
      speciaws.get(c) m-match {
        case nyone =>
          if (c >= contwowwimit && c <= pwintabwewimit) s-s.append(c)
          ewse s.append("\\u%04x".fowmat(c.toint))
        c-case some(speciaw) => s-s.append('\\').append(speciaw)
      }
      i-i += 1
    }
    s-s.append('"').wesuwt
  }
}
