package com.twittew.home_mixew.utiw

impowt com.twittew.home_mixew.modew.homefeatuwes.tweetimpwessionsfeatuwe
i-impowt c-com.twittew.pwoduct_mixew.component_wibwawy.featuwe_hydwatow.quewy.impwessed_tweets.impwessedtweets
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap

o-object t-tweetimpwessionshewpew {
  def t-tweetimpwessions(featuwes: f-featuwemap): s-set[wong] = {
    vaw manhattanimpwessions =
      featuwes.getowewse(tweetimpwessionsfeatuwe, (U ﹏ U) seq.empty).fwatmap(_.tweetids)
    v-vaw memcacheimpwessions = featuwes.getowewse(impwessedtweets, -.- s-seq.empty)

    (manhattanimpwessions ++ memcacheimpwessions).toset
  }
}
