class Admin::ApiController < Admin::BaseController
  before_action :check_for_cancel, only: [:create, :update]

  def index
    @api_keys = if params[:query]
                  sql_query = "%" + params[:query] + "%"
                  ApiKey.where("name LIKE ?", sql_query).order("name").paginate(page: params[:page])
                else
                  ApiKey.order("name").paginate(page: params[:page])
                end
  end

  def show
    redirect_to action: "index"
  end

  def new
    @api_key = ApiKey.new
  end

  def create
    # Use provided api key params if available otherwise fallback to empty
    # ApiKey object
    @api_key = params[:api_key].nil? ? ApiKey.new : ApiKey.new(api_key_params)
    if @api_key.save
      flash[:notice] = ts("New token successfully created")
      redirect_to action: "index"
    else
      render "new"
    end
  end

  def edit
    @api_key = ApiKey.find(params[:id])
  end

  def update
    @api_key = ApiKey.find(params[:id])
    if @api_key.update(api_key_params)
      flash[:notice] = ts("Access token was successfully updated")
      redirect_to action: "index"
    else
      render "edit"
    end
  end

  def destroy
    @api_key = ApiKey.find(params[:id])
    @api_key.destroy
    redirect_to(admin_api_path)
  end

  private

  def api_key_params
    params.require(:api_key).permit(
      :name, :access_token, :banned
    )
  end

  def check_for_cancel
    if params[:cancel_button]
      redirect_to action: "index"
    end
  end
end
