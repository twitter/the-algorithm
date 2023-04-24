{
  "role": "discode",
  "name": "uua-user-modification-prod",
  "config-files": [
    "uua-user-modification.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-user-modification"
      },
      {
        "type": "packer",
        "name": "uua-user-modification",
        "artifact": "./dist/uua-user-modification.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-user-modification-prod-atla",
          "key": "atla/discode/prod/uua-user-modification"
        },
        {
          "name": "uua-user-modification-prod-pdxa",
          "key": "pdxa/discode/prod/uua-user-modification"
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
