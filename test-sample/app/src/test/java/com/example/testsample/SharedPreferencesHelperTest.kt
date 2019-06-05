package com.example.testsample

import android.content.SharedPreferences
import com.example.testsample.enity.UserInfo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesHelperTest {

    companion object {
        private val TAG = SharedPreferencesHelperTest::class.java.simpleName

        private const val TEST_NAME = "Test name"
        private const val TEST_EMAIL = "test@email.com"
        private val TEST_DATE_OF_BIRTH = Calendar.getInstance()
    }

    init {
        TEST_DATE_OF_BIRTH.set(1996, 3, 17)
    }

    private var mUserInfo: UserInfo? = null

    private var mMockSharedPreferencesHelper: SharedPreferencesHelper? = null

    private var mMockBrokenSharedPreferencesHelper: SharedPreferencesHelper? = null

    @Mock
    var mMockSharedPreferences: SharedPreferences? = null

    @Mock
    var mMockBrokenPreference: SharedPreferences? = null

    @Mock
    var mMockEditor: SharedPreferences.Editor? = null

    @Mock
    var mMockBrokenEditor: SharedPreferences.Editor? = null

    @Before
    fun initMocks() {
        // create entity
        mUserInfo = UserInfo(TEST_NAME, TEST_DATE_OF_BIRTH.timeInMillis, TEST_EMAIL)

        // create a mocked SharedPreferences
        mMockSharedPreferencesHelper = createMockSharedPreference()

        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesHelper = createMockBrokenSharedPreference()
    }

    @Test
    fun sharedPreferencesHelper_SaveAndReadPersonalInfo() {
        val success = mUserInfo?.let { mMockSharedPreferencesHelper?.savePersonalInfo(it) }

        if (success != null) {
            assertThat("Checking that SharedPreferenceEntry.save... returns true", success, `is`(true))
        } else {
            assertThat("Checking that SharedPreferenceEntry.save... returns true", success, equalTo(null))
        }


        if (mMockSharedPreferencesHelper != null) {
            // Read personal information from SharedPreferences
            val userInfo = mMockSharedPreferencesHelper?.getPersonalInfo()

            // Make sure both written and retrieved personal information are equal.
            assertThat("Checking that UserInfo.name has been persisted and read correctly", mUserInfo?.name, `is`(equalTo(userInfo?.name)))
            assertThat("Checking that UserInfo.dateOfBirth has been persisted and read correctly", mUserInfo?.dateOfBirth, `is`(equalTo(userInfo?.dateOfBirth)))
            assertThat("Checking that UserInfo.email has been persisted and read correctly", mUserInfo?.email, `is`(equalTo(userInfo?.email)))
        }

    }

    @Test
    fun sharedPreferencesHelper_SavePersonalInfoFailed_ReturnsFalse() {
        // Read personal information from a broken

        val success = mUserInfo?.let { mMockBrokenSharedPreferencesHelper?.savePersonalInfo(it) }

        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success, `is`(false))
    }


    private fun createMockSharedPreference(): SharedPreferencesHelper? {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written correctly.
        `when`(mMockSharedPreferences?.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString())).thenReturn(mUserInfo?.name)
        `when`(mMockSharedPreferences?.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong())).thenReturn(mUserInfo?.dateOfBirth)
        `when`(mMockSharedPreferences?.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString())).thenReturn(mUserInfo?.email)

        // Mocking a successful commit.
        `when`(mMockEditor?.commit()).thenReturn(true)

        // Return the MockEditor when requesting it
        `when`(mMockSharedPreferences?.edit()).thenReturn(mMockEditor)
        return mMockSharedPreferencesHelper
    }

    private fun createMockBrokenSharedPreference(): SharedPreferencesHelper? {
        `when`(mMockBrokenEditor?.commit()).thenReturn(false)

        // Return the broken MockEditor when requesting it
        `when`(mMockBrokenPreference?.edit()).thenReturn(mMockBrokenEditor)

        return mMockBrokenPreference?.let { SharedPreferencesHelper(it) }
    }
}
