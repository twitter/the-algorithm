package com.tw ter.t etyp e
package store

 mport com.tw ter.t etyp e.thr ftscala._
 mport scala.ut l.match ng.Regex

object  d a ndex lper {

  /**
   * Wh ch t ets should   treat as " d a" t ets?
   *
   * Any t et that  s not a ret et and any of:
   * -  s expl c ly marked as a  d a t et.
   * - Has a  d a ent y.
   * -  ncludes a partner  d a URL.
   */
  def apply(partner d aRegexes: Seq[Regex]): T et => Boolean = {
    val  sPartnerUrl = partnerUrlMatc r(partner d aRegexes)

    t et =>
      getShare(t et). sEmpty &&
        (has d aFlagSet(t et) ||
          get d a(t et).nonEmpty ||
          getUrls(t et).ex sts( sPartnerUrl))
  }

  def partnerUrlMatc r(partner d aRegexes: Seq[Regex]): UrlEnt y => Boolean =
    _.expanded.ex sts { expandedUrl =>
      partner d aRegexes.ex sts(_.f ndF rst n(expandedUrl). sDef ned)
    }

  def has d aFlagSet(t et: T et): Boolean =
    t et.coreData.flatMap(_.has d a).getOrElse(false)
}
