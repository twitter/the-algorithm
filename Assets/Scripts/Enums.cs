using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Assets.Scripts
{
    //enums for the state of the slingshot, the 
    //state of the game and the state of the bird
    public enum SlingshotState
    {
        Idle,
        UserPulling,
        BirdFlying
    }

    public enum GameState
    {
        Start,
        BirdMovingToSlingshot,
        Playing,
        Won,
        Lost
    }


    public enum BirdState
    {
        BeforeThrown,
        Thrown
    }
    
}
