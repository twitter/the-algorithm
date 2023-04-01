class Opendoors::ExternalAuthorsController < ApplicationController

  before_action :users_only
  before_action :opendoors_only
  before_action :load_external_author, only: [:show, :edit, :update, :forward]

  def load_external_author
    @external_author = ExternalAuthor.find(params[:id])
  end

  def index
    if params[:query]
      @query = params[:query]
      sql_query = '%' + @query +'%'
      @external_authors = ExternalAuthor.where("external_authors.email LIKE ?", sql_query)
    else
      @external_authors = ExternalAuthor.unclaimed
    end
    # list in reverse order
    @external_authors = @external_authors.order("created_at DESC").paginate(page: params[:page])
  end

  def show
  end

  def new
    @external_author = ExternalAuthor.new
  end

  # create an external author identity (and pre-emptively block it)
  def create
    @external_author = ExternalAuthor.new(external_author_params)
    unless @external_author.save
      flash[:error] = ts("We couldn't save that address.")
    else
      flash[:notice] = ts("We have saved and blocked the email address #{@external_author.email}")
    end

    redirect_to opendoors_tools_path
  end

  def forward
    if @external_author.is_claimed
      flash[:error] = ts("This external author has already been claimed!")
      redirect_to opendoors_external_author_path(@external_author) and return
    end

    # get the invitation
    @invitation = Invitation.where(external_author_id: @external_author.id).first

    unless @invitation
      # if there is no invite we create one
      @invitation = Invitation.new(external_author: @external_author)
    end

    # send the invitation to specified address
    @email = params[:email]
    @invitation.invitee_email = @email
    @invitation.creator = User.find_by(login: "open_doors") || current_user
    if @invitation.save
      flash[:notice] = ts("Claim invitation for #{@external_author.email} has been forwarded to #{@invitation.invitee_email}!")
    else
      flash[:error] = ts("We couldn't forward the claim for #{@external_author.email} to that email address.") + @invitation.errors.full_messages.join(", ")
    end

    # redirect to external author listing for that user
    redirect_to opendoors_external_authors_path(query: @external_author.email)
  end

  private

  def external_author_params
    params.require(:external_author).permit(
      :email, :do_not_email, :do_not_import
    )
  end

end
