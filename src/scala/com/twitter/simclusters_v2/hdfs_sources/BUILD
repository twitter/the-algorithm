scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":data_sources",
        "3rdparty/src/jvm/com/twitter/scalding:core",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/thrift/com/twitter/hermit/candidate:hermit-candidate-scala",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/wtf/entity_real_graph:entity_real_graph-thrift-scala",
    ],
)

scala_library(
    name = "data_sources",
    sources = [],
    description = "DAL datasets we wish to expose externally",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":ads_fav_based_simclusters_cluster_to_tweet_index-scala",
        ":ads_fav_click_based_simclusters_cluster_to_tweet_index-scala",
        ":aggregatable_producer_simclusters_embeddings_by_fav_score-scala",
        ":aggregatable_producer_simclusters_embeddings_by_fav_score_2020-scala",
        ":aggregatable_producer_simclusters_embeddings_by_fav_score_2020_thrift-scala",
        ":aggregatable_producer_simclusters_embeddings_by_fav_score_thrift-scala",
        ":aggregatable_producer_simclusters_embeddings_by_follow_score_2020-scala",
        ":aggregatable_producer_simclusters_embeddings_by_follow_score_2020_thrift-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score_2020-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score_2020_thrift-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score_relaxed_fav_engagement_threshold_2020-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score_relaxed_fav_engagement_threshold_2020_thrift-scala",
        ":aggregatable_producer_simclusters_embeddings_by_log_fav_score_thrift-scala",
        ":clusters_members_connected_components_ape_similarity-scala",
        ":clusters_members_largest_dim_ape_similarity-scala",
        ":clusters_members_largest_dim_ape_similarity_2_day_update-scala",
        ":clusters_members_louvain_ape_similarity-scala",
        ":co_engagement_top_k_similar_tweets-scala",
        ":explore_mbcg_user_embeddings_kv-scala",
        ":fav_based_evergreen_content_simclusters_cluster_to_tweet_index-scala",
        ":fav_based_simclusters_cluster_to_tweet_index-scala",
        ":fav_based_video_simclusters_cluster_to_tweet_index-scala",
        ":fav_inferred_language_tfg_topic_embeddings-scala",
        ":fav_tfg_topic_embeddings-scala",
        ":fav_tfg_topic_embeddings_2020-scala",
        ":fav_tfg_topic_embeddings_2020_parquet-scala",
        ":fav_tfg_topic_embeddings_parquet-scala",
        ":full_multi_type_graph-scala",
        ":geopopular_top_tweet_impressed_topics-scala",
        ":hashtag_simclusters_embeddings_updated-scala",
        ":interested_in_twice_by_largest_dim-scala",
        ":interested_in_twice_by_largest_dim_2_day_update-scala",
        ":interested_in_twice_by_largest_dim_fav_score-scala",
        ":interested_in_twice_connected_components-scala",
        ":interested_in_twice_louvain-scala",
        ":log_fav_reverse_index_semantic_core_per_language_simclusters_embeddings-scala",
        ":log_fav_semantic_core_per_language_simclusters_embeddings-scala",
        ":log_fav_tfg_topic_embeddings-scala",
        ":log_fav_tfg_topic_embeddings_parquet-scala",
        ":multi_type_graph_for_top_k_right_nodes_thrift_50_m_scio-scala",
        ":multi_type_graph_for_top_k_right_nodes_thrift_scio-scala",
        ":multi_type_simclusters_right_node_to_clusters_thrift_50_m-scala",
        ":multi_type_simclusters_right_node_to_clusters_thrift_fav_90_p_20_m-scala",
        ":offline_cluster_top_media_tweets_20M_145K_2020-scala",
        ":offline_tweet_recommendations_from_interested_in_20M_145K_2020-scala",
        ":offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_0_EL_15-scala",
        ":offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_15-scala",
        ":offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_50-scala",
        ":offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_8_EL_50-scala",
        ":offline_tweet_recommendations_from_mts_consumer_embeddings-scala",
        ":producer_norms_and_counts-scala",
        ":producer_top_k_simcluster_embeddings_by_fav_score-scala",
        ":producer_top_k_simcluster_embeddings_by_fav_score_2020-scala",
        ":producer_top_k_simcluster_embeddings_by_fav_score_updated-scala",
        ":producer_top_k_simcluster_embeddings_by_follow_score-scala",
        ":producer_top_k_simcluster_embeddings_by_follow_score_2020-scala",
        ":producer_top_k_simcluster_embeddings_by_follow_score_updated-scala",
        ":push_open_based_simclusters_cluster_to_tweet_index-scala",
        ":reply_based_simclusters_cluster_to_tweet_index-scala",
        ":retweet_based_simclusters_cluster_to_tweet_index-scala",
        ":reverse_index_hashtag_simclusters_embeddings_updated-scala",
        ":reverse_index_semantic_core_per_language_simclusters_embeddings-scala",
        ":reverse_index_semantic_core_simclusters_embeddings-scala",
        ":reverse_index_semantic_core_simclusters_embeddings_2020-scala",
        ":reverse_index_semantic_core_simclusters_embeddings_updated-scala",
        ":right_node_cosine_similarity_scio-scala",
        ":right_node_sim_hash_scio-scala",
        ":rux_faved_top_k_tweets-scala",
        ":semantic_core_embeddings_from_producer-scala",
        ":semantic_core_per_language_simclusters_embeddings-scala",
        ":semantic_core_simclusters_embeddings-scala",
        ":semantic_core_simclusters_embeddings_2020-scala",
        ":semantic_core_simclusters_embeddings_updated-scala",
        ":simcluster_embedding_top_k_producers_by_fav_score-scala",
        ":simcluster_embedding_top_k_producers_by_fav_score_2020-scala",
        ":simcluster_embedding_top_k_producers_by_fav_score_updated-scala",
        ":simcluster_embedding_top_k_producers_by_follow_score-scala",
        ":simcluster_embedding_top_k_producers_by_follow_score_2020-scala",
        ":simcluster_embedding_top_k_producers_by_follow_score_updated-scala",
        ":simclusters_inferred_entities_from_interested_in-scala",
        ":simclusters_inferred_entities_from_interested_in_keyed_by_cluster-scala",
        ":simclusters_inferred_entities_from_known_for-scala",
        ":simclusters_offline_cluster_top_k_tweets-scala",
        ":simclusters_offline_tweet_cluster_scores-scala",
        ":simclusters_offline_tweet_top_k_clusters-scala",
        ":simclusters_v2_cluster_details-scala",
        ":simclusters_v2_cluster_details_20m_145k_2020-scala",
        ":simclusters_v2_cluster_details_20m_145k_updated-scala",
        ":simclusters_v2_cluster_details_lite-scala",
        ":simclusters_v2_cluster_details_lite_20m_145k_2020-scala",
        ":simclusters_v2_cluster_details_lite_20m_145k_updated-scala",
        ":simclusters_v2_embeddings_lite-scala",
        ":simclusters_v2_global_language_embedding-scala",
        ":simclusters_v2_global_language_embedding_thrift-scala",
        ":simclusters_v2_interested_in-scala",
        ":simclusters_v2_interested_in_20M_145K_2020-scala",
        ":simclusters_v2_interested_in_20M_145K_updated-scala",
        ":simclusters_v2_interested_in_from_aggregatable_producer_embeddings_20M_145K_2020-scala",
        ":simclusters_v2_interested_in_from_producer_embeddings_20M_145K_updated-scala",
        ":simclusters_v2_interested_in_lite_20M_145K_2020-scala",
        ":simclusters_v2_known_for_20M_145K_2020-scala",
        ":simclusters_v2_known_for_20M_145K_2020_thrift-scala",
        ":simclusters_v2_known_for_20M_145K_dec11-scala",
        ":simclusters_v2_known_for_20M_145K_updated-scala",
        ":simclusters_v2_known_for_20M_145K_updated_thrift-scala",
        ":simclusters_v2_raw_interested_in_20M_145K_2020-scala",
        ":simclusters_v2_raw_interested_in_20M_145K_dec11-scala",
        ":simclusters_v2_raw_interested_in_20M_145K_updated-scala",
        ":simclusters_v2_raw_interested_in_lite_20M_145K_2020-scala",
        ":simclusters_v2_raw_known_for_20M_145K_2020-scala",
        ":simclusters_v2_raw_known_for_20M_145K_dec11-scala",
        ":simclusters_v2_raw_known_for_20M_145K_updated-scala",
        ":simclusters_v2_user_to_interested_in_20M_145K_2020-scala",
        ":simclusters_v2_user_to_interested_in_20M_145K_dec11-scala",
        ":simclusters_v2_user_to_interested_in_20M_145K_updated-scala",
        ":simclusters_v2_user_to_interested_in_from_aggregatable_producer_embeddings_20M_145K_2020-scala",
        ":simclusters_v2_user_to_interested_in_lite_20M_145K_2020-scala",
        ":similar_topics_from_topic_follow_graph-scala",
        ":similar_users_by_fav_based_producer_embedding-scala",
        ":similar_users_by_follow_based_producer_embedding-scala",
        ":top_k_right_nouns-scala",
        ":top_k_right_nouns_scio-scala",
        ":top_locale_topics_for_producer_from_em-scala",
        ":top_producers_for_locale_topics_from_topic_follow_graph-scala",
        ":topic_top_producers_em-scala",
        ":truncated_multi_type_graph-scala",
        ":truncated_multi_type_graph_scio-scala",
        ":tweet_evaluation_timelines_reference_set-scala",
        ":user_topic_weighted_embedding-scala",
        ":user_topic_weighted_embedding_parquet-scala",
        ":user_user_fav_graph-scala",
        ":user_user_graph-scala",
        ":user_user_normalized_graph-scala",
        ":video_view_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/common",
    ],
)

create_datasets(
    base_name = "user_user_fav_graph",
    java_schema = "com.twitter.simclusters_v2.thriftjava.EdgeWithDecayedWeights",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.EdgeWithDecayedWeights",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "producer_norms_and_counts",
    java_schema = "com.twitter.simclusters_v2.thriftjava.NormsAndCounts",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.NormsAndCounts",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "user_user_normalized_graph",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserAndNeighbors",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserAndNeighbors",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "multi_type_simclusters_right_node_to_clusters_thrift_fav_90_p_20_m",
    java_schema = "com.twitter.simclusters_v2.thriftjava.RightNodeWithClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.RightNodeWithClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "multi_type_simclusters_right_node_to_clusters_thrift_50_m",
    java_schema = "com.twitter.simclusters_v2.thriftjava.RightNodeWithClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.RightNodeWithClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "user_user_graph",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserAndNeighbors",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserAndNeighbors",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

# InterestedIn
create_datasets(
    base_name = "simclusters_v2_raw_interested_in_20M_145K_dec11",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_raw_interested_in_20M_145K_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_raw_interested_in_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_raw_interested_in_lite_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "multi_type_graph_for_top_k_right_nodes_thrift_fav_90_p_20_m_scio",
    java_schema = "com.twitter.simclusters_v2.thriftjava.MultiTypeGraphEdge",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.MultiTypeGraphEdge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "multi_type_graph_for_top_k_right_nodes_thrift_50_m_scio",
    java_schema = "com.twitter.simclusters_v2.thriftjava.MultiTypeGraphEdge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.MultiTypeGraphEdge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in_20M_145K_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in_lite_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_user_to_interested_in_20M_145K_dec11",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToInterestedInClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_user_to_interested_in_20M_145K_updated",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToInterestedInClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_user_to_interested_in_20M_145K_2020",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToInterestedInClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_user_to_interested_in_lite_20M_145K_2020",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToInterestedInClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_user_to_interested_in_from_aggregatable_producer_embeddings_20M_145K_2020",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToInterestedInClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToInterestedInClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
# end of InterestedIn

# KnownFor
create_datasets(
    base_name = "simclusters_v2_raw_known_for_20M_145K_dec11",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_raw_known_for_20M_145K_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_raw_known_for_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_known_for_20M_145K_dec11",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_known_for_20M_145K_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_known_for_20M_145K_updated_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToKnownForClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToKnownForClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_known_for_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.KnownForInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_known_for_20M_145K_2020_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.UserToKnownForClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserToKnownForClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

# end of KnownFor

create_datasets(
    base_name = "simclusters_v2_cluster_details",
    key_type = "scala.Tuple2[String, Int]",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterDetailsInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClusterDetails",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_cluster_details_lite",
    java_schema = "com.twitter.simclusters_v2.thriftjava.ClusterDetailsLite",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.ClusterDetailsLite",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_embeddings_lite",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.EmbeddingsLite",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_cluster_details_20m_145k_updated",
    key_type = "scala.Tuple2[String, Int]",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterDetailsInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClusterDetails",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_cluster_details_lite_20m_145k_updated",
    java_schema = "com.twitter.simclusters_v2.thriftjava.ClusterDetailsLite",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.ClusterDetailsLite",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_v2_cluster_details_20m_145k_2020",
    key_type = "scala.Tuple2[String, Int]",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterDetailsInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClusterDetails",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_cluster_details_lite_20m_145k_2020",
    java_schema = "com.twitter.simclusters_v2.thriftjava.ClusterDetailsLite",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.ClusterDetailsLite",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "tweet_evaluation_timelines_reference_set",
    description = "A Tweet dataset that contains impressed tweets with engagement labels, parsed from Timelines",
    java_schema = "com.twitter.simclusters_v2.thriftjava.ReferenceTweets",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.ReferenceTweets",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "semantic_core_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "semantic_core_simclusters_embeddings_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "semantic_core_simclusters_embeddings_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "semantic_core_per_language_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "log_fav_semantic_core_per_language_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_simclusters_embeddings_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_simclusters_embeddings_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "reverse_index_semantic_core_per_language_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "log_fav_reverse_index_semantic_core_per_language_simclusters_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "hashtag_simclusters_embeddings_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_tfg_topic_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_tfg_topic_embeddings_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_tfg_topic_embeddings_parquet",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TfgTopicEmbeddings",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "fav_tfg_topic_embeddings_2020_parquet",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TfgTopicEmbeddings",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "fav_inferred_language_tfg_topic_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "log_fav_tfg_topic_embeddings",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "log_fav_tfg_topic_embeddings_parquet",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TfgTopicEmbeddings",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "reverse_index_hashtag_simclusters_embeddings_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.InternalIdEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.InternalIdEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_fav_score",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_fav_score_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_fav_score_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_fav_score",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_fav_score_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_fav_score_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_follow_score",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_follow_score_updated",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simcluster_embedding_top_k_producers_by_follow_score_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.PersistedFullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimClusterEmbeddingTopKProducersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopProducersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_follow_score",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_follow_score_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "producer_top_k_simcluster_embeddings_by_follow_score_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerTopKSimClusterEmbeddingsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "similar_users_by_fav_based_producer_embedding",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimilarUsersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.hermit.candidate.thriftscala.Candidates",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "similar_users_by_follow_based_producer_embedding",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.SimilarUsersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.hermit.candidate.thriftscala.Candidates",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_follow_score_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score_relaxed_fav_engagement_threshold_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_fav_score",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_fav_score_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ProducerEmbeddingsInjections.ProducerSimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score_2020_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_follow_score_2020_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_fav_score_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_fav_score_2020_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "aggregatable_producer_simclusters_embeddings_by_log_fav_score_relaxed_fav_engagement_threshold_2020_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.SimClustersEmbeddingWithId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingWithId",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

# TWICE & Clustering datasets
create_datasets(
    base_name = "interested_in_twice_by_largest_dim",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersMultiEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "interested_in_twice_by_largest_dim_fav_score",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersMultiEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "interested_in_twice_by_largest_dim_2_day_update",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersMultiEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "interested_in_twice_louvain",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersMultiEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "interested_in_twice_connected_components",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersMultiEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "clusters_members_largest_dim_ape_similarity",
    key_type = "com.twitter.simclusters_v2.common.UserId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusteringInjections.OrderedClustersAndMembersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.OrderedClustersAndMembers",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "clusters_members_largest_dim_ape_similarity_2_day_update",
    key_type = "com.twitter.simclusters_v2.common.UserId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusteringInjections.OrderedClustersAndMembersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.OrderedClustersAndMembers",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "clusters_members_louvain_ape_similarity",
    key_type = "com.twitter.simclusters_v2.common.UserId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusteringInjections.OrderedClustersAndMembersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.OrderedClustersAndMembers",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "clusters_members_connected_components_ape_similarity",
    key_type = "com.twitter.simclusters_v2.common.UserId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusteringInjections.OrderedClustersAndMembersInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.OrderedClustersAndMembers",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

# End of TWICE & Clustering datasets

create_datasets(
    base_name = "simclusters_offline_tweet_cluster_scores",
    description = "A dataset that contains the scores for tweet and cluster pairs",
    java_schema = "com.twitter.simclusters_v2.thriftjava.TweetAndClusterScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TweetAndClusterScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_offline_tweet_top_k_clusters",
    description = "A dataset that contains the top clusters for each tweet",
    java_schema = "com.twitter.simclusters_v2.thriftjava.TweetTopKClustersWithScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TweetTopKClustersWithScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_offline_cluster_top_k_tweets",
    description = "A dataset that contains the top tweets for each cluster",
    java_schema = "com.twitter.simclusters_v2.thriftjava.ClusterTopKTweetsWithScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.ClusterTopKTweetsWithScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "simclusters_inferred_entities_from_known_for",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InferredEntitiesInjections.InferredEntityInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersInferredEntities",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_inferred_entities_from_interested_in",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InferredEntitiesInjections.InferredEntityInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersInferredEntities",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_inferred_entities_from_interested_in_keyed_by_cluster",
    key_type = "Int",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InferredEntitiesInjections.InferredEntityKeyedByClusterInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersInferredEntities",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "co_engagement_top_k_similar_tweets",
    description = "A dataset that contains the top similar tweets based on co-engagement",
    java_schema = "com.twitter.simclusters_v2.thriftjava.TweetTopKTweetsWithScore",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TweetTopKTweetsWithScore",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "rux_faved_top_k_tweets",
    description = "A dataset that contains the top similar tweets based on rux fav-to-impression ratio",
    java_schema = "com.twitter.simclusters_v2.thriftjava.TweetTopKTweetsWithScore",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.TweetTopKTweetsWithScore",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "semantic_core_embeddings_from_producer",
    key_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.EntitySimClustersEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in_from_producer_embeddings_20M_145K_updated",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_interested_in_from_aggregatable_producer_embeddings_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "geopopular_top_tweet_impressed_topics",
    key_type = "String",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SemanticCoreEntitiesInjections.StringToSemanticCoreEntityScoreListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.recos.entities.thriftscala.SemanticCoreEntityScoreList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "similar_topics_from_topic_follow_graph",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SemanticCoreEntitiesInjections.LongToSemanticCoreEntityScoreListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.recos.entities.thriftscala.SemanticCoreEntityScoreList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "top_locale_topics_for_producer_from_em",
    key_type = "com.twitter.recos.entities.thriftscala.UserIdWithLocale",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SemanticCoreEntitiesInjections.UserWithLocaleToSemanticCoreEntityScoreListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.recos.entities.thriftscala.SemanticCoreEntityScoreList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "top_producers_for_locale_topics_from_topic_follow_graph",
    key_type = "com.twitter.recos.entities.thriftscala.SemanticCoreEntityWithLocale",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SemanticCoreEntitiesInjections.SemanticCoreEntityWithLocaleToUsersScoreListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.recos.entities.thriftscala.UserScoreList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "topic_top_producers_em",
    key_type = "com.twitter.recos.entities.thriftscala.SemanticCoreEntityWithLocale",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SemanticCoreEntitiesInjections.SemanticCoreEntityWithLocaleToUsersScoreListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.recos.entities.thriftscala.UserScoreList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "adhoc_abuse_simcluster_features",
    java_schema = "com.twitter.simclusters_v2.thriftjava.AdhocSingleSideClusterScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.AdhocSingleSideClusterScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "search_abuse_simcluster_features_manhattan",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.SingleSideUserScoresInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SingleSideUserScores",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "adhoc_cross_simcluster_block_interaction_features",
    java_schema = "com.twitter.simclusters_v2.thriftjava.AdhocCrossSimClusterInteractionScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.AdhocCrossSimClusterInteractionScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "adhoc_cross_simcluster_fav_interaction_features",
    java_schema = "com.twitter.simclusters_v2.thriftjava.AdhocCrossSimClusterInteractionScores",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.AdhocCrossSimClusterInteractionScores",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "top_k_right_nouns",
    key_type = "com.twitter.simclusters_v2.thriftscala.RightNodeTypeStruct",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.topKRightNounListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.NounWithFrequencyList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "top_k_right_nouns_scio",
    key_type = "com.twitter.simclusters_v2.thriftscala.RightNodeTypeStruct",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.topKRightNounListInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.NounWithFrequencyList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_cluster_top_media_tweets_20M_145K_2020",
    key_type = "com.twitter.simclusters_v2.thriftscala.DayPartitionedClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopMediaTweetsInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TweetsWithScore",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "truncated_multi_type_graph",
    key_type = "com.twitter.simclusters_v2.thriftscala.LeftNode",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.truncatedMultiTypeGraphInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "truncated_multi_type_graph_scio",
    key_type = "com.twitter.simclusters_v2.thriftscala.LeftNode",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.truncatedMultiTypeGraphInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "multi_type_graph_for_top_k_right_nodes_thrift_scio",
    java_schema = "com.twitter.simclusters_v2.thriftjava.MultiTypeGraphEdge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.MultiTypeGraphEdge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "full_multi_type_graph",
    java_schema = "com.twitter.simclusters_v2.thriftjava.MultiTypeGraphEdge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.MultiTypeGraphEdge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "right_node_sim_hash_scio",
    java_schema = "com.twitter.simclusters_v2.thriftjava.RightNodeSimHashSketch",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.RightNodeSimHashSketch",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "right_node_cosine_similarity_scio",
    key_type = "com.twitter.simclusters_v2.thriftscala.RightNode",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.similarRightNodesInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.SimilarRightNodes",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "user_topic_weighted_embedding",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "user_topic_weighted_embedding_parquet",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.UserTopicWeightedEmbedding",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "explore_mbcg_user_embeddings_kv",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.EntityEmbeddingsInjections.UserMbcgEmbeddingInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.ml.api.thriftscala.Embedding",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_interested_in_20M_145K_2020",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_0_EL_15",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_15",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_50",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_8_EL_50",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_from_mts_consumer_embeddings",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "video_view_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "retweet_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "reply_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "push_open_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "ads_fav_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "ads_fav_click_based_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_based_evergreen_content_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "fav_based_video_simclusters_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_global_language_embedding",
    key_type = "String",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.InterestedInInjection.languageInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn",
    scala_dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_v2_global_language_embedding_thrift",
    java_schema = "com.twitter.simclusters_v2.thriftjava.LanguageToClusters",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.LanguageToClusters",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
