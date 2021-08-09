# ForcePerms
## Why does this exist?
I couldn't figure out why the permissions like ``minecraft.command.gamemode`` didn't exist in the latest version of LuckPerms, and I needed them very desperately so I made this as a stopgap solution.

## This mod was tested with
Minecraft:           1.17.1 \
Fabric API:          0.37.2 \
LuckPerms (Fabric):  5.3.57

## What does it do?
You can now manually allow and disallow commands that do not have permission checks via ``forceperms.commands.COMMANDNAME`` permissions. \
Do regard that if the command does have its own permission check, that it will still be run after the ForcePerms check.
You can also set a precedent value for allowing by setting ``forceperms.default`` to either true or false.

Internally this is implemented by checking if a permission is set for the specified command. \
If it is, great, use that as our result. \
If it is not, use the ``forceperms.default`` result. \

## Possible configurations

### Manually disallowed commands
``forceperms.default`` set to ``true``

Each disallowed command: \
    ``forceperms.commands.DISALLOWEDCOMMAND`` commands set to ``false``
#### This configuration will allow all commands except the disallowed one to run.

### Manually allowed commands
``forceperms.default`` set to ``false``

Each allowed command: \
``forceperms.commands.ALLOWEDCOMMAND`` commands set to ``true``
#### This configuration will allow only the allowed commands to run.

# Warning: Please try to avoid using this mod unless you have no other choice. We do not take responsibility for you or your users' actions.
