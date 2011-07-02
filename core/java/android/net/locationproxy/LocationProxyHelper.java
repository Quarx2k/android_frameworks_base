/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.net.locationproxy;

import dalvik.system.DexClassLoader;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.location.LocationManager;
import android.util.Log;
import android.provider.Settings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * {@hide}
 */
public class LocationProxyHelper {

    private static final String TAG = "LocationProxyHelper";
    private static DexClassLoader sLocationProxyClassLoader;  
    static String LocationProxyJarLocation = "/system/framework/com.motorola.android.location.jar";


    /*   public static boolean isLocationProxySupported(Context context) {
        return context.getResources().getBoolean(
                com.android.internal.R.bool.config_sLocationProxyEnabled);
    }*/
    
public static Object createLocationProxyService(Context context, Handler handler) {
	// if (isLocationProxySupported(context)) {
 Object instance = null;
       try {
            DexClassLoader cl =  new DexClassLoader(LocationProxyJarLocation, new ContextWrapper(context).getCacheDir().getAbsolutePath(), null, ClassLoader.getSystemClassLoader());
            Class<?> klass = cl.loadClass("com.android.server.LocationProxyService");
            if (klass != null) {
            	Constructor<?> ctor = klass.getDeclaredConstructors()[0];
                instance = ctor.newInstance(context);
		if (instance != null && instance instanceof IBinder) {
			ServiceManager.addService(klass.getSimpleName(), (IBinder) instance); 
	       }
             }
        } catch (Exception e) {
            Log.e(TAG, "Unable to create LocationProxy instance", e);
        }
return instance;
	}
    //    }
    //    return null;
}
