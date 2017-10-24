## BlockHunt

### Description

BlockHunt is a customizable Minecraft minigame where you try to find as many of the specified block in the time provided. The play area is defined by a WorldGuard region. While in the WorldGuard region, a scoreboard of the current top 10 players will appear. At the end, a prize from a customizable list be chosen and given to the winner.

This is very much a work in progress at the moment.

The original idea comes from Pumpkin Hunt by TylerOG: https://www.spigotmc.org/resources/pumpkin-hunt.13966/

The project home is: https://github.com/JakeMoe/BlockHunt

### Installation

To install, just copy the jar file into your server's plugin folder. It will create a default config file the first time it is run.

### Configuration

To configure, you can edit the config.yml file by hand, or use in game commands. Type "/bh help" for more info. 

### Version History

* 0.1 - Jake Moe - 25 September 2017
  * Initial Commit
* 0.2 - Jake Moe - 25 September 2017
  * Filled out most of the load/enable/disable
  * Filled out commands
  * Filled out listener
  * Filled out config (read and write)
* 0.3 - Jake Moe - 25 September 2017
  * Updated version strings
  * Updated plugin.yml
  * Moved scoreboard out to separate class
* 0.4 - Jake Moe - 25 September 2017
  * Moved timer from Timer to BukkitRunnable
  * Fixed up various status messages
* 0.5 - Jake Moe - 25 September 2017
  * Made scoreboard title configurable
  * Added winner notification
  * Added player names instead of UUIDs
  * Formatted about and syntax output with colors
* 0.6 - Jake Moe - 26 September 2017
  * Added WorldGuard library and check for plugin
  * Code cleanup
* 0.7 - Jake Moe - 26 September 2017
  * Added WorldGuard region checks
  * Added Nicky plugin support
* 0.8 - Jake Moe - 27 September 2017
  * Created individual help for each command
  * Added prizes
  * Added Regions class
  * Code cleanup
* 0.9 - Jake Moe - 11 October 2017
  * Transfer to lobby then to game works
  * Lobby and game timers work
* 0.10 - Jake Moe - 14 October 2017
  * Split out Lobby and Game regions
* 0.11 - Jake Moe - 15 October 2017
  * Stores inventory, health, location before game
  * Restores inventory, health, location after game
  * Fixed up score from public variable to private variable with public methods
  * Found better way to sort HashMap by value; new method in Util class
  * Bug fixes
* 0.12 - Jake Moe - 25 October 2017
  * Save scores to files
  * Reset game with random block drops
  * Random potion drops
  * Better looking messages