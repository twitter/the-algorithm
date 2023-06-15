{
  "role": "discode",
  "name": "uua-tweetypie-event-staging",
  "config-files": [
    "uua-tweetypie-event.aurora"
  ],
  "build": {
    "play": true,
    "dependencies": [
      {
        "role": "packer",
        "name": "packer-client-no-pex",
        "version": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "name": "bundle",
        "target": "unified_user_actions/service/src/main/scala:uua-tweetypie-event"
      },
      {
        "type": "packer",
        "name": "uua-tweetypie-event-staging",
        "artifact": "./dist/uua-tweetypie-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-tweetypie-event-staging-pdxa",
          "key": "pdxa/discode/staging/uua-tweetypie-event"
        }
      ]
    }
  ]
}
