package co.apptailor.Worker.core;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class WorkerSelfModule extends ReactContextBaseJavaModule {

    private int workerId;
    private ReactApplicationContext parentContext;

    public WorkerSelfModule(ReactApplicationContext context) {
        super(context);
    }

    public void initialize(int workerId, ReactApplicationContext parentContext) {
        this.parentContext = parentContext;
        this.workerId = workerId;
    }

    @Override
    public @Nullable Map<String, Object> getConstants() {
      HashMap<String, Object> constants = new HashMap<String, Object>();
      constants.put("inWorker", parentContext == null ? 0 : 1);
      return constants;
    }

    @Override
    public String getName() {
        return "WorkerSelfManager";
    }

    @ReactMethod
    public void postMessage(String data) {
        if (parentContext == null) { return; }

        parentContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("Worker" + String.valueOf(workerId), data);
    }
}
