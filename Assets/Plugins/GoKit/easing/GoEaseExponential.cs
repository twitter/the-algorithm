using System;


public static class GoEaseExponential
{
	public static float EaseIn( float t, float b, float c, float d )
	{
		return ( t == 0 ) ? b : c * (float)Math.Pow( 2, 10 * ( t / d - 1 ) ) + b;
	}

	public static float EaseOut( float t, float b, float c, float d )
	{
		return ( t == d ) ? b + c : c * (float)( -Math.Pow( 2, -10 * t / d ) + 1 ) + b;
	}

	public static float EaseInOut( float t, float b, float c, float d )
	{
		if( t == 0 )
		{
			return b;
		}
		if( t == d )
		{
			return b + c;
		}
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * (float)Math.Pow( 2, 10 * ( t - 1 ) ) + b;
		}
		return c / 2 * (float)( -Math.Pow( 2, -10 * --t ) + 2 ) + b;
	}
}
