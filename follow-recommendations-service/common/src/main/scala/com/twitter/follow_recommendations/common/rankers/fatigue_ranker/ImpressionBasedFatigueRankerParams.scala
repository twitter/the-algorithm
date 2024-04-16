package com.tw ter.follow_recom ndat ons.common.rankers.fat gue_ranker

 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object  mpress onBasedFat gueRankerParams {
  // W t r to enable hard dropp ng of  mpressed cand dates
  object Drop mpressedCand dateEnabled extends Param[Boolean](false)
  // At what # of  mpress ons to hard drop cand dates.
  object DropCand date mpress onThreshold extends Param[ nt](default = 10)
  // W t r to scr be cand date rank ng/scor ng  nfo per rank ng stage
  object Scr beRank ng nfo nFat gueRanker
      extends FSParam[Boolean]("fat gue_ranker_scr be_rank ng_ nfo", true)
}
