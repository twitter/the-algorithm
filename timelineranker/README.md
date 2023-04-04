Overview
========

**TimelineRanker** (TLR) is a legacy service which provides relevance-scored Tweets from the Earlybird Search Index and User Tweet Entity Graph (UTEG) service. Despite its name, it no longer does any kind of heavy ranking/model based ranking itself - just uses relevance scores from the Search Index for ranked Tweet endpoints.


The following is a list of major services that Timeline Ranker interacts with:

**Earlybird-root-superroot (a.k.a Search)**

Timeline Ranker calls the Search Index's super root to fetch a list of Tweets.

**User Tweet Entity Graph (UTEG)**

Timeline Ranker calls UTEG to fetch a list of Tweets liked by the users you follow.

**Socialgraph**

Timeline Ranker calls Social Graph Service to obtain follow graph and user states such as blocked, muted, Retweets muted, etc.

**TweetyPie**

Timeline Ranker hydrates Tweets by calling TweetyPie so that it can post-filter Tweets based on certain hydrated fields.

**Manhattan**

Timeline Ranker hydrates some Tweet features (eg, user languages) from Manhattan.

**Home Mixer**

Home Mixer calls Timeline Ranker to fetch Tweets from the Earlybird Search Index and User Tweet Entity Graph (UTEG) service to power both the For You and Following Home Timelines.

Timeline Ranker does light ranking based on Earlybird Tweet candidate scores and truncates to the number of candidates requested by Home Mixer based on these scores



