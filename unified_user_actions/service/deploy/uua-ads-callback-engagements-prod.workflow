{
  "role": "discode",
  "name": "uua-ads-callback-engagements-prod",
  "config-files": [
    "uua-ads-callback-engagements.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-ads-callback-engagements"
      },
      {
        "type": "packer",
        "name": "uua-ads-callback-engagements",
        "artifact": "./dist/uua-ads-callback-engagements.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "prod",
      "targets": [
        {
          "name": "uua-ads-callback-engagements-prod-atla",
          "key": "atla/discode/prod/uua-ads-callback-engagements"
        },
        {
          "name": "uua-ads-callback-engagements-prod-pdxa",
          "key": "pdxa/discode/prod/uua-ads-callback-engagements"
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
