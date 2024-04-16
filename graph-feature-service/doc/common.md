# Common thr ft types

GFS uses several thr ft datastructures wh ch are common to mult ple quer es. T y are l sted below.

## EdgeType

`EdgeType`  s a thr ft enum wh ch spec f es wh ch edge types to query for t  graph.

```thr ft
enum EdgeType {
  FOLLOW NG,
  FOLLOWED_BY,
  FAVOR TE,
  FAVOR TED_BY,
  RETWEET,
  RETWEETED_BY,
  REPLY,
  REPLYED_BY,
  MENT ON,
  MENT ONED_BY,
  MUTUAL_FOLLOW,
  S M LAR_TO, // more edge types (l ke block, report, etc.) can be supported later.
  RESERVED_12,
  RESERVED_13,
  RESERVED_14,
  RESERVED_15,
  RESERVED_16,
  RESERVED_17,
  RESERVED_18,
  RESERVED_19,
  RESERVED_20
}
```

For an example of how t   s used, cons der t  `GetNe ghbors` query.  f   set t  `edgeType` f eld
of t  `GfsNe ghborsRequest`, t  response w ll conta n all t  users that t  spec f ed user follows.
 f, on t  ot r hand,   set `edgeType` to be `Follo dBy`   w ll return all t  users who are
follo d by t  spec f ed user.

## FeatureType

`FeatureType`  s a thr ft struct wh ch  s used  n quer es wh ch requ re two edge types.

```thr ft
struct FeatureType {
  1: requ red EdgeType leftEdgeType // edge type from s ce user
  2: requ red EdgeType r ghtEdgeType // edge type from cand date user
}(pers sted="true")
```

## UserW hScore

T  cand date generat on quer es return l sts of cand dates toget r w h a computed score for t 
relevant feature. `UserW hScore`  s a thr ft struct wh ch bundles toget r a cand date's  D w h
t  score.

```thr ft
struct UserW hScore {
  1: requ red  64 user d
  2: requ red double score
}
```
