package com.phidalgo.crudcategories.data.network.auth

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    context: Context
) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    suspend fun login(email: String, password: String): Result<AuthResult?> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            saveSession(true)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<AuthResult?> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            saveSession(true)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        clearSession()
    }

    fun isSessionActive(): Boolean {
        return sharedPreferences.getBoolean("session_active", false)
    }

    private fun saveSession(isActive: Boolean) {
        sharedPreferences.edit().putBoolean("session_active", isActive).apply()
    }

    private fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    fun getCurrentUser() = firebaseAuth.currentUser
}
