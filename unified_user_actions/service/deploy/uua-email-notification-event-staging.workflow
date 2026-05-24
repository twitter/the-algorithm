{
  "role": "discode",
  "name": "uua-email-notification-event-staging",
  "config-files": [
    "uua-email-notification-event.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-email-notification-event"
      },
      {
        "type": "packer",
        "name": "uua-email-notification-event-staging",
        "artifact": "./dist/uua-email-notification-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-email-notification-event-staging-pdxa",
          "key": "pdxa/discode/staging/uua-email-notification-event"
        }
      ]
    }
  ]
}
