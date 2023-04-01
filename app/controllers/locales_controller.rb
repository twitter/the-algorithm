class LocalesController < ApplicationController

  def index
    authorize Locale

    @locales = Locale.default_order
  end

  def new
    @locale = Locale.new
    authorize @locale
    @languages = Language.default_order
  end

  # GET /locales/en/edit
  def edit
    @locale = Locale.find_by(iso: params[:id])
    authorize @locale
    @languages = Language.default_order
  end

  def update
    @locale = Locale.find_by(iso: params[:id])
    @locale.attributes = locale_params
    authorize @locale
    if @locale.save
      flash[:notice] = ts('Your locale was successfully updated.')
      redirect_to action: 'index', status: 303
    else
      @languages = Language.default_order
      render action: "edit"
    end
  end


  def create
    @locale = Locale.new(locale_params)
    authorize @locale
    if @locale.save
      flash[:notice] = t('successfully_added', default: 'Locale was successfully added.')
      redirect_to locales_path
    else
      @languages = Language.default_order
      render action: "new"
    end
  end

  private

  def locale_params
    params.require(:locale).permit(
      :name, :iso, :language_id, :email_enabled, :interface_enabled
    )
  end
end
