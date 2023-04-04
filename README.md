# Update Twitter Algorithm Recommendations based on Google Ranking and Verify Developers and Creators with a Free Special Blue Tick that Lets Creators Earn Revenue through Ads in between Threads and Tweets
Introduction
This document outlines the process for updating Twitter's algorithm recommendations based on Google's ranking algorithm, introducing a new feature for developers and creators to get verified with a special blue tick, and allowing verified creators to earn revenue through ads in between threads and tweets.

Process
Updating Twitter Algorithm Recommendations
Monitor Google's algorithm updates: The Twitter SEO team should keep a close eye on Google's algorithm updates to identify any changes that could impact Twitter's search rankings.

Conduct an audit of Twitter's algorithm: The Twitter SEO team should conduct an audit of Twitter's algorithm to identify any areas where it could be improved based on the latest SEO best practices.

Analyze Google's ranking factors: The team should analyze Google's ranking factors to identify any that could be relevant to Twitter's algorithm.

Update Twitter's algorithm: Based on the audit and analysis, the Twitter SEO team should update Twitter's algorithm to incorporate any relevant Google ranking factors.

Test and evaluate: The team should test the updated algorithm and evaluate its impact on Twitter's search rankings.

Iterate: Based on the evaluation, the team should iterate on the algorithm, making further updates as needed.

Communicate changes: Finally, the team should communicate any changes to the algorithm to relevant stakeholders, such as product teams, developers, and users.

Verifying Developers and Creators
Introducing a new feature: Twitter is introducing a new feature for developers and creators to get verified with a special blue tick.

Eligibility criteria: Developers and creators who have a significant following on Twitter, and who are at risk of impersonation, can apply for the special blue tick.

Verification process: The verification process involves providing personal information and verifying ownership of a website or domain associated with the developer or creator's work.

Review and approval: Once the application is submitted, Twitter will review and approve the request if it meets the eligibility criteria and verification process requirements.

Implementation: Once approved, the special blue tick will be added to the developer or creator's Twitter profile.

Communication: Twitter will communicate the new feature and eligibility criteria to relevant stakeholders, such as developers and creators.

Allowing Creators to Earn Revenue through Ads
Introducing a new feature: Twitter is allowing verified creators to earn revenue through ads in between threads and tweets.

Eligibility criteria: Creators who have the special blue tick and meet certain eligibility criteria, such as having a certain number of followers and engagement on their content, can apply to participate in the program.

Ad placement and revenue sharing: The ads will be placed in between threads and tweets, and the revenue will be shared between Twitter and the creator.

Review and approval: Once the application is submitted, Twitter will review and approve the request if it meets the eligibility criteria and program requirements.

Implementation: Once approved, the creator will be able to participate in the program and earn revenue through ads.

Communication: Twitter will communicate the new feature and eligibility criteria to relevant stakeholders, such as verified creators.

Conclusion
By updating Twitter's algorithm based on Google's ranking factors, introducing a new feature for developers and creators to get verified with a special blue tick, and allowing verified creators to earn revenue through ads in between threads and tweets, Twitter can improve the user experience for its users, ensure that Twitter's search rankings are optimized for the latest best practices in SEO, reduce the risk of impersonation for developers and creators, and provide creators with new revenue streams.

Twitter Recommendation Algorithm

The Twitter Recommendation Algorithm is a set of services and jobs that are responsible for constructing and serving the
Home Timeline. For an introduction to how the algorithm works, please refer to our [engineering blog](https://blog.twitter.com/engineering/en_us/topics/open-source/2023/twitter-recommendation-algorithm). The
diagram below illustrates how major services and jobs interconnect.

![](docs/system-diagram.png)

These are the main components of the Recommendation Algorithm included in this repository:

| Type | Component | Description |
|------------|------------|------------|
| Feature | [SimClusters](src/scala/com/twitter/simclusters_v2/README.md) | Community detection and sparse embeddings into those communities. |
|         | [TwHIN](https://github.com/twitter/the-algorithm-ml/blob/main/projects/twhin/README.md) | Dense knowledge graph embeddings for Users and Tweets. |
|         | [trust-and-safety-models](trust_and_safety_models/README.md) | Models for detecting NSFW or abusive content. |
|         | [real-graph](src/scala/com/twitter/interaction_graph/README.md) | Model to predict likelihood of a Twitter User interacting with another User. |
|         | [tweepcred](src/scala/com/twitter/graph/batch/job/tweepcred/README) | Page-Rank algorithm for calculating Twitter User reputation. |
|         | [recos-injector](recos-injector/README.md) | Streaming event processor for building input streams for [GraphJet](https://github.com/twitter/GraphJet) based services. |
|         | [graph-feature-service](graph-feature-service/README.md) | Serves graph features for a directed pair of Users (e.g. how many of User A's following liked Tweets from User B). |
| Candidate Source | [search-index](src/java/com/twitter/search/README.md) | Find and rank In-Network Tweets. ~50% of Tweets come from this candidate source. |
|                  | [cr-mixer](cr-mixer/README.md) | Coordination layer for fetching Out-of-Network tweet candidates from underlying compute services. |
|                  | [user-tweet-entity-graph](src/scala/com/twitter/recos/user_tweet_entity_graph/README.md) (UTEG)| Maintains an in memory User to Tweet interaction graph, and finds candidates based on traversals of this graph. This is built on the [GraphJet](https://github.com/twitter/GraphJet) framework. Several other GraphJet based features and candidate sources are located [here](src/scala/com/twitter/recos) |
|                  | [follow-recommendation-service](follow-recommendations-service/README.md) (FRS)| Provides Users with recommendations for accounts to follow, and Tweets from those accounts. |
| Ranking | [light-ranker](src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/README.md) | Light ranker model used by search index (Earlybird) to rank Tweets. |
|         | [heavy-ranker](https://github.com/twitter/the-algorithm-ml/blob/main/projects/home/recap/README.md) | Neural network for ranking candidate tweets. One of the main signals used to select timeline Tweets post candidate sourcing. |
| Tweet mixing & filtering | [home-mixer](home-mixer/README.md) | Main service used to construct and serve the Home Timeline. Built on [product-mixer](product-mixer/README.md) |
|                          | [visibility-filters](visibilitylib/README.md) | Responsible for filtering Twitter content to support legal compliance, improve product quality, increase user trust, protect revenue through the use of hard-filtering, visible product treatments, and coarse-grained downranking. |
|                          | [timelineranker](timelineranker/README.md) | Legacy service which provides relevance-scored tweets from the Earlybird Search Index and UTEG service. |
| Software framework | [navi](navi/navi/README.md) | High performance, machine learning model serving written in Rust. |
|                    | [product-mixer](product-mixer/README.md) | Software framework for building feeds of content. |
|                    | [twml](twml/README.md) | Legacy machine learning framework built on TensorFlow v1. |

We include Bazel BUILD files for most components, but not a top level BUILD or WORKSPACE file.

## Contributing

We invite the community to submit GitHub issues and pull requests for suggestions on improving the recommendation algorithm. We are working on tools to manage these suggestions and sync changes to our internal repository. Any security concerns or issues should be routed to our official [bug bounty program](https://hackerone.com/twitter) through HackerOne. We hope to benefit from the collective intelligence and expertise of the global community in helping us identify issues and suggest improvements, ultimately leading to a better Twitter.

Read our blog on the open source initiative [here](https://blog.twitter.com/en_us/topics/company/2023/a-new-era-of-transparency-for-twitter).
