/*
 * This file is part of a module with proprietary Enterprise Features.
 *
 * Licensed to Crate.io Inc. ("Crate.io") under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * To use this file, Crate.io must have given you permission to enable and
 * use such Enterprise Features and you must have a valid Enterprise or
 * Subscription Agreement with Crate.io.  If you enable or use the Enterprise
 * Features, you represent and warrant that you have a valid Enterprise or
 * Subscription Agreement with Crate.io.  Your use of the Enterprise Features
 * if governed by the terms and conditions of your Enterprise or Subscription
 * Agreement with Crate.io.
 */

package io.crate.license;

import com.google.common.annotations.VisibleForTesting;

import static io.crate.license.LicenseKey.LicenseType;

final class TrialLicense {

    private TrialLicense() {
    }

    static LicenseKey createLicenseKey(int version, LicenseData licenseData) {
        return createLicenseKey(version, LicenseConverter.toJson(licenseData));
    }

    @VisibleForTesting
    static LicenseKey createLicenseKey(int version, byte[] decryptedContent) {
        byte[] encryptedContent = encrypt(decryptedContent);
        return LicenseKey.createLicenseKey(LicenseType.TRIAL,
            version,
            encryptedContent);
    }

    private static byte[] encrypt(byte[] data) {
        return CryptoUtils.encryptAES(data);
    }

    static byte[] decrypt(byte[] encryptedContent) {
        return CryptoUtils.decryptAES(encryptedContent);
    }
}
