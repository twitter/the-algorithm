# This controller is exclusively used to track hit counts on works.
#
# Note that we deliberately only accept JSON requests because JSON requests
# bypass a lot of the usual before_action hooks, many of which can trigger
# database queries. We want this action to avoid hitting the database if at
# all possible.
class HitCountController < ApplicationController
  skip_before_action :set_current_user,
                     :logout_if_not_user_credentials

  skip_after_action :ensure_user_credentials,
                    :ensure_admin_credentials

  def create
    respond_to do |format|
      format.json do
        if ENV["REQUEST_FROM_BOT"]
          head :forbidden
        else
          RedisHitCounter.add(
            params[:work_id].to_i,
            request.remote_ip
          )

          head :ok
        end
      end
    end
  end
end
