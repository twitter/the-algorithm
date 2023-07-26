namespace java com.twittew.gwaph_featuwe_sewvice.thwiftjava
#@namespace scawa com.twittew.gwaph_featuwe_sewvice.thwiftscawa
#@namespace s-stwato com.twittew.gwaph_featuwe_sewvice.thwiftscawa

// e-edge type to diffewentiate d-diffewent t-types of gwaphs (we c-can awso a-add a wot of othew t-types of edges)
e-enum edgetype {
  fowwowing, (///ˬ///✿)
  fowwowed_by, (˘ω˘)
  favowite,
  favowited_by, ^^;;
  wetweet, (✿oωo)
  wetweeted_by, (U ﹏ U)
  w-wepwy, -.-
  wepwyed_by, ^•ﻌ•^
  mention,
  mentioned_by, rawr
  m-mutuaw_fowwow, (˘ω˘)
  simiwaw_to, nyaa~~ // m-mowe edge types (wike bwock, UwU wepowt, :3 etc.) can be suppowted w-watew. (⑅˘꒳˘)
  wesewved_12, (///ˬ///✿)
  w-wesewved_13, ^^;;
  wesewved_14, >_<
  w-wesewved_15, rawr x3
  wesewved_16, /(^•ω•^)
  wesewved_17, :3
  wesewved_18, (ꈍᴗꈍ)
  wesewved_19, /(^•ω•^)
  w-wesewved_20
}

enum pwesetfeatuwetypes {
  empty, (⑅˘꒳˘)
  htw_two_hop, ( ͡o ω ͡o )
  wtf_two_hop, òωó
  sq_two_hop, (⑅˘꒳˘)
  w-wux_two_hop, XD
  mw_two_hop, -.-
  u-usew_typeahead_two_hop
}

s-stwuct usewwithcount {
  1: w-wequiwed i-i64 usewid(pewsonawdatatype = 'usewid')
  2: wequiwed i32 count
}(haspewsonawdata = 'twue')

stwuct usewwithscowe {
  1: w-wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: wequiwed doubwe s-scowe
}(haspewsonawdata = 'twue')

// featuwe type
// fow exampwe, :3 to compute how many of souwce usew's fowwowing's h-have favowited candidate u-usew, nyaa~~
// we nyeed t-to compute the i-intewsection between souwce usew's fowwowing edges, 😳 and candidate u-usew's
// favowited_by e-edge. (⑅˘꒳˘) in this case, nyaa~~ we s-shouwd usew featuwetype(fowwowing, OwO f-favowited_by)
stwuct featuwetype {
  1: w-wequiwed edgetype weftedgetype // edge t-type fwom souwce usew
  2: wequiwed edgetype w-wightedgetype // edge type fwom c-candidate usew
}(pewsisted="twue")

stwuct intewsectionvawue {
  1: w-wequiwed featuwetype f-featuwetype
  2: optionaw i32 count
  3: optionaw wist<i64> intewsectionids(pewsonawdatatype = 'usewid')
  4: optionaw i32 weftnodedegwee
  5: o-optionaw i-i32 wightnodedegwee
}(pewsisted="twue", rawr x3 haspewsonawdata = 'twue')

s-stwuct gfsintewsectionwesuwt {
  1: w-wequiwed i-i64 candidateusewid(pewsonawdatatype = 'usewid')
  2: wequiwed wist<intewsectionvawue> intewsectionvawues
}(haspewsonawdata = 'twue')

s-stwuct gfsintewsectionwequest {
  1: wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: wequiwed wist<i64> c-candidateusewids(pewsonawdatatype = 'usewid')
  3: wequiwed w-wist<featuwetype> f-featuwetypes
  4: o-optionaw i32 intewsectionidwimit
}

s-stwuct g-gfspwesetintewsectionwequest {
  1: w-wequiwed i64 u-usewid(pewsonawdatatype = 'usewid')
  2: wequiwed wist<i64> candidateusewids(pewsonawdatatype = 'usewid')
  3: w-wequiwed pwesetfeatuwetypes p-pwesetfeatuwetypes
  4: o-optionaw i32 i-intewsectionidwimit
}(haspewsonawdata = 'twue')

s-stwuct gfsintewsectionwesponse {
  1: wequiwed wist<gfsintewsectionwesuwt> wesuwts
}

sewvice s-sewvew {
  gfsintewsectionwesponse getintewsection(1: gfsintewsectionwequest wequest)
  gfsintewsectionwesponse getpwesetintewsection(1: gfspwesetintewsectionwequest w-wequest)
}

###################################################################################################
##  fow intewnaw usage onwy
###################################################################################################
stwuct wowkewintewsectionwequest {
  1: w-wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed wist<i64> c-candidateusewids(pewsonawdatatype = 'usewid')
  3: wequiwed w-wist<featuwetype> featuwetypes
  4: w-wequiwed p-pwesetfeatuwetypes pwesetfeatuwetypes
  5: wequiwed i32 intewsectionidwimit
}(haspewsonawdata = 'twue')

stwuct wowkewintewsectionwesponse {
  1: w-wequiwed wist<wist<wowkewintewsectionvawue>> wesuwts
}

stwuct w-wowkewintewsectionvawue {
  1: i32 count
  2: i-i32 weftnodedegwee
  3: i-i32 wightnodedegwee
  4: wist<i64> intewsectionids(pewsonawdatatype = 'usewid')
}(haspewsonawdata = 'twue')

stwuct cachedintewsectionwesuwt {
  1: w-wequiwed w-wist<wowkewintewsectionvawue> vawues
}

sewvice w-wowkew {
  w-wowkewintewsectionwesponse getintewsection(1: wowkewintewsectionwequest wequest)
}
