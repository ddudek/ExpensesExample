package pl.ddudek.mvxrnexample.view.common.reactnativebridge;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class NativeCallbacksBridgeModule extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;

  private NativeCallbacksBridgeListeners listeners;

  NativeCallbacksBridgeModule(ReactApplicationContext context, NativeCallbacksBridgeListeners listeners) {
    super(context);
    this.reactContext = context;
    this.listeners = listeners;
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }

  @NonNull
  @Override
  public String getName() {
    return "NativeCallbacksBridge";
  }

  @ReactMethod
  public void onExpenseItemClicked(String id) {
    for (NativeCallbacksBridgeListeners.NativeCallbacksBridgeListener listener: listeners.getListeners()) {
      listener.onExpenseItemClicked(id);
    }
  }
}