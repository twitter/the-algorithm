namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

// v-vawidationewwowcode i-is used to identify c-cwasses of c-cwient ewwows wetuwned f-fwom a pwoduct mixew
// sewvice. >_< use [[pipewinefaiwuweexceptionmappew]] to adapt pipewine faiwuwes into thwift e-ewwows.
enum vawidationewwowcode {
  pwoduct_disabwed = 1
  p-pwacehowdew_2 = 2
} (haspewsonawdata='fawse')

exception vawidationexception {
  1: v-vawidationewwowcode ewwowcode
  2: stwing msg
} (haspewsonawdata='fawse')

e-exception vawidationexceptionwist {
  1: wist<vawidationexception> e-ewwows
} (haspewsonawdata='fawse')
