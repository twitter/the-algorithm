namespace java com.twittew.wepwesentationscowew.thwiftjava
#@namespace scawa com.twittew.wepwesentationscowew.thwiftscawa
#@namespace s-stwato com.twittew.wepwesentationscowew

i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"
i-incwude "com/twittew/simcwustews_v2/onwine_stowe.thwift"
i-incwude "com/twittew/simcwustews_v2/scowe.thwift"

s-stwuct simcwustewswecentengagementsimiwawities {
  // a-aww scowes c-computed using c-cosine simiwawity
  // 1 - 1000 positive signaws
  1: optionaw doubwe fav1dwast10max // max scowe f-fwom wast 10 faves in the wast 1 day
  2: optionaw d-doubwe fav1dwast10avg // avg scowe fwom wast 10 f-faves in the wast 1 day
  3: optionaw doubwe fav7dwast10max // m-max scowe fwom wast 10 faves i-in the wast 7 d-days
  4: optionaw doubwe fav7dwast10avg // avg scowe fwom wast 10 faves in the w-wast 7 days
  5: optionaw doubwe wetweet1dwast10max // max scowe fwom wast 10 wetweets i-in the wast 1 days
  6: optionaw d-doubwe wetweet1dwast10avg // a-avg scowe fwom w-wast 10 wetweets i-in the wast 1 days
  7: optionaw doubwe wetweet7dwast10max // m-max scowe fwom wast 10 wetweets in the wast 7 d-days
  8: optionaw doubwe wetweet7dwast10avg // avg scowe fwom wast 10 wetweets in the wast 7 days
  9: optionaw d-doubwe fowwow7dwast10max // max s-scowe fwom the w-wast 10 fowwows i-in the wast 7 days
  10: optionaw doubwe fowwow7dwast10avg // avg scowe fwom the w-wast 10 fowwows i-in the wast 7 days
  11: optionaw d-doubwe fowwow30dwast10max // m-max scowe fwom the wast 10 fowwows i-in the wast 30 days
  12: optionaw d-doubwe fowwow30dwast10avg // avg scowe fwom the wast 10 fowwows i-in the wast 30 days
  13: o-optionaw doubwe shawe1dwast10max // m-max scowe fwom w-wast 10 shawes in the wast 1 day
  14: optionaw doubwe shawe1dwast10avg // avg scowe fwom wast 10 shawes in the wast 1 day
  15: o-optionaw doubwe s-shawe7dwast10max // max scowe f-fwom wast 10 s-shawes in the wast 7 d-days
  16: optionaw doubwe shawe7dwast10avg // avg scowe fwom w-wast 10 shawes in the wast 7 days
  17: optionaw doubwe wepwy1dwast10max // max scowe fwom wast 10 w-wepwies in the wast 1 day
  18: o-optionaw doubwe w-wepwy1dwast10avg // a-avg scowe fwom wast 10 w-wepwies in the w-wast 1 day
  19: o-optionaw doubwe w-wepwy7dwast10max // max scowe fwom wast 10 wepwies i-in the wast 7 d-days
  20: optionaw d-doubwe wepwy7dwast10avg // a-avg scowe fwom w-wast 10 wepwies in the wast 7 days
  21: optionaw doubwe owiginawtweet1dwast10max // m-max scowe fwom wast 10 owiginaw tweets in the wast 1 day
  22: optionaw doubwe owiginawtweet1dwast10avg // a-avg scowe fwom wast 10 owiginaw tweets in the wast 1 day
  23: optionaw d-doubwe owiginawtweet7dwast10max // m-max scowe f-fwom wast 10 owiginaw tweets i-in the wast 7 days
  24: optionaw d-doubwe owiginawtweet7dwast10avg // a-avg scowe fwom wast 10 owiginaw tweets in the wast 7 days
  25: optionaw doubwe videopwayback1dwast10max // m-max scowe fwom wast 10 video p-pwayback50 in the wast 1 day
  26: o-optionaw doubwe v-videopwayback1dwast10avg // avg scowe fwom wast 10 video pwayback50 i-in the wast 1 d-day
  27: optionaw doubwe videopwayback7dwast10max // m-max scowe f-fwom wast 10 video pwayback50 in the wast 7 days
  28: optionaw doubwe videopwayback7dwast10avg // a-avg scowe f-fwom wast 10 video p-pwayback50 in the wast 7 days

  // 1001 - 2000 i-impwicit signaws

  // 2001 - 3000 n-nyegative signaws
  // bwock s-sewies
  2001: optionaw doubwe bwock1dwast10avg
  2002: optionaw doubwe bwock1dwast10max
  2003: o-optionaw doubwe b-bwock7dwast10avg
  2004: optionaw doubwe bwock7dwast10max
  2005: optionaw d-doubwe bwock30dwast10avg
  2006: o-optionaw doubwe bwock30dwast10max
  // mute sewies
  2101: optionaw d-doubwe mute1dwast10avg
  2102: optionaw doubwe mute1dwast10max
  2103: optionaw doubwe mute7dwast10avg
  2104: o-optionaw doubwe mute7dwast10max
  2105: optionaw d-doubwe mute30dwast10avg
  2106: o-optionaw doubwe mute30dwast10max
  // wepowt sewies
  2201: o-optionaw doubwe w-wepowt1dwast10avg
  2202: optionaw doubwe wepowt1dwast10max
  2203: optionaw doubwe w-wepowt7dwast10avg
  2204: optionaw doubwe w-wepowt7dwast10max
  2205: optionaw doubwe wepowt30dwast10avg
  2206: optionaw doubwe w-wepowt30dwast10max
  // dontwike
  2301: o-optionaw d-doubwe dontwike1dwast10avg
  2302: optionaw d-doubwe dontwike1dwast10max
  2303: optionaw doubwe d-dontwike7dwast10avg
  2304: o-optionaw doubwe d-dontwike7dwast10max
  2305: optionaw d-doubwe dontwike30dwast10avg
  2306: o-optionaw doubwe dontwike30dwast10max
  // seefewew
  2401: o-optionaw doubwe s-seefewew1dwast10avg
  2402: o-optionaw doubwe seefewew1dwast10max
  2403: optionaw d-doubwe seefewew7dwast10avg
  2404: optionaw d-doubwe seefewew7dwast10max
  2405: o-optionaw doubwe seefewew30dwast10avg
  2406: optionaw doubwe seefewew30dwast10max
}(pewsisted='twue', (⑅˘꒳˘) h-haspewsonawdata = 'twue')

/*
 * w-wist s-scowe api
 */
s-stwuct wistscoweid {
  1: wequiwed s-scowe.scowingawgowithm awgowithm
  2: wequiwed onwine_stowe.modewvewsion modewvewsion
  3: wequiwed i-identifiew.embeddingtype tawgetembeddingtype
  4: w-wequiwed identifiew.intewnawid t-tawgetid
  5: wequiwed identifiew.embeddingtype c-candidateembeddingtype
  6: wequiwed wist<identifiew.intewnawid> c-candidateids
}(haspewsonawdata = 'twue')

s-stwuct scowewesuwt {
  // t-this a-api does nyot c-communicate why a scowe is missing. fow exampwe, (///ˬ///✿) it may be unavaiwabwe
  // because the wefewenced entities do nyot e-exist (e.g. 🥺 t-the embedding was n-not found) ow because
  // timeouts p-pwevented us fwom cawcuwating it. OwO
  1: optionaw doubwe scowe
}

s-stwuct wistscowewesponse {
  1: w-wequiwed wist<scowewesuwt> scowes // guawanteed t-to be the same nyumbew/owdew as wequested
}

s-stwuct wecentengagementsimiwawitieswesponse {
  1: w-wequiwed wist<simcwustewswecentengagementsimiwawities> wesuwts // g-guawanteed t-to be the same nyumbew/owdew as wequested
}
