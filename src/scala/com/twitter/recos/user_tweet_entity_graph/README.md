# UserTweetEntityGraph (UTEG)

## What is it
User Tweet Entity Graph (UTEG) is a Finalge thrift service built on the GraphJet framework. It maintains a graph of user-tweet relationships and serves user recommendations based on traversals in this graph.

## How is it used on Twitter
UTEG generates the "XXX Liked" out-of-network tweets seen on Twitter's Home Timeline.
The core idea behind UTEG is collaborative filtering. UTEG takes a user's weighted follow graph (i.e a list of weighted userIds) as input,
performs efficient traversal & aggregation, and returns the top-weighted tweets engaged based on # of users that engaged the tweet, as well as
the engaged users' weights.

UTEG is a stateful service and relies on a Kafka stream to ingest & persist states. It maintains in-memory user engagements over the past
24-48 hours. Older events are dropped and GC'ed.

For full details on storage & processing, please check out our open-sourced project GraphJet, a general-purpose high-performance in-memory storage engine.
- https://github.com/twitter/GraphJet
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
