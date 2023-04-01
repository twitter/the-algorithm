class ArchiveFaqsController < ApplicationController

  before_action :admin_only, except: [:index, :show]
  before_action :set_locale
  before_action :validate_locale, if: :logged_in_as_admin?
  before_action :require_language_id
  around_action :with_locale

  # GET /archive_faqs
  def index
    @archive_faqs = ArchiveFaq.order('position ASC')
    unless logged_in_as_admin?
      @archive_faqs = @archive_faqs.with_translations(I18n.locale)
    end
    respond_to do |format|
      format.html # index.html.erb
    end
  end

  # GET /archive_faqs/1
  def show
    @questions = []
    @archive_faq = ArchiveFaq.find_by!(slug: params[:id])
    if params[:language_id] == "en"
      @questions = @archive_faq.questions
    else
      @archive_faq.questions.each do |question|
        question.translations.each do |translation|
          if translation.is_translated == "1" && params[:language_id].to_s == translation.locale.to_s
            @questions << question
          end
        end
      end
    end
    @page_subtitle = @archive_faq.title + ts(" FAQ")

    respond_to do |format|
      format.html # show.html.erb
    end
  end

  protected
  def build_questions
    notice = ""
    num_to_build = params["num_questions"] ? params["num_questions"].to_i : @archive_faq.questions.count
    if num_to_build < @archive_faq.questions.count
      notice += ts("There are currently %{num} questions. You can only submit a number equal to or greater than %{num}. ", num: @archive_faq.questions.count)
      num_to_build = @archive_faq.questions.count
    elsif params["num_questions"]
      notice += ts("Set up %{num} questions. ", num: num_to_build)
    end
    num_existing = @archive_faq.questions.count
    num_existing.upto(num_to_build-1) do
      @archive_faq.questions.build
    end
    unless notice.blank?
      flash[:notice] = notice
    end
  end

  public
  # GET /archive_faqs/new
  def new
    @archive_faq = ArchiveFaq.new
    1.times { @archive_faq.questions.build(attributes: { question: "This is a temporary question", content: "This is temporary content", anchor: "ThisIsATemporaryAnchor"})}
    respond_to do |format|
      format.html # new.html.erb
    end
  end

  # GET /archive_faqs/1/edit
  def edit
    @archive_faq = ArchiveFaq.find_by(slug: params[:id])
    build_questions
  end

  # GET /archive_faqs/manage
  def manage
    @archive_faqs = ArchiveFaq.order('position ASC')
  end

  # POST /archive_faqs
  def create
    @archive_faq = ArchiveFaq.new(archive_faq_params)
      if @archive_faq.save
        flash[:notice] = 'ArchiveFaq was successfully created.'
        redirect_to(@archive_faq)
      else
        render action: "new"
      end
  end

  # PUT /archive_faqs/1
  def update
    @archive_faq = ArchiveFaq.find_by(slug: params[:id])

    if @archive_faq.update(archive_faq_params)
      flash[:notice] = 'ArchiveFaq was successfully updated.'
      redirect_to(@archive_faq)
    else
      render action: "edit"
    end
  end

  # reorder FAQs
  def update_positions
    if params[:archive_faqs]
      @archive_faqs = ArchiveFaq.reorder_list(params[:archive_faqs])
      flash[:notice] = ts("Archive FAQs order was successfully updated.")
    elsif params[:archive_faq]
      params[:archive_faq].each_with_index do |id, position|
        ArchiveFaq.update(id, position: position + 1)
        (@archive_faqs ||= []) << ArchiveFaq.find(id)
      end
    end
    respond_to do |format|
      format.html { redirect_to(archive_faqs_path) }
      format.js { render nothing: true }
    end
  end

  # The ?language_id=somelanguage needs to persist throughout URL changes
  # Get the value from set_locale to make sure there's no problem with order
  def default_url_options
    { language_id: set_locale.to_s }
  end

  # Set the locale as an instance variable first
  def set_locale
    session[:language_id] = params[:language_id].presence if session[:language_id] != params[:language_id].presence

    if current_user.present? && $rollout.active?(:set_locale_preference,
                                                 current_user)
      @i18n_locale = session[:language_id] || Locale.find(current_user.
        preference.preferred_locale).iso
    else
      @i18n_locale = session[:language_id] || I18n.default_locale
    end
  end

  def validate_locale
    return if Locale.exists?(iso: @i18n_locale)

    flash[:error] = "The specified locale does not exist."
    redirect_to url_for(request.query_parameters.merge(language_id: I18n.default_locale))
  end

  def require_language_id
    return if params[:language_id].present?

    redirect_to url_for(request.query_parameters.merge(language_id: @i18n_locale.to_s))
  end

  # Setting I18n.locale directly is not thread safe
  def with_locale
    I18n.with_locale(@i18n_locale) { yield }
  end

  # GET /archive_faqs/1/confirm_delete
  def confirm_delete
    @archive_faq = ArchiveFaq.find_by(slug: params[:id])
  end

  # DELETE /archive_faqs/1
  def destroy
    @archive_faq = ArchiveFaq.find_by(slug: params[:id])
    @archive_faq.destroy
    redirect_to(archive_faqs_path)
  end

  private

  def archive_faq_params
    params.require(:archive_faq).permit(
      :title,
      questions_attributes: [
        :id, :question, :anchor, :content, :screencast, :_destroy, :is_translated
      ]
    )
  end
end
