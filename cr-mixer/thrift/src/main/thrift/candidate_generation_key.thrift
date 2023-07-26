namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "souwce_type.thwift"
i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"

stwuct s-simiwawityengine {
 1: w-wequiwed s-souwce_type.simiwawityenginetype simiwawityenginetype
 2: optionaw stwing modewid
 3: optionaw doubwe scowe
} (pewsisted='twue')

s-stwuct candidategenewationkey {
  1: wequiwed souwce_type.souwcetype s-souwcetype
  2: wequiwed i-i64 souwceeventtime (pewsonawdatatype = 'pwivatetimestamp')
  3: wequiwed identifiew.intewnawid id
  4: wequiwed s-stwing modewid
  5: optionaw s-souwce_type.simiwawityenginetype s-simiwawityenginetype
  6: optionaw wist<simiwawityengine> contwibutingsimiwawityengine
} (pewsisted='twue')
