{
  "role": "discode",
  "name": "uua-enrichment-planner-staging",
  "config-files": [
    "uua-enrichment-planner.aurora"
  ],
  "build": {
    "play": true,
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
        "target": "unified_user_actions/service/src/main/scala:uua-enrichment-planner"
      },
      {
        "type": "packer",
        "name": "uua-enrichment-planner-staging",
        "artifact": "./dist/uua-enrichment-planner.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-enricher-enrichment-planner-pdxa",
          "key": "pdxa/discode/staging/uua-enrichment-planner"
        }
      ]
    }
  ]
}
