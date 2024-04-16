package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.ml.ap .matc r.FeatureMatc r
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport scala.collect on.JavaConverters._

object FeatureSelectorConf g {
  val BasePa rsToStore = Seq(
    ("tw ter_w de_user_aggregate.pa r", "*"),
    ("tw ter_w de_user_author_aggregate.pa r", "*"),
    ("user_aggregate_v5.cont nuous.pa r", "*"),
    ("user_aggregate_v7.pa r", "*"),
    ("user_author_aggregate_v2.pa r", "recap.earlyb rd.*"),
    ("user_author_aggregate_v2.pa r", "recap.searchfeature.*"),
    ("user_author_aggregate_v2.pa r", "recap.t etfeature.embeds*"),
    ("user_author_aggregate_v2.pa r", "recap.t etfeature.l nk_count*"),
    ("user_author_aggregate_v2.pa r", "engage nt_features. n_network.*"),
    ("user_author_aggregate_v2.pa r", "recap.t etfeature. s_reply.*"),
    ("user_author_aggregate_v2.pa r", "recap.t etfeature. s_ret et.*"),
    ("user_author_aggregate_v2.pa r", "recap.t etfeature.num_ nt ons.*"),
    ("user_author_aggregate_v5.pa r", "*"),
    ("user_author_aggregate_t ets ce_v1.pa r", "*"),
    ("user_engager_aggregate.pa r", "*"),
    ("user_ nt on_aggregate.pa r", "*"),
    ("user_request_context_aggregate.dow.pa r", "*"),
    ("user_request_context_aggregate.h .pa r", "*"),
    ("user_aggregate_v6.pa r", "*"),
    ("user_or g nal_author_aggregate_v1.pa r", "*"),
    ("user_or g nal_author_aggregate_v2.pa r", "*"),
    ("or g nal_author_aggregate_v1.pa r", "*"),
    ("or g nal_author_aggregate_v2.pa r", "*"),
    ("author_top c_aggregate.pa r", "*"),
    ("user_l st_aggregate.pa r", "*"),
    ("user_top c_aggregate.pa r", "*"),
    ("user_top c_aggregate_v2.pa r", "*"),
    ("user_ nferred_top c_aggregate.pa r", "*"),
    ("user_ nferred_top c_aggregate_v2.pa r", "*"),
    ("user_ d a_annotat on_aggregate.pa r", "*"),
    ("user_ d a_annotat on_aggregate.pa r", "*"),
    ("user_author_good_cl ck_aggregate.pa r", "*"),
    ("user_engager_good_cl ck_aggregate.pa r", "*")
  )
  val Pa rsToStore = BasePa rsToStore ++ Seq(
    ("user_aggregate_v2.pa r", "*"),
    ("user_aggregate_v5.boolean.pa r", "*"),
    ("user_aggregate_t ets ce_v1.pa r", "*"),
  )


  val LabelsToStore = Seq(
    "any_label",
    "recap.engage nt. s_favor ed",
    "recap.engage nt. s_ret eted",
    "recap.engage nt. s_repl ed",
    "recap.engage nt. s_open_l nked",
    "recap.engage nt. s_prof le_cl cked",
    "recap.engage nt. s_cl cked",
    "recap.engage nt. s_photo_expanded",
    "recap.engage nt. s_v deo_playback_50",
    "recap.engage nt. s_v deo_qual y_v e d",
    "recap.engage nt. s_repl ed_reply_ mpressed_by_author",
    "recap.engage nt. s_repl ed_reply_favor ed_by_author",
    "recap.engage nt. s_repl ed_reply_repl ed_by_author",
    "recap.engage nt. s_report_t et_cl cked",
    "recap.engage nt. s_block_cl cked",
    "recap.engage nt. s_mute_cl cked",
    "recap.engage nt. s_dont_l ke",
    "recap.engage nt. s_good_cl cked_convo_desc_favor ed_or_repl ed",
    "recap.engage nt. s_good_cl cked_convo_desc_v2",
    " l.engage nt. s_favor ed",
    " l.engage nt. s_ret eted",
    " l.engage nt. s_repl ed",
    " l.engage nt. s_open_l nked",
    " l.engage nt. s_prof le_cl cked",
    " l.engage nt. s_cl cked",
    " l.engage nt. s_photo_expanded",
    " l.engage nt. s_v deo_playback_50"
  )

  val Pa rGlobsToStore = for {
    (pref x, suff x) <- Pa rsToStore
    label <- LabelsToStore
  } y eld FeatureMatc r.glob(pref x + "." + label + "." + suff x)

  val BaseAggregateV2FeatureSelector = FeatureMatc r
    .none()
    .or(
      FeatureMatc r.glob(" ta.user_ d"),
      FeatureMatc r.glob(" ta.author_ d"),
      FeatureMatc r.glob("ent  es.or g nal_author_ d"),
      FeatureMatc r.glob("ent  es.top c_ d"),
      FeatureMatc r
        .glob("ent  es. nferred_top c_ ds" + TypedAggregateGroup.SparseFeatureSuff x),
      FeatureMatc r.glob("t  l nes. ta.l st_ d"),
      FeatureMatc r.glob("l st. d"),
      FeatureMatc r
        .glob("engage nt_features.user_ ds.publ c" + TypedAggregateGroup.SparseFeatureSuff x),
      FeatureMatc r
        .glob("ent  es.users. nt oned_screen_na s" + TypedAggregateGroup.SparseFeatureSuff x),
      FeatureMatc r.glob("user_aggregate_v2.pa r.recap.engage nt. s_dont_l ke.*"),
      FeatureMatc r.glob("user_author_aggregate_v2.pa r.any_label.recap.t etfeature.has_*"),
      FeatureMatc r.glob("request_context.country_code"),
      FeatureMatc r.glob("request_context.t  stamp_gmt_dow"),
      FeatureMatc r.glob("request_context.t  stamp_gmt_h "),
      FeatureMatc r.glob(
        "semant c_core. d a_understand ng.h gh_recall.non_sens  ve.ent y_ ds" + TypedAggregateGroup.SparseFeatureSuff x)
    )

  val AggregatesV2ProdFeatureSelector = BaseAggregateV2FeatureSelector
    .orL st(Pa rGlobsToStore.asJava)

  val ReducedPa rGlobsToStore = (for {
    (pref x, suff x) <- BasePa rsToStore
    label <- LabelsToStore
  } y eld FeatureMatc r.glob(pref x + "." + label + "." + suff x)) ++ Seq(
    FeatureMatc r.glob("user_aggregate_v2.pa r.any_label.*"),
    FeatureMatc r.glob("user_aggregate_v2.pa r.recap.engage nt. s_favor ed.*"),
    FeatureMatc r.glob("user_aggregate_v2.pa r.recap.engage nt. s_photo_expanded.*"),
    FeatureMatc r.glob("user_aggregate_v2.pa r.recap.engage nt. s_prof le_cl cked.*")
  )
}
