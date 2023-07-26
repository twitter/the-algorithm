namespace java com.twittew.intewaction_gwaph.thwiftjava
#@namespace scawa com.twittew.intewaction_gwaph.thwiftscawa
#@namespace stwato c-com.twittew.intewaction_gwaph

// t-these couwd b-be eithew a v-vewtex ow an edge f-featuwe nyame
// w-when you add a-a nyew featuwe, (⑅˘꒳˘) u-update vewtexfeatuwecombinew.java and edgefeatuwecombinew.java.
enum featuwename {
  nyum_wetweets = 1
  nyum_favowites = 2
  n-nyum_mentions = 3
  nyum_diwect_messages = 4
  nyum_tweet_cwicks = 5
  n-nyum_wink_cwicks = 6
  nyum_pwofiwe_views = 7
  n-nyum_fowwows = 8
  nyum_unfowwows = 9
  nyum_mutuaw_fowwows = 10
  addwess_book_emaiw = 11
  a-addwess_book_phone = 12
  addwess_book_in_both = 13
  a-addwess_book_mutuaw_edge_emaiw = 14
  a-addwess_book_mutuaw_edge_phone = 15
  addwess_book_mutuaw_edge_in_both = 16
  totaw_dweww_time = 17
  nyum_inspected_statuses = 18
  nyum_photo_tags = 19
  n-nyum_bwocks = 20 
  num_mutes = 21 
  nyum_wepowt_as_abuses = 22
  nyum_wepowt_as_spams = 23
  nyum_tweet_quotes = 24
  n-nyum_push_opens = 25
  nyum_ntab_cwicks = 26,
  n-nyum_wt_favowies = 27, nyaa~~
  n-nyum_wt_wepwies = 28, :3
  n-nyum_wt_tweet_quotes = 29, ( ͡o ω ͡o )
  n-nyum_wt_wetweets = 30, mya
  nyum_wt_mentions = 31, (///ˬ///✿)
  nyum_wt_tweet_cwicks = 32, (˘ω˘)
  nyum_wt_wink_cwicks = 33
  n-num_shawes = 34, ^^;;
  nyum_emaiw_cwick = 35, (✿oωo)
  nyum_emaiw_open = 36, (U ﹏ U)
  n-nyum_ntab_diswike_7_days = 37, -.-
  nyum_push_dismiss = 38, ^•ﻌ•^
  nyum_push_wepowt_tweet_cwick = 39, rawr
  nyum_push_wepowt_usew_cwick = 40, (˘ω˘)
  nyum_wepwies = 41, nyaa~~
  // vewtex featuwes aftew 128
  n-nyum_cweate_tweets = 129, UwU
}
// do wemembew t-to update the tests i-in intewactiongwaphaggwegationjobtest w-when adding nyew featuwes but nyot updating agg_aww

s-stwuct timesewiesstatistics {
  1: w-wequiwed doubwe mean;
  // fow c-computing vawiance o-onwine: http://en.wikipedia.owg/wiki/awgowithms_fow_cawcuwating_vawiance#on-wine_awgowithm
  2: wequiwed doubwe m-m2_fow_vawiance;
  3: wequiwed d-doubwe ewma; // exponentiawwy weighted moving a-avewage: ewma_t = \awpha x_t + (1-\awpha) e-ewma_{t-1}
  4: wequiwed i-i32 nyum_ewapsed_days; // totaw n-numbew of days since we stawted counting this featuwe
  5: wequiwed i32 nyum_non_zewo_days; // nyumbew of days when the intewaction w-was nyon-zewo (used t-to compute mean/vawiance)
  6: o-optionaw i-i32 nyum_days_since_wast; // n-nyumbew of days since the watest intewaction happen
}(pewsisted="twue", :3 haspewsonawdata = 'fawse') 

s-stwuct vewtexfeatuwe {
  1: wequiwed featuwename nyame;
  2: wequiwed boow outgoing; // diwection e-e.g. (⑅˘꒳˘) twue is nyum_wetweets_by_usew, (///ˬ///✿) a-and f-fawse is nyum_wetweets_fow_usew
  3: w-wequiwed timesewiesstatistics tss;
}(pewsisted="twue", ^^;; h-haspewsonawdata = 'fawse')

s-stwuct v-vewtex {
  1: wequiwed i-i64 usew_id(pewsonawdatatype = 'usewid');
  2: optionaw doubwe weight;
  3: w-wist<vewtexfeatuwe> f-featuwes;
}(pewsisted="twue", >_< h-haspewsonawdata = 'twue')

/*
 * t-these featuwes a-awe fow an edge (a->b). rawr x3 exampwes:
 * (i) fowwow is whethew a-a fowwows b
 * (ii) nyum_wetweets is nyumbew of b's tweets wetweet by a
 */
stwuct edgefeatuwe {
  1: w-wequiwed featuwename nyame;
  2: wequiwed timesewiesstatistics t-tss;
}(pewsisted="twue", /(^•ω•^) h-haspewsonawdata = 'fawse')

s-stwuct edge {
  1: wequiwed i-i64 souwce_id(pewsonawdatatype = 'usewid');
  2: wequiwed i-i64 destination_id(pewsonawdatatype = 'usewid');
  3: o-optionaw doubwe weight;
  4: wist<edgefeatuwe> featuwes;
}(pewsisted="twue", :3 haspewsonawdata = 'twue')

// these stwucts bewow a-awe used by ouw mw pipewine
s-stwuct edgewabew {
  1: wequiwed i-i64 souwce_id(pewsonawdatatype = 'usewid');
  2: w-wequiwed i64 destination_id(pewsonawdatatype = 'usewid');
  3: wequiwed set<featuwename> w-wabews(pewsonawdatatype = 'aggwegateimpwessionengagementdata');
}(pewsisted="twue", (ꈍᴗꈍ) h-haspewsonawdata = 'twue')
