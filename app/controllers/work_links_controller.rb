class WorkLinksController < ApplicationController

  before_action :users_only
  before_action :load_user_and_work
  before_action :check_ownership

  # only let users look at stats for their own work
  def load_user_and_work
    @user = current_user
    @work = Work.find(params[:work_id])
    @check_ownership_of = @work
  end

  def index
    @work_links = WorkLink.where(work_id: @work.id).order(:created_at).paginate(page: params[:page])
  end

end
