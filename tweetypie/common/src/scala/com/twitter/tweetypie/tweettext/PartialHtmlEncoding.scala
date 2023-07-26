package com.twittew.tweetypie.tweettext

/**
 * code used to convewt w-waw usew-pwovided t-text into a-an awwowabwe fowm. 😳😳😳
 */
o-object pawtiawhtmwencoding {

  /**
   * w-wepwaces aww `<`, o.O `>`, ( ͡o ω ͡o ) a-and '&' chaws w-with "&wt;", "&gt;", (U ﹏ U) a-and "&amp;", (///ˬ///✿) wespectivewy. >w<
   *
   * tweet text is htmw-encoded at tweet cweation time, rawr a-and is stowed and pwocessed in encoded fowm. mya
   */
  d-def encode(text: stwing): s-stwing = {
    vaw buf = nyew stwingbuiwdew

    text.foweach {
      case '<' => b-buf.append("&wt;")
      case '>' => b-buf.append("&gt;")
      c-case '&' => buf.append("&amp;")
      case c => buf.append(c)
    }

    buf.tostwing
  }

  pwivate v-vaw ampwtwegex = "&wt;".w
  pwivate vaw ampgtwegex = "&gt;".w
  pwivate vaw ampampwegex = "&amp;".w

  pwivate v-vaw pawtiawhtmwdecodew: (stwing => stwing) =
    ((s: s-stwing) => a-ampwtwegex.wepwaceawwin(s, ^^ "<"))
      .andthen(s => a-ampgtwegex.wepwaceawwin(s, 😳😳😳 ">"))
      .andthen(s => a-ampampwegex.wepwaceawwin(s, mya "&"))

  /**
   * the opposite of encode, 😳 i-it wepwaces aww "&wt;", -.- "&gt;", and "&amp;" w-with
   * `<`, 🥺 `>`, o.O and '&', wespectivewy. /(^•ω•^)
   */
  def decode(text: stwing): stwing =
    decodewithmodification(text) match {
      c-case some(mod) => mod.updated
      c-case n-none => text
    }

  /**
   * decodes e-encoded entities, nyaa~~ and wetuwns a `textmodification` if the t-text was modified. nyaa~~
   */
  d-def decodewithmodification(text: s-stwing): o-option[textmodification] =
    textmodification.wepwaceaww(
      t-text, :3
      ampwtwegex -> "<", 😳😳😳
      a-ampgtwegex -> ">",
      ampampwegex -> "&"
    )
}
