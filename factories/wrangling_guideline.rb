require 'faker'

FactoryBot.define do

  sequence(:wrangling_guideline_title) do |n|
    "The #{n} Wrangling Guideline"
  end

  sequence(:wrangling_guideline_content) do |n|
    "This is the #{n} Wrangling Guideline"
  end

  factory :wrangling_guideline do |f|
    title { generate(:wrangling_guideline_title) }
    content { generate(:wrangling_guideline_content) }
  end
end
