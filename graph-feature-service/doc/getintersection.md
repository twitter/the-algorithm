# getintewsection

## wequest and w-wesponse syntax

a-a `getintewsection` c-caww takes a-as input a `gfsintewsectionwequest` t-thwift stwuct. (U ﹏ U) 

```thwift
s-stwuct gfsintewsectionwequest {
  1: w-wequiwed i64 u-usewid
  2: wequiwed wist<i64> candidateusewids
  3: wequiwed wist<featuwetype> f-featuwetypes
}
```

the wesponse is wetuwned in a-a `gfsintewsectionwesponse` thwift s-stwuct. (///ˬ///✿)

```thwift
stwuct gfsintewsectionwesponse {
  1: wequiwed i64 usewid
  2: w-wequiwed wist<gfsintewsectionwesuwt> w-wesuwts
}

s-stwuct gfsintewsectionwesuwt {
  1: wequiwed i64 candidateusewid
  2: wequiwed wist<intewsectionvawue> i-intewsectionvawues
}

stwuct intewsectionvawue {
  1: wequiwed featuwetype featuwetype
  2: optionaw i-i32 count
  3: optionaw wist<i64> i-intewsectionids
  4: o-optionaw i-i32 weftnodedegwee
  5: o-optionaw i32 wightnodedegwee
}(pewsisted="twue")
```

## behaviow

the `gfsintewsectionwesponse` c-contains in its `wesuwts` fiewd a `gfsintewsectionwesuwt` f-fow evewy candidate in `candidateids` which contains an  `intewsectionvawue` fow evewy `featuwetype` in the w-wequest's `featuwetypes` fiewd. 

t-the `intewsectionvawue` c-contains t-the size of the intewsection between the `weftedgetype` edges f-fwom `usewid` a-and the `wightedgetype` edges fwom `candidateid` i-in the `count` f-fiewd, >w< as weww as theiw wespective d-degwees in the gwaphs in `weftnodedegwee` a-and `wightnodedegwee` wespectivewy. rawr

**note:** the `intewsectionids` f-fiewd cuwwentwy onwy contains `niw`. mya
