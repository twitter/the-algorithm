package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}

case class Rank ng nfo(
  scores: Opt on[Scores],
  rank: Opt on[ nt]) {

  def toThr ft: t.Rank ng nfo = {
    t.Rank ng nfo(scores.map(_.toThr ft), rank)
  }

  def toOffl neThr ft: offl ne.Rank ng nfo = {
    offl ne.Rank ng nfo(scores.map(_.toOffl neThr ft), rank)
  }
}

object Rank ng nfo {

  def fromThr ft(rank ng nfo: t.Rank ng nfo): Rank ng nfo = {
    Rank ng nfo(
      scores = rank ng nfo.scores.map(Scores.fromThr ft),
      rank = rank ng nfo.rank
    )
  }

}
