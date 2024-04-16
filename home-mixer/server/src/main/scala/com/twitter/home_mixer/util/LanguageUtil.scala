package com.tw ter.ho _m xer.ut l

 mport com.tw ter.search.common.constants.{thr ftscala => scc}
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l
 mport com.tw ter.serv ce. tastore.gen.{thr ftscala => smg}

object LanguageUt l {

  pr vate val DefaultM nProducedLanguageRat o = 0.05
  pr vate val DefaultM nConsu dLanguageConf dence = 0.8

  /**
   * Computes a l st of languages based on UserLanguages  nformat on retr eved from  tastore.
   *
   * T  l st  s sorted  n descend ng order of conf dence score assoc ated w h each language.
   * That  s, language w h h g st conf dence value  s  n  ndex 0.
   */
  def computeLanguages(
    userLanguages: smg.UserLanguages,
    m nProducedLanguageRat o: Double = DefaultM nProducedLanguageRat o,
    m nConsu dLanguageConf dence: Double = DefaultM nConsu dLanguageConf dence
  ): Seq[scc.Thr ftLanguage] = {
    val languageConf denceMap = computeLanguageConf denceMap(
      userLanguages,
      m nProducedLanguageRat o,
      m nConsu dLanguageConf dence
    )
    languageConf denceMap.toSeq.sortBy(-_._2).map(_._1) // _1 = language, _2 = score
  }

  /**
   * Computes conf dence map based on UserLanguages  nformat on retr eved from  tastore.
   * w re,
   * key   = language code
   * value = level of conf dence that t  language  s appl cable to a user.
   */
  pr vate def computeLanguageConf denceMap(
    userLanguages: smg.UserLanguages,
    m nProducedLanguageRat o: Double,
    m nConsu dLanguageConf dence: Double
  ): Map[scc.Thr ftLanguage, Double] = {

    val producedLanguages = getLanguageMap(userLanguages.produced)
    val consu dLanguages = getLanguageMap(userLanguages.consu d)
    val languages = (producedLanguages.keys ++ consu dLanguages.keys).toSet
    var maxConf dence = 0.0

    val conf denceMap = languages.map { language =>
      val produceRat o = producedLanguages
        .get(language)
        .map { score =>  f (score < m nProducedLanguageRat o) 0.0 else score }
        .getOrElse(0.0)

      val consu Conf dence = consu dLanguages
        .get(language)
        .map { score =>  f (score < m nConsu dLanguageConf dence) 0.0 else score }
        .getOrElse(0.0)

      val overallConf dence = (0.3 + 4 * produceRat o) * (0.1 + consu Conf dence)
      maxConf dence = Math.max(maxConf dence, overallConf dence)

      (language -> overallConf dence)
    }.toMap

    val normal zedConf denceMap =  f (maxConf dence > 0) {
      conf denceMap.map {
        case (language, conf denceScore) =>
          val normal zedScore = (conf denceScore / maxConf dence * 0.9) + 0.1
          (language -> normal zedScore)
      }
    } else {
      conf denceMap
    }
    normal zedConf denceMap
  }

  pr vate def getLanguageMap(
    scoredLanguages: Seq[smg.ScoredStr ng]
  ): Map[scc.Thr ftLanguage, Double] = {
    scoredLanguages.flatMap { scoredLanguage =>
      getThr ftLanguage(scoredLanguage. em).map { language => (language -> scoredLanguage.  ght) }
    }.toMap
  }

  pr vate def getThr ftLanguage(languageNa : Str ng): Opt on[scc.Thr ftLanguage] = {
    val languageOrd nal = Thr ftLanguageUt l.getThr ftLanguageOf(languageNa ).ord nal
    val language = scc.Thr ftLanguage(languageOrd nal)
    language match {
      case scc.Thr ftLanguage.Unknown => None
      case _ => So (language)
    }
  }
}
