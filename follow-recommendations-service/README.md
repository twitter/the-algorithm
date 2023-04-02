# Follow Recommendations Service

## Introduction

The Follow Recommendations Service (FRS) is a recommendation engine designed to provide users with personalized suggestions for accounts to follow. It currently supports Who-To-Follow (WTF) module recommendations across a variety of Twitter product interfaces. Additionally, FRS delivers FutureGraph tweet recommendations, which consist of tweets from accounts that users may be interested in following in the future by suggesting tweet authors.

## Design

The system accommodates diverse use cases such as Post New-User-Experience (NUX), advertisements, FutureGraph tweets, and more. Each use case features a unique display location identifier. To view all display locations, refer to the following path: `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models/DisplayLocation.scala`.

Recommendation steps are customized according to each display location. Common and high-level steps are encapsulated within the "RecommendationFlow," which includes operations like candidate generation, ranker selection, filtering, transformation, and truncation. To explore all flows, refer to this path: `follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/flows`.

For each product (corresponding to a display location), one or multiple flows can be selected to generate candidates based on code and configurations. To view all products, refer to the following path: `follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/products/home_timeline_tweet_recs`.

The FRS architecture is depicted below:

![FRS_architecture.png](FRS_architecture.png)

### Candidate Generation

During this step, FRS utilizes various user signals and algorithms to identify candidates from all Twitter accounts. The candidate source folder is located at `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/candidate_sources/`, with a README file provided within each candidate source folder.

### Filtering

In this phase, FRS applies different filtering logic after generating account candidates to improve quality and health. Filtering may occur before and/or after the ranking step, with heavier filtering logic (e.g., higher latency) typically applied after the ranking step. The filters' folder is located at `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/predicates`.

### Ranking

During this step, FRS employs both Machine Learning (ML) and heuristic rule-based candidate ranking. For the ML ranker, ML features are fetched beforehand (i.e., feature hydration),
and a DataRecord (the Twitter-standard Machine Learning data format used to represent feature data, labels, and predictions when training or serving) is constructed for each <user, candidate> pair. 
These pairs are then sent to a separate ML prediction service, which houses the ML model trained offline.
The ML prediction service returns a prediction score, representing the probability that a user will follow and engage with the candidate.
This score is a weighted sum of p(follow|recommendation) and p(positive engagement|follow), and FRS uses this score to rank the candidates.

The rankers' folder is located at `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/rankers`.

### Transformation

In this phase, the sequence of candidates undergoes necessary transformations, such as deduplication, attaching social proof (i.e., "followed by XX user"), adding tracking tokens, and more.
The transformers' folder can be found at `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/transforms`.

### Truncation
During this final step, FRS trims the candidate pool to a specified size, ensuring that only the most relevant and engaging candidates are presented to users, while maintaining an optimal user experience. The truncation process is implemented using the `Predicate.batchFilterTake()` function, which applies heavy filters to determine the eligibility of the candidates, and then selects the desired number of candidates from the filtered pool. The results are returned to the user as the final set of recommendations.

The `batchFilterTake()` function takes the following parameters:
- `candidates`: The pool of candidate accounts to be filtered and truncated.
- `predicate`: A function that takes a candidate account and determines whether it should be included in the final result set.
- `batchSize`: The size of the batches used to filter the candidates.
- `desiredCount`: The number of candidates desired in the final result set.
- `statsReceiver`: The `StatsReceiver` object used to report statistics for the truncation process.

By implementing this comprehensive truncation process, FRS ensures that the recommendations presented to users are of the highest quality, promoting meaningful connections within the platform.
