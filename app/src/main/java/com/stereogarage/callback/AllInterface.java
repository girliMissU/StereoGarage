package com.stereogarage.callback;

/**
 * Created by Administor on 17/11/6.
 */

public class AllInterface {

    public interface OnMenuSlideListener {
        void onMenuSlide(float offset);
    }

    public interface Cb {
        void callback3(String param);
    }

    public interface Cb1 {
        void callback4(String param);
    }

    public interface Cb2 {
        void callback5(String param);
    }

}