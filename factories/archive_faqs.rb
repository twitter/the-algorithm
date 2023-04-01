require "faker"

FactoryBot.define do
  sequence(:faq_title) do |n|
    "The #{n} FAQ"
  end

  factory :archive_faq do
    title { generate(:faq_title) }
  end

  factory :question do
    question { Faker::Lorem.sentence }
    anchor { Faker::Lorem.word }
    content { Faker::Lorem.paragraph }
    screencast { Faker::Internet.url }
  end
end
