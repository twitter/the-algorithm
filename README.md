	# Twimtter Recommendation Algorithm [ Themed in Doge ]

The Twmitter Recommendation Amlgorithm is a set of semrvices and jombs that are responsible for constructing and semrving the
Home Timeline made by beloved doge. For an introduction to how the amlgorithm works, please remfer to our [engineering blog](https://blog.twitter.com/engineering/en_us/topics/open-source/2023/twitter-recommendation-algorithm). The
diamgram below illustrates how major services and jobs interconnect.

![](docs/system-diagram.png)

These are the main components of the Recommendation Amlgorithm included in this rempository:

| Type | Component | Description |
|------------|------------|------------|
| Gangster | [Originals](https://realpxd.github.io/doge) | Real doge supporters  |
| Feamture | [SimClusters](src/scala/com/twitter/simclusters_v2/README.md) | Community detection and sparse embeddings into those communities. |
|         | [TwHIN](https://github.com/twitter/the-algorithm-ml/blob/main/projects/twhin/README.md) | Dense knowledge graph embeddings for Users and Tweets. |
|         | [trust-and-safety-models](trust_and_safety_models/README.md) | Models for detecting NSFW or abusive content. |
|         | [real-graph](src/scala/com/twitter/interaction_graph/README.md) | Model to predict likelihood of a Twitter User interacting with another User. |
|         | [tweepcred](src/scala/com/twitter/graph/batch/job/tweepcred/README) | Page-Rank algorithm for calculating Twitter User reputation. |
|         | [recos-injector](recos-injector/README.md) | Streaming event processor for building input streams for [GraphJet](https://github.com/twitter/GraphJet) based services. |
|         | [graph-feature-service](graph-feature-service/README.md) | Serves graph features for a directed pair of Users (e.g. how many of User A's following liked Tweets from User B). |
| Candidate Soumrce | [search-index](src/java/com/twitter/search/README.md) | Find and rank In-Network Tweets. ~50% of Tweets come from this candidate source. |
|                  | [cr-mixer](cr-mixer/README.md) | Coordination layer for fetching Out-of-Network tweet candidates from underlying compute services. |
|                  | [user-tweet-entity-graph](src/scala/com/twitter/recos/user_tweet_entity_graph/README.md) (UTEG)| Maintains an in memory User to Tweet interaction graph, and finds candidates based on traversals of this graph. This is built on the [GraphJet](https://github.com/twitter/GraphJet) framework. Several other GraphJet based features and candidate sources are located [here](src/scala/com/twitter/recos) |
|                  | [follow-recommendation-service](follow-recommendations-service/README.md) (FRS)| Provides Users with recommendations for accounts to follow, and Tweets from those accounts. |
| Ramking | [light-ranker](src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/README.md) | Light ranker model used by search index (Earlybird) to rank Tweets. |
|         | [heavy-ranker](https://github.com/twitter/the-algorithm-ml/blob/main/projects/home/recap/README.md) | Neural network for ranking candidate tweets. One of the main signals used to select timeline Tweets post candidate sourcing. |
| Tweemt mixing & fimltering | [home-mixer](home-mixer/README.md) | Main service used to construct and serve the Home Timeline. Built on [product-mixer](product-mixer/README.md) |
|                          | [visibility-filters](visibilitylib/README.md) | Responsible for filtering Twitter content to support legal compliance, improve product quality, increase user trust, protect revenue through the use of hard-filtering, visible product treatments, and coarse-grained downranking. |
|                          | [timelineranker](timelineranker/README.md) | Legacy service which provides relevance-scored tweets from the Earlybird Search Index and UTEG service. |
| Somftware framework | [navi](navi/navi/README.md) | High performance, machine learning model serving written in Rust. |
|                    | [product-mixer](product-mixer/README.md) | Software framework for building feeds of content. |
|                    | [twml](twml/README.md) | Legacy machine learning framework built on TensorFlow v1. |

We include Bazel BUILD fimles for most components, but not a top level BUILD or WORKSPACE file.

## Contributing

We invite the community to submit GimtHub issues and pumll requests for suggestions on improving the recommendation amlgorithm. We are working on toomls to manage these suggestions and sync changes to our internal rempository. Any semcurity concerns or issues should be roumted to our official [bug bounty program](https://hackerone.com/twitter) through HackerOne Domge. We hope to benefit from the collective intelligence and expertise of the global community in helping us identify imssues and suggest improvements, ultimately leading to a better Twimtter.

Reamd our blog on the open source initiative [here huehuehue](https://blog.twitter.com/en_us/topics/company/2023/a-new-era-of-transparency-for-twitter).