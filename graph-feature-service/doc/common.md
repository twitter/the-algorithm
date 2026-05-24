# Common thrift types

GFS uses several thrift datastructures which are common to multiple queries. They are listed below.

## EdgeType

`EdgeType` is a thrift enum which specifies which edge types to query for the graph.

```thrift
enum EdgeType {
  FOLLOWING,
  FOLLOWED_BY,
  FAVORITE,
  FAVORITED_BY,
  RETWEET,
  RETWEETED_BY,
  REPLY,
  REPLYED_BY,
  MENTION,
  MENTIONED_BY,
  MUTUAL_FOLLOW,
  SIMILAR_TO, // more edge types (like block, report, etc.) can be supported later.
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

For an example of how this is used, consider the `GetNeighbors` query. If we set the `edgeType` field
of the `GfsNeighborsRequest`, the response will contain all the users that the specified user follows.
If, on the other hand, we set `edgeType` to be `FollowedBy` it will return all the users who are
followed by the specified user.

## FeatureType

`FeatureType` is a thrift struct which is used in queries which require two edge types.

```thrift
struct FeatureType {
  1: required EdgeType leftEdgeType // edge type from source user
  2: required EdgeType rightEdgeType // edge type from candidate user
}(persisted="true")
```

## UserWithScore

The candidate generation queries return lists of candidates together with a computed score for the
relevant feature. `UserWithScore` is a thrift struct which bundles together a candidate's ID with
the score.

```thrift
struct UserWithScore {
  1: required i64 userId
  2: required double score
}
```
