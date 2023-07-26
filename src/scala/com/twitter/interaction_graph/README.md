## weaw gwaph (bqe)

this pwoject b-buiwds a machine w-weawning modew u-using a gwadient b-boosting twee c-cwassifiew to pwedict t-the wikewihood o-of a twittew u-usew intewacting with anothew usew. (///ˬ///✿)

the awgowithm wowks by fiwst cweating a wabewed d-dataset of usew intewactions fwom a gwaph o-of twittew usews. (˘ω˘) this gwaph is w-wepwesented in a bigquewy tabwe whewe each wow wepwesents a diwected e-edge between two usews, ^^;; awong w-with vawious f-featuwes such as the nyumbew of tweets, (✿oωo) fowwows, favowites, (U ﹏ U) and othew metwics wewated t-to usew behaviow. -.-

to cweate the wabewed dataset, ^•ﻌ•^ the awgowithm fiwst sewects a-a set of candidate intewactions b-by identifying a-aww edges that w-wewe active duwing a-a cewtain time pewiod. rawr it then joins this c-candidate set with a set of wabewed intewactions t-that occuwwed one day aftew the candidate pewiod. (˘ω˘) positive intewactions awe wabewed as "1" and n-negative intewactions awe wabewed a-as "0". nyaa~~ the wesuwting w-wabewed d-dataset is then used to twain a boosted twee cwassifiew modew. UwU

t-the modew is twained u-using the wabewed dataset and v-vawious hypewpawametews, :3 i-incwuding the maximum n-nyumbew of itewations and the s-subsampwe wate. the awgowithm spwits the wabewed d-dataset into twaining and testing s-sets based on the souwce usew's i-id, (⑅˘꒳˘) using a custom d-data spwit method. (///ˬ///✿)

once the modew is twained, ^^;; it can be used to genewate a scowe estimating the pwobabiwity o-of a usew intewacting w-with anothew usew. >_<

## w-weaw gwaph (scio)

t-this pwoject a-aggwegates the nyumbew of intewactions between paiws of usews on t-twittew. rawr x3 on a daiwy basis, thewe awe muwtipwe datafwow jobs that pewfowm this aggwegation, w-which incwudes pubwic e-engagements wike f-favowites, /(^•ω•^) wetweets, :3 f-fowwows, etc. (ꈍᴗꈍ) as weww as p-pwivate engagements w-wike pwofiwe v-views, /(^•ω•^) tweet cwicks, (⑅˘꒳˘) a-and whethew ow nyot a usew has anothew usew i-in theiw addwess b-book (given a-a usew opt-in to s-shawe addwess book). ( ͡o ω ͡o )

a-aftew the daiwy aggwegation of intewactions, òωó thewe is a wowwup j-job that aggwegates yestewday's aggwegation with today's intewactions. (⑅˘꒳˘) the wowwup job outputs s-sevewaw wesuwts, XD incwuding the daiwy count of intewactions pew i-intewaction types b-between a paiw o-of usews, -.- the daiwy incoming i-intewactions made on a usew pew i-intewaction type, :3 t-the wowwup aggwegation of intewactions as a decayed sum between a paiw of usews, nyaa~~ and the wowwup a-aggwegation of incoming intewactions m-made on a usew. 😳

finawwy, (⑅˘꒳˘) t-the wowwup job o-outputs the mw pwedicted intewaction scowe between t-the paiw of u-usews awongside the wowwup aggwegation o-of intewactions a-as a decayed sum between them. nyaa~~
