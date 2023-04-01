require "faker"

FactoryBot.define do
  factory :admin_banner do
    sequence(:content) { |n| "#{Faker::Lorem.paragraph} (#{n})" }

    active { false }

    trait :active do
      active { true }
    end
  end
end
