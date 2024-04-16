package com.tw ter.t etyp e.match ng

 mport com.tw ter.common.text.p pel ne.Tw terLanguage dent f er
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on
 mport java.ut l.Locale
 mport scala.collect on.JavaConvers ons.asScalaBuffer

object UserMutesBu lder {
  pr vate[match ng] val Default =
    new UserMutesBu lder(Token zer.DefaultPengu nVers on, None)

  pr vate val queryLang dent f er =
    (new Tw terLanguage dent f er.Bu lder).bu ldForQuery()
}

class UserMutesBu lder pr vate (pengu nVers on: Pengu nVers on, localeOpt: Opt on[Locale]) {

  /**
   * Use t  spec f ed Pengu n vers on w n token z ng a keyword mute
   * str ng.  n general, use t  default vers on, unless   need to
   * spec fy a part cular vers on for compat b l y w h anot r system
   * that  s us ng that vers on.
   */
  def w hPengu nVers on(ver: Pengu nVers on): UserMutesBu lder =
     f (ver == pengu nVers on) t 
    else new UserMutesBu lder(ver, localeOpt)

  /**
   * Use t  spec f ed locale w n token z ng a keyword mute str ng.
   */
  def w hLocale(locale: Locale): UserMutesBu lder =
     f (localeOpt.conta ns(locale)) t 
    else new UserMutesBu lder(pengu nVers on, So (locale))

  /**
   * W n token z ng a user mute l st, detect t  language of t 
   * text. T   s s gn f cantly more expens ve than us ng a predef ned
   * locale, but  s appropr ate w n t  locale  s not yet known.
   */
  def detectLocale(): UserMutesBu lder =
     f (localeOpt. sEmpty) t 
    else new UserMutesBu lder(pengu nVers on, localeOpt)

  pr vate[t ] lazy val token zer =
    localeOpt match {
      case None =>
        // No locale was spec f ed, so use a Token zer that performs
        // language detect on before token z ng.
        new Token zer {
          overr de def token ze(text: Str ng): TokenSequence = {
            val locale = UserMutesBu lder.queryLang dent f er. dent fy(text).getLocale
            Token zer.get(locale, pengu nVers on).token ze(text)
          }
        }

      case So (locale) =>
        Token zer.get(locale, pengu nVers on)
    }

  /**
   * G ven a l st of t  user's raw keyword mutes, return a preprocessed
   * set of mutes su able for match ng aga nst t et text.  f t   nput
   * conta ns any phrases that fa l val dat on, t n t y w ll be
   * dropped.
   */
  def bu ld(raw nput: Seq[Str ng]): UserMutes =
    UserMutes(raw nput.flatMap(val date(_).r ght.toOpt on))

  /**
   * Java-fr endly AP  for process ng a user's l st of raw keyword mutes
   *  nto a preprocessed form su able for match ng aga nst text.
   */
  def fromJavaL st(raw nput: java.ut l.L st[Str ng]): UserMutes =
    bu ld(asScalaBuffer(raw nput).toSeq)

  /**
   * Val date t  raw user  nput muted phrase. Currently, t  only
   *  nputs that are not val d for keyword mut ng are those  nputs that
   * do not conta n any keywords, because those  nputs would match all
   * t ets.
   */
  def val date(mutedPhrase: Str ng): E  r[UserMutes.Val dat onError, TokenSequence] = {
    val keywords = token zer.token ze(mutedPhrase)
     f (keywords. sEmpty) UserMutes.EmptyPhraseError else R ght(keywords)
  }
}

object UserMutes {
  sealed tra  Val dat onError

  /**
   * T  phrase's token zat on d d not produce any tokens
   */
  case object EmptyPhrase extends Val dat onError

  pr vate[match ng] val EmptyPhraseError = Left(EmptyPhrase)

  /**
   * Get a [[UserMutesBu lder]] that uses t  default Pengu n vers on and
   * performs language  dent f cat on to choose a locale.
   */
  def bu lder(): UserMutesBu lder = UserMutesBu lder.Default
}

/**
 * A user's muted keyword l st, preprocessed  nto token sequences.
 */
case class UserMutes pr vate[match ng] (toSeq: Seq[TokenSequence]) {

  /**
   * Do any of t  users' muted keyword sequences occur w h n t 
   * suppl ed text?
   */
  def matc s(text: TokenSequence): Boolean =
    toSeq.ex sts(text.conta nsKeywordSequence)

  /**
   * F nd all pos  ons of match ng muted keyword from t  user's
   * muted keyword l st
   */
  def f nd(text: TokenSequence): Seq[ nt] =
    toSeq.z pW h ndex.collect {
      case (token,  ndex)  f text.conta nsKeywordSequence(token) =>  ndex
    }

  def  sEmpty: Boolean = toSeq. sEmpty
  def nonEmpty: Boolean = toSeq.nonEmpty
}
