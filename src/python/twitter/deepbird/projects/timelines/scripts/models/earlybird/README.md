# Earlybird Light Ranker

*Note: The light ranker is an older part of the stack being replaced. The current model was trained years ago and uses odd features. A new model is being developed, and eventually, the entire stack will be rebuilt.*

The Earlybird light ranker is a logistic regression model predicting user engagement likelihood with tweets. It's a simplified version of the heavy ranker, capable of handling more tweets.



The Earlybird light ranker is a logistic regression model predicting user engagement likelihood with tweets. It's a simplified version of the heavy ranker, capable of handling more tweets. There are two main light ranker models: one for in-network tweets (`recap_earlybird`) and another for out-of-network (UTEG) tweets (`rectweet_earlybird`). Both models are trained using the `train.py` script, and they mainly differ in the features used. The in-network model uses `src/python/twitter/deepbird/projects/timelines/configs/recap/feature_config.py`, while the out-of-network model uses `src/python/twitter/deepbird/projects/timelines/configs/rectweet_earlybird/feature_config.py`.

The `train.py` script serves as a series of hooks for Twitter's `twml` framework, included under `twml/`.

### Features

The light ranker features pipeline is as follows:
![earlybird_features.png](earlybird_features.png)

Components explained below:

- Index Ingester: An indexing pipeline responsible for handling tweets as they are generated. This component serves as the primary input for Earlybird. It creates Tweet Data, which includes basic information about the tweet (text, URLs, media entities, facets, and more) and Static Features, which are features that can be computed directly from a tweet (such as whether it has a URL, cards, or quotes). All information computed by the Index Ingester is stored in an index and flushed as each realtime index segment becomes full. When Earlybird restarts, this information is loaded back from the disk. It's important to note that some features might be computed in non-trivial ways, such as determining the value of "hasUrl". These features could be computed and combined from raw information within the tweet and data from other services.
- Signal Ingester: Responsible for Realtime Features—per-tweet features that can change after indexing. These include
 social engagements like retweet count, favorite count, and reply count, as well as future spam signals. They are
  collected and computed in a Heron topology by processing multiple event streams and can be expanded to support more features.
- User Table Features: A separate set of per-user features sourced from the User Table Updater, which processes a stream
 written by the user service. It stores sparse realtime user information, and these per-user features are propagated to
  the tweet being scored by looking up the tweet's author.
- Search Context Features: Information about the current searcher, such as their UI language, their own produced/consumed language,
 and the current time. These features are combined with Tweet Data to compute some of the features used in scoring.
The scoring function in Earlybird uses both static and realtime features. Examples of static features used are:

- Whether the tweet is a retweet
- Whether the tweet contains a link
- Whether the tweet has trend words at ingestion time
- Whether the tweet is a reply
- A score for the static text quality, computed based on factors like offensiveness, content entropy, "shout" score, length, and readability.
- tweepcred, see top-level README.md

Examples of realtime features used are:

- Number of tweet likes/replies/retweets
- pToxicity and pBlock scores provided by health models
