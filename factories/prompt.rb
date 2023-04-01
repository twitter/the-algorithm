# frozen_string_literal: true
require 'faker'

FactoryBot.define do
  factory :prompt do
    description { Faker::Lorem.sentence }
  end

  factory :request, parent: :prompt, class: "Request"
  factory :offer, parent: :prompt, class: "Offer"
end
