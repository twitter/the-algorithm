class InviteFromQueueJob < ApplicationJob
  def perform(count:, creator: nil)
    InviteRequest.order(:id).limit(count).each do |request|
      request.invite_and_remove(creator)
    end
  end
end
