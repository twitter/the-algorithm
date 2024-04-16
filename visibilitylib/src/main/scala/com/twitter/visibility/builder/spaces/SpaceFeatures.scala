package com.tw ter.v s b l y.bu lder.spaces

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.g zmoduck.thr ftscala.MuteSurface
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.common.MutedKeywordFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.common.Aud oSpaceS ce
 mport com.tw ter.v s b l y.common.Space d
 mport com.tw ter.v s b l y.common.SpaceSafetyLabelMapS ce
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.{MutedKeyword => VfMutedKeyword}
 mport com.tw ter.v s b l y.models.SafetyLabel
 mport com.tw ter.v s b l y.models.SpaceSafetyLabel
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType

class SpaceFeatures(
  spaceSafetyLabelMap: StratoSpaceLabelMaps,
  authorFeatures: AuthorFeatures,
  relat onsh pFeatures: Relat onsh pFeatures,
  mutedKeywordFeatures: MutedKeywordFeatures,
  aud oSpaceS ce: Aud oSpaceS ce) {

  def forSpaceAndAuthor ds(
    space d: Space d,
    v e r d: Opt on[User d],
    author ds: Opt on[Seq[User d]]
  ): FeatureMapBu lder => FeatureMapBu lder = {

    _.w hFeature(SpaceSafetyLabels, spaceSafetyLabelMap.forSpace d(space d))
      .w hFeature(Author d, getSpaceAuthors(space d, author ds).map(_.toSet))
      .w hFeature(AuthorUserLabels, allSpaceAuthorLabels(space d, author ds))
      .w hFeature(V e rFollowsAuthor, v e rFollowsAnySpaceAuthor(space d, author ds, v e r d))
      .w hFeature(V e rMutesAuthor, v e rMutesAnySpaceAuthor(space d, author ds, v e r d))
      .w hFeature(V e rBlocksAuthor, v e rBlocksAnySpaceAuthor(space d, author ds, v e r d))
      .w hFeature(AuthorBlocksV e r, anySpaceAuthorBlocksV e r(space d, author ds, v e r d))
      .w hFeature(
        V e rMutesKeyword nSpaceT leForNot f cat ons,
        t leConta nsMutedKeyword(
          aud oSpaceS ce.getSpaceT le(space d),
          aud oSpaceS ce.getSpaceLanguage(space d),
          v e r d)
      )
  }

  def t leConta nsMutedKeyword(
    t leOptSt ch: St ch[Opt on[Str ng]],
    languageOptSt ch: St ch[Opt on[Str ng]],
    v e r d: Opt on[User d],
  ): St ch[VfMutedKeyword] = {
    t leOptSt ch.flatMap {
      case None => St ch.value(VfMutedKeyword(None))
      case So (spaceT le) =>
        languageOptSt ch.flatMap { languageOpt =>
          mutedKeywordFeatures.spaceT leConta nsMutedKeyword(
            spaceT le,
            languageOpt,
            mutedKeywordFeatures.allMutedKeywords(v e r d),
            MuteSurface.Not f cat ons)
        }
    }
  }

  def getSpaceAuthors(
    space d: Space d,
    author dsFromRequest: Opt on[Seq[User d]]
  ): St ch[Seq[User d]] = {
    author dsFromRequest match {
      case So (author ds) => St ch.apply(author ds)
      case _ => aud oSpaceS ce.getAdm n ds(space d)
    }
  }

  def allSpaceAuthorLabels(
    space d: Space d,
    author dsFromRequest: Opt on[Seq[User d]]
  ): St ch[Seq[Label]] = {
    getSpaceAuthors(space d, author dsFromRequest)
      .flatMap(author ds =>
        St ch.collect(author ds.map(author d => authorFeatures.authorUserLabels(author d)))).map(
        _.flatten)
  }

  def v e rMutesAnySpaceAuthor(
    space d: Space d,
    author dsFromRequest: Opt on[Seq[User d]],
    v e r d: Opt on[User d]
  ): St ch[Boolean] = {
    getSpaceAuthors(space d, author dsFromRequest)
      .flatMap(author ds =>
        St ch.collect(author ds.map(author d =>
          relat onsh pFeatures.v e rMutesAuthor(author d, v e r d)))).map(_.conta ns(true))
  }

  def anySpaceAuthorBlocksV e r(
    space d: Space d,
    author dsFromRequest: Opt on[Seq[User d]],
    v e r d: Opt on[User d]
  ): St ch[Boolean] = {
    getSpaceAuthors(space d, author dsFromRequest)
      .flatMap(author ds =>
        St ch.collect(author ds.map(author d =>
          relat onsh pFeatures.authorBlocksV e r(author d, v e r d)))).map(_.conta ns(true))
  }
}

class StratoSpaceLabelMaps(
  spaceSafetyLabelS ce: SpaceSafetyLabelMapS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("space_features")
  pr vate[t ] val spaceSafetyLabelsStats =
    scopedStatsRece ver.scope(SpaceSafetyLabels.na ).counter("requests")

  def forSpace d(
    space d: Space d,
  ): St ch[Seq[SpaceSafetyLabel]] = {
    spaceSafetyLabelS ce
      .fetch(space d).map(_.flatMap(_.labels.map { stratoSafetyLabelMap =>
        stratoSafetyLabelMap
          .map(label =>
            SpaceSafetyLabel(
              SpaceSafetyLabelType.fromThr ft(label._1),
              SafetyLabel.fromThr ft(label._2)))
      }).toSeq.flatten).ensure(spaceSafetyLabelsStats. ncr)
  }
}
