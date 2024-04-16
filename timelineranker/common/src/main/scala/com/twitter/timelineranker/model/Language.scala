package com.tw ter.t  l neranker.model

 mport com.tw ter.common.text.language.LocaleUt l
 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object Language {

  def fromThr ft(lang: thr ft.Language): Language = {
    requ re(lang.language. sDef ned, "language can't be None")
    requ re(lang.scope. sDef ned, "scope can't be None")
    Language(lang.language.get, LanguageScope.fromThr ft(lang.scope.get))
  }
}

/**
 * Represents a language and t  scope that   relates to.
 */
case class Language(language: Str ng, scope: LanguageScope.Value) {

  throw f nval d()

  def toThr ft: thr ft.Language = {
    val scopeOpt on = So (LanguageScope.toThr ft(scope))
    thr ft.Language(So (language), scopeOpt on)
  }

  def throw f nval d(): Un  = {
    val result = LocaleUt l.getLocaleOf(language)
    requ re(result != LocaleUt l.UNKNOWN, s"Language ${language}  s unsupported")
  }
}
