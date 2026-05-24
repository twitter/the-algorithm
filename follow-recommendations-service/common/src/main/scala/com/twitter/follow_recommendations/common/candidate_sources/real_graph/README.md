# RealGraph Candidate Source
Provides out-of-network RealGraph candidates for a given user. RealGraph is a user-user graph dataset that aims to measure the strength of the relationship between two users.

RealGraph comprises two components: a real-time pipeline that tracks various counts and relationships between user-user edges (such as the number of favorites, replies, retweets, clicks, whether followed, muted, or blocked), and an offline pipeline of a larger set of such user-user edge counts and relationships. Currently, the top k in-network scores have been exported for use by various teams.

The RealGraph dataset is used to predict user interactions at Twitter, and is based on the paper "[Realgraph: User interaction prediction at Twitter](http://www.ueo-workshop.com/wp-content/uploads/2014/04/sig-alternate.pdf)" by the UEO workshop at KDD'14.
