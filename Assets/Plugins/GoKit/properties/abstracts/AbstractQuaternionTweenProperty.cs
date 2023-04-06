using UnityEngine;
using System.Collections;


/// <summary>
/// base class for generic Quaternion props
/// </summary>
public abstract class AbstractQuaternionTweenProperty : AbstractTweenProperty
{
	protected Transform _target;
	
	protected Quaternion _originalEndValue;
	protected Quaternion _startValue;
	protected Quaternion _endValue;
	
	public AbstractQuaternionTweenProperty()
	{}
	
	
	public AbstractQuaternionTweenProperty( Quaternion endValue, bool isRelative = false ) : base( isRelative )
	{
		_originalEndValue = endValue;
	}


	public override bool validateTarget( object target )
	{
		return target is Transform;
	}
	
	
	public override void prepareForUse()
	{
		if (_isRelative && !_ownerTween.isFrom)
			_endValue = _startValue * _endValue;
	}

}
