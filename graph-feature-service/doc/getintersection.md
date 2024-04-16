# Get ntersect on

## Request and response syntax

A `Get ntersect on` call takes as  nput a `Gfs ntersect onRequest` thr ft struct. 

```thr ft
struct Gfs ntersect onRequest {
  1: requ red  64 user d
  2: requ red l st< 64> cand dateUser ds
  3: requ red l st<FeatureType> featureTypes
}
```

T  response  s returned  n a `Gfs ntersect onResponse` thr ft struct.

```thr ft
struct Gfs ntersect onResponse {
  1: requ red  64 user d
  2: requ red l st<Gfs ntersect onResult> results
}

struct Gfs ntersect onResult {
  1: requ red  64 cand dateUser d
  2: requ red l st< ntersect onValue>  ntersect onValues
}

struct  ntersect onValue {
  1: requ red FeatureType featureType
  2: opt onal  32 count
  3: opt onal l st< 64>  ntersect on ds
  4: opt onal  32 leftNodeDegree
  5: opt onal  32 r ghtNodeDegree
}(pers sted="true")
```

## Behav or

T  `Gfs ntersect onResponse` conta ns  n  s `results` f eld a `Gfs ntersect onResult` for every cand date  n `cand date ds` wh ch conta ns an  ` ntersect onValue` for every `FeatureType`  n t  request's `featureTypes` f eld. 

T  ` ntersect onValue` conta ns t  s ze of t   ntersect on bet en t  `leftEdgeType` edges from `user d` and t  `r ghtEdgeType` edges from `cand date d`  n t  `count` f eld, as  ll as t  r respect ve degrees  n t  graphs  n `leftNodeDegree` and `r ghtNodeDegree` respect vely.

**Note:** t  ` ntersect on ds` f eld currently only conta ns `N l`.
