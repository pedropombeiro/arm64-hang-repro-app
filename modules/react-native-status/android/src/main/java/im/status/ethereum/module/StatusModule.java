package im.status.ethereum.module;

import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import library.Library;

class StatusModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static final String TAG = "StatusModule";

    private static StatusModule module;
    private ReactApplicationContext reactContext;

    StatusModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "Status";
    }

    @Override
    public void onHostResume() {  // Activity `onResume`
        module = this;
        Library.doSomething(); // NOTE: Commenting this line makes the app run without hanging at startup
    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
}
