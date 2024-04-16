package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.contentrecom nder.{thr ftscala => cr}
 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.TSP tr cTagFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top cContextFunct onal yTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters. nferred_top c. nferredTop cAdapter
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContextFunct onal yType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.cl ents.strato.top cs.Top cSoc alProofCl ent
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => s d}
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.tsp.{thr ftscala => tsp}
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object TSP nferredTop cFeature extends Feature[T etCand date, Map[Long, Double]]
object TSP nferredTop cDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class TSP nferredTop cFeatureHydrator @ nject() (
  top cSoc alProofCl ent: Top cSoc alProofCl ent)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("TSP nferredTop c")

  overr de val features: Set[Feature[_, _]] = Set(
    TSP nferredTop cFeature,
    TSP nferredTop cDataRecordFeature,
    Top c dSoc alContextFeature,
    Top cContextFunct onal yTypeFeature
  )

  pr vate val topK = 3

  pr vate val S cesToSetSoc alProof: Set[s d.Cand dateT etS ce d] =
    Set(s d.Cand dateT etS ce d.S mcluster)

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(TSP nferredTop cFeature, Map.empty[Long, Double])
    .add(TSP nferredTop cDataRecordFeature, new DataRecord())
    .add(Top c dSoc alContextFeature, None)
    .add(Top cContextFunct onal yTypeFeature, None)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val tags = cand dates.collect {
      case cand date  f cand date.features.getTry(TSP tr cTagFeature). sReturn =>
        cand date.cand date. d -> cand date.features
          .getOrElse(TSP tr cTagFeature, Set.empty[tsp. tr cTag])
    }.toMap

    val top cSoc alProofRequest = tsp.Top cSoc alProofRequest(
      user d = query.getRequ redUser d,
      t et ds = cand dates.map(_.cand date. d).toSet,
      d splayLocat on = cr.D splayLocat on.Ho T  l ne,
      top cL st ngSett ng = tsp.Top cL st ngSett ng.Followable,
      context = Top cL st ngV e rContext.fromCl entContext(query.cl entContext).toThr ft,
      bypassModes = None,
      // Only T etM xer s ce has t  data. Convert t  T etM xer  tr c tag to tsp  tr c tag.
      tags =  f (tags. sEmpty) None else So (tags)
    )

    top cSoc alProofCl ent
      .getTop cT etSoc alProofResponse(top cSoc alProofRequest)
      .map {
        case So (response) =>
          handleResponse(response, cand dates)
        case _ => cand dates.map { _ => DefaultFeatureMap }
      }
  }

  pr vate def handleResponse(
    response: tsp.Top cSoc alProofResponse,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[FeatureMap] = {
    cand dates.map { cand date =>
      val top cW hScores = response.soc alProofs.getOrElse(cand date.cand date. d, Seq.empty)
       f (top cW hScores.nonEmpty) {
        val (soc alProof d, soc alProofFunct onal yType) =
           f (cand date.features
              .getOrElse(Cand dateS ce dFeature, None)
              .ex sts(S cesToSetSoc alProof.conta ns)) {
            getSoc alProof(top cW hScores)
          } else (None, None)

        val  nferredTop cFeatures =
          top cW hScores.sortBy(-_.score).take(topK).map(a => (a.top c d, a.score)).toMap

        val  nferredTop cDataRecord =
           nferredTop cAdapter.adaptToDataRecords( nferredTop cFeatures).asScala. ad

        FeatureMapBu lder()
          .add(TSP nferredTop cFeature,  nferredTop cFeatures)
          .add(TSP nferredTop cDataRecordFeature,  nferredTop cDataRecord)
          .add(Top c dSoc alContextFeature, soc alProof d)
          .add(Top cContextFunct onal yTypeFeature, soc alProofFunct onal yType)
          .bu ld()
      } else DefaultFeatureMap
    }
  }

  pr vate def getSoc alProof(
    top cW hScores: Seq[tsp.Top cW hScore]
  ): (Opt on[Long], Opt on[Top cContextFunct onal yType]) = {
    val follow ngTop c d = top cW hScores.collectF rst {
      case tsp.Top cW hScore(top c d, _, _, So (tsp.Top cFollowType.Follow ng)) => top c d
    }

     f (follow ngTop c d.nonEmpty) {
      return (follow ngTop c d, So (Bas cTop cContextFunct onal yType))
    }

    val  mpl c Follow ng d = top cW hScores.collectF rst {
      case tsp.Top cW hScore(top c d, _, _, So (tsp.Top cFollowType. mpl c Follow)) =>
        top c d
    }

     f ( mpl c Follow ng d.nonEmpty) {
      return ( mpl c Follow ng d, So (Recom ndat onTop cContextFunct onal yType))
    }

    (None, None)
  }
}
