create_thrift_libraries(
    base_name = "media-entity",
    sources = ["media_entity.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "mediaservices/commons/src/main/thrift",
    ],
    export_roots = [
        "mediaservices/commons/src/main/thrift:thrift",
    ],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-media-entity-thrift-java",
    provides_scala_name = "tweetypie-media-entity-thrift-scala",
)

create_thrift_libraries(
    base_name = "edit-control",
    sources = ["edit_control.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-edit-control-thrift-java",
    provides_scala_name = "tweetypie-edit-control-thrift-scala",
)

create_thrift_libraries(
    base_name = "api-fields",
    sources = ["api_fields.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-api-fields-thrift-java",
    provides_scala_name = "tweetypie-api-fields-thrift-scala",
)

create_thrift_libraries(
    base_name = "note-tweet",
    sources = ["note_tweet.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-note-tweet-thrift-java",
    provides_scala_name = "tweetypie-note-tweet-thrift-scala",
)

create_thrift_libraries(
    base_name = "tweet",
    sources = [
        "creative-entity-enrichments/creative_entity_enrichments.thrift",
        "geo/tweet_location_info.thrift",
        "media/media_ref.thrift",
        "tweet.thrift",
        "unmentions/unmentions.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":api-fields",
        ":edit-control",
        ":media-entity",
        ":note-tweet",
        "mediaservices/commons/src/main/thrift",
        "src/thrift/com/twitter/content-health/toxicreplyfilter",
        "src/thrift/com/twitter/dataproducts:enrichments_profilegeo",
        "src/thrift/com/twitter/escherbird:tweet-annotation",
        "src/thrift/com/twitter/expandodo:cards",
        "src/thrift/com/twitter/geoduck",
        "src/thrift/com/twitter/service/scarecrow/gen:tiered-actions",
        "src/thrift/com/twitter/spam/rtf:safety-label",
        "src/thrift/com/twitter/timelines/self_thread:thrift",
        "src/thrift/com/twitter/tseng/withholding:thrift",
        "src/thrift/com/twitter/tweet_pivots:tweet-pivots",
        "strato/config/src/thrift/com/twitter/strato/columns/creative_entity_enrichments",
        "unified-cards/thrift/src/main/thrift:thrift-contract",
    ],
    export_roots = [
        ":api-fields",
        ":edit-control",
        ":media-entity",
        ":note-tweet",
        "mediaservices/commons/src/main/thrift:thrift",
        "src/thrift/com/twitter/content-health/toxicreplyfilter",
        "src/thrift/com/twitter/dataproducts:enrichments_profilegeo",
        "src/thrift/com/twitter/escherbird:tweet-annotation",
        "src/thrift/com/twitter/expandodo:cards",
        "src/thrift/com/twitter/geoduck:geoduck",
        "src/thrift/com/twitter/service/scarecrow/gen:tiered-actions",
        "src/thrift/com/twitter/spam/rtf:safety-label",
        "src/thrift/com/twitter/timelines/self_thread:thrift",
        "src/thrift/com/twitter/tseng/withholding:thrift",
        "src/thrift/com/twitter/tweet_pivots:tweet-pivots",
        "strato/config/src/thrift/com/twitter/strato/columns/creative_entity_enrichments",
    ],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-tweet-thrift-java",
    provides_python_name = "tweetypie-tweet-thrift-python",
    provides_scala_name = "tweetypie-tweet-thrift-scala",
)

create_thrift_libraries(
    base_name = "service",
    sources = [
        "deleted_tweet.thrift",
        "tweet_service.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":audit",
        ":transient_context",
        ":tweet",
        "carousel/service/thrift:service",
        "incentives/jiminy/src/main/thrift/com/twitter/incentives/jiminy:thrift",
        "mediaservices/commons/src/main/thrift",
        "src/thrift/com/twitter/bouncer:bounce-action-thrift",
        "src/thrift/com/twitter/context:feature-context",
        "src/thrift/com/twitter/servo:servo-exception",
        "src/thrift/com/twitter/spam/features:safety-meta-data",
        "src/thrift/com/twitter/spam/rtf:safety-label",
        "src/thrift/com/twitter/spam/rtf:safety-level",
        "src/thrift/com/twitter/spam/rtf:safety-result",
        "src/thrift/com/twitter/tseng/withholding:thrift",
    ],
    export_roots = [
        ":transient_context",
        ":tweet",
        "carousel/service/thrift:service",
        "incentives/jiminy/src/main/thrift/com/twitter/incentives/jiminy:thrift",
        "src/thrift/com/twitter/bouncer:bounce-action-thrift",
        "src/thrift/com/twitter/context:feature-context",
        "src/thrift/com/twitter/spam/features:safety-meta-data",
        "src/thrift/com/twitter/spam/rtf:safety-level",
        "src/thrift/com/twitter/spam/rtf:safety-result",
    ],
    generate_languages = [
        "go",
        "java",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-service-thrift-java",
    provides_python_name = "tweetypie-service-thrift-python",
    provides_scala_name = "tweetypie-service-thrift-scala",
)

create_thrift_libraries(
    base_name = "events",
    sources = [
        "retweet_archival_event.thrift",
        "tweet_events.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":audit",
        ":transient_context",
        ":tweet",
        "src/thrift/com/twitter/gizmoduck:user-thrift",
    ],
    export_roots = [
        ":audit",
        ":transient_context",
        ":tweet",
        "src/thrift/com/twitter/gizmoduck:user-thrift",
    ],
    generate_languages = [
        "java",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-events-thrift-java",
    provides_scala_name = "tweetypie-events-thrift-scala",
)

create_thrift_libraries(
    base_name = "audit",
    sources = ["tweet_audit.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-audit-thrift-java",
    provides_scala_name = "tweetypie-audit-thrift-scala",
)

create_thrift_libraries(
    base_name = "deprecated",
    sources = ["deprecated.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":service",
        ":tweet",
        "mediaservices/commons/src/main/thrift",
        "src/thrift/com/twitter/expandodo:cards",
        "src/thrift/com/twitter/gizmoduck:user-thrift",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:media-entity",
    ],
    generate_languages = [
        "java",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-deprecated-thrift-java",
    provides_scala_name = "tweetypie-deprecated-thrift-scala",
)

create_thrift_libraries(
    base_name = "delete_location_data",
    sources = ["delete_location_data.thrift"],
    tags = ["bazel-compatible"],
    provides_java_name = "delete-location-data-java",
    provides_scala_name = "delete-location-data-scala",
)

create_thrift_libraries(
    base_name = "transient_context",
    sources = ["transient_context.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":tweet",
    ],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "transient-context-java",
    provides_scala_name = "transient-context-scala",
)

create_thrift_libraries(
    base_name = "tweet_comparison_service",
    sources = ["tweet_comparison_service.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":service",
        "src/thrift/com/twitter/context:twitter-context",
    ],
    generate_languages = [
        "java",
        "scala",
    ],
    provides_java_name = "tweet-comparison-service-thrift-java",
    provides_scala_name = "tweet-comparison-service-thrift-scala",
)

create_thrift_libraries(
    base_name = "tweet_service_graphql",
    sources = ["tweet_service_graphql.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/ads/callback:engagement_request",
        "strato/config/src/thrift/com/twitter/strato/graphql",
    ],
    generate_languages = [
        "scala",
        "strato",
    ],
    provides_scala_name = "tweet-service-graphql-scala",
)

create_thrift_libraries(
    base_name = "stored-tweet-info",
    sources = [
        "stored_tweet_info.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":tweet",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-stored-tweet-info-thrift-java",
    provides_scala_name = "tweetypie-stored-tweet-info-thrift-scala",
)

create_thrift_libraries(
    base_name = "tweet-service-federated",
    sources = [
        "tweet_service_federated.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        ":stored-tweet-info",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "tweetypie-service-federated-thrift-java",
    provides_scala_name = "tweetypie-service-federated-thrift-scala",
)
