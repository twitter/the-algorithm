namespace java com.twittew.tweetypie.media.thwiftjava
#@namespace scawa com.twittew.tweetypie.media.thwiftscawa
#@namespace s-stwato c-com.twittew.tweetypie.media
n-nyamespace p-py gen.twittew.tweetypie.media
n-nyamespace w-wb tweetypie


/**
* a-a mediawef w-wepwesents a wefewence to a piece of media in mediainfosewvice, rawr x3 awong with metadata
* a-about the souwce tweet that the media came f-fwom in case of pasted media. nyaa~~
**/
s-stwuct mediawef {
  1: stwing genewic_media_key (pewsonawdatatype = 'mediaid')

  // fow tweets w-with pasted media, /(^•ω•^) the id o-of the tweet whewe t-this media was copied fwom
  2: optionaw i64 souwce_tweet_id (pewsonawdatatype = 'tweetid')

  // the authow o-of souwce_tweet_id
  3: optionaw i64 souwce_usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', rawr haspewsonawdata='twue')
