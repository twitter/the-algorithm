packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil
import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}

objelonct Languagelon {

  delonf fromThrift(lang: thrift.Languagelon): Languagelon = {
    relonquirelon(lang.languagelon.isDelonfinelond, "languagelon can't belon Nonelon")
    relonquirelon(lang.scopelon.isDelonfinelond, "scopelon can't belon Nonelon")
    Languagelon(lang.languagelon.gelont, LanguagelonScopelon.fromThrift(lang.scopelon.gelont))
  }
}

/**
 * Relonprelonselonnts a languagelon and thelon scopelon that it relonlatelons to.
 */
caselon class Languagelon(languagelon: String, scopelon: LanguagelonScopelon.Valuelon) {

  throwIfInvalid()

  delonf toThrift: thrift.Languagelon = {
    val scopelonOption = Somelon(LanguagelonScopelon.toThrift(scopelon))
    thrift.Languagelon(Somelon(languagelon), scopelonOption)
  }

  delonf throwIfInvalid(): Unit = {
    val relonsult = LocalelonUtil.gelontLocalelonOf(languagelon)
    relonquirelon(relonsult != LocalelonUtil.UNKNOWN, s"Languagelon ${languagelon} is unsupportelond")
  }
}
