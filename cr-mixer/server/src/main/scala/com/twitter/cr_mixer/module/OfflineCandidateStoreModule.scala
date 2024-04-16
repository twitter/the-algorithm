package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Offl neCand dateStoreModule extends Tw terModule {
  type User d = Long
   mpl c  val t etCand dates nject on:  nject on[Cand dateT etsL st, Array[Byte]] =
    CompactScalaCodec(Cand dateT etsL st)

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT et2020Cand dateStore)
  def offl neT et2020Cand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ nterested n_2020"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT et2020Hl0El15Cand dateStore)
  def offl neT et2020Hl0El15Cand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ nterested n_2020_hl_0_el_15"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT et2020Hl2El15Cand dateStore)
  def offl neT et2020Hl2El15Cand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ nterested n_2020_hl_2_el_15"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT et2020Hl2El50Cand dateStore)
  def offl neT et2020Hl2El50Cand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ nterested n_2020_hl_2_el_50"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT et2020Hl8El50Cand dateStore)
  def offl neT et2020Hl8El50Cand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ nterested n_2020_hl_8_el_50"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neT etMTSCand dateStore)
  def offl neT etMTSCand dateMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_mts_consu r_embedd ngs"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neFavDecayedSumCand dateStore)
  def offl neFavDecayedSumCand dateStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_decayed_sum"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neFtrAt5Pop1000RankDecay11Cand dateStore)
  def offl neFtrAt5Pop1000RankDecay11Cand dateStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ftrat5_pop1000_rank_decay_1_1"
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Offl neFtrAt5Pop10000RankDecay11Cand dateStore)
  def offl neFtrAt5Pop10000RankDecay11Cand dateStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[User d, Cand dateT etsL st] = {
    bu ldOffl neCand dateStore(
      serv ce dent f er,
      datasetNa  = "offl ne_t et_recom ndat ons_from_ftrat5_pop10000_rank_decay_1_1"
    )
  }

  pr vate def bu ldOffl neCand dateStore(
    serv ce dent f er: Serv ce dent f er,
    datasetNa : Str ng
  ): ReadableStore[User d, Cand dateT etsL st] = {
    ManhattanRO
      .getReadableStoreW hMtls[Long, Cand dateT etsL st](
        ManhattanROConf g(
          HDFSPath(""), // not needed
          Appl cat on D("mult _type_s mclusters"),
          DatasetNa (datasetNa ),
          Apollo
        ),
        ManhattanKVCl entMtlsParams(serv ce dent f er)
      )
  }

}
