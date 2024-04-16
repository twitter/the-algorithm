package com.tw ter.v s b l y.bu lder.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.MuteOpt on
 mport com.tw ter.g zmoduck.thr ftscala.MuteSurface
 mport com.tw ter.g zmoduck.thr ftscala.{MutedKeyword => GdMutedKeyword}
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common._
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.{MutedKeyword => VfMutedKeyword}
 mport java.ut l.Locale

class MutedKeywordFeatures(
  userS ce: UserS ce,
  userRelat onsh pS ce: UserRelat onsh pS ce,
  keywordMatc r: KeywordMatc r.Matc r = KeywordMatc r.TestMatc r,
  statsRece ver: StatsRece ver,
  enableFollowC ck nMutedKeyword: Gate[Un ] = Gate.False) {

  pr vate[t ] val scopedStatsRece ver: StatsRece ver =
    statsRece ver.scope("muted_keyword_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val v e rMutesKeyword nT etForHo T  l ne =
    scopedStatsRece ver.scope(V e rMutesKeyword nT etForHo T  l ne.na ).counter("requests")
  pr vate[t ] val v e rMutesKeyword nT etForT etRepl es =
    scopedStatsRece ver.scope(V e rMutesKeyword nT etForT etRepl es.na ).counter("requests")
  pr vate[t ] val v e rMutesKeyword nT etForNot f cat ons =
    scopedStatsRece ver.scope(V e rMutesKeyword nT etForNot f cat ons.na ).counter("requests")
  pr vate[t ] val excludeFollow ngForMutedKeywordsRequests =
    scopedStatsRece ver.scope("exclude_follow ng").counter("requests")
  pr vate[t ] val v e rMutesKeyword nT etForAllSurfaces =
    scopedStatsRece ver.scope(V e rMutesKeyword nT etForAllSurfaces.na ).counter("requests")

  def forT et(
    t et: T et,
    v e r d: Opt on[Long],
    author d: Long
  ): FeatureMapBu lder => FeatureMapBu lder = { featureMapBu lder =>
    requests. ncr()
    v e rMutesKeyword nT etForHo T  l ne. ncr()
    v e rMutesKeyword nT etForT etRepl es. ncr()
    v e rMutesKeyword nT etForNot f cat ons. ncr()
    v e rMutesKeyword nT etForAllSurfaces. ncr()

    val keywordsBySurface = allMutedKeywords(v e r d)

    val keywordsW houtDef nedSurface = allMutedKeywordsW houtDef nedSurface(v e r d)

    featureMapBu lder
      .w hFeature(
        V e rMutesKeyword nT etForHo T  l ne,
        t etConta nsMutedKeyword(
          t et,
          keywordsBySurface,
          MuteSurface.Ho T  l ne,
          v e r d,
          author d
        )
      )
      .w hFeature(
        V e rMutesKeyword nT etForT etRepl es,
        t etConta nsMutedKeyword(
          t et,
          keywordsBySurface,
          MuteSurface.T etRepl es,
          v e r d,
          author d
        )
      )
      .w hFeature(
        V e rMutesKeyword nT etForNot f cat ons,
        t etConta nsMutedKeyword(
          t et,
          keywordsBySurface,
          MuteSurface.Not f cat ons,
          v e r d,
          author d
        )
      )
      .w hFeature(
        V e rMutesKeyword nT etForAllSurfaces,
        t etConta nsMutedKeywordW houtDef nedSurface(
          t et,
          keywordsW houtDef nedSurface,
          v e r d,
          author d
        )
      )
  }

  def allMutedKeywords(v e r d: Opt on[Long]): St ch[Map[MuteSurface, Seq[GdMutedKeyword]]] =
    v e r d
      .map {  d => userS ce.getAllMutedKeywords( d) }.getOrElse(St ch.value(Map.empty))

  def allMutedKeywordsW houtDef nedSurface(v e r d: Opt on[Long]): St ch[Seq[GdMutedKeyword]] =
    v e r d
      .map {  d => userS ce.getAllMutedKeywordsW houtDef nedSurface( d) }.getOrElse(
        St ch.value(Seq.empty))

  pr vate def mut ngKeywordsText(
    mutedKeywords: Seq[GdMutedKeyword],
    muteSurface: MuteSurface,
    v e r dOpt: Opt on[Long],
    author d: Long
  ): St ch[Opt on[Str ng]] = {
     f (muteSurface == MuteSurface.Ho T  l ne && mutedKeywords.nonEmpty) {
      St ch.value(So (mutedKeywords.map(_.keyword).mkStr ng(",")))
    } else {
      mutedKeywords.part  on(kw =>
        kw.muteOpt ons.conta ns(MuteOpt on.ExcludeFollow ngAccounts)) match {
        case (_, mutedKeywordsFromAnyone)  f mutedKeywordsFromAnyone.nonEmpty =>
          St ch.value(So (mutedKeywordsFromAnyone.map(_.keyword).mkStr ng(",")))
        case (mutedKeywordsExcludeFollow ng, _)
             f mutedKeywordsExcludeFollow ng.nonEmpty && enableFollowC ck nMutedKeyword() =>
          excludeFollow ngForMutedKeywordsRequests. ncr()
          v e r dOpt match {
            case So (v e r d) =>
              userRelat onsh pS ce.follows(v e r d, author d).map {
                case true =>
                case false => So (mutedKeywordsExcludeFollow ng.map(_.keyword).mkStr ng(","))
              }
            case _ => St ch.None
          }
        case (_, _) => St ch.None
      }
    }
  }

  pr vate def mut ngKeywordsTextW houtDef nedSurface(
    mutedKeywords: Seq[GdMutedKeyword],
    v e r dOpt: Opt on[Long],
    author d: Long
  ): St ch[Opt on[Str ng]] = {
    mutedKeywords.part  on(kw =>
      kw.muteOpt ons.conta ns(MuteOpt on.ExcludeFollow ngAccounts)) match {
      case (_, mutedKeywordsFromAnyone)  f mutedKeywordsFromAnyone.nonEmpty =>
        St ch.value(So (mutedKeywordsFromAnyone.map(_.keyword).mkStr ng(",")))
      case (mutedKeywordsExcludeFollow ng, _)
           f mutedKeywordsExcludeFollow ng.nonEmpty && enableFollowC ck nMutedKeyword() =>
        excludeFollow ngForMutedKeywordsRequests. ncr()
        v e r dOpt match {
          case So (v e r d) =>
            userRelat onsh pS ce.follows(v e r d, author d).map {
              case true =>
              case false => So (mutedKeywordsExcludeFollow ng.map(_.keyword).mkStr ng(","))
            }
          case _ => St ch.None
        }
      case (_, _) => St ch.None
    }
  }

  def t etConta nsMutedKeyword(
    t et: T et,
    mutedKeywordMap: St ch[Map[MuteSurface, Seq[GdMutedKeyword]]],
    muteSurface: MuteSurface,
    v e r dOpt: Opt on[Long],
    author d: Long
  ): St ch[VfMutedKeyword] = {
    mutedKeywordMap.flatMap { keywordMap =>
       f (keywordMap. sEmpty) {
        St ch.value(VfMutedKeyword(None))
      } else {
        val mutedKeywords = keywordMap.getOrElse(muteSurface, N l)
        val matchT etFn: KeywordMatc r.MatchT et = keywordMatc r(mutedKeywords)
        val locale = t et.language.map(l => Locale.forLanguageTag(l.language))
        val text = t et.coreData.get.text

        matchT etFn(locale, text).flatMap { results =>
          mut ngKeywordsText(results, muteSurface, v e r dOpt, author d).map(VfMutedKeyword)
        }
      }
    }
  }

  def t etConta nsMutedKeywordW houtDef nedSurface(
    t et: T et,
    mutedKeywordSeq: St ch[Seq[GdMutedKeyword]],
    v e r dOpt: Opt on[Long],
    author d: Long
  ): St ch[VfMutedKeyword] = {
    mutedKeywordSeq.flatMap { mutedKeyword =>
       f (mutedKeyword. sEmpty) {
        St ch.value(VfMutedKeyword(None))
      } else {
        val matchT etFn: KeywordMatc r.MatchT et = keywordMatc r(mutedKeyword)
        val locale = t et.language.map(l => Locale.forLanguageTag(l.language))
        val text = t et.coreData.get.text

        matchT etFn(locale, text).flatMap { results =>
          mut ngKeywordsTextW houtDef nedSurface(results, v e r dOpt, author d).map(
            VfMutedKeyword
          )
        }
      }
    }
  }
  def spaceT leConta nsMutedKeyword(
    spaceT le: Str ng,
    spaceLanguageOpt: Opt on[Str ng],
    mutedKeywordMap: St ch[Map[MuteSurface, Seq[GdMutedKeyword]]],
    muteSurface: MuteSurface,
  ): St ch[VfMutedKeyword] = {
    mutedKeywordMap.flatMap { keywordMap =>
       f (keywordMap. sEmpty) {
        St ch.value(VfMutedKeyword(None))
      } else {
        val mutedKeywords = keywordMap.getOrElse(muteSurface, N l)
        val matchT etFn: KeywordMatc r.MatchT et = keywordMatc r(mutedKeywords)

        val locale = spaceLanguageOpt.map(l => Locale.forLanguageTag(l))
        matchT etFn(locale, spaceT le).flatMap { results =>
           f (results.nonEmpty) {
            St ch.value(So (results.map(_.keyword).mkStr ng(","))).map(VfMutedKeyword)
          } else {
            St ch.None.map(VfMutedKeyword)
          }
        }
      }
    }
  }

}
