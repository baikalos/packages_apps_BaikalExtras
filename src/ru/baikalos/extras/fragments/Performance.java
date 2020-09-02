/*
 * Copyright (C) 2014 The Dirty Unicorns Project
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

package ru.baikalos.extras.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.SystemProperties;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import android.view.View;
import android.util.Log;

import android.content.res.Resources;

import com.android.internal.baikalos.Actions;

import ru.baikalos.extras.BaseSettingsFragment;
import ru.baikalos.extras.PerfProfileDetailsActivity;
import ru.baikalos.extras.R;

import com.aicp.gear.preference.SeekBarPreferenceCham;
import com.aicp.gear.preference.SecureSettingSeekBarPreference;

public class Performance extends BaseSettingsFragment {

    private static final String TAG = "Performance";

    private Context mContext;

    private ListPreference mDefaultPerfProfile;
    private ListPreference mDefaultThermProfile;

    private ListPreference mScrOffPerfProfile;
    private ListPreference mIdlePerfProfile;
    private ListPreference mIdleThermProfile;
    private ListPreference mEditPerfProfile;

    @Override
    protected int getPreferenceResource() {
        return R.xml.performance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = (Context) getActivity();
        final Resources res = getActivity().getResources();

        final PreferenceScreen screen = getPreferenceScreen();

        final PreferenceCategory profilesCategory =
                (PreferenceCategory) screen.findPreference("default_profiles");


        boolean perfProf  = SystemProperties.get("baikal.eng.perf", "0").equals("1");
        boolean thermProf  = SystemProperties.get("baikal.eng.therm", "0").equals("1");
        boolean perfEdit  = SystemProperties.get("baikal.eng.perf.edit", "0").equals("1");

        if( !perfProf && !thermProf ) {
            if( profilesCategory != null ) {
                screen.removePreference(profilesCategory);
            }
        }

        try {

            mDefaultPerfProfile = (ListPreference) findPreference("default_perf_profile");
            if( mDefaultPerfProfile != null ) { 
                if( !perfProf ) {
                    mDefaultPerfProfile.setVisible(false);
                } else {
                    String profile = getSystemPropertyString("persist.baikal.perf.default","balance");
                    Log.e(TAG, "mDefaultPerfProfile: getProfile=" + profile);
                    mDefaultPerfProfile.setValue(profile);
                    mDefaultPerfProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mDefaultPerfProfile: setProfile=" + newValue.toString());
			                setSystemPropertyString("persist.baikal.perf.default",newValue.toString());
                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mDefaultPerfProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }

            mDefaultThermProfile = (ListPreference) findPreference("default_therm_profile");
            if( mDefaultThermProfile != null ) { 
                if( !thermProf ) {
                    mDefaultThermProfile.setVisible(false);
                } else {
                    String profile = getSystemPropertyString("persist.baikal.therm.default","balance");
                    Log.e(TAG, "mDefaultThermProfile: getProfile=" + profile);
                    mDefaultThermProfile.setValue(profile);
                    mDefaultThermProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mDefaultThermProfile: setProfile=" + newValue.toString());
			                setSystemPropertyString("persist.baikal.therm.default",newValue.toString());
                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mDefaultPerfProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }


            mScrOffPerfProfile = (ListPreference) findPreference("scr_off_perf_profile");
            if( mScrOffPerfProfile != null ) { 
                if( !perfProf ) {
                    mScrOffPerfProfile.setVisible(false);
                } else {
                    String profile = getSystemPropertyString("persist.baikal.perf.scr_off","battery");
                    Log.e(TAG, "mScrOffPerfProfile: getProfile=" + profile);
                    mScrOffPerfProfile.setValue(profile);
                    mScrOffPerfProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mScrOffPerfProfile: setProfile=" + newValue.toString());
			                setSystemPropertyString("persist.baikal.perf.scr_off",newValue.toString());
                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mScrOffPerfProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }


            mIdlePerfProfile = (ListPreference) findPreference("idle_perf_profile");
            if( mIdlePerfProfile != null ) { 
                if( !perfProf ) {
                    mIdlePerfProfile.setVisible(false);
                } else {
                    String profile = getSystemPropertyString("persist.baikal.perf.idle","battery");
                    Log.e(TAG, "mIdlePerfProfile: getProfile=" + profile);
                    mIdlePerfProfile.setValue(profile);
                    mIdlePerfProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mIdlePerfProfile: setProfile=" + newValue.toString());
			                setSystemPropertyString("persist.baikal.perf.idle",newValue.toString());
                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mIdlePerfProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }

            mIdleThermProfile = (ListPreference) findPreference("idle_therm_profile");
            if( mIdleThermProfile != null ) { 
                if( !perfProf ) {
                    mIdleThermProfile.setVisible(false);
                } else {
                    String profile = getSystemPropertyString("persist.baikal.therm.idle","cool");
                    Log.e(TAG, "mIdleThermProfile: getProfile=" + profile);
                    mIdleThermProfile.setValue(profile);
                    mIdleThermProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mIdleThermProfile: setProfile=" + newValue.toString());
			                setSystemPropertyString("persist.baikal.therm.idle",newValue.toString());
                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mIdleThermProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }

            mEditPerfProfile = (ListPreference) findPreference("edit_perf_profile");
            if( mEditPerfProfile != null ) { 
                if( !perfProf || !perfEdit ) {
                    mEditPerfProfile.setVisible(false);
                } else {
                    //String profile = getSystemPropertyString("persist.baikal.therm.idle","cool");
                    //Log.e(TAG, "mIdleThermProfile: getProfile=" + profile);
                    //mIdleThermProfile.setValue(profile);
                    mEditPerfProfile.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                      public boolean onPreferenceChange(Preference preference, Object newValue) {
                        try {
                            Log.e(TAG, "mEditPerfProfile: setProfile=" + newValue.toString());
                            Intent intent = new Intent(mContext, PerfProfileDetailsActivity.class);
                            intent.putExtra("profileName",newValue.toString());
                            startActivity(intent);

                        } catch(Exception re) {
                            Log.e(TAG, "onCreate: mEditPerfProfile Fatal! exception", re );
                        }
                        return true;
                      }
                    });
                }
            }

        } catch(Exception re) {
            Log.e(TAG, "onCreate: Fatal! exception", re );
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setSystemPropertyString(String key, String value) {
        Log.e(TAG, "setSystemPropertyBoolean: key=" + key + ", value=" + value);
        SystemProperties.set(key, value);
    }

    private String getSystemPropertyString(String key, String def) {
        return SystemProperties.get(key,def);
    }

    private boolean getSystemPropertyBoolean(String key) {
        if( SystemProperties.get(key,"0").equals("1") || SystemProperties.get(key,"0").equals("true") ) return true;
	    return false;
    }

    private void setSystemPropertyBoolean(String key, boolean value) {
        String text = value?"1":"0";
        Log.e(TAG, "setSystemPropertyBoolean: key=" + key + ", value=" + value);
        SystemProperties.set(key, text);
    }

}
