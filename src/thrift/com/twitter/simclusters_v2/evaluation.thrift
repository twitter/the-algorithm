namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.evawuation
#@namespace s-scawa com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato c-com.twittew.simcwustews_v2

/**
 * s-suwface awea a-at which the w-wefewence tweet w-was dispwayed to the usew
 **/
enum dispwaywocation {
  timewineswecap = 1, /(^•ω•^)
  timewineswectweet = 2
}(haspewsonawdata = 'fawse')

stwuct tweetwabews {
  1: w-wequiwed boow iscwicked = fawse(pewsonawdatatype = 'engagementspwivate')
  2: w-wequiwed boow iswiked = f-fawse(pewsonawdatatype = 'engagementspubwic')
  3: wequiwed boow iswetweeted = fawse(pewsonawdatatype = 'engagementspubwic')
  4: w-wequiwed boow isquoted = fawse(pewsonawdatatype = 'engagementspubwic')
  5: w-wequiwed boow iswepwied = f-fawse(pewsonawdatatype = 'engagementspubwic')
}(pewsisted = 'twue', nyaa~~ haspewsonawdata = 'twue')

/**
 * data containew of a wefewence tweet with scwibed usew engagement w-wabews
 */
stwuct wefewencetweet {
  1: wequiwed i64 tweetid(pewsonawdatatype = 'tweetid')
  2: wequiwed i64 authowid(pewsonawdatatype = 'usewid')
  3: w-wequiwed i64 timestamp(pewsonawdatatype = 'pubwictimestamp')
  4: w-wequiwed d-dispwaywocation d-dispwaywocation
  5: w-wequiwed tweetwabews wabews
}(pewsisted="twue", nyaa~~ haspewsonawdata = 'twue')

/**
 * d-data containew of a candidate tweet g-genewated by the candidate awgowithm
 */
stwuct candidatetweet {
  1: wequiwed i64 tweetid(pewsonawdatatype = 'tweetid')
  2: o-optionaw doubwe scowe(pewsonawdatatype = 'engagementscowe')
  // the t-timestamp hewe i-is a syntheticawwy g-genewated timestamp. :3
  // fow evawuation puwpose. 😳😳😳 hence weft unannotated
  3: o-optionaw i64 t-timestamp
}(haspewsonawdata = 'twue')

/**
 * an e-encapsuwated cowwection o-of candidate tweets
 **/
s-stwuct candidatetweets {
  1: wequiwed i64 tawgetusewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed wist<candidatetweet> wecommendedtweets
}(haspewsonawdata = 'twue')

/**
 * a-an encapsuwated cowwection of wefewence t-tweets
 **/
stwuct wefewencetweets {
  1: w-wequiwed i64 tawgetusewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed wist<wefewencetweet> impwessedtweets
}(pewsisted="twue", (˘ω˘) haspewsonawdata = 'twue')

/**
 * a wist of candidate tweets
 **/
stwuct candidatetweetswist {
  1: w-wequiwed wist<candidatetweet> w-wecommendedtweets
}(haspewsonawdata = 'twue')