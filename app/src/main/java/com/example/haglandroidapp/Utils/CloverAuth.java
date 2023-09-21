package com.example.haglandroidapp.Utils;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.clover.sdk.internal.util.UnstableContentResolverClient;
import com.clover.sdk.util.CloverAccount;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CloverAuth {

    private static final String TAG = CloverAuth.class.getSimpleName();

    /**
     * Authenticates with the Clover service.  This method makes a network call to the
     * Clover service. It should be run on a background thread.
     * <p/>
     * This method will block indefinitely for a result.
     *
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Activity activity, Account account, boolean forceValidateToken)
            throws OperationCanceledException, AuthenticatorException, IOException {
        return authenticate(activity, account, forceValidateToken, null, null);
    }

    /**
     * Authenticates with the Clover service.  This method makes a network call to the
     * Clover service. It should be run on a background thread.
     *
     * @param activity           the activity that initiated the authentication
     * @param account            the account used for authentication
     * @param forceValidateToken flag for if token should be validated against API.
     *                           Increases response latency, use only when necessary.
     * @param timeout            the maximum time to wait
     * @param unit               the time unit of the timeout argument. This must not be null.
     * @see #authenticate(Context, boolean, Long, TimeUnit)
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Activity activity, Account account, boolean forceValidateToken, Long timeout, TimeUnit unit)
            throws OperationCanceledException, AuthenticatorException, IOException {
        ensureTargetSdkLessThan26(activity);

        Log.d(TAG, "Authenticating " + account);
        final Bundle options = new Bundle();
        options.putBoolean(CloverAccount.KEY_FORCE_VALIDATE, forceValidateToken);
        AccountManager accountManager = AccountManager.get(activity);
        AccountManagerFuture<Bundle> future = accountManager.getAuthToken(account,
                CloverAccount.CLOVER_AUTHTOKEN_TYPE, options, activity, null, null);
        Bundle result;
        if (timeout != null && unit != null) {
            Log.d(TAG, "Getting result with timeout " + timeout + " (" + unit + ")");
            result = future.getResult(timeout, unit);
        } else {
            Log.d(TAG, "Getting result (no timeout)");
            result = future.getResult();
        }
        return new AuthResult(result);
    }

    /**
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Context context, Account account, boolean forceValidateToken)
            throws OperationCanceledException, AuthenticatorException, IOException {
        return authenticate(context, account, forceValidateToken, null, null);
    }

    /**
     * @see #authenticate(Context, boolean, Long, TimeUnit)
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Context context, Account account, boolean forceValidateToken, Long timeout, TimeUnit unit)
            throws OperationCanceledException, AuthenticatorException, IOException {
        ensureTargetSdkLessThan26(context);

        Log.d(TAG, "Authenticating " + account);
        final Bundle options = new Bundle();
        options.putBoolean(CloverAccount.KEY_FORCE_VALIDATE, forceValidateToken);
        AccountManager accountManager = AccountManager.get(context);
        @SuppressLint("MissingPermission") AccountManagerFuture<Bundle> future = accountManager.getAuthToken(account,
                CloverAccount.CLOVER_AUTHTOKEN_TYPE, options, false, null, null);
        Bundle result;
        if (timeout != null && unit != null) {
            Log.d(TAG, "Getting result with timeout " + timeout + " (" + unit + ")");
            result = future.getResult(timeout, unit);
        } else {
            Log.d(TAG, "Getting result (no timeout)");
            result = future.getResult();
        }
        return new AuthResult(result);
    }

    /**
     * @see #authenticate(Context, boolean, Long, TimeUnit)
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Activity activity, Account account)
            throws OperationCanceledException, AuthenticatorException, IOException {
        return authenticate(activity, account, false);
    }

    /**
     * @see #authenticate(Context, boolean, Long, TimeUnit)
     * @deprecated Use {@link #authenticate(Context, boolean, Long, TimeUnit)}
     * (required when targeting SDK level 26 or higher)
     */
    @Deprecated
    public static AuthResult authenticate(Context context, Account account)
            throws OperationCanceledException, AuthenticatorException, IOException {
        return authenticate(context, account, false);
    }

    public static final String AUTH_AUTHORITY = "com.clover.app.auth";
    public static final Uri AUTH_URI = Uri.parse("content://" + AUTH_AUTHORITY);

    public static final String METHOD_AUTH = "auth";
    public static final String METHOD_AUTH_PERMISSIONS = "authPermissions";

    private static final ExecutorService exec = Executors.newSingleThreadExecutor();

    /**
     * Equivalent to calling {@link #authenticate(Context, boolean, Long, TimeUnit)} with
     * force validate false, and a timeout of 20 seconds.
     *
     * @see #authenticate(Context, boolean, Long, TimeUnit)
     */
    public static AuthResult authenticate(Context context)
            throws InterruptedException, ExecutionException, TimeoutException {
        return authenticate(context, false, 20L, TimeUnit.SECONDS);
    }


    public static AuthResult authenticate(Context context, boolean forceValidateToken, Long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        Log.d(TAG, "Authenticating with provider (no account)");

        final Bundle options = new Bundle();
        options.putBoolean(CloverAccount.KEY_FORCE_VALIDATE, forceValidateToken);

        Log.d(TAG, "Getting result from provider with timeout " + timeout + " (" + unit + ")");

        UnstableContentResolverClient client = new UnstableContentResolverClient(context.getContentResolver(), AUTH_URI);

        Future<AuthResult> future = exec.submit(() -> {
            final Bundle result = client.call(METHOD_AUTH, null, options, null);
            if (result == null) {
                Log.w(TAG, "Bundle result null from provider");
                return null;
            }
            return new AuthResult(result);
        });

        if (timeout != null && unit != null) {
            return future.get(timeout, unit);
        } else {
            return future.get();
        }
    }

    private static final String ERR_MSG_TARGET_TOO_HIGH
            = "This method cannot be called if your app targets SDK levels > %d. "
            + "Please use the variant that does not require an account parameter.";

    private static void ensureTargetSdkLessThan26(Context context) {
        int targetSdk = getTargetSdk(context);
        if (targetSdk > Build.VERSION_CODES.N_MR1) {
            throw new IllegalStateException(String.format(Locale.ROOT, ERR_MSG_TARGET_TOO_HIGH, Build.VERSION_CODES.N_MR1));
        }
    }

    private static int getTargetSdk(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * Container for the data returned when an account authenticates with the Clover service. If an
     * error occurs while trying to obtain the token (due to network down, server error or permission
     * error) only the errorMessage field is guaranteed to be set, all other values may be null.
     */
    public static class AuthResult {
        /**
         * The auth token, used for sending subsequent requests to the service. May be null on error.
         */
        public final String authToken;

        /**
         * The base URL of the Clover service. REST API endpoints are relative to this URL. May be null
         * on error.
         */
        public final String baseUrl;

        /**
         * Error message that was generated during authentication, or {@code null}.
         */
        public final String errorMessage;

        /**
         * The id of the merchant associated with the authenticated account. May be null on error.
         * <p>
         * Prefer <code>new MerchantConnector(...).getMerchant().getId()</code>, which always works
         * even if an auth token cannot be obtained due to error.
         */
        public final String merchantId;

        /**
         * The id of the app that performed authentication. May be null on error.
         */
        public final String appId;

        /**
         * The complete set of data returned by {@link android.accounts.AccountManager}.
         */
        public final Bundle authData;

        public AuthResult(Bundle authData) {
            this.authData = new Bundle(authData);
            authToken = authData.getString(AccountManager.KEY_AUTHTOKEN);
            baseUrl = authData.getString(CloverAccount.KEY_BASE_URL);
            merchantId = authData.getString(CloverAccount.KEY_MERCHANT_ID);
            appId = authData.getString(CloverAccount.KEY_APP_ID);
            errorMessage = authData.getString(AccountManager.KEY_ERROR_MESSAGE);

            Log.e("authToken", authToken);
        }

        @Override
        public String toString() {
            return "AuthResult{" +
                    "authToken='" + authToken + '\'' +
                    ", baseUrl='" + baseUrl + '\'' +
                    ", errorMessage='" + errorMessage + '\'' +
                    ", merchantId='" + merchantId + '\'' +
                    ", appId='" + appId + '\'' +
                    ", authData=" + authData +
                    '}';
        }
    }
}
