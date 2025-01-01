package com.yeudaby.wearzon.presentation.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yeudaby.wearzon.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun <T> readSetting(context: Context, key: Preferences.Key<T>): Flow<T?> {
    return context.dataStore.data.map { preferences ->
        preferences[key]
    }
}

suspend fun <T> saveSetting(context: Context, key: Preferences.Key<T>, value: T) {
    context.dataStore.edit { preferences ->
        preferences[key] = value
    }
}

object PreferencesKeys {
    val NIKUD = booleanPreferencesKey("nikud")
    val NUSACH = stringPreferencesKey("nusach")
    val FONT_SIZE = stringPreferencesKey("font_size")
}

enum class NusachOption(@StringRes val displayName: Int) {
    ASHKENAZ(R.string.ashkenaz),
    EDOT_HAMIZRACH(R.string.edot_hamizrach),
    SEFARD(R.string.sefard)
}

enum class FontSize(
    @StringRes val displayName: Int
) {
    SMALL(R.string.small),
    MEDIUM(R.string.medium),
    LARGE(R.string.large)
}
