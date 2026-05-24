{
  "role": "discode",
  "name": "uua-favorite-archival-events-staging",
  "config-files": [
    "uua-favorite-archival-events.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-favorite-archival-events"
      },
      {
        "type": "packer",
        "name": "uua-favorite-archival-events-staging",
        "artifact": "./dist/uua-favorite-archival-events.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-favorite-archival-events-staging-pdxa",
          "key": "pdxa/discode/staging/uua-favorite-archival-events"
        }
      ]
    }
  ]
}
