package com.marketingknob.clubapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Akshya on 5/10/2018.
 */

public class Prefs {
	public static SharedPreferences sharedPreferences = null;
	public static String PREF_FILE = "cast_display";

	public static void openPrefs(Context context) {
		sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
	}

	public static String getvalue(Context context, String key, String defaultValue) {

		Prefs.openPrefs(context);

		String result = Prefs.sharedPreferences.getString(key, defaultValue);
		Prefs.sharedPreferences = null;
		return result;
	}

	public static void setValue(Context context, String key, int value) {
		Prefs.openPrefs(context);
		Editor preferenceEditor = Prefs.sharedPreferences.edit();
		preferenceEditor.putInt(key, value);
		preferenceEditor.commit();
		preferenceEditor = null;
		Prefs.sharedPreferences = null;
	}

	public static void setValue(Context context, String key, String value) {
		Prefs.openPrefs(context);
		Editor preferenceEditor = Prefs.sharedPreferences.edit();
		preferenceEditor.putString(key, value);
		preferenceEditor.commit();
		preferenceEditor = null;
		Prefs.sharedPreferences = null;
	}

	public static void setValue(Context context, String key, boolean value) {
		Prefs.openPrefs(context);
		Editor preferenceEditor = Prefs.sharedPreferences.edit();
		preferenceEditor.putBoolean(key, value);
		preferenceEditor.commit();
		preferenceEditor = null;
		Prefs.sharedPreferences = null;
	}

	public static boolean getvalue(Context context, String key, boolean defaultValue) {

		Prefs.openPrefs(context);

		boolean result = Prefs.sharedPreferences.getBoolean(key, defaultValue);
		Prefs.sharedPreferences = null;
		return result;
	}

	public static int getvalue(Context context, String key, int defaultValue) {

		Prefs.openPrefs(context);

		int result = Prefs.sharedPreferences.getInt(key, defaultValue);
		Prefs.sharedPreferences = null;
		return result;
	}

	public static void setClear(Context context) {
		Prefs.openPrefs(context);
		Editor preferenceEditor = Prefs.sharedPreferences.edit();
		preferenceEditor.clear().commit();
		preferenceEditor = null;
		Prefs.sharedPreferences = null;
	}

	public static void remove(Context context, String key) {
		Prefs.openPrefs(context);
		Editor preferenceEditor = Prefs.sharedPreferences.edit();
		preferenceEditor.remove(key);
		preferenceEditor.commit();
		preferenceEditor = null;
		Prefs.sharedPreferences = null;
	}

}
