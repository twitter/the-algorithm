# UserUserGraph (UUG)

## What is it
User User Graph (UUG) is a Finalge thrift service built on the GraphJet framework. In maintains a graph of user-user relationships and serves user recommendations based on traversals of this graph.

## How is it used on Twitter
UUG recommends users to follow based on who your follow graph have recently followed.
The core idea behind UUG is collaborative filtering. UUG takes a user's weighted follow graph (i.e a list of weighted userIds) as input, 
performs efficient traversal & aggregation, and returns the top weighted users basd on # of users that engaged the users, as well as 
the engaging users' weights.

UUG is a stateful service and relies on a Kafka stream to ingest & persist states. It maintains an in-memory user engagements over the past 
week. Older events are dropped and GC'ed. 

For full details on storage & processing, please check out our open-sourced project GraphJet, a general-purpose high performance in-memory storage engine.
- https://github.com/twitter/GraphJet
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
