# FFACore

A Minecraft plugin for running Free For All arenas.

> Players can pick an arena and fight without having to queue for a match. Arenas can be created and edited in-game, with options for block interaction, explosions, item drops, block decay and map regeneration.

## Features

* Multiple FFA arenas
* Arena selection using a WorldEdit-style wand
* In-game arena creation and editing menus
* Per-arena spawn locations
* Arena warps with per-arena permissions
* Combat logging protection
* Optional block breaking and placing
* Block decay timers
* Map regeneration timers
* Explosion settings
* Item drop settings
* Persistent arena configuration
* MiniMessage-based messages and GUI text

## Dependencies
- [WorldEdit](https://modrinth.com/plugin/fastasyncworldedit)

## Commands

### `/selection-wand`

Gives you the arena selection tool.

Select two corners of the area you want to use for an arena.

### `/add-arena <name>`

Creates a new arena from the current selection.

After creating it, FFACore opens the arena setup menu where you can configure things such as:

* Arena name
* Block breaking/placing
* Block decay
* Map regeneration
* Explosions
* Item drops

### `/arenas`

Opens the arena management menu.

From there you can edit existing arenas, regenerate maps, teleport to an arena spawn, or delete an arena.

### `/warp <arena>`

Teleports you to an arena.

Players need permission for the specific arena:

```text
ffa.warp.<arena>
```

For example:

```text
ffa.warp.desert
```

Warping is also blocked while the player is in combat.

## Permissions

| Permission         | Description                             |
| ------------------ | --------------------------------------- |
| `ffa.admin`        | Access to arena administration commands |
| `ffa.warp.<arena>` | Access to a specific arena              |

`ffa.admin` is an operator permission by default.

## Arena setup

The setup process looks like this:

1. Get the selection wand using the `/selection-wand` command.
2. Select the 2 points of the arena.
3. Run `/add-arena <name>`.
4. Configure the arena in the GUI.
5. Save it. 
6. Give players access with `ffa.warp.<name>`.

## Arena options

> Each arena can have its own rules:

### Blocks

Block interaction can be set to:

* None
* Place only
* Break only
* Both

### Block decay

Placed blocks can automatically decay after:

* 5 seconds
* 10 seconds
* 30 seconds
* 1 minute
* 3 minutes
* Never

### Map regeneration

Arena regeneration can be configured for:

* 30 minutes
* 1 hour
* 6 hours
* 1 day
* Never

### Explosions

Explosions support separate handling for:

* No explosion effects
* Damage only
* Block breaking only
* Both

### Item drops

Item drops can be enabled or disabled per arena.

## Combat

> Players in combat can't use arena warps until their combat timer has expired. This prevents players from simply teleporting away when they are about to lose a fight. The default combat timer is 30 seconds.

## Configuration

Most plugin messages and GUI settings are kept in `config.yml`, including:

* Startup/shutdown messages
* Permission messages
* Combat messages
* Teleport messages
* Selection messages
* GUI titles
* GUI item names and lore
* Arena setting labels

## Limits

The current implementation allows up to 45 arenas cuz' I'm lazy. May add support for more arenas in the future.

## License

FFACore is released under the CC0 1.0 license. Our plugin ☭

## AI Usage
I asked an LLM to write the readme for me based on the project files etc. I cleaned it up wherever I can, and tried to reformat it to look as nice as possible, added explanations. If AI usage bothers you, feel free to contribute.
