namespace java com.twittew.unified_usew_actions.enwichew.intewnaw.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.enwichew.intewnaw.thwiftscawa
#@namespace s-stwato c-com.twittew.unified_usew_actions.enwichew.intewnaw

/**
* a-an enwichment p-pwan. (˘ω˘) it h-has muwtipwe stages f-fow diffewent p-puwposes duwing t-the enwichment pwocess. ^^
**/
stwuct enwichmentpwan {
  1: wequiwed wist<enwichmentstage> s-stages
}(pewsisted='twue', :3 haspewsonawdata='fawse')

/**
* a stage in t-the enwichment pwocess with wespect t-to the cuwwent key. -.- cuwwentwy it can be of 2 options:
* - we-pawtitioning on a-an id of type x
* - hydwating m-metadata on an id o-of type x
*
* a stage awso moves thwough diffewent statues fwom initiawized, 😳 pwocessing u-untiw compwetion. mya
* each stage contains one ow mowe instwuctions. (˘ω˘)
**/
stwuct enwichmentstage {
  1: w-wequiwed enwichmentstagestatus s-status
  2: w-wequiwed e-enwichmentstagetype s-stagetype
  3: wequiwed wist<enwichmentinstwuction> instwuctions

  // t-the output topic fow this stage. >_< this i-infowmation is nyot avaiwabwe when the stage was
  // fiwst setup, -.- and it's onwy avaiwabwe aftew t-the dwivew has finished wowking o-on
  // this s-stage. 🥺
  4: optionaw s-stwing outputtopic
}(pewsisted='twue', (U ﹏ U) haspewsonawdata='fawse')

/**
* the cuwwent pwocessing s-status of a s-stage. >w< it shouwd eithew be done (compwetion) o-ow n-not done (initiawized). mya
* twansient s-statuses such as "pwocessing" i-is dangewous since we can't exactwy be suwe that h-has been done. >w<
**/
enum enwichmentstagestatus {
  i-initiawized = 0
  compwetion = 20
}

/**
* t-the type of pwocessing i-in this stage. nyaa~~ fow exampwe, (✿oωo) wepawtioning the data ow hydwating the data. ʘwʘ
**/
enum enwichmentstagetype {
  wepawtition = 0
  h-hydwation = 10
}

e-enum enwichmentinstwuction {
  // aww enwichment b-based on a t-tweet id in uua g-goes hewe
  tweetenwichment = 0
  nyotificationtweetenwichment = 10
}
