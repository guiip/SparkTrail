/*
 * This file is part of SparkTrail 3.
 *
 * SparkTrail 3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SparkTrail 3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SparkTrail 3.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.util;

import org.bukkit.FireworkEffect;


public enum FireworkType {

    SMALL(FireworkEffect.Type.BALL),
    LARGE(FireworkEffect.Type.BALL_LARGE),
    BURST(FireworkEffect.Type.BURST),
    CREEPER(FireworkEffect.Type.CREEPER),
    STAR(FireworkEffect.Type.STAR);


    private FireworkEffect.Type fireworkType;

    FireworkType(FireworkEffect.Type fireworkType) {
        this.fireworkType = fireworkType;
    }

    public FireworkEffect.Type getFireworkType() {
        return this.fireworkType;
    }

    public static FireworkType getByType(FireworkEffect.Type type) {
        for (FireworkType t : FireworkType.values()) {
            if (t.getFireworkType().equals(type)) {
                return t;
            }
        }
        return null;
    }
}