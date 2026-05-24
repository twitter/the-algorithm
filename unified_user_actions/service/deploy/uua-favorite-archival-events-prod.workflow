{
  "role": "discode",
  "name": "uua-favorite-archival-events-prod",
  "config-files": [
    "uua-favorite-archival-events.aurora"
  ],
  "build": {
    "play": true,
    "trigger": {
      "cron-schedule": "0 17 * * 2"
    },
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
        "name": "uua-favorite-archival-events",
        "artifact": "./dist/uua-favorite-archival-events.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-favorite-archival-events-prod-atla",
          "key": "atla/discode/prod/uua-favorite-archival-events"
        },
        {
          "name": "uua-favorite-archival-events-prod-pdxa",
          "key": "pdxa/discode/prod/uua-favorite-archival-events"
        }
      ]
    }
  ],
  "subscriptions": [
    {
      "type": "SLACK",
      "recipients": [
        {
          "to": "discode-oncall"
        }
      ],
      "events": ["WORKFLOW_SUCCESS"]
    },
    {
      "type": "SLACK",
      "recipients": [{
        "to": "discode-oncall"
      }],
      "events": ["*FAILED"]
    }
  ]
}
