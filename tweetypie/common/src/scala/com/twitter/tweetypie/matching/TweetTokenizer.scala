package com.tw ter.t etyp e.match ng

 mport com.tw ter.common.text.p pel ne.Tw terLanguage dent f er
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on
 mport java.ut l.Locale

object T etToken zer extends Token zer {
  type LocaleP ck ng = Opt on[Locale] => Token zer

  /**
   * Get a Token zer-produc ng funct on that uses t  suppl ed locale
   * to select an appropr ate Token zer.
   */
  def localeP ck ng: LocaleP ck ng = {
    case None => T etToken zer
    case So (locale) => Token zer.forLocale(locale)
  }

  pr vate[t ] val t etLang dent f er =
    (new Tw terLanguage dent f er.Bu lder).bu ldForT et()

  /**
   * Get a Token zer that performs T et language detect on, and uses
   * that result to token ze t  text.  f   already know t  locale of
   * t  t et text, use `Token zer.get`, because  's much
   * c aper.
   */
  def get(vers on: Pengu nVers on): Token zer =
    new Token zer {
      overr de def token ze(text: Str ng): TokenSequence = {
        val locale = t etLang dent f er. dent fy(text).getLocale
        Token zer.get(locale, vers on).token ze(text)
      }
    }

  pr vate[t ] val Default = get(Token zer.DefaultPengu nVers on)

  /**
   * Token ze t  g ven text us ng T et language detect on and
   * `Token zer.DefaultPengu nVers on`. Prefer `Token zer.forLocale`  f
   *   already know t  language of t  text.
   */
  overr de def token ze(t etText: Str ng): TokenSequence =
    Default.token ze(t etText)
}
