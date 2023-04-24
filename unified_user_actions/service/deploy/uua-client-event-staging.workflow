{
  "role": "discode",
  "name": "uua-client-event-staging",
  "config-files": [
    "uua-client-event.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-client-event"
      },
      {
        "type": "packer",
        "name": "uua-client-event-staging",
        "artifact": "./dist/uua-client-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-client-event-staging-pdxa",
          "key": "pdxa/discode/staging/uua-client-event"
        }
      ]
    }
  ]
}
