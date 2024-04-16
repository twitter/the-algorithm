package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.contentfeatures.ContentFeaturesProv der
 mport com.tw ter.t  l neranker.core.HydratedT ets
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.cl ents.t etyp e.T etyP eCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala.T et nclude
 mport com.tw ter.t etyp e.thr ftscala.{T et => TT et}
 mport com.tw ter.ut l.Future

object T etyp eContentFeaturesProv der {
  val DefaultT etyP eF eldsToHydrate: Set[T et nclude] = T etyP eCl ent.CoreT etF elds ++
    T etyP eCl ent. d aF elds ++
    T etyP eCl ent.SelfThreadF elds ++
    Set[T et nclude](T et nclude. d aEnt yF eld d( d aEnt y.Add  onal tadataF eld. d))

  //add T et f elds from semant c core
  val T etyP eF eldsToHydrate: Set[T et nclude] = DefaultT etyP eF eldsToHydrate ++
    Set[T et nclude](T et nclude.T etF eld d(TT et.Esc rb rdEnt yAnnotat onsF eld. d))
  val EmptyHydratedT ets: HydratedT ets =
    HydratedT ets(Seq.empty[HydratedT et], Seq.empty[HydratedT et])
  val EmptyHydratedT etsFuture: Future[HydratedT ets] = Future.value(EmptyHydratedT ets)
}

class T etyp eContentFeaturesProv der(
  t etHydrator: T etHydrator,
  enableContentFeaturesGate: Gate[RecapQuery],
  enableTokens nContentFeaturesGate: Gate[RecapQuery],
  enableT etText nContentFeaturesGate: Gate[RecapQuery],
  enableConversat onControlContentFeaturesGate: Gate[RecapQuery],
  enableT et d aHydrat onGate: Gate[RecapQuery],
  statsRece ver: StatsRece ver)
    extends ContentFeaturesProv der {
  val scopedStatsRece ver: StatsRece ver = statsRece ver.scope("T etyp eContentFeaturesProv der")

  overr de def apply(
    query: RecapQuery,
    t et ds: Seq[T et d]
  ): Future[Map[T et d, ContentFeatures]] = {
     mport T etyp eContentFeaturesProv der._

    val t etyp eHydrat onHandler = new Fa lOpenHandler(scopedStatsRece ver)
    val hydratePengu nTextFeatures = enableContentFeaturesGate(query)
    val hydrateSemant cCoreFeatures = enableContentFeaturesGate(query)
    val hydrateTokens = enableTokens nContentFeaturesGate(query)
    val hydrateT etText = enableT etText nContentFeaturesGate(query)
    val hydrateConversat onControl = enableConversat onControlContentFeaturesGate(query)

    val user d = query.user d

    val hydratedT etsFuture = t etyp eHydrat onHandler {
      // t etyP e f elds to hydrate g ven hydrateSemant cCoreFeatures
      val f eldsToHydrateW hSemant cCore =  f (hydrateSemant cCoreFeatures) {
        T etyP eF eldsToHydrate
      } else {
        DefaultT etyP eF eldsToHydrate
      }

      // t etyP e f elds to hydrate g ven hydrateSemant cCoreFeatures & hydrateConversat onControl
      val f eldsToHydrateW hConversat onControl =  f (hydrateConversat onControl) {
        f eldsToHydrateW hSemant cCore ++ T etyP eCl ent.Conversat onControlF eld
      } else {
        f eldsToHydrateW hSemant cCore
      }

      t etHydrator.hydrate(So (user d), t et ds, f eldsToHydrateW hConversat onControl)

    } { e: Throwable => EmptyHydratedT etsFuture }

    hydratedT etsFuture.map[Map[T et d, ContentFeatures]] { hydratedT ets =>
      hydratedT ets.outerT ets.map { hydratedT et =>
        val contentFeaturesFromT et = ContentFeatures.Empty.copy(
          selfThread tadata = hydratedT et.t et.selfThread tadata
        )

        val contentFeaturesW hText = T etTextFeaturesExtractor.addTextFeaturesFromT et(
          contentFeaturesFromT et,
          hydratedT et.t et,
          hydratePengu nTextFeatures,
          hydrateTokens,
          hydrateT etText
        )
        val contentFeaturesW h d a = T et d aFeaturesExtractor.add d aFeaturesFromT et(
          contentFeaturesW hText,
          hydratedT et.t et,
          enableT et d aHydrat onGate(query)
        )
        val contentFeaturesW hAnnotat ons = T etAnnotat onFeaturesExtractor
          .addAnnotat onFeaturesFromT et(
            contentFeaturesW h d a,
            hydratedT et.t et,
            hydrateSemant cCoreFeatures
          )
        // add conversat onControl to content features  f hydrateConversat onControl  s true
         f (hydrateConversat onControl) {
          val contentFeaturesW hConversat onControl = contentFeaturesW hAnnotat ons.copy(
            conversat onControl = hydratedT et.t et.conversat onControl
          )
          hydratedT et.t et d -> contentFeaturesW hConversat onControl
        } else {
          hydratedT et.t et d -> contentFeaturesW hAnnotat ons
        }

      }.toMap
    }
  }
}
