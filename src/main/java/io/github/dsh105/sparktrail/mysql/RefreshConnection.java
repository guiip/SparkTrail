package com.github.dsh105.sparktrail.mysql;

import com.github.dsh105.sparktrail.SparkTrail;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project by DSH105
 */

public class RefreshConnection extends BukkitRunnable {

	private int timeout;

	public RefreshConnection(int timeout) {
		this.timeout = timeout;
		this.runTaskLater(SparkTrail.getInstance(), this.timeout);
	}

	@Override
	public void run() {
		SQLConnection sql = SparkTrail.getInstance().sqlCon;
		sql.close();
		sql.connect();
	}
}