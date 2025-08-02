{
  "role": "discode",
  "name": "uua-retweet-archival-events-staging",
  "config-files": [
    "uua-retweet-archival-events.aurora"
  ],
  "build": {
    "play": true,
    "dependencies": [
      {
        "role": "packer",
        "name": "packer-client-no-petwitter",
        "version": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "name": "bundle",
        "target": "unified_user_actions/service/src/main/scala:uua-retweet-archival-events"
      },
      {
        "type": "packer",
        "name": "uua-retweet-archival-events-staging",
        "artifact": "./dist/uua-retweet-archival-events.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-retweet-archival-events-staging-pdtwittera",
          "key": "pdtwittera/discode/staging/uua-retweet-archival-events"
        }
      ]
    }
  ]
}
