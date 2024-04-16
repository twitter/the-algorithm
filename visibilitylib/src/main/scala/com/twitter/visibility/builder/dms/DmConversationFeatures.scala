package com.tw ter.v s b l y.bu lder.dms

 mport com.tw ter.convosvc.thr ftscala.Conversat onQuery
 mport com.tw ter.convosvc.thr ftscala.Conversat onQueryOpt ons
 mport com.tw ter.convosvc.thr ftscala.Conversat onType
 mport com.tw ter.convosvc.thr ftscala.T  l neLookupState
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.DmConversat on d
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.dm_s ces.DmConversat onS ce
 mport com.tw ter.v s b l y.features._

case class  nval dDmConversat onFeatureExcept on( ssage: Str ng) extends Except on( ssage)

class DmConversat onFeatures(
  dmConversat onS ce: DmConversat onS ce,
  authorFeatures: AuthorFeatures) {

  def forDmConversat on d(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): FeatureMapBu lder => FeatureMapBu lder =
    _.w hFeature(
      DmConversat on sOneToOneConversat on,
      dmConversat on sOneToOneConversat on(dmConversat on d, v e r dOpt))
      .w hFeature(
        DmConversat onHasEmptyT  l ne,
        dmConversat onHasEmptyT  l ne(dmConversat on d, v e r dOpt))
      .w hFeature(
        DmConversat onHasVal dLastReadableEvent d,
        dmConversat onHasVal dLastReadableEvent d(dmConversat on d, v e r dOpt))
      .w hFeature(
        DmConversat on nfoEx sts,
        dmConversat on nfoEx sts(dmConversat on d, v e r dOpt))
      .w hFeature(
        DmConversat onT  l neEx sts,
        dmConversat onT  l neEx sts(dmConversat on d, v e r dOpt))
      .w hFeature(
        Author sSuspended,
        dmConversat onHasSuspendedPart c pant(dmConversat on d, v e r dOpt))
      .w hFeature(
        Author sDeact vated,
        dmConversat onHasDeact vatedPart c pant(dmConversat on d, v e r dOpt))
      .w hFeature(
        Author sErased,
        dmConversat onHasErasedPart c pant(dmConversat on d, v e r dOpt))
      .w hFeature(
        V e r sDmConversat onPart c pant,
        v e r sDmConversat onPart c pant(dmConversat on d, v e r dOpt))

  def dmConversat on sOneToOneConversat on(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    v e r dOpt match {
      case So (v e r d) =>
        dmConversat onS ce.getConversat onType(dmConversat on d, v e r d).flatMap {
          case So (Conversat onType.OneToOneDm | Conversat onType.SecretOneToOneDm) =>
            St ch.True
          case None =>
            St ch.except on( nval dDmConversat onFeatureExcept on("Conversat on type not found"))
          case _ => St ch.False
        }
      case _ => St ch.except on( nval dDmConversat onFeatureExcept on("V e r  d m ss ng"))
    }

  pr vate[dms] def dmConversat onHasEmptyT  l ne(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    dmConversat onS ce
      .getConversat onT  l neEntr es(
        dmConversat on d,
        Conversat onQuery(
          conversat on d = So (dmConversat on d),
          opt ons = So (
            Conversat onQueryOpt ons(
              perspect valUser d = v e r dOpt,
              hydrateEvents = So (false),
              supportsReact ons = So (true)
            )
          ),
          maxCount = 10
        )
      ).map(_.forall(entr es => entr es. sEmpty))

  pr vate[dms] def dmConversat onHasVal dLastReadableEvent d(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    v e r dOpt match {
      case So (v e r d) =>
        dmConversat onS ce
          .getConversat onLastReadableEvent d(dmConversat on d, v e r d).map(_.ex sts( d =>
             d > 0L))
      case _ => St ch.except on( nval dDmConversat onFeatureExcept on("V e r  d m ss ng"))
    }

  pr vate[dms] def dmConversat on nfoEx sts(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    v e r dOpt match {
      case So (v e r d) =>
        dmConversat onS ce
          .getDmConversat on nfo(dmConversat on d, v e r d).map(_. sDef ned)
      case _ => St ch.except on( nval dDmConversat onFeatureExcept on("V e r  d m ss ng"))
    }

  pr vate[dms] def dmConversat onT  l neEx sts(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    dmConversat onS ce
      .getConversat onT  l neState(
        dmConversat on d,
        Conversat onQuery(
          conversat on d = So (dmConversat on d),
          opt ons = So (
            Conversat onQueryOpt ons(
              perspect valUser d = v e r dOpt,
              hydrateEvents = So (false),
              supportsReact ons = So (true)
            )
          ),
          maxCount = 1
        )
      ).map {
        case So (T  l neLookupState.NotFound) | None => false
        case _ => true
      }

  pr vate[dms] def anyConversat onPart c pantMatc sCond  on(
    cond  on: User d => St ch[Boolean],
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    v e r dOpt match {
      case So (v e r d) =>
        dmConversat onS ce
          .getConversat onPart c pant ds(dmConversat on d, v e r d).flatMap {
            case So (part c pants) =>
              St ch
                .collect(part c pants.map(cond  on)).map(_.conta ns(true)).rescue {
                  case NotFound =>
                    St ch.except on( nval dDmConversat onFeatureExcept on("User not found"))
                }
            case _ => St ch.False
          }
      case _ => St ch.except on( nval dDmConversat onFeatureExcept on("V e r  d m ss ng"))
    }

  def dmConversat onHasSuspendedPart c pant(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    anyConversat onPart c pantMatc sCond  on(
      part c pant => authorFeatures.author sSuspended(part c pant),
      dmConversat on d,
      v e r dOpt)

  def dmConversat onHasDeact vatedPart c pant(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    anyConversat onPart c pantMatc sCond  on(
      part c pant => authorFeatures.author sDeact vated(part c pant),
      dmConversat on d,
      v e r dOpt)

  def dmConversat onHasErasedPart c pant(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    anyConversat onPart c pantMatc sCond  on(
      part c pant => authorFeatures.author sErased(part c pant),
      dmConversat on d,
      v e r dOpt)

  def v e r sDmConversat onPart c pant(
    dmConversat on d: DmConversat on d,
    v e r dOpt: Opt on[User d]
  ): St ch[Boolean] =
    v e r dOpt match {
      case So (v e r d) =>
        dmConversat onS ce
          .getConversat onPart c pant ds(dmConversat on d, v e r d).map {
            case So (part c pants) => part c pants.conta ns(v e r d)
            case _ => false
          }
      case _ => St ch.except on( nval dDmConversat onFeatureExcept on("V e r  d m ss ng"))
    }
}
