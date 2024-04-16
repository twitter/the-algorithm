package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.b ject on.thr ft.CompactThr ftCodec
 mport com.tw ter.ads.ent  es.db.thr ftscala.L ne emObject ve
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.thr ftscala.L ne em nfo
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataType
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .GeneralTensor
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.relevance_platform.common. nject on.LZ4 nject on
 mport com.tw ter.relevance_platform.common. nject on.SeqObject nject on
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.manhattan.Revenue
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.ut l.Future
 mport javax. nject.Na d
 mport scala.collect on.JavaConverters._

object Act vePromotedT etStoreModule extends Tw terModule {

  case class Act vePromotedT etStore(
    act vePromotedT etMHStore: ReadableStore[Str ng, DataRecord],
    statsRece ver: StatsRece ver)
      extends ReadableStore[T et d, Seq[L ne em nfo]] {
    overr de def get(t et d: T et d): Future[Opt on[Seq[L ne em nfo]]] = {
      act vePromotedT etMHStore.get(t et d.toStr ng).map {
        _.map { dataRecord =>
          val r chDataRecord = new R chDataRecord(dataRecord)
          val l ne em dsFeature: Feature[GeneralTensor] =
            new Feature.Tensor("act ve_promoted_t ets.l ne_ em_ ds", DataType. NT64)

          val l ne emObject vesFeature: Feature[GeneralTensor] =
            new Feature.Tensor("act ve_promoted_t ets.l ne_ em_object ves", DataType. NT64)

          val l ne em dsTensor: GeneralTensor = r chDataRecord.getFeatureValue(l ne em dsFeature)
          val l ne emObject vesTensor: GeneralTensor =
            r chDataRecord.getFeatureValue(l ne emObject vesFeature)

          val l ne em ds: Seq[Long] =
             f (l ne em dsTensor.getSetF eld == GeneralTensor._F elds. NT64_TENSOR && l ne em dsTensor.get nt64Tensor. sSetLongs) {
              l ne em dsTensor.get nt64Tensor.getLongs.asScala.map(_.toLong)
            } else Seq.empty

          val l ne emObject ves: Seq[L ne emObject ve] =
             f (l ne emObject vesTensor.getSetF eld == GeneralTensor._F elds. NT64_TENSOR && l ne emObject vesTensor.get nt64Tensor. sSetLongs) {
              l ne emObject vesTensor.get nt64Tensor.getLongs.asScala.map(object ve =>
                L ne emObject ve(object ve.to nt))
            } else Seq.empty

          val l ne em nfo =
             f (l ne em ds.s ze == l ne emObject ves.s ze) {
              l ne em ds.z pW h ndex.map {
                case (l ne em d,  ndex) =>
                  L ne em nfo(
                    l ne em d = l ne em d,
                    l ne emObject ve = l ne emObject ves( ndex)
                  )
              }
            } else Seq.empty

          l ne em nfo
        }
      }
    }
  }

  @Prov des
  @S ngleton
  def prov desAct vePromotedT etStore(
    manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams,
    @Na d(ModuleNa s.Un f edCac ) crM xerUn f edCac Cl ent:  mcac dCl ent,
    crM xerStatsRece ver: StatsRece ver
  ): ReadableStore[T et d, Seq[L ne em nfo]] = {

    val mhConf g = new ManhattanROConf g {
      val hdfsPath = HDFSPath("")
      val appl cat on D = Appl cat on D("ads_b gquery_features")
      val datasetNa  = DatasetNa ("act ve_promoted_t ets")
      val cluster = Revenue

      overr de def statsRece ver: StatsRece ver =
        crM xerStatsRece ver.scope("act ve_promoted_t ets_mh")
    }
    val mhStore: ReadableStore[Str ng, DataRecord] =
      ManhattanRO
        .getReadableStoreW hMtls[Str ng, DataRecord](
          mhConf g,
          manhattanKVCl entMtlsParams
        )(
           mpl c ly[ nject on[Str ng, Array[Byte]]],
          CompactThr ftCodec[DataRecord]
        )

    val underly ngStore =
      Act vePromotedT etStore(mhStore, crM xerStatsRece ver.scope("Act vePromotedT etStore"))
    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ngStore,
      cac Cl ent = crM xerUn f edCac Cl ent,
      ttl = 60.m nutes,
      asyncUpdate = false
    )(
      value nject on = LZ4 nject on.compose(SeqObject nject on[L ne em nfo]()),
      statsRece ver = crM xerStatsRece ver.scope(" mCac dAct vePromotedT etStore"),
      keyToStr ng = { k: T et d => s"apt/$k" }
    )

    ObservedCac dReadableStore.from(
       mcac dStore,
      ttl = 30.m nutes,
      maxKeys = 250000, // s ze of promoted t et  s around 200,000
      w ndowS ze = 10000L,
      cac Na  = "act ve_promoted_t et_cac ",
      maxMult GetS ze = 20
    )(crM xerStatsRece ver.scope(" n moryCac dAct vePromotedT etStore"))

  }

}
