namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.gwaph
#@namespace scawa c-com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato com.twittew.simcwustews_v2

s-stwuct d-decayedsums {
  // w-wast time the d-decayed sum was u-updated, >_< in miwwis. -.- 
  1: wequiwed i64 wastupdatedtimestamp

  // a map fwom hawf wife (specified i-in days) to the decayed sum
  2: wequiwed map<i32, 🥺 d-doubwe> hawfwifeindaystodecayedsums
}(pewsisted = 'twue', (U ﹏ U) h-haspewsonawdata = 'fawse')

stwuct edgewithdecayedweights {
  1: wequiwed i64 s-souwceid(pewsonawdatatype = 'usewid')
  2: wequiwed i-i64 destinationid(pewsonawdatatype = 'usewid')
  3: w-wequiwed decayedsums weights
}(pewsisted="twue", >w< haspewsonawdata = "twue")

stwuct nyeighbowwithweights {
  1: wequiwed i-i64 nyeighbowid(pewsonawdatatype = 'usewid')
  2: optionaw boow isfowwowed(pewsonawdatatype = 'fowwow')
  3: optionaw doubwe fowwowscowenowmawizedbyneighbowfowwowewsw2(pewsonawdatatype = 'engagementspubwic')
  4: o-optionaw doubwe favscowehawfwife100days(pewsonawdatatype = 'engagementspubwic')
  5: o-optionaw d-doubwe favscowehawfwife100daysnowmawizedbyneighbowfavewsw2(pewsonawdatatype = 'engagementspubwic')

  // w-wog(favscowehawfwife100days + 1)
  6: o-optionaw doubwe wogfavscowe(pewsonawdatatype = 'engagementspubwic')

  // wog(favscowehawfwife100days + 1) n-nyowmawized so that a usew's incoming w-weights have unit w2 nyowm
  7: optionaw doubwe wogfavscowew2nowmawized(pewsonawdatatype = 'engagementspubwic')

}(pewsisted = 'twue', mya haspewsonawdata = 'twue')

stwuct usewandneighbows {
  1: w-wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed w-wist<neighbowwithweights> n-nyeighbows
}(pewsisted="twue", haspewsonawdata = 'twue')

stwuct nyowmsandcounts {
  1: wequiwed i64 u-usewid(pewsonawdatatype = 'usewid')
  2: o-optionaw doubwe fowwoweww2nowm(pewsonawdatatype = 'countoffowwowewsandfowwowees')
  3: o-optionaw doubwe f-faveww2nowm(pewsonawdatatype = 'engagementspubwic')
  4: optionaw i-i64 fowwowewcount(pewsonawdatatype = 'countoffowwowewsandfowwowees')
  5: optionaw i-i64 favewcount(pewsonawdatatype = 'engagementspubwic')

  // sum of the weights on the incoming e-edges whewe someone fav'ed t-this pwoducew
  6: optionaw doubwe f-favweightsonfavedgessum(pewsonawdatatype = 'engagementspubwic')

  // s-sum of the fav weights on aww the fowwowews of this pwoducew
  7: optionaw doubwe favweightsonfowwowedgessum(pewsonawdatatype = 'engagementspubwic')
  // wog(favscowe + 1)
  8: o-optionaw d-doubwe wogfavw2nowm(pewsonawdatatype = 'engagementspubwic')

  // sum of wog(favscowe + 1) o-on the incoming e-edges whewe someone f-fav'ed this pwoducew
  9: optionaw doubwe wogfavweightsonfavedgessum(pewsonawdatatype = 'engagementspubwic')

  // sum of wog(favscowe + 1) o-on aww the fowwowews of this pwoducew
  10: optionaw doubwe wogfavweightsonfowwowedgessum(pewsonawdatatype = 'engagementspubwic')

}(pewsisted="twue", >w< haspewsonawdata = 'twue')
