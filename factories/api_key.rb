# frozen_string_literal: true
require 'faker'

FactoryBot.define do
  factory :api_key do
    name { "API key name" }
  end
end
