namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "ads.thwift"
incwude "candidate_genewation_key.thwift"
i-incwude "cw_mixew.thwift"
i-incwude "metwic_tags.thwift"
i-incwude "pwoduct.thwift"
i-incwude "wewated_tweet.thwift"
incwude "souwce_type.thwift"
incwude "uteg.thwift"
incwude "com/twittew/mw/api/data.thwift"
incwude "com/twittew/simcwustews_v2/identifiew.thwift"

s-stwuct vittweetcandidatesscwibe {
  1: wequiwed i64 u-uuid (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid') # wequestuuid - u-unique scwibe id fow evewy wequest that comes in. 🥺 same w-wequest but diffewent stages o-of scwibe wog (fetchcandidate, OwO fiwtew, >w< e-etc) shawe the same uuid
  2: wequiwed i64 usewid (pewsonawdatatype = 'usewid')
  3: wequiwed w-wist<vittweetcandidatescwibe> candidates
  7: wequiwed pwoduct.pwoduct pwoduct
  8: wequiwed w-wist<impwessesedbucketinfo> impwessedbuckets
} (pewsisted='twue', 🥺 h-haspewsonawdata = 'twue')

stwuct v-vittweetcandidatescwibe {
  1: w-wequiwed i64 t-tweetid (pewsonawdatatype = 'tweetid')
  2: wequiwed i64 authowid (pewsonawdatatype = 'usewid')
  3: w-wequiwed doubwe scowe
  4: wequiwed wist<metwic_tags.metwictag> m-metwictags
} (pewsisted='twue', nyaa~~ haspewsonawdata = 'twue')

stwuct gettweetswecommendationsscwibe {
  1: wequiwed i64 uuid (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid') # wequestuuid - unique scwibe i-id fow evewy wequest that c-comes in. ^^ same wequest b-but diffewent s-stages of scwibe wog (fetchcandidate, >w< fiwtew, OwO etc) shawe the s-same uuid
  2: w-wequiwed i64 usewid (pewsonawdatatype = 'usewid')
  3: wequiwed w-wesuwt wesuwt
  4: o-optionaw i64 twaceid
  5: optionaw p-pewfowmancemetwics pewfowmancemetwics
  6: o-optionaw wist<impwessesedbucketinfo> impwessedbuckets
} (pewsisted='twue', XD haspewsonawdata = 'twue')

s-stwuct souwcesignaw {
  # optionaw, ^^;; since t-that the nyext step covews aww i-info hewe
  1: o-optionaw identifiew.intewnawid id
} (pewsisted='twue')

stwuct pewfowmancemetwics {
  1: optionaw i64 watencyms
} (pewsisted='twue')

stwuct tweetcandidatewithmetadata {
  1: wequiwed i64 tweetid (pewsonawdatatype = 'tweetid')
  2: optionaw c-candidate_genewation_key.candidategenewationkey c-candidategenewationkey
  3: optionaw i-i64 authowid (pewsonawdatatype = 'usewid') # o-onwy fow intewweavewesuwt f-fow hydwating twaining data
  4: optionaw doubwe scowe # s-scowe with wespect to candidategenewationkey
  5: optionaw data.datawecowd datawecowd # attach a-any featuwes to this candidate
  6: o-optionaw i-i32 nyumcandidategenewationkeys # n-nyum candidategenewationkeys genewating this t-tweetid  
} (pewsisted='twue')

s-stwuct fetchsignawsouwceswesuwt { 
  1: o-optionaw s-set<souwcesignaw> signaws
} (pewsisted='twue')

stwuct fetchcandidateswesuwt {
  1: o-optionaw wist<tweetcandidatewithmetadata> t-tweets
} (pewsisted='twue')

s-stwuct p-pwewankfiwtewwesuwt {
  1: optionaw w-wist<tweetcandidatewithmetadata> tweets
} (pewsisted='twue')

stwuct intewweavewesuwt {
  1: optionaw wist<tweetcandidatewithmetadata> tweets
} (pewsisted='twue')

s-stwuct wankwesuwt {
  1: optionaw wist<tweetcandidatewithmetadata> tweets
} (pewsisted='twue')

stwuct topwevewapiwesuwt {
  1: wequiwed i-i64 timestamp (pewsonawdatatype = 'pwivatetimestamp')
  2: wequiwed cw_mixew.cwmixewtweetwequest wequest
  3: wequiwed cw_mixew.cwmixewtweetwesponse w-wesponse
} (pewsisted='twue')

u-union wesuwt {
  1: f-fetchsignawsouwceswesuwt fetchsignawsouwceswesuwt
  2: f-fetchcandidateswesuwt fetchcandidateswesuwt
  3: p-pwewankfiwtewwesuwt p-pwewankfiwtewwesuwt
  4: intewweavewesuwt intewweavewesuwt
  5: wankwesuwt wankwesuwt
  6: topwevewapiwesuwt t-topwevewapiwesuwt
} (pewsisted='twue', 🥺 haspewsonawdata = 'twue')

s-stwuct impwessesedbucketinfo {
  1: wequiwed i-i64 expewimentid (pewsonawdatatype = 'expewimentid')
  2: wequiwed s-stwing bucketname
  3: wequiwed i32 vewsion
} (pewsisted='twue')

############# wewatedtweets s-scwibe #############

s-stwuct getwewatedtweetsscwibe {
  1: w-wequiwed i64 uuid (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid') # w-wequestuuid - unique scwibe id fow evewy wequest that comes in. XD same w-wequest but diffewent s-stages of s-scwibe wog (fetchcandidate, (U ᵕ U❁) fiwtew, e-etc) shawe t-the same uuid
  2: wequiwed identifiew.intewnawid i-intewnawid
  3: wequiwed wewatedtweetwesuwt wewatedtweetwesuwt
  4: optionaw i64 wequestewid (pewsonawdatatype = 'usewid')
  5: o-optionaw i64 guestid (pewsonawdatatype = 'guestid')
  6: o-optionaw i64 twaceid
  7: optionaw pewfowmancemetwics p-pewfowmancemetwics
  8: o-optionaw wist<impwessesedbucketinfo> impwessedbuckets
} (pewsisted='twue', :3 haspewsonawdata = 'twue')

stwuct w-wewatedtweettopwevewapiwesuwt {
  1: wequiwed i64 timestamp (pewsonawdatatype = 'pwivatetimestamp')
  2: wequiwed wewated_tweet.wewatedtweetwequest wequest
  3: w-wequiwed wewated_tweet.wewatedtweetwesponse wesponse
} (pewsisted='twue')

u-union wewatedtweetwesuwt {
  1: w-wewatedtweettopwevewapiwesuwt wewatedtweettopwevewapiwesuwt
  2: fetchcandidateswesuwt fetchcandidateswesuwt
  3: p-pwewankfiwtewwesuwt p-pwewankfiwtewwesuwt # wesuwts aftew seqentiaw fiwtews
  # i-if watew we nyeed wankwesuwt, w-we can add it hewe
} (pewsisted='twue', ( ͡o ω ͡o ) haspewsonawdata = 'twue')

############# utegtweets scwibe #############

stwuct getutegtweetsscwibe {
  1: w-wequiwed i64 uuid (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid') # wequestuuid - u-unique s-scwibe id fow evewy wequest t-that comes in. òωó same wequest but d-diffewent stages o-of scwibe wog (fetchcandidate, σωσ f-fiwtew, (U ᵕ U❁) etc) shawe the same uuid
  2: w-wequiwed i64 u-usewid (pewsonawdatatype = 'usewid')
  3: wequiwed utegtweetwesuwt u-utegtweetwesuwt
  4: o-optionaw i-i64 twaceid
  5: optionaw pewfowmancemetwics pewfowmancemetwics
  6: o-optionaw wist<impwessesedbucketinfo> i-impwessedbuckets
} (pewsisted='twue', (✿oωo) h-haspewsonawdata = 'twue')

stwuct utegtweettopwevewapiwesuwt {
  1: wequiwed i64 timestamp (pewsonawdatatype = 'pwivatetimestamp')
  2: w-wequiwed u-uteg.utegtweetwequest w-wequest
  3: w-wequiwed uteg.utegtweetwesponse w-wesponse
} (pewsisted='twue')

union utegtweetwesuwt {
  1: utegtweettopwevewapiwesuwt utegtweettopwevewapiwesuwt
  2: fetchcandidateswesuwt fetchcandidateswesuwt
  # if watew we nyeed wankwesuwt, ^^ we c-can add it hewe
} (pewsisted='twue', ^•ﻌ•^ haspewsonawdata = 'twue')

############# g-getadswecommendations() scwibe #############

s-stwuct getadswecommendationsscwibe {
  1: w-wequiwed i64 uuid (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid') # w-wequestuuid - unique s-scwibe id f-fow evewy wequest t-that comes in. XD s-same wequest but diffewent stages of scwibe wog (fetchcandidate, :3 fiwtew, (ꈍᴗꈍ) etc) shawe the same uuid
  2: wequiwed i64 usewid (pewsonawdatatype = 'usewid')
  3: wequiwed a-adswecommendationswesuwt w-wesuwt
  4: optionaw i-i64 twaceid
  5: optionaw p-pewfowmancemetwics pewfowmancemetwics
  6: optionaw wist<impwessesedbucketinfo> i-impwessedbuckets
} (pewsisted='twue', :3 h-haspewsonawdata = 'twue')

stwuct adswecommendationtopwevewapiwesuwt {
  1: w-wequiwed i64 timestamp (pewsonawdatatype = 'pwivatetimestamp')
  2: wequiwed ads.adswequest wequest
  3: w-wequiwed a-ads.adswesponse wesponse
} (pewsisted='twue')

u-union adswecommendationswesuwt{
  1: a-adswecommendationtopwevewapiwesuwt adswecommendationtopwevewapiwesuwt
  2: fetchcandidateswesuwt fetchcandidateswesuwt
}(pewsisted='twue', (U ﹏ U) haspewsonawdata = 'twue')
