package com.github.dsh105.sparktrail.menu;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Project by DSH105
 */

public abstract class Menu {

	public Player viewer;
	public Location location = null;
	public UUID mobUuid = null;

	public abstract void setItems();
	public abstract void open(boolean sendMessage);
}