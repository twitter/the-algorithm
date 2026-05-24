{
  "role": "discode",
  "name": "rekey-uua-prod",
  "config-files": [
    "rekey-uua.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:rekey-uua"
      },
      {
        "type": "packer",
        "name": "rekey-uua",
        "artifact": "./dist/rekey-uua.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "rekey-uua-prod-atla",
          "key": "atla/discode/prod/rekey-uua"
        },
        {
          "name": "rekey-uua-prod-pdxa",
          "key": "pdxa/discode/prod/rekey-uua"
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
