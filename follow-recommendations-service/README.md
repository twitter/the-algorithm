# Follow Recommendations Service

## Introduction to the Follow Recommendations Service (FRS)
The Follow Recommendations Service (FRS) is a robust recommendation engine designed to provide users with personalized suggestions for accounts to follow. At present, FRS supports Who-To-Follow (WTF) module recommendations across a variety of Twitter product interfaces. Additionally, by suggesting tweet authors, FRS also delivers FutureGraph tweet recommendations, which consist of tweets from accounts that users may be interested in following in the future.

## Design
The system is tailored to accommodate diverse use cases, such as Post New-User-Experience (NUX), advertisements, FutureGraph tweets, and more. Each use case features a unique display location identifier. To view all display locations, refer to the following path: `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models/DisplayLocation.scala`.

Recommendation steps are customized according to each display location. Common and high-level steps are encapsulated within the "RecommendationFlow," which includes operations like candidate generation, ranker selection, filtering, transformation, and beyond. To explore all flows, refer to this path: `follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/flows`.

For each product (corresponding to a display location), one or multiple flows can be selected to generate candidates based on code and configurations. To view all products, refer to the following path: `follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/products/home_timeline_tweet_recs`.

The FRS overview diagram is depicted below:

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

### Transform
In this phase, the sequence of candidates undergoes necessary transformations, such as deduplication, attaching social proof (i.e., "followed by XX user"), adding tracking tokens, and more.
The transformers' folder can be found at `follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/transforms`.

### Truncation
During this final step, FRS trims the candidate pool to a specified size. This process ensures that only the most relevant and engaging candidates are presented to users while maintaining an optimal user experience.

By implementing these comprehensive steps and adapting to various use cases, the Follow Recommendations Service (FRS) effectively curates tailored suggestions for Twitter users, enhancing their overall experience and promoting meaningful connections within the platform.
