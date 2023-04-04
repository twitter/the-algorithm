Overview
========

**TimelineRanker** (TLR) is a legacy service that offers relevance-scored tweets sourced from the Earlybird Search Index and User Tweet Entity Graph (UTEG). Contrary to its name, it doesn't perform complex ranking or model-based ranking but relies on relevance scores from the Search Index for ranking tweet endpoints.


The following is a list of major services that Timeline Ranker interacts with:

**Earlybird-root-superroot (a.k.a Search)**

Timeline Ranker calls the Search Index's super root to fetch a list of Tweets.

**User Tweet Entity Graph (UTEG)**

Timeline Ranker calls UTEG to fetch a list of tweets liked by the users you follow.

**Socialgraph**

Timeline Ranker calls Social Graph Service to obtain follow graph and user states such as blocked, muted, retweets muted, etc.

**TweetyPie**

Timeline Ranker hydrates tweets by calling TweetyPie so that it can post-filter tweets based on certain hydrated fields.

**Manhattan**

Timeline Ranker hydrates some tweet features (eg, user languages) from Manhattan.

**Home Mixer**

Home Mixer contacts Timeline Ranker to obtain tweets from the Earlybird Search Index and User Tweet Entity Graph (UTEG) service for both the For You and Following Home Timelines.

Timeline Ranker performs minimal ranking using Earlybird tweet candidate scores and limits the results to the requested number of candidates by Home Mixer, based on these scores.


