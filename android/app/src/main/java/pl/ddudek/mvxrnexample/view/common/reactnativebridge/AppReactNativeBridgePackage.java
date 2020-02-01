package pl.ddudek.mvxrnexample.view.common.reactnativebridge;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppReactNativeBridgePackage implements ReactPackage {

  private AppReactNativeBridge listeners;

  public AppReactNativeBridgePackage(AppReactNativeBridge listeners) {
    this.listeners = listeners;
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  @Override
  public List<NativeModule> createNativeModules(
                              ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();

    modules.add(new AppReactNativeBridgeModule(reactContext, listeners));

    return modules;
  }

}