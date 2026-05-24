{
  "role": "discode",
  "name": "rekey-uua-iesource-prod",
  "config-files": [
    "rekey-uua-iesource.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:rekey-uua-iesource"
      },
      {
        "type": "packer",
        "name": "rekey-uua-iesource",
        "artifact": "./dist/rekey-uua-iesource.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "rekey-uua-iesource-prod-atla",
          "key": "atla/discode/prod/rekey-uua-iesource"
        },
        {
          "name": "rekey-uua-iesource-prod-pdxa",
          "key": "pdxa/discode/prod/rekey-uua-iesource"
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
