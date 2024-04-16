package com.tw ter.follow_recom ndat ons.common.models

tra  HasCustom nterests {
  def custom nterests: Opt on[Seq[Str ng]]
}

tra  HasUtt nterests {
  def utt nterest ds: Opt on[Seq[Long]]
}

tra  Has nterest ds extends HasCustom nterests w h HasUtt nterests {}
