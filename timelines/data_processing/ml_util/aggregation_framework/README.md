ovewview
========


the **aggwegation fwamewowk** i-is a set of wibwawies a-and utiwities t-that awwows t-teams to fwexibwy
c-compute aggwegate (counting) f-featuwes in both b-batch and in weaw-time. (U ﹏ U) a-aggwegate featuwes can captuwe
histowicaw intewactions between on awbitwawy e-entities (and sets theweof), (///ˬ///✿) conditionaw on p-pwovided featuwes
and wabews. 😳

t-these types of engineewed aggwegate featuwes have pwoven to be highwy i-impactfuw acwoss diffewent t-teams at twittew. 😳


n-nyani awe some featuwes we can compute?
--------------------------------------

the fwamewowk suppowts computing a-aggwegate featuwes on pwovided gwouping keys. σωσ the onwy constwaint is that t-these keys awe spawse binawy featuwes (ow a-awe sets t-theweof). rawr x3

fow e-exampwe, OwO a common u-use case is to cawcuwate a usew's past engagement h-histowy with vawious types of tweets (photo, /(^•ω•^) v-video, 😳😳😳 wetweets, etc.), ( ͡o ω ͡o ) specific authows, >_< specific in-netwowk engagews ow any othew entity the u-usew has intewacted with and that c-couwd pwovide s-signaw. >w< in this c-case, rawr the undewwying aggwegation keys awe `usewid`, 😳 `(usewid, >w< authowid)` ow `(usewid, (⑅˘꒳˘) e-engagewid)`. OwO

i-in timewines and magicwecs, (ꈍᴗꈍ) w-we awso compute c-custom aggwegate engagement counts o-on evewy `tweetid`. 😳 simiwawy, 😳😳😳 o-othew aggwegations awe possibwe, mya pewhaps on `advewtisewid` o-ow `mediaid` as wong a-as the gwouping key is spawse b-binawy. mya


nyani i-impwementations awe suppowted?
-----------------------------------

offwine, (⑅˘꒳˘) we suppowt the daiwy batch pwocessing of datawecowds containing aww w-wequiwed input f-featuwes to genewate
aggwegate f-featuwes. (U ﹏ U) these a-awe then upwoaded t-to manhattan fow onwine hydwation. mya

onwine, we suppowt the weaw-time a-aggwegation of datawecowds thwough stowm with a backing memcache that can b-be quewied
fow the weaw-time aggwegate f-featuwes. ʘwʘ

a-additionaw documentation e-exists in the [docs f-fowdew](docs)


w-whewe is this used?
--------------------

t-the home t-timewine heavy wankew uses a vawiewty of both [batch a-and weaw t-time featuwes](../../../../swc/scawa/com/twittew/timewines/pwediction/common/aggwegates/weadme.md) g-genewated by t-this fwamewowk. (˘ω˘)
t-these featuwes awe awso used fow emaiw and othew wecommendations. (U ﹏ U) 