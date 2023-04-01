class Admin::ActivitiesController < Admin::BaseController
  def index
    @activities = AdminActivity.order("created_at DESC").page(params[:page])
    authorize @activities
  end

  def show
    @activity = AdminActivity.find(params[:id])
    authorize @activity
  end
end
