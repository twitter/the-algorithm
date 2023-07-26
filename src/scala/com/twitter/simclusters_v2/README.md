# simcwustews: community-based wepwesentations f-fow h-hetewogeneous w-wecommendations a-at twittew

## ovewview
s-simcwustews i-is as a genewaw-puwpose w-wepwesentation w-wayew based on ovewwapping communities into which usews as weww as hetewogeneous c-content can be captuwed as spawse, rawr x3 intewpwetabwe v-vectows to suppowt a-a muwtitude of wecommendation tasks. ( ͡o ω ͡o )

we buiwd ouw usew and tweet s-simcwustews embeddings based on t-the infewwed communities, (˘ω˘) a-and the wepwesentations powew ouw pewsonawized tweet wecommendation v-via ouw onwine sewving sewvice simcwustews ann. 😳


fow mowe detaiws, OwO pwease wead o-ouw papew that was pubwished in k-kdd'2020 appwied d-data science twack: h-https://www.kdd.owg/kdd2020/accepted-papews/view/simcwustews-community-based-wepwesentations-fow-hetewogeneous-wecommendatio

## b-bwief intwoduction to simcwustews awgowithm

### f-fowwow wewationships as a bipawtite gwaph
f-fowwow wewationships on twittew awe pewhaps most nyatuwawwy thought of as diwected gwaph, (˘ω˘) whewe e-each nyode is a usew and each edge w-wepwesents a f-fowwow. òωó edges awe d-diwected in that usew 1 can fowwow usew 2, ( ͡o ω ͡o ) usew 2 can fowwow u-usew 1 ow both usew 1 a-and usew 2 can fowwow each o-othew. UwU

this diwected g-gwaph can be awso viewed a-as a bipawtite gwaph, /(^•ω•^) whewe nyodes a-awe gwouped into two sets, (ꈍᴗꈍ) pwoducews and consumews. 😳 i-in this bipawtite gwaph, mya p-pwoducews awe the usews who awe f-fowwowed and consumews a-awe the fowwowees. mya bewow is a toy exampwe of a fowwow gwaph fow fouw usews:

<img swc="images/bipawtite_gwaph.png" width = "400px">

> f-figuwe 1 - w-weft panew: a diwected f-fowwow gwaph; wight p-panew: a bipawtite g-gwaph wepwesentation of the diwected gwaph

### community d-detection - known fow 
the bipawtite fowwow gwaph can be used to identify gwoups o-of pwoducews who have simiwaw f-fowwowews, /(^•ω•^) ow who a-awe "known fow" a-a topic. ^^;; specificawwy, 🥺 the bipawtite f-fowwow gwaph c-can awso be w-wepwesented as an *m x-x ny* matwix (*a*), ^^ whewe consumews awe pwesented a-as *u* and p-pwoducews awe w-wepwesented as *v*. ^•ﻌ•^

p-pwoducew-pwoducew s-simiwawity is computed as the cosine simiwawity between usews w-who fowwow each pwoducew. /(^•ω•^) the wesuwting cosine simiwawity vawues can be used to constwuct a p-pwoducew-pwoducew simiwawity gwaph, ^^ whewe the nyodes awe pwoducews a-and edges awe w-weighted by the c-cowwesponding cosine simiwawity v-vawue. 🥺 nyoise wemovaw is pewfowmed, s-such that e-edges with weights bewow a specified thweshowd awe deweted fwom the gwaph. (U ᵕ U❁)

aftew nyoise wemovaw h-has been compweted, 😳😳😳 metwopowis-hastings s-sampwing-based community d-detection is then w-wun on the pwoducew-pwoducew simiwawity gwaph to identify a c-community affiwiation f-fow each pwoducew. nyaa~~ this awgowithm t-takes in a-a pawametew *k* fow the nyumbew of communities to be detected. (˘ω˘)

<img swc="images/pwoducew_pwoducew_simiwawity.png">

> f-figuwe 2 -  w-weft panew: m-matwix wepwesentation of the fowwow g-gwaph depicted i-in figuwe 1; middwe panew: pwoducew-pwoducew s-simiwawity is estimated by cawcuwating the cosine simiwawity between the usews who f-fowwow each pwoducew; w-wight panew: cosine simiwawity scowes awe u-used to cweate t-the pwoducew-pwoducew simiwawity gwaph. >_< a cwustewing awgowithm i-is wun on the gwaph to identify gwoups of pwoducews with simiwaw fowwowews. XD

community a-affiwiation scowes awe then used to constwuct a-an *n x k* "known f-fow" matwix (*v*). rawr x3 this matwix is maximawwy spawse, ( ͡o ω ͡o ) and e-each pwoducew is a-affiwiated with at most one community. :3 in pwoduction, mya the known f-fow dataset covews the top 20m p-pwoducews and k ~= 145000. σωσ in othew wowds, (ꈍᴗꈍ) we discovew awound 145k c-communities based on twittew's u-usew fowwow gwaph. OwO

<img s-swc="images/knownfow.png">

> figuwe 3 -  t-the cwustewing awgowithm wetuwns c-community a-affiwiation scowes f-fow each pwoducew. o.O these scowes a-awe wepwesented i-in matwix v. 😳😳😳

in the exampwe above, /(^•ω•^) pwoducew 1 i-is "known fow" c-community 2, OwO pwoducew 2 i-is "known fow" community 1, ^^ and so fowth. (///ˬ///✿)

### c-consumew embeddings - usew i-intewestedin
a-an intewested in matwix (*u*) can be computed by muwtipwying the m-matwix wepwesentation o-of the fowwow g-gwaph (*a*) b-by the known fow matwix (*v*): 

<img s-swc="images/intewestedin.png">

in this toy exampwe, (///ˬ///✿) consumew 1 is intewested in community 1 onwy, (///ˬ///✿) wheweas c-consumew 3 is intewested in aww t-thwee communities. ʘwʘ thewe is awso a-a nyoise wemovaw step appwied t-to the intewested in matwix.

we u-use the intewestedin e-embeddings t-to captuwe consumew's w-wong-tewm i-intewest. ^•ﻌ•^ the intewestedin embeddings is one of ouw majow souwce fow consumew-based tweet wecommendations. OwO

### pwoducew embeddings
w-when computing t-the known fow m-matwix, (U ﹏ U) each pwoducew can onwy b-be known fow a singwe community. (ˆ ﻌ ˆ)♡ awthough this maximawwy spawse m-matwix is usefuw f-fwom a computationaw pewspective, (⑅˘꒳˘) w-we know that ouw usews tweet about many diffewent t-topics and m-may be "known" in many diffewent c-communities. p-pwoducew embeddings ( *Ṽ* )  awe used to captuwe this wichew stwuctuwe of the gwaph. (U ﹏ U)

to cawcuwate p-pwoducew embeddings, o.O t-the cosine s-simiwawity i-is cawcuwated between e-each pwoducew’s fowwow gwaph a-and the intewested i-in vectow fow each community. mya

<img s-swc="images/pwoducew_embeddings.png">

p-pwoducew embeddings awe used f-fow pwoducew-based tweet wecommendations. fow exampwe, XD w-we can wecommend simiwaw t-tweets based on a-an account you just fowwowed. òωó

### e-entity embeddings
simcwustews can awso be used t-to genewate embeddings f-fow diffewent k-kind of contents, (˘ω˘) such as
- tweets (used fow tweet wecommendations)
- t-topics (used fow topicfowwow)

#### tweet embeddings
w-when a tweet is c-cweated, :3 its tweet embedding is i-initiawized as an empty vectow. OwO
t-tweet embeddings a-awe updated each time the tweet is favowited. mya s-specificawwy, (˘ω˘) the intewestedin vectow of each usew w-who fav-ed the t-tweet is added to the tweet vectow. o.O
s-since tweet embeddings awe u-updated each time a-a tweet is favowited, (✿oωo) t-they change ovew time. (ˆ ﻌ ˆ)♡

tweet embeddings awe cwiticaw fow ouw tweet wecommendation tasks. ^^;; we can cawcuwate tweet simiwawity and wecommend simiwaw tweets to usews based on theiw tweet engagement histowy. OwO

w-we have a o-onwine hewon job that updates the tweet embeddings i-in weawtime, 🥺 c-check out [hewe](summingbiwd/weadme.md) f-fow mowe. mya 

#### topic embeddings
t-topic embeddings (**w**) a-awe detewmined b-by taking the cosine simiwawity b-between consumews who awe intewested i-in a community a-and the nyumbew of aggwegated favowites each c-consumew has t-taken on a tweet t-that has a topic a-annotation (with s-some time decay). 😳

<img s-swc="images/topic_embeddings.png">


## p-pwoject diwectowy o-ovewview
the w-whowe simcwustews pwoject can b-be undewstood as 2 m-main components
- s-simcwustews offwine jobs (scawding / g-gcp)
- simcwustews weaw-time stweaming j-jobs 

### simcwustews offwine j-jobs

**simcwustews s-scawding jobs**

| j-jobs   | code  | descwiption  |
|---|---|---|
| k-knownfow  |  [simcwustews_v2/scawding/update_known_fow/updateknownfow20m145k2020.scawa](scawding/update_known_fow/updateknownfow20m145k2020.scawa) | the j-job outputs the knownfow dataset w-which stowes the wewationships b-between  cwustewid and pwoducewusewid. òωó </n> knownfow dataset covews the top 20m f-fowwowed pwoducews. /(^•ω•^) we use this k-knownfow dataset (ow s-so-cawwed cwustews) to buiwd aww othew entity embeddings. -.- |
| i-intewestedin embeddings|  [simcwustews_v2/scawding/intewestedinfwomknownfow.scawa](scawding/intewestedinfwomknownfow.scawa) |  t-this code impwements t-the job fow c-computing usews' intewestedin embedding fwom t-the  knownfow dataset. òωó </n> w-we use this dataset f-fow consumew-based tweet wecommendations.|
| pwoducew e-embeddings  | [simcwustews_v2/scawding/embedding/pwoducewembeddingsfwomintewestedin.scawa](scawding/embedding/pwoducewembeddingsfwomintewestedin.scawa)  |  the code impwements t-the job fow c-computew pwoducew e-embeddings, /(^•ω•^) which wepwesents t-the content usew p-pwoduces. /(^•ω•^) </n> w-we use this dataset f-fow pwoducew-based tweet wecommendations.|
| s-semantic cowe e-entity embeddings  | [simcwustews_v2/scawding/embedding/entitytosimcwustewsembeddingsjob.scawa](scawding/embedding/entitytosimcwustewsembeddingsjob.scawa)   | the j-job computes t-the semantic cowe e-entity embeddings. 😳 i-it outputs d-datasets that stowes t-the  "semanticcowe entityid -> w-wist(cwustewid)" and "cwustewid -> w-wist(semanticcowe entityid))" w-wewationships.|
| t-topic embeddings | [simcwustews_v2/scawding/embedding/tfg/favtfgbasedtopicembeddings.scawa](scawding/embedding/tfg/favtfgbasedtopicembeddings.scawa)  | jobs t-to genewate fav-based topic-fowwow-gwaph (tfg) topic embeddings </n> a topic's f-fav-based tfg e-embedding is the s-sum of its fowwowews' fav-based intewestedin. :3 we use this embedding f-fow topic w-wewated wecommendations.|

**simcwustews gcp jobs**

w-we have a gcp p-pipewine whewe we buiwd ouw simcwustews ann index via bigquewy. (U ᵕ U❁) t-this awwows us t-to do fast itewations a-and buiwd n-nyew embeddings mowe efficientwy compawed to scawding. ʘwʘ

a-aww simcwustews w-wewated gcp jobs awe undew [swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation](scio/bq_genewation). o.O

| jobs   | c-code  | descwiption  |
|---|---|---|
| pushopenbased simcwustews a-ann index  |  [engagementeventbasedcwustewtotweetindexgenewationjob.scawa](scio/bq_genewation/simcwustews_index_genewation/engagementeventbasedcwustewtotweetindexgenewationjob.scawa) | the job b-buiwds a cwustewid -> t-toptweet index based on u-usew-open engagement h-histowy. </n> this sann souwce i-is used fow candidate genewation f-fow nyotifications. ʘwʘ |
| v-videoviewbased s-simcwustews i-index|  [engagementeventbasedcwustewtotweetindexgenewationjob.scawa](scio/bq_genewation/simcwustews_index_genewation/engagementeventbasedcwustewtotweetindexgenewationjob.scawa) |  the j-job buiwds a cwustewid -> t-toptweet i-index based on the usew's video v-view histowy. ^^ </n> this sann souwce is used fow v-video wecommendation o-on home.|

### s-simcwustews weaw-time stweaming tweets jobs

| jobs   | code  | descwiption  |
|---|---|---|
| t-tweet embedding job |  [simcwustews_v2/summingbiwd/stowm/tweetjob.scawa](summingbiwd/stowm/tweetjob.scawa) | g-genewate the t-tweet embedding and index of tweets fow the simcwustews |
| p-pewsistent tweet embedding j-job|  [simcwustews_v2/summingbiwd/stowm/pewsistenttweetjob.scawa](summingbiwd/stowm/pewsistenttweetjob.scawa) |  p-pewsistent t-the tweet embeddings f-fwom memcache i-into manhattan.|