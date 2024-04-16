package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_t etyp e

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T etV s b l yPol cy
 mport com.tw ter.t etyp e.{thr ftscala => TP}

// Cand date Features
object  sCommun yT etFeature extends Feature[T etCand date, Boolean]

// T etyp e VF Features
object HasTakedownFeature extends Feature[T etCand date, Boolean]
object HasTakedownForLocaleFeature extends Feature[T etCand date, Boolean]
object  sHydratedFeature extends Feature[T etCand date, Boolean]
object  sNarrowcastFeature extends Feature[T etCand date, Boolean]
object  sNsfwAdm nFeature extends Feature[T etCand date, Boolean]
object  sNsfwFeature extends Feature[T etCand date, Boolean]
object  sNsfwUserFeature extends Feature[T etCand date, Boolean]
object  sNullcastFeature extends Feature[T etCand date, Boolean]
object QuotedT etDroppedFeature extends Feature[T etCand date, Boolean]
object QuotedT etHasTakedownFeature extends Feature[T etCand date, Boolean]
object QuotedT etHasTakedownForLocaleFeature extends Feature[T etCand date, Boolean]
object QuotedT et dFeature extends Feature[T etCand date, Opt on[Long]]
object S ceT etHasTakedownFeature extends Feature[T etCand date, Boolean]
object S ceT etHasTakedownForLocaleFeature extends Feature[T etCand date, Boolean]
object TakedownCountryCodesFeature extends Feature[T etCand date, Set[Str ng]]
object  sReplyFeature extends Feature[T etCand date, Boolean]
object  nReplyToFeature extends Feature[T etCand date, Opt on[Long]]
object  sRet etFeature extends Feature[T etCand date, Boolean]

object T etT etyp eCand dateFeatureHydrator {
  val CoreT etF elds: Set[TP.T et nclude] = Set[TP.T et nclude](
    TP.T et nclude.T etF eld d(TP.T et. dF eld. d),
    TP.T et nclude.T etF eld d(TP.T et.CoreDataF eld. d)
  )

  val NsfwLabelF elds: Set[TP.T et nclude] = Set[TP.T et nclude](
    // T et f elds conta n ng NSFW related attr butes,  n add  on to what ex sts  n coreData.
    TP.T et nclude.T etF eld d(TP.T et.NsfwH ghRecallLabelF eld. d),
    TP.T et nclude.T etF eld d(TP.T et.NsfwH ghPrec s onLabelF eld. d),
    TP.T et nclude.T etF eld d(TP.T et.NsfaH ghRecallLabelF eld. d)
  )

  val SafetyLabelF elds: Set[TP.T et nclude] = Set[TP.T et nclude](
    // T et f elds conta n ng RTF labels for abuse and spam.
    TP.T et nclude.T etF eld d(TP.T et.SpamLabelF eld. d),
    TP.T et nclude.T etF eld d(TP.T et.Abus veLabelF eld. d)
  )

  val Organ cT etTPHydrat onF elds: Set[TP.T et nclude] = CoreT etF elds ++
    NsfwLabelF elds ++
    SafetyLabelF elds ++
    Set(
      TP.T et nclude.T etF eld d(TP.T et.TakedownCountryCodesF eld. d),
      // QTs  mply a T etyP e -> SGS request dependency
      TP.T et nclude.T etF eld d(TP.T et.QuotedT etF eld. d),
      TP.T et nclude.T etF eld d(TP.T et.Esc rb rdEnt yAnnotat onsF eld. d),
      TP.T et nclude.T etF eld d(TP.T et.Commun  esF eld. d),
      // F eld requ red for determ n ng  f a T et was created v a News Ca ra.
      TP.T et nclude.T etF eld d(TP.T et.ComposerS ceF eld. d)
    )

  val  njectedT etTPHydrat onF elds: Set[TP.T et nclude] =
    Organ cT etTPHydrat onF elds ++ Set(
      //  nt ons  mply a T etyP e -> G zmoduck request dependency
      TP.T et nclude.T etF eld d(TP.T et. nt onsF eld. d),
      TP.T et nclude.T etF eld d(TP.T et.HashtagsF eld. d)
    )

  val DefaultFeatureMap = FeatureMapBu lder()
    .add( sNsfwAdm nFeature, false)
    .add( sNsfwUserFeature, false)
    .add( sNsfwFeature, false)
    .add( sNullcastFeature, false)
    .add( sNarrowcastFeature, false)
    .add(HasTakedownFeature, false)
    .add( sCommun yT etFeature, false)
    .add(TakedownCountryCodesFeature, Set.empty: Set[Str ng])
    .add( sHydratedFeature, false)
    .add(HasTakedownForLocaleFeature, false)
    .add(QuotedT etDroppedFeature, false)
    .add(S ceT etHasTakedownFeature, false)
    .add(QuotedT etHasTakedownFeature, false)
    .add(S ceT etHasTakedownForLocaleFeature, false)
    .add(QuotedT etHasTakedownForLocaleFeature, false)
    .add( sReplyFeature, false)
    .add( nReplyToFeature, None)
    .add( sRet etFeature, false)
    .bu ld()
}

class T etT etyp eCand dateFeatureHydrator(
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  safetyLevelPred cate: P pel neQuery => SafetyLevel)
    extends Cand dateFeatureHydrator[P pel neQuery, BaseT etCand date] {

   mport T etT etyp eCand dateFeatureHydrator._

  overr de val features: Set[Feature[_, _]] =
    Set(
       sNsfwFeature,
       sNsfwAdm nFeature,
       sNsfwUserFeature,
       sNullcastFeature,
       sNarrowcastFeature,
      HasTakedownFeature,
       sCommun yT etFeature,
      TakedownCountryCodesFeature,
       sHydratedFeature,
      HasTakedownForLocaleFeature,
      QuotedT etDroppedFeature,
      S ceT etHasTakedownFeature,
      QuotedT etHasTakedownFeature,
      S ceT etHasTakedownForLocaleFeature,
      QuotedT etHasTakedownForLocaleFeature,
       sReplyFeature,
       nReplyToFeature,
       sRet etFeature
    )

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T etT etyp e")

  overr de def apply(
    query: P pel neQuery,
    cand date: BaseT etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    val countryCode = query.getCountryCode.getOrElse("")

    t etyp eSt chCl ent
      .getT etF elds(
        t et d = cand date. d,
        opt ons = TP.GetT etF eldsOpt ons(
          t et ncludes = Organ cT etTPHydrat onF elds,
           ncludeRet etedT et = true,
           ncludeQuotedT et = true,
          v s b l yPol cy = T etV s b l yPol cy.UserV s ble,
          safetyLevel = So (safetyLevelPred cate(query))
        )
      ).map {
        case TP.GetT etF eldsResult(_, TP.T etF eldsResultState.Found(found), quoteOpt, _) =>
          val coreData = found.t et.coreData
          val  sNsfwAdm n = coreData.ex sts(_.nsfwAdm n)
          val  sNsfwUser = coreData.ex sts(_.nsfwUser)
          val hasTakedown = coreData.ex sts(_.hasTakedown)
          val  sReply = coreData.ex sts(_.reply.nonEmpty)
          val ancestor d = coreData.flatMap(_.reply).flatMap(_. nReplyToStatus d)
          val  sRet et = coreData.ex sts(_.share.nonEmpty)
          val takedownCountryCodes =
            found.t et.takedownCountryCodes.getOrElse(Seq.empty).map(_.toLo rCase).toSet

          val quotedT etDropped = quoteOpt.ex sts {
            case _: TP.T etF eldsResultState.F ltered =>
              true
            case _: TP.T etF eldsResultState.NotFound =>
              true
            case _ => false
          }
          val quotedT et sNsfw = quoteOpt.ex sts {
            case quoteT et: TP.T etF eldsResultState.Found =>
              quoteT et.found.t et.coreData.ex sts(data => data.nsfwAdm n || data.nsfwUser)
            case _ => false
          }
          val quotedT etHasTakedown = quoteOpt.ex sts {
            case quoteT et: TP.T etF eldsResultState.Found =>
              quoteT et.found.t et.coreData.ex sts(_.hasTakedown)
            case _ => false
          }
          val quotedT etTakedownCountryCodes = quoteOpt
            .collect {
              case quoteT et: TP.T etF eldsResultState.Found =>
                quoteT et.found.t et.takedownCountryCodes
                  .getOrElse(Seq.empty).map(_.toLo rCase).toSet
            }.getOrElse(Set.empty[Str ng])

          val s ceT et sNsfw =
            found.ret etedT et.ex sts(_.coreData.ex sts(data => data.nsfwAdm n || data.nsfwUser))
          val s ceT etHasTakedown =
            found.ret etedT et.ex sts(_.coreData.ex sts(_.hasTakedown))
          val s ceT etTakedownCountryCodes = found.ret etedT et
            .map { s ceT et: TP.T et =>
              s ceT et.takedownCountryCodes.getOrElse(Seq.empty).map(_.toLo rCase).toSet
            }.getOrElse(Set.empty)

          FeatureMapBu lder()
            .add( sNsfwAdm nFeature,  sNsfwAdm n)
            .add( sNsfwUserFeature,  sNsfwUser)
            .add( sNsfwFeature,  sNsfwAdm n ||  sNsfwUser || s ceT et sNsfw || quotedT et sNsfw)
            .add( sNullcastFeature, coreData.ex sts(_.nullcast))
            .add( sNarrowcastFeature, coreData.ex sts(_.narrowcast.nonEmpty))
            .add(HasTakedownFeature, hasTakedown)
            .add(
              HasTakedownForLocaleFeature,
              hasTakedownForLocale(hasTakedown, countryCode, takedownCountryCodes))
            .add(QuotedT etDroppedFeature, quotedT etDropped)
            .add(S ceT etHasTakedownFeature, s ceT etHasTakedown)
            .add(QuotedT etHasTakedownFeature, quotedT etHasTakedown)
            .add(
              S ceT etHasTakedownForLocaleFeature,
              hasTakedownForLocale(
                s ceT etHasTakedown,
                countryCode,
                s ceT etTakedownCountryCodes))
            .add(
              QuotedT etHasTakedownForLocaleFeature,
              hasTakedownForLocale(
                quotedT etHasTakedown,
                countryCode,
                quotedT etTakedownCountryCodes))
            .add( sCommun yT etFeature, found.t et.commun  es.ex sts(_.commun y ds.nonEmpty))
            .add(
              TakedownCountryCodesFeature,
              found.t et.takedownCountryCodes.getOrElse(Seq.empty).map(_.toLo rCase).toSet)
            .add( sHydratedFeature, true)
            .add( sReplyFeature,  sReply)
            .add( nReplyToFeature, ancestor d)
            .add( sRet etFeature,  sRet et)
            .bu ld()

        //  f no t et result found, return default features
        case _ =>
          DefaultFeatureMap
      }
  }

  pr vate def hasTakedownForLocale(
    hasTakedown: Boolean,
    countryCode: Str ng,
    takedownCountryCodes: Set[Str ng]
  ) = hasTakedown && takedownCountryCodes.conta ns(countryCode)
}
