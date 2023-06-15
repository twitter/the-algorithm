{
  "role": "discode",
  "name": "uua-retweet-archival-events-prod",
  "config-files": [
    "uua-retweet-archival-events.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-retweet-archival-events"
      },
      {
        "type": "packer",
        "name": "uua-retweet-archival-events",
        "artifact": "./dist/uua-retweet-archival-events.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-retweet-archival-events-prod-atla",
          "key": "atla/discode/prod/uua-retweet-archival-events"
        },
        {
          "name": "uua-retweet-archival-events-prod-pdxa",
          "key": "pdxa/discode/prod/uua-retweet-archival-events"
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
