{
  "role": "discode",
  "name": "uua-email-notification-event-prod",
  "config-files": [
    "uua-email-notification-event.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-email-notification-event"
      },
      {
        "type": "packer",
        "name": "uua-email-notification-event",
        "artifact": "./dist/uua-email-notification-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-email-notification-event-prod-atla",
          "key": "atla/discode/prod/uua-email-notification-event"
        },
        {
          "name": "uua-email-notification-event-prod-pdxa",
          "key": "pdxa/discode/prod/uua-email-notification-event"
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
