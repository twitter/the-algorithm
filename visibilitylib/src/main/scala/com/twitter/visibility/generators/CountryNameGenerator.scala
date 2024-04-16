package com.tw ter.v s b l y.generators

 mport com. bm. cu.ut l.ULocale
 mport com.tw ter.conf g.yaml.YamlMap
 mport com.tw ter.f nagle.stats.StatsRece ver

object CountryNa Generator {

  pr vate val AuroraF lesystemPath = "/usr/local/tw ter-conf g/tw ter/conf g/"

  pr vate val ContentBlock ngSupportedCountryL st = "takedown_countr es.yml"

  def prov desFromConf gBus(statsRece ver: StatsRece ver): CountryNa Generator = {
    fromF le(AuroraF lesystemPath + ContentBlock ngSupportedCountryL st, statsRece ver)
  }

  def prov desW hCustomMap(countryCodeMap: Map[Str ng, Str ng], statsRece ver: StatsRece ver) = {
    new CountryNa Generator(countryCodeMap, statsRece ver)
  }

  pr vate def fromF le(f leNa : Str ng, statsRece ver: StatsRece ver) = {
    val yamlConf g = YamlMap.load(f leNa )
    val countryCodeMap: Map[Str ng, Str ng] = yamlConf g.keySet.map { countryCode: Str ng =>
      val normal zedCode = countryCode.toUpperCase
      val countryNa : Opt on[Str ng] =
        yamlConf g.get(Seq(countryCode, "na ")).as nstanceOf[Opt on[Str ng]]
      (normal zedCode, countryNa .getOrElse(normal zedCode))
    }.toMap
    new CountryNa Generator(countryCodeMap, statsRece ver)
  }
}

class CountryNa Generator(countryCodeMap: Map[Str ng, Str ng], statsRece ver: StatsRece ver) {

  pr vate val scopedStatsRece ver = statsRece ver.scope("country_na _generator")
  pr vate val foundCountryRece ver = scopedStatsRece ver.counter("found")
  pr vate val m ss ngCountryRece ver = scopedStatsRece ver.counter("m ss ng")

  def getCountryNa (code: Str ng): Str ng = {
    val normal zedCode = code.toUpperCase
    countryCodeMap.get(normal zedCode) match {
      case So (retr evedNa ) => {
        foundCountryRece ver. ncr()
        retr evedNa 
      }
      case _ => {
        m ss ngCountryRece ver. ncr()
        val fallbackNa  =
          new ULocale("", normal zedCode).getD splayCountry(ULocale.forLanguageTag("en"))

         f (fallbackNa  == "")
          normal zedCode
        else
          fallbackNa 
      }
    }
  }
}
