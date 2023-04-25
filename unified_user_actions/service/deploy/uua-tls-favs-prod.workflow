{
  "role": "discode",
  "name": "uua-tls-favs-prod",
  "config-files": [
    "uua-tls-favs.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-tls-favs"
      },
      {
        "type": "packer",
        "name": "uua-tls-favs",
        "artifact": "./dist/uua-tls-favs.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-tls-favs-prod-atla",
          "key": "atla/discode/prod/uua-tls-favs"
        },
        {
          "name": "uua-tls-favs-prod-pdxa",
          "key": "pdxa/discode/prod/uua-tls-favs"
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
