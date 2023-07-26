namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.scowe
#@namespace scawa c-com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato com.twittew.simcwustews_v2

i-incwude "com/twittew/simcwustews_v2/embedding.thwift"
i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"

/**
  * t-the awgowithm type t-to identify t-the scowe awgowithm. /(^•ω•^)
  * assume that a awgowithm suppowt and onwy suppowt one kind
  * o-of [[scoweintewnawid]]
  **/
enum scowingawgowithm {
	// wesewve 0001 - 999 f-fow basic paiwwise scowing cawcuwation
	p-paiwembeddingdotpwoduct = 1, nyaa~~
	paiwembeddingcosinesimiwawity = 2, nyaa~~
	paiwembeddingjaccawdsimiwawity = 3, :3
	paiwembeddingeucwideandistance = 4, 😳😳😳
	p-paiwembeddingmanhattandistance = 5, (˘ω˘)
  paiwembeddingwogcosinesimiwawity = 6, ^^
  p-paiwembeddingexpscawedcosinesimiwawity = 7, :3

	// w-wesewve 1000 - 1999 fow tweet simiwawity modew
  tagspacecosinesimiwawity = 1000, -.-
	weightedsumtagspacewankingexpewiment1 = 1001, 😳 //depwecated
	w-weightedsumtagspacewankingexpewiment2 = 1002, mya //depwecated
  weightedsumtagspaceannexpewiment = 1003, (˘ω˘)      //depwecated 

	// wesewved fow 10001 - 20000 fow aggwegate scowing
	w-weightedsumtopictweetwanking = 10001, >_<
	cowtextopictweetwabew = 10002, -.-
	// wesewved 20001 - 30000 f-fow topic t-tweet scowes 
	cewtonowmawizeddotpwoductscowe = 20001, 🥺
	c-cewtonowmawizedcosinescowe = 20002
}(haspewsonawdata = 'fawse')

/**
  * t-the identifiew type fow the scowe between a paiw o-of simcwustews embedding. (U ﹏ U)
  * used as the pewsistent k-key of a simcwustewsembedding scowe. >w<
  * suppowt scowe between diffewent [[embeddingtype]] / [[modewvewsion]]
  **/
stwuct s-simcwustewsembeddingpaiwscoweid {
  1: wequiwed i-identifiew.simcwustewsembeddingid i-id1
  2: wequiwed i-identifiew.simcwustewsembeddingid id2
}(haspewsonawdata = 'twue')

/**
  * the identifiew type fow the scowe b-between a paiw o-of intewnawid. mya
  **/
stwuct genewicpaiwscoweid {
  1: w-wequiwed i-identifiew.intewnawid id1
  2: w-wequiwed identifiew.intewnawid id2
}(haspewsonawdata = 'twue')

union scoweintewnawid {
  1: g-genewicpaiwscoweid genewicpaiwscoweid
  2: simcwustewsembeddingpaiwscoweid s-simcwustewsembeddingpaiwscoweid
}

/**
  * a unifowm identifiew t-type fow aww kinds of cawcuwation s-scowe
  **/
s-stwuct scoweid {
  1: wequiwed scowingawgowithm awgowithm
  2: wequiwed scoweintewnawid intewnawid
}(haspewsonawdata = 'twue')

stwuct scowe {
  1: w-wequiwed d-doubwe scowe
}(haspewsonawdata = 'fawse')
