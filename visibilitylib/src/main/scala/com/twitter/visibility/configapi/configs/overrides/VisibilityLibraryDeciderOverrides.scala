package com.tw ter.v s b l y.conf gap .conf gs.overr des

 mport com.tw ter.dec der.LocalOverr des

object V s b l yL braryDec derOverr des
    extends LocalOverr des.Na space("v s b l y-l brary", "") {

  val EnableLocal zedTombstoneOnV s b l yResults = feature(
    "v s b l y_l brary_enable_local zed_tombstones_on_v s b l y_results")

  val EnableLocal zed nterst  alGenerator: LocalOverr des.Overr de =
    feature("v s b l y_l brary_enable_local zed_ nterst  al_generator")

  val Enable nnerQuotedT etV e rBlocksAuthor nterst  alRule: LocalOverr des.Overr de =
    feature("v s b l y_l brary_enable_ nner_quoted_t et_v e r_blocks_author_ nterst  al_rule")
  val Enable nnerQuotedT etV e rMutesAuthor nterst  alRule: LocalOverr des.Overr de =
    feature("v s b l y_l brary_enable_ nner_quoted_t et_v e r_mutes_author_ nterst  al_rule")

  val EnableBackendL m edAct ons: LocalOverr des.Overr de =
    feature("v s b l y_l brary_enable_backend_l m ed_act ons")

  val d sableLegacy nterst  alF lteredReason: LocalOverr des.Overr de = feature(
    "v s b l y_l brary_d sable_legacy_ nterst  al_f ltered_reason")
}
