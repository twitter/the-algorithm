package com.twitter.timelineranker.model

import com.twitter.common.text.language.LocaleUtil
import com.twitter.timelineranker.{thriftscala => thrift}

object Language {

  def fromThrift(lang: thrift.Language): Language = {
    require(lang.language.isDefined, "language can't be None")
    require(lang.scope.isDefined, "scope can't be None")
    Language(lang.language.get, LanguageScope.fromThrift(lang.scope.get))
  }
}

/**
 * Represents a language and the scope that it relates to.
 */
case class Language(language: String, scope: LanguageScope.Value) {

  throwIfInvalid()

  def toThrift: thrift.Language = {
    val scopeOption = Some(LanguageScope.toThrift(scope))
    thrift.Language(Some(language), scopeOption)
  }

  def throwIfInvalid(): Unit = {
    val result = LocaleUtil.getLocaleOf(language)
    require(result != LocaleUtil.UNKNOWN, s"Language ${language} is unsupported")
  }
}
