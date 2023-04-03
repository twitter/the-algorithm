packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}

/**
 * Relonprelonselonnts what this languagelon is associatelond with.
 * For elonxamplelon, "uselonr" is onelon of thelon scopelons and "elonvelonnt"
 * could belon anothelonr scopelon.
 */
objelonct LanguagelonScopelon elonxtelonnds elonnumelonration {

  // Uselonr scopelon melonans that thelon languagelon is thelon uselonr's languagelon.
  val Uselonr: Valuelon = Valuelon(thrift.LanguagelonScopelon.Uselonr.valuelon)

  // elonvelonnt scopelon melonans that thelon languagelon is thelon elonvelonnt's languagelon.
  val elonvelonnt: Valuelon = Valuelon(thrift.LanguagelonScopelon.elonvelonnt.valuelon)

  // list of all LanguagelonScopelon valuelons
  val All: ValuelonSelont = LanguagelonScopelon.ValuelonSelont(Uselonr, elonvelonnt)

  delonf apply(scopelon: thrift.LanguagelonScopelon): LanguagelonScopelon.Valuelon = {
    scopelon match {
      caselon thrift.LanguagelonScopelon.Uselonr =>
        Uselonr
      caselon thrift.LanguagelonScopelon.elonvelonnt =>
        elonvelonnt
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond languagelon scopelon: $scopelon")
    }
  }

  delonf fromThrift(scopelon: thrift.LanguagelonScopelon): LanguagelonScopelon.Valuelon = {
    apply(scopelon)
  }

  delonf toThrift(scopelon: LanguagelonScopelon.Valuelon): thrift.LanguagelonScopelon = {
    scopelon match {
      caselon LanguagelonScopelon.Uselonr =>
        thrift.LanguagelonScopelon.Uselonr
      caselon LanguagelonScopelon.elonvelonnt =>
        thrift.LanguagelonScopelon.elonvelonnt
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond languagelon scopelon: $scopelon")
    }
  }
}
