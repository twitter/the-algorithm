{
  "role": "discode",
  "name": "uua-tweetypie-event-prod",
  "config-files": [
    "uua-tweetypie-event.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-tweetypie-event"
      },
      {
        "type": "packer",
        "name": "uua-tweetypie-event",
        "artifact": "./dist/uua-tweetypie-event.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-tweetypie-event-prod-atla",
          "key": "atla/discode/prod/uua-tweetypie-event"
        },
        {
          "name": "uua-tweetypie-event-prod-pdxa",
          "key": "pdxa/discode/prod/uua-tweetypie-event"
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
