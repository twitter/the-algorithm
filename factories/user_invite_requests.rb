require 'faker'
FactoryBot.define do

  factory :user_invite_requests, class: UserInviteRequest do
    user_id { FactoryBot.create(:user).id }
    quantity { 5 }
    reason { "Because reasons!" }
    granted { false }
    handled { false }
  end
end
