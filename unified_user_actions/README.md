# Unified User Actions (UUA)

**Unified User Actions** (UUA) is a centralized, real-time stream of user actions on Twitter, consumed by various product, ML, and marketing teams. UUA reads client-side and server-side event streams that contain the user's actions and generates a unified real-time user actions Kafka stream. The Kafka stream is replicated to HDFS, GCP Pubsub, GCP GCS, GCP BigQuery.  The user actions include public actions such as favorites, retweets, replies and implicit actions like bookmark, impression, video view.

## Components 

- adapter: transform the raw inputs to UUA Thrift output
- client: Kafka client related utils
- kafka: more specific Kafka utils like customized serde
- service: deployment, modules and services