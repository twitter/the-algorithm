using UnityEngine;
using System;
using System.Collections;


public abstract class AbstractTweenProperty
{
	protected bool _isInitialized;
	public bool isInitialized { get { return _isInitialized; } }
	
	protected bool _isRelative;
	protected GoTween _ownerTween;
	
	protected Func<float, float, float, float, float> _easeFunction;
	
	

	public AbstractTweenProperty( bool isRelative = false )
	{
		_isRelative = isRelative;
	}
	
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	/// <summary>
	/// checks to see if a TweenProperty matches another. checks propertyNames of IGenericPropertys first then
	/// resorts to direct type checking
	/// </summary>
	public override bool Equals( object obj )
	{
		// null check first
		if( obj == null )
			return false;
		
		// handle IGenericProperty comparisons which just have the property name checked
		if( this is IGenericProperty && obj is IGenericProperty )
			return ((IGenericProperty)this).propertyName == ((IGenericProperty)obj).propertyName;
		
		// check for the same types
		if( obj.GetType() == this.GetType() )
			return true;
		
		return base.Equals( obj );
	}
	
	
	/// <summary>
	/// called by a Tween just after this property is validated and added to the Tweens property list
	/// </summary>
	public virtual void init( GoTween owner )
	{
		_isInitialized = true;
		_ownerTween = owner;
		
		// if we dont have an easeFunction use the owners type
		if( _easeFunction == null )
			setEaseType( owner.easeType );
	}
	
	
	/// <summary>
	/// clones the instance
	/// </summary>
    public AbstractTweenProperty clone()
    {
		var clone = MemberwiseClone() as AbstractTweenProperty;
		clone._ownerTween = null;
		clone._isInitialized = false;
		clone._easeFunction = null;
		
		return clone;
    }
	
	
	/// <summary>
	/// sets the ease type for this tween property
	/// technically, this should be an internal method
	/// </summary>
	public void setEaseType( GoEaseType easeType )
	{
		_easeFunction = GoTweenUtils.easeFunctionForType( easeType );
	}
	
	
    /// <summary>
    /// each TweenProperty should override this to ensure the object is the correct type
    /// </summary>
    public virtual bool validateTarget( object target )
    {
        return true;
    }
	
	
	/// <summary>
	/// subclasses should get the eased time then set the new value on the object
	/// </summary>
	public abstract void tick( float totalElapsedTime );
	
	
	/// <summary>
	/// called when a Tween is initially started.
	/// subclasses should strongly type the start/end/target and handle isFrom with
	/// regard to setting the proper start/end values
	/// </summary>
	public abstract void prepareForUse();

}
