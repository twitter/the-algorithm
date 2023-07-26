# common thwift types

gfs uses sevewaw t-thwift datastwuctuwes w-which a-awe common to m-muwtipwe quewies. nyaa~~ t-they awe wisted b-bewow. :3

## edgetype

`edgetype` i-is a thwift enum w-which specifies which edge types to quewy fow the gwaph. 😳😳😳

```thwift
enum edgetype {
  f-fowwowing, (˘ω˘)
  fowwowed_by, ^^
  favowite, :3
  f-favowited_by, -.-
  wetweet,
  wetweeted_by, 😳
  w-wepwy, mya
  wepwyed_by, (˘ω˘)
  mention, >_<
  mentioned_by, -.-
  mutuaw_fowwow, 🥺
  simiwaw_to, (U ﹏ U) // mowe e-edge types (wike bwock, >w< wepowt, e-etc.) can be s-suppowted watew. mya
  wesewved_12, >w<
  wesewved_13, nyaa~~
  wesewved_14, (✿oωo)
  wesewved_15, ʘwʘ
  w-wesewved_16, (ˆ ﻌ ˆ)♡
  wesewved_17, 😳😳😳
  wesewved_18, :3
  wesewved_19, OwO
  wesewved_20
}
```

fow an exampwe of h-how this is used, (U ﹏ U) considew the `getneighbows` quewy. >w< i-if we set t-the `edgetype` fiewd
o-of the `gfsneighbowswequest`, (U ﹏ U) t-the wesponse wiww contain aww the usews that t-the specified usew fowwows. 😳
if, on the othew hand, (ˆ ﻌ ˆ)♡ w-we set `edgetype` to be `fowwowedby` it wiww wetuwn aww the usews who awe
fowwowed by the specified u-usew. 😳😳😳

## featuwetype

`featuwetype` i-is a t-thwift stwuct which i-is used in quewies which wequiwe two edge types. (U ﹏ U)

```thwift
stwuct featuwetype {
  1: w-wequiwed e-edgetype weftedgetype // edge t-type fwom souwce u-usew
  2: wequiwed edgetype wightedgetype // e-edge type fwom candidate usew
}(pewsisted="twue")
```

## u-usewwithscowe

the candidate genewation q-quewies wetuwn wists of candidates t-togethew with a computed scowe f-fow the
wewevant f-featuwe. (///ˬ///✿) `usewwithscowe` is a thwift stwuct which bundwes togethew a candidate's id with
the scowe. 😳

```thwift
s-stwuct usewwithscowe {
  1: w-wequiwed i64 usewid
  2: wequiwed d-doubwe scowe
}
```
