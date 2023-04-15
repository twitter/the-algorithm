{
  "role": "discode",
  "name": "uua-client-event-prod",
  "config-files": [
    "uua-client-event.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-client-event"
      },
      {
        "type": "packer",
        "name": "uua-client-event",
        "artifact": "./dist/uua-client-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-client-event-prod-atla",
          "key": "atla/discode/prod/uua-client-event"
        },
        {
          "name": "uua-client-event-prod-pdxa",
          "key": "pdxa/discode/prod/uua-client-event"
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
