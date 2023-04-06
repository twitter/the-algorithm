using UnityEngine;
using System.Collections;


/// <summary>
/// base class for position, scale, eulers and the generic Vector3 props
/// </summary>
public abstract class AbstractVector3TweenProperty : AbstractTweenProperty
{
	protected Transform _target;
	
	protected Vector3 _originalEndValue;
	protected Vector3 _startValue;
	protected Vector3 _endValue;
	protected Vector3 _diffValue;
	
	
	public AbstractVector3TweenProperty()
	{}
	
	
	public AbstractVector3TweenProperty( Vector3 endValue, bool isRelative = false ) : base( isRelative )
	{
		_originalEndValue = endValue;
	}


	public override bool validateTarget( object target )
	{
		return target is Transform;
	}
	
	
	public override void prepareForUse()
	{
		if( _isRelative && !_ownerTween.isFrom )
			_diffValue = _endValue;
		else
			_diffValue = _endValue - _startValue;
	}
	
	
	public void resetWithNewEndValue( Vector3 endValue )
	{
		_originalEndValue = endValue;
		prepareForUse();
	}
	
}
