/*
 * Copyright (c) 2013 zedvoid.
 *
 * This file is part of Indico.
 *
 * Indico is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Indico is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Indico.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oigoiania.augmentedreality;

public class CircleUtils {
// ------------------------------ FIELDS ------------------------------

    private static final float CONV_CONST = 57.32484f;

// -------------------------- STATIC METHODS --------------------------

    public static int normalize(int degrees) {
        int effectiveAngle = Math.abs(degrees) % 360;

        if (degrees < 0) {
            effectiveAngle = 360 - Math.abs(effectiveAngle);
        }

        return effectiveAngle;
    }

    public static void normalize(int[] angles) {
        for (int i = 0; i < angles.length; i++) {
            angles[i] = normalize(angles[i]);
        }
    }

    public static int getRotationRequiredForNormalized(int initialAngle, int finalAngle) {
        int d1 = normalize(finalAngle) - normalize(initialAngle);
        int d2 = 360 - Math.abs(d1);

        if (d1 < 0) {
            d2 = Math.abs(d2);
        } else {
            d2 = -Math.abs(d2);
        }

        if (Math.abs(d1) < Math.abs(d2)) {
            return d1;
        } else {
            return d2;
        }
    }

    public static void getRotationRequiredForNormalized(int[] initalAngles, int[] finalAngles, int[] results) {
        for (int i = 0; i < results.length; i++) {
            results[i] = getRotationRequiredForNormalized(initalAngles[i], finalAngles[i]);
        }
    }

    public static int convertRadToDegrees(float rad) {
        return Math.round(rad * CONV_CONST);
    }

    public static void convertRadToDegrees(float[] rads, int[] results) {
        for (int i = 0; i < results.length; i++) {
            results[i] = convertRadToDegrees(rads[i]);
        }
    }
}
