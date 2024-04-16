package com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker

 mport com.tw ter.follow_recom ndat ons.common.ut ls.RandomUt l
 mport scala.ut l.Random

sealed tra  Cand dateShuffler[T] {
  def shuffle(seed: Opt on[Long])( nput: Seq[T]): Seq[T]
}

class NoShuffle[T]() extends Cand dateShuffler[T] {
  def shuffle(seed: Opt on[Long])( nput: Seq[T]): Seq[T] =  nput
}

class RandomShuffler[T]() extends Cand dateShuffler[T] {
  def shuffle(seed: Opt on[Long])( nput: Seq[T]): Seq[T] = {
    seed.map(new Random(_)).getOrElse(Random).shuffle( nput)
  }
}

tra  Rank  ghtedRandomShuffler[T] extends Cand dateShuffler[T] {

  def rankTo  ght(rank:  nt): Double
  def shuffle(seed: Opt on[Long])( nput: Seq[T]): Seq[T] = {
    val cand  ghts =  nput.z pW h ndex.map {
      case (cand date, rank) => (cand date, rankTo  ght(rank))
    }
    RandomUt l.  ghtedRandomShuffle(cand  ghts, seed.map(new Random(_))).unz p._1
  }
}

class Exponent alShuffler[T]() extends Rank  ghtedRandomShuffler[T] {
  def rankTo  ght(rank:  nt): Double = {
    1 / math
      .pow(rank.toDouble, 2.0) // t  funct on was proved to be effect ve  n prev ous DDGs
  }
}
