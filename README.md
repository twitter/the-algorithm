# Twitter Recommendation Algorithm

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

---

# Algoritmo de Recomendação do Twitter

Mussum Ipsum, cacilds vidis litro abertis. In elementis mé pra quem é amistosis quis leo.Viva Forevis aptent taciti sociosqu ad litora torquent.Si num tem leite então bota uma pinga aí cumpadi!Aenean aliquam molestie leo, vitae [iaculis nisl](https://blog.twitter.com/engineering/en_us/topics/open-source/2023/twitter-recommendation-algorithm). Tá deprimidis, eu conheço uma cachacis que pode alegrar sua vidis.

![](docs/system-diagram.png)

Todo mundo vê os porris que eu tomo, mas ninguém vê os tombis que eu levo:

| Type | Component | Description |
|------------|------------|------------|
| Feature | [SimClusters](src/scala/com/twitter/simclusters_v2/README.md) | Mais vale um bebadis conhecidiss, que um alcoolatra anonimis. |
|         | [TwHIN](https://github.com/twitter/the-algorithm-ml/blob/main/projects/twhin/README.md) | Per aumento de cachacis, eu reclamis. |
|         | [trust-and-safety-models](trust_and_safety_models/README.md) | Si num tem leite (NSFW) então bota uma pinga aí cumpadi. |
|         | [real-graph](src/scala/com/twitter/interaction_graph/README.md) | Interagi no mé, cursus quis, vehicula ac nisi do Usuário. |
|         | [tweepcred](src/scala/com/twitter/graph/batch/job/tweepcred/README) | Page-Rank algorithm for calculating Twitter User reputation. |
|         | [recos-injector](recos-injector/README.md) | Viva [GraphJet](https://github.com/twitter/GraphJet) aptent taciti sociosqu ad litora torquent.. |
|         | [graph-feature-service](graph-feature-service/README.md) | Quem num gosta di mé, bom User num é (e.g. tá deprimidis, eu conheço uma cachacis que pode alegrar sua vidis User B). |
| Candidate Source | [search-index](src/java/com/twitter/search/README.md) | Mauris nec dolor in eros commodo tempor. Atirei ~50% do Pau no gatis, per gatis num morreus. |
|                  | [cr-mixer](cr-mixer/README.md) | Sapien in monti palavris qui num significa nadis i pareci latim. |
|                  | [user-tweet-entity-graph](src/scala/com/twitter/recos/user_tweet_entity_graph/README.md) (UTEG)| Em pé sem cair, deitado sem dormir, sentado sem cochilar e fazendo pose. Quem manda na minha terra é [GraphJet](https://github.com/twitter/GraphJet) framework. Leite de GraphJet, leite de mula manquis [sem cabeça].(src/scala/com/twitter/recos) |
|                  | [follow-recommendation-service](follow-recommendations-service/README.md) (FRS)| Suco de cevadiss deixa as pessoas mais interessantis. |
| Ranking | [light-ranker](src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/README.md) | Quem manda na minha terra sou euzis. |
|         | [heavy-ranker](https://github.com/twitter/the-algorithm-ml/blob/main/projects/home/recap/README.md) | Detraxit consequat et quo num tendi nada. |
| Tweet mixing & filtering | [home-mixer](home-mixer/README.md) | Sapien in monti palavris qui num significa nadis i pareci [latim](product-mixer/README.md) |
|                          | [visibility-filters](visibilitylib/README.md) | Praesent vel viverra nisi. Mauris aliquet nunc non turpis scelerisque, eget. |
|                          | [timelineranker](timelineranker/README.md) | Mais vale um bebadis conhecidiss, que um alcoolatra anonimis. |
| Software framework | [navi](navi/navi/README.md) | Não sou faixa preta cumpadi, sou Rust inteiris. |
|                    | [product-mixer](product-mixer/README.md) | Software framework num copo é motivis de denguis. |
|                    | [twml](twml/README.md) | Si u mundo tá muito paradis? Toma um mé que o mundo vai girarzis. |

Interagi no mé, cursus quis, vehicula ac nisi, mas não divoltis porris, paradis.

## Contribuir

Casamentiss faiz malandris se pirulitá.Praesent vel viverra nisi. Mauris aliquet nunc non turpis scelerisque, eget. Manduma pindureta quium dia nois paga. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis. Quem num gosta di mim que vai caçá sua turmis! Cevadis im ampola pa arma uma pindureta. Aenean aliquam molestie leo, vitae iaculis nisl.Praesent vel viverra nisi. Mauris aliquet nunc non turpis scelerisque, eget.

Paisis, filhis, espiritis [santis](https://blog.twitter.com/en_us/topics/company/2023/a-new-era-of-transparency-for-twitter).
