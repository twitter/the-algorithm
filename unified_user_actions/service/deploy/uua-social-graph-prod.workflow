{
  "role": "discode",
  "name": "uua-social-graph-prod",
  "config-files": [
    "uua-social-graph.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-social-graph"
      },
      {
        "type": "packer",
        "name": "uua-social-graph",
        "artifact": "./dist/uua-social-graph.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-social-graph-prod-atla",
          "key": "atla/discode/prod/uua-social-graph"
        },
        {
          "name": "uua-social-graph-prod-pdxa",
          "key": "pdxa/discode/prod/uua-social-graph"
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
