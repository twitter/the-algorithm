require 'faker'

FactoryBot.define do
  factory :admin_post do
    language { Language.default }
    admin_id { FactoryBot.create(:admin).id }
    title { "AdminPost Title" }
    content { "AdminPost content long enough to pass validation" }
  end
end
