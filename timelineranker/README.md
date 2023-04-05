# TimelineRanker

**TimelineRanker** (TLR) is a legacy service that provides relevance-scored tweets from the Earlybird Search Index and User Tweet Entity Graph (UTEG) service. Despite its name, it no longer performs heavy ranking or model-based ranking itself; it only uses relevance scores from the Search Index for ranked tweet endpoints.

The following is a list of major services that Timeline Ranker interacts with:

- **Earlybird-root-superroot (a.k.a Search):** Timeline Ranker calls the Search Index's super root to fetch a list of Tweets.
- **User Tweet Entity Graph (UTEG):** Timeline Ranker calls UTEG to fetch a list of tweets liked by the users you follow.
- **Socialgraph:** Timeline Ranker calls Social Graph Service to obtain the follow graph and user states such as blocked, muted, retweets muted, etc.
- **TweetyPie:** Timeline Ranker hydrates tweets by calling TweetyPie to post-filter tweets based on certain hydrated fields.
- **Manhattan:** Timeline Ranker hydrates some tweet features (e.g., user languages) from Manhattan.

**Home Mixer** calls Timeline Ranker to fetch tweets from the Earlybird Search Index and User Tweet Entity Graph (UTEG) service to power both the For You and Following Home Timelines. Timeline Ranker performs light ranking based on Earlybird tweet candidate scores and truncates to the number of candidates requested by Home Mixer based on these scores.
