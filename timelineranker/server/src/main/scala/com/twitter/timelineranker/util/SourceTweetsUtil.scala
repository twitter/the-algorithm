package com.twittew.timewinewankew.utiw

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.seawch.eawwybiwd.thwiftscawa.thwiftseawchwesuwt
i-impowt c-com.twittew.timewines.modew.tweetid
i-impowt com.twittew.timewines.modew.usewid

o-object souwcetweetsutiw {
  d-def g-getsouwcetweetids(
    s-seawchwesuwts: seq[thwiftseawchwesuwt], (///ˬ///✿)
    seawchwesuwtstweetids: set[tweetid], >w<
    fowwowedusewids: s-seq[tweetid], rawr
    shouwdincwudewepwywoottweets: boowean, mya
    statsweceivew: s-statsweceivew
  ): seq[tweetid] = {
    v-vaw wepwywoottweetcountew = statsweceivew.countew("wepwywoottweet")

    vaw wetweetsouwcetweetids = getwetweetsouwcetweetids(seawchwesuwts, ^^ s-seawchwesuwtstweetids)

    vaw innetwowkwepwyinwepwytotweetids = g-getinnetwowkinwepwytotweetids(
      s-seawchwesuwts,
      seawchwesuwtstweetids, 😳😳😳
      fowwowedusewids
    )

    vaw extendedwepwiessouwcetweetids = getextendedwepwysouwcetweetids(
      s-seawchwesuwts, mya
      seawchwesuwtstweetids, 😳
      fowwowedusewids
    )

    vaw wepwywoottweetids = if (shouwdincwudewepwywoottweets) {
      v-vaw woottweetids = getwepwywoottweetids(
        s-seawchwesuwts, -.-
        s-seawchwesuwtstweetids
      )
      w-wepwywoottweetcountew.incw(woottweetids.size)

      w-woottweetids
    } ewse {
      seq.empty
    }

    (wetweetsouwcetweetids ++ extendedwepwiessouwcetweetids ++
      i-innetwowkwepwyinwepwytotweetids ++ wepwywoottweetids).distinct
  }

  def getinnetwowkinwepwytotweetids(
    seawchwesuwts: s-seq[thwiftseawchwesuwt], 🥺
    seawchwesuwtstweetids: set[tweetid], o.O
    fowwowedusewids: seq[usewid]
  ): seq[tweetid] = {
    s-seawchwesuwts
      .fiwtew(seawchwesuwtutiw.isinnetwowkwepwy(fowwowedusewids))
      .fwatmap(seawchwesuwtutiw.getsouwcetweetid)
      .fiwtewnot(seawchwesuwtstweetids.contains)
  }

  def getwepwywoottweetids(
    s-seawchwesuwts: s-seq[thwiftseawchwesuwt], /(^•ω•^)
    seawchwesuwtstweetids: s-set[tweetid]
  ): seq[tweetid] = {
    seawchwesuwts
      .fwatmap(seawchwesuwtutiw.getwepwywoottweetid)
      .fiwtewnot(seawchwesuwtstweetids.contains)
  }

  def getwetweetsouwcetweetids(
    s-seawchwesuwts: s-seq[thwiftseawchwesuwt], nyaa~~
    seawchwesuwtstweetids: s-set[tweetid]
  ): seq[tweetid] = {
    s-seawchwesuwts
      .fiwtew(seawchwesuwtutiw.iswetweet)
      .fwatmap(seawchwesuwtutiw.getsouwcetweetid)
      .fiwtewnot(seawchwesuwtstweetids.contains)
  }

  def getextendedwepwysouwcetweetids(
    s-seawchwesuwts: seq[thwiftseawchwesuwt], nyaa~~
    s-seawchwesuwtstweetids: set[tweetid], :3
    fowwowedusewids: s-seq[usewid]
  ): seq[tweetid] = {
    s-seawchwesuwts
      .fiwtew(seawchwesuwtutiw.isextendedwepwy(fowwowedusewids))
      .fwatmap(seawchwesuwtutiw.getsouwcetweetid)
      .fiwtewnot(seawchwesuwtstweetids.contains)
  }
}
